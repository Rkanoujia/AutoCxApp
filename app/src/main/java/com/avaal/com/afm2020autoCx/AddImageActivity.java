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
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.ExtraImageList;
import com.avaal.com.afm2020autoCx.models.GetImageModel;
import com.avaal.com.afm2020autoCx.models.RemoveImageModel;
import com.avaal.com.afm2020autoCx.models.SaveImageModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.GPSTrackerService;
import extra.LatLongCheckListner;
import extra.MarshMallowPermission;
import extra.PreferenceManager;
import extra.UploadScreen;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 09-03-2018.
 */

public class AddImageActivity extends AppCompatActivity implements LatLongCheckListner {
    @BindView(R.id.front_left)
    ImageView front_left;
    @BindView(R.id.front_right)
    ImageView front_right;
    @BindView(R.id.back_left)
    ImageView back_left;
    @BindView(R.id.back_right)
    ImageView back_right;
    @BindView(R.id.windshield)
    ImageView windshield;
    @BindView(R.id.roof)
    ImageView roof;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back_left_imageview)
    ImageView back_left_imageview;
    @BindView(R.id.back_right_imageview)
    ImageView back_right_imageview;
    @BindView(R.id.front_right_imageview)
    ImageView front_right_imageview;
    @BindView(R.id.front_left_imageview)
    ImageView front_left_imageview;
    @BindView(R.id.windshield_imageview)
    ImageView windshield_imageview;
    @BindView(R.id.roof_imageview)
    ImageView roof_imageview;

    @BindView(R.id._image_list)
    RecyclerView _image_list;

    @BindView(R.id.add_image)
    ImageView add_image;


    @BindView(R.id.front_left_imageedit)
    ImageView front_left_imageedit;
    @BindView(R.id.front_right_imageedit)
    ImageView front_right_imageedit;
    @BindView(R.id.back_left_imageedit)
    ImageView back_left_imageedit;
    @BindView(R.id.back_right_imageedit)
    ImageView back_right_imageedit;
    @BindView(R.id.windshield_imageedit)
    ImageView windshield_imageedit;
    @BindView(R.id.roof_imageedit)
    ImageView roof_imageedit;

    @BindView(R.id.front_left_imagedelete)
    ImageView front_left_imagedelete;
    @BindView(R.id.front_right_imagedelete)
    ImageView front_right_imagedelete;
    @BindView(R.id.back_left_imagedelete)
    ImageView back_left_imagedelete;
    @BindView(R.id.back_right_imagedelete)
    ImageView back_right_imagedelete;
    @BindView(R.id.windshield_imagedelete)
    ImageView windshield_imagedelete;
    @BindView(R.id.roof_imagedelete)
    ImageView roof_imagedelete;


    private FrameLayout mainlayout;
    private UploadScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    String vehicleId,vin;
    private Uri picUri,outputUri;
    String imageside;
    APIInterface apiInterface;
    String imageFilePath,fullAddress="";
    PreferenceManager prf;
    String frontLeftfileId="0",frontRightfileId="0",backLeftfileId="0",backRightfileId="0",windshieldfileId="0",rooffileId="0";
    String frontLeftfileUrl="",frontRightfileUrl="",backLeftfileUrl="",backRightfileUrl="",windshieldfileUrl="",rooffileUrl="";
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    final int CROP_PIC = 2;
    Location location;
    ProgressDialog pd;
    RequestOptions options;
    HashMap<Integer, String> fixName;
    private static final int TAKE_PICTURE_REQUEST_B = 100;
    private Bitmap mCameraBitmap;
//    ArrayList<SaveImageModel.datavalue1> ExtraImageList=new ArrayList<>();
    Map<String,String> image =new HashMap<String, String>();
    List<Map<String , String>> ExtraImageList= new ArrayList<Map<String,String>>();
    ExtraImageList adapterd = null;
    boolean IsRefresh=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_image_activity);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        title.setText("Pre-Inspections");

        vehicleId=getIntent().getStringExtra("VehicleId");
        vin=getIntent().getStringExtra("Vin");
        GPSTrackerService gpstrack = new GPSTrackerService(AddImageActivity.this, this);
        location = gpstrack.getLocation();

        pd = new ProgressDialog(AddImageActivity.this);
        pd.setMessage("loading..");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (!gpstrack.canGetLocation()){
            pd.show();
        }else{
            location=gpstrack.getLocation();
            getAddress();
        }
         apiInterface = APIClient.getClient().create(APIInterface.class);
        recyclerView();
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_draw)
                .error(R.drawable.no_img_found)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

         fixName = new HashMap<Integer, String>();
        fixName.put(R.drawable.car_d, "");
        fixName.put(R.drawable.car_b, "");
        fixName.put(R.drawable.car_c, "");
        fixName.put(R.drawable.car_a, "");
        fixName.put(R.drawable.car_e, "");
        fixName.put(R.drawable.car_f, "");
//        img_type.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//           if(editable.length()>3){
//               add_image.setEnabled(true);
//               add_image.setClickable(true);
//
//           }else{
//               add_image.setEnabled(false);
//               add_image.setClickable(false);
////               add_image.setVisibility(View.GONE);
//           }
//            }
//        });

        prf=new PreferenceManager(this);
//try {
//    if(getIntent().getStringExtra("IsInventry")!=null  ){
//        if(getIntent().getStringExtra("IsInventry").equalsIgnoreCase("1")) {
//            showAnimation();
//            getImages(vehicleId);
//
//        }
//    }
//    else{
//        if (prf.getStringData("OrderStatus").equalsIgnoreCase("saved") || prf.getStringData("OrderStatus").equalsIgnoreCase("shipped")) {
//            showAnimation();
//            getImages(vehicleId);
//
//
//        } else {
//            showAnimation();
//            getAFMImages(vehicleId);
//
//        }
//    }
//
//}catch (Exception e)
//{
//    e.printStackTrace();
//}

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            marshmallow.checkLocationPermission();
//               marshmallow.checkPermissionForExternalStorage();
//                    marshmallow.checkPermissionForCall();
//            marshmallow.checkPermissionForCamera();
            ActivityCompat.requestPermissions(AddImageActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.CAMERA}, 1);

