package com.avaal.com.afm2020autoCx;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.VehicleListAdapter;
import com.avaal.com.afm2020autoCx.models.GetInvVehicleModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdModel;
import com.avaal.com.afm2020autoCx.models.InventoryOrderModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 07-03-2018.
 */

public class VehicleListActivity extends AppCompatActivity {
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

    @SuppressLint("RestrictedApi")
    @Override
    protected void onResume() {
        super.onResume();
        if(prf.getStringData("OrderStatus")!=null) {

            Log.e("orderStatus 33",""+prf.getStringData("OrderStatus"));

            if (prf.getStringData("OrderStatus").equalsIgnoreCase("Saved")) {

                save_load.setClickable(true);
                save_load.setEnabled(true);
                ship_load.setClickable(true);
                ship_load.setEnabled(true);
                bottom_tab.setVisibility(View.VISIBLE);
                add_vehicle.setVisibility(View.VISIBLE);
                showAnimation();
                getVehicleList(orderId);
            }else if (prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")) {
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
        setContentView(R.layout.vehicle_list_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        title.setText("Vehicle List");
         prf = new PreferenceManager(this);
//         orderId=prf.getStringData("OrderId");
//        orderType=prf.getStringData("OrderType");
 try {
     orderId = getIntent().getStringExtra("OrderId");
 }catch (Exception e){
     e.printStackTrace();
     new Util().sendSMTPMail(VehicleListActivity.this,null,"CxE004",e,"");
 }
         apiInterface = APIClient.getClient().create(APIInterface.class);
//        getVehicleList(orderId);
        textshow.setVisibility(View.GONE);
        recyclerView();


        if(prf.getStringData("OrderStatus")!=null) {
            if (prf.getStringData("OrderStatus").equalsIgnoreCase("Saved") || prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")) {
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
        if (prf.getStringData("OrderStatus").equalsIgnoreCase("Saved")|| prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped") ) {
            if (getdata3.size() > 0) {

                Dialog dialog = new Dialog(VehicleListActivity.this);
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


                Intent i=new Intent(VehicleListActivity.this,NewAddVehicleActivity.class);
                    i.putExtra("VehicleId",getdata.oredrId);
//                    prf.saveStringData("OrderStatus","Saved");
                    i.putExtra("OrderId",""+orderid);
                    i.putExtra("VehicleType", "false");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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


                hideAnimation();
                try {
                    GetVehicleIdListModel getdata = response.body();
//                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                        getdata3 = getdata.dataV;
                        adapterd = new VehicleListAdapter(getdata3, VehicleListActivity.this);
                        recyclerView.setAdapter(adapterd);
                        isSelectedforInv();

//                        list.clear();
//                        for (int i = 0; getdata.dataV.size() > i; i++) {
//                            list.add(getdata.dataV.get(i).vinNumber);
//                        }
                        if (getdata3.size() == 0) {
                            if (prf.getStringData("When").equalsIgnoreCase("add")) {
                                if (prf.getStringData("OrderStatus").equalsIgnoreCase("Saved"))
                                    addNewVehicle();
                            } /*else
                                back(); */
                        }
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(VehicleListActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(VehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getAFMVehicleList(String orderid){
//        GetVehicleIdListModel vindetail1=new GetVehicleIdListModel(prf.getStringData("authKey"),orderid,vihicle,prf.getStringData("carrierPrimaryId"));
        Call<GetVehicleIdListModel> call1 = apiInterface.getAFMOrderVehicleList(""+orderid,""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetVehicleIdListModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdListModel> call, Response<GetVehicleIdListModel> response) {


                hideAnimation();
                try {
                    GetVehicleIdListModel getdata = response.body();
//                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                    getdata3 = getdata.dataV;
                    adapterd = new VehicleListAdapter(getdata3, VehicleListActivity.this);
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
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(VehicleListActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(VehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    @OnClick(R.id.save_load)
    void saveLoad(){
       if(getdata3.size()==0) {
           MDToast mdToast = MDToast.makeText(VehicleListActivity.this, "Please Add Vehicle", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
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
                    new Util().sendSMTPMail(VehicleListActivity.this,null,"CxE004",e,"");
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
            MDToast mdToast = MDToast.makeText(VehicleListActivity.this, "Please Add Vehicle", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }

        if (prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")){
            showAnimation();
            try {
                submitloadforinv("forship");
            } catch (Exception e) {
                e.printStackTrace();
                new Util().sendSMTPMail(VehicleListActivity.this, null, "CxE004", e, "");
            }
        }else{

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
                        new Util().sendSMTPMail(VehicleListActivity.this, null, "CxE004", e, "");
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 108) && (resultCode == RESULT_OK) && (data.getStringExtra("Inventry").equalsIgnoreCase("true"))) {
            try {
                String str1 = null;
                if (prf.getStringData("OrderStatus").equalsIgnoreCase("Saved")) {
                    showAnimation();
                    str1 = this.orderId;
//                        getVehicleList(orderId);
                    }else if (prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")) {
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
                settingsDialog.cancel();
                getVehicleId(orderId);

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                settingsDialog.cancel();
               prf.saveStringData("When", "add");
                Intent   intent = new Intent(VehicleListActivity.this, InventoryVehicleListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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


                hideAnimation();
                try {
                    GetVehicleIdModel getdata = response.body();
                    if (getdata.satus) {
                        MDToast mdToast = MDToast.makeText(VehicleListActivity.this, "Load Saved", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
//                    Intent j = new Intent(VehicleListActivity.this, DashBoardBottomMenu.class);
//
//                    j.putExtra("open","home");
//                    startActivity(j);
//                        finishAffinity();
                        new Util().myIntent(VehicleListActivity.this, NewOrderListActivity.class);

                    } else {
                        MDToast mdToast = MDToast.makeText(VehicleListActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(VehicleListActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(VehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
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



                hideAnimation();
                try {
                    GetVehicleIdModel getdata = response.body();
                    if (getdata.satus) {
                        if (prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")){
                            MDToast mdToast = MDToast.makeText(VehicleListActivity.this, "Order Updated", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
                        }else {
                            new Util().sendAlert(VehicleListActivity.this, prf.getStringData("userName") + " has placed an order having " + " vehicle(s) with Ref no " + orderId + ".", "Shipped");
                            MDToast mdToast = MDToast.makeText(VehicleListActivity.this, "Your load has been shipped to Carrier", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
                        }
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//
                        new Util().myIntent(VehicleListActivity.this, NewOrderListActivity.class);


//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    } else {
                        MDToast mdToast = MDToast.makeText(VehicleListActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(VehicleListActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(VehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
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



                    hideAnimation();
                    try {
                        InventoryOrderModel getdata1 = response.body();
                        if (getdata1.status) {
                            if (type.equalsIgnoreCase("forsave"))
                                forsave();
                            else {
                                forLoad();

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        new Util().sendSMTPMail(VehicleListActivity.this,null,"CxE004",e,"");
                    }
                }

                @Override
                public void onFailure(Call<InventoryOrderModel> call, Throwable t) {
                    call.cancel();
                    new Util().sendSMTPMail(VehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
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


                hideAnimation();
                try {
                    GetVehicleIdListModel getdata = response.body();
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

//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(VehicleListActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(VehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

}
