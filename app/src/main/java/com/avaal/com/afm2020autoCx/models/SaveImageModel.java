package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import extra.PreferenceManager;

/**
 * Created by dell pc on 13-03-2018.
 */

public class SaveImageModel {


    @SerializedName("Id")
    public String fileId;
    @SerializedName("FileName")
    public String fileName;
    @SerializedName("FileContentType")
    public String contentType;
    @SerializedName("FileSizeInKB")
    public String size;
    @SerializedName("FileExtension")
    public String extension;
    @SerializedName("FileBytes")
    public String fileData;
    @SerializedName("Remarks")
    public String remark;
    @SerializedName("DocumentTypeCode")
    public String docType;
    @SerializedName("VehicleID")
    public String temVehicleId;
    @SerializedName("FilePath")
    public String FilePath;
    @SerializedName("CreatedBy")
    public String CreatedBy;
    @SerializedName("MobileAppCode")
    public  String MobileAppCode;
//    @SerializedName("ActionType")
//    public String ActionType;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("CorporateId")
    public String CorporateId;
    @SerializedName("CustomerCode")
    public String CustomerCode;
    @SerializedName("Data")
    public datavalue1 datavalue;
    @SerializedName("Value")
    public String docUrl;
    @SerializedName("Value2")
    public  String imageId;

    public class datavalue1{

         @SerializedName("DocumentType")
         public String docType1;
        @SerializedName("Value")
        public String docUrl;
        @SerializedName("Value2")
        public  String imageId;

     }

    public  SaveImageModel(String fileName,String contentType,String size,String extension,String fileData,String remark,String docType,String temVehicleId,String fileId,String CorporateId,String CustomerCode,String CreatedBy){
        this.fileName=fileName;
        this.contentType=contentType;
        this.size=size;
        this.extension=extension;
        this.fileData=fileData;
        this.remark=remark;
        this.docType=docType;
        this.temVehicleId=temVehicleId;
        this.fileId=fileId;
        this.CorporateId=CorporateId;
        this.CustomerCode=CustomerCode;
        this.MobileAppCode=docType;
//                this.ActionType=docType;
        this.CreatedBy=CreatedBy;
    }

}
