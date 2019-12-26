package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 19-03-2018.
 */

public class ProfileDataModel {


    @SerializedName("AuthKey")
    String authKey;
    @SerializedName("CustomerID")
    String customerId;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Data")
    public DataValu data;
    public class DataValu{
        @SerializedName("PrimaryInfoCode")
        public String PrimaryInfoCode;
        @SerializedName("CompanyCode")
        public String CompanyCode;
        @SerializedName("Name")
        public String Name;
        @SerializedName("AddressOne")
        public String AddressOne;
        @SerializedName("CountryName")
        public String CountryName;
        @SerializedName("CountryCode")
        public String CountryCode;
        @SerializedName("StateCode")
        public String StateCode;
        @SerializedName("StateName")
        public String StateName;
        @SerializedName("PostalCode")
        public String PostalCode;
        @SerializedName("City")
        public String City;
        @SerializedName("Email")
        public  String Email;
        @SerializedName("Phone")
        public  String Phone;

    }

}