//            marshmallow.checkPermissionForExternalStorage();
        }
        IsRefresh=false;
        getList(true);
//        hideSoftKeyboard(this);
    }
    @Override
    public void onBackPressed() {
        // Write your code here


        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(IsRefresh)
        getList(true);
        IsRefresh=true;
    }
  void getList(boolean isRefresh){
      prf=new PreferenceManager(this);
      try {
          if(getIntent().getStringExtra("IsInventry")!=null  ){
              if(getIntent().getStringExtra("IsInventry").equalsIgnoreCase("1")) {
                  showAnimation();
                  getImages(vehicleId);

              }
          }
          else{
              if (prf.getStringData("OrderStatus").equalsIgnoreCase("saved") || prf.getStringData("OrderStatus").equalsIgnoreCase("shipped")) {
                  showAnimation();
                  getImages(vehicleId);
              } else {
                  showAnimation();
                  getAFMImages(vehicleId);

              }
          }

      }catch (Exception e)
      {
          e.printStackTrace();
      }
  }
    @OnClick(R.id.save_)
        void save(){
//        if(frontLeftfileUrl.equalsIgnoreCase("")){
//            MDToast mdToast = MDToast.makeText(this, "Please Add FrontLeft Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//            mdToast.show();
//            return;
//        }
//        if(frontRightfileUrl.equalsIgnoreCase("")){
//            MDToast mdToast = MDToast.makeText(this, "Please Add FrontRight Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//            mdToast.show();
//            return;
//        }
//        if(backLeftfileUrl.equalsIgnoreCase("")){
//            MDToast mdToast = MDToast.makeText(this, "Please Add BackLeft Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//            mdToast.show();
//            return;
//        }
//        if(backRightfileUrl.equalsIgnoreCase("")){
//            MDToast mdToast = MDToast.makeText(this, "Please Add BackRight Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//            mdToast.show();
//            return;
//        }
//        if(windshieldfileUrl.equalsIgnoreCase("")){
//            MDToast mdToast = MDToast.makeText(this, "Please Add Windshield Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//            mdToast.show();
//            return;
//        }
//        if(rooffileUrl.equalsIgnoreCase("")){
//            MDToast mdToast = MDToast.makeText(this, "Please Add Roof Image", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
//            mdToast.show();
//            return;
//        }

        Intent data = new Intent();
        data.putExtra("IsPreInspection","true");
        setResult(RESULT_OK,data);
        finish();
//        back();
    }
    @OnClick(R.id.home_)
    void home(){
        Intent j = new Intent(this, NewDashBoardActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        startActivity(j);
    }
    @OnClick(R.id.back)
    void back(){
        super.onBackPressed();
//        Intent data = new Intent();
//        data.putExtra("IsPreInspection","true");
//        setResult(RESULT_OK,data);
//        finish();
    }
    @OnClick(R.id.front_left_imageedit)
    void front(){
        imageside="FrontLeftAngleView";
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        fixName.put(R.drawable.car_a, "");

        opencamera();

    }
    @OnClick(R.id.front_right_imageedit)
    void frontright(){
        imageside="FrontRightAngleView";
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        fixName.put(R.drawable.car_c, "");

      opencamera();
    }
    @OnClick(R.id.add_damage)
    public void otherImage(){

        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
        HashMap<Integer,String> fixName2=new HashMap<Integer, String>();
        fixName2.put(R.drawable.car_d, "uploaded");
        fixName2.put(R.drawable.car_b, "uploaded");
        fixName2.put(R.drawable.car_c, "uploaded");
        fixName2.put(R.drawable.car_a, "uploaded");
        fixName2.put(R.drawable.car_e, "uploaded");
        fixName2.put(R.drawable.car_f, "uploaded");




        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_choose_view);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok = (Button) dialog.findViewById(R.id.buttonOk);
        Button cancel = (Button) dialog.findViewById(R.id.buttoncancel);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        Button buttoremove = (Button) dialog.findViewById(R.id.buttoremove);
        buttoremove.setVisibility(View.VISIBLE);
        buttoremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        title.setText("Upload images from");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(AddImageActivity.this,DamageActivity.class);
                intent.putExtra("IsGallery", "Yes");
                intent.putExtra("VehicleId",vehicleId);
                intent.putExtra("images",fixName2);
                intent.putExtra("IsEdit","yes");
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(AddImageActivity.this,DamageActivity.class);
                intent.putExtra("VehicleId",vehicleId);
                intent.putExtra("images",fixName2);
                intent.putExtra("IsEdit","yes");
                startActivity(intent);

            }
        });
        dialog.show();







    }

    @OnClick(R.id.back_left_imageedit)
    void backleft(){
        imageside="BackLeftAngleView";
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        fixName.put(R.drawable.car_f, "");
        opencamera();
    }
    @OnClick(R.id.windshield_imageedit)
    void windshield(){
        imageside="Windshield";
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        fixName.put(R.drawable.car_b, "");

        opencamera();
    }
    @OnClick(R.id.roof_imageedit)
    void roof(){
        imageside="Roof";
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        fixName.put(R.drawable.car_e, "");
        opencamera();
    }
//    @OnClick(R.id.front_left)
//    void front_left(){
//        try {
//            Log.e("frontleft",""+frontLeftfileUrl);
//            if(!frontLeftfileUrl.equalsIgnoreCase(""))
//            popUpList(frontLeftfileUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @OnClick(R.id.windshield)
//    void windshield_imageview(){
//        try {
//            if(!windshieldfileUrl.equalsIgnoreCase(""))
//                popUpList(windshieldfileUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @OnClick(R.id.front_right)
//    void front_right_imageview(){
//        try {
//            if(!frontRightfileUrl.equalsIgnoreCase(""))
//                popUpList(frontRightfileUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @OnClick(R.id.back_left)
//    void back_left_imageview(){
//        try {
//            if(!backLeftfileUrl.equalsIgnoreCase(""))
//                popUpList(backLeftfileUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @OnClick(R.id.roof)
//    void roof_imageview(){
//        try {
//            if(!rooffileUrl.equalsIgnoreCase(""))
//                popUpList(rooffileUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @OnClick(R.id.back_right)
//    void back_right_imageview(){
//        try {
//            if(!backRightfileUrl.equalsIgnoreCase(""))
//                popUpList(backRightfileUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    void opencamera(){
        Intent intent=new Intent(AddImageActivity.this,DamageActivity.class);
        intent.putExtra("VehicleId",vehicleId);
        intent.putExtra("images",fixName);
        intent.putExtra("IsEdit","yes");
        startActivity(intent);

