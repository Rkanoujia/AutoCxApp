package com.avaal.com.afm2020autoCx;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.models.ForgetPasswordModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import androidx.appcompat.app.AppCompatActivity;
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
 * Created by dell pc on 16-04-2018.
 */

public class ForgetPassword extends AppCompatActivity {
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    APIInterface apiInterface;

    @BindView(R.id.user_name1)
    EditText userName;
//    @BindView(R.id.paswrd)
//    EditText password;
    @BindView(R.id.corporate_id1)
    EditText corporateId;
    @BindView(R.id.home_)
    TextView home_;
    @BindView(R.id.title)
    TextView title;
    PreferenceManager prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        prf = new PreferenceManager(ForgetPassword.this);
        home_.setVisibility(View.GONE);
        title.setText("Forgot Password");
       try {
           if(prf.getStringData("corporateId")!=null)
           corporateId.setText(prf.getStringData("corporateId"));
           if(prf.getStringData("userName")!=null)
           userName.setText(prf.getStringData("userName"));
       }catch (Exception e){
           e.printStackTrace();
           new Util().sendSMTPMail(ForgetPassword.this,null,"CxE004",e,"");
       }

        ImageView bg_image = (ImageView) findViewById(R.id.bg_Img);

//        bg_image.setAlpha(0.6f);
        bg_image.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.login_bg, 500, 1000));
        ImageView logo_image = (ImageView) findViewById(R.id.logo_img);

//        bg_image.setAlpha(0.6f);
        logo_image.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.login_logo, 277, 119));

//        if(prf.getStringData("userName")!=null) {
//            userName.setText(prf.getStringData("userName"));
////        password.setText("Avaal@123");
//            corporateId.setText(prf.getStringData("corporateId"));
//        }
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

    }
    @OnClick(R.id.home_)
    void home(){
        new Util().myIntent(this,NewDashBoardActivity.class);
    }
    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();
    }
    @OnClick(R.id.back)
    void back(){
        Intent j = new Intent(this, LoginActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        j.putExtra("open","home");
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onBackPressed();
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
//
    @OnClick(R.id.forget)
    void forget(){

        if(userName.getText().toString().equalsIgnoreCase("")){
            MDToast mdToast = MDToast.makeText(ForgetPassword.this, "Enter Username", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        if(corporateId.getText().toString().equalsIgnoreCase("")){
            MDToast mdToast = MDToast.makeText(ForgetPassword.this, "Enter Corporate Id", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        if(!new Util().isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(ForgetPassword.this, "Check Your Internet connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        showAnimation();
        ForgetPasswordModel vindetail=new ForgetPasswordModel(userName.getText().toString(),corporateId.getText().toString());
//        Call<SaveImageModel> call1 = apiInterface.updateProfile(vehicleIds, fileNames,authKey1,content,docTypess,exten,size1,remarks, body);
        Call<ForgetPasswordModel> call1 = apiInterface.forgetPswrd(""+userName.getText(),"PRI",""+corporateId.getText(),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(Call<ForgetPasswordModel> call, Response<ForgetPasswordModel> response) {
                final ForgetPasswordModel getdata = response.body();
                hideAnimation();
                try {
                if(getdata.status) {
                    MDToast mdToast = MDToast.makeText(ForgetPassword.this, getdata.message, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                    Intent j = new Intent(ForgetPassword.this, LoginActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        j.putExtra("open","home");
                    startActivity(j);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
//                        notification.setChecked(getdata.data.isByApp);
//                        message.setChecked(getdata.data.isByText);
//                        email.setChecked(getdata.data.isByMail);



                }else{
                    MDToast mdToast = MDToast.makeText(ForgetPassword.this, getdata.message, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                    mdToast.show();
                }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(ForgetPassword.this,null,"CxE004",e,"");
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(ForgetPassword.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.forgot_scre);
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
}