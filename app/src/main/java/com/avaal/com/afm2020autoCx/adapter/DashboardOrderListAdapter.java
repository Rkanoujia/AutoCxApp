package com.avaal.com.afm2020autoCx.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.NewOrderViewActivity;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.RouteSelectionActivity;
import com.avaal.com.afm2020autoCx.ViewRouteMapListActivity;
import com.avaal.com.afm2020autoCx.models.ConfirmOrderModel;
import com.avaal.com.afm2020autoCx.models.OrderListModel;
;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 17-03-2018.
 */

public class DashboardOrderListAdapter extends RecyclerView.Adapter<DashboardOrderListAdapter.MyViewHolder> {

    private ArrayList<OrderListModel.datavalue1> tripList;
    private Context context;
    Boolean isSet=true;
    PreferenceManager prf;
    APIInterface apiInterface;
    @Override
    public void onBindViewHolder(final DashboardOrderListAdapter.MyViewHolder holder, final int position) {

//        holder.tripStatus.setCompoundDrawableTintList(ColorStateList.);
//        holder.date.setText(String.valueOf(movie.getDeliverydate()));
//        Picasso.with(context).load("http://www.car-logos.org/wp-content/uploads/2011/09/"+(tripList.get(position).makeV).toLowerCase()+".png").into(holder.typeicon);

            holder.id.setText(tripList.get(position).SavedOrderNumber);


        if(tripList.get(position).toString().equalsIgnoreCase("null"))
            holder.title.setText("Pending");
        else
            holder.title.setText(tripList.get(position).status.toUpperCase());

        if(tripList.get(position).status.equalsIgnoreCase("TripStarted"))
            holder.title.setTextColor(context.getResources().getColor(R.color.blue));
        else  if(tripList.get(position).status.equalsIgnoreCase("Dispatched")) {
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.blue));
            holder.title.setTextColor(context.getResources().getColor(R.color.blue));
        }else  if(tripList.get(position).status.equalsIgnoreCase("QUOTED")) {
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.orange));
            holder.title.setTextColor(context.getResources().getColor(R.color.orange));
        }else  if(tripList.get(position).status.equalsIgnoreCase("Delivered")) {
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.title.setTextColor(context.getResources().getColor(R.color.black));
        } else  if(tripList.get(position).status.equalsIgnoreCase("Confirmed")) {
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.title.setTextColor(context.getResources().getColor(R.color.green));
        }else  if(tripList.get(position).status.equalsIgnoreCase("CANCELLED")) {
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.orange));
            holder.title.setTextColor(context.getResources().getColor(R.color.orange));
        }

        holder.order_lbl.setText("Order# ");
         holder.temp_li.setVisibility(View.VISIBLE);
         holder.temp_order_id.setText(tripList.get(position).CustAppOrderId);
        holder.pickup_name.setText(tripList.get(position).PickupName);
        holder.delivery_name.setText(tripList.get(position).dropName);
        holder.from.setText(tripList.get(position).pickupCity+", "+tripList.get(position).pickupstateCode +", "+tripList.get(position).PickupCountryCode);
        holder.to.setText(tripList.get(position).dropCity+", "+tripList.get(position).dropSateCode+", "+tripList.get(position).DeliveryCountryCode);
        holder.from_address.setText(tripList.get(position).pickupZip);
        holder.to_address.setText(tripList.get(position).dropZip);
        if(tripList.get(position).vehiclecount.equalsIgnoreCase("1"))
        holder.vehicle_num.setText(tripList.get(position).vehiclecount+" Vehicle");
        else
            holder.vehicle_num.setText(tripList.get(position).vehiclecount+" Vehicles");
        holder.from_date.setText(new Util().getUtcToCurrentTime(tripList.get(position).PickupDateTime));
        holder.to_date.setText(new Util().getUtcToCurrentTime(tripList.get(position).DropDateTime));
        holder.from_time.setText("");
        holder.to_time.setText("");
        if(tripList.get(position).PickupCountryCode.equalsIgnoreCase("CA") && tripList.get(position).DeliveryCountryCode.equalsIgnoreCase("CA"))
            holder.typ_.setText("Inter Provincial");
        else if(tripList.get(position).PickupCountryCode.equalsIgnoreCase("US") && tripList.get(position).DeliveryCountryCode.equalsIgnoreCase("US"))
            holder.typ_.setText("Inter State");
        else if(tripList.get(position).PickupCountryCode.equalsIgnoreCase("CA") && tripList.get(position).DeliveryCountryCode.equalsIgnoreCase("US"))
        holder.typ_.setText("Export");
        else
            holder.typ_.setText("Import");

        holder.track.setText("");
        holder.track.setCompoundDrawablesWithIntrinsicBounds(null,null,context.getResources().getDrawable(R.mipmap.pending_trip),null);

        if(tripList.get(position).status.equalsIgnoreCase("Open")|| tripList.get(position).status.equalsIgnoreCase("Confirmed")) {
            holder.track.setVisibility(View.GONE);
        }
        else {
            holder.track.setVisibility(View.VISIBLE);
        }

        if(tripList.get(position).status.equalsIgnoreCase("DELETED") || tripList.get(position).status.equalsIgnoreCase("CANCELLED")){
            holder.fram_cancel.setVisibility(View.VISIBLE);
            holder.full_item.setClickable(false);
            holder.full_item.setEnabled(false);
            if(tripList.get(position).status.equalsIgnoreCase("DELETED"))
                holder.cancelation.setText("DELETED");
            else
                holder.cancelation.setText("CANCELLED");
        }else {
            holder.full_item.setClickable(true);
            holder.full_item.setEnabled(true);
            holder.fram_cancel.setVisibility(View.GONE);
        }
        holder.track.setVisibility(View.GONE);

        if(tripList.get(position).status.equalsIgnoreCase("QUOTED")) {
            if(tripList.get(position).vehiclecount.equalsIgnoreCase("0")){
                holder.full_item.setClickable(false);
                holder.full_item.setEnabled(false);
                if(tripList.get(position).PickupDateTime.equalsIgnoreCase("0001-01-01T00:00:00")){
                    holder.from_date.setText("");
                    holder.to_date.setText("");
                }

            }
        }

