package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InvoiceListModel {

    @SerializedName("Status")
    public Boolean satus;

    @SerializedName("Message")
    public String Message;
    @SerializedName("Data")
    public ArrayList<datalist> datalist;

    public  class datalist{
        @SerializedName("InvoiceNumber")
        public String InvoiceNumber;
        @SerializedName("TotalAmount")
        public String TotalAmount;
        @SerializedName("CurrencyCode")
        public String CurrencyCode;
        @SerializedName("InvoiceDate")
        public String InvoiceDate;
        @SerializedName("InvoiceStatus")
        public String InvoiceStatus;
        @SerializedName("CustomerOrderNumber")
        public String CustomerOrderNumber;
        @SerializedName("OrderID")
        public String OrderID;
        @SerializedName("FactoringCompany")
        public String FactoringCompany;
        @SerializedName("OrderNumber")
        public  String OrderNumber;
        @SerializedName("InvoiceType")
        public  String InvoiceType;

    }
}
