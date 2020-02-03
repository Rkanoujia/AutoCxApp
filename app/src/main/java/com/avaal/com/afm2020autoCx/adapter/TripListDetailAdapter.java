package com.avaal.com.afm2020autoCx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.InventoryVehicleListActivity;
import com.avaal.com.afm2020autoCx.MapsActivity;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.models.TripListModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;


/**
 * Created by dell pc on 29-03-2018.
 */

public class TripListDetailAdapter extends RecyclerView.Adapter<TripListDetailAdapter.MyViewHolder> {

    private ArrayList<TripListModel.TripDetail> tripList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView trip_status, trip_no,truck_no,first_driver,first_trailer,carrier_name,to_date,view_map,current_location;
        LinearLayout not_carrier,linear_carrier;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            trip_status = (TextView) view.findViewById(R.id.trip_status);
            trip_no = (TextView) view.findViewById(R.id.trip_no);
            truck_no=(TextView)view.findViewById(R.id.truck_no);
            first_driver=(TextView)view.findViewById(R.id.first_driver);
            first_trailer=(TextView)view.findViewById(R.id.first_trailer);
            carrier_name=(TextView)view.findViewById(R.id.carrier_name);
            linear_carrier=(LinearLayout)view.findViewById(R.id.linear_carrier);
            not_carrier=(LinearLayout)view.findViewById(R.id.not_carrier);
            current_location=(TextView)view.findViewById(R.id.addresss);
//            tracks=(LinearLayout)view.findViewById(R.id.tracks);
//            view_map=(TextView)view.findViewById(R.id.view_map);
            image=(ImageView)view.findViewById(R.id.image);

        }
    }


    public TripListDetailAdapter(ArrayList<TripListModel.TripDetail> tripList, Context context) {
        this.tripList = tripList;
        this.context=context;
    }

    @Override
    public TripListDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list_item, parent, false);

        return new TripListDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TripListDetailAdapter.MyViewHolder holder, final int position) {

//        holder.tripStatus.setCompoundDrawableTintList(ColorStateList.);
//        holder.date.setText(String.valueOf(movie.getDeliverydate()));
//        Picasso.with(context).load("http://www.car-logos.org/wp-content/uploads/2011/09/"+(tripList.get(position).makeV).toLowerCase()+".png").into(holder.typeicon);
        if(tripList.get(position).tripStatus!=null)
        holder.trip_status.setText(tripList.get(position).tripStatus);
        holder. trip_no.setText("Trip# "+tripList.get(position).tripNumber);


        if(tripList.get(position).carrierName==null || tripList.get(position).carrierName.equalsIgnoreCase("")) {
            holder.truck_no.setText(tripList.get(position).truckNumber);
            holder.linear_carrier.setVisibility(View.GONE);
            if (tripList.get(position).secondDriver == null || tripList.get(position).secondDriver.equalsIgnoreCase("NA"))
                holder.first_driver.setText(tripList.get(position).firstDriver);
            else
                holder.first_driver.setText(tripList.get(position).firstDriver + "/\n" + tripList.get(position).secondDriver);
            if (tripList.get(position).secondTrailer == null || tripList.get(position).secondTrailer.equalsIgnoreCase("NA"))
                holder.first_trailer.setText(tripList.get(position).firstTrailer);
            else
                holder.first_trailer.setText(tripList.get(position).firstTrailer + "/\n" + tripList.get(position).secondTrailer);
            if(tripList.get(position).firstDriver==null)
                holder.not_carrier.setVisibility(View.INVISIBLE);
        }else{
            holder.not_carrier.setVisibility(View.GONE);
            holder.linear_carrier.setVisibility(View.VISIBLE);
            holder.carrier_name.setText(tripList.get(position).carrierName);
        }

//         if(tripList.get(position).carrierAddress!=null){
            if(tripList.get(position).carrierLongitude!=null && !tripList.get(position).carrierLongitude.equalsIgnoreCase("0.0")){
//                holder.tracks.setVisibility(View.VISIBLE);
//                holder.current_location.setText("Current Location :"+tripList.get(position).carrierAddress);
//                String url = "http://maps.google.com/maps/api/staticmap?center=" + tripList.get(position).carrierLatitude + "," + tripList.get(position).carrierLongitude + "&zoom=15&size=200x200&sensor=false";
//                String url="http://maps.google.com/maps/api/staticmap?markers=color:green%7C" + tripList.get(position).carrierLatitude + "," + tripList.get(position).carrierLongitude +"&zoom=19&size=5000x400&sensor=true";
////                String url="https://maps.googleapis.com/maps/api/staticmap?center="+tripList.get(position).carrierLatitude+","+tripList.get(position).carrierLongitude +"&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C"+40.702147,-74.015794+"&key="+R.string.google_maps_key;
//
//                Picasso.with(context).load(url).into(holder.image);
                if(tripList.get(position).carrierAddress!=null && !tripList.get(position).carrierAddress.equalsIgnoreCase("null"))
                holder.current_location.setText(tripList.get(position).carrierAddress);
                holder.current_location.setVisibility(View.VISIBLE);

            }else
            holder.current_location.setVisibility(View.INVISIBLE);

//         }
//        String url="http://maps.google.com/maps/api/staticmap?markers=color:brown%7C" + tripList.get(position).carrierLatitude + "," + tripList.get(position).carrierLongitude +"&zoom=15&size=500x200&sensor=true";
////                String url="https://maps.googleapis.com/maps/api/staticmap?center="+tripList.get(position).carrierLatitude+","+tripList.get(position).carrierLongitude +"&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C"+40.702147,-74.015794+"&key="+R.string.google_maps_key;
//
//        Picasso.with(context).load(url).into(holder.image);

        holder.current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tripList.get(position).carrierAddress.trim().length()!=0) {
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("lat", tripList.get(position).carrierLatitude);
                    intent.putExtra("lon", tripList.get(position).carrierLongitude);
                    intent.putExtra("Add", "" + tripList.get(position).carrierAddress);
                    context.startActivity(intent);
                }else{
                    MDToast mdToast = MDToast.makeText(context, "Location Not Found", MDToast.LENGTH_LONG, MDToast.TYPE_INFO);
                    mdToast.show();
                }
            }
        });
        if(tripList.get(position).tripStatus!=null)
        if(tripList.get(position).tripStatus.equalsIgnoreCase("TripStarted"))
            holder.trip_status.setTextColor(context.getResources().getColor(R.color.blue));
            else  if(tripList.get(position).tripStatus.equalsIgnoreCase("Dispatched"))
            holder.trip_status.setTextColor(context.getResources().getColor(R.color.dot_inactive_screen1));
                else  if(tripList.get(position).tripStatus.equalsIgnoreCase("Enroute"))
            holder.trip_status.setTextColor(context.getResources().getColor(R.color.enroute));
                    else  if(tripList.get(position).tripStatus.equalsIgnoreCase("Delivered"))
            holder.trip_status.setTextColor(context.getResources().getColor(R.color.dot_inactive_screen2));


    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }




}