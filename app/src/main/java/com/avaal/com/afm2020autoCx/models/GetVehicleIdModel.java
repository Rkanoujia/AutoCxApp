package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 13-03-2018.
 */

public class GetVehicleIdModel {
    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("TempOrderID")
    public String oredrId;
    @SerializedName("Status")
    public Boolean satus;
    @SerializedName("CustomerID")
    public String customerId;
    @SerializedName("Data")
    public datavalue data;
    public  class datavalue{
        @SerializedName("TempVehicleID")
        public String temOdId;

    }
    public  GetVehicleIdModel(String authKey,String oredrId,String customerId){
        this.authKey=authKey;
        this.oredrId=oredrId;
        this.customerId=customerId;

    }
}
