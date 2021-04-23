package com.avaal.com.afm2020autoCx.frahment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.CreateTripType;
import com.avaal.com.afm2020autoCx.LoginActivity;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.adapter.DashboardOrderListAdapter;
import com.avaal.com.afm2020autoCx.adapter.OrderListAdapter;
import com.avaal.com.afm2020autoCx.models.OrderListModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.BottomSheetLayout;
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfmOrderFragment extends Fragment {

    @BindView(R.id._order_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.all_ic)
    TextView allIcon;
    @BindView(R.id.dispatch_ic)
    TextView dispatchIcon;
    @BindView(R.id.confirm_ic)
    TextView confirmIcon;
    @BindView(R.id.enroute_ic)
    TextView enrouteIcon;
    @BindView(R.id.delivered_ic)
    TextView deliveredcon;
    @BindView(R.id.canceled_ic)
    TextView canceledIcon;
    String filterString="";
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
        @BindView(R.id.add_trip)
        FloatingActionButton add_trip;
    @BindView(R.id.bottom_sheet1)
    BottomSheetLayout mBottomSheetLayout;
    APIInterface apiInterface;
    private long mLastClickTime = 0;
    public AfmOrderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.afm_order_fragment, container, false);
        ButterKnife.bind(this, rootView);
        mainlayout=(FrameLayout)rootView.findViewById(R.id.main_fram);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        return rootView;
    }

    public void onStart() {
        super.onStart();
        recyclerView();
        Util util=new Util();
        if(util.isNetworkAvailable(getActivity())) {
            showAnimation();
            getSaveLoads();
        }
        mBottomSheetLayout.setFab(add_trip);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.GRAY);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Util util=new Util();
                if(util.isNetworkAvailable(getActivity()))
                    getSaveLoads();
                else
                    swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    @OnClick(R.id.add_trip) void onFabClick() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if(!new Util().isNetworkAvailable(getActivity())) {
            MDToast mdToast = MDToast.makeText(getActivity(), "Check Your Internet connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        mBottomSheetLayout.expandFab();
    }
    void recyclerView(){
        RecyclerView.LayoutManager kLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(kLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 0));

    }

    @OnClick(R.id.all_li)
    void alafilter(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mBottomSheetLayout.contractFab();
        filterString="";
        allIcon.setVisibility(View.VISIBLE);
        dispatchIcon.setVisibility(View.INVISIBLE);
        confirmIcon.setVisibility(View.INVISIBLE);
        enrouteIcon.setVisibility(View.INVISIBLE);
        deliveredcon.setVisibility(View.INVISIBLE);
        canceledIcon.setVisibility(View.INVISIBLE);
        getSaveLoads();
    }
    @OnClick(R.id.confirm_li)
    void confirmfilter(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mBottomSheetLayout.contractFab();
        filterString="Confirmed";
        allIcon.setVisibility(View.INVISIBLE);
        dispatchIcon.setVisibility(View.INVISIBLE);
        confirmIcon.setVisibility(View.VISIBLE);
        enrouteIcon.setVisibility(View.INVISIBLE);
        deliveredcon.setVisibility(View.INVISIBLE);
        canceledIcon.setVisibility(View.INVISIBLE);
        getSaveLoads();
    }
    @OnClick(R.id.dispatch_li)
    void dispatchfilter(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mBottomSheetLayout.contractFab();
        filterString="Dispatched";
        allIcon.setVisibility(View.INVISIBLE);
        dispatchIcon.setVisibility(View.VISIBLE);
        confirmIcon.setVisibility(View.INVISIBLE);
        enrouteIcon.setVisibility(View.INVISIBLE);
        deliveredcon.setVisibility(View.INVISIBLE);
        canceledIcon.setVisibility(View.INVISIBLE);
        getSaveLoads();
    }
    @OnClick(R.id.enroute_li)
    void enroutefilter(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mBottomSheetLayout.contractFab();
        filterString="Enroute";
        allIcon.setVisibility(View.INVISIBLE);
        dispatchIcon.setVisibility(View.INVISIBLE);
        confirmIcon.setVisibility(View.INVISIBLE);
        enrouteIcon.setVisibility(View.VISIBLE);
        deliveredcon.setVisibility(View.INVISIBLE);
        canceledIcon.setVisibility(View.INVISIBLE);
        getSaveLoads();
    }
    @OnClick(R.id.delivered_li)
    void deliveredfilter(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mBottomSheetLayout.contractFab();
        filterString="Delivered";
        allIcon.setVisibility(View.INVISIBLE);
        dispatchIcon.setVisibility(View.INVISIBLE);
        confirmIcon.setVisibility(View.INVISIBLE);
        enrouteIcon.setVisibility(View.INVISIBLE);
        deliveredcon.setVisibility(View.VISIBLE);
        canceledIcon.setVisibility(View.INVISIBLE);
        getSaveLoads();
    }
    @OnClick(R.id.canceled_li)
    void canceledfilter(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mBottomSheetLayout.contractFab();
        filterString="Cancelled";
        allIcon.setVisibility(View.INVISIBLE);
        dispatchIcon.setVisibility(View.INVISIBLE);
        confirmIcon.setVisibility(View.INVISIBLE);
        enrouteIcon.setVisibility(View.INVISIBLE);
        deliveredcon.setVisibility(View.INVISIBLE);
        canceledIcon.setVisibility(View.VISIBLE);
        getSaveLoads();
    }
    void getSaveLoads(){
        PreferenceManager prf=new PreferenceManager(getActivity());
//        OrderListModel vindetail1=new OrderListModel(prf.getStringData("authKey"),prf.getStringData("carrierPrimaryId"),"OL","Saved");
        Call<OrderListModel> call1 = apiInterface.getAFMLoadListByStatus("0",""+filterString,""+ prf.getStringData("userCode"),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                swipeRefreshLayout.setRefreshing(false);

                hideAnimation();
                try {
                    OrderListModel getdata = response.body();
                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                        ArrayList<OrderListModel.datavalue1> getdata3 = getdata.dataValue;
                        DashboardOrderListAdapter adapterd = new DashboardOrderListAdapter(getdata3, getActivity());
                        recyclerView.setAdapter(adapterd);
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }else{
                        ArrayList<OrderListModel.datavalue1> getdata3 = getdata.dataValue;
                        DashboardOrderListAdapter adapterd = new DashboardOrderListAdapter(getdata3, getActivity());
                        recyclerView.setAdapter(adapterd);
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