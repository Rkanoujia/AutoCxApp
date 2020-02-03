package com.avaal.com.afm2020autoCx.fingerprint;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.MainActivity;
import com.avaal.com.afm2020autoCx.R;


/**
 * Created by whit3hawks on 11/16/16.
 */

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        ((Activity) context).finish();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void update(String e){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
    }
//    void login(){
//        Util util=new Util();
//        if(!util.isNetworkAvailable(context)) {
//            MDToast mdToast = MDToast.makeText(context, "Check Your Internet", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
//            mdToast.show();
//            return;
//        }
//
//        PreferenceManager prf=new PreferenceManager(context);
//        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
//        LoginModel user = new LoginModel(prf.getStringData("userName"), prf.getStringData("password"), prf.getStringData("corporateId"));
//        Call<LoginModel> call1 = apiInterface.userLogin(user);
//        call1.enqueue(new Callback<LoginModel>() {
//            @Override
//            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
//
//                LoginModel login = response.body();
//
//                try {
//                    if (login.Status) {
////                        MDToast mdToast = MDToast.makeText(SplashActivity.this, "Login Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
////                        mdToast.show();
//
////                        prf = new PreferenceManager(SplashActivity.this);
////                        prf.saveStringData("authKey", login.datavalue.authkey);
////                        prf.saveStringData("carrierPrimaryId", String.valueOf(login.datavalue.id));
////                        prf.saveStringData("userName", userName.getText().toString());
////                        prf.saveStringData("corporateId", corporateId.getText().toString());
////                        prf.saveBoolData("logout",true);
////
////                        if(remember.isChecked()) {
////                            prf.saveStringData("password", password.getText().toString());
////                            prf.saveBoolData("remember", true);
////                        }
////                        else
////                            prf.saveBoolData("remember", false);
//
//                        Intent j = new Intent(context, DashBoardBottomMenu.class);
//                        j.putExtra("open", "home");
//                        j.putExtra("AuthKey", login.datavalue.authkey);
//                        context.startActivity(j);
//                        ((Activity) context).finish();
//
//
//                    } else {
//                        Intent j = new Intent(context, LoginActivity.class);
//                        context.startActivity(j);
//                        ((Activity) context).finish();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(Call<LoginModel> call, Throwable t) {
//                call.cancel();
//            }
//        });
//
//    }
}
