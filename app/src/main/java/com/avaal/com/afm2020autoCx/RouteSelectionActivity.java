package com.avaal.com.afm2020autoCx;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.avaal.com.afm2020autoCx.models.DropDownModel;
import com.avaal.com.afm2020autoCx.models.GetVehicleIdListModel;
import com.avaal.com.afm2020autoCx.models.LocationDetailModel;
import com.avaal.com.afm2020autoCx.models.MilesModel;
import com.avaal.com.afm2020autoCx.models.OrderDetailModel;
import com.avaal.com.afm2020autoCx.models.OrderListModel;
import com.avaal.com.afm2020autoCx.models.PrimaryInfoDetailModel;
import com.avaal.com.afm2020autoCx.models.RouteSelectionModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.LoaderScreen;
import extra.MyImage;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 27-02-2018.
 */

public class RouteSelectionActivity extends AppCompatActivity {
    @BindView(R.id.pickup_date)
    TextView pickupdate;
    @BindView(R.id.delivery_date)
    TextView deliveydate;
    @BindView(R.id.pickup_spinner)
    AutoCompleteTextView pickupSpin;
    @BindView(R.id.delivery_spinner)
    AutoCompleteTextView deliverySpin;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.type)
            TextView type;
    @BindView(R.id.from)
            View from;
    @BindView(R.id.to)
    View to;
