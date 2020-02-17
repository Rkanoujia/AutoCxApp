package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MilesModel {
    @SerializedName("Message")
    String temVehicleId;
    @SerializedName("AuthKey")
    String authKey;
    @SerializedName("Status")
    public  Boolean status;

    @SerializedName("Data")
    public datavalu dataValuer;
    public class datavalu{
        @SerializedName("TotalMiles")
        public   String TotalMiles;
        @SerializedName("DistanceUnit")
        public   String DistanceUnit;
        @SerializedName("ErrorMsg")
        public  String ErrorMsg;


    }


}