//        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
//        if (!marshMallowPermission.checkPermissionForCamera()) {
//            marshMallowPermission.requestPermissionForCamera();
//        }  else {
////            Intent intent=new Intent(AddImageActivity.this, CameraActivity.class);
////            intent.putExtra("Name",""+imageside);
////            startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
//
//            Intent pictureIntent = new Intent(
//                    MediaStore.ACTION_IMAGE_CAPTURE);
//            if(pictureIntent.resolveActivity(getPackageManager()) != null){
//                //Create a file to store the image
//                File photoFile = null;
////                try {
//                    photoFile = createImageFile();
////                } catch (IOException ex) {
//                    // Error occurred while creating the File
//                }
//                if (photoFile != null) {
//                    Uri photoURI = FileProvider.getUriForFile(this, "com.avaal.com.afm2020autoCx.fileprovider", photoFile);
//                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                            photoURI);
//                    startActivityForResult(pictureIntent,
//                            CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
//                }
//            }
//
//
//        }
    }
    @OnClick(R.id.back_left)
    void  back_left(){
        popUp(backLeftfileUrl,backLeftfileId,"BackLeftAngleView");
    }
    @OnClick(R.id.back_right)
    void  back_right(){
        popUp(backRightfileUrl,backRightfileId,"BackRightAngleView");
    }
    @OnClick(R.id.front_left)
    void  front_left(){
        popUp(frontLeftfileUrl,frontLeftfileId,"FrontLeftAngleView");
    }
    @OnClick(R.id.front_right)
    void  front_right(){
        popUp(frontRightfileUrl,frontRightfileId,"FrontRightAngleView");
    }
    @OnClick(R.id.windshield)
    void  windshield_img(){
        popUp(windshieldfileUrl,windshieldfileId,"Windshield");
    }
    @OnClick(R.id.roof)
    void  roof_img(){
        popUp(rooffileUrl,rooffileId,"Roof");
    }


    @OnClick(R.id.front_left_imageview)
    void   front_left_imageview_reload()
    {
//        popUp(frontLeftfileUrl,"FrontLeftAngleView");
        Glide.with(AddImageActivity.this).load(frontLeftfileUrl).apply(options).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(front_left);
    }
    @OnClick(R.id.front_right_imageview)
    void   front_right_imageview_reload(){
//        popUp(frontRightfileUrl,"FrontRightAngleView");
        Glide.with(AddImageActivity.this).load(frontRightfileUrl).apply(options).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(front_right);
    }
    @OnClick(R.id.back_left_imageview)
    void back_left_imageview_reload(){
//        popUp(backLeftfileUrl,"BackLeftAngleView");
        Glide.with(AddImageActivity.this).load(backLeftfileUrl).apply(options).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(back_left);
    }
    @OnClick(R.id.back_right_imageview)
    void back_right_imageview_reload(){
//        popUp(backRightfileUrl,"BackRightAngleView");
        Glide.with(AddImageActivity.this).load(backRightfileUrl).apply(options).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(back_right);
    }
    @OnClick(R.id.windshield_imageview)
    void windshield_imageview_reload(){
//        popUp(windshieldfileUrl,"Windshield");
        Glide.with(AddImageActivity.this).load(windshieldfileUrl).apply(options).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(windshield);
    }
    @OnClick(R.id.roof_imageview)
    void roof_imgview_reload(){
        Glide.with(AddImageActivity.this).load(rooffileUrl).apply(options).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(roof);
//        popUp(rooffileUrl,"Roof");

    }

    @OnClick(R.id.back_left_imagedelete)
    void  back_left_delete(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("");
        message.setText("Are you sure you want delete this? ");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteImg(backLeftfileId,"BackLeftAngleView");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();








    }
    @OnClick(R.id.back_right_imagedelete)
    void  back_right_delete(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("");
        message.setText("Are you sure you want delete this? ");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteImg(backRightfileId,"BackRightAngleView");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();


    }
    @OnClick(R.id.front_left_imagedelete)
    void  front_left_delete(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("");
        message.setText("Are you sure you want delete this? ");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteImg(frontLeftfileId,"FrontLeftAngleView");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();


    }
    @OnClick(R.id.front_right_imagedelete)
    void  front_right_delete(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("");
        message.setText("Are you sure you want delete this? ");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteImg(frontRightfileId,"FrontRightAngleView");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();


    }
    @OnClick(R.id.windshield_imagedelete)
    void  windshield_img_delete(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("");
        message.setText("Are you sure you want delete this? ");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteImg(windshieldfileId,"Windshield");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();

    }
    @OnClick(R.id.roof_imagedelete)
    void  roof_img_delete(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("");
        message.setText("Are you sure you want delete this? ");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteImg(rooffileId,"Roof");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();



    }


    @OnClick(R.id.back_right_imageedit)
    void backright(){
        imageside="BackRightAngleView";
        Util util=new Util();
        if(!util.isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "Check Internet Connection", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }
  opencamera();

//        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
//        if (!marshMallowPermission.checkPermissionForCamera()) {
//            marshMallowPermission.requestPermissionForCamera();
//        }  else {
////            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//////
////            File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                picUri = FileProvider.getUriForFile(this,
////                        "com.avaal.com.afmautocustomer.fileprovider", f);
////            } else
////                picUri = Uri.fromFile(f);
//////
//////                    Log.d("save path",outputFileUri.toString());
////            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
////            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
////            startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
//            Intent pictureIntent = new Intent(
//                    MediaStore.ACTION_IMAGE_CAPTURE);
//            if(pictureIntent.resolveActivity(getPackageManager()) != null){
//                //Create a file to store the image
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) {
//                    // Error occurred while creating the File
//                }
//                if (photoFile != null) {
//                    Uri photoURI = FileProvider.getUriForFile(this, "com.avaal.com.afm2020autoCx.fileprovider", photoFile);
//                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                            photoURI);
////                    pictureIntent.createChooser(pictureIntent, "Back Right Corner");
//                    startActivityForResult(pictureIntent,
//                            CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
//                }
//            }


//        }
    }
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "temp";
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
           if(requestCode==CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE){
//                Bundle extras = data.getExtras();
//                Log.e("gjhdj",""+extras.get("File"));
//                imageFilePath = ""+extras.get("File");
                if(imageFilePath!=null) {
                    Log.e("save file path", imageFilePath);
                    showAnimation();
                    System.gc();
                    if(imageside.equalsIgnoreCase("BackRightAngleView")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveImage("BackRightAngleView", imageFilePath, backRightfileId);
                            }
                        }).start();

                    }
                    else if(imageside.equalsIgnoreCase("BackLeftAngleView")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                        saveImage("BackLeftAngleView", imageFilePath, backLeftfileId);
                            }
                        }).start();
                    }
                    else if(imageside.equalsIgnoreCase("FrontRightAngleView")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveImage("FrontRightAngleView", imageFilePath, frontRightfileId);
                            }
                        }).start();

                    }
                    else if(imageside.equalsIgnoreCase("FrontLeftAngleView")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveImage("FrontLeftAngleView", imageFilePath, frontLeftfileId);
                            }
                        }).start();

                    }else if(imageside.equalsIgnoreCase("Windshield")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveImage("Windshield", imageFilePath, windshieldfileId);
                            }
                        }).start();

                    }else if(imageside.equalsIgnoreCase("Roof")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveImage("Roof", imageFilePath, rooffileId);
                            }
                        }).start();

                    }
                    else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveImage(imageside, imageFilePath, "0");
                            }
                        }).start();

                    }
                }


            }
