package com.avaal.com.afm2020autoCx.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.RouteSelectionActivity;
import com.avaal.com.afm2020autoCx.VehicleListActivity;
import com.avaal.com.afm2020autoCx.ViewRouteMapListActivity;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdModel;
import com.avaal.com.afm2020autoCx.models.OrderListModel;
import com.avaal.com.afm2020autoCx.models.RemoveOrderModel;

import com.google.gson.Gson;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;


import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 16-03-2018.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private ArrayList<OrderListModel.datavalue1> tripList;
    private Context context;
    APIInterface apiInterface;
    Boolean isSet=false;
    PreferenceManager prf;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, from,to,title,from_address,to_address,vehicle_num,from_date,to_date,from_time,to_time,typ_,track,cancelation,pickup_name,delivery_name;
        LinearLayout full_item;
        FrameLayout fram_cancel;
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
//            expand=(TextView) view.findViewById(R.id.expand);

        }
    }


    public OrderListAdapter(ArrayList<OrderListModel.datavalue1> tripList, Context context) {
        this.tripList = tripList;
        this.context=context;

        //        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public OrderListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_triplist_item, parent, false);

        return new OrderListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderListAdapter.MyViewHolder holder, final int position) {

//        holder.tripStatus.setCompoundDrawableTintList(ColorStateList.);
//        holder.date.setText(String.valueOf(movie.getDeliverydate()));
//        Picasso.with(context).load("http://www.car-logos.org/wp-content/uploads/2011/09/"+(tripList.get(position).makeV).toLowerCase()+".png").into(holder.typeicon);
        if(!tripList.get(position).orderId.trim().equalsIgnoreCase("0"))
        holder.id.setText(tripList.get(position).orderId);
        else
            holder.id.setText(tripList.get(position).SavedOrderNumber);


//        if(tripList.get(position).orderId.length()>0)
//            holder.title.setVisibility(View.GONE);
      if(tripList.get(position).status.equalsIgnoreCase(""))
        holder.title.setText("SAVED");
      else
          holder.title.setText(tripList.get(position).status.toUpperCase());

        if(tripList.get(position).status.equalsIgnoreCase("Saved"))
            holder.title.setTextColor(context.getResources().getColor(R.color.textPrimaryDark));
        else
            holder.title.setTextColor(context.getResources().getColor(R.color.blue));

        holder.from.setText(tripList.get(position).pickupCity+", "+tripList.get(position).pickupstateCode+", "+tripList.get(position).PickupCountryCode);
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
        holder.pickup_name.setText(tripList.get(position).PickupName);
        holder.delivery_name.setText(tripList.get(position).dropName);
if(tripList.get(position).orderType!=null) {
    if (tripList.get(position).orderType.equalsIgnoreCase("Interprovincial"))
        holder.typ_.setText("Inter Provincial");
    else if (tripList.get(position).orderType.equalsIgnoreCase("Interstate"))
        holder.typ_.setText("Inter State");
    else
        holder.typ_.setText(tripList.get(position).orderType);
}
        if(tripList.get(position).status.equalsIgnoreCase("DELETED") || tripList.get(position).status.equalsIgnoreCase("CANCEL")){
            holder.fram_cancel.setVisibility(View.VISIBLE);
            if(tripList.get(position).status.equalsIgnoreCase("DELETED"))
                holder.cancelation.setText("DELETED");
            else
                holder.cancelation.setText("CANCELLED");
        }else
            holder.fram_cancel.setVisibility(View.GONE);
         holder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this?");
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
                        removeLoad(tripList.get(position).orderId,position);
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
        });

                    holder.track.setVisibility(View.GONE);
                    holder.track.setText("");
                    holder.track.setCompoundDrawablesWithIntrinsicBounds(null,null,context.getResources().getDrawable(R.mipmap.delete1),null);


