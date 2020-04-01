package com.avaal.com.afm2020autoCx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.models.ChangePasswordModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 17-04-2018.
 */

public class ChangePassword extends AppCompatActivity {
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    APIInterface apiInterface;

        @BindView(R.id.paswrd_old)
    EditText paswrd_old;
    @BindView(R.id.paswrd_new)
    EditText paswrd_new;
    @BindView(R.id.paswrd_confirm)
    EditText paswrd_confirm;
    @BindView(R.id.home_)
    TextView home_;
    @BindView(R.id.title)
    TextView title;
    PreferenceManager prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        prf = new PreferenceManager(this);
//        home_.setVisibility(View.GONE);
        title.setText("Change Password");
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
        Intent j = new Intent(this, NewDashBoardActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        startActivity(j);
        finish();
    }
    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();
    }
    @OnClick(R.id.back)
    void back(){
//        Intent j = new Intent(this, LoginActivity.class);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
////        j.putExtra("open","home");
//        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onBackPressed();
    }


        @OnClick(R.id.login)
    void cahngePswrd(){

        if(!paswrd_old.getText().toString().equalsIgnoreCase(prf.getStringData("password"))){
            MDToast mdToast = MDToast.makeText(ChangePassword.this, "Current Password Not Match", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
            if(paswrd_new.getText().toString().trim().equalsIgnoreCase("")){
                MDToast mdToast = MDToast.makeText(ChangePassword.this, "Please Enter New Password", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                mdToast.show();
                return;
            }
        if(!paswrd_new.getText().toString().equalsIgnoreCase(paswrd_confirm.getText().toString()))
        {
            MDToast mdToast = MDToast.makeText(ChangePassword.this, "Confirm Password Not Match", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(prf.getStringData("password").equalsIgnoreCase(paswrd_confirm.getText().toString())){
            MDToast mdToast = MDToast.makeText(ChangePassword.this, "Password should not match with last password.", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
            mdToast.show();
            return;
        }

showAnimation();
//        Call<SaveImageModel> call1 = apiInterface.updateProfile(vehicleIds, fileNames,authKey1,content,docTypess,exten,size1,remarks, body);
        Call<ChangePasswordModel> call1 = apiInterface.changePassword(""+prf.getStringData("userName"),""+prf.getStringData("password"),""+paswrd_confirm.getText(),"PRI",""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                final ChangePasswordModel getdata = response.body();
                hideAnimation();
                try {
                if(getdata.status) {
                    MDToast mdToast = MDToast.makeText(ChangePassword.this, "Update Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();

//                        notification.setChecked(getdata.data.isByApp);
//                        message.setChecked(getdata.data.isByText);
//                        email.setChecked(getdata.data.isByMail);
                    prf.saveBoolData("remember",false);
                    prf.saveStringData("password","");
                    Intent j = new Intent(ChangePassword.this, LoginActivity.class);
                    j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
                    startActivity(j);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finishAffinity();



                }else{
                    MDToast mdToast = MDToast.makeText(ChangePassword.this, "Something went wrong", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                    mdToast.show();
                }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(ChangePassword.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.change_scre);
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