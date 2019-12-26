package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 04-04-2018.
 */

public class LocationDetailModel {
    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("LocationID")
    public String locationId;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Message")
    public String message;
    @SerializedName("Data")
    public Data datavalue;
    public class Data{
        @SerializedName("LocationName")
        public String locationName;
        @SerializedName("Address")
        public String address;
        @SerializedName("City")
        public String city;
        @SerializedName("State")
        public String state;
        @SerializedName("Country")
        public String country;
        @SerializedName("PostalCode")
        public String postalCode;
        @SerializedName("ContactPerson")
        public String contactPerson;
        @SerializedName("Mobile")
        public String mobile;
        @SerializedName("Phone")
        public String phone;
    }
   public LocationDetailModel(String authKey,String locationId){
        this.authKey=authKey;
        this.locationId=locationId;
    }
}
