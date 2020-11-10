package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

public class PreImagesModel {
    @SerializedName("FileName")
    public  String FileName;
    @SerializedName("FileExtension")
    public  String FileExtension;
    @SerializedName("FileBytes")
    public  String FileBytes;
    @SerializedName("CorporateId")
    public  String CorporateId;
    @SerializedName("ModuleRefCode")
    public  String ModuleRefCode;
    @SerializedName("ModuleTypeCode")
    public  String ModuleTypeCode;
    @SerializedName("CallSource")
    public  String CallSource;
    @SerializedName("MobileAppCode")
    public  String MobileAppCode;
    @SerializedName("DocumentTypeCode")
    public  String DocumentTypeCode;
    @SerializedName("ActionType")
    public  String ActionType;
    @SerializedName("FileContentType")
    public  String FileContentType;
    @SerializedName("FileSizeInKB")
    public String FileSizeInKB;
    @SerializedName("ParentModuleTypeCode")
    public  String ParentModuleTypeCode;
    @SerializedName("ParentModuleRefCode")
    public  String ParentModuleRefCode;
    @SerializedName("CreatedBy")
    public String CreatedBy;
    @SerializedName("AppTripId")
    public String TripID;
    String ID;
    @SerializedName("ItemCode")
    public String ItemCode;

    public String IsSync;

    public String getIsSync() {
        return IsSync;
    }

    public void setIsSync(String isSync) {
        IsSync = isSync;
    }
    //    @SerializedName("ItemCode")
//    public String NameWithId;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public PreImagesModel(){

    }

    public String getTripID() {
        return TripID;
    }

    public void setTripID(String tripID) {
        TripID = tripID;
    }

    public PreImagesModel(String FileName, String FileExtension, String FileBytes, String CorporateId, String ModuleRefCode, String ModuleTypeCode, String CallSource, String MobileAppCode,
                          String DocumentTypeCode, String ActionType, String FileContentType, String FileSizeInKB, String CreatedBy, String ItemCode, String ParentModuleTypeCode, String ParentModuleRefCode, String TripID,String IsSync){
        this.FileName=FileName;
        this.FileExtension=FileExtension;
        this.FileBytes=FileBytes;
        this.CorporateId=CorporateId;
        this.ModuleRefCode=ModuleRefCode;
        this.ModuleTypeCode=ModuleTypeCode;
        this.CallSource=CallSource;
        this.MobileAppCode=MobileAppCode;
        this.DocumentTypeCode=DocumentTypeCode;
        this.ActionType=ActionType;
        this.FileContentType=FileContentType;
        this.FileSizeInKB=FileSizeInKB;
        this.CreatedBy=CreatedBy;
        this.ItemCode=ItemCode;
        this.ParentModuleTypeCode=ParentModuleTypeCode;
        this.ParentModuleRefCode=ParentModuleRefCode;
        this.TripID=TripID;
        this.IsSync=IsSync;
//        this.NameWithId=TripID+""+FileName;
    }
    public String getFileBytes() {
        return FileBytes;
    }

    public void setFileBytes(String fileBytes) {
        FileBytes = fileBytes;
    }

    public String getActionType() {

        return ActionType;
    }

    public void setActionType(String actionType) {
        ActionType = actionType;
    }

    public String getDocumentTypeCode() {

        return DocumentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        DocumentTypeCode = documentTypeCode;
    }

    public String getMobileAppCode() {

        return MobileAppCode;
    }

    public void setMobileAppCode(String mobileAppCode) {
        MobileAppCode = mobileAppCode;
    }

    public String getModuleTypeCode() {

        return ModuleTypeCode;
    }

    public void setModuleTypeCode(String moduleTypeCode) {
        ModuleTypeCode = moduleTypeCode;
    }

    public String getModuleRefCode() {

        return ModuleRefCode;
    }

    public void setModuleRefCode(String moduleRefCode) {
        ModuleRefCode = moduleRefCode;
    }
}


