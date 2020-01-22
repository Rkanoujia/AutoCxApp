package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 20-03-2018.
 */

public class DashBoardModel {
    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("CustomerID")
   public String customerId;
    @SerializedName("Status")
   public Boolean status;
    @SerializedName("Data")
    public dataval data;

    public class dataval{
        @SerializedName("SavedOrder")
        public int saveLoad;
        @SerializedName("ShippedOrder")
        public int ShippedLoad;
        @SerializedName("DispatchedOrder")
        public int DispatchedOrder;
        @SerializedName("TripsEnroute")
        public int TripsEnroute;
        @SerializedName("TotalReceivedVehicle")
        public int TotalReceivedVehicle;
        @SerializedName("TotalShippedVehicle")
        public int TotalShippedVehicle;
        @SerializedName("Outstanding")
        public String Outstanding;
        @SerializedName("TotalPaid")
        public int paid;
        @SerializedName("TotalInvoiced")
        public int invoiced;
        @SerializedName("TotalOutstanding")
        public int TotalOutstanding;
        @SerializedName("TotalOrder")
        public int TotalOrder;
    }
    public DashBoardModel(String authKey,String customerId){
        this.authKey=authKey;
        this.customerId=customerId;
    }
}
