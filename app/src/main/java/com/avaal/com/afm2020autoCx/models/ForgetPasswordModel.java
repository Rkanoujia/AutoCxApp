package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 18-04-2018.
 */

public class ForgetPasswordModel {
    @SerializedName("Email")
    String email;
    @SerializedName("CorporateID")
    String corporateID;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Message")
    public String message;
   public ForgetPasswordModel(String email,String corporateID){
        this.email=email;
        this.corporateID=corporateID;
    }
}
