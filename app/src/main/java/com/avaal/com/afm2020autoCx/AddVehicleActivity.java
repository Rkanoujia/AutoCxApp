package com.avaal.com.afm2020autoCx;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.VehicleListAdapter;
import com.avaal.com.afm2020autoCx.barcode.BarcodeCaptureActivity;
import com.avaal.com.afm2020autoCx.models.DropDownModel;
import com.avaal.com.afm2020autoCx.models.GetImageDetailmodel;
import com.avaal.com.afm2020autoCx.models.GetImageModel;
import com.avaal.com.afm2020autoCx.models.GetInvVehicleModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdModel;
import com.avaal.com.afm2020autoCx.models.LookUpModel;
import com.avaal.com.afm2020autoCx.models.MakeModel;
import com.avaal.com.afm2020autoCx.models.MasterDropDownModel;
import com.avaal.com.afm2020autoCx.models.RemoveImageModel;
import com.avaal.com.afm2020autoCx.models.SaveImageModel;
import com.avaal.com.afm2020autoCx.models.VehicleInfoModel;
import com.avaal.com.afm2020autoCx.models.VehicleModelModel;
import com.avaal.com.afm2020autoCx.models.VinDetailModel;
import com.avaal.com.afm2020autoCx.tabtargetview.TapTarget;
import com.avaal.com.afm2020autoCx.tabtargetview.TapTargetView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import extra.GPSTrackerService;
import extra.LatLongCheckListner;
import extra.LoaderScreen;
import extra.MarshMallowPermission;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 07-03-2018.
 */

public class AddVehicleActivity extends AppCompatActivity implements LatLongCheckListner {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.vin)
    EditText vinNo;
    @BindView(R.id.model)
    AutoCompleteTextView model;
    @BindView(R.id.make)
    AutoCompleteTextView make;
    @BindView(R.id.year)
    AutoCompleteTextView year;
    @BindView(R.id.title1)
    LinearLayout title1;
    @BindView(R.id.bill)
    LinearLayout bill;
    @BindView(R.id.tracking)
    LinearLayout tracking;
    @BindView(R.id.oem_tag)
    LinearLayout oem_tag;
    @BindView(R.id.millage)
    LinearLayout millage;
    @BindView(R.id.tpms)
    LinearLayout tpms;
    @BindView(R.id.declared)
    LinearLayout declared;
    @BindView(R.id.build)
    LinearLayout build;
    @BindView(R.id.gvwr)
    LinearLayout gvwr;
    @BindView(R.id.diesel)
    LinearLayout diesel;
    @BindView(R.id.speedo)
    LinearLayout speedo;
    @BindView(R.id.release_form)
    LinearLayout release_form;
    @BindView(R.id.title_conv)
    LinearLayout title_conv;
    @BindView(R.id.bill_txt)
    Spinner bill_txt;
    @BindView(R.id.title_txt)
    Spinner title_txt;
    @BindView(R.id.tracking_txt)
    EditText tracking_txt;
    @BindView(R.id.oem_tag_txt)
    Spinner oem_tag_txt;
    @BindView(R.id.release_form_txt)
    Spinner release_form_txt;
    @BindView(R.id.millage_spinner)
    Spinner millage_spinner;
    @BindView(R.id.millage_txt)
    EditText millage_txt;
    @BindView(R.id.tpms_txt)
    Spinner tpms_txt;
    @BindView(R.id.declared_txt)
    EditText declared_txt;
    @BindView(R.id.build_month)
    Spinner build_month;
    @BindView(R.id.build_year)
    AutoCompleteTextView build_year;
    @BindView(R.id.gvwr_txt)
    EditText gvwr_txt;
    @BindView(R.id.diesel_txt)
    Spinner diesel_txt;
    @BindView(R.id.speedo_txt)
    Spinner speedo_txt;
    @BindView(R.id.title_conv_txt)
    Spinner title_conv_txt;
    @BindView(R.id.title_txt_img)
    ImageView title_txt_img;
    @BindView(R.id.title_conv_txt_img)
    ImageView title_conv_txt_img;
    @BindView(R.id.oem_tag_txt_img)
    ImageView oem_tag_txt_img;
    @BindView(R.id.release_form_txt_img)
    ImageView release_form_txt_img;
    @BindView(R.id.millage_txt_img)
    ImageView millage_txt_img;
    @BindView(R.id.tpms_txt_img)
    ImageView tpms_txt_img;
    @BindView(R.id.bill_txt_img)
    ImageView bill_txt_img;
    @BindView(R.id.tracking_txt_img)
    ImageView tracking_txt_img;
    @BindView(R.id.title_txt_img_view)
    ImageView title_txt_img_view;
    @BindView(R.id.title_conv_txt_img_view)
    ImageView title_conv_txt_img_view;
    @BindView(R.id.oem_tag_txt_img_view)
    ImageView oem_tag_txt_img_view;
    @BindView(R.id.release_form_txt_img_view)
    ImageView release_form_txt_img_view;
    @BindView(R.id.millage_txt_img_view)
    ImageView millage_txt_img_view;
    @BindView(R.id.tpms_txt_img_view)
    ImageView tpms_txt_img_view;
    @BindView(R.id.bill_txt_img_view)
    ImageView bill_txt_img_view;
    @BindView(R.id.tracking_txt_img_view)
    ImageView tracking_txt_img_eye;
    @BindView(R.id.save_vehicle)
    TextView save_vehicle;
    @BindView(R.id.gvwr_spinner)
    Spinner gvwr_spinner;
    @BindView(R.id.currency_spinner)
    Spinner currency_spinner;
    @BindView(R.id.bol)
    TextView saveBol;
    @BindView(R.id.scan)
    TextView scan;
    @BindView(R.id.color_txt)
    TextView color_txt;
    @BindView(R.id.recall1_txt_img_view)
    ImageView recall1_txt_img_eye;
    @BindView(R.id.recall2_txt_img_view1)
    ImageView recall2_txt_img_eye;
    @BindView(R.id.title_txt_img_view1)
    ImageView title_txt_img_view1;
    @BindView(R.id.title_txt_img1)
    ImageView title_txt_img1;
    @BindView(R.id.title_conv_txt_img1)
    ImageView title_conv_txt_img1;
    @BindView(R.id.title_conv_txt_img_view1)
    ImageView title_conv_txt_img_view1;

    @BindView(R.id.recall1_txt_img)
    ImageView recall1_txt_img;
    @BindView(R.id.recall2_txt_img1)
    ImageView recall2_txt_img1;
    @BindView(R.id.recall_sheet)
            LinearLayout recall_sheet;
    @BindView(R.id.title_conv_)
            LinearLayout title_conv_;
    @BindView(R.id.lat)
    TextView lat;
    @BindView(R.id.longi)
            TextView longi;
    @BindView(R.id.save_new)
            TextView save_new;
    APIInterface apiInterface;
   Boolean preInspection=true;
    VehicleApiInterface vehicleApiInterface;
    ArrayList<String> yearList = new ArrayList<>();
    ArrayList<String> makeList = new ArrayList<>();
    ArrayList<String> modelList = new ArrayList<>();
    ArrayList<String> milegeList = new ArrayList<>();
    ArrayList<String> milegeCode = new ArrayList<>();
    ArrayList<String> listValue = new ArrayList<>();
    ArrayList<String> currency = new ArrayList<>();
    ArrayList<String> listValueGvwrId = new ArrayList<>();
    ArrayList<String> listValueGvwr = new ArrayList<>();
    ArrayList<String> listBuildYaer = new ArrayList<>();
    ArrayList<String> listBuildMonth = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter4;
    PreferenceManager prf;
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final int PREINSPECTION=101;
    String imageFilePath, imageType, qWvrUnit;
    String buildMonth="", buildYear="",miliage="",fullAddress="";
    String oemStr, tpmsStr, dieselStr, speedoStr, titleconvStr, billStr, titleStr, trackStr,releaseFormStr,currency_txt;
    private String trackUrl="", billUrl="", titleConvUrl="", tpmsUrl="", millageUrl="", oemUrl="", releaseFromUrl="",titleUrl="", titleUrl1="", titleConvUrl1="",imgId;
    private String trackId="0", billId="0", titleConvId="0", tpmsId="0", millageId="0", oemId="0",releaseFromId="0", titleId="0", titleId1="0", titleConvId1="0",recall2Url="",recall2Id="0",recall1Url="",recall1Id="0";
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1771;
    Boolean inventory=false;
    Location location;
    String orderId="0",vehicleId="0";
    ProgressDialog pd;
    boolean IsNew=false;
    ArrayList<String> myList=new ArrayList<>();
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehicle_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        title.setText("Vehicle Details");

        listValue.add("No");
        listValue.add("Yes");
        currency.add("CAD");
        currency.add("USD");
        listBuildYaer.add(" ");


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            marshmallow.checkLocationPermission();
//               marshmallow.checkPermissionForExternalStorage();
//                    marshmallow.checkPermissionForCall();
//            marshmallow.checkPermissionForCamera();
            ActivityCompat.requestPermissions(AddVehicleActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.CAMERA}, 1);


//            marshmallow.checkPermissionForExternalStorage();
        }

        prf = new PreferenceManager(this);
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(prf.getStringData("ScanTutorial").equalsIgnoreCase(""))
        tutorial();
        try {
            showAnimation();
            qwvrList("WeightUnit");
            milegeList("DTC");
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }

//        if(getIntent().getStringExtra("OrderId").equalsIgnoreCase("0"))
            orderId=getIntent().getStringExtra("OrderId");
//        else
//            orderId= prf.getStringData("OrderId");
         vehicleId=getIntent().getStringExtra("VehicleId");

        if (getIntent().getStringExtra("VehicleType") != null)
        {
            if (getIntent().getStringExtra("VehicleType").equalsIgnoreCase("true")) {
//                if (prf.getStringData("OrderStatus").equalsIgnoreCase("save")) {
//                    try {
//                        showAnimation();
//                        getVehicleList(prf.getStringData("OrderId"), "VLSaved");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }else {
                    inventory = true;
                    try {
                        showAnimation();
                        getInventoryVehicle();
                    } catch (Exception paramBundle) {
                        paramBundle.printStackTrace();
                    }
//                }
            }else {

                if (prf.getStringData("OrderStatus").equalsIgnoreCase("Saved")) {
                    try {
                        showAnimation();
                        getVehicleList(orderId, "VLSaved");
                    } catch (Exception e) {
                        new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                        e.printStackTrace();
                    }
                }else if (prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")) {
                    try {
                        showAnimation();
                        getVehicleList(orderId, "VLSaved");
                    } catch (Exception e) {
                        new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        showAnimation();
                        getAFMVehicleList(prf.getStringData("OrderId"));
                    } catch (Exception e) {
                        new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                        e.printStackTrace();
                    }
                }
            }
        }

        int maxLength = 17;
        vinNo.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(maxLength)});

//        vinNo.setFilters(new InputFilter[] {
//                new InputFilter.LengthFilter(maxLength)
//        });
        vinNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                String s = editable.toString();
//                if(!s.equals(s.toUpperCase())) {
//                    s = s.toUpperCase();
//                    vinNo.setText(s);
//                }
                Boolean isup=true;
                if (vinNo.length() >= 8) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        saveBol.setBackgroundColor(getColor(R.color.green));
                        saveBol.setTextColor(getColor(R.color.colorWhite));

                    }
