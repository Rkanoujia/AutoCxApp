package com.avaal.com.afm2020autoCx;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.OrderListAdapter;
import com.avaal.com.afm2020autoCx.adapter.VehicleListAdapter;
import com.avaal.com.afm2020autoCx.models.GetInvVehicleModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdModel;
import com.avaal.com.afm2020autoCx.models.InventoryOrderModel;
import com.avaal.com.afm2020autoCx.models.OrderListModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewOrderViewActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id._vehicle_list)
    RecyclerView recyclerView;
    @BindView(R.id.save_load)
    TextView save_load;
    @BindView(R.id.add_vehicle)
    FloatingActionButton add_vehicle;
    @BindView(R.id.ship_load)
    TextView ship_load;
    @BindView(R.id.bottom_tab)
    LinearLayout bottom_tab;
    @BindView(R.id.toptext)
    TextView textshow;
    @BindView(R.id.top_linear)
    LinearLayout top_linear;
    @BindView(R.id.status)
    TextView title1 ;
    @BindView(R.id.order_id)
    TextView id ;
    @BindView(R.id._from)
    TextView from;
    @BindView(R.id._to)
    TextView to;
    @BindView(R.id.from_address)
    TextView from_address;
    @BindView(R.id.to_address)
    TextView to_address;
    @BindView(R.id.vehicle_num)
    TextView vehicle_num;
    @BindView(R.id.full_item1)
    LinearLayout full_item;
    @BindView(R.id.from_date)
    TextView from_date;
    @BindView(R.id.to_date)
    TextView to_date;
    @BindView(R.id.from_time)
    TextView from_time;
    @BindView(R.id.to_time)
    TextView to_time;
    @BindView(R.id.type_)
    TextView typ_;
    @BindView(R.id.track)
    TextView track;
    @BindView(R.id.cancelation)
    TextView cancelation;
    @BindView(R.id.pickup_name)
   TextView pickup_name;
    @BindView(R.id.delivery_name)
    TextView delivery_name;
    @BindView(R.id.order_lbl)
    TextView order_lbl;
 @BindView(R.id.vieword)
         FrameLayout vieword;

    String vehicleId;
    PreferenceManager prf;
    APIInterface apiInterface;
    String orderId;
    VehicleListAdapter adapterd = null;
    ArrayList<GetVehicleIdListModel.datavalue> getdata3=new ArrayList<>();
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    ArrayList<String> list=new ArrayList<>();
    OrderListModel.datavalue1 viewModel;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getStringExtra("OrderStatus")!=null) {

            Log.e("orderStatus 33",""+getIntent().getStringExtra("OrderStatus"));

            if (getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("")|| getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Saved")) {

                save_load.setClickable(true);
                save_load.setEnabled(true);
                ship_load.setClickable(true);
                ship_load.setEnabled(true);
                bottom_tab.setVisibility(View.VISIBLE);
                add_vehicle.setVisibility(View.VISIBLE);
                showAnimation();
                getVehicleList(orderId);
                getSaveOrder();
            }else if (getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Shipped")) {
//                add_vehicle.setVisibility(View.GONE);
//                bottom_tab.setVisibility(View.GONE);
//                ship_load.setVisibility(View.GONE);
//                top_linear.setVisibility(View.GONE);
//                save_load.setClickable(false);
//                save_load.setEnabled(false);
//                ship_load.setClickable(false);
//                ship_load.setEnabled(false);
                save_load.setVisibility(View.GONE);
                save_load.setEnabled(false);
                ship_load.setText("Update Load");
                ship_load.setClickable(true);
                ship_load.setEnabled(true);
                bottom_tab.setVisibility(View.VISIBLE);
                add_vehicle.setVisibility(View.VISIBLE);
                showAnimation();
                getSaveOrder();
                getVehicleList(orderId);

            }else {
                add_vehicle.setVisibility(View.GONE);
                bottom_tab.setVisibility(View.GONE);
//                ship_load.setVisibility(View.GONE);
                top_linear.setVisibility(View.GONE);
                save_load.setClickable(false);
                save_load.setEnabled(false);
                ship_load.setClickable(false);
                ship_load.setEnabled(false);
                showAnimation();
                getAFMVehicleList(orderId);
                viewOrderDetail();
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

//        Util util=new Util();
//        if(!util.isNetworkAvailable(this)){
//            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
//            mdToast.show();
//            return;
//        }
//        try {
//            if (prf.getStringData("OrderStatus").equalsIgnoreCase("save")) {
////                showAnimation();
//
//
//
//                getVehicleList(orderId, "VLSaved");
//            }
//            else {
//                showAnimation();
//                getVehicleList(orderId, "VLShipped");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_view_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        title.setText("Order Detail");
        prf = new PreferenceManager(this);
//         orderId=prf.getStringData("OrderId");
//        orderType=prf.getStringData("OrderType");
        try {
            orderId = getIntent().getStringExtra("OrderId");
        }catch (Exception e){
            e.printStackTrace();
            new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
        }
        Log.e("orderStatus 33",""+getIntent().getStringExtra("OrderStatus")+"/"+orderId);
        apiInterface = APIClient.getClient().create(APIInterface.class);
//        getVehicleList(orderId);
        textshow.setVisibility(View.GONE);
        recyclerView();


        if(getIntent().getStringExtra("OrderStatus")!=null) {
            if (getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Saved") || getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Shipped")) {
                save_load.setClickable(true);
                save_load.setEnabled(true);
                ship_load.setClickable(true);
                ship_load.setEnabled(true);
                bottom_tab.setVisibility(View.VISIBLE);
                add_vehicle.setVisibility(View.VISIBLE);

            }else {
                add_vehicle.setVisibility(View.GONE);
                bottom_tab.setVisibility(View.GONE);
//                ship_load.setVisibility(View.GONE);
                top_linear.setVisibility(View.GONE);
                save_load.setClickable(false);
                save_load.setEnabled(false);
                ship_load.setClickable(false);
                ship_load.setEnabled(false);
            }
        }


    }
    @OnClick(R.id.vieword)
    void viewedit(){
        Intent j = new Intent(this, RouteSelectionActivity.class);
        j.putExtra("from", viewModel.PickupCountryCode);
        j.putExtra("to", viewModel.DeliveryCountryCode);
        j.putExtra("Id",orderId);
        j.putExtra("OrderFrom","Update");
        String gson=new Gson().toJson(viewModel);
        j.putExtra("ModelClass",gson);
        startActivity(j);
    }
    @OnClick(R.id.add_vehicle)
    void addNewVehicle(){
        popUp();
//        Util util=new Util();
//        if(util.isNetworkAvailable(this))
//        getVehicleId(orderId);

    }
    @OnClick(R.id.home_)
    void home(){
        new Util().myIntent(this,NewDashBoardActivity.class);
    }
    @OnClick(R.id.back)
    void back(){
        if (getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Saved")|| getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Shipped") ) {
            if (getdata3.size() > 0) {

                Dialog dialog = new Dialog(NewOrderViewActivity.this);
                dialog.setContentView(R.layout.custome_alert_dialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // if button is clicked, close the custom dialog
                Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
                Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
                TextView title=(TextView)dialog.findViewById(R.id.title) ;
                TextView message=(TextView)dialog.findViewById(R.id.message) ;
                title.setText("");
                message.setText("Are you sure you want to back?");
                ok.setText("Yes");
                cancel.setText("No");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        prf.saveStringData("vehicleList", null);
                        // Write your code here to invoke YES event
                        onBackPressed();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }else
                onBackPressed();
        }else
            onBackPressed();

    }
    @Override
    public void onBackPressed() {
        // Write your code here
        prf.saveStringData("vehicleList", null);
        super.onBackPressed();
    }
    void recyclerView(){
        RecyclerView.LayoutManager kLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(kLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));

    }
    void getVehicleId(final String orderid){
        GetVehicleIdModel vindetail=new GetVehicleIdModel(prf.getStringData("authKey"),orderid,"");
        Call<GetInvVehicleModel> call1 = apiInterface.getTempInvVehicleIds("0","0", null, null, null, null, null, null, null,null, null, null, null,null, null,null, null, null, null,null, null, null , null, null,null ,""+prf.getStringData("userCode"),null,""+ prf.getStringData("userName"),new Util().getDateTime(),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<GetInvVehicleModel>() {
            @Override
            public void onResponse(Call<GetInvVehicleModel> call, Response<GetInvVehicleModel> response) {

                GetInvVehicleModel getdata = response.body();
                if(getdata.satus) {


                    Intent i=new Intent(NewOrderViewActivity.this,NewAddVehicleActivity.class);
                    i.putExtra("VehicleId",getdata.oredrId);
//                    prf.saveStringData("OrderStatus","Saved");
                    i.putExtra("OrderId",""+orderid);
                    i.putExtra("VehicleType", "false");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("vihiclevinList",list);
                    startActivity(i);
                }
            }
            @Override
            public void onFailure(Call<GetInvVehicleModel> call, Throwable t) {
                call.cancel();
            }
        });
    }
    void getVehicleList(String orderid){
//        GetVehicleIdListModel vindetail1=new GetVehicleIdListModel(prf.getStringData("authKey"),orderid,vihicle,prf.getStringData("carrierPrimaryId"));
        Call<GetVehicleIdListModel> call1 = apiInterface.getInventoryVehicleList(""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),""+orderid,"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetVehicleIdListModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdListModel> call, Response<GetVehicleIdListModel> response) {

                GetVehicleIdListModel getdata = response.body();
                hideAnimation();
                try {

//                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                    getdata3 = getdata.dataV;
                    adapterd = new VehicleListAdapter(getdata3, NewOrderViewActivity.this);
                    recyclerView.setAdapter(adapterd);
                    isSelectedforInv();

//                        list.clear();
//                        for (int i = 0; getdata.dataV.size() > i; i++) {
//                            list.add(getdata.dataV.get(i).vinNumber);
//                        }
                    if (getdata3.size() == 0) {
                        if (prf.getStringData("When").equalsIgnoreCase("add")) {
                            if (getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Saved"))
                                addNewVehicle();
                        } /*else
                                back(); */
                    }
//                    Intent i=new Intent(NewOrderViewActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(NewOrderViewActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getAFMVehicleList(String orderid){
//        GetVehicleIdListModel vindetail1=new GetVehicleIdListModel(prf.getStringData("authKey"),orderid,vihicle,prf.getStringData("carrierPrimaryId"));
        Call<GetVehicleIdListModel> call1 = apiInterface.getAFMOrderVehicleList(""+orderid,""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetVehicleIdListModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdListModel> call, Response<GetVehicleIdListModel> response) {

                GetVehicleIdListModel getdata = response.body();
                hideAnimation();
                try {

//                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                    getdata3 = getdata.dataV;
                    adapterd = new VehicleListAdapter(getdata3, NewOrderViewActivity.this);
                    recyclerView.setAdapter(adapterd);
//                    isSelectedforInv();

//                        list.clear();
//                        for (int i = 0; getdata.dataV.size() > i; i++) {
//                            list.add(getdata.dataV.get(i).vinNumber);
//                        }
//                    if (getdata3.size() == 0) {
//                        if (prf.getStringData("When").equalsIgnoreCase("add")) {
//                            if (prf.getStringData("OrderStatus").equalsIgnoreCase("Saved"))
//                                addNewVehicle();
//                        } /*else
//                                back(); */
//                    }
//                    Intent i=new Intent(NewOrderViewActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(NewOrderViewActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    @OnClick(R.id.save_load)
    void saveLoad(){
        if(getdata3.size()==0) {
            MDToast mdToast = MDToast.makeText(NewOrderViewActivity.this, "Please Add Vehicle", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Message
        alertDialog.setMessage("Do you want to save load?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                showAnimation();
                try {
                    submitloadforinv("forsave");
                } catch (Exception e) {
                    e.printStackTrace();
                    new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
                }
//                deleteVihicle(tripList.get(position).vehiocleId, position);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }
    @OnClick(R.id.ship_load)
    void shipLoad(){
        if(getdata3.size()==0) {
            MDToast mdToast = MDToast.makeText(NewOrderViewActivity.this, "Please Add Vehicle", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Message
        alertDialog.setMessage("Do you want to ship load?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                showAnimation();
                try {
                    submitloadforinv("forship");
                } catch (Exception e) {
                    e.printStackTrace();
                    new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
                }

//                deleteVihicle(tripList.get(position).vehiocleId, position);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 108) && (resultCode == RESULT_OK) && (data.getStringExtra("Inventry").equalsIgnoreCase("true"))) {
            try {
                String str1 = null;
                if (getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Saved")) {
                    showAnimation();
                    str1 = this.orderId;
//                        getVehicleList(orderId);
                }else if (getIntent().getStringExtra("OrderStatus").equalsIgnoreCase("Shipped")) {
                    showAnimation();
//                    getVehicleList(orderId);
//                    top_linear.setVisibility(View.GONE);
                }
                else {
                    showAnimation();
                    getAFMVehicleList(orderId);
                    top_linear.setVisibility(View.GONE);
                }
//                }
//                for (String str2 = "VLSaved";; str2 = "VLShipped")
//                {
//                    getVehicleList(str1, str2);
//                    break;
//                    top_linear.setVisibility(8);
//                    showAnimation();
//                    str1 = this.orderId;
//                }

            }

            catch(Exception localException)
            {
                localException.printStackTrace();
            }
        }

    }

    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.list_main);
        if (mainlayout != null && loaderScreen == null) {
            loaderScreen = new LoaderScreen(this);
            loaderView = loaderScreen.getView();
            loaderScreen.showBackground(getApplicationContext(), true);
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
    void popUp(){
        final BottomSheetDialog settingsDialog = new BottomSheetDialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(LayoutInflater.from(this).inflate(R.layout.new_bottom_sheet
                , null));
        settingsDialog.show();
        TextView confirm=(TextView)settingsDialog.findViewById(R.id.invent);
        TextView track=(TextView)settingsDialog.findViewById(R.id.new_vin);
        list.clear();
        int i = 0;
        while (getdata3.size() > i)
        {
            list.add(getdata3.get(i).vinNumber);
            i += 1;
        }
//                }
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.dismiss();
                getVehicleId(orderId);

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                settingsDialog.dismiss();
                prf.saveStringData("When", "add");
                Intent   intent = new Intent(NewOrderViewActivity.this, InventoryVehicleListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("IsSelect", "true");
                intent.putExtra("vihiclevinList", list);
                startActivityForResult(intent, 108);

            }
        });

    }
    void forsave() throws Exception{

        showAnimation();

        Call<GetVehicleIdModel> call1 = apiInterface.saveLoads(orderId,""+true,""+ prf.getStringData("userName"),new Util().getDateTime(),""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"bearer "+ prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<GetVehicleIdModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdModel> call, Response<GetVehicleIdModel> response) {

                GetVehicleIdModel getdata = response.body();
                hideAnimation();
                try {
                    if (getdata.satus) {
                        MDToast mdToast = MDToast.makeText(NewOrderViewActivity.this, "Load Saved", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
//                    Intent j = new Intent(NewOrderViewActivity.this, DashBoardBottomMenu.class);
//
//                    j.putExtra("open","home");
//                    startActivity(j);
                        finishAffinity();
                        new Util().myIntent(NewOrderViewActivity.this, NewOrderListActivity.class);
//                    Intent i=new Intent(NewOrderViewActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    } else {
                        MDToast mdToast = MDToast.makeText(NewOrderViewActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(NewOrderViewActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void forLoad() throws Exception{

        showAnimation();
//        GetVehicleIdModel vindetail=new GetVehicleIdModel(prf.getStringData("authKey"),,prf.getStringData("carrierPrimaryId"));
        Call<GetVehicleIdModel> call1 = apiInterface.shipLoads(orderId,""+ prf.getStringData("userName"),new Util().getDateTime(),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/x-www-form-urlencoded");

        call1.enqueue(new Callback<GetVehicleIdModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdModel> call, Response<GetVehicleIdModel> response) {

                GetVehicleIdModel getdata = response.body();

                hideAnimation();
                try {
                    if (getdata.satus) {
                        new Util().sendAlert(NewOrderViewActivity.this,prf.getStringData("userName")+" has placed an order having "+" vehicle(s) with Ref no "+orderId+".","Shipped");
                        MDToast mdToast = MDToast.makeText(NewOrderViewActivity.this, "Your load has been shipped to Carrier", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finishAffinity();
                        new Util().myIntent(NewOrderViewActivity.this, NewOrderListActivity.class);


//                    Intent i=new Intent(NewOrderViewActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    } else {
                        MDToast mdToast = MDToast.makeText(NewOrderViewActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(NewOrderViewActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void submitloadforinv (final String type)throws Exception{
        String vehicleid="";

        for(int i=0;getdata3.size()>i;i++){
            if(getdata3.get(i).getSelect()!=null){
                if(getdata3.get(i).getSelect()){
                    if(vehicleid.equalsIgnoreCase(""))
                        vehicleid=getdata3.get(i).vehiocleId;
                    else
                        vehicleid=vehicleid+","+getdata3.get(i).vehiocleId;
                }

            }

        }
        Log.e("vihicles",""+vehicleid);
        if(vehicleid.equalsIgnoreCase("")){
            if(type.equalsIgnoreCase("forsave"))
                forsave();
            else{
                forLoad();

            }
        }else {
//            InventoryOrderModel vindetail = new InventoryOrderModel(prf.getStringData("authKey"), , prf.getStringData("OrderId"));
            Call<InventoryOrderModel> call1 = apiInterface.orderFromInventory(""+orderId,""+vehicleid,""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
            call1.enqueue(new Callback<InventoryOrderModel>() {
                @Override
                public void onResponse(Call<InventoryOrderModel> call, Response<InventoryOrderModel> response) {

                    InventoryOrderModel getdata1 = response.body();

                    hideAnimation();
                    try {
                        if (getdata1.status) {
                            if (type.equalsIgnoreCase("forsave"))
                                forsave();
                            else {
                                forLoad();

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
                    }
                }

                @Override
                public void onFailure(Call<InventoryOrderModel> call, Throwable t) {
                    call.cancel();
                    new Util().sendSMTPMail(NewOrderViewActivity.this,t,"CxE001",null,""+call.request().url().toString());
                }
            });
        }

    }
    void isSelectedforInv()
            throws Exception
    {
//        Gson gson = new Gson();
//        StringBuilder localStringBuilder = new StringBuilder();
//        localStringBuilder.append("");
//        localStringBuilder.append(this.prf.a("vehicleList"));
//        Log.e("vehicleList", localStringBuilder.toString());
        getInventoryVehicleList();
//        Gson gson = new Gson();
//        if (this.prf.getStringData("vehicleList") != null)
//        {
//            ArrayList<GetVehicleIdListModel.datavalue>  localObject = gson.fromJson(prf.getStringData("vehicleList"), new TypeToken<ArrayList<GetVehicleIdListModel.datavalue>>() {}.getType());
//            if (localObject != null)
//            {
//                for(int i=0;localObject.size()>i;i++){
//                   getdata3.add(localObject.get(i));
//
//                }
//                adapterd.notifyDataSetChanged();
//            }
//        }
    }
    void getInventoryVehicleList(){
//        GetVehicleIdListModel vindetail1=new GetVehicleIdListModel(prf.getStringData("authKey"),"","",prf.getStringData("carrierPrimaryId"));
        Call<GetVehicleIdListModel> call1 = apiInterface.getInventoryVehicleList(""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"0","bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetVehicleIdListModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdListModel> call, Response<GetVehicleIdListModel> response) {

                GetVehicleIdListModel getdata = response.body();
                hideAnimation();
                try {
                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
//                        getdata3 = getdata.dataV;
                        Gson gson = new Gson();
                        if (prf.getStringData("vehicleList") != null)
                        {
                            ArrayList<GetVehicleIdListModel.datavalue>  localObject = gson.fromJson(prf.getStringData("vehicleList"), new TypeToken<ArrayList<GetVehicleIdListModel.datavalue>>() {}.getType());
                            if (localObject != null)
                            {
                                for(int i=0;localObject.size()>i;i++){
                                    for(int j=0;getdata.dataV.size()>j;j++){
                                        if(getdata.dataV.get(j).vehiocleId.equalsIgnoreCase(localObject.get(i).vehiocleId)) {
                                            getdata.dataV.get(i).setSelect(true);
                                            getdata3.add(getdata.dataV.get(i));
                                            adapterd.notifyDataSetChanged();
                                        }
                                    }
//                                    getdata3.add(localObject.get(i));

                                }

                            }
                        }

//                    Intent i=new Intent(NewOrderViewActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(NewOrderViewActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(NewOrderViewActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void viewOrderDetail() {
        Gson gson = new Gson();
        if (getIntent().getStringExtra("ModelClass") != null) {
            OrderListModel.datavalue1 listObject = gson.fromJson(getIntent().getStringExtra("ModelClass"), new TypeToken<OrderListModel.datavalue1>() {
            }.getType());
            viewModel=listObject;

                id.setText(listObject.SavedOrderNumber);

            order_lbl.setText("Order# ");
//        if(tripList.get(position).orderId.length()>0)
//            holder.title.setVisibility(View.GONE);
            title1.setText(listObject.status.toUpperCase());

            if(listObject.status.equalsIgnoreCase("TripStarted"))
                title1.setTextColor(getResources().getColor(R.color.blue));
            else  if(listObject.status.equalsIgnoreCase("Dispatched")) {
                title1.setTextColor(getResources().getColor(R.color.blue));
            }else  if(listObject.status.equalsIgnoreCase("QUOTED")) {
                title1.setTextColor(getResources().getColor(R.color.orange));
            }else  if(listObject.status.equalsIgnoreCase("Delivered")) {
                title1.setTextColor(getResources().getColor(R.color.black));
            } else  if(listObject.status.equalsIgnoreCase("Confirmed")) {
                title1.setTextColor(getResources().getColor(R.color.green));
            }else  if(listObject.status.equalsIgnoreCase("CANCELLED")) {
                title1.setTextColor(getResources().getColor(R.color.orange));
            }

            from.setText(listObject.pickupCity + ", " + listObject.pickupstateCode + ", " + listObject.PickupCountryCode);
            to.setText(listObject.dropCity + ", " + listObject.dropSateCode + ", " + listObject.DeliveryCountryCode);
            from_address.setText(listObject.pickupZip);
            to_address.setText(listObject.dropZip);
            if (listObject.vehiclecount.equalsIgnoreCase("1"))
                vehicle_num.setText(listObject.vehiclecount + " Vehicle");
            else
                vehicle_num.setText(listObject.vehiclecount + " Vehicles");
            from_date.setText(new Util().getUtcToCurrentTime(listObject.PickupDateTime));
            to_date.setText(new Util().getUtcToCurrentTime(listObject.DropDateTime));
            from_time.setText("");
            to_time.setText("");
            pickup_name.setText(listObject.PickupName);
            delivery_name.setText(listObject.dropName);
            if(listObject.PickupCountryCode.equalsIgnoreCase("CA") && listObject.DeliveryCountryCode.equalsIgnoreCase("CA"))
                typ_.setText("Inter Provincial");
            else if(listObject.PickupCountryCode.equalsIgnoreCase("US") && listObject.DeliveryCountryCode.equalsIgnoreCase("US"))
                typ_.setText("Inter State");
            else if(listObject.PickupCountryCode.equalsIgnoreCase("CA") && listObject.DeliveryCountryCode.equalsIgnoreCase("US"))
                typ_.setText("Export");
            else
                typ_.setText("Import");
        }
    }
    void getSaveOrder(){
        PreferenceManager prf=new PreferenceManager(NewOrderViewActivity.this);
//        OrderListModel vindetail1=new OrderListModel(prf.getStringData("authKey"),prf.getStringData("carrierPrimaryId"),"OL","Saved");
        Call<OrderListModel> call1 = apiInterface.getOrderList("",""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),""+orderId,"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {


                OrderListModel getdata = response.body();
                hideAnimation();
                try {
                    if (getdata.satus) {
                        if(getdata.dataValue.size()>0) {
                            viewModel=getdata.dataValue.get(0);
                            if (!getdata.dataValue.get(0).orderId.trim().equalsIgnoreCase("0"))
                                id.setText(getdata.dataValue.get(0).orderId);
                            else
                                id.setText(getdata.dataValue.get(0).SavedOrderNumber);
//        if(tripList.get(position).orderId.length()>0)
//            holder.title.setVisibility(View.GONE);
                            if (getdata.dataValue.get(0).status.equalsIgnoreCase(""))
                                title1.setText("SAVED");
                            else
                                title1.setText(getdata.dataValue.get(0).status.toUpperCase());

                            if (getdata.dataValue.get(0).status.equalsIgnoreCase("Saved"))
                                title1.setTextColor(getResources().getColor(R.color.textPrimaryDark));
                            else
                                title1.setTextColor(getResources().getColor(R.color.blue));

                            from.setText(getdata.dataValue.get(0).pickupCity + ", " + getdata.dataValue.get(0).pickupstateCode + ", " + getdata.dataValue.get(0).PickupCountryCode);
                            to.setText(getdata.dataValue.get(0).dropCity + ", " + getdata.dataValue.get(0).dropSateCode + ", " + getdata.dataValue.get(0).DeliveryCountryCode);
                            from_address.setText(getdata.dataValue.get(0).pickupZip);
                            to_address.setText(getdata.dataValue.get(0).dropZip);
                            if (getdata.dataValue.get(0).vehiclecount.equalsIgnoreCase("1"))
                                vehicle_num.setText(getdata.dataValue.get(0).vehiclecount + " Vehicle");
                            else
                                vehicle_num.setText(getdata.dataValue.get(0).vehiclecount + " Vehicles");
                            from_date.setText(new Util().getUtcToCurrentTime(getdata.dataValue.get(0).PickupDateTime));
                            to_date.setText(new Util().getUtcToCurrentTime(getdata.dataValue.get(0).DropDateTime));
                            from_time.setText("");
                            to_time.setText("");
                            pickup_name.setText(getdata.dataValue.get(0).PickupName);
                            delivery_name.setText(getdata.dataValue.get(0).dropName);
                            if (getdata.dataValue.get(0).orderType != null) {
                                if (getdata.dataValue.get(0).orderType.equalsIgnoreCase("Interprovincial"))
                                    typ_.setText("Inter Provincial");
                                else if (getdata.dataValue.get(0).orderType.equalsIgnoreCase("Interstate"))
                                    typ_.setText("Inter State");
                                else
                                    typ_.setText(getdata.dataValue.get(0).orderType);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<OrderListModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(NewOrderViewActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
}
