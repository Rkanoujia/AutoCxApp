package com.avaal.com.afm2020autoCx;

import com.avaal.com.afm2020autoCx.adapter.AppSettingModel;
import com.avaal.com.afm2020autoCx.models.AddCityZipModel;
import com.avaal.com.afm2020autoCx.models.AddLocationModel;
import com.avaal.com.afm2020autoCx.models.ChangePasswordModel;
import com.avaal.com.afm2020autoCx.models.CompanyProfileModel;
import com.avaal.com.afm2020autoCx.models.ConfirmOrderModel;
import com.avaal.com.afm2020autoCx.models.DashBoardModel;
import com.avaal.com.afm2020autoCx.models.DropDownModel;
import com.avaal.com.afm2020autoCx.models.ForAddModel;
import com.avaal.com.afm2020autoCx.models.ForgetPasswordModel;
import com.avaal.com.afm2020autoCx.models.GetImageDetailmodel;
import com.avaal.com.afm2020autoCx.models.GetImageModel;
import com.avaal.com.afm2020autoCx.models.GetInvVehicleModel;
import com.avaal.com.afm2020autoCx.models.GetLocationDetailModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdModel;
import com.avaal.com.afm2020autoCx.models.InventoryOrderModel;
import com.avaal.com.afm2020autoCx.models.ItemListModel;
import com.avaal.com.afm2020autoCx.models.LocationDetailModel;
import com.avaal.com.afm2020autoCx.models.LoginModel;
import com.avaal.com.afm2020autoCx.models.LogoutModel;
import com.avaal.com.afm2020autoCx.models.LookUpModel;
import com.avaal.com.afm2020autoCx.models.MasterDropDownModel;
import com.avaal.com.afm2020autoCx.models.MilesModel;
import com.avaal.com.afm2020autoCx.models.OrderDetailModel;
import com.avaal.com.afm2020autoCx.models.OrderListModel;
import com.avaal.com.afm2020autoCx.models.PrimaryInfoDetailModel;
import com.avaal.com.afm2020autoCx.models.ProfileDataModel;
import com.avaal.com.afm2020autoCx.models.RemoveImageModel;
import com.avaal.com.afm2020autoCx.models.RemoveOrderModel;
import com.avaal.com.afm2020autoCx.models.RemoveVehicleModel;
import com.avaal.com.afm2020autoCx.models.RouteSelectionModel;
import com.avaal.com.afm2020autoCx.models.SaveImageModel;
import com.avaal.com.afm2020autoCx.models.TripListModel;
import com.avaal.com.afm2020autoCx.models.VehicleInfoModel;
import com.avaal.com.afm2020autoCx.models.VinDetailModel;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by dell pc on 23-02-2018.
 */

public interface APIInterface {
    //    @GET("/api/unknown")
//    Call<MultipleResource> doGetListResources();
     @FormUrlEncoded
    @POST("/token")
    Call<LoginModel> userLogin(@Field ("username") String username,@Field("password") String password,@Field("grant_type") String grant_type, @Header("Content-Type") String ContentType);