//        holder.full_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PreferenceManager prf = new PreferenceManager(context);
//                prf.saveStringData("OrderId",tripList.get(position).orderId);
//                prf.saveStringData("OrderType",tripList.get(position).orderType);
//                prf.saveStringData("OrderStatus",tripList.get(position).status);
////                Intent i=new Intent(context,VehicleListActivity.class);
//////                    i.putExtra("OrderStatus",tripList.get(position).status);
////                context.startActivity(i);
//                if(tripList.get(position).orderType.equalsIgnoreCase("Interprovincial")) {
//                    Intent j = new Intent(context, RouteSelectionActivity.class);
//                    j.putExtra("from", "1");
//                    j.putExtra("to", "1");
//                    j.putExtra("Id", tripList.get(position).orderId);
//                    context.startActivity(j);
//                }else if(tripList.get(position).orderType.equalsIgnoreCase("export")) {
//                    Intent j = new Intent(context, RouteSelectionActivity.class);
//                    j.putExtra("from", "1");
//                    j.putExtra("to", "4");
//                    j.putExtra("Id", tripList.get(position).orderId);
//                    context.startActivity(j);
//                }else if(tripList.get(position).orderType.equalsIgnoreCase("import")) {
//                    Intent j = new Intent(context, RouteSelectionActivity.class);
//                    j.putExtra("from", "4");
//                    j.putExtra("to", "1");
//                    j.putExtra("Id", tripList.get(position).orderId);
//                    context.startActivity(j);
//                }else if(tripList.get(position).orderType.equalsIgnoreCase("InterState")) {
//                    Intent j = new Intent(context, RouteSelectionActivity.class);
//                    j.putExtra("from", "4");
//                    j.putExtra("to", "4");
//                    j.putExtra("Id", tripList.get(position).orderId);
//                    context.startActivity(j);
//                }
//
//            }
//        });

