package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 17-04-2018.
 */

public class LogoutModel {
    @SerializedName("AuthKey")
    String authKey;
    @SerializedName("CustomerID")
    String customerID;
    @SerializedName("Status")
   public Boolean status;
    public  LogoutModel(String authKey,String customerID){
        this.authKey=authKey;
        this.customerID=customerID;
    }
}
