package extra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.AddImageActivity;
import com.avaal.com.afm2020autoCx.AddVehicleActivity;
import com.avaal.com.afm2020autoCx.BuildConfig;
import com.avaal.com.afm2020autoCx.NewDashBoardActivity;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.models.ForAddModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.VehicleInfoModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by dell pc on 19-03-2018.
 */

public class Util {
    public boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//
//
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        Boolean Connected = false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) for (int i = 0; i < info.length; i++)
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                    Log.e("My Network is: ", "Connected");
                    Connected = true;
                } else {}
        } else {
//            Log.e("My Network is: ", "Not Connected");
//
//            Toast.makeText(CurrentActivity.getApplicationContext(),
//                    "Please Check Your internet connection",
//                    Toast.LENGTH_LONG).show();
            Connected = false;

        }
        return Connected;
    }

   public void save(GetVehicleIdListModel.datavalue selectVehicle ,Activity activity)throws Exception {

        PreferenceManager prf = new PreferenceManager(activity);
        String orderDate = "",inventryDate="";
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<VehicleInfoModel> call1 = apiInterface.saveVehicle(selectVehicle.vehiocleId,""+selectVehicle.TempOrderID, "" + selectVehicle.vinNumber, "" + selectVehicle.model, "" +selectVehicle.makeV, "" + selectVehicle.year, "" + selectVehicle.oem, "" + selectVehicle.mileageValue, ""+selectVehicle.mileageUnit,"" + selectVehicle.tpms, "" + selectVehicle.declareValue, "" + selectVehicle.buildYear, ""+selectVehicle.buildMonth,"" + selectVehicle.gvwrValue, selectVehicle.gvwrUnit,"" + selectVehicle.diesel, "" + selectVehicle.speedoConversion, "" + selectVehicle.titleConversion, ""+selectVehicle.title,"" + selectVehicle.billOfSale, "" + selectVehicle.trackingConfig, "" , ""+selectVehicle.isInventory, ""+selectVehicle.InventoryDate,""+selectVehicle.OrderDate ,""+prf.getStringData("userCode"),""+selectVehicle.releasefrom,""+selectVehicle.Color,""+selectVehicle.DeclaredCurrency,""+selectVehicle.Latitude,""+selectVehicle.Longitude,""+ prf.getStringData("userName"),new Util().getDateTime(),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<VehicleInfoModel>() {
            @Override
            public void onResponse(Call<VehicleInfoModel> call, Response<VehicleInfoModel> response) {

                VehicleInfoModel dropdata = response.body();

                try {
                    if (dropdata.status) {

                            MDToast mdToast = MDToast.makeText(activity, "Updated", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(activity,null,"CxE004",e,"");
                }
            }

            @Override
            public void onFailure(Call<VehicleInfoModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(activity,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

    public String getDateTime(){
        Calendar calendar = Calendar.getInstance();

        long time = System.currentTimeMillis();
        calendar.setTimeInMillis(time);

        Locale loc = new Locale("en", "US");


        //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, loc);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM/dd/yyyy hh:mm aa", loc);
        Date date = new Date();
//        Crashlytics.log(Log.DEBUG, "preprair Arrive Json data", "trip Id -"+model1.getDRIVERID());
        Log.e("calender date ",dateFormat.format(calendar.getTime()));
//        Log.e("date ",dateFormat.format(date));
        return dateFormat.format(calendar.getTime());
    }
    public String getDate(){
        Calendar calendar = Calendar.getInstance();

        Locale loc = new Locale("en", "US");
        long time = System.currentTimeMillis();
        calendar.setTimeInMillis(time);

        Log.e("UTC date ",""+calendar.getTime());

        //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, loc);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM/dd/yyyy", loc);
        Date date = new Date();
//        Crashlytics.log(Log.DEBUG, "preprair Arrive Json data", "trip Id -"+model1.getDRIVERID());
        Log.e("calender date ",dateFormat.format(calendar.getTime()));
//        Log.e("date ",dateFormat.format(date));
        return dateFormat.format(calendar.getTime());
    }
    public static  long getUtcTime(long time) {
        System.out.println("Time="+time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date dbefore=new Date(time);
        System.out.println("Date before conversion="+format.format(dbefore));
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        TimeZone timezone = c.getTimeZone();
        int offset = timezone.getRawOffset();
        if(timezone.inDaylightTime(new Date())){
            offset = offset + timezone.getDSTSavings();
        }
        int offsetHrs = offset / 1000 / 60 / 60;
        int offsetMins = offset / 1000 / 60 % 60;

        System.out.println("offset: " + offsetHrs);
        System.out.println("offset: " + offsetMins);

        c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
        c.add(Calendar.MINUTE, (-offsetMins));

        System.out.println("Date after conversion: "+format.format(c.getTime()));
        System.out.println("Time converted="+c.getTime().getTime());
        return c.getTime().getTime();


    }
    public String get15DaysAfterDate(){
        Calendar calendar = Calendar.getInstance();

        Locale loc = new Locale("en", "US");

        long time = System.currentTimeMillis();
        calendar.setTimeInMillis(time);

        //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, loc);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM/dd/yyyy", loc);
        Date date = new Date();
//        Crashlytics.log(Log.DEBUG, "preprair Arrive Json data", "trip Id -"+model1.getDRIVERID());
        Log.e("calender date ",dateFormat.format(calendar.getTime()));
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date newDate = calendar.getTime();
//        Log.e("date ",dateFormat.format(date));
        return dateFormat.format(newDate);
    }
    public  String getUtcToCurrentTime(String time) {
        try {
            if(time!=null && !time.equalsIgnoreCase("")) {
//            SimpleDateFormat dateFormatter,dateFormatter1;
//            //create a new Date object using the UTC timezone
//            Log.e("", "Input utc date:" + time);
////                dateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
//
//            dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
//            Date utcDate = null;
//            try {
//                utcDate = dateFormatter.parse(time);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            //Convert the UTC date to Local timezone
//            dateFormatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
//            dateFormatter1.setTimeZone(TimeZone.getDefault());
//            String formattedDate = dateFormatter1.format(utcDate);
                return time;
            }else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
    }
    public  String getUtcToCurrentDateOnly(String time) {
        try {
            if(time!=null && !time.equalsIgnoreCase("")) {
                SimpleDateFormat dateFormatter,dateFormatter1;
                //create a new Date object using the UTC timezone
                Log.e("", "Input utc date:" + time);

//                dateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                dateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa",Locale.US);
//                dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date utcDate = null;
                try {
                    utcDate = dateFormatter.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Convert the UTC date to Local timezone
                dateFormatter1 = new SimpleDateFormat("MM/dd/yyyy ", Locale.US);
//                dateFormatter1.setTimeZone(TimeZone.getDefault());
                String formattedDate = dateFormatter1.format(utcDate);
                return formattedDate;
            }else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
    }

    public  String getUtcToOrderdateTime(String stringDate) {


        Calendar calendar = Calendar.getInstance();
        long time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa",Locale.US);
        try {
            Date mDate = sdf.parse(stringDate);
            time = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        long time = System.currentTimeMillis();

        calendar.setTimeInMillis(time);

        //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, loc);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM/dd/yyyy hh:mm aa", Locale.US);

//        Crashlytics.log(Log.DEBUG, "preprair Arrive Json data", "trip Id -"+model1.getDRIVERID());
        Log.e("calender date ",dateFormat.format(calendar.getTime()));

//        Log.e("date ",dateFormat.format(date));
        return dateFormat.format(calendar.getTime());
    }
    public void sendAlert(final Activity activity, String message, String title) {

        PreferenceManager prf = new PreferenceManager(activity);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ForAddModel> call1 = apiInterface.saveAlerts("0","0","","App","Web","CustomerAPP","NotificationBoard","Customer",""+prf.getStringData("userCode"),""+prf.getStringData("userName"),"General",""+title,"","All","","","User",""+message,"Mobile",""+ prf.getStringData("userName"),new Util().getDateTime(),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");

        call1.enqueue(new Callback<ForAddModel>() {
            @Override
            public void onResponse(Call<ForAddModel> call, Response<ForAddModel> response) {


                Log.e("track message ",response.message());
//                hideAnimation();
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ForAddModel> call, Throwable t) {

                call.cancel();

            }
        });
    }
    public void myIntent(Activity activity,Class class1){
        Intent j = new Intent(activity, class1);
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        activity.startActivity(j);
        activity. overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        activity.finish();
    }

    public Boolean isValidPostalCode(String postal, String countryCode) {
        String expression;
        if (countryCode.equals("IN")) {
            expression = "^[1-9][0-9]{5}$";
        } else if (countryCode.equals("US") || countryCode.equals("MX")) {
            expression = "^[0-9]{5}(?:-[0-9]{4})?$";
        } else if (countryCode.equals("GB")) {
            expression = "^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$";
        } else if (countryCode.equals("AU")) {
            expression = "^(?:(?:[2-8]\\d|9[0-7]|0?[28]|0?9(?=09))(?:\\d{2}))$";
        } else {
            expression = "^(\\d{5}(-\\d{6})?|[A-CEGHJ-NPRSTVXY]\\d[A-CEGHJ-NPRSTV-Z] ?\\d[A-CEGHJ-NPRSTV-Z]\\d)$";
        }

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);


        return pattern.matcher(postal).matches();


    }
    public final boolean isValidName(String text1) {

        String expression = "^[A-Za-z0-9'\\/_\\-()\\s]{0,100}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(text1);
        return matcher.matches();
    }
    public final boolean isValidAdde(String text1) {

        String expression = "^[a-zA-Z0-9'\\/_\\-,.#&()\\s]{0,250}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(text1);
        return matcher.matches();
    }

    public final boolean isValidCity(String text1) {

        String expression = "[a-zA-Z ]+";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(text1);
        return matcher.matches();
    }

    public final boolean isValidVin(String text1) {

        String expression = "^[a-zA-Z0-9]*$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(text1);
        return matcher.matches();
    }
    public void sendSMTPMail(Activity activity,Throwable messageTxt,String code,Exception e,String url){

        MDToast mdToast = MDToast.makeText(activity, "" + code, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        mdToast.show();
        if(BuildConfig.DEBUG){
            return;
        }
        StringBuilder error = new StringBuilder();
        if(e!=null) {
            StackTraceElement[] exc = e.getStackTrace();

            for (int i = 0; exc.length > i; i++) {
                error.append(exc[i]);
                error.append("\n");
            }
        }else{
            StackTraceElement[] exc =messageTxt.getStackTrace();

            for (int i = 0; exc.length > i; i++) {
                error.append(exc[i]);
                error.append("\n");
            }
        }
//
        String from = activity.getString(R.string.recipient);

        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtps.host", "smtp.1and1.com");
//        properties.setProperty("mail.smtps.port", "587");
        properties.setProperty("mail.smtps.auth", "true");
        properties.setProperty("mail.user", activity.getString(R.string.recipient));
        properties.setProperty("mail.password", activity.getString(R.string.paswrd));
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.setProperty("mail.smtp.socketFactory.port", activity.getString(R.string.port));
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        //properties.put("mail.debug", "true");
          PreferenceManager prf=new PreferenceManager(activity);
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try{
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(activity.getString(R.string.recipient)));
            message.addRecipient(Message.RecipientType.CC,
                    new InternetAddress(activity.getString(R.string.cc)));

            message.setSubject("AFM Cx App Exception");



            String meesg=("<table style=\"padding: 0px; background-color: #FFFFFF; width: 640px; margin-left: auto; margin-right: auto; border-collapse: collapse;\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "            <tbody style=\"vertical-align: top;\">\n" +
                    "                <tr>\n" +
                    "                    <td style=\"border: 0px; background: #FFFFFF; font-size: 12px; width: 600px; padding: 40px 20px 40px;text-align: left;\">\n" +
                    "                        <table style=\"padding: 0px; width: 600px; font-size: 12px; font-family: sans-serif; white-space: normal; text-align: left; border-color: #9E9E9E; border-collapse: collapse; \" cellpadding=\"0\" cellspacing=\"0\" border=\"1\">\n" +
                    "                            <tbody>\n" +
                    "                                <tr>\n" +
                    "                                    <th style=\"padding: 5px 8px;text-align: left;\">UserName</th>\n" +
                    "                                    <td style=\"padding: 5px 8px; word-break: break-word;\">"+prf.getStringData("userName")+"</td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                    <th style=\"padding: 5px 8px; vertical-align: top;text-align: left;\">API</th>\n" +
                    "                                    <td style=\"padding: 5px 8px; word-break: break-word;\">\n" +url+
                    "                                    </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                    <th style=\"padding: 5px 8px;vertical-align: top;text-align: left;\">CorporateID</th>\n" +
                    "                                    <td style=\"padding: 5px 8px; word-break: break-word;\">\n" + prf.getStringData("corporateId")+
                    "                                    </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                    <th style=\"padding: 5px 8px;vertical-align: top;text-align: left;\">Activity</th>\n" +
                    "                                    <td style=\"padding: 5px 8px; word-break: break-word;\">\n" + activity+
                    "                                    </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                    <th style=\"padding: 5px 8px;vertical-align: top;text-align: left;\">Error</th>\n" +
                    "                                    <td style=\"padding: 5px 8px; word-break: break-word;\">\n" + error+
                    "                                    </td>\n" +
                    "                                </tr>\n" +
                    "                            </tbody>\n" +
                    "                        </table>\n" +
                    "                    </td>\n" +
                    "                </tr>\n" +
                    "            </tbody>\n" +
                    "        </table>");

//            message.setText("UserName: "+prf.getStringData("userName")+"("+prf.getStringData("corporateId")+")\n"+url+"\n"+activity+"\n"+error);
//            message.setText(String.valueOf(Html.fromHtml(meesg)));
            message.setContent(meesg, "text/html");
            if (android.os.Build.VERSION.SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            Transport trnsport;
            trnsport = session.getTransport("smtps");
            trnsport.connect(null, properties.getProperty("mail.password"));
            message.saveChanges();
            trnsport.sendMessage(message, message.getAllRecipients());
            trnsport.close();
            Log.e("mail send", "Done");
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }


    }
}
