package com.avaal.com.afm2020autoCx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.ItemListAdapter;
import com.avaal.com.afm2020autoCx.adapter.TripListDetailAdapter;
import com.avaal.com.afm2020autoCx.models.ItemListModel;
import com.avaal.com.afm2020autoCx.models.OrderListModel;
import com.avaal.com.afm2020autoCx.models.TripListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
 * Created by dell pc on 28-03-2018.
 */

public class ViewRouteMapListActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id._trip_list)
    RecyclerView recyclerView;
    @BindView(R.id._from_order)
            TextView _from_order;
    @BindView(R.id.status_order)
            TextView status_order;
    @BindView(R.id.order_id_order)
            TextView order_id_order;
    @BindView(R.id.vehicle_num_order)
            TextView vehicle_num_order;
    @BindView( R.id.type_order_)
            TextView type_order_;
    @BindView(R.id.from_address_order)
            TextView from_address_order;
    @BindView(R.id.from_date_order)
            TextView from_date_order;
    @BindView(R.id.from_time_order)
            TextView from_time_order;
    @BindView(R.id._to_order)
            TextView _to_order;
    @BindView(R.id.to_address_order)
            TextView to_address_order;
    @BindView(R.id.to_date_order)
            TextView to_date_order;
    @BindView(R.id.to_time_order)
            TextView to_time_order;
