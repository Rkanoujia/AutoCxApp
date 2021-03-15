package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.FormUrlEncoded;

/**
 * Created by dell pc on 23-02-2018.
 */

public class LoginModel {

    @SerializedName("access_token")
    public String access_token;
    @SerializedName("refresh_token")
    public String refresh_token;
    @SerializedName("corporateid")
    public String corporateid;
    @SerializedName("userName")
    public String userName;
    @SerializedName("error")
    public String error;
    @SerializedName("error_description")
    public String error_description;




//    public LoginModel(String name,String coorporateId, String pswrd,String grant_type) {
//        this.name = coorporateId+"~"+name+"~regular~"+FirebaseInstanceId.getInstance().getToken()+"~PRI~Android";
//        this.pswrd = pswrd;
//        this.grant_type=grant_type;
////        this.deviceId=deviceId;
//    }
}
