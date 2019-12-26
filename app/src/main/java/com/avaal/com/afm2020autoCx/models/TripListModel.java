package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell pc on 29-03-2018.
 */

public class TripListModel {



    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("OrderNumber")
    public String orderNumber;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Data")
    public ArrayList<TripDetail> getData;
    public class TripDetail{
        @SerializedName("TripID")
        public  String TripID;
        @SerializedName("TripNumber")
        public String tripNumber;
        @SerializedName("TruckNumber")
        public String truckNumber;
        @SerializedName("FirstDriverName")
        public String firstDriver;
        @SerializedName("SecondDriverName")
        public String secondDriver;
        @SerializedName("FirstTrailerNumber")
        public String firstTrailer;
        @SerializedName("SecondTrailerNumber")
        public String secondTrailer;
        @SerializedName("TripStatus")
        public String tripStatus;
        @SerializedName("CarrierName")
        public String carrierName;
        @SerializedName("Address")
        public String carrierAddress;
        @SerializedName("Longitude")
        public String carrierLongitude;
        @SerializedName("Latitude")
        public String carrierLatitude;



    }
    public ArrayList<ItemDetail>Itemlist;
    public class ItemDetail{
        @SerializedName("VIN")
        public String itemVin;
        @SerializedName("Meassage")
        public String itemMessage;
        @SerializedName("item")
        public String itemDrop;
    }

    public TripListModel(String authKey ,String orderNumber){
        this.authKey=authKey;
        this.orderNumber=orderNumber;
    }
}