//    @BindView(R.id.item_status)
//            TextView item_status;
//    @BindView(R.id.trip_status)
//            TextView trip_status;
    @BindView(R.id._item_list)
    RecyclerView _item_list;
    APIInterface apiInterface;
    ArrayList<TripListModel.TripDetail> getdata3 =new ArrayList<>();
    TripListDetailAdapter adapterd;
    ArrayList<ItemListModel.ItemDetail> getdata4=new ArrayList<>() ;
    ItemListAdapter adapter1;
    Boolean item=true;
    @Override
    protected void onStart() {
        super.onStart();







        vehicle_num_order.setText(getIntent().getStringExtra("vehicle_num"));
        type_order_.setText(getIntent().getStringExtra("type"));

        from_date_order.setText(new Util().getUtcToCurrentTime(getIntent().getStringExtra("from_date")));
        from_time_order.setText(getIntent().getStringExtra("from_time"));


        to_date_order.setText(new Util().getUtcToCurrentTime(getIntent().getStringExtra("to_date")));
        to_time_order.setText(getIntent().getStringExtra("to_time"));
        Gson gson = new Gson();
        if ( getIntent().getStringExtra("ModelClass") != null) {
            OrderListModel.datavalue1 listObject = gson.fromJson(getIntent().getStringExtra("ModelClass"), new TypeToken<OrderListModel.datavalue1>() {
            }.getType());
            from_address_order.setText(listObject.pickupCity+", \n"+listObject.pickupstateCode+", "+listObject.PickupCountryCode+", "+listObject.pickupZip);
            to_address_order.setText(listObject.dropCity+", \n"+listObject.dropSateCode+", "+listObject.DeliveryCountryCode+", "+listObject.dropZip);
            _from_order.setText(listObject.PickupName);
            _to_order.setText(listObject.dropName);
            order_id_order.setText("Order# "+listObject.SavedOrderNumber);

            if(listObject.PickupCountryCode.equalsIgnoreCase("CA") && listObject.DeliveryCountryCode.equalsIgnoreCase("CA"))
                type_order_.setText("Inter Provincial");
            else if(listObject.PickupCountryCode.equalsIgnoreCase("US") && listObject.DeliveryCountryCode.equalsIgnoreCase("US"))
                type_order_.setText("Inter State");
            else if(listObject.PickupCountryCode.equalsIgnoreCase("CA") && listObject.DeliveryCountryCode.equalsIgnoreCase("US"))
                type_order_.setText("Export");
            else
                type_order_.setText("Import");

            status_order.setText(listObject.status);
            if(listObject.status.equalsIgnoreCase("TripStarted"))
                status_order.setTextColor(this.getResources().getColor(R.color.blue));
            else  if(listObject.status.equalsIgnoreCase("Dispatched"))
                status_order.setTextColor(this.getResources().getColor(R.color.dot_inactive_screen1));
            else  if(listObject.status.equalsIgnoreCase("Enroute"))
                status_order.setTextColor(this.getResources().getColor(R.color.enroute));
            else  if(listObject.status.equalsIgnoreCase("Delivered"))
                status_order.setTextColor(this.getResources().getColor(R.color.dot_inactive_screen2));

            getItemLoads(getIntent().getStringExtra("OrderId"));
            getTripLoads(listObject.SavedOrderNumber);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_route_map_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
         apiInterface = APIClient.getClient().create(APIInterface.class);
        title.setText("Track Shipment");
        recyclerView();
        recyclerView1();
//        getItemLoads(getIntent().getStringExtra("OrderId"));
//        getTripLoads(getIntent().getStringExtra("OrderId"));

    }
    @OnClick(R.id.home_)
    void home(){
        new Util().myIntent(this,NewDashBoardActivity.class);
    }
    @OnClick(R.id.back)
    void back(){
//        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("vihiclevinList");
//        if(myList!=null){
//            if(myList.size()==0){
////                Intent intent=new Intent(this,AddImageActivity.class);
////                intent.putExtra("VehicleId",getIntent().getStringExtra("VehicleId"));
////                startActivity(intent);
//            }
//        }
//        prf.saveStringData("When","back");
        super.onBackPressed();
    }
    @Override
    public void onBackPressed() {
        // Write your code here
        super.onBackPressed();
    }

    void recyclerView(){
        RecyclerView.LayoutManager kLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(kLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        adapterd = new TripListDetailAdapter(getdata3, ViewRouteMapListActivity.this);
        recyclerView.setAdapter(adapterd);

    }
    void recyclerView1(){
        RecyclerView.LayoutManager kLayoutManager = new LinearLayoutManager(this);
        _item_list.setLayoutManager(kLayoutManager);
        _item_list.setItemAnimator(new DefaultItemAnimator());
        _item_list.addItemDecoration(new DividerItemDecoration(this, 0));
        adapter1 = new ItemListAdapter(getdata4, ViewRouteMapListActivity.this);
        _item_list.setAdapter(adapter1);

    }
    void getTripLoads(String orderNo){
        PreferenceManager prf=new PreferenceManager(this);
        TripListModel vindetail1=new TripListModel(prf.getStringData("authKey"),orderNo);
        Call<TripListModel> call1 = apiInterface.getTripTrackingByOrder(""+orderNo,""+ prf.getStringData("userCode"),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");

        call1.enqueue(new Callback<TripListModel>() {
            @Override
            public void onResponse(Call<TripListModel> call, Response<TripListModel> response) {

                TripListModel getdata = response.body();
                try {
                    if (getdata.status) {
// GetVehicleIdListModel tripDetails;
                         getdata3 = getdata.getData;
                         adapterd = new TripListDetailAdapter(getdata3, ViewRouteMapListActivity.this);
                        recyclerView.setAdapter(adapterd);

//                    ArrayList<TripListModel.ItemDetail> getdata4=getdata.Itemlist;
//                    ItemListAdapter adapter1=new ItemListAdapter(getdata4,ViewRouteMapListActivity.this);
//                    _item_list.setAdapter(adapter1);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TripListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(ViewRouteMapListActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    @OnClick(R.id.items_)
    void tripStatus(){
        if(item) {
            _item_list.setVisibility(View.VISIBLE);
            item=false;
        }else{
            _item_list.setVisibility(View.GONE);
            item=true;
        }

    }
//    @OnClick(R.id.item_status)
//    void itemStatus(){
//        recyclerView.setVisibility(View.GONE);
//        _item_list.setVisibility(View.VISIBLE);
//        item_status.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        item_status.setTextColor(getResources().getColor(R.color.colorWhite));
//        trip_status.setBackgroundColor(getResources().getColor(R.color.colorWhite));
//        trip_status.setTextColor(getResources().getColor(R.color.colorPrimary));
//    }
    void getItemLoads(String orderNo){
        PreferenceManager prf=new PreferenceManager(this);
        ItemListModel vindetail1=new ItemListModel(prf.getStringData("authKey"),orderNo);
        Call<ItemListModel> call1 = apiInterface.getAFMVehicleList(""+orderNo,""+ prf.getStringData("userCode"),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<ItemListModel>() {
            @Override
            public void onResponse(Call<ItemListModel> call, Response<ItemListModel> response) {

                ItemListModel getdata = response.body();
                try {
                    if (getdata.status) {
// GetVehicleIdListModel tripDetails;


                        getdata4 = getdata.Itemlist;
                         adapter1 = new ItemListAdapter(getdata4, ViewRouteMapListActivity.this);
                        _item_list.setAdapter(adapter1);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ItemListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(ViewRouteMapListActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
}
