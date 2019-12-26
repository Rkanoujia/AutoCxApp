package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell pc on 16-03-2018.
 */

public class MakeModel {
    @SerializedName("Message")
    public String message;
    @SerializedName("Results")
    public ArrayList<resultsVa> resultV;
    public class resultsVa{
        @SerializedName("MakeName")
        public String makeName;
    }

}
