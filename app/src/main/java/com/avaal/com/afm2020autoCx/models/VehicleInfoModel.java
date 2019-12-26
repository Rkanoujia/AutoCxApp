package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 13-03-2018.
 */

public class VehicleInfoModel {
    @SerializedName("GVWRValue")
    String GvwrValue = "";
    @SerializedName("IsInventory")
    Boolean IsInventory;
    @SerializedName("AuthKey")
    String authKey;
    @SerializedName("BillOfSale")
    String billOfsale = "";
    @SerializedName("BuildMonth")
    String buildMonth = "";
    @SerializedName("BuildYear")
    String buildYear = "";
    @SerializedName("DeclaredValue")
    String declaredvalue = "";
    @SerializedName("Diesel")
    String diesel = "";
    @SerializedName("GVWRUnit")
    String gVWRUnit = "";
    @SerializedName("Make")
    String make;
    @SerializedName("MileageUnit")
    String mileageUnit = "";
    @SerializedName("MileageValue")
    String mileageValue = "";
    @SerializedName("Model")
    String model;
    @SerializedName("OemTag")
    String oemTag;
    @SerializedName("SpeedoConversion")
    String speedconversion = "";
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("TPMS")
    String tPMS = "";
    @SerializedName("TempVehicleID")
    String temVehicleId;
    @SerializedName("Title")
    String title = "";
    @SerializedName("TitleConversion")
    String titleConversion = "";
    @SerializedName("TrackingConfirmation")
    String trackingConfir = "";
    @SerializedName("VINNumber")
    String vinNumber;
    @SerializedName("IsReleaseForm")
    String isReleaseForm;
    @SerializedName("Year")
    String year;
    @SerializedName("Color")
    String Color;




            @SerializedName("Message")
           public String Message;


    public VehicleInfoModel(String authKey, String temVehicleId, String vinNumber, String make, String model, String year, String oemTag,
                            String mileageValue, String tPMS, String declaredvalue, String buildYear, String GvwrValue, String diesel, String speedconversion, String titleConversion,
                            String billOfsale, String trackingConfir, String title, String buildMonth, String gVWRUnit, String mileageUnit, Boolean IsInventory,String isReleaseForm)
    {
        this.authKey = authKey;
        this.temVehicleId = temVehicleId;
        this.vinNumber = vinNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.oemTag = oemTag;
        this.mileageValue = mileageValue;
        this.tPMS = tPMS;
        this.declaredvalue = declaredvalue;
        this.buildYear = buildYear;
        this.GvwrValue = GvwrValue;
        this.diesel = diesel;
        this.speedconversion = speedconversion;
        this.titleConversion = titleConversion;
        this.billOfsale = billOfsale;
        this.trackingConfir = trackingConfir;
        this.title = title;
        this.buildMonth = buildMonth;
        this.gVWRUnit = gVWRUnit;
        this.mileageUnit = mileageUnit;
        this.IsInventory = IsInventory;
        this.isReleaseForm=isReleaseForm;
    }


}
