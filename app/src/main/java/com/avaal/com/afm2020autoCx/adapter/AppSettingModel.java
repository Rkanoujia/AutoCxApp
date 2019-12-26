package com.avaal.com.afm2020autoCx.adapter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 17-04-2018.
 */

public class AppSettingModel {

    @SerializedName("Message")
    String Message;
    @SerializedName("Status")
    public Boolean status;
       @SerializedName("Data")
   public vData data;
    public class vData{
        @SerializedName("IsNotifyByApp")
       public Boolean isByApp;
        @SerializedName("IsNotifyByText")
        public Boolean isByText;
        @SerializedName("IsNotifyByEmail")
        public Boolean isByMail;

    }
}
