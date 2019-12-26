package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MasterDropDownModel {
    @SerializedName("Data")
   public ArrayList<Data> data;
    public class Data{
        @SerializedName("Code")
        public String Code;
        @SerializedName("Name")
        public String Name;
    }
}