//    holder.full_item.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//             prf = new PreferenceManager(context);
//            prf.saveStringData("OrderStatus","save");
////            prf.saveStringData("OrderId",tripList.get(position).orderId);
////            prf.saveStringData("OrderType",tripList.get(position).orderType);
////            prf.saveStringData("OrderStatus","save");
////            prf.saveStringData("When","add");
////            Intent i=new Intent(context,VehicleListActivity.class);
//////                    i.putExtra("VehicleId",getRespone.data.temVehicleId);
////            context.startActivity(i);
//            if(tripList.get(position).orderType.equalsIgnoreCase("Interprovincial")) {
//                Intent j = new Intent(context, RouteSelectionActivity.class);
//////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
////                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
//                j.putExtra("from", "1");
//                j.putExtra("to", "1");
//                j.putExtra("Id", tripList.get(position).orderId);
//                context.startActivity(j);
//            }else if(tripList.get(position).orderType.equalsIgnoreCase("export")) {
//                Intent j = new Intent(context, RouteSelectionActivity.class);
//////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
////                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
//                j.putExtra("from", "1");
//                j.putExtra("to", "4");
//                j.putExtra("Id", tripList.get(position).orderId);
//                context.startActivity(j);
//            }else if(tripList.get(position).orderType.equalsIgnoreCase("import")) {
//                Intent j = new Intent(context, RouteSelectionActivity.class);
//////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
////                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
//                j.putExtra("from", "4");
//                j.putExtra("to", "1");
//                j.putExtra("Id", tripList.get(position).orderId);
//                context.startActivity(j);
//            }else if(tripList.get(position).orderType.equalsIgnoreCase("InterState")) {
//                Intent j = new Intent(context, RouteSelectionActivity.class);
//////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
////                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
//                j.putExtra("from", "4");
//                j.putExtra("to", "4");
//                j.putExtra("Id", tripList.get(position).orderId);
//                context.startActivity(j);
//            }
////            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//        }
//    });

        holder.full_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//

                forsaveStatusOrderOption(position,tripList.get(position).status);



            }
        });




    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

  void  forsaveStatusOrderOption(final int position,String Type){

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
      confirm.setText("Ship Load");
//                confirm.setCompoundDrawablesWithIntrinsicBounds(null,context.getResources().getDrawable(R.mipmap.delete1),null,null);
//                if(!tripList.get(position).status.equalsIgnoreCase("Open"))
//                {
//                    confirm.setEnabled(false);
//                    confirm.setClickable(false);
//                }
      track.setCompoundDrawablesWithIntrinsicBounds(null,context.getResources().getDrawable(R.mipmap.delete),null,null);
      track.setText("Delete");
      if(!Type.equalsIgnoreCase("Saved")){
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
              confirm.setCompoundDrawableTintList(ColorStateList.valueOf(Color.GRAY));

              confirm.setEnabled(false);
              confirm.setClickable(false);
              track.setEnabled(true);
              track.setClickable(true);
//              track.setCompoundDrawableTintList(ColorStateList.valueOf(Color.GRAY));
          }else{
              confirm.setEnabled(false);
              confirm.setClickable(false);
              track.setEnabled(true);
              track.setClickable(true);
          }
      }

//                if(tripList.get(position).status.equalsIgnoreCase("Open")|| tripList.get(position).status.equalsIgnoreCase("Confirmed")) {
////                    track.setVisibility(View.GONE);
//                    track.setClickable(false);
//                    track.setEnabled(false);
//                }
//                else {
////                    track.setVisibility(View.VISIBLE);
//                    track.setClickable(true);
//                    track.setEnabled(true);
//                }
      track.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              settingsDialog.dismiss();
              AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
              // Setting Dialog Message
              alertDialog.setMessage("Are you sure you want delete this?");
              // Setting Positive "Yes" Button
              alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {

                      // Write your code here to invoke YES event
                      removeLoad(tripList.get(position).orderId,position);
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
      });
      info.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
//                        PreferenceManager prf = new PreferenceManager(context);
              prf = new PreferenceManager(context);
              prf.saveStringData("OrderStatus",tripList.get(position).status);
              settingsDialog.dismiss();
//            prf.saveStringData("OrderId",tripList.get(position).orderId);
//            prf.saveStringData("OrderType",tripList.get(position).orderType);
//            prf.saveStringData("OrderStatus","save");
//            prf.saveStringData("When","add");
//            Intent i=new Intent(context,VehicleListActivity.class);
////                    i.putExtra("VehicleId",getRespone.data.temVehicleId);
//            context.startActivity(i);
                  Intent j = new Intent(context, RouteSelectionActivity.class);
