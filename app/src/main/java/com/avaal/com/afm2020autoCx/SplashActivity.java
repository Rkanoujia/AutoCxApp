package com.avaal.com.afm2020autoCx;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avaal.com.afm2020autoCx.models.LoginModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.jsoup.Jsoup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import extra.LoaderScreen;
import extra.MyImage;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by dell pc on 11-01-2018.
 */

public class SplashActivity extends AppCompatActivity {
    private Snackbar snackbar;
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    PreferenceManager prf;
    APIInterface apiInterface;
    boolean isLoaded = false;
    String token="dfdgfdgb";
    private static int CODE_AUTHENTICATION_VERIFICATION = 241;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        TextView version = (TextView) findViewById(R.id.version);
        ImageView bg_image = (ImageView) findViewById(R.id.center_img);

//        bg_image.setAlpha(0.6f);
        bg_image.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.splash_logo, 300, 300));

        final String androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        Log.e("token", "fcmToken" + token);
                        // Log and toast
//                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        prf = new PreferenceManager(SplashActivity.this);
        try {
            //commenting to test without version checker. uncomment to start checking.
//            versionChecker.execute();
            version.setText("Version " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        String djd=packageInfo.packageName;
        final PackageInfo finalPackageInfo = packageInfo;
        snackbar = Snackbar.make(findViewById(R.id.mainLinearSplash), "Check Your Internet Connection...", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(SplashActivity.this)) {
                    snackbar.dismiss();
                    showAnimation();
                    // oncheck(finalPackageInfo.packageName, androidDeviceId);

                } else {
                    if (!snackbar.isShown())
                        snackbar.show();
                }

            }
        });

//        new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//
//
//
//        if (prf.getBoolData("IsFingerprint")) {
//            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//            if (km.isKeyguardSecure()) {
//
//                Intent i = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    i = km.createConfirmDeviceCredentialIntent("Unlock "+getResources().getString(R.string.app_name), "confirm your Screen lock pattern, PIN or password");
//                }
//                startActivityForResult(i, CODE_AUTHENTICATION_VERIFICATION);
//            } else
//                Toast.makeText(SplashActivity.this, "No any security setup done by user(pattern or password or pin or fingerprint", Toast.LENGTH_SHORT).show();
//        }else{
//            Intent j = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(j);
//              finish();
//        }
//                }
//        }, 3000);


//        Intent j = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(j);
//        finish();

//        if (isNetworkAvailable(this)) {
//            showAnimation();

//            new Handler().postDelayed(new Runnable() {
//
//            /*
//            * Showing splash screen with a timer. This will be useful when you
//            * want to show case your app logo / company
//            */
//
//                @Override
//                public void run() {
//                    hideAnimation();
//                    Intent j = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(j);
//                    finish();
//                }
//            }, 3000);

            //oncheck(packageInfo.packageName, androidDeviceId);
//        } else
//            snackbar.show();

//        Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.translate);
//        animTranslate.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation arg0) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation arg0) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation arg0) {
//
//            }
//        });
//        logo.startAnimation(animTranslate);
//        Animation animFade = AnimationUtils.loadAnimation(this, R.anim.fadein);
//        logo.startAnimation(animFade);