    @GET("api/Common/GetMSTDDLData?")
    Call<DropDownModel> getLocation(@Query("DDLType") String DDLType,@Query("IsCacheRequired") String IsCacheRequired,@Query("UserCode") String UserCode,@Query("CorporateId") String CorporateId,@Query("Filter1") String Filter1,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @GET("/api/masters/PrimaryInfo/get?")
    Call<PrimaryInfoDetailModel> getPrimaryInfoDetail(@Query("Code") String Code, @Query("CorporateId") String CorporateId, @Header("Authorization") String authorization, @Header("Content-Type") String contentType);




    @FormUrlEncoded
    @POST("/api/masters/PrimaryInfo/post")
    Call<ForAddModel> addPrimaryInfo(@Field("Type") String Type,@Field("PrimaryInfoCode") String PrimaryInfoCode, @Field("CompanyCode") String CompanyCode,@Field("Name") String Name,
                                     @Field("AddressOne") String AddressOne,@Field("CountryCode") String CountryCode,
                                     @Field("StateCode") String StateCode, @Field("PostalCode") String PostalCode, @Field("City") String City,
                                     @Field("IsCarrier") Boolean IsCarrier, @Field("IsCustomer") Boolean IsCustomer, @Field("IsLocation") Boolean IsLocation,@Field("CreatedBy") String CreatedBy , @Field("Notes") String Notes,
                                     @Field("CorporateId") String CorporateId, @Field("CallSource") String CallSource, @Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @GET("/api/Common/GetMSTDDLData?")
    Call<MasterDropDownModel> getMasterDropDown(@Query("DDLType") String DDLType, @Query("IsCacheRequired") Boolean IsCacheRequired, @Query("UserCode") String UserCode, @Query("Filter1") String Filter1, @Query("Filter2") String Filter2, @Query("CorporateId") String CorporateId, @Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @GET("/api/Common/GetMstLookUpData?")
    Call<LookUpModel> getGetMstLookUpDataDropDown(@Query("DDLType") String DDLType, @Query("IsCacheRequired") Boolean IsCacheRequired, @Query("UserCode") String UserCode, @Query("Filter1") String Filter1, @Query("Filter2") String Filter2, @Query("CorporateId") String CorporateId, @Header("Authorization") String authorization, @Header("Content-Type") String contentType);


//    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/CustomerApp/GetVehicleInformation")
    Call<VinDetailModel> getVinDetail(@Query("VinNumber") String VinNumber,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

//    public bool IsShipped
//    public DateTime ShippedDateTime
//    public bool IsReceived
//    public DateTime ReceivedDateTime
//    public string ReceivedBy
//    public DateTime CreatedOn
//    public string CreatedBy

    @FormUrlEncoded
    @POST("/api/CustomerApp/SaveTempLoad")
    Call<RouteSelectionModel> getTempIds(@Field("TempOrderID") String TempOrderID,@Field("OrderType") String OrderType,@Field("PickupCode") String PickupCode,@Field("PickupDateTime") String PickupDateTime,
            @Field("PrimaryInfoCode") String PrimaryInfoCode,@Field("DropCode") String DropCode,@Field("DropDateTime") String DropDateTime,@Field("PickupNotes") String PickupNotes,
                                         @Field("Distance") String Distance,@Field("DistanceUnit") String DistanceUnit,
                                         @Field("DropNotes") String DropNotes,@Field("StopCode") String StopCode,@Field("StopDateTime") String StopDateTime,@Field("StopNotes") String StopNotes,@Field("IsSaved") String IsSaved,@Field("IsDeleted") String IsDeleted,
             @Field("FreightTypeLuCode") String FreightTypeLuCode,
             @Field("CreatedBy") String CreatedBy,@Field("CreatedOn") String CreatedOn,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST("/api/CustomerApp/SaveVehicle")
    Call<VehicleInfoModel> saveVehicle(@Field("VehicleID") String VehicleID,@Field("TempOrderID") String TempOrderID,@Field("VinNumber") String VinNumber,@Field("Model") String Model,@Field("Make") String Make,
                                       @Field("Year") String mYear,@Field("OemTag") String OemTag,@Field("MileageValue") String MileageValue,@Field("MileageUnit") String MileageUnit,@Field("TPMS") String TPMS,
                                       @Field("DeclaredValue") String DeclaredValue,@Field("BuildYear") String BuildYear,@Field("BuildMonth") String BuildMonth,@Field("GVWRValue") String GVWRValue,@Field("GVWRUnit") String GVWRUnit,
                                       @Field("Diesel") String Diesel,@Field("SpeedoConversion") String SpeedoConversion,@Field("TitleConversion") String TitleConversion,@Field("Title") String Title,
                                       @Field("BillOfSale") String BillOfSale,@Field("TrackingConfirmation") String TrackingConfirmation,@Field("SavedItemID") String SavedItemID,@Field("IsInventory") String IsInventory,
                                       @Field("InventoryDate") String InventoryDate,@Field("OrderDate") String OrderDate,@Field("CustomerCode") String CustomerCode,@Field("IsReleaseForm") String IsReleaseForm,@Field("Color") String Color,
                                       @Field("DeclaredCurrency") String DeclaredCurrency,@Field("Latitude") String Latitude,@Field("Longitude") String Longitude,
                                       @Field("CreatedBy") String CreatedBy,@Field("CreatedOn") String CreatedOn,@Field("CorporateId") String CorporateId,
                                       @Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @POST("/Api/orderitem/GetTempVehicleID")
    Call<GetVehicleIdModel> getTempVehicleIds(@Body GetVehicleIdModel user);

    @POST("/Api/OrderItem/GetVehiclesByTempOrderID")
    Call<GetVehicleIdListModel> getTempVehicleList(@Body GetVehicleIdListModel user);



    @POST("/api/CustomerApp/UploadVehicleDocuments")
    Call<SaveImageModel> saveImages(@Body SaveImageModel user,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @Multipart
    @POST("/Api/Document/Upload")
    Call<SaveImageModel> updateProfile(@Part("TempVehicleID") RequestBody id, @Part("FileName") RequestBody fileName,@Part("AuthKey") RequestBody authKey,@Part("ContentType") RequestBody contentType,@Part("DocumentType") RequestBody DocType,@Part("Extension") RequestBody extension,@Part("Size") RequestBody size,@Part("Remarks") RequestBody fullName, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("/api/CustomerApp/SaveTempLoad")
    Call<GetVehicleIdModel> saveLoads(@Field("TempOrderID") String TempOrderID,@Field("IsSaved") String IsSaved,@Field("CreatedBy") String CreatedBy,@Field("CreatedOn") String CreatedOn,@Field("CorporateId") String CorporateId,@Field("CustomerCode") String CustomerCode,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);



    @FormUrlEncoded
    @POST("/api/CustomerApp/ShipLoad")
    Call<GetVehicleIdModel> shipLoads(@Field("Id") String Id,@Field("CreatedBy") String CreatedBy,@Field("CreatedOn") String CreatedOn,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

//    @POST("/Api/orderitem/GetOrdersByCustomer")
//    Call<OrderListModel> getOrderList(@Body OrderListModel user);

@GET("/api/CustomerApp/GetLoadListByStatus?")
Call<OrderListModel> getOrderList(@Query("Status") String Status,@Query("CorporateId") String CorporateId,@Query("CustomerCode") String CustomerCode,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @FormUrlEncoded
    @POST("/api/CustomerApp/PostAlerts")
    Call<ForAddModel> saveAlerts(@Field("NotificationID") String UserNotificationID,@Field("TripID") String TripID,@Field("DateSent") String DateSent,@Field("FromSource") String FromSource,@Field("ToDestination") String ToDestination,
                                 @Field("SourceModule") String SourceModule,@Field("DestinationModule") String DestinationModule,@Field("RefType") String RefType,@Field("RefCode") String RefCode,@Field("SenderName") String SenderName,
                                 @Field("NotificationType") String NotificationType,@Field("NotificationTitle") String NotificationTitle,@Field("NotificationSubType") String NotificationSubType,@Field("Status") String Status,
                                 @Field("ReceiverRefType") String ReceiverRefType,@Field("ReceiverRefCode") String ReceiverRefCode,@Field("ReceiverName") String ReceiverName,@Field("NotificationText") String NotificationText,
                                 @Field("CallSource") String CallSource,@Field("CreatedBy") String CreatedBy,@Field("CreatedOn") String CreatedOn, @Field("CorporateId") String CorporateId, @Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @GET("/api/CustomerApp/GetOrderDetailsByStatus")
    Call<OrderDetailModel> getOrderDetail(@Query("tempOrderId") String tempOrderId,@Query("OrderStatus") String OrderStatus,@Query("CustomerCode") String CustomerCode,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST("/api/CustomerApp/DeleteVehicle")
    Call<RemoveVehicleModel> removeVehicle(@Field("Id") String Id,@Field("CorporateId") String CorporateId,@Field("CustomerCode") String CustomerCode, @Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @POST("/Api/Document/GetDocListByVehicle")
    Call<GetImageModel> getImages(@Body GetImageModel user);

    @GET("/api/CustomerApp/GetCustomerDetails?")
    Call<ProfileDataModel> getProfile(@Query("CorporateId") String CorporateId,@Query("UserId") String UserId, @Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @GET("/api/Masters/Company/Get?")
    Call<CompanyProfileModel> getCompanyProfile(@Query("CorporateId") String CorporateId,@Query("CompanyCode") String CompanyCode,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @GET("/api/CustomerApp/GetDashBoardData")
    Call<DashBoardModel> getDashBoard(@Query("CustomerCode") String CustomerCode,@Query("FromDate") String FromDate,@Query("ToDate") String ToDate,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @POST("/api/orderitem/GetOrderStatus")
    Call<TripListModel> getTripList(@Body TripListModel page);

    @POST("/Api/OrderItem/GetVehiclesByOrder")
    Call<ItemListModel> getItemList(@Body ItemListModel page);

    @GET("/api/CustomerApp/GetVehicleList")
    Call<GetVehicleIdListModel> getInventoryVehicleList(@Query("CorporateId") String CorporateId,@Query("CustomerCode") String CustomerCode,@Query("TempOrderId") String TempOrderId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST("/api/CustomerApp/DeleteTempOrder")
    Call<RemoveOrderModel> removeOrder(@Field("Id") String Id,@Field("CustomerCode") String CustomerCode,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);



    @FormUrlEncoded
    @POST("/api/CustomerApp/SaveVehicle")
    Call<GetInvVehicleModel> getTempInvVehicleIds(@Field("VehicleID") String VehicleID,@Field("TempOrderID") String TempOrderID,@Field("VinNumber") String VinNumber,@Field("Model") String Model,@Field("Make") String Make,
                                                  @Field("Year") String mYear,@Field("OemTag") String OemTag,@Field("MileageValue") String MileageValue,@Field("MileageUnit") String MileageUnit,@Field("TPMS") String TPMS,
                                                  @Field("DeclaredValue") String DeclaredValue,@Field("BuildYear") String BuildYear,@Field("BuildMonth") String BuildMonth,@Field("GVWRValue") String GVWRValue,@Field("GVWRUnit") String GVWRUnit,
                                                  @Field("Diesel") String Diesel,@Field("SpeedoConversion") String SpeedoConversion,@Field("TitleConversion") String TitleConversion,@Field("Title") String Title,
                                                  @Field("BillOfSale") String BillOfSale,@Field("TrackingConfirmation") String TrackingConfirmation,@Field("SavedItemID") String SavedItemID,@Field("IsInventory") String IsInventory,
                                                  @Field("InventoryDate") String InventoryDate,@Field("OrderDate") String OrderDate,@Field("CustomerCode") String CustomerCode,@Field("IsReleaseForm") String IsReleaseForm,@Field("CreatedBy") String CreatedBy,@Field("CreatedOn") String CreatedOn,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);
    @FormUrlEncoded
    @POST("api/CustomerApp/AddVehicleFromInventory")
    Call<InventoryOrderModel> orderFromInventory(@Field("Id") String Id,@Field("Code") String Code,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @GET("/api/CustomerApp/GetDocumentList")
    Call<GetImageModel> getImage(@Query("VehicleId") String VehicleId,@Query("DocumentType") String DocumentType,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST("/api/CustomerApp/SetCustomerConfig")
    Call<AppSettingModel> appSetting(@Field("IsNotifyByText") String IsNotifyByText,@Field("IsNotifyByEmail") String IsNotifyByEmail,@Field("IsNotifyByApp") String IsNotifyByApp,@Field("UserId") String UserId,@Field("CreatedBy") String CreatedBy,@Field("CreatedOn") String CreatedOn,@Field("CustomerCode") String CustomerCode,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @GET("api/CustomerApp/GetCustomerConfig?")
    Call<AppSettingModel> getappSetting(@Query("CustomerCode") String CustomerCode,@Query("UserId") String UserId,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST("/api/CustomerApp/LogOut")
    Call<LogoutModel> logOut(@Field("Code") String Code,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST("/api/CustomerApp/ResetPassword")
    Call<ChangePasswordModel> changePassword(@Field("UserId") String UserId,@Field("OldPassword") String OldPassword,@Field("NewPassword") String NewPassword,@Field("value") String value,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST("/api/CustomerApp/ForgotPassword")
    Call<ForgetPasswordModel> forgetPswrd(@Field("UserId") String userName,@Field("RefType") String RefType,@Field("CorporateId") String CorporateId,@Header("Content-Type") String contentType);

    @POST("Api/AutoOrder/Confirm")
    Call<ConfirmOrderModel> confirOrder(@Body ConfirmOrderModel user);

    @FormUrlEncoded
    @POST("/api/CustomerApp/DeleteVehicleDocuments")
    Call<RemoveImageModel> deleteImage(@Field("DocumentID") String Id,@Field("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);


    @GET("/api/CustomerApp/GetAFMLoadListByStatus?")
    Call<OrderListModel> getAFMLoadListByStatus(@Query("OrderID") String OrderID,@Query("Status") String Status,@Query("CustomerCode") String CustomerCode,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @GET("/api/CustomerApp/GetTripTrackingByOrder?")
    Call<TripListModel> getTripTrackingByOrder(@Query("OrderNumber") String OrderNumber,@Query("CustomerCode") String CustomerCode,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @GET("/api/CustomerApp/GetAFMVehicleList?")
    Call<GetVehicleIdListModel> getAFMOrderVehicleList(@Query("OrderId") String OrderId,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @GET("/api/CustomerApp/GetAFMVehicleList?")
    Call<ItemListModel> getAFMVehicleList(@Query("OrderId") String OrderId,@Query("CustomerCode") String CustomerCode,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);

    @GET("/api/CustomerApp/GetAFMDocumentList")
    Call<GetImageModel> getAFMImage(@Query("ItemCode") String ItemCode,@Query("DocumentType") String DocumentType,@Query("CorporateId") String CorporateId,@Header("Authorization") String authorization, @Header("Content-Type") String contentType);


    @GET("/api/Common/GetTotalMiles?")
    Call<MilesModel> getTotalMiles(@Query("Origin_LocCode") String Origin_LocCode, @Query("Destination_LocCode") String Destination_LocCode, @Query("CorporateId") String CorporateId, @Header("Authorization") String authorization, @Header("Content-Type") String contentType);


}
