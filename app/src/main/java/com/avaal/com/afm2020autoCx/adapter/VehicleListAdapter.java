package com.avaal.com.afm2020autoCx.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.AddImageActivity;
import com.avaal.com.afm2020autoCx.AddVehicleActivity;
import com.avaal.com.afm2020autoCx.InventoryVehicleListActivity;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.RemoveVehicleModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dell pc on 16-03-2018.
 */

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.MyViewHolder> {

    private ArrayList<GetVehicleIdListModel.datavalue> tripList;
    private Context context;
    PreferenceManager prf;
    APIInterface apiInterface;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, make, model, delete_,is_preinspec,view_details,damage;
        public ImageView typeicon;
        public LinearLayout list_item;
        FrameLayout item;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.vin_num);
            make = (TextView) view.findViewById(R.id.make_v);
            model = (TextView) view.findViewById(R.id.model_v);
            delete_ = (TextView) view.findViewById(R.id.delete_);
            typeicon = (ImageView) view.findViewById(R.id.vehicle_img);
            list_item = (LinearLayout) view.findViewById(R.id.list_item);
            item=(FrameLayout)view.findViewById(R.id.item);
            is_preinspec=(TextView)view.findViewById(R.id.is_preinspec);
            damage=(TextView)view.findViewById(R.id.damage);
            view_details=(TextView)view.findViewById(R.id.view_details);

        }
    }


    public VehicleListAdapter(ArrayList<GetVehicleIdListModel.datavalue> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
        prf = new PreferenceManager(context);
        //        orderType=prf.getStringData("OrderType");
//         vehicleId=getIntent().getStringExtra("VehicleId");
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventry_vehicle_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

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

                if(tripList.size()==1)
                    holder.delete_.setVisibility(View.GONE);
            if(tripList.get(position).IsPreInspectionDone.equalsIgnoreCase("1")){

                holder.is_preinspec.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
                holder.is_preinspec.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
            }else{
                holder.is_preinspec.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                holder.is_preinspec.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
            }
            if(tripList.get(position).ExtraCount.equalsIgnoreCase("0"))
                holder.damage.setText("No");
            else
                holder.damage.setText(tripList.get(position).ExtraCount);
//                if(tripList.get(position).isInventory)
//                    holder.item.setBackgroundTintList();

            if (!prf.getStringData("OrderStatus").equalsIgnoreCase("saved") && !prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")) {
                holder.delete_.setVisibility(View.GONE);
            }else if(tripList.get(position).isInventory){
//                holder.item.setBackground(R.drawable.round_corners);

                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.item.setBackground(ContextCompat.getDrawable(context, R.drawable.orange_round) );
                } else {
                    holder.item.setBackground(ContextCompat.getDrawable(context, R.drawable.orange_round));
                }
            }
                else
            {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.item.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_round) );
                } else {
                    holder.item.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_round));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        holder.delete_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this?");
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
//                        if(tripList.get(position).isInventory) {
                            if (tripList.get(position).getSelect() != null && tripList.get(position).getSelect()) {
                                try {
                                    remove(position);
//                                tripList.remove(position);
//                                notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                            }else
//                                saveLoad(tripList.get(position).vehiocleId, position);
                        }else
                        saveLoad(tripList.get(position).vehiocleId, position,tripList.get(position).isInventory);
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
        holder.is_preinspec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prf.getStringData("OrderStatus").equalsIgnoreCase("saved") || prf.getStringData("OrderStatus").equalsIgnoreCase("shipped")) {
                    Intent intent = new Intent(context, AddImageActivity.class);
                    intent.putExtra("VehicleId", "" + tripList.get(position).vehiocleId);
                    intent.putExtra("Vin", "" + tripList.get(position).vinNumber);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, AddImageActivity.class);
                    intent.putExtra("VehicleId", "" + tripList.get(position).ItemCode);
                    intent.putExtra("Vin", "" + tripList.get(position).vinNumber);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }
        });

        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prf.getStringData("OrderStatus").equalsIgnoreCase("saved") || prf.getStringData("OrderStatus").equalsIgnoreCase("shipped")) {
                    Intent intent = new Intent(context, AddVehicleActivity.class);
                    if (tripList.get(position).getSelect() != null && tripList.get(position).getSelect()) {
                        intent.putExtra("ForInventory", "false");
                        intent.putExtra("VehicleType", "true");
                    } else {
                        intent.putExtra("ForInventory", "false");
                        intent.putExtra("VehicleType", "false");
                    }
                    intent.putExtra("OrderId", tripList.get(position).TempOrderID);
                    intent.putExtra("VehicleId", tripList.get(position).vehiocleId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
//                }
                }else{
                    Intent intent = new Intent(context, AddVehicleActivity.class);
                    if (tripList.get(position).getSelect() != null && tripList.get(position).getSelect()) {
                        intent.putExtra("ForInventory", "false");
                        intent.putExtra("VehicleType", "true");
                    } else {
                        intent.putExtra("ForInventory", "false");
                        intent.putExtra("VehicleType", "false");
                    }
                    intent.putExtra("OrderId", tripList.get(position).TempOrderID);
                    intent.putExtra("VehicleId", tripList.get(position).ItemCode);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    void saveLoad(String tempVehicleId, final int position, boolean isInvnrty) {
        RemoveVehicleModel vindetail = new RemoveVehicleModel(prf.getStringData("authKey"), tempVehicleId,isInvnrty);
        Call<RemoveVehicleModel> call1 = apiInterface.removeVehicle(""+tempVehicleId,""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"bearer "+ prf.getStringData("accessToken"),"application/x-www-form-urlencoded");

        call1.enqueue(new Callback<RemoveVehicleModel>() {
            @Override
            public void onResponse(Call<RemoveVehicleModel> call, Response<RemoveVehicleModel> response) {

                RemoveVehicleModel getdata = response.body();
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
            public void onFailure(Call<RemoveVehicleModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail((Activity) context,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void remove(int position)
            throws Exception
    {
        Gson gson = new Gson();
        if (prf.getStringData("vehicleList") != null)
        {
            ArrayList<GetVehicleIdListModel.datavalue>  mylist = gson.fromJson(prf.getStringData("vehicleList"), new TypeToken<ArrayList<GetVehicleIdListModel.datavalue>>() {}.getType());
            if (mylist != null)
            {
                int i = 0;
                while (mylist.size() > i)
                {
                    if (tripList.get(position).vehiocleId.equalsIgnoreCase(mylist.get(i).vehiocleId)) {
                        mylist.remove(i);
                    }
                    i += 1;
                }
            }
            String localObject = new Gson().toJson(mylist);
            prf.saveStringData("vehicleList", localObject);
        }
        tripList.remove(position);
        notifyDataSetChanged();
    }
}
