package com.avaal.com.afm2020autoCx;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.models.GetInvVehicleModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.RemoveVehicleModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryVehicleListActivity extends AppCompatActivity {
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
    boolean IsSelect = false;
    String vehicleId;
    PreferenceManager prf;
    APIInterface apiInterface;
    String orderId;
    InventryVehicleListAdapter adapterd = null;
    ArrayList<GetVehicleIdListModel.datavalue> getdata3;
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    boolean exist=true;
    ArrayList<GetVehicleIdListModel.datavalue> selectedData = new ArrayList();
    ArrayList<String> list=new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();

        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        try {
            getVehicleList();
            this.ship_load.setVisibility(View.GONE);
            this.save_load.setText("Add");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_list_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        title.setText("Inventory Vehicle List");
        prf = new PreferenceManager(this);
        orderId=prf.getStringData("OrderId");
//        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (getIntent().getStringExtra("IsSelect") != null) {
            if (getIntent().getStringExtra("IsSelect").contains("false"))
            {
                this.IsSelect = false;
                this.textshow.setVisibility(View.GONE);
                this.bottom_tab.setVisibility(View.GONE);
            }
            else
            {
                this.textshow.setText("Note: Please hold for inventory vehicle selection");
                this.bottom_tab.setVisibility(View.VISIBLE);
                this.IsSelect = true;
            }
        }
        this.top_linear.setVisibility(View.GONE);
//        getVehicleList(orderId);
        recyclerView();

        try
        {
            setstore();
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        if(prf.getStringData("OrderStatus")!=null) {
//            if (prf.getStringData("OrderStatus").equalsIgnoreCase("save")) {
//                save_load.setClickable(true);
//                save_load.setEnabled(true);
//                ship_load.setClickable(true);
//                ship_load.setEnabled(true);
//                bottom_tab.setVisibility(View.VISIBLE);
//                add_vehicle.setVisibility(View.VISIBLE);
//            }else {
//                add_vehicle.setVisibility(View.GONE);
//                bottom_tab.setVisibility(View.GONE);
////                ship_load.setVisibility(View.GONE);
//                save_load.setClickable(false);
//                save_load.setEnabled(false);
//                ship_load.setClickable(false);
//                ship_load.setEnabled(false);
//            }
//        }


    }
    @OnClick(R.id.add_vehicle)
    void addNewVehicle(){
        showAnimation();
        prf.saveStringData("OrderId", " ");
        Util util=new Util();
        if(util.isNetworkAvailable(this)) {
            try {
//                Intent i=new Intent(InventoryVehicleListActivity.this,AddVehicleActivity.class);
//                i.putExtra("VehicleId","0");
//                //                    prf.saveStringData("VehicleType","true");
//                i.putExtra("VehicleType","true");
//                i.putExtra("vihiclevinList",list);
//                startActivity(i);

                getVehicleId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    @OnClick(R.id.home_)
    void home(){
        new Util().myIntent(this,NewDashBoardActivity.class);
    }
    @OnClick(R.id.back)
    void back(){
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

    }
    void getVehicleId()throws Exception{
//        GetInvVehicleModel vindetail=new GetInvVehicleModel(prf.getStringData("authKey"),"I", prf.getStringData("carrierPrimaryId"));
        Call<GetInvVehicleModel> call1 = apiInterface.getTempInvVehicleIds("0","0", null, null, null, null, null, null, null,null, null, null, null,null, null,null, null, null, null,null, null, null , null, null,null ,""+prf.getStringData("userCode"),null,""+ prf.getStringData("userName"),new Util().getDateTime(),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");

        call1.enqueue(new Callback<GetInvVehicleModel>() {
            @Override
            public void onResponse(Call<GetInvVehicleModel> call, Response<GetInvVehicleModel> response) {

                GetInvVehicleModel getdata = response.body();
                try {
                    if(getdata.satus) {

    //                    paramAnonymousl = new Intent(InventoryListActivity.this, AddVehicleActivity.class);
    //                    paramAnonymousl.putExtra("VehicleId", paramAnonymousb.data.temOdId);
    //                    paramAnonymousl.putExtra("VehicleType", "true");
    //                    paramAnonymousl.putExtra("vihiclevinList", InventoryListActivity.this.list);
//                        prf.saveStringData("OrderStatus", "saved");
                        Intent i=new Intent(InventoryVehicleListActivity.this,AddVehicleActivity.class);
                        i.putExtra("VehicleId",getdata.oredrId);
                        i.putExtra("OrderId","0");
    //                    prf.saveStringData("VehicleType","true");
                        i.putExtra("VehicleType","true");
                        i.putExtra("vihiclevinList",list);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetInvVehicleModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(InventoryVehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getVehicleList(){
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
                        getdata3 = getdata.dataV;
                        adapterd = new InventryVehicleListAdapter(getdata3,InventoryVehicleListActivity.this,IsSelect);
                        recyclerView.setAdapter(adapterd);
                        list.clear();
                        for (int i = 0; getdata.dataV.size() > i; i++) {
                            list.add(getdata.dataV.get(i).vinNumber);
                        }
                        if (getdata3.size() == 0) {
                            if (prf.getStringData("When").equalsIgnoreCase("add")) {
                                if (prf.getStringData("OrderStatus").equalsIgnoreCase("saved"))
                                    addNewVehicle();
                            } else
                                back();

                        }else
                        isSelectedforInv();
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }
                    if(getdata.Message.equalsIgnoreCase("Record not found")){
                        if (prf.getStringData("When").equalsIgnoreCase("add")) {
                                addNewVehicle();
                        }
//                        else
//                            back();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(InventoryVehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void isSelectedforInv()
    {
        Gson gson = new Gson();
        if ( prf.getStringData("vehicleList") != null)
        {
          ArrayList<GetVehicleIdListModel.datavalue>  listObject = gson.fromJson(prf.getStringData("vehicleList"), new TypeToken<ArrayList<GetVehicleIdListModel.datavalue>>() {}.getType());
            if (listObject != null)
            {
                for(int i=0;listObject.size()>i;i++){

                    for(int j=0;getdata3.size()>j;j++){
                        if(getdata3.get(j).vehiocleId.equalsIgnoreCase(listObject.get(i).vehiocleId)){
                            getdata3.remove(j);

                        }
                    }
                }

                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        adapterd.notifyDataSetChanged();
                    }
                });



//                int i = 0;
//                while (localObject.size() > i)
//                {
//                    int j = 0;
//                    while (this.getdata3.size() > j)
//                    {
//                        if (getdata3.get(j).vehiocleId.equalsIgnoreCase(localObject.get(i).vehiocleId)) {
//                            this.getdata3.remove(j);
//                        }
//                        j += 1;
//                    }
//                    i += 1;
//                }
            }
//            localObject = new StringBuilder();
//            ((StringBuilder)localObject).append("");
//            ((StringBuilder)localObject).append(this.getdata3.size());
//            Log.e("total selst items", ((StringBuilder)localObject).toString());
//            runOnUiThread(new Runnable()
//            {
//                public void run()
//                {
//                    adapterd.notifyDataSetChanged();
//                }
//            });
        }
    }
    @OnClick(R.id.save_load)
    void saveLoad(){

        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("vihiclevinList");

             for(int i=0;getdata3.size()>i;i++){
                 if(getdata3.get(i).getSelect()!=null && getdata3.get(i).getSelect() && myList!=null){
                     for(int j=0;myList.size()>j;j++){
                         if(getdata3.get(i).vinNumber.equalsIgnoreCase(myList.get(j))){
                             AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                             // Setting Dialog Message
                             alertDialog.setMessage("VIN "+myList.get(j)+" is already exist.");
                             // Setting Positive "Yes" Button
                             alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {

                                     // Write your code here to invoke YES event
                                 }
                             });
                             alertDialog.show();
                             return;
                         }
                     }
                 }
             }
        for(int i=0;getdata3.size()>i;i++){
                 if(getdata3.get(i).getSelect() != null && getdata3.get(i).getSelect()){
                     exist = false;
                     selectedData.add(getdata3.get(i));
                 }
        }
        if (exist)
        {
            MDToast mdToast = MDToast.makeText(InventoryVehicleListActivity.this, "Select Vehicle", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        String gson=new Gson().toJson(selectedData);
       prf.saveStringData("vehicleList", gson);
        Intent intent = new Intent();
        intent.putExtra("Inventry", "true");
        setResult(RESULT_OK, intent);
        finish();
//
//        showAnimation();
//        GetVehicleIdModel vindetail=new GetVehicleIdModel(prf.getStringData("authKey"),prf.getStringData("OrderId"),"");
//        Call<GetVehicleIdModel> call1 = apiInterface.saveLoads(vindetail);
//        call1.enqueue(new Callback<GetVehicleIdModel>() {
//            @Override
//            public void onResponse(Call<GetVehicleIdModel> call, Response<GetVehicleIdModel> response) {
//
//                GetVehicleIdModel getdata = response.body();
//                hideAnimation();
//                try {
//                    if (getdata.satus) {
//                        MDToast mdToast = MDToast.makeText(InventoryVehicleListActivity.this, "Load Saved", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                        mdToast.show();
////                    Intent j = new Intent(VehicleListActivity.this, DashBoardBottomMenu.class);
////
////                    j.putExtra("open","home");
////                    startActivity(j);
//                        Intent j = new Intent(InventoryVehicleListActivity.this, DashBoardBottomMenu.class);
//                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                        j.putExtra("open", "save");
//                        startActivity(j);
////                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
////                    i.putExtra("VehicleId",getdata.data.temOdId);
//////                    i.putExtra("orderType",orderType);
////                    startActivity(i);
//                    } else {
//                        MDToast mdToast = MDToast.makeText(InventoryVehicleListActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                        mdToast.show();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(Call<GetVehicleIdModel> call, Throwable t) {
//                call.cancel();
//            }
//        });
    }
//    @OnClick(R.id.ship_load)
//    void shipLoad(){
//        showAnimation();
//        GetVehicleIdModel vindetail=new GetVehicleIdModel(prf.getStringData("authKey"),prf.getStringData("OrderId"),prf.getStringData("carrierPrimaryId"));
//        Call<GetVehicleIdModel> call1 = apiInterface.shipLoads(vindetail);
//        call1.enqueue(new Callback<GetVehicleIdModel>() {
//            @Override
//            public void onResponse(Call<GetVehicleIdModel> call, Response<GetVehicleIdModel> response) {
//
//                GetVehicleIdModel getdata = response.body();
//
//                hideAnimation();
//                try {
//                    if (getdata.satus) {
//                        MDToast mdToast = MDToast.makeText(InventoryVehicleListActivity.this, "Shipped Load Success", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                        mdToast.show();
//                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                        Intent j = new Intent(InventoryVehicleListActivity.this, DashBoardBottomMenu.class);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                        j.putExtra("open", "ship");
//                        startActivity(j);
////                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
////                    i.putExtra("VehicleId",getdata.data.temOdId);
//////                    i.putExtra("orderType",orderType);
////                    startActivity(i);
//                    } else {
//                        MDToast mdToast = MDToast.makeText(InventoryVehicleListActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                        mdToast.show();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(Call<GetVehicleIdModel> call, Throwable t) {
//                call.cancel();
//            }
//        });
//    }
    void setstore()
            throws Exception
    {
        Gson gson = new Gson();
        selectedData.clear();
        if (prf.getStringData("vehicleList") != null)
        {
            ArrayList<GetVehicleIdListModel.datavalue> vehiclelist1 = gson.fromJson(prf.getStringData("vehicleList"), new TypeToken<ArrayList<GetVehicleIdListModel.datavalue>>() {}.getType());
            if (vehiclelist1 != null)
            {
                int i = 0;
                while (vehiclelist1.size() > i)
                {
                    selectedData.add(vehiclelist1.get(i));
                    i += 1;
                }
            }

            runOnUiThread(new Runnable()
            {
                public void run()
                {
                  if(getdata3.size()>0)
                    adapterd.notifyDataSetChanged();
                }
            });
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

    public class InventryVehicleListAdapter extends RecyclerView.Adapter<InventryVehicleListAdapter.MyViewHolder> {

        private ArrayList<GetVehicleIdListModel.datavalue> tripList;
        private Context context;
        PreferenceManager prf;
        APIInterface apiInterface;
        Boolean IsSelect;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, make, model, delete_,days,date_,is_preinspec,damage,view_details;
            public ImageView typeicon;
            public LinearLayout list_item;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.vin_num);
                make = (TextView) view.findViewById(R.id.make_v);
                model = (TextView) view.findViewById(R.id.model_v);
                delete_ = (TextView) view.findViewById(R.id.delete_);
                typeicon = (ImageView) view.findViewById(R.id.vehicle_img);
                list_item = (LinearLayout) view.findViewById(R.id.list_item);
                date_=(TextView)view.findViewById(R.id.date_);
                days=(TextView)view.findViewById(R.id.days_);
                is_preinspec=(TextView)view.findViewById(R.id.is_preinspec);
                damage=(TextView)view.findViewById(R.id.damage);
                view_details=(TextView)view.findViewById(R.id.view_details);

            }
        }


        public InventryVehicleListAdapter(ArrayList<GetVehicleIdListModel.datavalue> tripList,Context context ,Boolean IsSelect) {
            this.tripList = tripList;
            this.context = context;
            this.IsSelect=IsSelect;
            prf = new PreferenceManager(context);
            //        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        @Override
        public InventryVehicleListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inventry_vehicle_list_item, parent, false);

            return new InventryVehicleListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        holder.tripStatus.setCompoundDrawableTintList(ColorStateList.);
//        holder.date.setText(String.valueOf(movie.getDeliverydate()));
//        if(!tripList.get(position).makeV.equalsIgnoreCase("null"))
//        String[] logo=(tripList.get(position).makeV).toLowerCase().split(" ");
            try {
                String logo1 = "r";
                if (tripList.get(position).makeV != null)
                    logo1 = (tripList.get(position).makeV).toLowerCase().replaceAll(" ", "");
//            Picasso.with(context).load("http://www.car-logos.org/wp-content/uploads/2011/09/" + logo1 + ".png").into(holder.typeicon);
                if(tripList.get(position).Imagepath!=null){
                    if(!tripList.get(position).Imagepath.equalsIgnoreCase(""))
                        Picasso.with(context).load(tripList.get(position).Imagepath).error(R.drawable.nocar).into(holder.typeicon);
                    else
                        Picasso.with(context).load("http://www.autocarbrands.com/wp-content/uploads/2014/04/" + logo1 + ".png").error(R.drawable.nocar).into(holder.typeicon);
                }else
                Picasso.with(context).load("http://www.autocarbrands.com/wp-content/uploads/2014/04/" + logo1 + ".png").error(R.drawable.nocar).into(holder.typeicon);
                holder.title.setText(tripList.get(position).vinNumber);
                holder.make.setText(tripList.get(position).makeV);
                if (tripList.get(position).year != null)
                    if (tripList.get(position).year.equalsIgnoreCase(""))
                        holder.model.setText(tripList.get(position).model);
                    else
                        holder.model.setText(tripList.get(position).model + " (" + tripList.get(position).year + ")");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if(tripList.get(position).IsPreInspectionDone.equalsIgnoreCase("1")){

                            holder.is_preinspec.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                            holder.is_preinspec.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                        }else{
                            holder.is_preinspec.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                            holder.is_preinspec.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                        }
                    }
                    if(tripList.get(position).ExtraCount.equalsIgnoreCase("0"))
                        holder.damage.setText("No");
                     else
                        holder.damage.setText(tripList.get(position).ExtraCount);

//                if(tripList.size()==1)
//                    holder.delete_.setVisibility(View.GONE);
//                else
//
//                if (!prf.getStringData("OrderStatus").equalsIgnoreCase("save")) {
//                    holder.delete_.setVisibility(View.GONE);
//                }

                if(tripList.get(position).getSelect()!=null){
                    if(tripList.get(position).getSelect()){
                        holder.list_item.setBackgroundColor(Color.GRAY);
                    }else{
                        holder.list_item.setBackgroundColor(Color.WHITE);
                    }
                }else
                    tripList.get(position).setSelect(false);
                String current = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US).format(new Date());
                int day=getDaysbitweenDays(current,new Util().getUtcToCurrentTime(tripList.get(position).createDate));
                if(day>1)
                holder.days.setText(""+day+" Days");
                else
                    holder.days.setText(""+day+" Day");

                        holder.date_.setText(new Util().getUtcToCurrentTime((tripList.get(position).createDate)));

            }catch (Exception e){
                e.printStackTrace();
            }
            holder.delete_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog dialog = new Dialog(InventoryVehicleListActivity.this);
                    dialog.setContentView(R.layout.custome_alert_dialog);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    // if button is clicked, close the custom dialog
                    Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
                    Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
                    TextView title=(TextView)dialog.findViewById(R.id.title) ;
                    TextView message=(TextView)dialog.findViewById(R.id.message) ;
                    title.setText("");
                    message.setText("Are you sure you want delete this? ");
                    ok.setText("Yes");
                    cancel.setText("No");
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            deleteVihicle(tripList.get(position).vehiocleId, position,false);
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                }
            });
            if(IsSelect){
                holder.list_item.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if(tripList.get(position).getSelect() != null && tripList.get(position).getSelect()){
                            tripList.get(position).setSelect(false);
                        }else
                            tripList.get(position).setSelect(true);
                        notifyDataSetChanged();
                        return false;
                    }
                });
            }
            holder.is_preinspec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InventoryVehicleListActivity.this, AddImageActivity.class);
                    intent.putExtra("VehicleId", ""+tripList.get(position).vehiocleId);
                    intent.putExtra("Vin", ""+tripList.get(position).vinNumber);
                    intent.putExtra("IsInventry", "1");
//                    prf.saveStringData("OrderStatus", "saved");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent,10001);
                }
            });

            holder.view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                if(prf.getStringData("OrderStatus").equalsIgnoreCase("save")){

                    Intent intent=new Intent(context,AddVehicleActivity.class);
                    intent.putExtra("VehicleId",tripList.get(position).vehiocleId);
                    intent.putExtra("OrderId","0");
//                    prf.saveStringData("OrderStatus", "saved");
//                    intent.putExtra("ForInventory", "false");
                    intent.putExtra("VehicleType", "true");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



                    context.startActivity(intent);
//                }

                }
            });

        }

        @Override
        public int getItemCount() {
            return tripList.size();
        }
     int getDaysbitweenDays(String currentdate1,String olddate1){
         SimpleDateFormat dateFormat = new SimpleDateFormat(
                 "MM/dd/yyyy hh:mm aa", Locale.US);
          int day=0;
         try {

             Date olddate = dateFormat.parse(currentdate1);
             Date currentdate = dateFormat.parse(olddate1);
//             System.out.println(oldDate);

             long diff = olddate.getTime() - currentdate.getTime();
             long seconds = diff / 1000;
             long minutes = seconds / 60;
             long hours = minutes / 60;
             long days = hours / 24;
             day= (int) days;
         }catch (Exception e){
             e.printStackTrace();
         }
         return day;
     }
        void deleteVihicle(String tempVehicleId, final int position,boolean IsInvent) {
            RemoveVehicleModel vindetail = new RemoveVehicleModel(prf.getStringData("authKey"), tempVehicleId,IsInvent);
            Call<RemoveVehicleModel> call1 = apiInterface.removeVehicle(""+tempVehicleId,""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"bearer "+ prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
            call1.enqueue(new Callback<RemoveVehicleModel>() {
                @Override
                public void onResponse(Call<RemoveVehicleModel> call, Response<RemoveVehicleModel> response) {

                    RemoveVehicleModel getdata = response.body();
                    if(getdata.status!=null) {
                        if (getdata.status) {
                            MDToast mdToast = MDToast.makeText(InventoryVehicleListActivity.this, ""+getdata.Message, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                            list.remove(tripList.get(position).vinNumber);
                            tripList.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RemoveVehicleModel> call, Throwable t) {
                    call.cancel();
                    new Util().sendSMTPMail(InventoryVehicleListActivity.this,t,"CxE001",null,""+call.request().url().toString());
                }
            });
        }

    }
}