////        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                j.putExtra("AuthKey", getIntent().getStringExtra("AuthKey"));
                  j.putExtra("from", tripList.get(position).PickupCountryCode);
                  j.putExtra("to", tripList.get(position).DeliveryCountryCode);
                  j.putExtra("Id", tripList.get(position).orderId);
                  String gson=new Gson().toJson(tripList.get(position));
                  j.putExtra("ModelClass",gson);
                  context.startActivity(j);

          }
      });
      confirm.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
              // Setting Dialog Message
              alertDialog.setMessage("Do you want to ship the load?");
              // Setting Positive "Yes" Button
              alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {

                      // Write your code here to invoke YES event
                      try {
                          saveLoad(tripList.get(position).orderId, position);
                      } catch (Exception e) {
                          e.printStackTrace();
                          new Util().sendSMTPMail((Activity) context,null,"CxE004",e,"");
                      }
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

              settingsDialog.dismiss();


//                        Intent i=new Intent(context,ViewRouteMapListActivity.class);
//                        i.putExtra("OrderId",tripList.get(position).saveOrderId);
//                        i.putExtra("from_",tripList.get(position).pickupState);
//                        i.putExtra("status",holder.title.getText());
//                        i.putExtra("vehicle_num",tripList.get(position).vehiclecount);
//                        i.putExtra("type",tripList.get(position).orderType);
//                        i.putExtra("from_address",tripList.get(position).pickupAddress+"\n"+tripList.get(position).pickupCity+"\n"+tripList.get(position).pickupZip);
//                        i.putExtra("from_date",tripList.get(position).pickupDate);
//                        i.putExtra("from_time",tripList.get(position).pickupTime);
//                        i.putExtra("to_",tripList.get(position).dropState);
//                        i.putExtra("to_address",tripList.get(position).dropAddress+"\n"+tripList.get(position).dropCity+"\n"+tripList.get(position).dropZip);
//                        i.putExtra("to_date",tripList.get(position).dropDate);
//                        i.putExtra("to_time",tripList.get(position).dropTime);
//                        context.startActivity(i);
          }
      });



  }
