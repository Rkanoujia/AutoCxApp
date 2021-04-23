package com.avaal.com.afm2020autoCx;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.models.CompanyProfileModel;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.MarshMallowPermission;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 17-03-2018.
 */

public class CallABActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.comp_profile)
    ImageView comp_profile;
    @BindView(R.id.comp_name)
    TextView comp_name;
    @BindView(R.id.comp_address)
    TextView comp_address;
    @BindView(R.id.comp_city)
    TextView comp_city;
    @BindView(R.id.comp_state)
    TextView comp_state;
    @BindView(R.id.comp_phn)
    TextView comp_phn;
    @BindView(R.id.comp_fax)
    TextView comp_fax;
    @BindView(R.id.comp_email)
    TextView comp_email;
    @BindView(R.id.comp_web)
    TextView comp_web;
    @BindView(R.id.comp_country)
    TextView comp_country;
    @BindView(R.id.comp_postal)
    TextView comp_postal;
String Phn_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_ab_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        title.setText("Company Profile");
    }

    @Override
    public void onBackPressed() {
        // Write your code here
        super.onBackPressed();
    }
    @OnClick(R.id.home_)
    void home(){
        Intent j = new Intent(this, NewDashBoardActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        startActivity(j);
    }
    @OnClick(R.id.back)
    void back() {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onBackPressed();
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
        Picasso.with(this).load("https://images.unsplash.com/photo-450037586774-00cb81edd142?auto=format&fit=crop&w=750&q=80").error(R.drawable.noprofile).into(comp_profile);
        getProfile();
    }

    void getProfile() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        PreferenceManager prf = new PreferenceManager(this);
        Call<CompanyProfileModel> call1 = apiInterface.getCompanyProfile(""+prf.getStringData("corporateId"),""+prf.getStringData("CompnyCode"),"bearer "+prf.getStringData("accessToken"),"application/json");

        call1.enqueue(new Callback<CompanyProfileModel>() {
            @Override
            public void onResponse(Call<CompanyProfileModel> call, Response<CompanyProfileModel> response) {


                try {
                     CompanyProfileModel getdata = response.body();
                    if (getdata.status) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("name",""+getdata.data.get(0).name);
                                comp_name.setText(getdata.data.get(0).name);
                                comp_address.setText(getdata.data.get(0).address);
                                comp_city.setText(getdata.data.get(0).city);
                                comp_state.setText(getdata.data.get(0).state);
                                comp_country.setText(getdata.data.get(0).country);
                                comp_postal.setText(getdata.data.get(0).postal);
                                comp_phn.setText(getdata.data.get(0).phone);
                                comp_fax.setText(getdata.data.get(0).fax);
                                comp_email.setText(getdata.data.get(0).email);
                                comp_web.setText(getdata.data.get(0).website);
                                Phn_call = getdata.data.get(0).phone;
                                String temp = getdata.data.get(0).logo.replace("delhiserver", "192.168.1.20");
//                    temp = temp.replaceAll(" ", "%20");
                                temp = temp.replaceAll(" ", "%20");


                                Log.e("", temp);


                                Picasso.with(CallABActivity.this).load(temp).error(R.drawable.noprofile).into(comp_profile);
                            }
                        });



//                    Picasso.with(CallABActivity.this).load(getdata.data.logo.replaceAll(" ", "%20")).error(R.drawable.ic_camera).into(comp_profile);
//                    Picasso.with(CallABActivity.this).load("http://192.168.1.20:7887/Images/CompanyLogo/AFM%20Suite%20Development133201834.png").error(R.drawable.ic_camera).into(comp_profile);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-500395235658-f87dff62cbf3?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_right);


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CompanyProfileModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(CallABActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });

    }
 @OnClick(R.id.call_compny)
    void callCmpny() {
     if(Phn_call.equalsIgnoreCase("") ||Phn_call.equalsIgnoreCase("null")){
         MDToast mdToast = MDToast.makeText(this, "No Phone Number", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
         mdToast.show();
         return;
     }
        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" +"+1"+ Phn_call));//change the number
             if(marshMallowPermission.checkPermissionForCall())
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

        if (marshMallowPermission.checkPermissionForCall()) {
            startActivity(callIntent);
       }
    }
}
