package extra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.NewDashBoardActivity;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.models.ForAddModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public String getDateTime(){
        Calendar calendar = Calendar.getInstance();

        long time = System.currentTimeMillis();
        calendar.setTimeInMillis(getUtcTime(time));

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
        calendar.setTimeInMillis(getUtcTime(time));

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
        calendar.setTimeInMillis(getUtcTime(time));

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
                SimpleDateFormat dateFormatter,dateFormatter1;
                //create a new Date object using the UTC timezone
                Log.e("", "Input utc date:" + time);

//                dateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US);
                dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date utcDate = null;
                try {
                    utcDate = dateFormatter.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Convert the UTC date to Local timezone
                dateFormatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
                dateFormatter1.setTimeZone(TimeZone.getDefault());
                String formattedDate = dateFormatter1.format(utcDate);
                return formattedDate;
            }else
                return "00/00/0000 00:00:00";
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

        calendar.setTimeInMillis(getUtcTime(time));

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
    public final boolean isValidVin(String text1) {

        String expression = "^[a-zA-Z0-9]*$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(text1);
        return matcher.matches();
    }

}
