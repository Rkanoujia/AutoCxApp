package com.avaal.com.afm2020autoCx.frahment;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.CreateTripType;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.adapter.OrderListAdapter;
import com.avaal.com.afm2020autoCx.models.OrderListModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by dell pc on 08-03-2018.
 */

public class LoadsFragment extends Fragment {

    @BindView(R.id._order_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    @BindView(R.id.add_trip)
    FloatingActionButton add_trip;
     @BindView(R.id.save_ic)
     TextView saveIcon;
    @BindView(R.id.ship_ic)
    TextView shipIcon;
    @BindView(R.id.all_ic)
    TextView allIcon;
    @BindView(R.id.bottom_sheet1)
    BottomSheetLayout mBottomSheetLayout;
    @BindView(R.id.new_vehicle)
    LinearLayout new_vehicle;
    APIInterface apiInterface;
    String filterString="";
    public LoadsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.save_loads, container, false);
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
    void recyclerView(){
        RecyclerView.LayoutManager kLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(kLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 0));

    }
    void getSaveLoads(){
        PreferenceManager prf=new PreferenceManager(getActivity());
//        OrderListModel vindetail1=new OrderListModel(prf.getStringData("authKey"),prf.getStringData("carrierPrimaryId"),"OL","Saved");
        Call<OrderListModel> call1 = apiInterface.getOrderList(""+filterString,""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"0","bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                swipeRefreshLayout.setRefreshing(false);
                OrderListModel getdata = response.body();
                hideAnimation();
                try {
                    if (getdata.satus) {
                        if(getdata.dataValue.size()==0){
                            new_vehicle.setVisibility(View.VISIBLE);
                        }else
                            new_vehicle.setVisibility(View.GONE);
// GetVehicleIdListModel tripDetails;
                        ArrayList<OrderListModel.datavalue1> getdata3 = getdata.dataValue;
                        OrderListAdapter adapterd = new OrderListAdapter(getdata3, getActivity());
                        recyclerView.setAdapter(adapterd);
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }else{
                        new_vehicle.setVisibility(View.VISIBLE);
                        ArrayList<OrderListModel.datavalue1> getdata3 = getdata.dataValue;
                        OrderListAdapter adapterd = new OrderListAdapter(getdata3, getActivity());
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
    @OnClick(R.id.add_order)
    void add_order(){
        Intent j = new Intent(getActivity(), CreateTripType.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    @OnClick(R.id.new_vehicle)
    void  new_vehicle(){
        Intent j = new Intent(getActivity(), CreateTripType.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
@OnClick(R.id.ship_li)
void shipfilter(){
    mBottomSheetLayout.contractFab();
    filterString="Shipped";
    allIcon.setVisibility(View.INVISIBLE);
    saveIcon.setVisibility(View.INVISIBLE);
    shipIcon.setVisibility(View.VISIBLE);
    getSaveLoads();
}
    @OnClick(R.id.save_li)
    void savefilter(){
        mBottomSheetLayout.contractFab();
        filterString="Saved";
        allIcon.setVisibility(View.INVISIBLE);
        saveIcon.setVisibility(View.VISIBLE);
        shipIcon.setVisibility(View.INVISIBLE);
        getSaveLoads();
    }
    @OnClick(R.id.all_li)
    void allfilter(){
        mBottomSheetLayout.contractFab();
        filterString="";
        allIcon.setVisibility(View.VISIBLE);
        saveIcon.setVisibility(View.INVISIBLE);
        shipIcon.setVisibility(View.INVISIBLE);
        getSaveLoads();
    }
    @OnClick(R.id.add_trip) void onFabClick() {
        mBottomSheetLayout.expandFab();
    }

//    @OnClick(R.id.add_trip)
//    void add_trip(){
//        Intent j = new Intent(getActivity(), CreateTripType.class);
//
////        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
//        startActivity(j);
//        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//    }
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