//            else if (requestCode == UCrop.REQUEST_CROP) {
//                final Uri resultUri = UCrop.getOutput(data);
//
//                if(resultUri!=null) {
//                    Log.e("save file path", resultUri.getPath());
//                    if(imageside.equalsIgnoreCase("back_right"))
//                    saveImage("BackRightCornerView",resultUri);
//                    else if(imageside.equalsIgnoreCase("back_left"))
//                        saveImage("BackLeftCornerView",resultUri);
//                    else if(imageside.equalsIgnoreCase("front_right"))
//                        saveImage("FrontRightCornerView",resultUri);
//                    else if(imageside.equalsIgnoreCase("front_left"))
//                        saveImage("FrontLeftCornerView",resultUri);
//                }
//                outputUri
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                Uri resultUri = result.getUri();
//                Uri selectedImage = data.getData();
//                Bundle extras = data.getExtras();
//                Log.e("select URI",selectedImage.toString());
//                final String filepath = getPath(this,selectedImage);
//                File myFile = new File(outputUri.getPath());
//////                final String name=String.valueOf("AFM"+System.currentTimeMillis()) + ".pdf";
//                final String name = myFile.getName();
//                final String filepath = myFile.getAbsolutePath();


//                if(new Util().getFileSize(new File(filepath))>2) {
//                    LayoutInflater inflater = getLayoutInflater();
//                    View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.toast_layout_root));
//                    TextView text = (TextView) layout.findViewById(R.id.text);
//                    text.setText("File size not be more than 2MB");
//                    Toast toast = new Toast(this);
//                    toast.setDuration(Toast.LENGTH_LONG);
//                    toast.setView(layout);
//                    toast.show();
//                    return;
//                }

