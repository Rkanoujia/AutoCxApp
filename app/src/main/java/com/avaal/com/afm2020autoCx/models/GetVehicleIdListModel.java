package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell pc on 16-03-2018.
 */

public class GetVehicleIdListModel
{
    @SerializedName("CustomerID")
    public String CustomerID;
    @SerializedName("Message")
    public String Message;
    @SerializedName("Data")
    public ArrayList<datavalue> dataV;
    @SerializedName("TempOrderID")
    public String oredrId;
    @SerializedName("Status")
    public Boolean satus;
    @SerializedName("Type")
    public String typeV;

//    public GetVehicleIdListModel(String authKey, String oredrId, String typeV, String CustomerID)
//    {
//
//        this.oredrId = oredrId;
//        this.typeV = typeV;
//        this.CustomerID = CustomerID;
//    }

    public class datavalue
    {
        @SerializedName("TempOrderID")
        public String TempOrderID;
        public Boolean IsSelect;
        @SerializedName("BillOfSale")
        public String billOfSale;
        @SerializedName("BuildMonth")
        public String buildMonth;
        @SerializedName("BuildYear")
        public String buildYear;
        @SerializedName("CreatedOn")
        public String createDate;
        @SerializedName("DeclaredValue")
        public int declareValue;
        @SerializedName("Diesel")
        public String diesel;
        @SerializedName("GVWRUnit")
        public String gvwrUnit;
        @SerializedName("GVWRValue")
        public String gvwrValue;
        @SerializedName("IsInventory")
        public Boolean isInventory;
        @SerializedName("Make")
        public String makeV;
        @SerializedName("MileageUnit")
        public String mileageUnit;
        @SerializedName("MileageValue")
        public String mileageValue;
        @SerializedName("Model")
        public String model;
        @SerializedName("OemTag")
        public String oem;
        @SerializedName("SpeedoConversion")
        public String speedoConversion;
        @SerializedName("Title")
        public String title;
        @SerializedName("TitleConversion")
        public String titleConversion;
        @SerializedName("TPMS")
        public String tpms;
        @SerializedName("IsReleaseForm")
        public String releasefrom;
        @SerializedName("TrackingConfirmation")
        public String trackingConfig;
        @SerializedName("VehicleID")
        public String vehiocleId;
        @SerializedName("VinNumber")
        public String vinNumber;
        @SerializedName("Year")
        public String year;
        @SerializedName("IsPreInspectionDone")
        public String IsPreInspectionDone;
        @SerializedName("Imagepath")
        public String Imagepath;
        @SerializedName("ExtraCount")
        public String ExtraCount;
        @SerializedName("Color")
        public String Color;
        @SerializedName("DeclaredCurrency")
        public String DeclaredCurrency;
        @SerializedName("ItemCode")
        public String ItemCode;
        @SerializedName("Latitude")
        public Double Latitude;
        @SerializedName("Longitude")
        public Double Longitude;
        @SerializedName("InventoryDate")
        public  String InventoryDate;
        @SerializedName("OrderDate")
        public  String OrderDate;
        public datavalue() {}

        public Boolean getSelect()
        {
            return this.IsSelect;
        }

        public void setSelect(Boolean paramBoolean)
        {
            this.IsSelect = paramBoolean;
        }
    }

}
