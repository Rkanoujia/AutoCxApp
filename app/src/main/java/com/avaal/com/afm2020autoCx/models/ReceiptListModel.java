package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReceiptListModel {
    @SerializedName("Status")
    public Boolean satus;

    @SerializedName("Message")
    public String Message;
    @SerializedName("Data")
    public ArrayList<datalist> datalist;
    @SerializedName("TotalCount")
    String TotalCount;
            @SerializedName("FilteredCount")
            String FilteredCount;

    public  class datalist{
        @SerializedName("ReceiptNumber")
        public String ReceiptNumber;
        @SerializedName("ReceiptAmount")
        public String ReceiptAmount;
        @SerializedName("CurrencyCode")
        public String CurrencyCode;
        @SerializedName("ReceiptDate")
        public String ReceiptDate;
        @SerializedName("PaymentMethod")
        public String PaymentMethod;
        @SerializedName("CustomerOrderNumber")
        public String CustomerOrderNumber;
        @SerializedName("ReferenceNo")
        public String ReferenceNo;
        @SerializedName("ReceiptId")
        public String ReceiptId;
        @SerializedName("OrderNumber")
        public  String OrderNumber;
        @SerializedName("InvoiceNumbers")
        public  String InvoiceNumber;
        @SerializedName("ReceiptType")
        public  String ReceiptType;

    }
}
