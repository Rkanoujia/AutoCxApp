package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 17-04-2018.
 */

public class ChangePasswordModel {
    @SerializedName("AuthKey")
    String authKey;
    @SerializedName("CustomerID")
    String customerID;
    @SerializedName("NewPassword")
    String newPassword;
    @SerializedName("Status")
   public Boolean status;
    public ChangePasswordModel(String authKey, String customerID, String newPassword){
        this.authKey=authKey;
        this.customerID=customerID;
        this.newPassword=newPassword;
    }
}
