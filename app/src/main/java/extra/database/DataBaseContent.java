package extra.database;

/**
 * Created by Rohit Kumar on 16-06-2016.
 */
public class DataBaseContent {

    public static final String TABLE_TRIP = "Order";
    public static final String TripID = "TripID";
    public static final String TripNumber = "TripNumber";
    public static final String TripStatus = "TripStatus";
    public static final String TotalDistance = "TotalDistance";
    public static final String TripType = "TripType";
    public static final String TruckNumber = "TruckNumber";
    public static final String TrailerNumber = "TrailerNumber";
    public static final String SecondTrailerNumber = "SecondTrailerNumber";
    public static final String FirstPickupDate = "FirstPickupDate";
    public static final String PickupCountry = "PickupCountry";
    public static final String PickupCountryCode = "PickupCountryCode";
    public static final String PickupCity = "PickupCity";
    public static final String PickupPostalCode = "PickupPostalCode";
    public static final String LastDeliveryDate = "LastDeliveryDate";
    public static final String DeliveryCountryCode = "DeliveryCountryCode";
    public static final String DeliveryCountry = "DeliveryCountry";
    public static final String DeliveryCity = "DeliveryCity";
    public static final String DeliveryPostalCode = "DeliveryPostalCode";
    public static final String DistanceUnit = "DistanceUnit";
    public static final String PickupStateCode = "PickupStateCode";
    public static final String DeliveryStateCode = "DeliveryStateCode";
    public static final String CustomeTrailerNumber = "CustomeTrailerNumber";
    public static final String CustomeSecondTrailerNumber = "CustomeSecondTrailerNumber";
    public static final String OrderFormType = "OrderFormType";
    public static final String DriverId = "DriverId";


    static String CREATE_TRIP = "create table "
            + TABLE_TRIP + " (" + TripID + " text primary key , "
            + TripNumber + " text, "
            + TripStatus + " text,"
            + TotalDistance + " text,"
            + TripType + " text,"
            + TruckNumber + " text,"
            + FirstPickupDate + " text,"
            + PickupCountry + " text,"
            + PickupCountryCode + " text,"
            + PickupCity + " text,"
            + PickupPostalCode + " text,"
            + LastDeliveryDate + " text,"
            + DeliveryCountryCode + " text,"
            + DeliveryCountry + " text,"
            + DeliveryCity + " text,"
            + DeliveryPostalCode + " text,"
            + DistanceUnit + " text,"
            + PickupStateCode + " text,"
            + DeliveryStateCode + " text,"
            + DriverId + " text,"
            + TrailerNumber + " text,"
            + SecondTrailerNumber + " text,"
            + CustomeTrailerNumber + " text,"
            + CustomeSecondTrailerNumber + " text,"
            + OrderFormType + " text );";



    public static final String TABLE_TRIPDETAIL = "TripDetailsTable";
    public static final String TripDetailID = "TripDetailID";
    public static final String ActionType = "ActionType";
    public static final String StopName = "StopName";
    public static final String ItemCount = "ItemCount";
    public static final String StopAddressOne = "StopAddressOne";
    public static final String StopCity = "StopCity";
    public static final String StopPostalCode = "StopPostalCode";
    public static final String StopContactPersonName = "StopContactPersonName";
    public static final String StopContactPersonPhone = "StopContactPersonPhone";
    public static final String DOR = "DOR";
    public static final String ArrivalDate = "ArrivalDate";
    public static final String DepartureDate = "DepartureDate";
    public static final String StopStatus = "StopStatus";
    public static final String Chain = "Chain";
    public static final String StopCode = "StopCode";
    public static final String StopFullAddress = "StopFullAddress";
    public static final String StopAddressTwo = "StopAddressTwo";
    public static final String StopCountry = "StopCountry";
    public static final String StopCountryCode = "StopCountryCode";
    public static final String StopState = "StopState";
    public static final String StopStateCode = "StopStateCode";
    public static final String EmptyDistance = "EmptyDistance";
        public static final String IsTripPreInspectionCompleted = "IsTripPreInspectionCompleted";
    public static final String TripCurrentStatus = "TripCurrentStatus";
    public static final String Distance = "Distance";
    public static final String TripId = "TripId";


    static String CREATE_TRIPDETAIL = "create table "
            + TABLE_TRIPDETAIL + " (" + TripDetailID + " text primary key , "
            + ActionType + " text, "
            + StopName + " text,"
            + ItemCount + " text,"
            + StopAddressOne + " text,"
            + StopCity + " text,"
            + StopPostalCode + " text,"
            + StopContactPersonName + " text,"
            + StopContactPersonPhone + " text,"
            + DOR + " text,"
            + ArrivalDate + " text,"
            + DepartureDate + " text,"
            + StopStatus + " text,"
            + Chain + " text,"
            + StopCode + " text,"
            + StopFullAddress + " text,"
            + StopAddressTwo + " text,"
            + StopCountry + " text,"
            + StopCountryCode + " text,"
            + StopState + " text,"
            + StopStateCode + " text,"
            + EmptyDistance + " text,"
            + DistanceUnit + " text,"
            + TripCurrentStatus + " text,"
            + Distance + " text,"
            + TripId + " text ,"
            + DriverId + " text ,"
      + IsTripPreInspectionCompleted + " text );";