//                    hideSoftKeyboard(AddVehicleActivity.this);
                    saveBol.setClickable(true);
                    saveBol.setEnabled(true);
                    make.setClickable(true);
                    make.setEnabled(true);
                    model.setClickable(true);
                    model.setEnabled(true);
                    year.setClickable(true);
                    year.setEnabled(true);
                    if(vinNo.length()==17) {
                        try {
                            if(!isup)
                            getVehicleDetail(vinNo.getText().toString());
                        } catch (Exception e) {
                            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                            e.printStackTrace();
                        }
                    }
                } else {
                    isup=false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        saveBol.setBackgroundColor(Color.parseColor("#b0aaaa"));
                        saveBol.setTextColor(getColor(R.color.black));
                        make.setClickable(false);
                        make.setEnabled(false);
                        model.setClickable(false);
                        model.setEnabled(false);
                        year.setClickable(false);
                        year.setEnabled(false);
                    }

                }
            }
        });
        vinNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("vihiclevinList");
                if (myList != null) {
                    for (int i = 0; myList.size() > i; i++) {
                        if (myList.get(i).equalsIgnoreCase(vinNo.getText().toString())) {
                            MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "Already exist VIN Number", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                            mdToast.show();
                            return;
                        }
                    }
                }

                if(vinNo.length() >= 8) {
                    try {
                        getVehicleDetail(vinNo.getText().toString());
                    } catch (Exception e) {
                        new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                        e.printStackTrace();
                    }
                }
                else {
                    if(vinNo.length() != 0) {
                        MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "Enter Valid VIN Number", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                        return;
                    }
                }
            }
        });

        PreferenceManager prf = new PreferenceManager(this);
        if (prf.getStringData("OrderType").equalsIgnoreCase("Export")) {
           tracking.setVisibility(View.GONE);
            bill.setVisibility(View.GONE);
           title1.setVisibility(View.GONE);
            recall_sheet.setVisibility(View.VISIBLE);
            oem_tag.setVisibility(View.VISIBLE);
            millage.setVisibility(View.VISIBLE);
            tpms.setVisibility(View.VISIBLE);
           declared.setVisibility(View.VISIBLE);
            build.setVisibility(View.VISIBLE);
            release_form.setVisibility(View.GONE);
            gvwr.setVisibility(View.VISIBLE);
           diesel.setVisibility(View.VISIBLE);
           speedo.setVisibility(View.VISIBLE);
            title_conv.setVisibility(View.VISIBLE);
        } else if (prf.getStringData("OrderType").equalsIgnoreCase("Import")) {
            oem_tag.setVisibility(View.GONE);
            millage.setVisibility(View.GONE);
            tpms.setVisibility(View.GONE);
            declared.setVisibility(View.VISIBLE);
            build.setVisibility(View.GONE);
            gvwr.setVisibility(View.GONE);
            diesel.setVisibility(View.GONE);
            speedo.setVisibility(View.GONE);
            title_conv.setVisibility(View.GONE);
            title_conv_.setVisibility(View.GONE);
            recall_sheet.setVisibility(View.GONE);
            tracking.setVisibility(View.VISIBLE);
            release_form.setVisibility(View.GONE);
            bill.setVisibility(View.VISIBLE);
        } else {
            model.setImeOptions(View.VISIBLE);
            release_form.setVisibility(View.VISIBLE);
            oem_tag.setVisibility(View.GONE);
            millage.setVisibility(View.GONE);
            tpms.setVisibility(View.GONE);
            declared.setVisibility(View.VISIBLE);
            build.setVisibility(View.GONE);
            gvwr.setVisibility(View.GONE);
            diesel.setVisibility(View.GONE);
            speedo.setVisibility(View.GONE);
            title_conv.setVisibility(View.GONE);
            title_conv_.setVisibility(View.GONE);
            tracking.setVisibility(View.GONE);
            bill.setVisibility(View.GONE);
            title1.setVisibility(View.GONE);
            recall_sheet.setVisibility(View.GONE);
        }
        if ((getIntent().getStringExtra("VehicleType") != null) && getIntent().getStringExtra("VehicleType").equalsIgnoreCase("true"))
        {
            inventory = true;
            tracking.setVisibility(View.GONE);
            bill.setVisibility(View.GONE);
            title1.setVisibility(View.GONE);
            recall_sheet.setVisibility(View.VISIBLE);
            oem_tag.setVisibility(View.VISIBLE);
            millage.setVisibility(View.VISIBLE);
            tpms.setVisibility(View.VISIBLE);
            declared.setVisibility(View.VISIBLE);
            release_form.setVisibility(View.GONE);
            build.setVisibility(View.VISIBLE);
            gvwr.setVisibility(View.VISIBLE);
            diesel.setVisibility(View.VISIBLE);
            speedo.setVisibility(View.VISIBLE);
            title_conv.setVisibility(View.VISIBLE);
            title_conv_.setVisibility(View.VISIBLE);
        }
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear - 15; i <= thisYear + 5; i++) {
            yearList.add("" + i);
        }
        int thisYear1 = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear1 - 20; i <= thisYear1 + 10; i++) {
            listBuildYaer.add("" + i);
        }
        listBuildMonth.add(" ");
        listBuildMonth.add("JAN - 01");
        listBuildMonth.add("FEB - 02");
        listBuildMonth.add("MAR - 03");
        listBuildMonth.add("APR - 04");
        listBuildMonth.add("MAY - 05");
        listBuildMonth.add("JUN - 06");
        listBuildMonth.add("JUL - 07");
        listBuildMonth.add("AUG - 08");
        listBuildMonth.add("SEP - 09");
        listBuildMonth.add("OCT - 10");
        listBuildMonth.add("NOV - 11");
        listBuildMonth.add("DEC - 12");

        try {
            years();
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }
        try {
            make();
        } catch (Exception e) {
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
            e.printStackTrace();
        }
        try {
            model();
        } catch (Exception e) {
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
            e.printStackTrace();
        }
        spinner1();
        spinner2();
        spinner3();
        spinner4();
        spinner5();
        spinner7();
        spinner8();
        spinner9();
        spinner10();
        spinner11();
        spinner12();
        spinner13();

        GPSTrackerService gpstrack = new GPSTrackerService(AddVehicleActivity.this, this);
        location = gpstrack.getLocation();
        if(location!=null) {
            lat.setText("" + location.getLatitude());
            longi.setText("" + location.getLongitude());
        }else{
            lat.setText("0.000000");
            longi.setText("0.000000");
        }
        pd = new ProgressDialog(AddVehicleActivity.this);
        pd.setMessage("loading..");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (!gpstrack.canGetLocation()){
            pd.show();
        }else{
            location=gpstrack.getLocation();
            getAddress();
        }
//                 if(prf.getStringData("OrderStatus")==null)
//             if(!prf.getStringData("OrderStatus").equalsIgnoreCase("null")){
//                 save_vehicle.setClickable(false);
//                 save_vehicle.setEnabled(false);


//             }
    }
    @OnTouch(R.id.li_touch)
    boolean li_touch(){
        try {
            hideSoftKeyboard(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
@OnClick(R.id.get_lat_long)
 void latlong(){
     Intent intent=new Intent(this,NewMapsActivity.class);
     intent.putExtra("lati",location.getLatitude());
     intent.putExtra("longi",location.getLongitude());
     startActivityForResult(intent,1111);
 }

    void spinner1() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        diesel_txt.setAdapter(adapter);
    }

    void spinner2() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        speedo_txt.setAdapter(adapter);
    }

    void spinner3() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        title_conv_txt.setAdapter(adapter);
    }

    void spinner4() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        tpms_txt.setAdapter(adapter);
    }

    void spinner5() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        oem_tag_txt.setAdapter(adapter);
    }

    void spinner6() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValueGvwr);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        gvwr_spinner.setAdapter(adapter);
        gvwr_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                qWvrUnit = listValueGvwrId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void spinner7() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        bill_txt.setAdapter(adapter);
    }

    void spinner8() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        title_txt.setAdapter(adapter);
    }

    void spinner9() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listBuildYaer);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        build_year.setAdapter(adapter);
    }

    void spinner10() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listBuildMonth);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        build_month.setAdapter(adapter);
    }

    void spinner11() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
