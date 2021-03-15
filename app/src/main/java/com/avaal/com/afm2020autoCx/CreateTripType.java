package com.avaal.com.afm2020autoCx;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.PreferenceManager;
import extra.Util;

/**
 * Created by dell pc on 26-02-2018.
 */

public class CreateTripType extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.home_)
    TextView home_;

    @BindView(R.id.fram_cn_to_us)
    FrameLayout fram_cn_to_us;
    @BindView(R.id.fram_us_to_cn)
    FrameLayout fram_us_to_cn;
    @BindView(R.id.fram_us_to_us)
    FrameLayout fram_us_to_us;
    PreferenceManager prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_trip_type);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        title.setText("Create Load");
         prf = new PreferenceManager(this);
//        this.setTitle("Create A Trip ");
//
////Remove notification bar
        home_.setVisibility(View.VISIBLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(prf.getBoolData("logout")==null){
            Intent j = new Intent(CreateTripType.this, LoginActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        j.putExtra("open","home");
            startActivity(j);
            finish();
        }
        if(!prf.getBoolData("logout")){
            Intent j = new Intent(CreateTripType.this, LoginActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        j.putExtra("open","home");
            startActivity(j);
            finish();
        }
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
    @OnClick(R.id.home_)
    void home(){
        new Util().myIntent(this,NewDashBoardActivity.class);
    }
    @OnClick(R.id.cn_to_cn)
    void onCreateTrip(){

                Intent j = new Intent(CreateTripType.this, RouteSelectionActivity.class);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
        prf.saveStringData("When", "add");
        prf.saveStringData("OrderStatus","Saved");
                j.putExtra("from", "CA");
                j.putExtra("to", "CA");
                j.putExtra("Id", "0");
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(j);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                finish();
    }
    @OnClick(R.id.cn_to_us)
    void onCreateTrip1(){

//        TranslateAnimation slide = new TranslateAnimation(0, 0, 0,-300 );
//        slide.setDuration(1000);
//        slide.setFillAfter(true);
//        fram_cn_to_us.startAnimation(slide);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                // Do something after 5s = 5000ms
                Intent j = new Intent(CreateTripType.this, RouteSelectionActivity.class);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
        prf.saveStringData("When", "add");
        prf.saveStringData("OrderStatus","Saved");
                j.putExtra("from", "CA");
                j.putExtra("to", "US");
        j.putExtra("Id", "0");
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(j);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

//                finish();
//            }
//        }, 500);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fram_cn_to_us.clearAnimation();
//            }
//        },550);


    }
    @OnClick(R.id.us_to_cn)
    void onCreateTrip2(){
//        TranslateAnimation slide = new TranslateAnimation(0, 0, 0,-600 );
//        slide.setDuration(1000);
//        slide.setFillAfter(true);
//        fram_us_to_cn.startAnimation(slide);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
                Intent j = new Intent(CreateTripType.this, RouteSelectionActivity.class);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
        prf.saveStringData("When", "add");
                  prf.saveStringData("OrderStatus","Saved");
                j.putExtra("from", "US");
                j.putExtra("to", "CA");
                j.putExtra("Id", "0");
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(j);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

//                finish();
//            }
//        }, 500);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fram_us_to_cn.clearAnimation();
//            }
//        },550);


    }
    @OnClick(R.id.us_to_us)
    void onCreateTrip3(){
//        TranslateAnimation slide = new TranslateAnimation(0, 0, 0,-900 );
//        slide.setDuration(1000);
//        slide.setFillAfter(true);
//        fram_us_to_us.startAnimation(slide);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                // Do something after 5s = 5000ms
                Intent j = new Intent(CreateTripType.this, RouteSelectionActivity.class);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
        prf.saveStringData("When", "add");
        prf.saveStringData("OrderStatus","Saved");
                j.putExtra("from", "US");
                j.putExtra("to", "US");
                j.putExtra("Id", "0");
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(j);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

//                finish();
//            }
//        }, 500);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fram_us_to_us.clearAnimation();
//            }
//        },550);

    }

}
