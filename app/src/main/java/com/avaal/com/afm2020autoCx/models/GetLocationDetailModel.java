package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 20-03-2018.
 */

public class GetLocationDetailModel {
    @SerializedName("AuthKey")
   public String authKey;
    @SerializedName("LocationID")
    public String locationId;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Data")
    public AddLocationModel data;
    public GetLocationDetailModel(String authKey,String locationId){
        this.authKey=authKey;
        this.locationId=locationId;
    }
}
