package com.avaal.com.afm2020autoCx;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.VehicleListAdapter;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.InvoiceListModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id._vehicle_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeLayout;
    PreferenceManager prf;
    APIInterface apiInterface;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    InvoiceListAdapter adapterd = null;
    ArrayList<InvoiceListModel.datalist> getdata3=new ArrayList<>();
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    int pageNo=1;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onResume() {
        super.onResume();
        if(!new Util().isNetworkAvailable(InvoiceListActivity.this)) {
            MDToast mdToast = MDToast.makeText(InvoiceListActivity.this, "Check Your Internet connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        getdata3.clear();
        pageNo=1;
        adapterd.notifyDataSetChanged();
         showAnimation();
            getAFMInvoiceList(pageNo);


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
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        title.setText("Invoice List");
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
        adapterd = new InvoiceListAdapter(getdata3, InvoiceListActivity.this);
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
                            getAFMInvoiceList(pageNo);
//                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });


    }

    void getAFMInvoiceList(int pageno){
//        GetVehicleIdListModel vindetail1=new GetVehicleIdListModel(prf.getStringData("authKey"),orderid,vihicle,prf.getStringData("carrierPrimaryId"));
        Call<InvoiceListModel> call1 = apiInterface.getInvoiceList("100",""+pageno,"","",""+ prf.getStringData("userCode"),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<InvoiceListModel>() {
            @Override
            public void onResponse(Call<InvoiceListModel> call, Response<InvoiceListModel> response) {

                InvoiceListModel getdata = response.body();
                hideAnimation();
                swipeLayout.setRefreshing(false);
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
                            MDToast mdToast = MDToast.makeText(InvoiceListActivity.this, "" + getdata.Message, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                            mdToast.show();
                        }
//                        MDToast mdToast = MDToast.makeText(FleetListActivity.this, "" + fleetlist.Message, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
//                        mdToast.show();
                    }
//
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(InvoiceListActivity.this,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<InvoiceListModel> call, Throwable t) {
                call.cancel();
                swipeLayout.setRefreshing(false);
                hideAnimation();
                new Util().sendSMTPMail(InvoiceListActivity.this,t,"CxE001",null,""+call.request().url().toString());
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

    @Override
    public void onRefresh() {
        if(!new Util().isNetworkAvailable(InvoiceListActivity.this)) {
            swipeLayout.setRefreshing(false);
            MDToast mdToast = MDToast.makeText(InvoiceListActivity.this, "Check Your Internet connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        getdata3.clear();
        pageNo=1;
        adapterd.notifyDataSetChanged();
        showAnimation();
        getAFMInvoiceList(pageNo);
    }

    public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.MyViewHolder> {

        private ArrayList<InvoiceListModel.datalist> invoiceList;
        private Context context;
        PreferenceManager prf;
        APIInterface apiInterface;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView invoice_num, invoice_date, model, order, customer, amount, factoring, status,type;
            public ImageView typeicon;
            public LinearLayout list_item;
            FrameLayout item;
            CardView card_view;

            public MyViewHolder(View view) {
                super(view);
                invoice_num = (TextView) view.findViewById(R.id.invoice_num);
                invoice_date = (TextView) view.findViewById(R.id.invoice_date);
                order=(TextView)view.findViewById(R.id.order_);
                customer=(TextView)view.findViewById(R.id.customer_);
                amount=(TextView)view.findViewById(R.id.amount_);
                factoring=(TextView)view.findViewById(R.id.factoring_);
                status=(TextView)view.findViewById(R.id.status_);
                card_view=(CardView)view.findViewById(R.id.card_view);
                type=(TextView)view.findViewById(R.id.type);


            }
        }


        public InvoiceListAdapter(ArrayList<InvoiceListModel.datalist> invoiceList, Context context) {
            this.invoiceList = invoiceList;
            this.context = context;
            prf = new PreferenceManager(context);
            //        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        @Override
        public InvoiceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.invoice_list_item, parent, false);

            return new InvoiceListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(InvoiceListAdapter.MyViewHolder holder, final int position) {


//        if(!tripList.get(position).makeV.equalsIgnoreCase("null"))
//        String[] logo=(tripList.get(position).makeV).toLowerCase().split(" ");
            try {

                holder.invoice_num.setText(String.valueOf(invoiceList.get(position).InvoiceNumber));
                holder.invoice_date.setText(new Util().getUtcToCurrentDateOnly((invoiceList.get(position).InvoiceDate)));
                holder.order.setText(invoiceList.get(position).OrderNumber);
                holder.customer.setText(invoiceList.get(position).CustomerOrderNumber);
                holder.amount.setText(invoiceList.get(position).CurrencyCode+" "+invoiceList.get(position).TotalAmount);
//                if(invoiceList.get(position).CurrencyCode.equalsIgnoreCase("INR"))
//                    holder.amount.setText("₹ "+invoiceList.get(position).TotalAmount);
//                else
//                    holder.amount.setText("$ "+invoiceList.get(position).TotalAmount);
//                holder.amount.setText(invoiceList.get(position).CurrencyCode+" "+invoiceList.get(position).TotalAmount);
                if(invoiceList.get(position).FactoringCompany==null ||invoiceList.get(position).FactoringCompany.equalsIgnoreCase(""))
                    holder.factoring.setText("NA");
                else
                holder.factoring.setText(invoiceList.get(position).FactoringCompany);
                holder.status.setText(invoiceList.get(position).InvoiceStatus);
                holder.type.setText(invoiceList.get(position).InvoiceType);
                if(invoiceList.get(position).InvoiceStatus.equalsIgnoreCase("Paid")){
                    holder.card_view.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }else{
                    holder.card_view.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
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
