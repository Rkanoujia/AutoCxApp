package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 18-04-2018.
 */

public class ConfirmOrderModel {
    @SerializedName("AuthKey")
    String authkey;
    @SerializedName("CustomerID")
    String customerID;
    @SerializedName("OrderNumber")
    String orderNumber;
    @SerializedName("Status")
    public Boolean status;
    public  ConfirmOrderModel(String authkey,String customerID,String orderNumber){
        this.authkey=authkey;
        this.customerID=customerID;
        this.orderNumber=orderNumber;
    }
}
