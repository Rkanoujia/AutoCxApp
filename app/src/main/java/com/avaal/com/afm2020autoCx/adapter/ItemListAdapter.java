package com.avaal.com.afm2020autoCx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.models.ItemListModel;
import com.github.xizzhu.simpletooltip.ToolTip;
import com.github.xizzhu.simpletooltip.ToolTipView;

import java.util.ArrayList;

import extra.PreferenceManager;
import extra.Util;

/**
 * Created by dell pc on 31-05-2018.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {

    private ArrayList<ItemListModel.ItemDetail> tripList;
    private Context context;
    PreferenceManager prf;
    APIInterface apiInterface;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView vin_num1, status,info_,delivery_date,pickup_date;


        public MyViewHolder(View view) {
            super(view);
            vin_num1 = (TextView) view.findViewById(R.id.vin_num1);
            status = (TextView) view.findViewById(R.id.status);
            info_=(TextView)view.findViewById(R.id.info_);
            pickup_date=(TextView)view.findViewById(R.id.pickup_date);
            delivery_date=(TextView)view.findViewById(R.id.delivery_date);


        }
    }


    public ItemListAdapter(ArrayList<ItemListModel.ItemDetail> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
        prf = new PreferenceManager(context);
        //        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public ItemListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_item, parent, false);

        return new ItemListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemListAdapter.MyViewHolder holder, final int position) {

//        holder.tripStatus.setCompoundDrawableTintList(ColorStateList.);
//        holder.date.setText(String.valueOf(movie.getDeliverydate()));
//        if(!tripList.get(position).makeV.equalsIgnoreCase("null"))
//        String[] logo=(tripList.get(position).makeV).toLowerCase().split(" ");
        try {
       holder.vin_num1.setText(tripList.get(position).itemVin);
       if(tripList.get(position).DeliveryDate!=null && !tripList.get(position).DeliveryDate.equalsIgnoreCase("0001-01-01T00:00:00"))
            holder.delivery_date.setText(new Util().getUtcToCurrentTime(tripList.get(position).DeliveryDate));
       else
           holder.delivery_date.setText("");

            if(tripList.get(position).PickupDate!=null && !tripList.get(position).PickupDate.equalsIgnoreCase("0001-01-01T00:00:00"))
            holder.pickup_date.setText(new Util().getUtcToCurrentTime(tripList.get(position).PickupDate));
            else
                holder.pickup_date.setText("");
//       if(tripList.get(position).itemDrop.equalsIgnoreCase("Yes")) {
//           holder.status.setText("Dropped");
//           holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//           holder.info_.setVisibility(View.VISIBLE);
//
//       }
//       else {
           holder.status.setTextColor(context.getResources().getColor(R.color.colorAccent));
           holder.info_.setVisibility(View.INVISIBLE);
           holder.status.setText(tripList.get(position).itemStatus);
//       }

        }catch (Exception e){
            e.printStackTrace();
        }
        holder.info_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                // Setting Dialog Message
//                alertDialog.setMessage(tripList.get(position).itemMessage);
//                // Setting Positive "Yes" Button
//                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        // Write your code here to invoke YES event
////                        saveLoad(tripList.get(position).vehiocleId, position);
//                    }
//                });
//
////                // Setting Negative "NO" Button
////                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int which) {
////                        // Write your code here to invoke NO event
////                        dialog.cancel();
////                    }
////                });
//
//                // Showing Alert Message
//                alertDialog.show();

                ToolTip toolTip = new ToolTip.Builder()
                        .withText("  "+tripList.get(position).itemMessage+"  ")
                        .withTextSize(45)
                        .build();
                ToolTipView toolTipView = new ToolTipView.Builder(context)
                        .withAnchor(view)
                        .withToolTip(toolTip)
                        .withGravity(Gravity.LEFT)
                        .build();
                toolTipView.show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }



}