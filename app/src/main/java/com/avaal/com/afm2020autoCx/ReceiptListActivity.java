package com.avaal.com.afm2020autoCx;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.VehicleListAdapter;
import com.avaal.com.afm2020autoCx.models.GetInvVehicleModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdModel;
import com.avaal.com.afm2020autoCx.models.InventoryOrderModel;
import com.avaal.com.afm2020autoCx.models.InvoiceListModel;
import com.avaal.com.afm2020autoCx.models.ReceiptListModel;
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

public class ReceiptListActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id._vehicle_list)
    RecyclerView recyclerView;
    PreferenceManager prf;
    APIInterface apiInterface;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    ReceiptListAdapter adapterd = null;
    ArrayList<ReceiptListModel.datalist> getdata3=new ArrayList<>();
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    int pageNo=1;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onResume() {
        super.onResume();
        pageNo=1;
        getdata3.clear();
        adapterd.notifyDataSetChanged();
        showAnimation();
        getAFMReceiptList(pageNo);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_list_activity);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        title.setText("Receipt List");
        prf = new PreferenceManager(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        recyclerView();

    }

    @OnClick(R.id.home_)
    void home(){
        new Util().myIntent(this,NewDashBoardActivity.class);
    }
    @OnClick(R.id.back)
    void back(){
        onBackPressed();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    void recyclerView(){
        LinearLayoutManager kLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(kLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        adapterd = new ReceiptListAdapter(getdata3, ReceiptListActivity.this);
        recyclerView.setAdapter(adapterd);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = kLayoutManager.getChildCount();
                    totalItemCount = kLayoutManager.getItemCount();
                    pastVisiblesItems = kLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            getAFMReceiptList(pageNo);
//                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });


    }

    void getAFMReceiptList(int pageno){
//        GetVehicleIdListModel vindetail1=new GetVehicleIdListModel(prf.getStringData("authKey"),orderid,vihicle,prf.getStringData("carrierPrimaryId"));
        Call<ReceiptListModel> call1 = apiInterface.getReceiptList("","10",""+pageno,"","",""+ prf.getStringData("userCode"),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<ReceiptListModel>() {
            @Override
            public void onResponse(Call<ReceiptListModel> call, Response<ReceiptListModel> response) {

                ReceiptListModel getdata = response.body();
                hideAnimation();
                try {

                    getdata3.addAll(getdata.datalist);
                    adapterd.notifyDataSetChanged();
                    if(getdata.satus) {
                        if(getdata.datalist.size()<10)
                            loading=false;
                        else {
                            ++pageNo;
                            loading = true;
                        }

                    }else{
                        hideAnimation();
                        if(pageno==1) {
                            MDToast mdToast = MDToast.makeText(ReceiptListActivity.this, "" + getdata.Message, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                            mdToast.show();
                        }
//                        MDToast mdToast = MDToast.makeText(FleetListActivity.this, "" + fleetlist.Message, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
//                        mdToast.show();
                    }
//
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(ReceiptListActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<ReceiptListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(ReceiptListActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
    public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.MyViewHolder> {

        private ArrayList<ReceiptListModel.datalist> invoiceList;
        private Context context;
        PreferenceManager prf;
        APIInterface apiInterface;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView receipt_num, receipt_date, model, order, customer, amount, receipt_type, location;
            public ImageView typeicon;
            public LinearLayout inv_no_li;
            FrameLayout item;

            public MyViewHolder(View view) {
                super(view);
                receipt_num = (TextView) view.findViewById(R.id.invoice_num);
               receipt_date = (TextView) view.findViewById(R.id.invoice_date);
                order=(TextView)view.findViewById(R.id.order_);
                customer=(TextView)view.findViewById(R.id.customer_);
                amount=(TextView)view.findViewById(R.id.amount_);
                receipt_type=(TextView)view.findViewById(R.id.receipt_type);
                inv_no_li=(LinearLayout)view.findViewById(R.id.inv_no_li);




            }
        }


        public ReceiptListAdapter(ArrayList<ReceiptListModel.datalist> invoiceList, Context context) {
            this.invoiceList = invoiceList;
            this.context = context;
            prf = new PreferenceManager(context);
            //        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        @Override
        public ReceiptListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receipt_list_item, parent, false);

            return new ReceiptListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ReceiptListAdapter.MyViewHolder holder, final int position) {


//        if(!tripList.get(position).makeV.equalsIgnoreCase("null"))
//        String[] logo=(tripList.get(position).makeV).toLowerCase().split(" ");
            try {


                holder.receipt_num.setText(String.valueOf(invoiceList.get(position).ReceiptNumber));
                holder.receipt_date.setText(new Util().getUtcToCurrentDateOnly((invoiceList.get(position).ReceiptDate)));
                holder.order.setText(invoiceList.get(position).InvoiceNumber);
                holder.receipt_type.setText(invoiceList.get(position).ReceiptType);
                holder.amount.setText(invoiceList.get(position).CurrencyCode+" "+invoiceList.get(position).ReceiptAmount);
//                if(invoiceList.get(position).CurrencyCode.equalsIgnoreCase("INR"))
//                holder.amount.setText("₹ "+invoiceList.get(position).ReceiptAmount);
//                else
//                    holder.amount.setText("$ "+invoiceList.get(position).ReceiptAmount);
                if(invoiceList.get(position).ReceiptType.equalsIgnoreCase("Receipt for credit")){
                    holder.inv_no_li.setVisibility(View.INVISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return invoiceList.size();
        }
    }

}
