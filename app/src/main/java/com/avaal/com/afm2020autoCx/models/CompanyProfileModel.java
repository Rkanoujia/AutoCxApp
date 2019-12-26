package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell pc on 19-03-2018.
 */

public class CompanyProfileModel {
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Data")
    public ArrayList<DataValue3> data;
    public class DataValue3{

        @SerializedName("Name")
        public String name;
        @SerializedName("AddressOne")
        public String address;
        @SerializedName("City")
        public String city;
        @SerializedName("StateCode")
        public String state;
        @SerializedName("CountryCode")
        public String country;
        @SerializedName("PostalCode")
        public String postal;
        @SerializedName("Phone")
        public String phone;
        @SerializedName("Email")
        public String email;
        @SerializedName("Fax")
        public String fax;
        @SerializedName("Website")
        public String website;
        @SerializedName("LogoMedium")
        public String logo;
    }

}