//        holder.track.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(context,ViewRouteMapListActivity.class);
//                i.putExtra("OrderId",tripList.get(position).saveOrderId);
//                i.putExtra("from_",tripList.get(position).pickupState);
//                i.putExtra("status",holder.title.getText());
//                i.putExtra("vehicle_num",tripList.get(position).vehiclecount);
//                i.putExtra("type",tripList.get(position).orderType);
//                i.putExtra("from_address",tripList.get(position).pickupAddress+"\n"+tripList.get(position).pickupCity+"\n"+tripList.get(position).pickupZip);
//                i.putExtra("from_date",tripList.get(position).pickupDate);
//                i.putExtra("from_time",tripList.get(position).pickupTime);
//                i.putExtra("to_",tripList.get(position).dropState);
//                i.putExtra("to_address",tripList.get(position).dropAddress+"\n"+tripList.get(position).dropCity+"\n"+tripList.get(position).dropZip);
//                i.putExtra("to_date",tripList.get(position).dropDate);
//                i.putExtra("to_time",tripList.get(position).dropTime);
//
//
//
//                context.startActivity(i);
//            }
//        });

        holder.full_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    final Dialog settingsDialog = new Dialog(context);
                final BottomSheetDialog settingsDialog = new BottomSheetDialog(context);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(LayoutInflater.from(context).inflate(R.layout.bottom_sheet
                            , null));
                    settingsDialog.show();
                    TextView confirm=(TextView)settingsDialog.findViewById(R.id.confirm);
                TextView track=(TextView)settingsDialog.findViewById(R.id.track);
                TextView info=(TextView)settingsDialog.findViewById(R.id.info);
//                    ImageView image=(ImageView)settingsDialog.findViewById(R.id.image_url);
//                    Button ok=(Button)settingsDialog.findViewById(R.id.ok);
                if(!tripList.get(position).status.equalsIgnoreCase("Open"))
                {
                    confirm.setEnabled(false);
                    confirm.setClickable(false);
                }
                confirm.setEnabled(false);
                confirm.setClickable(false);
                confirm.setVisibility(View.INVISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    confirm.setCompoundDrawableTintList(ColorStateList.valueOf(Color.GRAY));
                }
                if(tripList.get(position).status.equalsIgnoreCase("Open")|| tripList.get(position).status.equalsIgnoreCase("Confirmed")) {
//                    track.setVisibility(View.GONE);
                    track.setClickable(false);
                    track.setEnabled(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        track.setCompoundDrawableTintList(ColorStateList.valueOf(Color.GRAY));
                    }
                    track.setVisibility(View.INVISIBLE);
                }
                else {
                    track.setVisibility(View.VISIBLE);
//                    track.setVisibility(View.VISIBLE);
                    track.setClickable(true);
                    track.setEnabled(true);
                }
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        settingsDialog.dismiss();
                        saveLoad(tripList.get(position).saveOrderId,  position);
                    }
                });
                info.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PreferenceManager prf = new PreferenceManager(context);
                            prf.saveStringData("OrderId",tripList.get(position).orderId);
                            prf.saveStringData("OrderType",tripList.get(position).orderType);
                            prf.saveStringData("OrderStatus",tripList.get(position).status);
//                Intent i=new Intent(context,VehicleListActivity.class);
////                    i.putExtra("OrderStatus",tripList.get(position).status);
//                context.startActivity(i);
                            settingsDialog.dismiss();

                            Intent j = new Intent(context, NewOrderViewActivity.class);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
//              j.putExtra("from", tripList.get(position).PickupCountryCode);
                                j.putExtra("OrderStatus", tripList.get(position).status);
                            j.putExtra("OrderId", tripList.get(position).orderId);

//              j.putExtra("ModelClass",gson);
                            String gson=new Gson().toJson(tripList.get(position));
                            j.putExtra("ModelClass",gson);
                            context.startActivity(j);
