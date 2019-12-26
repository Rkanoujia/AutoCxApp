package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell pc on 16-03-2018.
 */

public class VehicleModelModel {
    @SerializedName("Message")
    public String message;
    @SerializedName("Results")
    public ArrayList<resultsV> results;
    public class resultsV{
        @SerializedName("Model_Name")
        public String modelName;
        @SerializedName("Model_ID")
        public String modelID;
    }
}
