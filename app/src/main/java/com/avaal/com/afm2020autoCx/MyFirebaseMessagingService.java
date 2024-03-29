package com.avaal.com.afm2020autoCx;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;


/**
 * Created by dell pc on 04-04-2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//                sendNotification(remoteMessage.getNotification().getBody());
                if (remoteMessage.getData().get("ContentType") == null)
                    sendNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("type"), "", remoteMessage.getData().get("id"));
                else
                    sendNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("type"), remoteMessage.getData().get("ContentType"), remoteMessage.getData().get("id"));
                if (remoteMessage.getData().get("type").equalsIgnoreCase("logout")) {

                    Intent dialogIntent = new Intent(this, LoginActivity.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    dialogIntent.putExtra("logoutAnother", true);
                    startActivity(dialogIntent);

//                    AfmDbHandler dbHandler = new AfmDbHandler(this);
//                    dbHandler.clearlogin();
//                    SharedPreferences.Editor editor = getSharedPreferences(SplashActivity.APP_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
//                    editor.putString("IsLogin", "");
//                    editor.putInt("driverId", 0);
//                    editor.putString("email", "ddd");
//                    editor.putString("corpid", "ddd");
//                    editor.commit();
//                    Intent msgIntent = new Intent(this, ToSaveTripdataService.class);
//                    startService(msgIntent);
//                    this.finishAffinity();
//                    finishAndRemoveTask ();
                }
//                else if(remoteMessage.getData().get("type").equalsIgnoreCase("Dispatched")){
//
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                }else if(remoteMessage.getData().get("type").equalsIgnoreCase("Enroute")){
//
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//
//                }else if(remoteMessage.getData().get("type").equalsIgnoreCase("Confirmed")){
//
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//
//                }else if(remoteMessage.getData().get("type").equalsIgnoreCase("Delivered")){
//
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
////        j.putExtra("open","pending");
//                }
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            sendNotification(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
//    private void scheduleJob() {
//        // [START dispatch_job]
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);
//        // [END dispatch_job]
//    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String type, String content, String order) {
        Intent intent = null;
        if (type.equalsIgnoreCase("Logout")) {
            intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        } else if (type.equalsIgnoreCase("OrderConfirm")) {
            intent = new Intent(this, NewOrderListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("AFMOrder", "err");
            intent.putExtra("orderId", "" + order);
//            startActivity(dialogIntent);
        } else {
            intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("open", "home");
//            startActivity(dialogIntent);

        }




    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT);
    String channelId = getString(R.string.default_notification_channel_id);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.notifi_icon)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Since android Oreo notification channel is needed.
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O)

    {
        NotificationChannel channel = new NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
    }

        notificationManager.notify(0 /* ID of notification */,notificationBuilder.build());
}
}