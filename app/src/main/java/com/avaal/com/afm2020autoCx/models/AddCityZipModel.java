package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 23-03-2018.
 */

public class AddCityZipModel {
    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("StateID")
    public String stateId;
    @SerializedName("cityName")
    public String cityName;
    @SerializedName("CityID")
    public String cityId;
    @SerializedName("ZipCode")
    public String zipCode;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("ID")
    public String id;
    public  AddCityZipModel(String authKey,String stateId,String cityName,String cityId,String zipCode){
        this.authKey=authKey;
        this.stateId=stateId;
        this.cityName=cityName;
        this.cityId=cityId;
        this.zipCode=zipCode;
    }

}
