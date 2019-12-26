package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell pc on 17-03-2018.
 */

public class GetImageModel {
    @SerializedName("TempVehicleID")
    String temVehicleId;
    @SerializedName("AuthKey")
    String authKey;
    @SerializedName("Status")
  public  Boolean status;

    @SerializedName("Data")
    public ArrayList<datavalu> dataValuer;
    public class datavalu{
        @SerializedName("Id")
        public   String docId;
        @SerializedName("FileName")
        public   String FileName;
        @SerializedName("DocumentType")
        public  String doctype;
        @SerializedName("FilePath")
        public  String DocURL;

    }
   public GetImageModel(String authKey,String temVehicleId){
        this.temVehicleId=temVehicleId;
        this.authKey=authKey;
    }
}