//                                Intent j = new Intent(context, RouteSelectionActivity.class);
//                                j.putExtra("from", tripList.get(position).PickupCountryCode);
//                                j.putExtra("to", tripList.get(position).DeliveryCountryCode);
//                                j.putExtra("Id", tripList.get(position).orderId);
//                                String gson=new Gson().toJson(tripList.get(position));
//                                j.putExtra("ModelClass",gson);
//                                context.startActivity(j);


                        }
                    });
                track.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        settingsDialog.dismiss();
                        Intent i=new Intent(context,ViewRouteMapListActivity.class);
                        i.putExtra("OrderId",tripList.get(position).orderId);
                        i.putExtra("from_",tripList.get(position).pickupState);
                        i.putExtra("status",holder.title.getText());
                        i.putExtra("vehicle_num",tripList.get(position).vehiclecount);
                        i.putExtra("type",tripList.get(position).orderType);
                        i.putExtra("from_address",tripList.get(position).pickupAddress+"\n"+tripList.get(position).pickupCity+"\n"+tripList.get(position).pickupZip);
                        i.putExtra("from_date",tripList.get(position).PickupDateTime);
                        i.putExtra("from_time","");
                        i.putExtra("to_",tripList.get(position).dropState);
                        i.putExtra("to_address",tripList.get(position).dropAddress+"\n"+tripList.get(position).dropCity+"\n"+tripList.get(position).dropZip);
                        i.putExtra("to_date",tripList.get(position).DropDateTime);
                        String gson=new Gson().toJson(tripList.get(position));
                        i.putExtra("ModelClass",gson);
                        context.startActivity(i);
                    }
                });


            }
        });

    }


    public DashboardOrderListAdapter(ArrayList<OrderListModel.datavalue1> tripList, Context context) {
        this.tripList = tripList;
        this.context=context;
        prf = new PreferenceManager(context);
        //        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public DashboardOrderListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_triplist_item, parent, false);

        return new DashboardOrderListAdapter.MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, from,to,title,from_address,to_address,vehicle_num,from_date,to_date,from_time,to_time,typ_,track,cancelation,pickup_name,delivery_name,order_lbl,temp_order_id;
        LinearLayout full_item,temp_li;
        FrameLayout fram_cancel;
        View view1;
//        public ImageView typeicon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.status);
            id = (TextView) view.findViewById(R.id.order_id);
            from=(TextView)view.findViewById(R.id._from);
            to=(TextView)view.findViewById(R.id._to);
            from_address=(TextView)view.findViewById(R.id.from_address);
            to_address=(TextView)view.findViewById(R.id.to_address);
            vehicle_num=(TextView)view.findViewById(R.id.vehicle_num);
            full_item=(LinearLayout)view.findViewById(R.id.full_item1);
            from_date=(TextView)view.findViewById(R.id.from_date);
            to_date=(TextView)view.findViewById(R.id.to_date);
            from_time=(TextView)view.findViewById(R.id.from_time);
            to_time=(TextView)view.findViewById(R.id.to_time);
            typ_=(TextView)view.findViewById(R.id.type_);
            track=(TextView)view.findViewById(R.id.track);
            cancelation=(TextView)view.findViewById(R.id.cancelation);
            fram_cancel=(FrameLayout)view.findViewById(R.id.fram_cancel);
            pickup_name=(TextView)view.findViewById(R.id.pickup_name);
            delivery_name=(TextView)view.findViewById(R.id.delivery_name);
            order_lbl=(TextView) view.findViewById(R.id.order_lbl);
            view1=(View)view.findViewById(R.id.view1);
            temp_order_id=(TextView)view.findViewById(R.id.temp_order_id);
            temp_li=(LinearLayout)view.findViewById(R.id.temp_li);

        }
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }


    void saveLoad(String OrderId, final int position) {
        ConfirmOrderModel vindetail = new ConfirmOrderModel(prf.getStringData("authKey"),prf.getStringData("carrierPrimaryId"), OrderId);
        Call<ConfirmOrderModel> call1 = apiInterface.confirOrder(vindetail);
        call1.enqueue(new Callback<ConfirmOrderModel>() {
            @Override
            public void onResponse(Call<ConfirmOrderModel> call, Response<ConfirmOrderModel> response) {

                ConfirmOrderModel getdata = response.body();
                try {
                    if (getdata.status != null) {
                        if (getdata.status) {
                            MDToast mdToast = MDToast.makeText(context, "Load Confirmed successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                            tripList.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail((Activity) context,null,"CxE004",e,"");
                }
            }

            @Override
            public void onFailure(Call<ConfirmOrderModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail((Activity) context,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

}
