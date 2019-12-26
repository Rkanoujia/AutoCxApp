package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 29-03-2018.
 */

public class RemoveOrderModel {
    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("TempOrderId")
    public String tempOrderId;
    @SerializedName("Status")
    public Boolean status;
    public RemoveOrderModel(String authKey,String tempOrderId){
      this.authKey=authKey;
      this.tempOrderId=tempOrderId;
    }

}