//
//
//            }



        }
    }
    void saveImage(String docType,String imageFilePath,String fileid1){
//        if(uri==null){
//            return;
//        }
        System.gc();
          final File f=new File(imageFilePath);
//          File f1=f.getAbsoluteFile();
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
//                Locale.getDefault()).format(new Date());
        String lastdegit=vin.substring(vin.length() - 8);
          String fileName=docType+"-"+lastdegit;
        String filetemdata="";

        long length = f.length() / 1024;//size in KB
        if(length<150){
            MDToast mdToast = MDToast.makeText(this, "Image Size should be less then 150KB", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
            mdToast.show();
            return;
        }

        int size = 0;
        try {
            Bitmap thumbnail;
//            Bitmap thumbnail = BitmapFactory.decodeFile(f.getAbsolutePath());
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

            thumbnail = BitmapFactory.decodeFile(imageFilePath, bitmapOptions);
            ExifInterface exif = new ExifInterface(imageFilePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            thumbnail = rotateBitmap(thumbnail, orientation);
            thumbnail = getScaledBitmap(thumbnail, 800, 1200);
            Bitmap second = addText(thumbnail);
            thumbnail.recycle();
            filetemdata = bitmapToBase64(second);
            size= (int) filetemdata.length();
            saveImage(second, fileName);
            second.recycle();

            System.gc();
            Runtime.getRuntime().gc();
            }catch (OutOfMemoryError e){
                e.printStackTrace();
            } catch (Exception e) {
            e.printStackTrace();
        }

        size=size/1024 ;
//        Log.e("image Data:",filetemdata);
//        File file = new File(f.getPath());

//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("image/*"), file);

// MultipartBody.Part is used to send also the actual file nam
        PreferenceManager prf = new PreferenceManager(this);
// add another part within the multipart request
//        RequestBody fullName =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), "Your Name");
//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);


        SaveImageModel vindetail=new SaveImageModel(fileName,"image/jpeg",""+size,".jpeg",filetemdata,"",docType,vehicleId,fileid1,""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),""+ prf.getStringData("userName"));
//        Call<SaveImageModel> call1 = apiInterface.updateProfile(vehicleIds, fileNames,authKey1,content,docTypess,exten,size1,remarks, body);
        Call<SaveImageModel> call1 = apiInterface.saveImages(vindetail,"bearer "+ prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<SaveImageModel>() {
            @Override
            public void onResponse(Call<SaveImageModel> call, Response<SaveImageModel> response) {
                final SaveImageModel getdata = response.body();
                hideAnimation();
                    if(getdata.status) {

                        try {
                            if(f.exists())
                                f.delete();
               System.gc();


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (imageside.equalsIgnoreCase("BackRightAngleView")) {
                                        back_right_imageview.setVisibility(View.VISIBLE);
                                        back_right_imagedelete.setVisibility(View.VISIBLE);

                                        backRightfileId = getdata.imageId;
                                        backRightfileUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Picasso.with(AddImageActivity.this).load(backRightfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.back_right).into(back_right);

                                          }
                                      });

                                       if(rooffileUrl.equalsIgnoreCase(""))
                                        roof();
                                    } else if (imageside.equalsIgnoreCase("BackLeftAngleView")) {
                                        back_left_imageview.setVisibility(View.VISIBLE);
                                        back_left_imagedelete.setVisibility(View.VISIBLE);

                                        backLeftfileId = getdata.imageId;
                                        backLeftfileUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Picasso.with(AddImageActivity.this).load(backLeftfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.back_left).into(back_left);
                                            }
                                        });


                                        if(backRightfileUrl.equalsIgnoreCase(""))
                                        backright();
                                    } else if (imageside.equalsIgnoreCase("FrontRightAngleView")) {
                                        front_right_imageview.setVisibility(View.VISIBLE);
                                        front_right_imagedelete.setVisibility(View.VISIBLE);

                                        frontRightfileId = getdata.imageId;
                                        frontRightfileUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Picasso.with(AddImageActivity.this).load(frontRightfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.front_right).into(front_right);

                                            }
                                        });

                                      if(frontLeftfileUrl.equalsIgnoreCase(""))
                                       front();
                                    } else if (imageside.equalsIgnoreCase("FrontLeftAngleView")) {
                                        front_left_imageview.setVisibility(View.VISIBLE);
                                        front_left_imagedelete.setVisibility(View.VISIBLE);

                                        frontLeftfileUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Picasso.with(AddImageActivity.this).load(frontLeftfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.front_left).into(front_left);

                                            }
                                        });
                                         frontLeftfileId = getdata.imageId;
                                       if(backLeftfileUrl.equalsIgnoreCase(""))
                                        backleft();
                                    } else if (imageside.equalsIgnoreCase("Windshield")) {
                                        windshield_imageview.setVisibility(View.VISIBLE);
                                        windshield_imagedelete.setVisibility(View.VISIBLE);

                                        windshieldfileUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Picasso.with(AddImageActivity.this).load(windshieldfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.windshield).into(windshield);

                                            }
                                        });
                                         windshieldfileId = getdata.imageId;

                                    }else if (imageside.equalsIgnoreCase("Roof")) {
                                        roof_imageview.setVisibility(View.VISIBLE);
                                        roof_imagedelete.setVisibility(View.VISIBLE);
                                        rooffileUrl = getdata.docUrl.replace("DelhiServer", "192.168.1.20");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Picasso.with(AddImageActivity.this).load(rooffileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.roof).into(roof);

                                            }
                                        });
                                         rooffileId = getdata.imageId;
                                        if(windshieldfileUrl.equalsIgnoreCase(""))
                                            windshield();

                                    }/*if (!imageside.equalsIgnoreCase("OEMTag")&&!imageside.equalsIgnoreCase("TitleFront")&&!imageside.equalsIgnoreCase("TitleBack")&&!imageside.equalsIgnoreCase("MileageValue")
                                            &&!imageside.equalsIgnoreCase("BillOfSale")&&!imageside.equalsIgnoreCase("TitleConversionBack")&&!imageside.equalsIgnoreCase("TitleConversionFront")&&!imageside.equalsIgnoreCase("TPMS")&&!imageside.equalsIgnoreCase("TrackingConfirmation")){
                                       */
                                    else{

                                        String lastdegit=vin.substring(vin.length() - 8);
                                        getImages(vehicleId);
//                                        if(ExtraImageList.size()!=0) {
//                                            for (int i = 0; ExtraImageList.size() > i; i++) {
////                                                ExtraImageList.contains("imageName");
//                                                if (!ExtraImageList.contains(imageside + "-" + lastdegit)) {
//                                                    image = new HashMap<>();
//                                                    image.put("imageUrl", getdata.docUrl);
//                                                    image.put("imageName", imageside + "-" + lastdegit);
//                                                    image.put("imageId", getdata.imageId);
//
//                                                }
//
//                                            }
//                                            ExtraImageList.add(image);
//                                            adapterd = new ExtraImageList(ExtraImageList, AddImageActivity.this);
//                                            _image_list.setAdapter(adapterd);
//                                        }else{
//                                            image = new HashMap<>();
//                                            image.put("imageUrl", getdata.docUrl);
//                                            image.put("imageName", imageside + "-" + lastdegit);
//                                            image.put("imageId", getdata.imageId);
//                                            ExtraImageList.add(image);
//                                            adapterd = new ExtraImageList(ExtraImageList, AddImageActivity.this);
//                                            _image_list.setAdapter(adapterd);
//                                        }

                                    }
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
            }

            @Override
            public void onFailure(Call<SaveImageModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddImageActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    private String bitmapToBase64(Bitmap bitmap) {
               System.gc();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
    public static Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
    }
    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
    void firstOpenMenu(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_choose_view);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok = (Button) dialog.findViewById(R.id.buttonOk);
        Button cancel = (Button) dialog.findViewById(R.id.buttoncancel);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        Button buttoremove = (Button) dialog.findViewById(R.id.buttoremove);
        buttoremove.setVisibility(View.VISIBLE);
        buttoremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        title.setText("Upload images from");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(AddImageActivity.this,DamageActivity.class);
                intent.putExtra("VehicleId",vehicleId);
                intent.putExtra("IsGallery", "Yes");
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(AddImageActivity.this,DamageActivity.class);
                intent.putExtra("VehicleId",vehicleId);
                startActivity(intent);

            }
        });
        dialog.show();
    }
    void getImages(String tempVehicleId){
         prf = new PreferenceManager(this);
        GetImageModel vindetail=new GetImageModel(prf.getStringData("authKey"),tempVehicleId);
        Call<GetImageModel> call1 = apiInterface.getImage(getIntent().getStringExtra("VehicleId"),"",""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetImageModel>() {
            @Override
            public void onResponse(Call<GetImageModel> call, Response<GetImageModel> response) {
                GetImageModel getdata = response.body();
                try {
                    front_left_imagedelete.setVisibility(View.GONE);
                    front_right_imagedelete.setVisibility(View.GONE);
                    back_left_imagedelete.setVisibility(View.GONE);
                    back_right_imagedelete.setVisibility(View.GONE);
                    windshield_imagedelete.setVisibility(View.GONE);
                    roof_imagedelete.setVisibility(View.GONE);
                    fixName.put(R.drawable.car_d, "");
                    fixName.put(R.drawable.car_b, "");
                    fixName.put(R.drawable.car_c, "");
                    fixName.put(R.drawable.car_a, "");
                    fixName.put(R.drawable.car_e, "");
                    fixName.put(R.drawable.car_f, "");
                    if (getdata.status) {
                        ExtraImageList = new ArrayList<>();
                        ExtraImageList.clear();

                        if(getdata.dataValuer.size()==0){

                            firstOpenMenu();

                        }



                        for (int i = 0; getdata.dataValuer.size() > i; i++) {
                            System.gc();
                            if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("FrontLeftAngleView")) {
                                frontLeftfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");
                                front_left_imageview.setVisibility(View.VISIBLE);
                                front_left_imagedelete.setVisibility(View.VISIBLE);

                                Picasso.with(AddImageActivity.this).load(frontLeftfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_c).placeholder(R.drawable.progress_draw)  .into(front_left);
                                frontLeftfileId = getdata.dataValuer.get(i).docId;

                                fixName.put(R.drawable.car_a, "uploaded");


                            } else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("FrontRightAngleView")) {
                                frontRightfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");
                                frontRightfileId = getdata.dataValuer.get(i).docId;
                                front_right_imagedelete.setVisibility(View.VISIBLE);
                                front_right_imageview.setVisibility(View.VISIBLE);

                                fixName.put(R.drawable.car_c, "uploaded");
                                Picasso.with(AddImageActivity.this).load(frontRightfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_a).placeholder(R.drawable.progress_draw)  .into(front_right);
                            } else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("BackLeftAngleView")) {
                                backLeftfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");

                                back_left_imagedelete.setVisibility(View.VISIBLE);
                                Picasso.with(AddImageActivity.this).load(backLeftfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_f).placeholder(R.drawable.progress_draw)  .into(back_left);
                                back_left_imageview.setVisibility(View.VISIBLE);
                                backLeftfileId = getdata.dataValuer.get(i).docId;
                                fixName.put(R.drawable.car_f, "uploaded");
                            } else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("BackRightAngleView")) {
                                backRightfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");

                                back_right_imagedelete.setVisibility(View.VISIBLE);
                                Picasso.with(AddImageActivity.this).load(backRightfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_d).placeholder(R.drawable.progress_draw)  .into(back_right);
                                back_right_imageview.setVisibility(View.VISIBLE);
                                backRightfileId = getdata.dataValuer.get(i).docId;
                                fixName.put(R.drawable.car_d, "uploaded");


                            }
                            else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("Windshield")) {
                                windshieldfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");

                                windshield_imagedelete.setVisibility(View.VISIBLE);

                                fixName.put(R.drawable.car_b, "uploaded");
                                Picasso.with(AddImageActivity.this).load(windshieldfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_b).placeholder(R.drawable.progress_draw)  .into(windshield);
                                windshield_imageview.setVisibility(View.VISIBLE);
                                windshieldfileId = getdata.dataValuer.get(i).docId;

                            }
                            else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("Roof")) {
                                rooffileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");

                                roof_imagedelete.setVisibility(View.VISIBLE);

                                fixName.put(R.drawable.car_e, "uploaded");

                                Picasso.with(AddImageActivity.this).load(rooffileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_e).placeholder(R.drawable.progress_draw)  .into(roof);
                                roof_imageview.setVisibility(View.VISIBLE);
                                rooffileId = getdata.dataValuer.get(i).docId;

                            }else if (!getdata.dataValuer.get(i).doctype.equalsIgnoreCase("OEMTag") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TitleFront") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TitleBack") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("MileageValue")
                                    && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("BillOfSale") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TitleConversionBack") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TitleConversionFront") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TPMS")
                                    && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("IRSNumber")&& !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("RecallSheet1")&& !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("RecallSheet2")&& !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("ReleaseForm")) {

                                image = new HashMap<>();
                                image.put("imageUrl", getdata.dataValuer.get(i).DocURL);
                                image.put("imageName", getdata.dataValuer.get(i).FileName);
                                image.put("imageId", getdata.dataValuer.get(i).docId);
                                ExtraImageList.add(image);
                                adapterd = new ExtraImageList(ExtraImageList, AddImageActivity.this,true,AddImageActivity.this);
                                _image_list.setAdapter(adapterd);
                            }
                        }


