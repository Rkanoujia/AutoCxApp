package com.avaal.com.afm2020autoCx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.DashboardOrderListAdapter;
import com.avaal.com.afm2020autoCx.adapter.OrderListAdapter;
import com.avaal.com.afm2020autoCx.models.OrderListModel;


import java.util.ArrayList;

import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.BottomSheetLayout;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 18-04-2018.
 */

public class EnrouteTripActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id._order_list)
    RecyclerView recyclerView;
    @BindView(R.id.home_)
    TextView home_;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.add_trip)
    FloatingActionButton add_trip;
    @BindView(R.id.bottom_sheet1)
    BottomSheetLayout mBottomSheetLayout;


    APIInterface apiInterface;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_loads);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        title.setText("Orders");
        recyclerView();
        Util util=new Util();
        if(util.isNetworkAvailable(this))
            getSaveLoads();

        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.GRAY);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Util util=new Util();
                if(util.isNetworkAvailable(EnrouteTripActivity.this))
                    getSaveLoads();
                else
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
        mBottomSheetLayout.setFab(add_trip);

    }
    @Override
    public void onBackPressed() {
        // Write your code here
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onBackPressed();
    }
    @OnClick(R.id.add_trip) void onFabClick() {
        mBottomSheetLayout.expandFab();
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
    void recyclerView(){
        RecyclerView.LayoutManager kLayoutManager = new LinearLayoutManager(EnrouteTripActivity.this);
        recyclerView.setLayoutManager(kLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(EnrouteTripActivity.this, 0));

    }
    void getSaveLoads(){
        PreferenceManager prf=new PreferenceManager(EnrouteTripActivity.this);
        Call<OrderListModel> call1 = apiInterface.getOrderList("Saved",""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"0","bearer "+ prf.getStringData("accessToken"),"application/json");

        call1.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                swipeRefreshLayout.setRefreshing(false);
                OrderListModel getdata = response.body();
                try {
                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                        ArrayList<OrderListModel.datavalue1> getdata3 = getdata.dataValue;
                        OrderListAdapter adapterd = new OrderListAdapter(getdata3, EnrouteTripActivity.this);
                        recyclerView.setAdapter(adapterd);
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    new Util().sendSMTPMail(EnrouteTripActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<OrderListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(EnrouteTripActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
}