//        tracking_txt.setAdapter(adapter);
    }
    void spinner12() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listValue);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        release_form_txt.setAdapter(adapter);
    }
    void spinner13() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, currency);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        currency_spinner.setAdapter(adapter);
    }
    @OnClick(R.id.title_txt_img)
    void title_txt_img() {
//        if(!title_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter Title is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (vinNo.getText().toString().length()< 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "TitleFront";
        imgId=titleId;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }
    }

    @OnClick(R.id.title_txt_img1)
    void title_txt_img1() {
//        if(!title_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter Title is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "TitleBack";
        imgId=titleId1;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }
    }

    @OnClick(R.id.title_conv_txt_img)
    void title_conv() {
//        if(!title_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter Title is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "TitleConversionFront";
        imgId=titleConvId;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.title_conv_txt_img1)
    void title_conv1() {
//        if(!title_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter Title is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "TitleConversionBack";
        imgId=titleConvId1;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.oem_tag_txt_img)
    void oem_tag_txt_img() {
//        if(!oem_tag_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter OEM Tag is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "OEMTag";
        imgId=oemId;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.release_form_txt_img)
    void release_form_txt_img() {
//        if(!oem_tag_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter OEM Tag is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "ReleaseForm";
        imgId=releaseFromId;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }
    }
    @OnClick(R.id.millage_txt_img)
    void millage_txt_img() {
//        if(!millage_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter millage is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "MileageValue";
        imgId=millageId;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tpms_txt_img)
    void tpms_txt_img() {
//        if(!tpms_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter Tpms is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "TPMS";
        imgId=tpmsId;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }
    }

    @OnClick(R.id.bill_txt_img)
    void bill_txt_img() {
//        if(!bill_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter BillOfSale is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "BillOfSale";
        imgId=billId;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }
    }

    @OnClick(R.id.tracking_txt_img)
    void tracking_txt_img() {
//        if(!tracking_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter 72h Tracking is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "IRSNumber";
        imgId=trackId;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.recall1_txt_img)
    void recall1_txt_img() {
//        if(!tracking_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter 72h Tracking is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "RecallSheet1";
        imgId=recall1Id;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.recall2_txt_img1)
    void recall2_txt_img1() {
//        if(!tracking_txt.getText().toString().equalsIgnoreCase("Y")){
//            MDToast mdToast = MDToast.makeText(this, "Enter 72h Tracking is Y", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//            mdToast.show();
//            return;
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if (this.vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        imageType = "RecallSheet2";
        imgId=recall2Id;
        try {
            cameraOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.title_txt_img_view)
    void title_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(titleUrl,titleId,"TitleFront");
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }

    }
    @OnClick(R.id.recall1_txt_img_view)
    void recall1_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(recall1Url,recall1Id,"RecallSheet1");
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }

    }
    @OnClick(R.id.recall2_txt_img_view1)
    void recall2_txt_img_view1() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(recall2Url,recall2Id,"RecallSheet2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.title_txt_img_view1)
    void title_txt_img_view1() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(titleUrl1,titleId1,"TitleBack");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.title_conv_txt_img_view)
    void title_conv_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(titleConvUrl,titleConvId,"TitleConversionFront");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.title_conv_txt_img_view1)
    void title_conv_txt_img_view1() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(titleConvUrl1,titleConvId1,"TitleConversionBack");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.oem_tag_txt_img_view)
    void oem_tag_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(oemUrl,oemId,"OEMTag");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.release_form_txt_img_view)
    void release_form_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(releaseFromUrl,releaseFromId,"ReleaseForm");
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }
    }

    @OnClick(R.id.millage_txt_img_view)
    void millage_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(millageUrl,millageId,"MileageValue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tpms_txt_img_view)
    void tpms_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(tpmsUrl,tpmsId,"TPMS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.bill_txt_img_view)
    void bill_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(billUrl,billId,"BillOfSale");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tracking_txt_img_view)
    void tracking_txt_img_view() {
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        try {
            popUp(trackUrl,trackId,"IRSNumber");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.home_)
    void home() {
        new Util().myIntent(this,NewDashBoardActivity.class);

    }

    @OnClick(R.id.back)
    void back() {
//        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("vihiclevinList");
//        if(myList!=null){
//            if(myList.size()==0){
////                Intent intent=new Intent(this,AddImageActivity.class);
////                intent.putExtra("VehicleId",getIntent().getStringExtra("VehicleId"));
////                startActivity(intent);
//            }
//        }

        if(orderId.equalsIgnoreCase("0")) {
            if(vinNo.getText().length()>7) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to Back?");
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
                        prf.saveStringData("When", "back");
                        finish();

                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }else{
                prf.saveStringData("When", "back");
                finish();
            }
        }else{
            prf.saveStringData("When", "back");
            finish();
        }



    }

    @Override
    public void onBackPressed() {
        // Write your code here
        prf.saveStringData("When", "back");
        finish();
        super.onBackPressed();

//
//
//        if(getIntent().getStringExtra("IsAdd")!=null && vinNo.getText().length()>7) {
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//            // Setting Dialog Message
//            alertDialog.setMessage("Are you sure you want to Back?");
//            // Setting Positive "Yes" Button
//            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//
//                    // Write your code here to invoke YES event
//
//                    doubleBackToExitPressedOnce=false;
//                }
//            });
//
//            // Setting Negative "NO" Button
//            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    // Write your code here to invoke NO event
//                    dialog.cancel();
//                }
//            });
//
//            // Showing Alert Message
//            alertDialog.show();
//        }


    }

    @OnClick(R.id.bol)
    void bol() {
//        hideSoftKeyboard(AddVehicleActivity.this);

        if(!vinNo.getText().toString().matches("[a-zA-Z0-9]+")) {

                MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                mdToast.show();
                return;

        }else{
            if (this.vinNo.getText().length() < 8) {
                MDToast mdToast = MDToast.makeText(this, "Enter Valid Vin", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                mdToast.show();
                return;
            }
        }
        Intent intent = new Intent(this, AddImageActivity.class);
        intent.putExtra("VehicleId", vehicleId);
        intent.putExtra("Vin", ""+vinNo.getText());
        if(inventory)
            intent.putExtra("IsInventry", "1");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,PREINSPECTION);
    }

    @OnClick(R.id.scan)
    void scanBarcode() {
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
//                    statusMessage.setText(R.string.barcode_success);
//                    barcodeValue.setText(barcode.displayValue);
//                    String vinNo1 = barcode.rawValue.substring(barcode.rawValue.length() - 17);

                    if(!new Util().isValidVin(""+barcode.rawValue)) {
                        MDToast mdToast = MDToast.makeText(this, "Invalid Vin #", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                        vinNo.setText("");
                        return;
                    }

                    if (barcode.rawValue.length() > 17)
                        vinNo.setText(barcode.rawValue.substring(barcode.rawValue.length() - 17));
                    else
                        vinNo.setText(barcode.rawValue);
//                    getVehicleDetail(barcode.rawValue);
                    try {
                        getVehicleDetail(vinNo.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                    }
                    Log.d("", "Barcode read: " + barcode.rawValue);
                }
            }
        } else if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (imageFilePath != null) {
                    Log.e("save file path", imageFilePath);
                    showAnimation();
                    try {
                        saveImage(imageType, imageFilePath, vehicleId,imgId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                    }
                }
            }
        }else if (requestCode == PREINSPECTION) {
            if (resultCode == RESULT_OK) {
                String street = data.getStringExtra("IsPreInspection");
                Log.e("preinspection from",street);
                if(street.equalsIgnoreCase("true")){
                    preInspection=false;
                    Log.e("preinspection from retu",""+preInspection);
                }
                if (IsNew) {
                    myList.add("" + vinNo.getText());
                    getVehicleId(orderId);
                } else
                    finish();
            }
        }else if (requestCode == 1111) {
            if (resultCode == RESULT_OK) {
                String street = data.getStringExtra("location");
                if(street.equalsIgnoreCase("true")){
                   lat.setText(""+data.getStringExtra("Latitude"));
                    longi.setText(""+data.getStringExtra("Longitude"));
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void getVehicleDetail(String vinNo1)throws Exception {
        PreferenceManager prf = new PreferenceManager(this);
//        JSONObject js=new JSONObject();
//        try {
//            js.put("AuthKey",prf.getStringData("authKey"));
//            js.put("VinNumber",vinNo1);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Bundle bundle = getIntent().getExtras();
//        ArrayList<GetVehicleIdListModel.datavalue> data2 =getIntent().getSerializableExtra("vihicleList");
//        if(data2!=null) {
//            for (int i = 0; data2.size() > i; i++) {
//                if (data2.get(i).vinNumber.equalsIgnoreCase(vinNo1)) {
//                    MDToast mdToast = MDToast.makeText(this, "Vin Already Exist", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                    mdToast.show();
//                    return;
//                }
//            }
//        }
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        showAnimation();

//        VinDetailModel vindetail = new VinDetailModel(prf.getStringData("authKey"), vinNo1);
        Call<VinDetailModel> call1 = apiInterface.getVinDetail(vinNo1,"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<VinDetailModel>() {
            @Override
            public void onResponse(Call<VinDetailModel> call, Response<VinDetailModel> response) {
                hideAnimation();
                VinDetailModel dropdata = response.body();
                try {
//                        vinNo.setText(dropdata.data.vin);

                        if(dropdata.make==null){
                            MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "VIN Number does not exists", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                            mdToast.show();
                            year.setEnabled(true);
                            year.setClickable(true);
                            model.setEnabled(true);
                            model.setClickable(true);
                            make.setEnabled(true);
                            make.setClickable(true);

                    }else{
                            if(!dropdata.model.trim().equalsIgnoreCase(""))
                            model.setText(dropdata.model.trim());
                            make.setText(dropdata.make.trim());
                            year.setText(dropdata.year.trim());
                        }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                }
            }

            @Override
            public void onFailure(Call<VinDetailModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
@OnClick(R.id.save_new)
void save_new(){
        IsNew=true;
    saveNew();
}
    @OnClick(R.id.save_vehicle)
    void saveData(){
        IsNew=false;
        saveNew();


    }

    void  saveNew(){
        if (vinNo.getText().length() < 8) {
            MDToast mdToast = MDToast.makeText(this, "Enter Valid VIN Number", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        myList = (ArrayList<String>) getIntent().getSerializableExtra("vihiclevinList");
        if (myList != null) {
            for (int i = 0; myList.size() > i; i++) {
                if (myList.get(i).equalsIgnoreCase(vinNo.getText().toString())) {
                    MDToast mdToast = MDToast.makeText(this, "Already exist VIN Number", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                    mdToast.show();
                    return;
                }
            }
        }

        try {
            if (millage_txt.getText().length() > 0) {
                if (millage_spinner.getSelectedItem().toString().equalsIgnoreCase("select")) {
                    MDToast mdToast = MDToast.makeText(this, "Select Mileage Unit", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                    mdToast.show();
                    return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }

//        if (millage_spinner.getSelectedItem().toString().equalsIgnoreCase("KM"))
//            miliage = "1";
//        else if (millage_spinner.getSelectedItem().toString().equalsIgnoreCase("Miles"))
//            miliage = "2";
//        else
//            miliage = "";

        if(year.getText().toString().trim().length()!=4 && year.getText().toString().trim().length()!=0){
            MDToast mdToast = MDToast.makeText(this, "Enter Correct Year", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }


        if (build_month.getSelectedItem().toString().equalsIgnoreCase(" "))
            buildMonth = "";
        else
            buildMonth = "" + build_month.getSelectedItemPosition();

//        if (build_year.getSelectedItem().toString().equalsIgnoreCase("0"))
        if(build_year.getText().toString().trim().length()!=4 && build_year.getText().toString().trim().length()!=0){
            MDToast mdToast = MDToast.makeText(this, "Enter Correct Build Year", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        buildYear = build_year.getText().toString();
//        else
//            buildYear = "" + build_year.getSelectedItem();
        if (gvwr_txt.getText().length() > 0) {
            if (gvwr_spinner.getSelectedItem().toString().equalsIgnoreCase("--Select--")) {
                MDToast mdToast = MDToast.makeText(this, "Select GVWR Unit", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                mdToast.show();
                return;
            }
        }
        currency_txt=currency_spinner.getSelectedItem().toString();

        if (oem_tag_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
            oemStr = "true";
        else
            oemStr = "false";

        if (release_form_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
            releaseFormStr = "true";
        else
            releaseFormStr = "false";
        if (tpms_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
            tpmsStr = "true";
        else
            tpmsStr = "false";
        if (diesel_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
            dieselStr = "true";
        else
            dieselStr = "false";
        if (speedo_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
            speedoStr = "true";
        else
            speedoStr = "false";
        if (title_conv_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
            titleconvStr = "true";
        else
            titleconvStr = "false";
        if (bill_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
            billStr = "true";
        else
            billStr = "false";
        if (title_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
            titleStr = "true";
        else
            titleStr = "false";

//        if (tracking_txt.getSelectedItem().toString().equalsIgnoreCase("Yes"))
//            trackStr = "Y";
//        else
//            trackStr = "N";
        Util util = new Util();
        if (!util.isNetworkAvailable(this)) {
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
//        if (prf.getStringData("OrderType").equalsIgnoreCase("Import")) {
//           if(titleUrl.equalsIgnoreCase("") ){
//               MDToast mdToast = MDToast.makeText(this, "Please Add Front Title Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//               mdToast.show();
//               return;
//           }
//            if(titleUrl1.equalsIgnoreCase("") ){
//                MDToast mdToast = MDToast.makeText(this, "Please Add  Back Title Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                mdToast.show();
//                return;
//            }
//        }else if (prf.getStringData("OrderType").equalsIgnoreCase("Export")) {
//            if(titleConvUrl.equalsIgnoreCase("") ){
//                MDToast mdToast = MDToast.makeText(this, "Please Add  Front Title Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                mdToast.show();
//                return;
//            }
//            if(titleConvUrl1.equalsIgnoreCase("") ){
//                MDToast mdToast = MDToast.makeText(this, "Please Add Back Title Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                mdToast.show();
//                return;
//            }
//        }
        if(preInspection){
            Log.e("preinspection",""+preInspection);

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to continue without pre_inspection images?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            try {
                                save( oemStr, tpmsStr, dieselStr, speedoStr, titleconvStr, billStr, titleStr, buildMonth, miliage,inventory,releaseFormStr,currency_txt,true);
                            } catch (Exception e) {
                                e.printStackTrace();
                                new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                            }

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
//                                    Intent j = new Intent(DashBoardBottomMenu.this, DashBoardBottomMenu.class);
//                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                                    j.putExtra("open","home");
//                                    startActivity(j);
                            try {
                                save( oemStr, tpmsStr, dieselStr, speedoStr, titleconvStr, billStr, titleStr, buildMonth, miliage,inventory,releaseFormStr,currency_txt,false);
                            } catch (Exception e) {
                                e.printStackTrace();
                                new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                            }

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
//            MDToast mdToast = MDToast.makeText(this, "Please Add Pre-Inspection Images", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//            mdToast.show();


        }else {
            try {
                save( oemStr, tpmsStr, dieselStr, speedoStr, titleconvStr, billStr, titleStr, buildMonth, miliage,inventory,releaseFormStr,currency_txt,false);
            } catch (Exception e) {
                e.printStackTrace();
                new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
            }
        }
    }
    void save(String oemStr1,String tpmsStr1,String dieselStr1,String speedoStr1,String titleconvStr1,String billStr1,String titleStr1,String buildMonth1,String miliage1,Boolean inventry,String releaseIs,String currency_val,boolean isPreinspect)throws Exception {
        showAnimation();
        PreferenceManager prf = new PreferenceManager(this);
        String orderDate = "",inventryDate="";
        if(inventry){
            inventryDate=new Util().getDateTime();
        }else{
            orderDate=new Util().getDateTime();
        }

        Call<VehicleInfoModel> call1 = apiInterface.saveVehicle(vehicleId,""+orderId, "" + vinNo.getText(), "" + model.getText(), "" + make.getText(), "" + year.getText(), "" + oemStr1, "" + millage_txt.getText(), ""+miliage1,"" + tpmsStr1, "" + declared_txt.getText(), "" + build_year.getText(), ""+buildMonth1,"" + gvwr_txt.getText(), qWvrUnit,"" + dieselStr1, "" + speedoStr1, "" + titleconvStr1, ""+titleStr1,"" + billStr1, "" + tracking_txt.getText(), "" , ""+inventry, ""+inventryDate,""+orderDate ,""+prf.getStringData("userCode"),""+releaseIs,""+color_txt.getText(),""+currency_val,""+lat.getText(),""+longi.getText(),""+ prf.getStringData("userName"),new Util().getDateTime(),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<VehicleInfoModel>() {
            @Override
            public void onResponse(Call<VehicleInfoModel> call, Response<VehicleInfoModel> response) {

                VehicleInfoModel dropdata = response.body();
                hideAnimation();
                try {
                    if (dropdata.status) {
                            if (orderId.equalsIgnoreCase("0")) {
                                MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "vehicle added successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                            } else {
                                MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "vehicle has been updated successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                            }
                            if(isPreinspect){
                                Intent intent = new Intent(AddVehicleActivity.this, AddImageActivity.class);
                                intent.putExtra("VehicleId", vehicleId);
                                intent.putExtra("Vin", ""+vinNo.getText());
                                if(inventory)
                                    intent.putExtra("IsInventry", "1");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivityForResult(intent,PREINSPECTION);
                            }else {
                                if (IsNew) {
                                    myList.add("" + vinNo.getText());
                                    getVehicleId(orderId);
                                } else
                                    finish();
                            }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                }
            }

            @Override
            public void onFailure(Call<VehicleInfoModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void save1(String oemStr1,String tpmsStr1,String dieselStr1,String speedoStr1,String titleconvStr1,String billStr1,String titleStr1,String buildMonth1,String miliage1,Boolean inventry,String releaseIs,String currency_val)throws Exception {
        showAnimation();
        String orderDate = "",inventryDate="";
        if(inventry){
            inventryDate=new Util().getDateTime();
        }else{
            orderDate=new Util().getDateTime();
        }
        PreferenceManager prf = new PreferenceManager(this);
//        VehicleInfoModel vindetail = new VehicleInfoModel(prf.getStringData("authKey"), getIntent().getStringExtra("VehicleId"), "" + vinNo.getText(), "" + make.getText(), "" + model.getText(), "" + year.getText(), "" + oemStr1, "" + millage_txt.getText(), "" + tpmsStr1, "" + declared_txt.getText(), "" + build_year.getText(), "" + gvwr_txt.getText(), "" + dieselStr1, "" + speedoStr1, "" + titleconvStr1, "" + billStr1, "" + tracking_txt.getText(), "" + titleStr1, buildMonth1, qWvrUnit, miliage1,inventry,releaseIs);
        Call<VehicleInfoModel> call1 = apiInterface.saveVehicle(vehicleId,""+orderId, "" + vinNo.getText(), "" + model.getText(), "" + make.getText(), "" + year.getText(), "" + oemStr1, "" + millage_txt.getText(), ""+miliage1,"" + tpmsStr1, "" + declared_txt.getText(), "" + build_year.getText(), ""+buildMonth1,"" + gvwr_txt.getText(), qWvrUnit,"" + dieselStr1, "" + speedoStr1, "" + titleconvStr1, ""+titleStr1,"" + billStr1, "" + tracking_txt.getText(), "" , ""+inventry, ""+inventryDate,""+orderDate ,""+prf.getStringData("userCode"),""+releaseIs,""+color_txt.getText(),""+currency_val,""+lat.getText(),""+longi.getText(),""+ prf.getStringData("userName"),new Util().getDateTime(),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");

        call1.enqueue(new Callback<VehicleInfoModel>() {
            @Override
            public void onResponse(Call<VehicleInfoModel> call, Response<VehicleInfoModel> response) {

                VehicleInfoModel dropdata = response.body();
                hideAnimation();
                       try {
                           if (dropdata.status) {
                               if(IsNew){
                                   myList.add(""+vinNo.getText());
                                   getVehicleId(orderId);
                               }
//                    MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, ""+dropdata.Message, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                    mdToast.show();
//                    back();
                           }
                       }catch (Exception e){
                           e.printStackTrace();
                       }
            }

            @Override
            public void onFailure(Call<VehicleInfoModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void years() throws Exception{
        adapter1 = new ArrayAdapter<String>(this,
                R.layout.autocomplete_list_row,
                R.id.text, yearList);
// Specify the layout to use when the list of choices appears
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        year.setThreshold(0);
        year.setAdapter(adapter1);
        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                try {
                    hideSoftKeyboard(AddVehicleActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showAnimation();
                try {
                    getMakeList(year.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    getMakeList(year.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void milege()throws Exception {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner, milegeList);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        millage_spinner.setAdapter(adapter);
        millage_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                miliage = milegeCode.get(i);
//                millage_txt.setText(milegeList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//             adapter4 = new ArrayAdapter<String>(this,
//             android.R.layout.select_dialog_item, milegeList);
//// Specify the layout to use when the list of choices appears
//     adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//     millage_spinner.setAdapter(adapter4);
//     millage_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//         @Override
//         public void onItemClick(AdapterView<?> parent, View view,
//                                 int position, long id) {
//             getMakeList(milegeList.get(position));
//
//
//         }
//     });
    }

    void make()throws  Exception {
        adapter = new ArrayAdapter<String>(this,
                R.layout.autocomplete_list_row,
                R.id.text, makeList);
// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        make.setThreshold(0);
        make.setAdapter(adapter);
        make.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showAnimation();
                try {
                    getModelList(make.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Log.e("select make",make.getText().toString());
                try {
                    hideSoftKeyboard(AddVehicleActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    void model()throws Exception {
        adapter2 = new ArrayAdapter<String>(this,
                R.layout.autocomplete_list_row,
                R.id.text, modelList);
// Specify the layout to use when the list of choices appears
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        model.setThreshold(0);
        model.setAdapter(adapter2);
//     model.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//         @Override
//         public void onItemClick(AdapterView<?> parent, View view,
//                                 int position, long id) {
//
//         }
//     });
    }

    void getMakeList(String year) throws Exception{
        vehicleApiInterface = VehicleAPIClient.getVehicleAPIClient().create(VehicleApiInterface.class);

        Call<MakeModel> call1 = vehicleApiInterface.getMakeVehicle(year, "json");
        call1.enqueue(new Callback<MakeModel>() {
            @Override
            public void onResponse(Call<MakeModel> call, Response<MakeModel> response) {

                MakeModel dropdata = response.body();
                try {
                    hideAnimation();
                makeList.clear();
                if (dropdata.message.equalsIgnoreCase("Response returned successfully")) {
                    for (int i = 0; dropdata.resultV.size() > i; i++) {
                        makeList.add(dropdata.resultV.get(i).makeName);
                    }

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.notifyDataSetChanged();

                        try {
                            make();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<MakeModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

    void getModelList(String makeName)throws Exception {
        vehicleApiInterface = VehicleAPIClient.getVehicleAPIClient().create(VehicleApiInterface.class);

        Call<VehicleModelModel> call1 = vehicleApiInterface.getModelVehicle(makeName, "json");
        call1.enqueue(new Callback<VehicleModelModel>() {
            @Override
            public void onResponse(Call<VehicleModelModel> call, Response<VehicleModelModel> response) {


                try {
                    VehicleModelModel dropdata = response.body();
                    modelList.clear();
                    hideAnimation();
                    if (dropdata.message.equalsIgnoreCase("Response returned successfully")) {
                        for (int i = 0; dropdata.results.size() > i; i++) {
                            modelList.add(dropdata.results.get(i).modelName);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                adapter2.notifyDataSetChanged();
                                try {
                                    model();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                }

            }

            @Override
            public void onFailure(Call<VehicleModelModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getAFMVehicleList(String orderid){
//        GetVehicleIdListModel vindetail1=new GetVehicleIdListModel(prf.getStringData("authKey"),orderid,vihicle,prf.getStringData("carrierPrimaryId"));
        Call<GetVehicleIdListModel> call1 = apiInterface.getAFMOrderVehicleList(""+orderid,""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetVehicleIdListModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdListModel> call, Response<GetVehicleIdListModel> response) {

                GetVehicleIdListModel getdata = response.body();
                try {
                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                        final ArrayList<GetVehicleIdListModel.datavalue> getdata3 = getdata.dataV;
                        if (getdata3.equals("null")) {
                            hideAnimation();
                        }

                        if (getdata3.size() == 0) {
                            hideAnimation();
                        }


                        for (int i = 0; getdata3.size() > i; i++) {
                            if (getdata3.get(i).ItemCode.equalsIgnoreCase(vehicleId)) {
                                try {
                                    save_vehicle.setClickable(false);
                                    save_vehicle.setEnabled(false);
                                    preInspection = false;
                                    inventory = getdata3.get(i).isInventory;
                                    vinNo.setText(getdata3.get(i).vinNumber);
                                    year.setText(getdata3.get(i).year);
                                    make.setText(getdata3.get(i).makeV);
                                    model.setText(getdata3.get(i).model);
                                    oemStr = getdata3.get(i).oem;
                                    color_txt.setText(getdata3.get(i).Color);
                                    if (getdata3.get(i).oem.equalsIgnoreCase("true")) {
                                        oem_tag_txt.setSelection(1);
                                        getAFMImages("OEMTag");
                                    } else
                                        oem_tag_txt.setSelection(0);

                                    releaseFormStr = getdata3.get(i).releasefrom;
                                    if (getdata3.get(i).releasefrom.equalsIgnoreCase("true")) {
                                        release_form_txt.setSelection(1);
                                        getAFMImages("ReleaseForm");
                                    } else
                                        release_form_txt.setSelection(0);

                                    getAFMImages("MileageValue");
                                    tpmsStr = getdata3.get(i).tpms;
                                    dieselStr = getdata3.get(i).diesel;
                                    speedoStr = getdata3.get(i).speedoConversion;
                                    titleconvStr = getdata3.get(i).titleConversion;
                                    billStr = getdata3.get(i).billOfSale;
                                    titleStr = getdata3.get(i).title;
                                    miliage = getdata3.get(i).mileageUnit;
//                            if (getdata3.get(i).mileageUnit.equalsIgnoreCase("2")) {
//                                millage_spinner.setSelection(2);
////                                    getImages("MileageValue");
//                            } else if (getdata3.get(i).mileageUnit.equalsIgnoreCase("1")) {
//                                millage_spinner.setSelection(1);
////                                    getImages("MileageValue");
//                            } else
//                                millage_spinner.setSelection(0);
                                    final int posi = i;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            millage_spinner.setSelection(milegeCode.indexOf(getdata3.get(posi).mileageUnit));
                                        }
                                    }, 1000);

                                    currency_spinner.setSelection(currency.indexOf(getdata3.get(posi).DeclaredCurrency));


                                    millage_txt.setText(getdata3.get(i).mileageValue);
                                    if (getdata3.get(i).tpms.equalsIgnoreCase("true")) {
                                        tpms_txt.setSelection(1);

                                    } else
                                        tpms_txt.setSelection(0);

                                    getAFMImages("TPMS");
                                    declared_txt.setText("" + getdata3.get(i).declareValue);
                                    if (getdata3.get(i).buildMonth != null)
                                        if (!getdata3.get(i).buildMonth.equalsIgnoreCase(""))
                                            build_month.setSelection(Integer.parseInt(getdata3.get(i).buildMonth));
//                                if (!getdata3.get(i).buildYear.equalsIgnoreCase(""))
//                                    build_year.setSelection(listBuildYaer.indexOf(getdata3.get(i).buildYear));
                                    if (getdata3.get(i).buildYear == null)
                                        build_year.setText("");
                                    else
                                        build_year.setText(getdata3.get(i).buildYear);
                                    gvwr_txt.setText(getdata3.get(i).gvwrValue);

//                                if(getdata3.get(i).gvwrUnit.)
                                    if (getdata3.get(i).diesel.equalsIgnoreCase("true")) {
                                        diesel_txt.setSelection(1);

                                    } else
                                        diesel_txt.setSelection(0);
                                    getAFMImages("TitleConversionFront");
                                    getAFMImages("TitleConversionBack");
                                    if (getdata3.get(i).speedoConversion.equalsIgnoreCase("true"))
                                        speedo_txt.setSelection(1);
                                    else
                                        speedo_txt.setSelection(0);
                                    if (getdata3.get(i).titleConversion.equalsIgnoreCase("true")) {
                                        title_conv_txt.setSelection(1);

                                    } else
                                        title_conv_txt.setSelection(0);
                                    getAFMImages("IRSNumber");
                                    if (getdata3.get(i).trackingConfig != null) {
                                        if (!getdata3.get(i).trackingConfig.equalsIgnoreCase("")) {
                                            tracking_txt.setText(getdata3.get(i).trackingConfig);

                                        }

                                    }
//                                if (getdata3.get(i).trackingConfig.equalsIgnoreCase("Y")) {
//                                    tracking_txt.setSelection(1);
//                                    getImages("IRS Number");
//                                }
//                                else
//                                    tracking_txt.setSelection(0);
                                    lat.setText(""+getdata3.get(i).Latitude);
                                    longi.setText(""+getdata3.get(i).Longitude);
                                    findViewById(R.id.get_lat_long).setVisibility(View.INVISIBLE);

                                    qWvrUnit = getdata3.get(i).gvwrUnit;

                                    if (getdata3.get(i).billOfSale.equalsIgnoreCase("true")) {
                                        bill_txt.setSelection(1);
                                        getAFMImages("BillOfSale");
                                    } else
                                        bill_txt.setSelection(0);
                                    if (getdata3.get(i).title.equalsIgnoreCase("true")) {
                                        title_txt.setSelection(1);

                                    } else
                                        title_txt.setSelection(0);
                                    getAFMImages("TitleFront");
                                    getAFMImages("TitleBack");
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            gvwr_spinner.setSelection(listValueGvwrId.indexOf(getdata3.get(posi).gvwrUnit));
                                        }
                                    }, 1000);

//                                    }
//                                });


                                    getAFMImages("RecallSheet1");
                                    getAFMImages("RecallSheet2");


                                } catch (Exception e) {
                                    e.printStackTrace();
                                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                                }


                                save_vehicle.setClickable(false);
                                save_vehicle.setEnabled(false);
                                save_vehicle.setText("Update");

                                save_vehicle.setVisibility(View.GONE);
                                saveBol.setText("View Pre-Inspections");
                                year.setEnabled(false);
                                year.setClickable(false);
                                make.setEnabled(false);
                                make.setClickable(false);
                                model.setEnabled(false);
                                model.setClickable(false);
                                vinNo.setClickable(false);
                                vinNo.setEnabled(false);
                                color_txt.setClickable(false);
                                color_txt.setEnabled(false);
                                oem_tag_txt.setClickable(false);
                                oem_tag_txt.setEnabled(false);
                                millage_spinner.setClickable(false);
                                millage_spinner.setEnabled(false);
                                millage_txt.setClickable(false);
                                millage_txt.setEnabled(false);
                                tpms_txt.setClickable(false);
                                tpms_txt.setEnabled(false);
                                declared_txt.setClickable(false);
                                declared_txt.setEnabled(false);
                                build_month.setClickable(false);
                                build_month.setEnabled(false);
                                build_year.setClickable(false);
                                build_year.setEnabled(false);
                                gvwr_txt.setClickable(false);
                                gvwr_txt.setEnabled(false);
                                diesel_txt.setClickable(false);
                                diesel_txt.setEnabled(false);
                                speedo_txt.setClickable(false);
                                speedo_txt.setEnabled(false);
                                title_conv_txt.setClickable(false);
                                title_conv_txt.setEnabled(false);
                                tracking_txt.setClickable(false);
                                tracking_txt.setEnabled(false);
                                bill_txt.setClickable(false);
                                bill_txt.setEnabled(false);
                                title_txt.setClickable(false);
                                title_txt.setEnabled(false);
                                gvwr_spinner.setClickable(false);
                                gvwr_spinner.setEnabled(false);
                                scan.setClickable(false);
                                scan.setEnabled(false);
                                title_txt_img.setClickable(false);
                                title_txt_img.setEnabled(false);
                                title_conv_txt_img.setClickable(false);
                                title_conv_txt_img.setEnabled(false);
                                title_conv_txt_img1.setClickable(false);
                                title_conv_txt_img1.setEnabled(false);
                                oem_tag_txt_img.setClickable(false);
                                oem_tag_txt_img.setEnabled(false);
                                millage_txt_img.setClickable(false);
                                millage_txt_img.setEnabled(false);
                                tpms_txt_img.setClickable(false);
                                tpms_txt_img.setEnabled(false);
                                bill_txt_img.setClickable(false);
                                bill_txt_img.setEnabled(false);
                                tracking_txt_img.setClickable(false);
                                tracking_txt_img.setEnabled(false);
                                recall1_txt_img.setClickable(false);
                                recall2_txt_img1.setEnabled(false);
                                currency_spinner.setEnabled(false);
                                currency_spinner.setClickable(false);
                                release_form_txt.setClickable(false);
                                release_form_txt.setEnabled(false);
                                release_form_txt_img.setClickable(false);
                                release_form_txt_img.setEnabled(false);

                            }
                            if (i == getdata3.size() - 1) {
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        hideAnimation();
                                    }
                                }, 6000);
                            }
                        }
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                }finally {
                    hideAnimation();
                }

            }
            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getVehicleList(String orderid, final String vihicleType)throws Exception {
//        GetVehicleIdListModel vindetail1 = new GetVehicleIdListModel(prf.getStringData("authKey"), orderid, vihicleType,prf.getStringData("carrierPrimaryId"));
        Call<GetVehicleIdListModel> call1 = apiInterface.getInventoryVehicleList(""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),""+orderid,"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetVehicleIdListModel>() {
            @Override
            public void onResponse(Call<GetVehicleIdListModel> call, Response<GetVehicleIdListModel> response) {
//                hideAnimation();

                GetVehicleIdListModel getdata = response.body();
                try {
                    if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                        final ArrayList<GetVehicleIdListModel.datavalue> getdata3 = getdata.dataV;
                        if (getdata3.equals("null")) {
                            hideAnimation();
                        }

                        if (getdata3.size() == 0) {
                            hideAnimation();
                        }


                        for (int i = 0; getdata3.size() > i; i++) {
                            if (getdata3.get(i).vehiocleId.equalsIgnoreCase(vehicleId)) {
                                try {
                                    save_vehicle.setClickable(false);
                                    save_vehicle.setEnabled(false);
                                    preInspection = false;
                                    inventory=getdata3.get(i).isInventory;
                                    vinNo.setText(getdata3.get(i).vinNumber);
                                    year.setText(getdata3.get(i).year);
                                    make.setText(getdata3.get(i).makeV);
                                    model.setText(getdata3.get(i).model);
                                    oemStr = getdata3.get(i).oem;
                                    color_txt.setText(getdata3.get(i).Color);
                                    if (getdata3.get(i).oem.equalsIgnoreCase("true")) {
                                        oem_tag_txt.setSelection(1);
                                        getImages("OEMTag");
                                    } else
                                        oem_tag_txt.setSelection(0);

                                    releaseFormStr = getdata3.get(i).releasefrom;
                                    if (getdata3.get(i).releasefrom.equalsIgnoreCase("true")) {
                                        release_form_txt.setSelection(1);
                                        getImages("ReleaseForm");
                                    } else
                                        release_form_txt.setSelection(0);

                                    getImages("MileageValue");
                                    tpmsStr = getdata3.get(i).tpms;
                                    dieselStr = getdata3.get(i).diesel;
                                    speedoStr = getdata3.get(i).speedoConversion;
                                    titleconvStr = getdata3.get(i).titleConversion;
                                    billStr = getdata3.get(i).billOfSale;
                                    titleStr = getdata3.get(i).title;
                                    miliage=getdata3.get(i).mileageUnit;
                                    lat.setText(""+getdata3.get(i).Latitude);
                                    longi.setText(""+getdata3.get(i).Longitude);

//                            if (getdata3.get(i).mileageUnit.equalsIgnoreCase("2")) {
//                                millage_spinner.setSelection(2);
////                                    getImages("MileageValue");
//                            } else if (getdata3.get(i).mileageUnit.equalsIgnoreCase("1")) {
//                                millage_spinner.setSelection(1);
////                                    getImages("MileageValue");
//                            } else
//                                millage_spinner.setSelection(0);
                                    final int posi = i;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            millage_spinner.setSelection(milegeCode.indexOf(getdata3.get(posi).mileageUnit));
                                        }
                                    }, 1000);

                                    currency_spinner.setSelection(currency.indexOf(getdata3.get(posi).DeclaredCurrency));



                                    millage_txt.setText(getdata3.get(i).mileageValue);
                                    if (getdata3.get(i).tpms.equalsIgnoreCase("true")) {
                                        tpms_txt.setSelection(1);
                                        getImages("TPMS");
                                    } else
                                        tpms_txt.setSelection(0);
                                    declared_txt.setText(""+getdata3.get(i).declareValue);
                                    if(getdata3.get(i).buildMonth!=null)
                                        if (!getdata3.get(i).buildMonth.equalsIgnoreCase(""))
                                            build_month.setSelection(Integer.parseInt(getdata3.get(i).buildMonth));
//                                if (!getdata3.get(i).buildYear.equalsIgnoreCase(""))
//                                    build_year.setSelection(listBuildYaer.indexOf(getdata3.get(i).buildYear));
                                    if (getdata3.get(i).buildYear == null)
                                        build_year.setText("");
                                    else
                                        build_year.setText(getdata3.get(i).buildYear);
                                    gvwr_txt.setText(getdata3.get(i).gvwrValue);

//                                if(getdata3.get(i).gvwrUnit.)
                                    if (getdata3.get(i).diesel.equalsIgnoreCase("true")) {
                                        diesel_txt.setSelection(1);

                                    } else
                                        diesel_txt.setSelection(0);
                                    getImages("TitleConversionFront");
                                    getImages("TitleConversionBack");
                                    if (getdata3.get(i).speedoConversion.equalsIgnoreCase("true"))
                                        speedo_txt.setSelection(1);
                                    else
                                        speedo_txt.setSelection(0);
                                    if (getdata3.get(i).titleConversion.equalsIgnoreCase("true")) {
                                        title_conv_txt.setSelection(1);

                                    } else
                                        title_conv_txt.setSelection(0);
                                    getImages("IRSNumber");
                                    if (getdata3.get(i).trackingConfig != null) {
                                        if (!getdata3.get(i).trackingConfig.equalsIgnoreCase("")) {
                                            tracking_txt.setText(getdata3.get(i).trackingConfig);

                                        }

                                    }
//                                if (getdata3.get(i).trackingConfig.equalsIgnoreCase("Y")) {
//                                    tracking_txt.setSelection(1);
//                                    getImages("IRS Number");
//                                }
//                                else
//                                    tracking_txt.setSelection(0);
                                    qWvrUnit=getdata3.get(i).gvwrUnit;

                                    if (getdata3.get(i).billOfSale.equalsIgnoreCase("true")) {
                                        bill_txt.setSelection(1);
                                        getImages("BillOfSale");
                                    } else
                                        bill_txt.setSelection(0);
                                    if (getdata3.get(i).title.equalsIgnoreCase("true")) {
                                        title_txt.setSelection(1);

                                    } else
                                        title_txt.setSelection(0);
                                    getImages("TitleFront");
                                    getImages("TitleBack");
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            gvwr_spinner.setSelection(listValueGvwrId.indexOf(getdata3.get(posi).gvwrUnit));
                                        }
                                    }, 1000);

//                                    }
//                                });

                                    getImages("RecallSheet1");
                                    getImages("RecallSheet2");
                                    save_vehicle.setClickable(true);
                                    save_vehicle.setEnabled(true);
                                    save_vehicle.setText("Update");
                                    save_new.setText("Update & New");

                                    if (vihicleType.equalsIgnoreCase("VLSaved")) {
                                        save_vehicle.setClickable(true);
                                        save_vehicle.setEnabled(true);
                                        save_vehicle.setText("Update");
                                        year.setEnabled(false);
                                        year.setClickable(false);
                                        make.setEnabled(false);
                                        make.setClickable(false);
                                        model.setEnabled(false);
                                        model.setClickable(false);
                                    } else {
                                        save_vehicle.setVisibility(View.GONE);
                                        saveBol.setText("View Pre-Inspection");
                                        year.setEnabled(false);
                                        year.setClickable(false);
                                        make.setEnabled(false);
                                        make.setClickable(false);
                                        model.setEnabled(false);
                                        model.setClickable(false);
                                        vinNo.setClickable(false);
                                        vinNo.setEnabled(false);
                                        color_txt.setClickable(false);
                                        color_txt.setEnabled(false);
                                        oem_tag_txt.setClickable(false);
                                        oem_tag_txt.setEnabled(false);
                                        millage_spinner.setClickable(false);
                                        millage_spinner.setEnabled(false);
                                        millage_txt.setClickable(false);
                                        millage_txt.setEnabled(false);
                                        tpms_txt.setClickable(false);
                                        tpms_txt.setEnabled(false);
                                        declared_txt.setClickable(false);
                                        declared_txt.setEnabled(false);
                                        build_month.setClickable(false);
                                        build_month.setEnabled(false);
                                        build_year.setClickable(false);
                                        build_year.setEnabled(false);
                                        gvwr_txt.setClickable(false);
                                        gvwr_txt.setEnabled(false);
                                        diesel_txt.setClickable(false);
                                        diesel_txt.setEnabled(false);
                                        speedo_txt.setClickable(false);
                                        speedo_txt.setEnabled(false);
                                        title_conv_txt.setClickable(false);
                                        title_conv_txt.setEnabled(false);
                                        tracking_txt.setClickable(false);
                                        tracking_txt.setEnabled(false);
                                        bill_txt.setClickable(false);
                                        bill_txt.setEnabled(false);
                                        title_txt.setClickable(false);
                                        title_txt.setEnabled(false);
                                        gvwr_spinner.setClickable(false);
                                        gvwr_spinner.setEnabled(false);
                                        scan.setClickable(false);
                                        scan.setEnabled(false);
                                        title_txt_img.setClickable(false);
                                        title_txt_img.setEnabled(false);
                                        title_conv_txt_img.setClickable(false);
                                        title_conv_txt_img.setEnabled(false);
                                        title_conv_txt_img1.setClickable(false);
                                        title_conv_txt_img1.setEnabled(false);
                                        oem_tag_txt_img.setClickable(false);
                                        oem_tag_txt_img.setEnabled(false);
                                        millage_txt_img.setClickable(false);
                                        millage_txt_img.setEnabled(false);
                                        tpms_txt_img.setClickable(false);
                                        tpms_txt_img.setEnabled(false);
                                        bill_txt_img.setClickable(false);
                                        bill_txt_img.setEnabled(false);
                                        tracking_txt_img.setClickable(false);
                                        tracking_txt_img.setEnabled(false);
                                        recall1_txt_img.setClickable(false);
                                        recall2_txt_img1.setEnabled(false);
                                        currency_spinner.setEnabled(false);
                                        currency_spinner.setClickable(false);
                                        release_form_txt.setClickable(false);
                                        release_form_txt.setEnabled(false);
                                        release_form_txt_img.setClickable(false);
                                        release_form_txt_img.setEnabled(false);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                                }
                            }
                            if (i == getdata3.size() - 1) {
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        hideAnimation();
                                    }
                                }, 6000);
                            }
                        }
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                }finally {
                    hideAnimation();
                }

                }


            @Override
            public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.vehicle_screen);
        if (mainlayout != null && loaderScreen == null) {
            loaderScreen = new LoaderScreen(this);
            loaderView = loaderScreen.getView();
            loaderScreen.showBackground(getApplicationContext(), true);
            mainlayout.addView(loaderView, layoutParams);

            if (!isLoaded) {
                if (loaderView != null && loaderScreen != null) {
                    loaderView.setVisibility(View.VISIBLE);
                    loaderScreen.startAnimating();
                }
            }
        }
    }

    private void hideAnimation() {
        if (loaderView != null) {
            loaderView.setVisibility(View.GONE);
            mainlayout.removeView(loaderView);
        }
        if (loaderScreen != null) {
            loaderScreen.stopAnimation();
            loaderScreen = null;
        }
    }

    void cameraOpen() throws Exception{

        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
        if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera();
        } else {
//            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////
//            File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                picUri = FileProvider.getUriForFile(this,
//                        "com.avaal.com.afmautocustomer.fileprovider", f);
//            } else
//                picUri = Uri.fromFile(f);
////
////                    Log.d("save path",outputFileUri.toString());
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
//            startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",ex,"");
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "com.avaal.com.afm2020autoCx.fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(pictureIntent,
                            CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
                }
            }


        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    void saveImage(String docType, String imageFilePath, String vehicleId1,String fileId) throws Exception{
//        if(uri==null){
//            return;
//        }

        final File f = new File(imageFilePath);
        File f1 = f.getAbsoluteFile();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.US).format(new Date());
        String lastdegit=vinNo.getText().toString().substring(vinNo.getText().length() - 8);
        String fileName = docType+"-"+lastdegit;
        String filetemdata = "";
        int size = 0;
        try {
//            Bitmap thumbnail = (BitmapFactory.decodeFile(f.getAbsolutePath()));
           Bitmap thumbnail;
//            Bitmap thumbnail = BitmapFactory.decodeFile(f.getAbsolutePath());
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            thumbnail = BitmapFactory.decodeFile(imageFilePath, bitmapOptions);
            thumbnail = getScaledBitmap(thumbnail, 500, 800);
            Bitmap second = addText(thumbnail);
            thumbnail.recycle();
            filetemdata = bitmapToBase64(second);
//            saveImage(second, fileName);
            second.recycle();

            System.gc();
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
        long fileSizeInBytes = filetemdata.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
//        long fileSizeInKB = fileSizeInBytes / 1024;
//        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
//        long fileSizeInMB = fileSizeInKB / 1024;
//        Log.e("image size", "" + fileSizeInMB);
//        if(fileSizeInMB>2){
//            MDToast mdToast = MDToast.makeText(this, "Image Not more then 2MB", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//            mdToast.show();
//            return;
//        }
        if(f.exists())
            f.delete();

        size = (int) fileSizeInBytes;


        size=size/1024 ;
//        Log.e("image Data:",filetemdata);
// MultipartBody.Part is used to send also the actual file nam
        PreferenceManager prf = new PreferenceManager(this);
// add another part within the multipart request
//        RequestBody fullName =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), "Your Name");
//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);


        SaveImageModel vindetail = new SaveImageModel(fileName, "image/jpeg", "" + size, ".jpeg", filetemdata, "", docType, vehicleId1, fileId,""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),""+ prf.getStringData("userName"));
//        Call<SaveImageModel> call1 = apiInterface.updateProfile(vehicleIds, fileNames,authKey1,content,docTypess,exten,size1,remarks, body);
        Call<SaveImageModel> call1 = apiInterface.saveImages(vindetail,"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<SaveImageModel>() {
            @Override
            public void onResponse(Call<SaveImageModel> call, Response<SaveImageModel> response) {
                final SaveImageModel getdata = response.body();
                hideAnimation();
                try {
                    if (getdata.status) {
                        if(f.exists())
                            f.delete();
                           System.gc();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (imageType.equalsIgnoreCase("TitleFront")) {
                                    titleUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
//                                title_txt_img.setVisibility(View.GONE);
                                    titleId = getdata.imageId;

                                    title_txt_img_view.setVisibility(View.VISIBLE);
                                } else if (imageType.equalsIgnoreCase("TitleBack")) {
                                    titleUrl1 = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
//                                title_txt_img.setVisibility(View.GONE);
                                    titleId1 = getdata.imageId;
//                                title_txt.setSelection(1);
                                    title_txt_img_view1.setVisibility(View.VISIBLE);
                                } else if (imageType.equalsIgnoreCase("OEMTag")) {
                                    oem_tag_txt.setSelection(1);
                                    oemId = getdata.imageId;
                                    oemUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
//                                oem_tag_txt_img.setVisibility(View.GONE);
                                    oem_tag_txt_img_view.setVisibility(View.VISIBLE);
                                }else if (imageType.equalsIgnoreCase("ReleaseForm")) {
                                    release_form_txt.setSelection(1);
                                    releaseFromId = getdata.imageId;
                                    releaseFromUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
//                                oem_tag_txt_img.setVisibility(View.GONE);
                                    release_form_txt_img_view.setVisibility(View.VISIBLE);
                                } else if (imageType.equalsIgnoreCase("MileageValue")) {
                                    millageUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
//                                millage_txt_img.setVisibility(View.GONE);
                                    millageId = getdata.imageId;
                                    millage_txt_img_view.setVisibility(View.VISIBLE);
                                } else if (imageType.equalsIgnoreCase("TPMS")) {
                                    tpms_txt.setSelection(1);
                                    tpmsId = getdata.imageId;
                                    tpmsUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
//                                tpms_txt_img.setVisibility(View.GONE);
                                    tpms_txt_img_view.setVisibility(View.VISIBLE);
                                } else if (imageType.equalsIgnoreCase("TitleConversionFront")) {
                                    titleConvUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");

                                    titleConvId = getdata.imageId;
//                                title_conv_txt.setVisibility(View.GONE);
                                    title_conv_txt_img_view.setVisibility(View.VISIBLE);
                                } else if (imageType.equalsIgnoreCase("TitleConversionBack")) {
                                    titleConvUrl1 = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
                                    titleConvId1 = getdata.imageId;
//                                title_conv_txt.setVisibility(View.GONE);
                                    title_conv_txt_img_view1.setVisibility(View.VISIBLE);
                                } else if (imageType.equalsIgnoreCase("BillOfSale")) {
                                    billUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
                                    bill_txt.setSelection(1);
                                    billId = getdata.imageId;

//                                bill_txt_img.setVisibility(View.GONE);
                                    bill_txt_img_view.setVisibility(View.VISIBLE);
                                } else if (imageType.equalsIgnoreCase("IRSNumber")) {
                                    trackUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20").replaceAll(" ", "%20");
//                                    tracking_txt.setSelection(1);
                                    trackId = getdata.imageId;
//                                tracking_txt_img.setVisibility(View.GONE);
                                    tracking_txt_img_eye.setVisibility(View.VISIBLE);
                                }
                                else if (imageType.equalsIgnoreCase("RecallSheet1")) {
                                    recall1Url = getdata.docUrl.replace("DelhiServer", "192.168.1.20").replaceAll(" ", "%20");
//                                    tracking_txt.setSelection(1);
                                    recall1Id = getdata.imageId;
//                                tracking_txt_img.setVisibility(View.GONE);
                                    recall1_txt_img_eye.setVisibility(View.VISIBLE);
                                }
                                else if (imageType.equalsIgnoreCase("RecallSheet2")) {
                                    recall2Url = getdata.docUrl.replace("DelhiServer", "192.168.1.20").replaceAll(" ", "%20");
//                                    tracking_txt.setSelection(1);
                                    recall2Id = getdata.imageId;
//                                tracking_txt_img.setVisibility(View.GONE);
                                    recall2_txt_img_eye.setVisibility(View.VISIBLE);
                                }

//                                if(!titleUrl.equalsIgnoreCase("") && !titleUrl1.equalsIgnoreCase(""))
//                                    title_txt.setSelection(1);
//
//                                if(!titleConvUrl.equalsIgnoreCase("") && !titleConvUrl1.equalsIgnoreCase(""))
//                                    title_conv_txt.setSelection(1);

                            }
                        });

                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                }
            }

            @Override
            public void onFailure(Call<SaveImageModel> call, Throwable t) {
                hideAnimation();
                call.cancel();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

    private String bitmapToBase64(Bitmap bitmap)throws Exception {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
    public static Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
    }
    void popUp(String url, final String id, final String type1)throws Exception {
        final Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
                , null));
        settingsDialog.show();
        ImageView image = (ImageView) settingsDialog.findViewById(R.id.image_url);
        ImageView deleteImg = (ImageView) settingsDialog.findViewById(R.id.delete_view);
        Button ok = (Button) settingsDialog.findViewById(R.id.ok);
        if (!prf.getStringData("OrderStatus").equalsIgnoreCase("Saved") && !prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped"))
            deleteImg.setVisibility(View.GONE);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.dismiss();
            }
        });
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (type1.equalsIgnoreCase("TitleFront")) {
//                    MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "Can not Delete this Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                    mdToast.show();
//                   return;
//                } else if (type1.equalsIgnoreCase("TitleBack")) {
//                    MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "Can not Delete this Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                    mdToast.show();
//                    return;
//                } else if (type1.equalsIgnoreCase("TitleConversionFront")) {
//                    MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "Can not Delete this Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                    mdToast.show();
//                  return;
//                } else if (type1.equalsIgnoreCase("TitleConversionBack")) {
//                    MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, "Can not Delete this Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                    mdToast.show();
//                 return;
//                }





                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddVehicleActivity.this);
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this?");
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke YES event
                        try {
                            deleteImg(id,type1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                        }

                        settingsDialog.dismiss();

                    }
                });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        });
        Picasso.with(AddVehicleActivity.this).load(url).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.d1).into(image);

    }

    void qwvrList(final String type)throws Exception {

//        DropDownModel user = new DropDownModel("GVWRUnit", "G", "U", prf.getStringData("authKey"));
        Call<MasterDropDownModel> call1 = apiInterface.getMasterDropDown(""+type,false,""+prf.getStringData("userName"),"","",""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<MasterDropDownModel>() {
            @Override
            public void onResponse(Call<MasterDropDownModel> call, Response<MasterDropDownModel> response) {

                MasterDropDownModel dropdata = response.body();
                if(type.equalsIgnoreCase("WeightUnit")) {
                    listValueGvwrId.clear();
                    listValueGvwr.clear();
                    for (int i = 0; dropdata.data.size() > i; i++) {
                        listValueGvwrId.add(dropdata.data.get(i).Code);
                        listValueGvwr.add(dropdata.data.get(i).Name);
                    }

                }else if(type.equalsIgnoreCase("DTC")){
                    milegeList.clear();
                    milegeCode.clear();
                    for (int i = 0; dropdata.data.size() > i; i++) {
                        milegeCode.add(dropdata.data.get(i).Code);
                        milegeList.add(dropdata.data.get(i).Name);
                    }
                }
                spinner6();
                hideAnimation();


            }

            @Override
            public void onFailure(Call<MasterDropDownModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void milegeList(final String type)throws Exception {

//        DropDownModel user = new DropDownModel("GVWRUnit", "G", "U", prf.getStringData("authKey"));
        Call<LookUpModel> call1 = apiInterface.getGetMstLookUpDataDropDown(""+type,false,""+prf.getStringData("userName"),"","",""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<LookUpModel>() {
            @Override
            public void onResponse(Call<LookUpModel> call, Response<LookUpModel> response) {

                LookUpModel dropdata = response.body();

                    milegeList.clear();
                    milegeCode.clear();
                    for (int i = 0; dropdata.data.size() > i; i++) {
                        milegeCode.add(dropdata.data.get(i).Code);
                        milegeList.add(dropdata.data.get(i).Name);
                        if(i==dropdata.data.size()-1){
                            try {
                                milege();
                            } catch (Exception e) {
                                e.printStackTrace();
                                new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                            }
                        }
                    }



            }

            @Override
            public void onFailure(Call<LookUpModel> call, Throwable t) {
                call.cancel();
                hideAnimation();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) throws Exception{
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    void getImages(final String type)throws Exception{

        Call<GetImageModel> call1 = apiInterface.getImage(vehicleId,""+type,""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetImageModel>() {
            @Override
            public void onResponse(Call<GetImageModel> call, Response<GetImageModel> response) {

                GetImageModel dropdata = response.body();
                try {
                    if (dropdata.status) {
                        if(dropdata.dataValuer.size()>0) {
                            if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TitleFront")) {
                                titleUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
    //                                title_txt_img.setVisibility(View.GONE);
                                titleId=dropdata.dataValuer.get(0).docId;
                                title_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TitleBack")) {
                                titleUrl1 = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
    //                                title_txt_img.setVisibility(View.GONE);
    //                                title_txt.setSelection(1);
                                titleId1=dropdata.dataValuer.get(0).docId;
                                title_txt_img_view1.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("OEMTag")) {

                                oemUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
    //                                oem_tag_txt_img.setVisibility(View.GONE);
                                oemId=dropdata.dataValuer.get(0).docId;
                                oem_tag_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("MileageValue")) {
                                millageUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
    //                                millage_txt_img.setVisibility(View.GONE);
                                millageId=dropdata.dataValuer.get(0).docId;
                                millage_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TPMS")) {

                                tpmsUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
    //                                tpms_txt_img.setVisibility(View.GONE);
                                tpmsId=dropdata.dataValuer.get(0).docId;
                                tpms_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TitleConversionFront")) {
                                titleConvUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
    //                                title_conv_txt.setVisibility(View.GONE);
                                titleConvId=dropdata.dataValuer.get(0).docId;

                                title_conv_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TitleConversionBack")) {
                                titleConvUrl1 = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                titleConvId1=dropdata.dataValuer.get(0).docId;
    //                                title_conv_txt.setVisibility(View.GONE);
                                title_conv_txt_img_view1.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("BillOfSale")) {
                                billUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
    //                                bill_txt_img.setVisibility(View.GONE);
                                billId=dropdata.dataValuer.get(0).docId;
                                bill_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("IRSNumber")) {
                                trackUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20").replaceAll(" ","%20");
    //                                tracking_txt_img.setVisibility(View.GONE);
                                trackId=dropdata.dataValuer.get(0).docId;
                                tracking_txt_img_eye.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("RecallSheet1")) {
                                recall1Url = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20").replaceAll(" ","%20");
    //                                tracking_txt_img.setVisibility(View.GONE);
                                recall1Id=dropdata.dataValuer.get(0).docId;
                                recall1_txt_img_eye.setVisibility(View.VISIBLE);
                            }
                            else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("RecallSheet2")) {
                                recall2Url = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20").replaceAll(" ","%20");
    //                                tracking_txt_img.setVisibility(View.GONE);
                                recall2Id=dropdata.dataValuer.get(0).docId;
                                recall2_txt_img_eye.setVisibility(View.VISIBLE);
                            }else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("ReleaseForm")) {
                                releaseFromUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
    //                                oem_tag_txt_img.setVisibility(View.GONE);
                                releaseFromId=dropdata.dataValuer.get(0).docId;
                                release_form_txt_img_view.setVisibility(View.VISIBLE);
                            }
                        }
    //                    else{
    //                        if(type.equalsIgnoreCase("OEMTag")) {
    //                            oem_tag_txt.setSelection(0);
    //                        }
    //                        else if(type.equalsIgnoreCase(""))
    //
    //
    //                    }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                }


            }

            @Override
            public void onFailure(Call<GetImageModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getAFMImages(final String type)throws Exception{
        GetImageDetailmodel user = new GetImageDetailmodel( prf.getStringData("authKey"),vehicleId,type);
        Call<GetImageModel> call1 = apiInterface.getAFMImage(vehicleId,""+type,""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetImageModel>() {
            @Override
            public void onResponse(Call<GetImageModel> call, Response<GetImageModel> response) {

                GetImageModel dropdata = response.body();
                try {
                    if (dropdata.status) {
                        if(dropdata.dataValuer.size()>0) {
                            if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TitleFront")) {
                                titleUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                //                                title_txt_img.setVisibility(View.GONE);
                                titleId=dropdata.dataValuer.get(0).docId;
                                title_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TitleBack")) {
                                titleUrl1 = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                //                                title_txt_img.setVisibility(View.GONE);
                                //                                title_txt.setSelection(1);
                                titleId1=dropdata.dataValuer.get(0).docId;
                                title_txt_img_view1.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("OEMTag")) {

                                oemUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                //                                oem_tag_txt_img.setVisibility(View.GONE);
                                oemId=dropdata.dataValuer.get(0).docId;
                                oem_tag_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("MileageValue")) {
                                millageUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                //                                millage_txt_img.setVisibility(View.GONE);
                                millageId=dropdata.dataValuer.get(0).docId;
                                millage_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TPMS")) {

                                tpmsUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                //                                tpms_txt_img.setVisibility(View.GONE);
                                tpmsId=dropdata.dataValuer.get(0).docId;
                                tpms_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TitleConversionFront")) {
                                titleConvUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                //                                title_conv_txt.setVisibility(View.GONE);
                                titleConvId=dropdata.dataValuer.get(0).docId;

                                title_conv_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("TitleConversionBack")) {
                                titleConvUrl1 = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                titleConvId1=dropdata.dataValuer.get(0).docId;
                                //                                title_conv_txt.setVisibility(View.GONE);
                                title_conv_txt_img_view1.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("BillOfSale")) {
                                billUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                //                                bill_txt_img.setVisibility(View.GONE);
                                billId=dropdata.dataValuer.get(0).docId;
                                bill_txt_img_view.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("IRSNumber")) {
                                trackUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20").replaceAll(" ","%20");
                                //                                tracking_txt_img.setVisibility(View.GONE);
                                trackId=dropdata.dataValuer.get(0).docId;
                                tracking_txt_img_eye.setVisibility(View.VISIBLE);
                            } else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("RecallSheet1")) {
                                recall1Url = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20").replaceAll(" ","%20");
                                //                                tracking_txt_img.setVisibility(View.GONE);
                                recall1Id=dropdata.dataValuer.get(0).docId;
                                recall1_txt_img_eye.setVisibility(View.VISIBLE);
                            }
                            else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("RecallSheet2")) {
                                recall2Url = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20").replaceAll(" ","%20");
                                //                                tracking_txt_img.setVisibility(View.GONE);
                                recall2Id=dropdata.dataValuer.get(0).docId;
                                recall2_txt_img_eye.setVisibility(View.VISIBLE);
                            }else if (dropdata.dataValuer.get(0).doctype.equalsIgnoreCase("ReleaseForm")) {
                                releaseFromUrl = dropdata.dataValuer.get(0).DocURL.replace("DelhiServer", "192.168.1.20");
                                //                                oem_tag_txt_img.setVisibility(View.GONE);
                                releaseFromId=dropdata.dataValuer.get(0).docId;
                                release_form_txt_img_view.setVisibility(View.VISIBLE);
                            }
                        }
                        //                    else{
                        //                        if(type.equalsIgnoreCase("OEMTag")) {
                        //                            oem_tag_txt.setSelection(0);
                        //                        }
                        //                        else if(type.equalsIgnoreCase(""))
                        //
                        //
                        //                    }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                }


            }

            @Override
            public void onFailure(Call<GetImageModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

    void deleteImg(String id, final String type)throws Exception{
        RemoveImageModel vindetail = new RemoveImageModel(prf.getStringData("authKey"), id);
        Call<RemoveImageModel> call1 = apiInterface.deleteImage(id,""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<RemoveImageModel>() {
            @Override
            public void onResponse(Call<RemoveImageModel> call, Response<RemoveImageModel> response) {

                RemoveImageModel getdata = response.body();
                if(getdata.status!=null) {
                    if (getdata.status) {

                        MDToast mdToast = MDToast.makeText(AddVehicleActivity.this, type+" Image deleted successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                        if (type.equalsIgnoreCase("OEMTag")) {
                            oemStr="false";
                            oemUrl = "";
//                                oem_tag_txt_img.setVisibility(View.GONE);
                            oemId="0";
                            oem_tag_txt.setSelection(0);
                            oem_tag_txt_img_view.setVisibility(View.GONE);
                        }else if (type.equalsIgnoreCase("ReleaseForm")) {
                            releaseFormStr="N";
                            releaseFromUrl = "";
//                                oem_tag_txt_img.setVisibility(View.GONE);
                            releaseFromId="0";
                            release_form_txt.setSelection(0);
                            release_form_txt_img_view.setVisibility(View.GONE);
                        } else if (type.equalsIgnoreCase("MileageValue")) {
                            millageUrl = "";
                            miliage="N";
                                millage_txt_img_view.setVisibility(View.GONE);
                            millageId="0";
                            millage_spinner.setSelection(0);
                        } else if (type.equalsIgnoreCase("TPMS")) {
                            tpmsStr="false";
                            tpmsUrl = "";
//                                tpms_txt_img.setVisibility(View.GONE);
                            tpms_txt.setSelection(0);
                            tpmsId="0";
                            tpms_txt_img_view.setVisibility(View.GONE);
                        }  else if (type.equalsIgnoreCase("BillOfSale")) {
                            billUrl = "";
                            billStr="false";
//                                bill_txt_img.setVisibility(View.GONE);
                            bill_txt.setSelection(0);
                            billId="0";
                            bill_txt_img_view.setVisibility(View.GONE);
                        } else if (type.equalsIgnoreCase("IRSNumber")) {
                            trackUrl = "";
                            tracking_txt.setText("");
//                                tracking_txt_img.setVisibility(View.GONE);
                            trackId="0";

                            tracking_txt_img_eye.setVisibility(View.GONE);
                        }else if (type.equalsIgnoreCase("RecallSheet1")) {
                            recall1Url = "";
//                                tracking_txt_img.setVisibility(View.GONE);
                            recall1Id="0";

                            recall1_txt_img_eye.setVisibility(View.GONE);
                        }
                        else if (type.equalsIgnoreCase("RecallSheet2")) {
                            recall2Url = "";

//                                tracking_txt_img.setVisibility(View.GONE);
                            recall2Id="0";

                            recall2_txt_img_eye.setVisibility(View.GONE);
                        }
                        else if (type.equalsIgnoreCase("TitleFront")) {
                            titleUrl = "";

//                                tracking_txt_img.setVisibility(View.GONE);
                            titleId="0";

                            title_txt_img_view.setVisibility(View.GONE);
                        }
                        else if (type.equalsIgnoreCase("TitleBack")) {
                            titleUrl1 = "";

//                                tracking_txt_img.setVisibility(View.GONE);
                            titleId1="0";

                            title_txt_img_view1.setVisibility(View.GONE);
                        }
                        else if (type.equalsIgnoreCase("TitleConversionFront")) {
                            titleConvUrl = "";

//                                tracking_txt_img.setVisibility(View.GONE);
                            titleConvId="0";

                            title_conv_txt_img_view.setVisibility(View.GONE);
                        }
                        else if (type.equalsIgnoreCase("TitleConversionBack")) {
                            titleConvUrl1 = "";

//                                tracking_txt_img.setVisibility(View.GONE);
                            titleConvId1="0";

                            title_conv_txt_img_view1.setVisibility(View.GONE);
                        }
                        try {
                            save1( oemStr, tpmsStr, dieselStr, speedoStr, titleconvStr, billStr, titleStr, buildMonth, miliage,inventory,releaseFormStr,currency_txt);
                        } catch (Exception e) {
                            e.printStackTrace();
                            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RemoveImageModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
void getInventoryVehicle(){
//    GetVehicleIdListModel vindetail1 = new GetVehicleIdListModel(prf.getStringData("authKey"), null, "",prf.getStringData("carrierPrimaryId"));
    Call<GetVehicleIdListModel> call1 = apiInterface.getInventoryVehicleList(""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),"0","bearer "+ prf.getStringData("accessToken"),"application/json");
    call1.enqueue(new Callback<GetVehicleIdListModel>() {
        @Override
        public void onResponse(Call<GetVehicleIdListModel> call, Response<GetVehicleIdListModel> response) {
//            hideAnimation();
            GetVehicleIdListModel getdata = response.body();
            if (getdata.satus) {
// GetVehicleIdListModel tripDetails;
                final ArrayList<GetVehicleIdListModel.datavalue> getdata3 = getdata.dataV;
                if (getdata3.equals("null")) {
                    hideAnimation();
                }

                if (getdata3.size() == 0) {
                    hideAnimation();
                }


                for (int i = 0; getdata3.size() > i; i++) {
                    if (getdata3.get(i).vehiocleId.equalsIgnoreCase(vehicleId)) {
                        try {
                            save_vehicle.setClickable(true);
                            save_vehicle.setEnabled(true);
                            save_vehicle.setText("Update");
                            save_new.setText("Update & New");
//                            save_vehicle.setClickable(false);
//                            save_vehicle.setEnabled(false);
                            inventory=getdata3.get(i).isInventory;
                            preInspection = false;
                            vinNo.setText(getdata3.get(i).vinNumber);
                            year.setText(getdata3.get(i).year);
                            make.setText(getdata3.get(i).makeV);
                            model.setText(getdata3.get(i).model);
                            oemStr = getdata3.get(i).oem;
                            color_txt.setText(getdata3.get(i).Color);
                            if (getdata3.get(i).oem.equalsIgnoreCase("true")) {
                                oem_tag_txt.setSelection(1);
                                getImages("OEMTag");
                            } else
                                oem_tag_txt.setSelection(0);

                                    releaseFormStr = getdata3.get(i).releasefrom;
                            if (getdata3.get(i).releasefrom.equalsIgnoreCase("true")) {
                                release_form_txt.setSelection(1);
                                getImages("ReleaseForm");
                            } else
                                release_form_txt.setSelection(0);

                            getImages("MileageValue");
                            tpmsStr = getdata3.get(i).tpms;
                            dieselStr = getdata3.get(i).diesel;
                            speedoStr = getdata3.get(i).speedoConversion;
                            titleconvStr = getdata3.get(i).titleConversion;
                            billStr = getdata3.get(i).billOfSale;
                            titleStr = getdata3.get(i).title;
                            miliage=getdata3.get(i).mileageUnit;
//                            if (getdata3.get(i).mileageUnit.equalsIgnoreCase("2")) {
//                                millage_spinner.setSelection(2);
////                                    getImages("MileageValue");
//                            } else if (getdata3.get(i).mileageUnit.equalsIgnoreCase("1")) {
//                                millage_spinner.setSelection(1);
////                                    getImages("MileageValue");
//                            } else
//                                millage_spinner.setSelection(0);
                            final int posi = i;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    millage_spinner.setSelection(milegeCode.indexOf(getdata3.get(posi).mileageUnit));
                                }
                            }, 1000);

                                    currency_spinner.setSelection(currency.indexOf(getdata3.get(posi).DeclaredCurrency));



                            millage_txt.setText(getdata3.get(i).mileageValue);
                            if (getdata3.get(i).tpms.equalsIgnoreCase("true")) {
                                tpms_txt.setSelection(1);
                                getImages("TPMS");
                            } else
                                tpms_txt.setSelection(0);
                            declared_txt.setText(""+getdata3.get(i).declareValue);
                            if(getdata3.get(i).buildMonth!=null)
                            if (!getdata3.get(i).buildMonth.equalsIgnoreCase(""))
                                build_month.setSelection(Integer.parseInt(getdata3.get(i).buildMonth));
//                                if (!getdata3.get(i).buildYear.equalsIgnoreCase(""))
//                                    build_year.setSelection(listBuildYaer.indexOf(getdata3.get(i).buildYear));
                            if (getdata3.get(i).buildYear == null)
                                build_year.setText("");
                            else
                                build_year.setText(getdata3.get(i).buildYear);
                            gvwr_txt.setText(getdata3.get(i).gvwrValue);

//                                if(getdata3.get(i).gvwrUnit.)
                            if (getdata3.get(i).diesel.equalsIgnoreCase("true")) {
                                diesel_txt.setSelection(1);

                            } else
                                diesel_txt.setSelection(0);
                            getImages("TitleConversionFront");
                            getImages("TitleConversionBack");
                            if (getdata3.get(i).speedoConversion.equalsIgnoreCase("true"))
                                speedo_txt.setSelection(1);
                            else
                                speedo_txt.setSelection(0);
                            if (getdata3.get(i).titleConversion.equalsIgnoreCase("true")) {
                                title_conv_txt.setSelection(1);

                            } else
                                title_conv_txt.setSelection(0);
                            getImages("IRSNumber");
                            if (getdata3.get(i).trackingConfig != null) {
                                if (!getdata3.get(i).trackingConfig.equalsIgnoreCase("")) {
                                    tracking_txt.setText(getdata3.get(i).trackingConfig);

                                }

                            }
//                                if (getdata3.get(i).trackingConfig.equalsIgnoreCase("Y")) {
//                                    tracking_txt.setSelection(1);
//                                    getImages("IRS Number");
//                                }
//                                else
//                                    tracking_txt.setSelection(0);
                            lat.setText(""+getdata3.get(i).Latitude);
                            longi.setText(""+getdata3.get(i).Longitude);

                            qWvrUnit=getdata3.get(i).gvwrUnit;

                            if (getdata3.get(i).billOfSale.equalsIgnoreCase("true")) {
                                bill_txt.setSelection(1);
                                getImages("BillOfSale");
                            } else
                                bill_txt.setSelection(0);
                            if (getdata3.get(i).title.equalsIgnoreCase("true")) {
                                title_txt.setSelection(1);

                            } else
                                title_txt.setSelection(0);
                            getImages("TitleFront");
                            getImages("TitleBack");
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    gvwr_spinner.setSelection(listValueGvwrId.indexOf(getdata3.get(posi).gvwrUnit));
                                }
                            }, 1000);

//                                    }
//                                });

                            getImages("RecallSheet1");
                            getImages("RecallSheet2");


//                            if (prf.getStringData("OrderStatus") != null) {
//                                if (prf.getStringData("OrderStatus").equalsIgnoreCase("saved")) {
//                                    save_vehicle.setClickable(true);
//                                    save_vehicle.setEnabled(true);
//                                    save_vehicle.setText("Update");
//                                    year.setEnabled(false);
//                                    year.setClickable(false);
//                                    make.setEnabled(false);
//                                    make.setClickable(false);
//                                    model.setEnabled(false);
//                                    model.setClickable(false);
//                                } else {
//                                    save_vehicle.setVisibility(View.GONE);
//                                    saveBol.setText("View BOL");
//                                    year.setEnabled(false);
//                                    year.setClickable(false);
//                                    make.setEnabled(false);
//                                    make.setClickable(false);
//                                    model.setEnabled(false);
//                                    model.setClickable(false);
//                                    vinNo.setClickable(false);
//                                    vinNo.setEnabled(false);
//                                    oem_tag_txt.setClickable(false);
//                                    oem_tag_txt.setEnabled(false);
//                                    release_form_txt.setClickable(false);
//                                    release_form_txt.setEnabled(false);
//                                    millage_spinner.setClickable(false);
//                                    millage_spinner.setEnabled(false);
//                                    millage_txt.setClickable(false);
//                                    millage_txt.setEnabled(false);
//                                    tpms_txt.setClickable(false);
//                                    tpms_txt.setEnabled(false);
//                                    declared_txt.setClickable(false);
//                                    declared_txt.setEnabled(false);
//                                    build_month.setClickable(false);
//                                    build_month.setEnabled(false);
//                                    build_year.setClickable(false);
//                                    build_year.setEnabled(false);
//                                    gvwr_txt.setClickable(false);
//                                    gvwr_txt.setEnabled(false);
//                                    diesel_txt.setClickable(false);
//                                    diesel_txt.setEnabled(false);
//                                    speedo_txt.setClickable(false);
//                                    speedo_txt.setEnabled(false);
//                                    title_conv_txt.setClickable(false);
//                                    title_conv_txt.setEnabled(false);
//                                    tracking_txt.setClickable(false);
//                                    tracking_txt.setEnabled(false);
//                                    bill_txt.setClickable(false);
//                                    bill_txt.setEnabled(false);
//                                    title_txt.setClickable(false);
//                                    title_txt.setEnabled(false);
//                                    gvwr_spinner.setClickable(false);
//                                    gvwr_spinner.setEnabled(false);
//                                    scan.setClickable(false);
//                                    scan.setEnabled(false);
//                                    title_txt_img.setClickable(false);
//                                    title_txt_img.setEnabled(false);
//                                    title_conv_txt_img.setClickable(false);
//                                    title_conv_txt_img.setEnabled(false);
//                                    oem_tag_txt_img.setClickable(false);
//                                    oem_tag_txt_img.setEnabled(false);
//                                    release_form_txt_img.setClickable(false);
//                                    release_form_txt_img.setEnabled(false);
//                                    millage_txt_img.setClickable(false);
//                                    millage_txt_img.setEnabled(false);
//                                    tpms_txt_img.setClickable(false);
//                                    tpms_txt_img.setEnabled(false);
//                                    bill_txt_img.setClickable(false);
//                                    bill_txt_img.setEnabled(false);
//                                    tracking_txt_img.setClickable(false);
//                                    tracking_txt_img.setEnabled(false);
//                                    recall1_txt_img.setClickable(false);
//                                    recall2_txt_img1.setEnabled(false);
//                                }
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
                        }
                    }
                    if (i == getdata3.size() - 1) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                hideAnimation();
                            }
                        }, 6000);
                    }
                }
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
            }
            hideAnimation();
        }

        @Override
        public void onFailure(Call<GetVehicleIdListModel> call, Throwable t) {
            hideAnimation();
            call.cancel();
            new Util().sendSMTPMail(AddVehicleActivity.this,t,"CxE001",null,""+call.request().url().toString());
        }
    });
}
    private Bitmap addText(Bitmap toEdit)throws Exception{
        Calendar calendar = Calendar.getInstance();
        Bitmap dest = toEdit.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(dest);
        Paint paint = new Paint();  //set the look
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
//        paint.setShadowLayer(2.0f, 1.0f, 1.0f, Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setColor(Color.BLACK);
        int pictureHeight = dest.getHeight();
        paint.setTextSize(20f);
        Locale loc = new Locale("en", "US");
        String timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",
                loc).format(calendar.getTime());
//        canvas.drawBitmap(originalImg, 100, 50, paint);
        canvas.drawText(fullAddress , 10,  30, paint);
        canvas.drawText(timeStamp , 10,  50, paint);
        return dest;
    }
    private void saveImage(Bitmap finalBitmap, String image_name) throws Exception{


        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root,"Cx App Images");
        if(!myDir.exists())
            myDir.mkdirs();

        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.US).format(new Date());
        String fname = timeStamp+"-"+image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            finalBitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            new Util().sendSMTPMail(AddVehicleActivity.this,null,"CxE004",e,"");
        }
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, image_name);
        values.put(MediaStore.Images.Media.DESCRIPTION, image_name);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis ());
        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
        values.put("_data", file.getAbsolutePath());

        ContentResolver cr = getContentResolver();
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    @Override
    public void onCheckLatLongListner(Location location1) {
        this.location=location1;
        getAddress();

    }

    @Override
    public void onCheckLocationEnable(Boolean location1) {
        if(!location1){
            if (pd.isShowing())
                pd.dismiss();
        }
    }

    void getAddress(){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(AddVehicleActivity.this, Locale.US);
        if(location!=null) {
            Log.e("location", ""+location);
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                if(postalCode== null || postalCode.equalsIgnoreCase("null"))
                    postalCode="";
                fullAddress = "" + city + "," + state + "," + postalCode;
                if(pd!=null) {
                    if (pd.isShowing())
                        pd.dismiss();
                }
//                prf.saveStringData("address", fullAddress);

                Log.e("address", "" + city + "," + state + "," + postalCode);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }else{
            if(pd!=null) {
                if (pd.isShowing())
                    pd.dismiss();
            }
        }
    }
    void  tutorial(){
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.scan), "", "Add VIN Number of Vehicle through BarCode and QR Code Scanner ")
                        // All options below are optional
                        .outerCircleColor(R.color.colorPrimary)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.colorWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.colorWhite)      // Specify the color of the title text
                        .descriptionTextSize(15)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.colorWhite)  // Specify the color of the description text
                        .textColor(R.color.colorWhite)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
//                        .icon(Drawable)                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional

                    }
                });
        prf.saveStringData("ScanTutorial","1");
    }


    void getVehicleId(final String orderid){
        GetVehicleIdModel vindetail=new GetVehicleIdModel(prf.getStringData("authKey"),orderid,"");
        Call<GetInvVehicleModel> call1 = apiInterface.getTempInvVehicleIds("0",""+orderid, null, null, null, null, null, null, null,null, null, null, null,null, null,null, null, null, null,null, null, null , null, null,null ,""+prf.getStringData("userCode"),null,""+ prf.getStringData("userName"),new Util().getDateTime(),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<GetInvVehicleModel>() {
            @Override
            public void onResponse(Call<GetInvVehicleModel> call, Response<GetInvVehicleModel> response) {

                GetInvVehicleModel getdata = response.body();
                if(getdata.satus) {

                    Intent i=new Intent(AddVehicleActivity.this,AddVehicleActivity.class);
                    i.putExtra("VehicleId",getdata.oredrId);
                    i.putExtra("OrderId",""+orderId);
                    //                    prf.saveStringData("VehicleType","true");
                    i.putExtra("VehicleType",""+getIntent().getStringExtra("VehicleType"));
                    i.putExtra("vihiclevinList",myList);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }
            }
            @Override
            public void onFailure(Call<GetInvVehicleModel> call, Throwable t) {
                call.cancel();
            }
        });
    }
}