//    @BindView(R.id.linear_yard)
//    LinearLayout yard_view;
//    @BindView(R.id.yard_date)
//    TextView yarddate;
//    @BindView(R.id.yard_spinner)
//    AutoCompleteTextView yardSpin;
//    @BindView(R.id.yard_remark)
//    EditText yard_remark;
    @BindView(R.id.pickup_remark)
    EditText pickup_remark;
    @BindView(R.id.delivery_remark)
    EditText delivery_remark;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.add1)
    ImageView add1;
    @BindView(R.id.save_btn)
    TextView save_btn;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.miles)
    TextView miles;
    @BindView(R.id.item)
    TextView item;
    @BindView(R.id.ord)
    TextView ord;

    @BindView(R.id.main_selection)
    FrameLayout mainlayout;
    String FreightTypeLuCode="FRT02";
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    String pickupId="0",pickupName,deliveryId="0",deliveryName,yardId="0",yardName,orderType,tempOrderId="0";
    private static final int FROM_ACTIVITY_REQUEST_CODE = 109;
    private static final int TO_ACTIVITY_REQUEST_CODE = 107;
    PreferenceManager prf;
    Boolean next=false;
    String myFormat = "MM/dd/yyyy"; //In which you need put here
    final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    APIInterface apiInterface;
    List<String> pickupLocationId = new ArrayList<>();
    List<String> pickupLocationName = new ArrayList<>();
    List<String> deliveryLocationId = new ArrayList<>();
    List<String> deliveryLocationName = new ArrayList<>();
    List<String> yardLocationId = new ArrayList<>();
    List<String> yardLocationName = new ArrayList<>();
    Calendar myCalendar = Calendar.getInstance();
    Calendar dCalendar = Calendar.getInstance();
    Calendar yCalendar = Calendar.getInstance();
    String distanceUnit,distance;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (FROM_ACTIVITY_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    pickupLocation(getIntent().getStringExtra("from"));
                    pickupId = data.getStringExtra("id");
                    pickupName = data.getStringExtra("LocationName");
                    pickupSpin.setText(pickupName);
                    add.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
                    try {
                        hideSoftKeyboard(this);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                break;
            }
            case (TO_ACTIVITY_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    deliveryLocation(getIntent().getStringExtra("to"));
                    deliveryId = data.getStringExtra("id");
                    deliveryName = data.getStringExtra("LocationName");
                    deliverySpin.setText(deliveryName);
                    add1.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
                    try {
                        hideSoftKeyboard(this);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.item)
    void  item(){
        FreightTypeLuCode="FRT02";
        freightType(FreightTypeLuCode);
    }
    @OnClick(R.id.ord)
    void  ord(){
        FreightTypeLuCode="FRT01";
        freightType(FreightTypeLuCode);
    }

    void freightType(String FreightTypeLuCode1){
     if(FreightTypeLuCode1.equalsIgnoreCase("FRT02")){
         item.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
         item.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
         ord.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
         ord.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
     }else{
         ord.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
         ord.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
         item.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
         item.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
     }
    }
    @OnClick(R.id.getMile)
    void  getMile(){
        try {
            getMiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_selection_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        linear.setVisibility(View.GONE);
        title.setText("Route Selection");
         prf = new PreferenceManager(RouteSelectionActivity.this);
//        authKey = getIntent().getStringExtra("AuthKey");
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }


        prf.saveStringData("vehicleList", null);
//        if(!prf.getStringData("OrderStatus").equalsIgnoreCase("Saved")){
//            pickupdate.setEnabled(false);
//            pickupdate.setClickable(false);
//            deliveydate.setEnabled(false);
//            deliveydate.setClickable(false);
//            pickupSpin.setEnabled(false);
//            pickupSpin.setClickable(false);
//            deliverySpin.setEnabled(false);
//            deliverySpin.setClickable(false);
//            pickup_remark.setEnabled(false);
//            pickup_remark.setClickable(false);
//            delivery_remark.setEnabled(false);
//            delivery_remark.setClickable(false);
//
//            save_btn.setVisibility(View.GONE);
//
//        }
        pickupLocation(getIntent().getStringExtra("from"));
        deliveryLocation(getIntent().getStringExtra("to"));
        yardLocation();
//        this.setTitle("Create A Trip ");
//
////Remove notification bar
        Date c = Calendar.getInstance().getTime();


        deliveydate.setText(sdf.format(c)+" "+String.format("%02d:%02d", 12, 00)+" "+ "AM");
        pickupdate.setText(sdf.format(c)+" "+String.format("%02d:%02d", 12, 00)+" "+ "AM");
        if(getIntent().getStringExtra("from").equalsIgnoreCase("CA") && getIntent().getStringExtra("to").equalsIgnoreCase("CA")){
            type.setText("Inter Provincial");
            orderType="InterProvincial";
            from.setBackground(getResources().getDrawable(R.mipmap.cn));
            to.setBackground(getResources().getDrawable(R.mipmap.cn));
        }
        else if(getIntent().getStringExtra("from").equalsIgnoreCase("CA") && getIntent().getStringExtra("to").equalsIgnoreCase("US")){
            type.setText("Export");
            orderType="Export";
//            yard_view.setVisibility(View.VISIBLE);
            from.setBackground(getResources().getDrawable(R.mipmap.cn));
            to.setBackground(getResources().getDrawable(R.mipmap.us));
        }else if(getIntent().getStringExtra("from").equalsIgnoreCase("US") && getIntent().getStringExtra("to").equalsIgnoreCase("CA")){
            type.setText("Import");
            orderType="Import";
            from.setBackground(getResources().getDrawable(R.mipmap.us));
            to.setBackground(getResources().getDrawable(R.mipmap.cn));
        }else if(getIntent().getStringExtra("from").equalsIgnoreCase("US") && getIntent().getStringExtra("to").equalsIgnoreCase("US")){
            type.setText("Inter State");
            orderType="InterState";
            from.setBackground(getResources().getDrawable(R.mipmap.us));
            to.setBackground(getResources().getDrawable(R.mipmap.us));
        }
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                updateLabel();
//            }
//
//        };

//        pickupdate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(RouteSelectionActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        final DatePickerDialog.OnDateSetListener ydate = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                yCalendar.set(Calendar.YEAR, year);
//                yCalendar.set(Calendar.MONTH, monthOfYear);
//                yCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                updateyardLabel();
//            }
//
//        };

//        yarddate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(RouteSelectionActivity.this, ydate, yCalendar
//                        .get(Calendar.YEAR), yCalendar.get(Calendar.MONTH),
//                        yCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        final DatePickerDialog.OnDateSetListener ddate = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                dCalendar.set(Calendar.YEAR, year);
//                dCalendar.set(Calendar.MONTH, monthOfYear);
//                dCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updatedelLabel();
//            }
//
//        };
//
//        deliveydate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(RouteSelectionActivity.this, ddate, dCalendar
//                        .get(Calendar.YEAR), dCalendar.get(Calendar.MONTH),
//                        dCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
        final Calendar pCalendar = Calendar.getInstance();
        pickupdate.setKeyListener(null);
        final DatePickerDialog.OnDateSetListener pdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                pCalendar.set(Calendar.YEAR, year);
                pCalendar.set(Calendar.MONTH, monthOfYear);
                pCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    holder.arrivaldate.setText(sdf.format(coCalendar.getTime()));
//                    DeliveryList.get(position).setArrive(sdf.format(coCalendar.getTime()));
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(RouteSelectionActivity.this, //same Activity Context like before
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String format;
                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }
//                                pickupdate.setText(sdf.format(pCalendar.getTime())+" "+String.format("%02d:%02d:00", hourOfDay, minute));

                                pickupdate.setText(sdf.format(pCalendar.getTime())+" "+String.format("%02d:%02d", hourOfDay, minute) + " " + format);
//                                    weekopen.setText(hourOfDay+":"+minute); //You set the time for the EditText created
                            }
                        }, mHour, mMinute, true);
                pickupdate.setText(sdf.format(pCalendar.getTime())+" "+String.format("%02d:%02d", 12, 00) + " "+"AM");
//                tpd.show();
            }

        };

        pickupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RouteSelectionActivity.this, pdate, pCalendar
                        .get(Calendar.YEAR), pCalendar.get(Calendar.MONTH),
                        pCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
/*jhbhjhbjkngjk*/
        final Calendar coCalendar = Calendar.getInstance();
        deliveydate.setKeyListener(null);
        final DatePickerDialog.OnDateSetListener codate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                coCalendar.set(Calendar.YEAR, year);
                coCalendar.set(Calendar.MONTH, monthOfYear);
                coCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    holder.arrivaldate.setText(sdf.format(coCalendar.getTime()));
