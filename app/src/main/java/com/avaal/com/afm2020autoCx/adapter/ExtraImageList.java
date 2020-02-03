package com.avaal.com.afm2020autoCx.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.models.RemoveImageModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;


import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 26-04-2018.
 */

public class ExtraImageList extends RecyclerView.Adapter<ExtraImageList.MyViewHolder> {

    private List<Map<String , String>> tripList;
    private Context context;
    PreferenceManager prf;
    APIInterface apiInterface;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name1;
        LinearLayout not_carrier,linear_carrier;
        public ImageView dam_img;

        public MyViewHolder(View view) {
            super(view);
            dam_img = (ImageView) view.findViewById(R.id.dam_img);
            name1 = (TextView) view.findViewById(R.id.name1);

//            first_driver=(TextView)view.findViewById(R.id.first_driver);
//            first_trailer=(TextView)view.findViewById(R.id.first_trailer);
//            carrier_name=(TextView)view.findViewById(R.id.carrier_name);
//            linear_carrier=(LinearLayout)view.findViewById(R.id.linear_carrier);
//            not_carrier=(LinearLayout)view.findViewById(R.id.not_carrier);
//            current_location=(TextView)view.findViewById(R.id.addresss);
////            tracks=(LinearLayout)view.findViewById(R.id.tracks);
////            view_map=(TextView)view.findViewById(R.id.view_map);
//            image=(ImageView)view.findViewById(R.id.image);

        }
    }


    public ExtraImageList(List<Map<String , String>>  tripList, Context context) {
        this.tripList = tripList;
        this.context=context;
        prf = new PreferenceManager(context);
        //        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public ExtraImageList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_item, parent, false);

        return new ExtraImageList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExtraImageList.MyViewHolder holder, final int position) {

//        holder.tripStatus.setCompoundDrawableTintList(ColorStateList.);
//        holder.date.setText(String.valueOf(movie.getDeliverydate()));
//        Picasso.with(context).load("http://www.car-logos.org/wp-content/uploads/2011/09/"+(tripList.get(position).makeV).toLowerCase()+".png").into(holder.typeicon);

//         if(tripList.get(position).carrierAddress!=null){

//                holder.current_location.setText("Current Location :"+tripList.get(position).carrierAddress);
//                String url = "http://maps.google.com/maps/api/staticmap?center=" + tripList.get(position).carrierLatitude + "," + tripList.get(position).carrierLongitude + "&zoom=15&size=200x200&sensor=false";
            final String url=(tripList.get(position).get("imageUrl")).replaceAll(" ","%20").replace("DelhiServer","192.168.1.20");
//                String url="https://maps.googleapis.com/maps/api/staticmap?center="+tripList.get(position).carrierLatitude+","+tripList.get(position).carrierLongitude +"&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C"+40.702147,-74.015794+"&key="+R.string.google_maps_key;
           holder.name1.setText(tripList.get(position).get("imageName"));
//            Picasso.with(context).load(url).into(holder.dam_img);

        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .crossFade()
                .into(holder.dam_img);
           holder.dam_img.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   popUp(url,tripList.get(position).get("imageName"),position,tripList.get(position).get("imageId"));
               }
           });


//         }
//        String url="http://maps.google.com/maps/api/staticmap?markers=color:brown%7C" + tripList.get(position).carrierLatitude + "," + tripList.get(position).carrierLongitude +"&zoom=15&size=500x200&sensor=true";
////                String url="https://maps.googleapis.com/maps/api/staticmap?center="+tripList.get(position).carrierLatitude+","+tripList.get(position).carrierLongitude +"&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C"+40.702147,-74.015794+"&key="+R.string.google_maps_key;
//
//        Picasso.with(context).load(url).into(holder.image);


    }

    void popUp(String url, String name, final int pos, final String id){
        Log.e("url",url);
        final Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(LayoutInflater.from(context).inflate(R.layout.image_layout_view
                , null));
        settingsDialog.show();
        ImageView image=(ImageView)settingsDialog.findViewById(R.id.image_url1);
        TextView header=(TextView)settingsDialog.findViewById(R.id.header);
        ImageView delete=(ImageView)settingsDialog.findViewById(R.id.delete11);
        Button ok=(Button)settingsDialog.findViewById(R.id.ok1);
        header.setText(name);
        Picasso.with(context).load(url).error(R.drawable.ic_camera).into(image);
        if (!prf.getStringData("OrderStatus").equalsIgnoreCase("Saved"))
            delete.setVisibility(View.GONE);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this?");
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
                        deleteImg(id,  pos);
                        settingsDialog.dismiss();

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

    }
    @Override
    public int getItemCount() {
        return tripList.size();
    }

void deleteImg(String id, final int position){
//    RemoveImageModel vindetail = new RemoveImageModel(prf.getStringData("authKey"), id);
    Call<RemoveImageModel> call1 = apiInterface.deleteImage(id,""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
    call1.enqueue(new Callback<RemoveImageModel>() {
        @Override
        public void onResponse(Call<RemoveImageModel> call, Response<RemoveImageModel> response) {

            RemoveImageModel getdata = response.body();
            if(getdata.status!=null) {
                if (getdata.status) {
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
        public void onFailure(Call<RemoveImageModel> call, Throwable t) {
            call.cancel();
            new Util().sendSMTPMail((Activity) context,t,"CxE001",null,""+call.request().url().toString());
        }
    });
}


}