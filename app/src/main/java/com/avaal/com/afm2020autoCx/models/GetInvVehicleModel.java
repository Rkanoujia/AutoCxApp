package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

public class GetInvVehicleModel
{

    @SerializedName("CustomerID")
    public String customerId;
    @SerializedName("Value")
    public String oredrId;
    @SerializedName("Status")
    public Boolean satus;
    @SerializedName("Message")
    public String Message;

}
