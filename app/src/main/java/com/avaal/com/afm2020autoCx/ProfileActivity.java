package com.avaal.com.afm2020autoCx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.models.LogoutModel;
import com.avaal.com.afm2020autoCx.models.ProfileDataModel;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 17-03-2018.
 */

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cus_profile)
    ImageView custProfile;
    @BindView(R.id._name)
    TextView _name;
    @BindView(R.id._address)
    TextView _address;
    @BindView(R.id._city)
    TextView _city;
    @BindView(R.id._state)
    TextView _state;
    @BindView(R.id._phn)
    TextView _phn;
    @BindView(R.id._email)
    TextView _email;
    @BindView(R.id._country)
    TextView _country;
    @BindView(R.id._postal)
    TextView _postal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        title.setText("Profile");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        Picasso.with(ProfileActivity.this).load("https://images.unsplash.com/photo-450037586774-00cb81edd142?auto=format&fit=crop&w=750&q=80").error(R.drawable.noprofile).into(custProfile);
        getProfile();

    }
    @OnClick(R.id.home_)
    void home(){
        Intent j = new Intent(this, NewDashBoardActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        startActivity(j);
    }
    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();
    }
    @OnClick(R.id.back)
    void back(){
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onBackPressed();
    }
    @OnClick(R.id.signout)
    void signout(){
        new AlertDialog.Builder(this)
                            .setMessage("Are you sure you want to Logout ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    logout();

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
//                                    Intent j = new Intent(DashBoardBottomMenu.this, DashBoardBottomMenu.class);
//                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                                    j.putExtra("open","home");
//                                    startActivity(j);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
    }
    void getProfile(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        PreferenceManager prf = new PreferenceManager(this);
        Call<ProfileDataModel> call1 = apiInterface.getProfile(""+prf.getStringData("corporateId"),""+prf.getStringData("userName"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<ProfileDataModel>() {
            @Override
            public void onResponse(Call<ProfileDataModel> call, Response<ProfileDataModel> response) {

                ProfileDataModel getdata = response.body();
                try {
                    if (getdata.status) {
                        _name.setText(getdata.data.Name);
                        _address.setText(getdata.data.AddressOne);
                        _city.setText(getdata.data.City);
                        _state.setText(getdata.data.StateName);
                        _country.setText(getdata.data.CountryName);
                        _postal.setText(getdata.data.PostalCode);
                        _phn.setText(getdata.data.Phone);
                        _email.setText(getdata.data.Email);

//                    Picasso.with(this).load("https://images.unsplash.com/photo-450037586774-00cb81edd142?auto=format&fit=crop&w=750&q=80").error(R.drawable.R.drawable.noprofile).into(front_right);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-504196606672-aef5c9cefc92?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_left);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-500395235658-f87dff62cbf3?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_right);


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ProfileDataModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(ProfileActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    
    }
void logout(){
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    PreferenceManager prf = new PreferenceManager(this);
    LogoutModel vindetail=new LogoutModel(prf.getStringData("authKey"),prf.getStringData("carrierPrimaryId"));
    Call<LogoutModel> call1 = apiInterface.logOut(""+prf.getStringData("userName"),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
    call1.enqueue(new Callback<LogoutModel>() {
        @Override
        public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {

            LogoutModel getdata = response.body();
            try {
                if (getdata.status) {
                    MDToast mdToast = MDToast.makeText(ProfileActivity.this, "Logout Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                    PreferenceManager prf = new PreferenceManager(ProfileActivity.this);
//                prf.saveStringData("authKey", "");
                    prf.saveBoolData("logout", false);
//                prf.saveStringData("carrierPrimaryId", "");
//                prf.saveStringData("userName", "");
//                prf.saveStringData("corporateId", "");
//                prf.saveStringData("password", "");
//                prf.saveBoolData("remember", false);

                    Intent j = new Intent(ProfileActivity.this, LoginActivity.class);
                    j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
                    startActivity(j);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finishAffinity();

//                    Picasso.with(this).load("https://images.unsplash.com/photo-450037586774-00cb81edd142?auto=format&fit=crop&w=750&q=80").error(R.drawable.R.drawable.noprofile).into(front_right);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-504196606672-aef5c9cefc92?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_left);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-500395235658-f87dff62cbf3?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_right);


                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(Call<LogoutModel> call, Throwable t) {
            call.cancel();
            new Util().sendSMTPMail(ProfileActivity.this,t,"CxE001",null,""+call.request().url().toString());
        }
    });
}
@OnClick(R.id.change_paswrd)
    void change(){
    Intent j = new Intent(ProfileActivity.this, ChangePassword.class);
    j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
    startActivity(j);
    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
}
}
