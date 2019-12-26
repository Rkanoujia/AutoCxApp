package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LookUpModel {
    @SerializedName("Data")
    public ArrayList<Data> data;
    public class Data{
        @SerializedName("LuCode")
        public String Code;
        @SerializedName("DisplayName")
        public String Name;
    }
}