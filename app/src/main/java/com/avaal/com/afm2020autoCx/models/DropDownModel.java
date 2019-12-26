package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell pc on 27-02-2018.
 */

public class DropDownModel {
    @SerializedName("ClassName")
    public String className;
    @SerializedName("Param1")
    public String param1;
    @SerializedName("Param2")
    public String param2;
    @SerializedName("AuthKey")
    public String authkey;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Data")
    public List<spinners> data = new ArrayList<spinners>();
    public class spinners{
        @SerializedName("ID")
        public String id;
        @SerializedName("Name")
        public String name;
        @SerializedName("Code")
        public String code;
    }

    public DropDownModel(String className, String param1,String param2,String authkey) {
        this.className = className;
        this.param1 = param1;
        this.param2=param2;
        this.authkey=authkey;
    }
}
