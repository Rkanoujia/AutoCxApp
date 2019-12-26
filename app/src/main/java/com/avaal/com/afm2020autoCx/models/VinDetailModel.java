package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell pc on 07-03-2018.
 */

public class VinDetailModel {
    @SerializedName("VINNo")
    public String vin;
    @SerializedName("Make")
    public String make;
    @SerializedName("Model")
    public String model;
    @SerializedName("Year")
    public String year;

}
