package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell pc on 04-06-2018.
 */

public class ItemListModel {
    @SerializedName("AuthKey")
    public String authKey;
    @SerializedName("OrderNumber")
    public String orderNumber;
    @SerializedName("Status")
    public Boolean status;
    @SerializedName("Data")
    public ArrayList<ItemDetail>Itemlist;
    public class ItemDetail{
        @SerializedName("VinNumber")
        public String itemVin;
        @SerializedName("Remarks")
        public String itemMessage;
        @SerializedName("ItemStatus")
        public String itemStatus;
        @SerializedName("Dropped")
        public String itemDrop;
        @SerializedName("PickupDepartDate")
        public String PickupDate;

        @SerializedName("DeliveryDepartDate")
        public String DeliveryDate;

    }

    public ItemListModel(String authKey ,String orderNumber){
        this.authKey=authKey;
        this.orderNumber=orderNumber;
    }
}