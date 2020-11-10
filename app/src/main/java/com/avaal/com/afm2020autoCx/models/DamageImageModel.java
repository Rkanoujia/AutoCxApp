package com.avaal.com.afm2020autoCx.models;

public class DamageImageModel {
    String name;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    String viewName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String code;
    Integer imageid;

    public int getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public DamageImageModel(String name, String url,Integer imageid,String code,String viewName){
        this.name=name;
        this.url=url;
        this.imageid=imageid;
        this.code=code;
        this.viewName=viewName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;
}