//    void forShipStatusOrderOption(final int position){
//        final BottomSheetDialog settingsDialog = new BottomSheetDialog(context);
//        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        settingsDialog.setContentView(LayoutInflater.from(context).inflate(R.layout.bottom_sheet
//                , null));
//        settingsDialog.show();
//        TextView confirm=(TextView)settingsDialog.findViewById(R.id.confirm);
//        TextView track=(TextView)settingsDialog.findViewById(R.id.track);
//        TextView info=(TextView)settingsDialog.findViewById(R.id.info);
////                    ImageView image=(ImageView)settingsDialog.findViewById(R.id.image_url);
////                    Button ok=(Button)settingsDialog.findViewById(R.id.ok);
//        if(!tripList.get(position).status.equalsIgnoreCase("Open"))
//        {
//            confirm.setEnabled(false);
//            confirm.setClickable(false);
//        }
//        confirm.setEnabled(false);
//        confirm.setClickable(false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            confirm.setCompoundDrawableTintList(ColorStateList.valueOf(Color.GRAY));
//        }
//        if(tripList.get(position).status.equalsIgnoreCase("Open")|| tripList.get(position).status.equalsIgnoreCase("Confirmed")) {
////                    track.setVisibility(View.GONE);
//            track.setClickable(false);
//            track.setEnabled(false);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                track.setCompoundDrawableTintList(ColorStateList.valueOf(Color.GRAY));
//            }
//        }
//        else {
////                    track.setVisibility(View.VISIBLE);
//            track.setClickable(true);
//            track.setEnabled(true);
//        }
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                settingsDialog.dismiss();
////                saveLoad(tripList.get(position).saveOrderId,  position);
//            }
//        });
//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PreferenceManager prf = new PreferenceManager(context);
//                prf.saveStringData("OrderId",tripList.get(position).orderId);
//                prf.saveStringData("OrderType",tripList.get(position).orderType);
//                prf.saveStringData("OrderStatus",tripList.get(position).status);
////                Intent i=new Intent(context,VehicleListActivity.class);
//////                    i.putExtra("OrderStatus",tripList.get(position).status);
////                context.startActivity(i);
//                settingsDialog.dismiss();
//                if(tripList.get(position).orderType.equalsIgnoreCase("Interprovincial")) {
//                    Intent j = new Intent(context, RouteSelectionActivity.class);
//                    j.putExtra("from", "CA");
//                    j.putExtra("to", "CA");
//                    j.putExtra("Id", tripList.get(position).orderId);
//                    context.startActivity(j);
//                }else if(tripList.get(position).orderType.equalsIgnoreCase("export")) {
//                    Intent j = new Intent(context, RouteSelectionActivity.class);
//                    j.putExtra("from", "CA");
//                    j.putExtra("to", "US");
//                    j.putExtra("Id", tripList.get(position).orderId);
//                    context.startActivity(j);
//                }else if(tripList.get(position).orderType.equalsIgnoreCase("import")) {
//                    Intent j = new Intent(context, RouteSelectionActivity.class);
//                    j.putExtra("from", "US");
//                    j.putExtra("to", "CA");
//                    j.putExtra("Id", tripList.get(position).orderId);
//                    context.startActivity(j);
//                }else if(tripList.get(position).orderType.equalsIgnoreCase("InterState")) {
//                    Intent j = new Intent(context, RouteSelectionActivity.class);
//                    j.putExtra("from", "US");
//                    j.putExtra("to", "US");
//                    j.putExtra("Id", tripList.get(position).orderId);
//                    context.startActivity(j);
//                }
//
//
//            }
//        });
//        track.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                settingsDialog.dismiss();
//                Intent i=new Intent(context,ViewRouteMapListActivity.class);
//                i.putExtra("OrderId",tripList.get(position).saveOrderId);
//                i.putExtra("from_",tripList.get(position).pickupState);
//                i.putExtra("status",holder.title.getText());
//                i.putExtra("vehicle_num",tripList.get(position).vehiclecount);
//                i.putExtra("type",tripList.get(position).orderType);
//                i.putExtra("from_address",tripList.get(position).pickupAddress+"\n"+tripList.get(position).pickupCity+"\n"+tripList.get(position).pickupZip);
//                i.putExtra("from_date",tripList.get(position).PickupDateTime);
//                i.putExtra("from_time","");
//                i.putExtra("to_",tripList.get(position).dropState);
//                i.putExtra("to_address",tripList.get(position).dropAddress+"\n"+tripList.get(position).dropCity+"\n"+tripList.get(position).dropZip);
//                i.putExtra("to_date",tripList.get(position).DropDateTime);
//                i.putExtra("to_time","");
//                context.startActivity(i);
//            }
//        });
//
//
//    }
    void removeLoad(String tempOrderId, final int position) {
        prf = new PreferenceManager(context);

        Call<RemoveOrderModel> call1 = apiInterface.removeOrder(tempOrderId,""+ prf.getStringData("userCode"),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<RemoveOrderModel>() {
            @Override
            public void onResponse(Call<RemoveOrderModel> call, Response<RemoveOrderModel> response) {

                RemoveOrderModel getdata = response.body();
                if(getdata.status!=null) {
                    if (getdata.status) {
                        MDToast mdToast = MDToast.makeText(context, "Order has been deleted successfully. ", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                        tripList.remove(position);
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<RemoveOrderModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail((Activity) context,t,"CxE001",null,""+call.request().url());
            }
        });
    }
    void saveLoad(final String OrderId, final int position)throws Exception {
        prf = new PreferenceManager(context);
//        GetVehicleIdModel vindetail=new GetVehicleIdModel(prf.getStringData("authKey"),OrderId,prf.getStringData("carrierPrimaryId"));
        Call<GetVehicleIdModel> call1 = apiInterface.shipLoads(OrderId,""+ prf.getStringData("userName"),new Util().getDateTime(),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/x-www-form-urlencoded");

        call1.enqueue(new Callback<GetVehicleIdModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdModel> call, Response<GetVehicleIdModel> response) {

                GetVehicleIdModel getdata = response.body();
                try {
                    if (getdata.satus) {
                        new Util().sendAlert((Activity) context,prf.getStringData("userName")+" has placed an order having "+" vehicle(s) with Ref no "+OrderId+".","Shipped");
                        MDToast mdToast = MDToast.makeText(context, "Your load has been shipped to Carrier", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
                        tripList.get(position).status="Shipped";
//                        tripList.remove(position);
                        notifyDataSetChanged();
//                        Intent j = new Intent(context, DashBoardBottomMenu.class);
////             overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//                        j.putExtra("open", "ship");
//                        context.startActivity(j);
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    } else {
                        MDToast mdToast = MDToast.makeText(context, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail((Activity) context,null,"CxE004",e,"");
                }
            }
            @Override
            public void onFailure(Call<GetVehicleIdModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail((Activity) context,t,"CxE001",null,""+call.request().url());
            }
        });
    }
}