    public static final String TABLE_NOTIFICATION = "NotificationTable";
    public static final String NOTIFICATION_ID = "TripID";
    public static final String TripID1 = "TripNumber";
    public static final String MESSAGE = "Message";
    public static final String DATE = "Date";
    public static final String SENDER = "Sender";
    public static final String ISREAD = "Isread";
    public static final String TYPE = "Type";


    //    {id=0, time=5/30/2019 11:13:28 AM, type=General, message=sfsdf}
//
    static String CREATE_NOTIFICATION = "create table "
            + TABLE_NOTIFICATION + " (" + NOTIFICATION_ID + " integer primary key autoincrement, "
            + TripID1 + " text,"
            + MESSAGE + " text,"
            + TYPE + " text,"
            + DATE + " text,"
            + SENDER + " text,"
            + ISREAD + " text,"
            + DriverId + " text );";


    public static final String TABLE_IMAGES = "ImageTable";
    public static final String ID = "ID";
    public static final String IMAGE_URL = "ImageUrl";
    public static final String IMAGE_IsUpload = "IsUpload";
    public static final String DocumentTypeCode = "DocumentTypeCode";
    public static final String ModuleRefCode = "ModuleRefCode";
    public static final String ModuleTypeCode = "ModuleTypeCode";
    public static final String ParentModuleRefCode = "ParentModuleRefCode";
    public static final String ParentModuleTypeCode = "ParentModuleTypeCode";
    public static final String ImageActionType = "ImageActionType";
    public static final String MobileAppCode = "MobileAppCode";
    public static final String ItemCode = "ItemCode";


    //    {id=0, time=5/30/2019 11:13:28 AM, type=General, message=sfsdf}
//
    static String CREATE_IMAGES = "create table "
            + TABLE_IMAGES + " (" + ID + " integer primary key autoincrement, "
            + ImageActionType + " text,"
            + DocumentTypeCode + " text,"
            + IMAGE_URL + " text,"
            + ModuleRefCode + " text,"
            + ModuleTypeCode + " text,"
            + MobileAppCode + " text,"
            + IMAGE_IsUpload + " text,"
            + DriverId + " text,"
            + ItemCode + " text,"
            + ParentModuleRefCode + " text,"
            + ParentModuleTypeCode + " text,"
            + TripId + " text );";


    public static final String TABLE_ORDER = "OrderTable";
    public static final String OrderID = "OrderID";
    public static final String OrderNumber = "OrderNumber";
    public static final String ImageCount = "ImageCount";
    public static final String TripDetailId = "TripDetailId";
    public static final String Type = "Type";


    //    {id=0, time=5/30/2019 11:13:28 AM, type=General, message=sfsdf}
//
    static String CREATE_ORDER = "create table "
            + TABLE_ORDER + " (" + OrderID + " text , "
            + OrderNumber + " text,"
            + ImageCount + " text,"
            + TripDetailId + " text,"
            + TripId + " text,"
            + Type + " text,"
            + DriverId + " text, primary key (" + OrderID + "," + TripDetailId + ") );";


    public static final String TABLE_ITEMS = "ItemsTable";
    public static final String CommodityName = "CommodityName";
    public static final String Weight = "Weight";
    public static final String WeightUnit = "WeightUnit";
    public static final String Quantity = "Quantity";
    public static final String QuantityUnit = "QuantityUnit";
    public static final String PickupFullAddress = "PickupFullAddress";
    public static final String DeliveryFullAddress = "DeliveryFullAddress";
    public static final String ItemStatus = "ItemStatus";
    public static final String StopType = "StopType";

    public static final String VinNumber ="VinNumber";
    public static final String Model ="Model";
    public static final String Make ="Make";
    public static final String Year ="Year";
    public static final String MileageValue ="MileageValue";
    public static final String MileageUnit ="MileageUnit";
    public static final String DeclaredValue ="DeclaredValue";
    public static final String DeclaredCurrency ="DeclaredCurrency";
    public static final String IsPreinspectionDone ="IsPreinspectionDone";



    //    {id=0, time=5/30/2019 11:13:28 AM, type=General, message=sfsdf}
//
    static String CREATE_ITEMS = "create table "
            + TABLE_ITEMS + " (" + ItemCode + " text , "
            + CommodityName + " text,"
            + Distance + " text,"
            + DistanceUnit + " text,"
            + Weight + " text,"
            + WeightUnit + " text,"
            + Quantity + " text,"
            + QuantityUnit + " text,"
            + PickupFullAddress + " text,"
            + DeliveryFullAddress + " text,"
            + ItemStatus + " text,"
            + ImageCount + " text,"
            + TripDetailId + " text,"
            + TripId + " text,"
            + StopType + " text,"
            + DriverId + " text,"
            + OrderNumber + " text ,"
            + VinNumber + " text,"
            + Model + " text,"
            + Make + " text,"
            + Year + " text,"
            + MileageValue + " text,"
            + MileageUnit + " text,"
            + DeclaredValue + " text,"
            + DeclaredCurrency + " text,"
            + IsPreinspectionDone + " text , primary key (" + ItemCode + "," + TripDetailId + "));";

}