//        new Handler().postDelayed(new Runnable() {
//            //
////            /*
////             * Showing splash screen with a timer. This will be useful when you
////             * want to show case your app logo / company
////             */
////
//            @Override
//            public void run() {
//                Intent j = new Intent(getApplicationContext(), WelcomeActivity.class);
//                startActivity(j);
//                finish();
//            }
//        }, 5000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==CODE_AUTHENTICATION_VERIFICATION)
        {
            login();
//            Toast.makeText(this, "Success: Verified user's identity", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent j = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(j);
            finish();
            Toast.makeText(this, "Failure: Unable to verify user's identity", Toast.LENGTH_SHORT).show();
        }
    }
    void login(){
        Util util=new Util();
        if(!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(SplashActivity.this, "Check Your Internet", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        showAnimation();


        Call<LoginModel> call1 = apiInterface.userLogin(prf.getStringData("corporateId")+"~"+prf.getStringData("userName")+"~regular~"+token+"~PRI~Android~"+Build.BRAND+" "+Build.MODEL+"~"+Build.VERSION.RELEASE,prf.getStringData("password"),"password","application/json");
        call1.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                LoginModel login = response.body();
                hideAnimation();
                try {
                    if (login.error == null) {
//                        MDToast mdToast = MDToast.makeText(SplashActivity.this, "Login Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                        mdToast.show();

                        prf = new PreferenceManager(SplashActivity.this);
                        prf.saveStringData("accessToken", login.access_token);
//                        MDToast mdToast = MDToast.makeText(SplashActivity.this, "Login Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                        mdToast.show();

//                        prf = new PreferenceManager(SplashActivity.this);
//                        prf.saveStringData("authKey", login.datavalue.authkey);
//                        prf.saveStringData("carrierPrimaryId", String.valueOf(login.datavalue.id));
//                        prf.saveStringData("userName", userName.getText().toString());
//                        prf.saveStringData("corporateId", corporateId.getText().toString());
//                        prf.saveBoolData("logout",true);
//
//                        if(remember.isChecked()) {
//                            prf.saveStringData("password", password.getText().toString());
//                            prf.saveBoolData("remember", true);
//                        }
//                        else
//                            prf.saveBoolData("remember", false);
                        Intent j = new Intent(SplashActivity.this, NewDashBoardActivity.class);
                        startActivity(j);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();


                    } else {
                        Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(j);
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(SplashActivity.this,null,"CxE001",e,"");
                    Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(j);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                call.cancel();
                Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(j);
                finish();
                new Util().sendSMTPMail(SplashActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });

    }
//    private void oncheck(String appId, String deviceId) {
//        String url = "http://192.168.1.182:8080/manifest/Api/Api_validation.action?appId=" + appId + "&deviceId=" + deviceId + "&source=android";
//        JSONObject js = null;
//        try {
//            js=new JSONObject();
//            js.put("appId",appId);
//            js.put("deviceId",deviceId);
//            js.put("sourceName","android");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonApi jshhdj = new JsonApi();
//        jshhdj.makeJsonObjectRequest(js,this, WebServiceUrl.Authenticate, new JsonInterface.VolleyResponseListener() {
//            @Override
//            public void onError(String message) {
//                hideAnimation();
//                Log.e("response error", message);
//                LayoutInflater inflater = getLayoutInflater();
//                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout_root));
//
//                TextView text = (TextView) layout.findViewById(R.id.text);
//                text.setText("Authentication fail..");
//                Toast toast = new Toast(getApplicationContext());
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.setView(layout);
//                toast.show();
////                Toast.makeText(SpashActivity.this, "Authentication fail..", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    hideAnimation();
//                    if (response.getBoolean("Status")) {
//                        Log.e("response error", response.getString("Status"));
//                        isSuccess(response.getString("AuthKey"));
//                    }else{
//                        LayoutInflater inflater = getLayoutInflater();
//                        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout_root));
//                        TextView text = (TextView) layout.findViewById(R.id.text);
//                        text.setText("Authentication fail..");
//                        Toast toast = new Toast(getApplicationContext());
//                        toast.setDuration(Toast.LENGTH_LONG);
//                        toast.setView(layout);
//                        toast.show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    private void isSuccess(String authKey) {
        Intent j = new Intent(getApplicationContext(), LoginActivity.class);
       // j.putExtra("AuthKey", authKey);
        startActivity(j);
        finish();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.mainLinearSplash);
        if (mainlayout != null && loaderScreen == null) {
            loaderScreen = new LoaderScreen(this);
            loaderView = loaderScreen.getView();
            loaderScreen.showBackground(getApplicationContext(), true);
            mainlayout.addView(loaderView, layoutParams);

            if (!isLoaded) {
                if (loaderView != null && loaderScreen != null) {
                    loaderView.setVisibility(View.VISIBLE);
                    loaderScreen.startAnimating();
                }
            }
        }
    }
    private void hideAnimation() {
        if (loaderView != null) {
            loaderView.setVisibility(View.GONE);
            mainlayout.removeView(loaderView);
        }
        if (loaderScreen != null) {
            loaderScreen.stopAnimation();
            loaderScreen = null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        showAnimation();
        new Handler().postDelayed(new Runnable() {
//            //            //
//////            /*
//////             * Showing splash screen with a timer. This will be useful when you
//////             * want to show case your app logo / company
//////             */
//////
//            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        forUpdate();
                    }
                }).start();

            }
        }, 500);
    }
    void forUpdate(){

        new GetVersionCode().execute();

//            Log.e("update", "Current version " + currentVersion + "playstore version " + mLatestVersionName);
//                    if (mLatestVersionName != null && !mLatestVersionName.isEmpty()) {
////                hideAnimation();
//                        Intent j = new Intent(getApplicationContext(), LoginActivity.class);
////                            j.putExtra("APP_Token", token);
//                        j.putExtra("logoutAnother", false);
//                        j.putExtra("AppVersion", mLatestVersionName);
//                        startActivity(j);
//                        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                        finish();
//                    }else{
////               hideAnimation();
//                        Intent j = new Intent(getApplicationContext(), LoginActivity.class);
////                            j.putExtra("APP_Token", token);
//                        j.putExtra("logoutAnother", false);
////                j.putExtra("AppVersion", mLatestVersionName);
//                        startActivity(j);
//                        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                        finish();
//                    }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {

//                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + LoginActivity.this.getPackageName() + "&hl=it").timeout(30000)
//
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
//                        .get()
//                        .select("div[itemprop=softwareVersion]")
//                        .first()
//                        .ownText();
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+SplashActivity.this.getPackageName())
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();

                return newVersion;

            } catch (Exception e) {

                return newVersion;

            }

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                PackageInfo pInfo = null;
                try {
                    pInfo = SplashActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (!pInfo.versionName.equalsIgnoreCase(onlineVersion)) {
                    hideAnimation();
                    //show dialog
                    showUpdateAlert();

                }else{
                    if(prf.getStringData("userName")!=null) {
//            userName.setText(prf.getStringData("userName"));
//            corporateId.setText(prf.getStringData("corporateId"));
                        if(prf.getBoolData("remember")) {
//                remember.setChecked(true);
//                password.setText(prf.getStringData("password"));
                            if(prf.getBoolData("logout")) {
//                                if (prf.getBoolData("IsFingerprint")) {
//                                    Intent j = new Intent(getApplicationContext(), FingerprintActivity.class);
//                                    j.addFlags(j.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(j);
//                                    finish();
//                                }else
                                login();
                            } else{
                                Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(j);
                                finish();
                            }
                        }else{
                            Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(j);
                            finish();
                        }
                    }else{
                        Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(j);
                        finish();
                    }
                }

            }

//            Log.e("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }
    void showUpdateAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
        alertDialog.setTitle("Please update your app");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("This app version is no longer supported. Update your app .");
        alertDialog.setPositiveButton("UPDATE NOW", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        alertDialog.show();
    }

}





