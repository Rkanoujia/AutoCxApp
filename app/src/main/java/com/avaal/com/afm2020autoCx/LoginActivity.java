package com.avaal.com.afm2020autoCx;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.avaal.com.afm2020autoCx.models.LoginModel;
import com.avaal.com.afm2020autoCx.models.ProfileDataModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.LoaderScreen;
import extra.MyImage;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dell pc on 15-01-2018.
 */

public class LoginActivity extends AppCompatActivity implements FingerprintHelper.FingerprintHelperListener {
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    APIInterface apiInterface;

    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.paswrd)
    EditText password;
    @BindView(R.id.corporate_id)
    EditText corporateId;
    @BindView(R.id.remember)
    Switch remember;
    PreferenceManager prf;
    private static int CODE_AUTHENTICATION_VERIFICATION = 242;
    String authkey1,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        ImageView bg_image = (ImageView) findViewById(R.id.bg_Img);

//        bg_image.setAlpha(0.6f);
        bg_image.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.login_bg, 500, 1000));
        ImageView logo_image = (ImageView) findViewById(R.id.logo_img);

//        bg_image.setAlpha(0.6f);
        logo_image.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.login_logo, 277, 119));

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
         prf = new PreferenceManager(LoginActivity.this);
         try {
//             if(getIntent().getBooleanExtra("logoutAnother",true)!=null)
             if (getIntent().getBooleanExtra("logoutAnother",false)) {
                 prf.saveBoolData("logout",false);
             }
         }catch (Exception e){
             e.printStackTrace();
             new Util().sendSMTPMail(LoginActivity.this,null,"CxE004",e,"");
         }
        FirebaseApp.initializeApp(this);
        token = FirebaseInstanceId.getInstance().getToken();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            marshmallow.checkLocationPermission();
//               marshmallow.checkPermissionForExternalStorage();
//                    marshmallow.checkPermissionForCall();
//            marshmallow.checkPermissionForCamera();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.CAMERA}, 1);
            }else{
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.CAMERA}, 1);
            }

//            marshmallow.checkPermissionForExternalStorage();
        }
try {
    if (prf.getStringData("userName") != null) {
        userName.setText(prf.getStringData("userName"));
        corporateId.setText(prf.getStringData("corporateId").toUpperCase());

        if (prf.getBoolData("remember")) {
            remember.setChecked(true);
            password.setText(prf.getStringData("password"));
            if (prf.getBoolData("logout")) {
                try {
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                    new Util().sendSMTPMail(LoginActivity.this,null,"CxE004",e,"");
                }
            }
        }
    }
}catch (Exception e){
             e.printStackTrace();
    new Util().sendSMTPMail(LoginActivity.this,null,"CxE004",e,"");
}

        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    userName.setSelection(userName.getText().length());
            }
        });
        corporateId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    corporateId.setSelection(corporateId.getText().length());
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    password.setSelection(password.getText().length());
            }
        });