//                    Picasso.with(this).load("https://images.unsplash.com/photo-503454537195-1dcabb73ffb9?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(front_left);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-450037586774-00cb81edd142?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(front_right);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-504196606672-aef5c9cefc92?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_left);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-500395235658-f87dff62cbf3?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_right);

                    }else{
//                        Intent intent=new Intent(AddImageActivity.this,DamageActivity.class);
//                        intent.putExtra("VehicleId",vehicleId);
//                        startActivity(intent);
                    }
//                    if (!prf.getStringData("OrderStatus").equalsIgnoreCase("Saved")) {

//                        front_left_imagedelete.setVisibility(View.GONE);
//                        front_right_imagedelete.setVisibility(View.GONE);
//                        back_left_imagedelete.setVisibility(View.GONE);
//                        back_right_imagedelete.setVisibility(View.GONE);
//                        windshield_imagedelete.setVisibility(View.GONE);
//                        roof_imagedelete.setVisibility(View.GONE);

                        front_left_imageedit.setVisibility(View.VISIBLE);
//                        front_left.setEnabled(false);
                        front_right_imageedit.setVisibility(View.VISIBLE);
//                        front_right.setEnabled(false);
                        back_left_imageedit.setVisibility(View.VISIBLE);
//                        back_left.setEnabled(false);
                        back_right_imageedit.setVisibility(View.VISIBLE);
