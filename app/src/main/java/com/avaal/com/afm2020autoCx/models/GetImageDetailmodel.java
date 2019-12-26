package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 04-04-2018.
 */

public class GetImageDetailmodel {
    @SerializedName("TempVehicleID")
    String temVehicleId;
    @SerializedName("AuthKey")
    String authKey;
    @SerializedName("DocumentType")
    String documentType;
    @SerializedName("Status")
    public  Boolean status;

    @SerializedName("Data")
    public datavalu dataValuer;
    public class datavalu{
        @SerializedName("DocumentID")
        public   String docId;
        @SerializedName("DocumentType")
        public  String doctype;
        @SerializedName("DocumentURL")
        public  String DocURL;

    }
    public GetImageDetailmodel(String authKey,String temVehicleId,String documentType){
        this.temVehicleId=temVehicleId;
        this.authKey=authKey;
        this.documentType=documentType;
    }
}
