package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 28-02-2018.
 */

public class AddLocationModel {

    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("CarrierPrimaryInfoId")
    public String carrierId="0";
    @SerializedName("Type")
    public String type="L";
    @SerializedName("Name")
    public String name;
    @SerializedName("Address")
    public String address;
    @SerializedName("CityID")
    public String cityId;
    @SerializedName("StateId")
    public String stateId;
    @SerializedName("CountryId")
    public String countryId;
    @SerializedName("PostalCodeId")
    public String postalId;
    @SerializedName("ID")
    public String id;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("CreateBy")
 public String createBy;
    @SerializedName("Message")
    public String message;
    @SerializedName("CreatedDate")
    public String createddate;
    public  AddLocationModel(String authKey, String name,String address,String countryId,String stateId,String cityId,String postalId,String createBy,String createddate){
        this.authKey=authKey;
        this.name=name;
        this.address=address;
        this.cityId=cityId;
        this.countryId=countryId;
        this.stateId=stateId;
        this.postalId=postalId;
        this.createBy=createBy;
        this.createddate=createddate;
    }


}