//                    DeliveryList.get(position).setArrive(sdf.format(coCalendar.getTime()));
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                int ampm = dCalendar.get(Calendar.AM_PM);
                final String ampmStr = (ampm == 0) ? "AM" : "PM";
                TimePickerDialog tpd = new TimePickerDialog(RouteSelectionActivity.this, //same Activity Context like before
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String format;
                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }
//                                deliveydate.setText(sdf.format(coCalendar.getTime())+" "+String.format("%02d:%02d:00", hourOfDay, minute));
//                                    weekopen.setText(hourOfDay+":"+minute); //You set the time for the EditText created
                                deliveydate.setText(sdf.format(coCalendar.getTime())+" "+String.format("%02d:%02d", 12, 00)+" "+ "AM");



                            }
                        }, mHour, mMinute, false
                );
                deliveydate.setText(sdf.format(coCalendar.getTime())+" "+String.format("%02d:%02d", 12, 00)+" "+ "AM");
//                tpd.show();
            }

        };

        deliveydate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(RouteSelectionActivity.this, codate, coCalendar
                        .get(Calendar.YEAR), coCalendar.get(Calendar.MONTH),
                        coCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    if(getIntent().getStringExtra("Id")!=null){
        if(!getIntent().getStringExtra("Id").equalsIgnoreCase("0")){
            tempOrderId=getIntent().getStringExtra("Id");



//            if(!util.isNetworkAvailable(this)){
//                MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
//                mdToast.show();
//                return;
//            }
          showAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getDetail();
//                    if(prf.getStringData("OrderStatus").equalsIgnoreCase("save")) {
//                        try {
//                            getOrderdetail(getIntent().getStringExtra("Id"),"OSaved");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else {
//                        try {
//                            getOrderdetail(getIntent().getStringExtra("Id"),"OShipped");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            }, 3000);
        }
    }
        pickup_remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    hideSoftKeyboard(RouteSelectionActivity.this);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
    @OnClick(R.id.click_scr)
    void maine(){

//        if(pickupSpin.getText().toString().equalsIgnoreCase(""))
//            pickupId="0";
//        else{
//            if(pickupLocationName.indexOf(pickupSpin.getText().toString())!=-1)
//                pickupId=pickupLocationId.get(pickupLocationName.indexOf(pickupSpin.getText().toString()));
//            else
//                pickupId="0";
//        }
//
//        if(deliverySpin.getText().toString().equalsIgnoreCase(""))
//            deliveryId="0";
//        else{
//            if(deliveryLocationName.indexOf(deliverySpin.getText().toString())!=-1){
//                deliveryId=deliveryLocationId.get(deliveryLocationName.indexOf(deliverySpin.getText().toString()));
//            }
//            else
//                deliveryId="0";
//        }
//
//
//        if(pickupId.equalsIgnoreCase("0")){
//            pickupId="0";
//            pickupName="";
//            pickupSpin.setText("");
//            add.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.ic_action_add, 30, 30));
//        }else{
//            pickupSpin.setText(pickupName);
//            add.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
//        }
//        if(deliveryId.equalsIgnoreCase("0")){
//            deliveryId="0";
//            deliveryName="";
//            deliverySpin.setText("");
//            add1.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.ic_action_add, 30, 30));
//        }else{
//            deliverySpin.setText(deliveryName);
//            add1.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
//        }
    }

    @OnClick(R.id.next_btn)
    void next(){
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        prf.saveStringData("OrderType",orderType);
        next=true;
        if(!prf.getStringData("OrderStatus").equalsIgnoreCase("Saved") && !prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")){

            Intent i=new Intent(this,VehicleListActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("OrderId",tempOrderId);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            startActivity(i);
        }
            else {
            try {
                prf.saveStringData("When", "add");
                getVehicleid();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//
    }

    @OnClick(R.id.save_btn)
    void save(){
        next=false;
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        try {
            getVehicleid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();
    }
    @OnClick(R.id.back)
    void back(){
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onBackPressed();
    }
    @OnClick(R.id.home_)
    void home(){
        new Util().myIntent(this,NewDashBoardActivity.class);
    }
        @OnClick(R.id.add)
    void addNew1Location(){


            if(pickupSpin.getText().toString().equalsIgnoreCase(""))
                pickupId="0";
            else{
                if(pickupLocationName.indexOf(pickupSpin.getText().toString())!=-1)
                    pickupId=pickupLocationId.get(pickupLocationName.indexOf(pickupSpin.getText().toString()));
                else
                    pickupId="0";
            }



        if(pickupSpin.getText().toString().equalsIgnoreCase("")){
            Intent j = new Intent(RouteSelectionActivity.this, AddLocationActivity.class);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            j.putExtra("countryId", getIntent().getStringExtra("from"));
            startActivityForResult(j, FROM_ACTIVITY_REQUEST_CODE);
        }else {
//            Intent j = new Intent(RouteSelectionActivity.this, AddLocationActivity.class);
//            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//            j.putExtra("locationId", pickupId);
//            j.putExtra("countryId", getIntent().getStringExtra("from"));
//            startActivityForResult(j, FROM_ACTIVITY_REQUEST_CODE);
            try {
                popUp(pickupId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//            startActivity(j);

    }
    @OnClick(R.id.add1)
    void addNewLocation(){
        if(deliverySpin.getText().toString().equalsIgnoreCase(""))
            deliveryId="0";
        else{
            if(deliveryLocationName.indexOf(deliverySpin.getText().toString())!=-1){
                deliveryId=deliveryLocationId.get(deliveryLocationName.indexOf(deliverySpin.getText().toString()));
            }
            else
                deliveryId="0";
        }

        if(deliverySpin.getText().toString().equalsIgnoreCase("")){
            Intent j = new Intent(RouteSelectionActivity.this, AddLocationActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
            j.putExtra("countryId", getIntent().getStringExtra("to"));
            startActivityForResult(j, TO_ACTIVITY_REQUEST_CODE);
        }else {
//            Intent j = new Intent(RouteSelectionActivity.this, AddLocationActivity.class);
//            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//            j.putExtra("locationId", deliveryId);
//            j.putExtra("countryId", getIntent().getStringExtra("to"));
//            startActivityForResult(j, TO_ACTIVITY_REQUEST_CODE);
            try {
                popUp(deliveryId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void setSpinner1() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.autocomplete_list_row,
                R.id.text, pickupLocationName);
// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pickupSpin.setThreshold(0);
        pickupSpin.setAdapter(adapter);
        if(pickupName!=null){
            pickupSpin.setText(pickupName);
        }
        pickupSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i1, long l) {
                Log.e("setd","value1");
                hideSoftKeyboard(RouteSelectionActivity.this);
                pickupName=pickupSpin.getText().toString();
                pickupId=pickupLocationId.get(pickupLocationName.indexOf(pickupName));
                add.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
                Log.e("pickupId",""+pickupName);
                try {
                    getMiles();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                in.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        pickupSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pickupId=pickupLocationId.get(i);

                pickupName=pickupLocationName.get(i);
                Log.e("pickupId2",""+pickupName);
                try {
                    getMiles();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pickupId="0";
                pickupName="";
                pickupSpin.setText("");
                add.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.ic_action_add, 30, 30));
            }
        });
//        pickupSpin.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                pickupSpin.setText(pickupName);
//            }
//        });
        pickupSpin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                pickupName=pickupSpin.getText().toString();
                if(pickupLocationName.indexOf(pickupName)==-1) {
                    pickupId="0";
                    pickupName="";
                    pickupSpin.setText("");
                    add.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.ic_action_add, 30, 30));

                }else{
                    pickupSpin.setText(pickupName);
                    pickupId = pickupLocationId.get(pickupLocationName.indexOf(pickupName));
                    add.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
                }

            }
        });


//        stateAdapter.setDropDownViewResource(R.layout.spinner_textview_align);
    }
    void setYardSpinner1() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.autocomplete_list_row,
                R.id.text, yardLocationName);
// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
//        yardSpin.setThreshold(0);
//        yardSpin.setAdapter(adapter);
//        if(yardName!=null){
//            yardSpin.setText(yardName);
//        }
//        yardSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                hideSoftKeyboard(RouteSelectionActivity.this);
//            }
//        });
//        yardSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                yardId=yardLocationId.get(i);
//                yardName=yardLocationName.get(i);
//                hideSoftKeyboard(RouteSelectionActivity.this);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        stateAdapter.setDropDownViewResource(R.layout.spinner_textview_align);
    }

    void setSpinner2() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.autocomplete_list_row,
                R.id.text, deliveryLocationName);
// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        deliverySpin.setThreshold(0);
        deliverySpin.setAdapter(adapter);
        if(deliveryName!=null){
            deliverySpin.setText(deliveryName);
        }
        deliverySpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("setd","value1");
                hideSoftKeyboard(RouteSelectionActivity.this);
                deliveryName=deliverySpin.getText().toString();
                deliveryId=deliveryLocationId.get(deliveryLocationName.indexOf(deliveryName));
//                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                add1.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
                try {
                    getMiles();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        deliverySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deliveryId=deliveryLocationId.get(i);
                deliveryName=deliveryLocationName.get(i);
                try {
                    getMiles();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                deliveryId="0";
                deliveryName="";
                deliverySpin.setText("");
                add1.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.ic_action_add, 30, 30));
            }
        });


        deliverySpin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                deliveryName=deliverySpin.getText().toString();
                if(deliveryLocationName.indexOf(deliveryName)==-1) {
                    deliveryId="0";
                    deliveryName="";
                    deliverySpin.setText("");
                    add1.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.drawable.ic_action_add, 30, 30));

                }else{
                    deliveryId = deliveryLocationId.get(deliveryLocationName.indexOf(deliveryName));
                    deliverySpin.setText(deliveryName);
                    add1.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
                }

            }
        });
//        stateAdapter.setDropDownViewResource(R.layout.spinner_textview_align);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
//        String myFormat = "MM/dd/yyyy";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        final int mHour1 = myCalendar.get(Calendar.HOUR_OF_DAY);
        final int mMinute1 = myCalendar.get(Calendar.MINUTE);
        int ampm1 = myCalendar.get(Calendar.AM_PM);
        final String ampmStr1 = (ampm1 == 0) ? "AM" : "PM";
        TimePickerDialog tpd = new TimePickerDialog(this, //same Activity Context like before
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        pickupdate.setText(sdf.format(myCalendar.getTime())+" "+String.valueOf(mHour1) + ":" + String.valueOf(mMinute1)+" "+ampmStr1);
//                                    weekopen.setText(hourOfDay+":"+minute); //You set the time for the EditText created
                    }
                }, mHour1, mMinute1, false);
        tpd.show();

    }

    private void updatedelLabel() {
//        String myFormat = "MM/dd/yyyy hh:mm a"; //In which you need put here
        String myFormat = "MM/dd/yyyy";
        final SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);
        final int mHour = dCalendar.get(Calendar.HOUR_OF_DAY);
        final int mMinute = dCalendar.get(Calendar.MINUTE);
        int ampm = dCalendar.get(Calendar.AM_PM);
        final String ampmStr = (ampm == 0) ? "AM" : "PM";
        TimePickerDialog tpd = new TimePickerDialog(this, //same Activity Context like before
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        deliveydate.setText(sdf1.format(dCalendar.getTime())+" "+String.valueOf(mHour) + ":" + String.valueOf(mMinute)+" "+ampmStr);
//                                    weekopen.setText(hourOfDay+":"+minute); //You set the time for the EditText created
                    }
                }, mHour, mMinute, false);
        tpd.show();
