package com.avaal.com.afm2020autoCx;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avaal.com.afm2020autoCx.models.AddCityZipModel;
import com.avaal.com.afm2020autoCx.models.AddLocationModel;
import com.avaal.com.afm2020autoCx.models.DropDownModel;
import com.avaal.com.afm2020autoCx.models.ForAddModel;
import com.avaal.com.afm2020autoCx.models.GetLocationDetailModel;
import com.avaal.com.afm2020autoCx.models.LocationDetailModel;
import com.avaal.com.afm2020autoCx.models.MasterDropDownModel;
import com.avaal.com.afm2020autoCx.models.PrimaryInfoDetailModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 28-02-2018.
 */

public class AddLocationActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.spinner_country)
    TextView countrySpin;
    @BindView(R.id.spinner_state)
    Spinner stateSpin;
    @BindView(R.id.city)
    EditText citySpin;
    @BindView(R.id.zip)
    EditText zipSpin;
    @BindView(R.id.location_name)
    EditText locationName;
    @BindView(R.id.location_address)
    EditText locationAddress;
    @BindView(R.id.addziptxt)
    ImageView addziptxt;
    @BindView(R.id.addcitytxt)
    ImageView addcitytxt;
    @BindView(R.id.bottom_save)
            LinearLayout bottom_save;
    ArrayAdapter<String> adapter;
    String getCountryId;
    APIInterface apiInterface;
    List<String> countryId = new ArrayList<>();
    List<String> countryName = new ArrayList<>();
    List<String> stateId = new ArrayList<>();
    List<String> stateName = new ArrayList<>();
    List<String> cityId = new ArrayList<>();
    List<String> cityName = new ArrayList<>();
    List<String> zipId = new ArrayList<>();
    List<String> zipName = new ArrayList<>();
    String stateId_="0",cityId_="0",zipId_="0";
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    PreferenceManager prf;
    String locationId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        title.setText("Add Location");
         prf=new PreferenceManager(this);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
         apiInterface = APIClient.getClient().create(APIInterface.class);
         getCountryId=getIntent().getStringExtra("countryId");
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
//        getLocation(getIntent().getStringExtra("locationId"));
//        dropDownValue("State",getCountryId);
        countryName.add("Canada");
        countryName.add("United States");
        setSpinner1("Country");
        setSpinner1("State");
          if(getIntent().getStringExtra("locationId")!=null){
              locationId=getIntent().getStringExtra("locationId");
              getLocation(getIntent().getStringExtra("locationId"));
          }
        getCompanyList(getCountryId);
        if(getCountryId.equalsIgnoreCase("CA"))
            countrySpin.setText(countryName.get(0));
        else
            countrySpin.setText(countryName.get(1));


