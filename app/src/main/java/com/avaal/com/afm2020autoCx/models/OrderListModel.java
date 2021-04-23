package com.avaal.com.afm2020autoCx.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dell pc on 16-03-2018.
 */

public class OrderListModel {

    @SerializedName("Status")
    public Boolean satus;
    @SerializedName("Type")
    public String typeV;
    @SerializedName("FilterBY")
    public String filterBy;
    @SerializedName("Data")
    public ArrayList<datavalue1> dataValue;

    public  class datavalue1{
        @SerializedName("TempOrderID")
        public String orderId;
        @SerializedName("SavedOrderID")
        public String saveOrderId;
        @SerializedName("OrderType")
        public String orderType;
        @SerializedName("PickupCode")
        public String pickupId;
        @SerializedName("PickupDateTime")
        public String PickupDateTime;
        @SerializedName("SavedOrderNumber")
        public String SavedOrderNumber;
        @SerializedName("PickupNotes")
        public String pickupNote;
        @SerializedName("DropCode")
        public String dropId;
        @SerializedName("DropDateTime")
        public String DropDateTime;
//        @SerializedName("DropTime")
//        public String dropTime;
        @SerializedName("DropNotes")
        public String dropNote;
        @SerializedName("StopID")
        public String stopId;
        @SerializedName("StopDate")
        public String stopDate;
        @SerializedName("StopTime")
        public String stopTime;
        @SerializedName("StopNotes")
        public String stopNote;
        @SerializedName("PrimaryInfoCode")
        public String PrimaryInfoCode;
        @SerializedName("VehicleCount")
        public String vehiclecount;
        @SerializedName("PickupName")
        public String PickupName;
        @SerializedName("PickupAddressOne")
        public String pickupAddress;
        @SerializedName("PickupCity")
        public String pickupCity;
        @SerializedName("PickupStateName")
        public String pickupState;
        @SerializedName("PickupStateCode")
        public String pickupstateCode;
        @SerializedName("PickupPostalCode")
        public String pickupZip;
        @SerializedName("PickupCountryCode")
        public String PickupCountryCode;
        @SerializedName("DeliveryName")
        public String dropName;
        @SerializedName("DeliveryAddressOne")
        public String dropAddress;
        @SerializedName("DeliveryCity")
        public String dropCity;
        @SerializedName("DeliveryStateName")
        public String dropState;
        @SerializedName("DeliveryStateCode")
        public String dropSateCode;
        @SerializedName("DeliveryPostalCode")
        public String dropZip;
        @SerializedName("DeliveryCountryCode")
        public String DeliveryCountryCode;
        @SerializedName("StopName")
        public String stopName;
        @SerializedName("StopAdress")
        public String stopAddress;
        @SerializedName("StopCity")
        public String stopCity;
        @SerializedName("StopState")
        public String stopState;
        @SerializedName("StopStateCode")
        public String stopStateCode;
        @SerializedName("StopZip")
        public String stopZip;
        @SerializedName("StopCountry")
        public String stopCountry;
        @SerializedName("FreightTypeLuCode")
        public String FreightTypeLuCode;
        @SerializedName("Status")
        public String status;
        @SerializedName("PickupNumber")
        public String PickupNumber;
        @SerializedName("DeliveryNumber")
        public String DeliveryNumber;
        @SerializedName("CustomerOrderNo")
        public String CustomerOrderNo;
        @SerializedName("CustAppOrderId")
        public String CustAppOrderId;



    }

}
