package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 13-03-2018.
 */

public class RouteSelectionModel {

    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("OrderType")
    public String odType;
    @SerializedName("PickupID")
    public String pickupId;
    @SerializedName("DropID")
    public String dropId;
    @SerializedName("CarrierPrimaryInfoID")
    public String carrirId;
    @SerializedName("PickupDate")
    public String pickupDate;
    @SerializedName("PickupTime")
    public String pickupTime;
    @SerializedName("DropDate")
    public String dropDate;
    @SerializedName("DropTime")
    public String dropTime;
    @SerializedName("PickupNotes")
    public String pickupRemark;
    @SerializedName("DropNotes")
    public String dropRemark;
    @SerializedName("Status")
    public Boolean satus;
    @SerializedName("StopID")
    public String stopId="0";
    @SerializedName("Value")
    public String tempOrderId;
    @SerializedName("Message")
    public String Message;


   /* Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");*/

    @SerializedName("StopDate")
    public String stopDate;
    @SerializedName("StopTime")
    public String stopTime;
    @SerializedName("StopNotes")
    public String stopRemarks;
    @SerializedName("Data")
    public datavalue data;
    public  class datavalue{
        @SerializedName("TempOrderID")
        public String temOdId;
//        @SerializedName("TempVehicleID")
//        public String temVehicleId;
    }


}