//        editTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                if (hasFocus)
//                    scrollView.scrollBy(0, 150);
//            }
//            });

        /**
         Create new user
         **/
        /**
         POST name and job Url encoded.
         **/

        Log.e("TAG", "SERIAL: " + Build.SERIAL);
        Log.e("TAG","MODEL: " + Build.MODEL);
        Log.e("TAG","ID: " + Build.ID);
        Log.e("TAG","Manufacture: " + Build.MANUFACTURER);
        Log.e("TAG","brand: " + Build.BRAND);
        Log.e("TAG","type: " + Build.TYPE);
        Log.e("TAG","user: " + Build.USER);
        Log.e("TAG","BASE: " + Build.VERSION_CODES.BASE);
        Log.e("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.e("TAG","SDK  " + Build.VERSION.SDK);
        Log.e("TAG","BOARD: " + Build.BOARD);
        Log.e("TAG","BRAND " + Build.BRAND);
        Log.e("TAG","HOST " + Build.HOST);
        Log.e("TAG","FINGERPRINT: "+Build.FINGERPRINT);
        Log.e("TAG","Version Code: " + Build.VERSION.RELEASE);



}
@OnClick(R.id.driver_app)
void driverApp(){
    final String appPackageName = "com.avaal.com.afmdriver"; // getPackageName() from Context or Activity object
    try {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    } catch (android.content.ActivityNotFoundException anfe) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
    }
}
  @OnClick(R.id.emanifest_app)
    void emanifestApp(){
        final String appPackageName = "com.avaal.com.aceacilookup"; // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        //Check for the fingerprint permission and listen for fingerprint
        //add additional checks along with this condition based on your logic
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            if (checkFingerPrintSettings(this)) {
//                //Fingerprint is available, update the UI to ask user for Fingerprint auth
//                //start listening for Fingerprint
//                fingerprintHelper = new FingerprintHelper(this);
//                fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
//                fingerprintHelper.startAuth(fingerprintManager, null);
//            } else {
//                Log.d("Authe", "Finger print disabled due to No Login credentials or no Fingerprint");
//            }
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPause() {
        super.onPause();

    }


@OnClick(R.id.login)
    void login(){

    if(corporateId.getText()==null ||corporateId.getText().length()==0){
        MDToast mdToast = MDToast.makeText(LoginActivity.this, "Enter Corporate Id", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
        mdToast.show();
        return;
    }
    if(userName.getText()==null ||userName.getText().length()==0){
        MDToast mdToast = MDToast.makeText(LoginActivity.this, "Enter Username", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
        mdToast.show();
        return;
    }
    if(password.getText()==null ||password.getText().length()==0){
        MDToast mdToast = MDToast.makeText(LoginActivity.this, "Enter Password", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
        mdToast.show();
        return;
    }
    Util util=new Util();
    if(!util.isNetworkAvailable(this)) {
        MDToast mdToast = MDToast.makeText(LoginActivity.this, "Check Your Internet connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
        mdToast.show();
        return;
    }

   showAnimation();

        Call<LoginModel> call1 = apiInterface.userLogin(corporateId.getText().toString().trim()+"~"+userName.getText().toString()+"~regular~"+FirebaseInstanceId.getInstance().getToken()+"~PRI~Android~"+Build.BRAND+" "+Build.MODEL+"~"+Build.VERSION.RELEASE,password.getText().toString(),"password","application/json");
        call1.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                 String  ggg=null;
                try {
                    if (response.message().equalsIgnoreCase("ok")) {
//                hideAnimation();
                        LoginModel login = response.body();

                        if (login.error == null) {
//                            MDToast mdToast = MDToast.makeText(LoginActivity.this, "Login Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                            mdToast.show();


                            prf = new PreferenceManager(LoginActivity.this);
                            prf.saveStringData("accessToken", login.access_token);

                            prf.saveStringData("userName", userName.getText().toString());
                            prf.saveStringData("corporateId", corporateId.getText().toString().trim());
                            prf.saveBoolData("logout", true);

                            if (remember.isChecked()) {
                                prf.saveStringData("password", password.getText().toString());
                                prf.saveBoolData("remember", true);
                            } else
                                prf.saveBoolData("remember", false);

                            getProfile();

//                        Intent j = new Intent(LoginActivity.this, DashBoardBottomMenu.class);
//                        j.putExtra("open", "home");
//                        j.putExtra("AuthKey", login.datavalue.authkey);
//                        startActivity(j);
//                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                        finish();
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // do stuff
//                                    onIsfingerprint();
//                                }
//                            }, 500);



                        } else {
                            if (login.error_description.contains("Company Does Not Exists")) {
                                MDToast mdToast = MDToast.makeText(LoginActivity.this, "" + login.error_description, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                                mdToast.show();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    corporateId.setBackgroundTintList(getColorStateList(R.color.red));
                                }
                            } else if (login.error_description.equalsIgnoreCase("Username does not Exists")) {
                                MDToast mdToast = MDToast.makeText(LoginActivity.this, "Reference no. doesn't exist#", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                                mdToast.show();
                            } else if (login.error_description.contains("Password")) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    password.setBackgroundTintList(getColorStateList(R.color.red));
                                }
                                MDToast mdToast = MDToast.makeText(LoginActivity.this, "" + login.error_description, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                                mdToast.show();
                            } else {
                                MDToast mdToast = MDToast.makeText(LoginActivity.this, "" + login.error_description, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                                mdToast.show();
                            }
                        }
                    }else {
                        hideAnimation();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if(jObjError.getString("error_description").contains("Company Does Not Exists")){
//                            corporateId.setBackgroundTintList(getColorStateList(R.color.red));
                        }
                        if(jObjError.getString("error_description").contains("Username does not Exists")){
//                            userId.setBackgroundTintList(getColorStateList(R.color.red));
                        }
                        if(jObjError.getString("error_description").contains("Password")){
//                            password.setBackgroundTintList(getColorStateList(R.color.red));
                        }

                        MDToast mdToast = MDToast.makeText(LoginActivity.this, "" + jObjError.get("error_description"), MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                        mdToast.show();
                    }
                }catch (Exception e){
                    hideAnimation();
                   e.printStackTrace();
                    new Util().sendSMTPMail(LoginActivity.this,null,"CxE004",e,""+call.request().url());
               }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(LoginActivity.this,t,"CxE001",null,""+call.request().url().toString());

                hideAnimation();
            }
        });

}
@OnClick(R.id.forget)
void forget(){
    Intent j = new Intent(LoginActivity.this, ForgetPassword.class);

//    j.putExtra("open", "home");
//    j.putExtra("AuthKey", login.datavalue.authkey);
    startActivity(j);
    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
}

 void onIsfingerprint(){


     final KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);


//         if (km.isKeyguardSecure()) {
//                 new AlertDialog.Builder(this)
//                         .setMessage("Are you want to Login with Phone Authentication ?")
//
//                         // Specifying a listener allows you to take an action before dismissing the dialog.
//                         // The dialog is automatically dismissed when a dialog button is clicked.
//                         .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                             public void onClick(DialogInterface dialog, int which) {
//                                 // Continue with delete operation
//                                 Intent i = null;
//                                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                                     i = km.createConfirmDeviceCredentialIntent("Lock "+getResources().getString(R.string.app_name), "confirm your Screen lock pattern, PIN or password");
//                                 }
//                                 startActivityForResult(i, CODE_AUTHENTICATION_VERIFICATION);
//                             }
//                         })
//
//                         // A null listener allows the button to dismiss the dialog and take no further action.
//                         .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                             @Override
//                             public void onClick(DialogInterface dialog, int which) {
//
//                                 prf.saveBoolData("IsFingerprint", false);
//                                 new Util().myIntent(LoginActivity.this, NewDashBoardActivity.class);
//
//                             }
//                         })
//                         .show();
//             } else {
//                 prf.saveBoolData("IsFingerprint", false);
//                 new Util().myIntent(LoginActivity.this, NewDashBoardActivity.class);
////                 Intent j = new Intent(LoginActivity.this, NewDashBoardActivity.class);
////                 j.putExtra("open", "home");
////             j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                 startActivity(j);
////                 overridePendingTransition(R.anim.fadein, R.anim.fadeout);
////                 finish();
//             }


 }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==CODE_AUTHENTICATION_VERIFICATION)
        {
            prf.saveBoolData("IsFingerprint", true);
            Intent j = new Intent(LoginActivity.this, NewDashBoardActivity.class);
            j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(j);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            finish();

        }
        else
        {
            Toast.makeText(this, "Failure: Unable to verify user's identity", Toast.LENGTH_SHORT).show();
        }
    }
    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.login_scre);
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
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        Log.e("fghgh", "value 1");
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void authenticationFailed(String error) {
        Toast.makeText(this, "Invalid Fingeprint", Toast.LENGTH_LONG).show();
    }

    @Override
    public void authenticationSuccess(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(this, "valid Fingeprint", Toast.LENGTH_LONG).show();
    }
    void getProfile(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        final PreferenceManager prf =new PreferenceManager(this);
        Call<ProfileDataModel> call1 = apiInterface.getProfile(""+ prf.getStringData("corporateId"),""+ prf.getStringData("userName"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<ProfileDataModel>() {
            @Override
            public void onResponse(Call<ProfileDataModel> call, Response<ProfileDataModel> response) {


                try {
                    ProfileDataModel getdata = response.body();
                    if (getdata.status) {
                        hideAnimation();
                        prf.saveStringData("CompnyCode",getdata.data.CompanyCode);
                        prf.saveStringData("userCode", getdata.data.PrimaryInfoCode);
                        prf.saveStringData("Name", getdata.data.Name);

                        prf.saveBoolData("IsFingerprint", false);
                        new Util().myIntent(LoginActivity.this, NewDashBoardActivity.class);

//                    Picasso.with(this).load("https://images.unsplash.com/photo-450037586774-00cb81edd142?auto=format&fit=crop&w=750&q=80").error(R.drawable.R.drawable.noprofile).into(front_right);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-504196606672-aef5c9cefc92?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_left);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-500395235658-f87dff62cbf3?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_right);


                    }
                }catch (Exception e){
                    hideAnimation();
                    e.printStackTrace();
                    new Util().sendSMTPMail(LoginActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<ProfileDataModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(LoginActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });

    }
}