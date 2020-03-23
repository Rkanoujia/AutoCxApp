package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

public class CustOrderModel {
    @SerializedName("Status")
    public boolean staus;
    @SerializedName("Message")
    public String Message;
    @SerializedName("Data")
    public  Data Value;
    public class Data{
        @SerializedName("Status")
        public boolean staus;
        @SerializedName("Message")
        public String Message;
        @SerializedName("Value")
        public  String Value;
    }


}
