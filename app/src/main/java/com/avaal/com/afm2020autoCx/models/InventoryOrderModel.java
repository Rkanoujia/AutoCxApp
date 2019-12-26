package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

public class InventoryOrderModel
{
    @SerializedName("AuthKey")
    String authkey;
    @SerializedName("OrderID")
    String orderID;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Vehicles")
    String vehicles;

    public InventoryOrderModel(String authkey, String vehicles, String orderID)
    {
        this.authkey = authkey;
        this.vehicles = vehicles;
        this.orderID = orderID;
    }
}
