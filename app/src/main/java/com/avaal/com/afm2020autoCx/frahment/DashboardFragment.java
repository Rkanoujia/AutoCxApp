package com.avaal.com.afm2020autoCx.frahment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
 * Created by dell pc on 08-03-2018.
 */

public class DashboardFragment extends Fragment {
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
    String orderId;
    ArrayList<OrderListModel.datavalue1> getdata3;
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    public DashboardFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.save_loads, container, false);
        ButterKnife.bind(this, rootView);
        mainlayout=(FrameLayout)rootView.findViewById(R.id.main_fram);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        return rootView;
    }

    @SuppressLint("RestrictedApi")
    public void onStart() {
        super.onStart();
        home_.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        recyclerView();
        try {
            if (getArguments().getString("orderId") != null) {
                orderId = getArguments().getString("orderId");
                Log.e("getOderId",orderId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Util util=new Util();
        if(util.isNetworkAvailable(getActivity())) {
            try {
                showAnimation();
                getSaveLoads(getArguments().getString("message"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(getArguments().getString("message").equalsIgnoreCase("Shipped"))
            title.setText("Dispatched Loads");
        else  if(getArguments().getString("message").equalsIgnoreCase("Confirmed"))
            title.setText("Confirmed Loads");
        else  if(getArguments().getString("message").equalsIgnoreCase("Enroute"))
            title.setText("En-Route Trips");
        else if(getArguments().getString("message").equalsIgnoreCase("Trip"))
            title.setText("Confirmed Loads");

        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.GRAY);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Util util=new Util();
                if(util.isNetworkAvailable(getActivity())) {
                    try {

                        getSaveLoads(getArguments().getString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
    void getSaveLoads(String filterBy)throws Exception{
        PreferenceManager prf=new PreferenceManager(getActivity());
        Call<OrderListModel> call1 = apiInterface.getOrderList("Dispatched",""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"bearer "+ prf.getStringData("accessToken"),"application/json");

        call1.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                swipeRefreshLayout.setRefreshing(false);
                OrderListModel getdata = response.body();
                hideAnimation();
                try {
                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                         getdata3 = getdata.dataValue;
                        DashboardOrderListAdapter adapterd = new DashboardOrderListAdapter(getdata3, getActivity());
                        recyclerView.setAdapter(adapterd);
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);

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
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<OrderListModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                MDToast mdToast = MDToast.makeText(getActivity(), "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                mdToast.show();
                new Util().sendSMTPMail(getActivity(),t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
void tracktrip(ArrayList<OrderListModel.datavalue1> getdata4)throws Exception{
        if(orderId!=null){
            for(int i=0;getdata4.size()>i;i++){
                if(getdata4.get(i).orderId.equalsIgnoreCase(orderId)){
                    Log.e("getOrderId33",orderId);
                  gotopage(getdata4.get(i));
                }

            }

        }
}
void gotopage(OrderListModel.datavalue1 datavalue1)throws Exception{
    Log.e("OrderId2",datavalue1.saveOrderId);
    Intent intent=new Intent(getActivity(),ViewRouteMapListActivity.class);
    intent.putExtra("OrderId",datavalue1.saveOrderId);
    intent.putExtra("from_",datavalue1.pickupState);
    intent.putExtra("status",datavalue1.status);
    intent.putExtra("vehicle_num",datavalue1.vehiclecount);
    intent.putExtra("type",datavalue1.orderType);
    intent.putExtra("from_address",datavalue1.pickupAddress+"\n"+datavalue1.pickupCity+"\n"+datavalue1.pickupZip);
    intent.putExtra("from_date",datavalue1.PickupDateTime);
    intent.putExtra("from_time","");
    intent.putExtra("to_",datavalue1.dropState);
    intent.putExtra("to_address",datavalue1.dropAddress+"\n"+datavalue1.dropCity+"\n"+datavalue1.dropZip);
    intent.putExtra("to_date",datavalue1.DropDateTime);
    intent.putExtra("to_time","");
    startActivity(intent);

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

