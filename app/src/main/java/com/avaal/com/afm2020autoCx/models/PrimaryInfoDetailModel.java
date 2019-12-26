package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

public class PrimaryInfoDetailModel {
    @SerializedName("Status")
    public Boolean Status;
    @SerializedName("Message")
    public String Message;
    @SerializedName("Data")
    public Data data;

    public class Data {
        @SerializedName("PrimaryInfoCode")
        public String PrimaryInfoCode;
        @SerializedName( "PrimaryInfoType")
        public String PrimaryInfoType;
        @SerializedName( "CompanyCode")
        public String CompanyCode;
        @SerializedName( "CompanyName")
        public String CompanyName;
        @SerializedName( "RegionID")
        public String RegionID;
        @SerializedName("RegionName")
        public String RegionName;
        @SerializedName("Name")
        public String Name;
        @SerializedName( "AddressOne")
        public String AddressOne;
        @SerializedName( "AddressTwo")
        public String AddressTwo;
        @SerializedName("CountryName")
        public String CountryName;
        @SerializedName( "CountryCode")
        public String CountryCode;
        @SerializedName("StateName")
        public String StateName;
        @SerializedName("StateCode")
        public String StateCode;
        @SerializedName( "PostalCode")
        public String PostalCode;
        @SerializedName( "City")
        public String City;
        @SerializedName("Latitude")
        public String Latitude;
        @SerializedName("Longitude")
        public String Longitude;
        @SerializedName("IsCarrier")
        public boolean IsCarrier;
        @SerializedName("IsCustomer")
        public boolean IsCustomer;
        @SerializedName("IsLocation")
        public boolean IsLocation;
        @SerializedName("IsYard")
        public String IsYard;
        @SerializedName("ReferenceNumber")
        public String ReferenceNumber;
        @SerializedName( "WebSite")
        public String WebSite;
        @SerializedName( "Email")
        public String Email;
        @SerializedName( "Phone")
        public String Phone;
        @SerializedName( "Ext")
        public String Ext;
        @SerializedName( "Fax")
        public String Fax;
        @SerializedName( "Notes")
        public String Notes;
        @SerializedName("YardCode")
        public String YardCode;
        @SerializedName("SlotPrefix")
        public String SlotPrefix;
        @SerializedName("LanePrefix")
        public String LanePrefix;
        @SerializedName( "NoOfSlots")
        public String NoOfSlots;
        @SerializedName("NoOfLanes")
        public String NoOfLanes;
        @SerializedName("QbID")
        public String QbID;
        @SerializedName("VqbID")
        public String VqbID;
        @SerializedName("QbLastSyncDate")
        public String QbLastSyncDate;
        @SerializedName("CTPAT")
        public String CTPAT;
        @SerializedName("CSA")
        public String CSA;
        @SerializedName("PIP")
        public String PIP;
        @SerializedName("IsAppointment")
        public String IsAppointment;
        @SerializedName( "LegalName")
        public String LegalName;
        @SerializedName( "MobileAppSettingID")
        public String MobileAppSettingID;
        @SerializedName( "Id")
        public String Id;
        @SerializedName( "CorporateId")
        public String CorporateId;
        @SerializedName( "CallSource")
        public String CallSource;
        @SerializedName( "IsActive")
        public Boolean IsActive;
        @SerializedName("IsDeleted")
        public String IsDeleted;
        @SerializedName("CreatedOn")
        public String CreatedOn;
        @SerializedName("CreatedBy")
        public String CreatedBy;
        @SerializedName("ModifiedOn")
        public String ModifiedOn;
        @SerializedName( "ModifiedBy")
        public String ModifiedBy;
        @SerializedName("Status")
        public String Status;
        @SerializedName("Message")
        public String Message;
    }
}
