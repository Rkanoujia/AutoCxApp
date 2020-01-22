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
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 18-04-2018.
 */

public class ConfirmLoadActivity extends AppCompatActivity {
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

        title.setText("Confirmed Trips");
        recyclerView();
        Util util=new Util();
        if(util.isNetworkAvailable(this))
            getSaveLoads();

        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.GRAY);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Util util=new Util();
                if(util.isNetworkAvailable(ConfirmLoadActivity.this))
                    getSaveLoads();
                else
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
        add_trip.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {
        // Write your code here
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
    void recyclerView(){
        RecyclerView.LayoutManager kLayoutManager = new LinearLayoutManager(ConfirmLoadActivity.this);
        recyclerView.setLayoutManager(kLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(ConfirmLoadActivity.this, 0));

    }
    void getSaveLoads(){
        PreferenceManager prf=new PreferenceManager(ConfirmLoadActivity.this);
        Call<OrderListModel> call1 = apiInterface.getOrderList("",""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"bearer "+ prf.getStringData("accessToken"),"application/json");

        call1.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                swipeRefreshLayout.setRefreshing(false);
                OrderListModel getdata = response.body();
                try{
                if(getdata.satus) {
// GetVehicleIdListModel tripDetails;
                    ArrayList<OrderListModel.datavalue1> getdata3=getdata.dataValue;
                    DashboardOrderListAdapter adapterd = new DashboardOrderListAdapter(getdata3,ConfirmLoadActivity.this);
                    recyclerView.setAdapter(adapterd);
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
            public void onFailure(Call<OrderListModel> call, Throwable t) {
                call.cancel();
            }
        });
    }
}