package com.avaal.com.afm2020autoCx.frahment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.LoginActivity;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.ViewRouteMapListActivity;
import com.avaal.com.afm2020autoCx.adapter.DashboardOrderListAdapter;
import com.avaal.com.afm2020autoCx.models.OrderListModel;
import com.valdesekamdem.library.mdtoast.MDToast;

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
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 18-04-2018.
 */

public class DeliveredFragment extends Fragment {
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
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    String orderId;
    public DeliveredFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.save_loads, container, false);
        ButterKnife.bind(this, rootView);
        mainlayout = (FrameLayout) rootView.findViewById(R.id.main_fram);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        return rootView;
    }

    @SuppressLint("RestrictedApi")
    public void onStart() {
        super.onStart();

        home_.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        try {
            if (getArguments().getString("orderId") != null) {
                orderId = getArguments().getString("orderId");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        recyclerView();
        Util util=new Util();
        if(util.isNetworkAvailable(getActivity())) {
            showAnimation();
            getSaveLoads("Delivered");
        }

            title.setText("Delivered Trips");

        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.GRAY);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Util util=new Util();
                if(util.isNetworkAvailable(getActivity()))
                    getSaveLoads("Delivered");
                else
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
        add_trip.setVisibility(View.GONE);


    }
    void recyclerView(){
        RecyclerView.LayoutManager kLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(kLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 0));

    }
    void getSaveLoads(String filterBy){
        PreferenceManager prf=new PreferenceManager(getActivity());
        Call<OrderListModel> call1 = apiInterface.getOrderList("",""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"0","bearer "+ prf.getStringData("accessToken"),"application/json");

        call1.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                swipeRefreshLayout.setRefreshing(false);

                try {
                    OrderListModel getdata = response.body();
                    hideAnimation();
                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;

                            final ArrayList<OrderListModel.datavalue1> getdata3 = getdata.dataValue;
                            DashboardOrderListAdapter adapterd = new DashboardOrderListAdapter(getdata3, getActivity());
                            recyclerView.setAdapter(adapterd);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        tracktrip(getdata3);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
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
            public void onFailure(Call<OrderListModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(getActivity(),t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void tracktrip(ArrayList<OrderListModel.datavalue1> getdata3)throws Exception{
        if(orderId!=null){
            for(int i=0;getdata3.size()>i;i++){
                if(getdata3.get(i).orderId.equalsIgnoreCase(orderId)){
                    Intent intent=new Intent(getActivity(),ViewRouteMapListActivity.class);
                    intent.putExtra("OrderId",getdata3.get(i).saveOrderId);
                    intent.putExtra("from_",getdata3.get(i).pickupState);
                    intent.putExtra("status",getdata3.get(i).status);
                    intent.putExtra("vehicle_num",getdata3.get(i).vehiclecount);
                    intent.putExtra("type",getdata3.get(i).orderType);
                    intent.putExtra("from_address",getdata3.get(i).pickupAddress+"\n"+getdata3.get(i).pickupCity+"\n"+getdata3.get(i).pickupZip);
                    intent.putExtra("from_date",getdata3.get(i).PickupDateTime);
                    intent.putExtra("from_time","");
                    intent.putExtra("to_",getdata3.get(i).dropState);
                    intent.putExtra("to_address",getdata3.get(i).dropAddress+"\n"+getdata3.get(i).dropCity+"\n"+getdata3.get(i).dropZip);
                    intent.putExtra("to_date",getdata3.get(i).DropDateTime);
                    intent.putExtra("to_time","");
                    startActivity(intent);
                }

            }

        }
    }
    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        if (mainlayout != null && loaderScreen == null) {
            loaderScreen = new LoaderScreen(getActivity());
            loaderView = loaderScreen.getView();
            loaderScreen.showBackground(getActivity(), true);
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
}