//                        back_right.setEnabled(false);
                        windshield_imageedit.setVisibility(View.VISIBLE);
//                        windshield.setEnabled(false);
                        roof_imageedit.setVisibility(View.VISIBLE);
//                        roof.setEnabled(false);
                    findViewById(R.id.add_damage).setVisibility(View.VISIBLE);

//                        title.setText("View Images");
//                    }else {
//
//
//
//                    }

                }catch (Exception e){
                    e.printStackTrace();

                }finally {
                    hideAnimation();
                }
            }
            @Override
            public void onFailure(Call<GetImageModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddImageActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void getAFMImages(String tempVehicleId){
        prf = new PreferenceManager(this);
        GetImageModel vindetail=new GetImageModel(prf.getStringData("authKey"),tempVehicleId);
        Call<GetImageModel> call1 = apiInterface.getAFMImage(getIntent().getStringExtra("VehicleId"),"",""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<GetImageModel>() {
            @Override
            public void onResponse(Call<GetImageModel> call, Response<GetImageModel> response) {
                GetImageModel getdata = response.body();
                try {
                    if (getdata.status) {
                        ExtraImageList = new ArrayList<>();
                        ExtraImageList.clear();
                        for (int i = 0; getdata.dataValuer.size() > i; i++) {
                            System.gc();
                            if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("FrontLeftAngleView")) {
                                frontLeftfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");
                                front_left_imageview.setVisibility(View.VISIBLE);

                                Picasso.with(AddImageActivity.this).load(frontLeftfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_c).placeholder(R.drawable.progress_draw) .into(front_left);
                                frontLeftfileId = getdata.dataValuer.get(i).docId;



                            } else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("FrontRightAngleView")) {
                                frontRightfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");

                                frontRightfileId = getdata.dataValuer.get(i).docId;
                                front_right_imageview.setVisibility(View.VISIBLE);
                                Picasso.with(AddImageActivity.this).load(frontRightfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_a).placeholder(R.drawable.progress_draw) .into(front_right);

                            } else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("BackLeftAngleView")) {
                                backLeftfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");
                                back_left_imageview.setVisibility(View.VISIBLE);

                                Picasso.with(AddImageActivity.this).load(backLeftfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_f).placeholder(R.drawable.progress_draw)  .into(back_left);

                                backLeftfileId = getdata.dataValuer.get(i).docId;
                            } else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("BackRightAngleView")) {
                                backRightfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");

                                Picasso.with(AddImageActivity.this).load(backRightfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_d).placeholder(R.drawable.progress_draw)  .into(back_right);
                                back_right_imageview.setVisibility(View.VISIBLE);
                                backRightfileId = getdata.dataValuer.get(i).docId;

                            }
                            else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("Windshield")) {
                                windshieldfileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");

                                Picasso.with(AddImageActivity.this).load(windshieldfileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_b).placeholder(R.drawable.progress_draw)  .into(windshield);
                                windshield_imageview.setVisibility(View.VISIBLE);
                                windshieldfileId = getdata.dataValuer.get(i).docId;

                            }
                            else if (getdata.dataValuer.get(i).doctype.equalsIgnoreCase("Roof")) {
                                rooffileUrl = getdata.dataValuer.get(i).DocURL.replace("DelhiServer", "192.168.1.20");
                                Picasso.with(AddImageActivity.this).load(rooffileUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.car_e).placeholder(R.drawable.progress_draw)  .into(roof);
                                roof_imageview.setVisibility(View.VISIBLE);
                                rooffileId = getdata.dataValuer.get(i).docId;

                            }else if (!getdata.dataValuer.get(i).doctype.equalsIgnoreCase("OEMTag") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TitleFront") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TitleBack") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("MileageValue")
                                    && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("BillOfSale") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TitleConversionBack") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TitleConversionFront") && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("TPMS")
                                    && !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("IRSNumber")&& !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("RecallSheet1")&& !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("RecallSheet2")&& !getdata.dataValuer.get(i).doctype.equalsIgnoreCase("ReleaseForm")) {
                                image = new HashMap<>();
                                image.put("imageUrl", getdata.dataValuer.get(i).DocURL);
                                image.put("imageName", getdata.dataValuer.get(i).FileName);
                                image.put("imageId", getdata.dataValuer.get(i).docId);
                                ExtraImageList.add(image);
                                adapterd.notifyDataSetChanged();
                            }
                        }




//                    Picasso.with(this).load("https://images.unsplash.com/photo-503454537195-1dcabb73ffb9?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(front_left);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-450037586774-00cb81edd142?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(front_right);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-504196606672-aef5c9cefc92?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_left);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-500395235658-f87dff62cbf3?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_right);

                    }
                    front_left_imageedit.setVisibility(View.GONE);
                    front_right_imageedit.setVisibility(View.GONE);
                    back_left_imageedit.setVisibility(View.GONE);
                    back_right_imageedit.setVisibility(View.GONE);
                    windshield_imageedit.setVisibility(View.GONE);
                    roof_imageedit.setVisibility(View.GONE);

                    front_left_imagedelete.setVisibility(View.GONE);
                    front_right_imagedelete.setVisibility(View.GONE);
                    back_left_imagedelete.setVisibility(View.GONE);
                    back_right_imagedelete.setVisibility(View.GONE);
                    windshield_imagedelete.setVisibility(View.GONE);
                    roof_imagedelete.setVisibility(View.GONE);

                      findViewById(R.id.add_damage).setVisibility(View.GONE);
                    front_left.setClickable(false);
                    front_left.setEnabled(false);
                    front_right.setClickable(false);
                    front_right.setEnabled(false);
                    back_left.setClickable(false);
                    back_left.setEnabled(false);
                    back_right.setClickable(false);
                    back_right.setEnabled(false);
                    windshield.setClickable(false);
                    windshield.setEnabled(false);
                    roof.setClickable(false);
                    roof.setEnabled(false);

                    title.setText("View Images");
                }catch (Exception e){
                    e.printStackTrace();

                }finally {
                    hideAnimation();
                }
            }
            @Override
            public void onFailure(Call<GetImageModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddImageActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }
    void recyclerView(){
        int numberOfColumns = 2;
        _image_list.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapterd = new ExtraImageList(ExtraImageList, AddImageActivity.this,false,AddImageActivity.this);
        _image_list.setAdapter(adapterd);
//        _image_list.addItemDecoration(new DividerItemDecoration(this, 0));


    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    void popUp(String url, final String id, final String type){
        Log.e("url",url);
        Log.e("id",id);
        Log.e("type",type);
        final Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
                , null));
        settingsDialog.show();
        ImageView image=(ImageView)settingsDialog.findViewById(R.id.image_url);
        ImageView delete=(ImageView)settingsDialog.findViewById(R.id.delete_view);
        Button ok=(Button)settingsDialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.dismiss();
            }
        });
