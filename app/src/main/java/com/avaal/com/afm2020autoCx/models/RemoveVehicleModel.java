package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 17-03-2018.
 */

public class RemoveVehicleModel {
    @SerializedName("AuthKey")
   public String AuthKey;
    @SerializedName("TempVehicleID")
   public String tempVehicleId;
    @SerializedName("IsInventory")
    public Boolean IsInventory;
    @SerializedName("Message")
    public String Message;
    @SerializedName("Status")
   public Boolean status;
    public RemoveVehicleModel(String AuthKey,String tempVehicleId,Boolean IsInventory){
        this.AuthKey=AuthKey;
        this.tempVehicleId=tempVehicleId;
        this.IsInventory=IsInventory;
    }
}