//        zipSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i>0){
//                    dropDownValue("Zip Code",zipId.get(i));
//                }
//            }
//        });
//        this.setTitle("Create A Trip ");
//
////Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();
    }
    @OnClick(R.id.back)
    void back(){
        super.onBackPressed();
    }
    @OnClick(R.id.home_)
    void home(){
        Intent j = new Intent(this, NewDashBoardActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        startActivity(j);
    }


    @OnClick(R.id.saveloc1)
    void saveLocation(){
         prf=new PreferenceManager(this);
        String fg=""+stateSpin.getSelectedItem();
        if(locationName.getText().toString().equalsIgnoreCase(" ") || locationName.getText().length()<2){
            MDToast mdToast = MDToast.makeText(this, "Enter Location Name", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(locationAddress.getText().toString().equalsIgnoreCase(" ") || locationAddress.getText().length()<2){
            MDToast mdToast = MDToast.makeText(this, "Enter Location Address", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(stateId_.equalsIgnoreCase("0"))
        {
            MDToast mdToast = MDToast.makeText(this, "Select State", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(citySpin.getText().toString().trim().equalsIgnoreCase(""))
        {
            MDToast mdToast = MDToast.makeText(this, "Enter City", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(zipSpin.getText().toString().trim().equalsIgnoreCase(""))
        {
            MDToast mdToast = MDToast.makeText(this, "Enter ZipCode", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(!new Util().isValidPostalCode(zipSpin.getText().toString(),getCountryId)){
            MDToast mdToast = MDToast.makeText(this, "Enter Correct ZipCode", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(!new Util().isValidAdde(locationAddress.getText().toString())){
            MDToast mdToast = MDToast.makeText(this, "Enter Correct Address", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        if(!new Util().isValidCity(citySpin.getText().toString())){
            MDToast mdToast = MDToast.makeText(this, "Enter Correct City", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        showAnimation();
        saveEmployeeDetail();


    }
    void dropDownValue(final String type, String from) {

        prf = new PreferenceManager(AddLocationActivity.this);
        DropDownModel user = new DropDownModel(type, from, "", prf.getStringData("authKey"));
        Call<DropDownModel> call1 = apiInterface.getLocation("Location","true",""+prf.getStringData("userName"),""+prf.getStringData("corporateId"),"IN","bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<DropDownModel>() {
            @Override
            public void onResponse(Call<DropDownModel> call, Response<DropDownModel> response) {

                DropDownModel dropdata = response.body();
                try {
                    if (dropdata.status) {
                        if (type.equalsIgnoreCase("Country")) {
                            for (int i = 0; dropdata.data.size() > i; i++) {
                                countryId.add(dropdata.data.get(i).id);
                                countryName.add(dropdata.data.get(i).name);
                            }
                        } else if (type.equalsIgnoreCase("State")) {
                            for (int i = 0; dropdata.data.size() > i; i++) {
                                stateId.add(dropdata.data.get(i).id);
                                stateName.add(dropdata.data.get(i).name);
                            }
                        } else if (type.equalsIgnoreCase("City")) {
                            cityId.clear();
                            cityName.clear();
                            for (int i = 0; dropdata.data.size() > i; i++) {
                                cityId.add(dropdata.data.get(i).id);
                                cityName.add(dropdata.data.get(i).name);
                            }
                        } else if (type.equalsIgnoreCase("ZipCode")) {
                            zipId.clear();
                            zipName.clear();
                            for (int i = 0; dropdata.data.size() > i; i++) {
                                zipId.add(dropdata.data.get(i).id);
                                zipName.add(dropdata.data.get(i).name);
                            }
                            try {
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                new Util().sendSMTPMail(AddLocationActivity.this,null,"CxE004",e,"");
                                e.printStackTrace();
                            }
                        }

                    }
                    setSpinner1(type);
                }catch (Exception e){
                    e.printStackTrace();
                    new Util().sendSMTPMail(AddLocationActivity.this,null,"CxE004",e,"");
                }



            }

            @Override
            public void onFailure(Call<DropDownModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddLocationActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void setSpinner1(String type) {
        if(type.equalsIgnoreCase("Country")) {
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_spinner_item, countryName);
//// Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//            countrySpin.setAdapter(adapter);
//            countrySpin.setClickable(false);
//            countrySpin.setEnabled(false);

        }else if(type.equalsIgnoreCase("State")) {
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, stateName);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            stateSpin.setAdapter(adapter);

            stateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    stateId_=stateId.get(i);
//                    if(!stateId.get(i).equalsIgnoreCase("0")){
//                        try {
//                            adapter4.notifyDataSetChanged();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
////                        zipSpin.setSelection(0);
//                        stateId_=stateId.get(i);
//                    }else{
//
//                        addcitytxt.setEnabled(false);
//                        addcitytxt.setClickable(false);
//                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

//        stateAdapter.setDropDownViewResource(R.layout.spinner_textview_align);
    }


//    void addcityZip(String state1, final String cityname1, final String cityid1, final String zip1){
//        AddCityZipModel addLocat = new AddCityZipModel(prf.getStringData("authKey"),state1,cityname1,cityid1,zip1);
//        Call<AddCityZipModel> call1 = apiInterface.addCityZip(addLocat);
//        call1.enqueue(new Callback<AddCityZipModel>() {
//            @Override
//            public void onResponse(Call<AddCityZipModel> call, Response<AddCityZipModel> response) {
//
//                AddCityZipModel login = response.body();
//                try {
//                    if (login.status) {
//                        if (cityid1.equalsIgnoreCase("0")) {
//                            MDToast mdToast = MDToast.makeText(AddLocationActivity.this, "City and Zip Added", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                            mdToast.show();
//                            dropDownValue("City", stateId_);
//                            cityId_ = login.id;
//                            dropDownValue("ZipCode", login.id);
//
//
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    citySpin.setSelection(cityName.indexOf(cityname1));
////                               zipSpin.setSelection(zipName.indexOf(zip1));
////                               zipId_=zipId.get(zipName.indexOf(zip1));
//                                }
//                            }, 2000);
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            zipSpin.setSelection(zipName.indexOf(zip1));
//                                            zipId_ = zipId.get(zipName.indexOf(zip1));
//                                        }
//                                    });
//
//                                }
//                            }, 3000);
//                            hideSoftKeyboard(AddLocationActivity.this);
//
//                        } else {
//                            MDToast mdToast = MDToast.makeText(AddLocationActivity.this, "Zip Added", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                            mdToast.show();
//                            zipId_ = login.id;
//                            dropDownValue("ZipCode", cityId_);
//
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    zipSpin.setSelection(zipName.indexOf(zip1));
//                                }
//                            }, 2000);
//                            hideSoftKeyboard(AddLocationActivity.this);
//                        }
//                    }else{
//                        MDToast mdToast = MDToast.makeText(AddLocationActivity.this,"Already Exist" , MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                        mdToast.show();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<AddCityZipModel> call, Throwable t) {
//                call.cancel();
//            }
//        });
//    }
    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.location_main);
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
    void getLocation(String id){

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
                        hideSoftKeyboard(AddLocationActivity.this);
                        bottom_save.setVisibility(View.GONE);
                        addziptxt.setVisibility(View.GONE);
                        addcitytxt.setVisibility(View.GONE);
                        locationName.setText(dropdata1.data.Name);
                        locationAddress.setText(dropdata1.data.AddressOne);
//                    countrySpin.setSelection(countryName.indexOf(dropdata1.datavalue.country));
                        final String state=dropdata1.data.StateCode;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stateSpin.setSelection(stateName.indexOf(state.trim()));
                            }
                        }, 2000);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                citySpin.setSelection(cityName.indexOf(dropdata1.data.City));
                            }
                        }, 3000);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                zipSpin.setSelection(zipName.indexOf(dropdata1.data.PostalCode));
                            }
                        }, 4000);
                        locationName.setClickable(false);
                        locationName.setEnabled(false);
                        locationAddress.setClickable(false);
                        locationAddress.setEnabled(false);
                        stateSpin.setClickable(false);
                        stateSpin.setEnabled(false);
                    }catch (Exception e){
                        e.printStackTrace();
                        new Util().sendSMTPMail(AddLocationActivity.this,null,"CxE004",e,"");
                    }

                }
                else{
                    MDToast mdToast = MDToast.makeText(AddLocationActivity.this, dropdata1.Message, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                    mdToast.show();
                }




            }

            @Override
            public void onFailure(Call<PrimaryInfoDetailModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddLocationActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getCompanyList(String filter1){
//        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<MasterDropDownModel> call1 = apiInterface.getMasterDropDown("state",true,""+prf.getStringData("userName"),filter1,null,""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<MasterDropDownModel>() {
            @Override
            public void onResponse(Call<MasterDropDownModel> call, Response<MasterDropDownModel> response) {


                Log.e("message ",response.message());
//                hideAnimation();
                if(response.message().equalsIgnoreCase("ok")) {

                    MasterDropDownModel userdeatail = response.body();
//                hideAnimation();
//                    if(userdeatail.Status) {
                    try {
                        stateId.add("0");
                        stateName.add("Select");
                           for (int i = 0; userdeatail.data.size() > i; i++) {
                               stateId.add(userdeatail.data.get(i).Code);
                               stateName.add(userdeatail.data.get(i).Name);
                               if(i==userdeatail.data.size()-1)
                                   adapter.notifyDataSetChanged();
                           }



//                            name.setText(userdeatail.data.Name);
//                            phone.setText(userdeatail.data.Phone);
//                            firstline.setText(userdeatail.data.FullAddress);
//                            country.setText(userdeatail.data.Country);
//                            state.setText(userdeatail.data.State);
//                            city.setText(userdeatail.data.City);
//                            postal.setText(userdeatail.data.PostalCode);
//                            company.setText(userdeatail.data.CompanyName);

                    } catch (Exception e) {
                        e.printStackTrace();
                        new Util().sendSMTPMail(AddLocationActivity.this,null,"CxE004",e,"");
                    }
//                    }
//                    else{
//                        MDToast mdToast = MDToast.makeText(PrimaryInfoDetailActivity.this, "" + userdeatail.Message, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
//                        mdToast.show();
//                    }
                }else{
                    MDToast mdToast = MDToast.makeText(AddLocationActivity.this, "" + response.code(), MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                    mdToast.show();
                }

            }

            @Override
            public void onFailure(Call<MasterDropDownModel> call, Throwable t) {
                hideAnimation();
                call.cancel();
                new Util().sendSMTPMail(AddLocationActivity.this,t,"CxE001",null,""+call.request().url().toString());

            }
        });
    }
    void saveEmployeeDetail(){
        showAnimation();
        Call<ForAddModel> call1 = apiInterface.addPrimaryInfo("3",""+locationId,""+prf.getStringData("CompnyCode"),""+locationName.getText(),""+locationAddress.getText(),""+getCountryId,""+stateId_,""+zipSpin.getText(),""+citySpin.getText(),false,false,true,""+prf.getStringData("userName"),null,""+prf.getStringData("corporateId"),"Mobile","bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<ForAddModel>() {
            @Override
            public void onResponse(Call<ForAddModel> call, Response<ForAddModel> response) {

                hideAnimation();
                Log.e("message ",response.message());
//                hideAnimation();
                if(response.message().equalsIgnoreCase("ok")) {

                    ForAddModel userdeatail = response.body();
//                hideAnimation();
                    if(userdeatail.staus) {
                        MDToast mdToast = MDToast.makeText(AddLocationActivity.this, "" + userdeatail.Message, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
                        Intent resultIntent = new Intent();
                        // TODO Add extras or a data URI to this intent as appropriate.
                        resultIntent.putExtra("id",userdeatail.Value);
                        resultIntent.putExtra("LocationName", locationName.getText().toString());
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }else {
                        MDToast mdToast = MDToast.makeText(AddLocationActivity.this, "" + userdeatail.Message, MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                        mdToast.show();

                    }



//                    if (login.Status) {
//                        MDToast mdToast = MDToast.makeText(LoginActivity.this, "Login Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                        mdToast.show();

//

//
//                        if(remember.isChecked()) {
//                            prf.saveStringData("password", password.getText().toString());
//                            prf.saveBoolData("remember", true);
//                        }
//                        else
//                            prf.saveBoolData("remember", false);
//                        Intent j = new Intent(LoginActivity.this, DashBoardBottomMenu.class);
//                        j.putExtra("open", "home");
//                        j.putExtra("AuthKey", login.datavalue.authkey);
//                        startActivity(j);
//                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                        finish();
//
//
//                    } else {
//                        MDToast mdToast = MDToast.makeText(LoginActivity.this, login.message, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//                        mdToast.show();
//                    }
                    

                }else{
                    MDToast mdToast = MDToast.makeText(AddLocationActivity.this, "" + response.code(), MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                    mdToast.show();
                }

            }

            @Override
            public void onFailure(Call<ForAddModel> call, Throwable t) {
//                hideAnimation();
                hideAnimation();
                call.cancel();
                new Util().sendSMTPMail(AddLocationActivity.this,t,"CxE001",null,""+call.request().url().toString());

            }
        });
    }
}