//        Picasso.with(this).load(url).error(R.drawable.ic_camera).into(image);
        Glide.with(this)
                .load(url).apply(options)
                .into(image);
//        delete.setVisibility(View.GONE);
        if (!prf.getStringData("OrderStatus").equalsIgnoreCase("Saved"))
            delete.setVisibility(View.GONE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dialog dialog = new Dialog(AddImageActivity.this);
                dialog.setContentView(R.layout.custome_alert_dialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // if button is clicked, close the custom dialog
                Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
                Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
                TextView title=(TextView)dialog.findViewById(R.id.title) ;
                TextView message=(TextView)dialog.findViewById(R.id.message) ;
                title.setText("");
                message.setText("Are you sure you want delete this? ");
                ok.setText("Yes");
                cancel.setText("No");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        deleteImg(windshieldfileId,"Windshield");
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        deleteImg(id,type);
                        settingsDialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }
    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.image_scrn);
        if (mainlayout != null && loaderScreen == null) {
            loaderScreen = new UploadScreen(this);
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
    void deleteImg(String id, final String type){
        Call<RemoveImageModel> call1 = apiInterface.deleteImage(id,""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<RemoveImageModel>() {
            @Override
            public void onResponse(Call<RemoveImageModel> call, Response<RemoveImageModel> response) {

                RemoveImageModel getdata = response.body();
                if(getdata.status!=null) {
                    if (getdata.status) {
//                    Intent i=new Intent(VehicleListActivity.this,AddVehicleActivity.class);
//                    i.putExtra("VehicleId",getdata.data.temOdId);
////                    i.putExtra("orderType",orderType);
//                    startActivity(i);
                        if(type.equalsIgnoreCase("BackRightAngleView")){
                            backRightfileUrl="";
                            back_right_imageview.setVisibility(View.GONE);
                            back_right_imagedelete.setVisibility(View.GONE);
                            backRightfileId="0";
                            Picasso.with(AddImageActivity.this).load(R.drawable.car_d).error(R.drawable.no_img_found).into(back_right);

                        }
                        else if(type.equalsIgnoreCase("BackLeftAngleView")){
                            backLeftfileUrl="";
                            back_left_imageview.setVisibility(View.GONE);
                            back_left_imagedelete.setVisibility(View.GONE);
                            backLeftfileId="0";
                            Picasso.with(AddImageActivity.this).load(R.drawable.car_f).error(R.drawable.no_img_found).into(back_left);

                        }else if(type.equalsIgnoreCase("FrontRightAngleView")){
                            frontRightfileUrl="";

                            front_right_imageview.setVisibility(View.GONE);
                            front_right_imagedelete.setVisibility(View.GONE);
                            frontRightfileId="0";
                            Picasso.with(AddImageActivity.this).load(R.drawable.car_a).error(R.drawable.no_img_found).into(front_right);

                        }else if(type.equalsIgnoreCase("FrontLeftAngleView")){
                             frontLeftfileUrl="";

                            front_left_imageview.setVisibility(View.GONE);
                            front_left_imagedelete.setVisibility(View.GONE);
                       frontLeftfileId="0";
                            Picasso.with(AddImageActivity.this).load(R.drawable.car_c).error(R.drawable.no_img_found).into(front_left);

                        }else if(type.equalsIgnoreCase("Windshield")){
                            windshieldfileUrl="";
                            windshieldfileId="0";
                            windshield_imageview.setVisibility(View.GONE);
                            windshield_imagedelete.setVisibility(View.GONE);
                            Picasso.with(AddImageActivity.this).load(R.drawable.car_b).error(R.drawable.no_img_found).into(windshield);

                        }
                        else if(type.equalsIgnoreCase("Roof")){
                            rooffileUrl="";
                            rooffileId="0";
                            roof_imageview.setVisibility(View.GONE);
                            roof_imagedelete.setVisibility(View.GONE);

                            Picasso.with(AddImageActivity.this).load(R.drawable.car_e).error(R.drawable.no_img_found).into(roof);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RemoveImageModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(AddImageActivity.this,t,"CxE001",null,""+call.request().url().toString());
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
        }
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, image_name);
//        values.put(MediaStore.Images.Media.DESCRIPTION, image_name);
//        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis ());
//        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
//        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
//        values.put("_data", file.getAbsolutePath());
//
//        ContentResolver cr = getContentResolver();
//        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
        geocoder = new Geocoder(AddImageActivity.this, Locale.US);
        if(location!=null) {
            Log.e("address", "");

            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                if(postalCode==null ||postalCode.equalsIgnoreCase("null"))
                    postalCode="";
                fullAddress = "" + city + "," + state + "," + postalCode;
                if(pd!=null) {
                    if (pd.isShowing())
                        pd.dismiss();
                }
//                prf.saveStringData("address", fullAddress);
             prf.saveStringData("address",fullAddress);
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
    void popUpList(String url) throws Exception {

            Intent intent = new Intent(AddImageActivity.this, ImageViewActivity.class);
            intent.putExtra("Url", "" + url);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivityForResult(intent, 5456);

    }
}
