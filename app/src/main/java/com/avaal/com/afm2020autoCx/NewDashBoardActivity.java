package com.avaal.com.afm2020autoCx;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.frahment.FullBottomSheetDialogFragment;
import com.avaal.com.afm2020autoCx.models.DashBoardModel;
import com.avaal.com.afm2020autoCx.tabtargetview.TapTarget;
import com.avaal.com.afm2020autoCx.tabtargetview.TapTargetView;

import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewDashBoardActivity extends AppCompatActivity {
    @BindView(R.id.account)
    CardView account;
    @BindView(R.id.status)
    CardView status;
    @BindView(R.id.account_li)
    LinearLayout account_li;
    @BindView(R.id.status_li)
    LinearLayout status_li;
    @BindView(R.id.account_txt)
    TextView account_txt;
    @BindView(R.id.status_txt)
    TextView status_txt;
    @BindView(R.id.name)
    TextView name;



    @BindView(R.id.total_ord)
    TextView total_ord;
    @BindView(R.id.trip_pending)
    TextView trip_pending;
    @BindView(R.id.total_enroute)
    TextView total_enroute;
    @BindView(R.id.Shipped_vehicle)
    TextView Shipped_vehicle;
    @BindView(R.id.total_recieve_vehicle)
    TextView total_recieve_vehicle;
    @BindView(R.id.outstanding_amt)
    TextView outstanding_amt;
    @BindView(R.id.invoice)
    TextView invoice;
    @BindView(R.id.paid)
    TextView paid;
    @BindView(R.id.out_standing)
            TextView out_standing;

    String message="";
    PreferenceManager prf;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_dashboard_activity);
        ButterKnife.bind(this);
        prf=new PreferenceManager(this);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            message= "Good Morning !";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            message= "Good Afternoon !";
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            message="Good Evening !";
        }
       /* else if(timeOfDay >= 21 && timeOfDay < 24){
            message= "Good Night !";
        }*/


        name.setText("Hi, "+message+"\n"+prf.getStringData("Name"));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                name.setText("Hi, "+message+"\n"+prf.getStringData("Name"));
            }
        }, 1000 );
        getDashboardData();
        if(prf.getStringData("mainMenuTutorial").equalsIgnoreCase(""))
        tutorial();


    }
    @OnClick(R.id.account)
    void  account(){
        account_txt.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        status_txt.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        account.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        status.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        account_li.setVisibility(View.VISIBLE);
        status_li.setVisibility(View.GONE);
    }
    @OnClick(R.id.status)
    void  status(){
        account_txt.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        status_txt.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        status.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        account.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        account_li.setVisibility(View.GONE);
        status_li.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.new_manu)
    void new_manu(){
        startActivity(new Intent(this,NewMenuActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
  @OnClick(R.id.pending_loads)
  void pending_loads(){
      Intent j = new Intent(this, NewOrderListActivity.class);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
      j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(j);
      overridePendingTransition(R.anim.fadein, R.anim.fadeout);
  }
    @OnClick(R.id.enroute_loads)
    void enroute_loads(){
        Intent j = new Intent(this, NewOrderListActivity.class);
        j.putExtra("AFMOrder", "ggd");
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    @OnClick(R.id.total_invoice)
    void total_invoice(){
        Intent j = new Intent(this, NewOrderListActivity.class);
        j.putExtra("AFMOrder", "ggd");
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    @OnClick(R.id.total_paid)
    void total_paid(){
        Intent j = new Intent(this, NewOrderListActivity.class);
        j.putExtra("AFMOrder", "ggd");
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    @OnClick(R.id.totl_order)
    void totalOrder(){
        Intent j = new Intent(this, NewOrderListActivity.class);
        j.putExtra("AFMOrder", "err");
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    void getDashboardData(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        PreferenceManager prf=new PreferenceManager(this);

        Call<DashBoardModel> call1 = apiInterface.getDashBoard(""+ prf.getStringData("userCode"),new Util().get15DaysAfterDate(),new Util().getDate(),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<DashBoardModel>() {
            @Override
            public void onResponse(Call<DashBoardModel> call, Response<DashBoardModel> response) {

                final DashBoardModel getdata = response.body();
                try {
                    if (getdata.status) {
// GetVehicleIdListModel tripDetails;
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                // change UI elements here

                        trip_pending.setText(""+getdata.data.saveLoad);
                        total_enroute.setText(""+getdata.data.TripsEnroute);
                        Shipped_vehicle.setText(""+getdata.data.TotalShippedVehicle);
                        total_recieve_vehicle.setText(""+getdata.data.TotalReceivedVehicle);
                        outstanding_amt.setText(""+(getdata.data.TotalOutstanding));
                        invoice.setText(""+getdata.data.invoiced);
                        paid.setText(""+getdata.data.paid);
                        if(getdata.data.Outstanding!=null || !getdata.data.Outstanding.equalsIgnoreCase("null"))
                        out_standing.setText(""+getdata.data.Outstanding);
                        else
                            out_standing.setText("$ 0");
//                        int ordr=getdata.data.saveLoad+getdata.data.TripsEnroute+getdata.data.DispatchedOrder;
//                        Log.e("order",""+ordr);
                        total_ord.setText(""+getdata.data.TotalOrder);
                            }
                        });
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DashBoardModel> call, Throwable t) {
                call.cancel();
            }
        });
    }
    void  tutorial(){
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.new_manu), "Menu", "Its have more Options")
                        // All options below are optional
                        .outerCircleColor(R.color.colorPrimary)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.colorWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.colorWhite)      // Specify the color of the title text
                        .descriptionTextSize(15)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.colorWhite)  // Specify the color of the description text
                        .textColor(R.color.colorWhite)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
//                        .icon(Drawable)                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional

                    }
                });
        prf.saveStringData("mainMenuTutorial","1");
    }

}
