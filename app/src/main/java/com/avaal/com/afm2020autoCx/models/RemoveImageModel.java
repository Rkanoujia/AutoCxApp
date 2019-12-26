package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 27-04-2018.
 */

public class RemoveImageModel {
    @SerializedName("AuthKey")
    public String AuthKey;
    @SerializedName("FileID")
    public String tempVehicleId;
    @SerializedName("Status")
    public Boolean status;
    public RemoveImageModel(String AuthKey,String tempVehicleId){
        this.AuthKey=AuthKey;
        this.tempVehicleId=tempVehicleId;
    }
}