//        deliveydate.setText(sdf.format(dCalendar.getTime()));
    }
  void updateyardLabel(){
//      String myFormat = "MM/dd/yyyy hh:mm a";
      String myFormat = "MM/dd/yyyy";//In which you need put here
      final SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);
      int mHour = yCalendar.get(Calendar.HOUR_OF_DAY);
      int mMinute = yCalendar.get(Calendar.MINUTE);
      TimePickerDialog tpd = new TimePickerDialog(this, //same Activity Context like before
              new TimePickerDialog.OnTimeSetListener() {

                  @Override
                  public void onTimeSet(TimePicker view, int hourOfDay,
                                        int minute) {
//                      yarddate.setText(sdf1.format(yCalendar.getTime())+" "+String.format("%02d:%02d:00", hourOfDay, minute));
//                                    weekopen.setText(hourOfDay+":"+minute); //You set the time for the EditText created
                  }
              }, mHour, mMinute, false);
      tpd.show();
  }
    void pickupLocation(String from) {

        Call<DropDownModel> call1 = apiInterface.getLocation("Location","false",""+prf.getStringData("userName"),""+prf.getStringData("corporateId"),""+from,"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<DropDownModel>() {
            @Override
            public void onResponse(Call<DropDownModel> call, Response<DropDownModel> response) {
  try {
      DropDownModel dropdata = response.body();
      if (dropdata.status) {
          pickupLocationId.clear();
          pickupLocationName.clear();
          for (int i = 0; dropdata.data.size() > i; i++) {
              pickupLocationId.add(dropdata.data.get(i).code);
              pickupLocationName.add(dropdata.data.get(i).name);
          }

      }
      setSpinner1();
  }catch (Exception e){
      e.printStackTrace();
  }


            }

            @Override
            public void onFailure(Call<DropDownModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(RouteSelectionActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void yardLocation() {
        PreferenceManager prf = new PreferenceManager(RouteSelectionActivity.this);
        DropDownModel user = new DropDownModel("Location", "", "Y", prf.getStringData("authKey"));
        Call<DropDownModel> call1 = apiInterface.getLocation("Location","true",""+prf.getStringData("userName"),""+prf.getStringData("corporateId"),"IN","bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<DropDownModel>() {
            @Override
            public void onResponse(Call<DropDownModel> call, Response<DropDownModel> response) {

                DropDownModel dropdata = response.body();
                try {
                    if (dropdata.status) {
                        yardLocationId.clear();
                        yardLocationName.clear();
                        for (int i = 0; dropdata.data.size() > i; i++) {
                            yardLocationId.add(dropdata.data.get(i).id);
                            yardLocationName.add(dropdata.data.get(i).name);
                        }

                    }
                    setYardSpinner1();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DropDownModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(RouteSelectionActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void deliveryLocation(String to) {

        PreferenceManager prf = new PreferenceManager(RouteSelectionActivity.this);
        Call<DropDownModel> call1 = apiInterface.getLocation("Location","false",""+prf.getStringData("userName"),""+prf.getStringData("corporateId"),""+to,"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<DropDownModel>() {
            @Override
            public void onResponse(Call<DropDownModel> call, Response<DropDownModel> response) {
               try {
                   DropDownModel dropdata = response.body();
                   if (dropdata.status) {
                       deliveryLocationId.clear();
                       deliveryLocationName.clear();
                       for (int i = 0; dropdata.data.size() > i; i++) {
                           deliveryLocationId.add(dropdata.data.get(i).code);
                           deliveryLocationName.add(dropdata.data.get(i).name);
                       }

                   }
                   setSpinner2();
               }catch (Exception e){
                   e.printStackTrace();
               }

            }

            @Override
            public void onFailure(Call<DropDownModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(RouteSelectionActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getVehicleid()throws Exception{
        PreferenceManager prf = new PreferenceManager(this);
//        JSONObject js=new JSONObject();
//        try {
//            js.put("AuthKey",prf.getStringData("authKey"));
//            js.put("VinNumber",vinNo1);
//        } catch (JSONException e) {
//            e.printStackTrace();

//        }
        if(pickupSpin.getText().toString().equalsIgnoreCase(""))
            pickupId="0";
        else{
            if(pickupLocationName.indexOf(pickupSpin.getText().toString())!=-1)
                pickupId=pickupLocationId.get(pickupLocationName.indexOf(pickupSpin.getText().toString()));
            else
                pickupId="0";
        }

        if(deliverySpin.getText().toString().equalsIgnoreCase(""))
            deliveryId="0";
        else{
            if(deliveryLocationName.indexOf(deliverySpin.getText().toString())!=-1){
                deliveryId=deliveryLocationId.get(deliveryLocationName.indexOf(deliverySpin.getText().toString()));
            }
            else
                deliveryId="0";
        }
        yardId="0";
//        if(yardSpin.getText().toString().equalsIgnoreCase(""))
//            yardId="0";
//        else{
//            if(yardLocationName.indexOf( yardSpin.getText().toString())!=-1){
//                yardId= yardLocationId.get( yardLocationName.indexOf( yardSpin.getText().toString()));
//            }
//            else
//                yardId="0";
//        }

        String pickupdateTime=(""+pickupdate.getText());
        String deliverydateTime=(""+deliveydate.getText());
//        String[] yarddateTime=(""+yarddate.getText()).split(" ");
        String yardDate,yardtime;
         if(pickupdateTime.length()<3){
             MDToast mdToast = MDToast.makeText(this, "Select Pickup DateTime", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
             mdToast.show();
             return;
         }
         if(pickupId.equalsIgnoreCase("0")){
             MDToast mdToast = MDToast.makeText(this, "Select Pickup Location", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
             mdToast.show();
             return;
         }

         if(deliverydateTime.length()<3){
             MDToast mdToast = MDToast.makeText(this, "Select Delivery DateTime", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
             mdToast.show();
             return;
         }
         if(deliveryId.equalsIgnoreCase("0")){
             MDToast mdToast = MDToast.makeText(this, "Select Delivery Location", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
             mdToast.show();
             return;
         }
//         if(orderType.equalsIgnoreCase("Export")){
//             if(yarddateTime.length<3){
//                 MDToast mdToast = MDToast.makeText(this, "Select Yard dateTime", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                 mdToast.show();
//                 return;
//             }
//             if(yardId.equalsIgnoreCase("0")){
//                 MDToast mdToast = MDToast.makeText(this, "Select Yard Address", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                 mdToast.show();
//                 return;
//             }
//         }else

             if(orderType.equalsIgnoreCase("InterProvincial")){
             if(pickupSpin.getText().toString().equalsIgnoreCase(deliverySpin.getText().toString())){
                 MDToast mdToast = MDToast.makeText(this, "Pickup and Delivery location could not be same", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                 mdToast.show();
                 return;
             }
         }else if(orderType.equalsIgnoreCase("InterState")){
             if(pickupSpin.getText().toString().equalsIgnoreCase(deliverySpin.getText().toString())){
                 MDToast mdToast = MDToast.makeText(this, "Pickup and Delivery location could not be same", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                 mdToast.show();
                 return;
             }
         }

        Date strDate = null,endDate=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a",Locale.ENGLISH);
            strDate =(Date) sdf.parse(pickupdate.getText()+"");
            endDate = (Date) sdf.parse(deliveydate.getText()+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if(endDate==null){
                MDToast mdToast = MDToast.makeText(this, "Select delivery date", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                mdToast.show();
                return;
            }
            if(strDate==null){
                MDToast mdToast = MDToast.makeText(this, "Select Pickup date", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                mdToast.show();
                return;
            }
            if (!endDate.equals(strDate) && !endDate.after(strDate)) {
                MDToast mdToast = MDToast.makeText(this, "Pickup date should be less then and equal to Delivery date", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                mdToast.show();
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        yardDate="";
        yardtime="";
//        if(yarddateTime.length<3){
//             yardDate="";
//             yardtime="";
//        }else {
//             yardDate=yarddateTime[0];
//             yardtime=yarddateTime[1]+" "+yarddateTime[2];
//        }

        showAnimation();
//        RouteSelectionModel vindetail=new RouteSelectionModel(prf.getStringData("authKey"),orderType,pickupId,
//                deliveryId,prf.getStringData("carrierPrimaryId"),pickupdateTime[0],pickupdateTime[1]+" "+pickupdateTime[2],deliverydateTime[1]+" "+deliverydateTime[2],deliverydateTime[0],""+pickup_remark.getText(),""+delivery_remark.getText(),yardId,yardDate,yardtime,""/*+yard_remark.getText(*/,tempOrderId);
              Call<RouteSelectionModel> call1 = apiInterface.getTempIds(""+tempOrderId,orderType,""+pickupId,new Util().getUtcToOrderdateTime(pickupdateTime),""+ prf.getStringData("userCode"),""+deliveryId,new Util().getUtcToOrderdateTime(deliverydateTime),""+pickup_remark.getText(),""+distance,""+distanceUnit,""+delivery_remark.getText(),yardId,yardDate+" "+yardtime,"","0","",""+FreightTypeLuCode,""+ prf.getStringData("userName"),new Util().getDateTime(),""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<RouteSelectionModel>() {
            @Override
            public void onResponse(Call<RouteSelectionModel> call, Response<RouteSelectionModel> response) {

                RouteSelectionModel getRespone = response.body();
                Log.e("get JSon",getRespone.toString());
                hideAnimation();
                try {


                    if (getRespone.satus) {


                        PreferenceManager prf = new PreferenceManager(RouteSelectionActivity.this);
                        prf.saveStringData("OrderId", getRespone.tempOrderId);
                        prf.saveStringData("OrderType", "" + type.getText());
                        prf.saveStringData("When", "add");
                        if(next) {
                            Intent i = new Intent(RouteSelectionActivity.this, VehicleListActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("OrderId",getRespone.tempOrderId);
//                      i.putExtra("when",getRespone.data.temVehicleId);
                            startActivity(i);
                        }else{
                            MDToast mdToast = MDToast.makeText(RouteSelectionActivity.this, save_btn.getText()+" Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
                        }
                    } else {
                        MDToast mdToast = MDToast.makeText(RouteSelectionActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(Call<RouteSelectionModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(RouteSelectionActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.main_selection);
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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        Log.e("fghgh","value 1");
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    void getDetail(){
        Gson gson = new Gson();
        if ( getIntent().getStringExtra("ModelClass") != null) {
            OrderListModel.datavalue1 listObject = gson.fromJson(getIntent().getStringExtra("ModelClass"), new TypeToken<OrderListModel.datavalue1>() {
            }.getType());


            String strDate = null,endDate=null;


            strDate = new Util().getUtcToCurrentTime(listObject.PickupDateTime);
            endDate = new Util().getUtcToCurrentTime(listObject.DropDateTime);

            tempOrderId=listObject.orderId;
            pickupdate.setText("" + strDate);
            deliveydate.setText("" + endDate);
            prf.saveStringData("OrderId",tempOrderId);
            pickup_remark.setText(listObject.pickupNote);
            delivery_remark.setText(listObject.dropNote);

            FreightTypeLuCode=listObject.FreightTypeLuCode;
            freightType(listObject.FreightTypeLuCode);


            Log.e("pickupId",""+listObject.pickupId);
            if(pickupLocationId.indexOf(listObject.pickupId)==-1){
                pickupId="0";
                pickupSpin.setText("");
            }else{
                pickupId = listObject.pickupId;
                pickupSpin.setText(pickupLocationName.get(pickupLocationId.indexOf(listObject.pickupId)));
            }
            if(deliveryLocationId.indexOf(listObject.dropId)==-1){
                deliveryId="0";
                deliverySpin.setText("");
            }else{
                deliveryId = listObject.dropId;
                deliverySpin.setText(deliveryLocationName.get(deliveryLocationId.indexOf(listObject.dropId)));
            }
            try {
                getMiles();
            } catch (Exception e) {
                e.printStackTrace();
            }
//                       if(!prf.getStringData("OrderStatus").equalsIgnoreCase("save")){
//                           pickupSpin.dismissDropDown();
//                           deliverySpin.dismissDropDown();
//                       }
            add.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
            add1.setImageBitmap(new MyImage().decodeSampledBitmapFromResource(getResources(), R.mipmap.info, 30, 30));
            pickupSpin.dismissDropDown();
            deliverySpin.dismissDropDown();
//                       pickupSpin.setText(pickupLocationName.get(pickupLocationId.indexOf(getdata.dataValue.pickupId)));
//                       deliverySpin.setText(deliveryLocationName.get(deliveryLocationId.indexOf(getdata.dataValue.dropId)));
            if(prf.getStringData("OrderStatus").equalsIgnoreCase("Saved") || prf.getStringData("OrderStatus").equalsIgnoreCase("Shipped")){
                save_btn.setText("Update");
                save_btn.setVisibility(View.VISIBLE);
            }

             hideAnimation();
            Log.e("data",listObject.PickupName);
        }
    }
   void getMiles()throws Exception{
       miles.setText("Distance "+" : "+0.00);
        if(pickupId.equalsIgnoreCase("0"))
            return;

        if(deliveryId.equalsIgnoreCase("0"))
            return;

       distanceUnit="";
       distance="";

       final PreferenceManager prf=new PreferenceManager(this);
       Call<MilesModel> call1 = apiInterface.getTotalMiles(""+pickupId,""+deliveryId,""+ prf.getStringData("corporateId"),"bearer "+ prf.getStringData("accessToken"),"application/json");
       call1.enqueue(new Callback<MilesModel>() {
           @Override
           public void onResponse(Call<MilesModel> call, Response<MilesModel> response) {
           hideAnimation();
               MilesModel getdata = response.body();
               try{

                       if(getdata.status) {
//                   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                           distanceUnit=getdata.dataValuer.DistanceUnit;
                                   distance=""+getdata.dataValuer.TotalMiles;
                           miles.setText("Distance "+" : "+String.format("%.2f", getdata.dataValuer.TotalMiles) +" "+getdata.dataValuer.DistanceUnit);

// GetVehicleIdListModel tripDetails;
//                   OrderListAdapter adapterd = new OrderListAdapter(getdata3,getActivity());
//                   recyclerView.setAdapter(adapterd);
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
               }else{
                   MDToast mdToast = MDToast.makeText(RouteSelectionActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                   mdToast.show();
               }
               }catch (Exception e){
                   e.printStackTrace();
                   new Util().sendSMTPMail(RouteSelectionActivity.this,null,"CxE004",e,""+call.request().url().toString());
               }
           }
           @Override
           public void onFailure(Call<MilesModel> call, Throwable t) {
               call.cancel();
               new Util().sendSMTPMail(RouteSelectionActivity.this,t,"CxE001",null,""+call.request().url().toString());
               MDToast mdToast = MDToast.makeText(RouteSelectionActivity.this, "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
               mdToast.show();
               hideAnimation();
           }
       });
    }
    void popUp(String id)throws Exception{
        final Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.view_location
                , null));
        settingsDialog.show();
        final TextView loc_name=(TextView)settingsDialog.findViewById(R.id.loc_name);
        final TextView loc_address=(TextView)settingsDialog.findViewById(R.id.loc_address);
        final TextView loc_country=(TextView)settingsDialog.findViewById(R.id.loc_country);
        final TextView loc_state=(TextView)settingsDialog.findViewById(R.id.loc_state);
        final TextView loc_city=(TextView)settingsDialog.findViewById(R.id.loc_city);
        final TextView loc_zip=(TextView)settingsDialog.findViewById(R.id.loc_zip);
        final TextView loc_contact_prsion=(TextView)settingsDialog.findViewById(R.id.loc_contact_prsion);
        final TextView loc_email=(TextView)settingsDialog.findViewById(R.id.loc_email);
        LinearLayout ok=(LinearLayout)settingsDialog.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.dismiss();
            }
        });
        Call<PrimaryInfoDetailModel> call1 = apiInterface.getPrimaryInfoDetail(""+id,""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<PrimaryInfoDetailModel>() {
            @Override
            public void onResponse(Call<PrimaryInfoDetailModel> call, Response<PrimaryInfoDetailModel> response) {
                final PrimaryInfoDetailModel dropdata1 = response.body();
                hideAnimation();

                if(dropdata1.Status) {
//                    MDToast mdToast = MDToast.makeText(AddLocationActivity.this, "Location Added", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                    mdToast.show();
//                    Intent resultIntent = new Intent();
//                    // TODO Add extras or a data URI to this intent as appropriate.
//                    resultIntent.putExtra("id", dropdata1.id);
//                    resultIntent.putExtra("LocationName", locationName.getText().toString());
//                    setResult(Activity.RESULT_OK, resultIntent);
//                    finish();

                    try {
                        loc_name.setText(dropdata1.data.Name);
                        loc_address.setText(dropdata1.data.AddressOne);
                        loc_country.setText(dropdata1.data.CountryName);
                        loc_state.setText(dropdata1.data.StateName);
                        loc_city.setText(dropdata1.data.City);
                        loc_zip.setText(dropdata1.data.PostalCode);
                        loc_contact_prsion.setText("");
//                        if(dropdata1.data.!=null) {
//                            if (!dropdata1.datavalue.contactPerson.equalsIgnoreCase("null"))
//                                loc_contact_prsion.setText("Contact Person : " + dropdata1.datavalue.contactPerson);
//                            else
//                                loc_contact_prsion.setText("Contact Person : ");
//                        }else
//                            loc_contact_prsion.setText("Contact Person : ");
                        if(dropdata1.data.Phone!=null) {
                            if (!dropdata1.data.Phone.equalsIgnoreCase("null"))
                                loc_email.setText("Mobile : " + dropdata1.data.Phone);
                            else
                                loc_email.setText("Mobile : ");
                        }else
                        loc_email.setText("Mobile : ");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
//                else{
//                    MDToast mdToast = MDToast.makeText(AddLocationActivity.this, dropdata1.message, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                    mdToast.show();
//                }




            }

            @Override
            public void onFailure(Call<PrimaryInfoDetailModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(RouteSelectionActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });

    }
}