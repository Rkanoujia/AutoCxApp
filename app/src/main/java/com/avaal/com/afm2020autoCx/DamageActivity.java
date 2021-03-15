package com.avaal.com.afm2020autoCx;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.cuscamera.CameraDemoActivity;
import com.avaal.com.afm2020autoCx.models.DamageImageModel;
import com.avaal.com.afm2020autoCx.models.ForAddModel;
import com.avaal.com.afm2020autoCx.models.PreImagesModel;
import com.avaal.com.afm2020autoCx.models.SaveImageModel;
import com.bumptech.glide.Glide;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.ViewType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DamageActivity extends ImageBaseActivity implements OnPhotoEditorListener,
        View.OnClickListener{

    private static final String TAG = DamageActivity.class.getSimpleName();
    public static final String EXTRA_IMAGE_PATHS = "extra_image_paths";
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    private PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;

    private ConstraintLayout mRootView;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    private boolean mIsFilterVisible;
    String imageFilePath;
//    LinearLayout delete_pic,retake,remove_all,show_hide;
    FloatingActionButton fabmenu;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    boolean isFABOpen=false;
    private StickerListener mStickerListener;
    boolean open=true;
    ImageAdatpter imagesAdapte;
    ArrayList<DamageImageModel> imagesList=new ArrayList<>();
    @BindView(R.id.rvEmoji)
    RecyclerView image;
    HashMap<Integer, String> imagesFix,orignalimagesFix;
    HashMap<Integer, String> fixName;
    HashMap<Integer, String> fixViewName;
    int IssaveImg=0;
    PreferenceManager prf;
    String itemId;
    int uploadImg=0;
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;
    private long mLastClickTime = 0;
    Options options;
    ArrayList<String> returnValue = new ArrayList<>();
    public void setStickerListener(StickerListener stickerListener) {
        mStickerListener = stickerListener;
    }

    public interface StickerListener {
        void onStickerClick(Bitmap bitmap);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.damage_activity);
        ButterKnife.bind(this);
        initViews();
        fixName = new HashMap<Integer, String>();
        fixName.put(R.drawable.car_c, "FrontRightAngleView");
        fixName.put(R.drawable.car_a, "FrontLeftAngleView");
        fixName.put(R.drawable.car_f, "BackLeftAngleView");
        fixName.put(R.drawable.car_d, "BackRightAngleView");
        fixName.put(R.drawable.car_e, "Roof");
        fixName.put(R.drawable.car_b, "Windshield");

      fixViewName = new HashMap<Integer, String>();
        fixViewName.put(R.drawable.car_c, "Front Right Angle");
        fixViewName.put(R.drawable.car_a, "Front Left Angle");
        fixViewName.put(R.drawable.car_f, "Rear Left Angle");
        fixViewName.put(R.drawable.car_d, "Rear Right Angle");
        fixViewName.put(R.drawable.car_e, "Rear Roof");
        fixViewName.put(R.drawable.car_b, "Windshield and Front Roof");

        imagesFix = new HashMap<Integer, String>();
        imagesFix.put(R.drawable.car_c, "");
        imagesFix.put(R.drawable.car_a, "");
        imagesFix.put(R.drawable.car_f, "");
        imagesFix.put(R.drawable.car_d, "");
        imagesFix.put(R.drawable.car_e, "");
        imagesFix.put(R.drawable.car_b, "");
        orignalimagesFix = new HashMap<Integer, String>();
        orignalimagesFix.put(R.drawable.car_c, "");
        orignalimagesFix.put(R.drawable.car_a, "");
        orignalimagesFix.put(R.drawable.car_f, "");
        orignalimagesFix.put(R.drawable.car_d, "");
        orignalimagesFix.put(R.drawable.car_e, "");
        orignalimagesFix.put(R.drawable.car_b, "");

        prf=new PreferenceManager(this);

//        mWonderFont = Typeface.createFromAsset(getAssets(), "beyond_wonderland.ttf");
//        mStickerBSFragment.setStickerListener(this);

//        delete_pic=findViewById(R.id.delete_pic);
//        retake=findViewById(R.id.retake);
//        remove_all=findViewById(R.id.remove);
//        show_hide=findViewById(R.id.view);

        fabmenu = findViewById(R.id.menu_list);
//        arrow_view=findViewById(R.id.arrow_view);

        RecyclerView rvEmoji = findViewById(R.id.images);

//        LinearLayoutManager llmFilters1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        rvEmoji.setLayoutManager(llmFilters1);
        int numberOfColumns = 2;
        rvEmoji.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        StickerAdapter stickerAdapter = new StickerAdapter();
        rvEmoji.setAdapter(stickerAdapter);
//        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
//        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
//        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
//        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        //Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

        mPhotoEditor.setOnPhotoEditorListener(this);

//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//        openCamera( );

        //Set Image Dynamically
        mPhotoEditorView.getSource().setImageResource(R.drawable.bg_car_image);

//        delete_pic.startAnimation(fab_close);
//        retake.startAnimation(fab_close);
//        remove_all.startAnimation(fab_close);
//        show_hide.startAnimation(fab_close);
//        fabmenu.startAnimation(fab_anticlock);
//        delete_pic.setClickable(false);
//        retake.setClickable(false);
//        isFABOpen=false;

//        findViewById(R.id.menu_list).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isFABOpen){
//                    delete_pic.startAnimation(fab_close);
//                    retake.startAnimation(fab_close);
//                    remove_all.startAnimation(fab_close);
//                    show_hide.startAnimation(fab_close);
//                    fabmenu.startAnimation(fab_anticlock);
//                    delete_pic.setClickable(false);
//                    retake.setClickable(false);
//                    isFABOpen=false;
//                }else{
//                    delete_pic.startAnimation(fab_open);
//                    retake.startAnimation(fab_open);
//                    remove_all.startAnimation(fab_open);
//                    show_hide.startAnimation(fab_open);
//                    fabmenu.startAnimation(fab_clock);
//                    delete_pic.setClickable(true);
//                    retake.setClickable(true);
//                    isFABOpen = true;
//                }
//            }
//        });
//        findViewById(R.id.delete_pic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPhotoEditorView.getSource().setImageResource(R.drawable.bg_car_image);
//            }
//        });



        imagesAdapte =new ImageAdatpter(imagesList);
        image.setAdapter(imagesAdapte);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        image.setLayoutManager(layoutManager);




//        VinNumber= getIntent().getStringExtra("Vin");

        itemId=getIntent().getStringExtra("VehicleId");
        if(getIntent().getStringExtra("IsEdit")!=null){
            imagesFix = (HashMap<Integer, String>)getIntent().getSerializableExtra("images");
        }


        options = Options.init()
                .setRequestCode(100)
                .setCount(20)
                .setFrontfacing(false)
                .setMode(Options.Mode.Picture)
                .setPreSelectedUrls(returnValue)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/AFM");
        if(getIntent().getStringExtra("IsGallery")!=null){
            imagesFix.put(R.drawable.car_c, "IsGallery");
            imagesFix.put(R.drawable.car_a, "IsGallery");
            imagesFix.put(R.drawable.car_f, "IsGallery");
            imagesFix.put(R.drawable.car_d, "IsGallery");
            imagesFix.put(R.drawable.car_e, "IsGallery");
            imagesFix.put(R.drawable.car_b, "IsGallery");
//            returnValue.clear();
            Pix.start(DamageActivity.this, options);
        }else
      {
          Intent intent=new Intent(DamageActivity.this, CameraDemoActivity.class);
          intent.putExtra("images",imagesFix);
          startActivityForResult(intent,1222);
      }

    }


    @OnClick(R.id.cancel)
    void cancel(){
       onBackPressed();

    }
    @OnClick(R.id.delete_pic)
            void remdelete_picove() {
        if (imagesList.size() == 0) {
            return;
        }

        mPhotoEditor.clearAllViews();
        if (imagesList.size() == 0) {
            MDToast mdToast = MDToast.makeText(this, "No Image for Delete All Marks", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
        } else {
            if(imagesList.get(IssaveImg).getName().contains("extra_")) {
                orignalimagesFix.remove(imagesList.get(IssaveImg).getImageid());
                imagesFix.remove(imagesList.get(IssaveImg).getImageid());
            }else {
                orignalimagesFix.put(imagesList.get(IssaveImg).getImageid(), "");
                imagesFix.put(imagesList.get(IssaveImg).getImageid(), "");
            }
            mPhotoEditor.clearAllViews();
            imagesList.remove(IssaveImg);
            imagesAdapte.notifyDataSetChanged();
            if (imagesList.size() > 0) {
                IssaveImg = imagesList.size()-1;
                mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagesList.get(IssaveImg).getUrl())));
            } else
                mPhotoEditorView.getSource().setImageResource(R.drawable.bg_car_image);


        }

    }
    @OnClick(R.id.retake)
    void retake(){
        if (imagesList.size() == 0) {
            MDToast mdToast = MDToast.makeText(this, "No Images for Retake", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
        } else {
            mPhotoEditor.clearAllViews();
            if(imagesList.get(IssaveImg).getName().contains("extra_")){
                orignalimagesFix.remove(imagesList.get(IssaveImg).getImageid());
                imagesFix.remove(imagesList.get(IssaveImg).getImageid());
//                imagesList.remove(IssaveImg);
//                imagesAdapte.notifyDataSetChanged();
                openCamera();
            }else{
                orignalimagesFix.put(imagesList.get(IssaveImg).getImageid(), "");
                imagesFix.put(imagesList.get(IssaveImg).getImageid(), "");
//                imagesList.remove(IssaveImg);
//                imagesAdapte.notifyDataSetChanged();
                openCamera();
            }


        }

    }

    @OnClick(R.id.remove)
    void remove(){
        if(imagesList.size()==0){
            MDToast mdToast = MDToast.makeText(this, "No Image for Delete", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
//            new Util().toastView(DamageActivity.this,"No Images for delete","");
        }else {
            mPhotoEditor.clearAllViews();
//        if(imagesFix.get(imagesList.get(IssaveImg).getImageid())==null)
            imagesList.get(IssaveImg).setUrl(imagesFix.get(imagesList.get(IssaveImg).getImageid()));
            imagesAdapte.notifyDataSetChanged();
            mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagesFix.get(imagesList.get(IssaveImg).getImageid()))));
        }

    }
    @OnClick(R.id.delete_)
    void  deleteAll(){
        if(imagesList.size()==0){
            MDToast mdToast = MDToast.makeText(this, "No Image found for Delete", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
//            new Util().toastView(DamageActivity.this,"No Images found for delete","");
            return;
        }
        final Dialog dialog = new Dialog(DamageActivity.this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("Delete All Images");
        message.setText("Are You Sure ?");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               imagesList.clear();
                mPhotoEditor.clearAllViews();
               imagesAdapte.notifyDataSetChanged();
                imagesFix = new HashMap<Integer, String>();
                imagesFix.put(R.drawable.car_d, "");
                imagesFix.put(R.drawable.car_b, "");
                imagesFix.put(R.drawable.car_c, "");
                imagesFix.put(R.drawable.car_a, "");
                imagesFix.put(R.drawable.car_e, "");
                imagesFix.put(R.drawable.car_f, "");
                orignalimagesFix = new HashMap<Integer, String>();
                orignalimagesFix.put(R.drawable.car_d, "");
                orignalimagesFix.put(R.drawable.car_b, "");
                orignalimagesFix.put(R.drawable.car_c, "");
                orignalimagesFix.put(R.drawable.car_a, "");
                orignalimagesFix.put(R.drawable.car_e, "");
                orignalimagesFix.put(R.drawable.car_f, "");
                mPhotoEditorView.getSource().setImageResource(R.drawable.bg_car_image);

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
    @OnClick(R.id.upload)
    void  upload(){

        if(!new Util().isNetworkAvailable(this)){
            MDToast mdToast = MDToast.makeText(this, "NO InternetConnection", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }

        if(imagesList.size()==0){
            MDToast mdToast = MDToast.makeText(this, "No Image found for Upload", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        saveImage(IssaveImg,"");
        final Dialog dialog = new Dialog(DamageActivity.this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("Upload");
        message.setText("Are You Sure?");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showAnimation();
                sendpreinspection(imagesList.get(uploadImg));

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

    private void initViews() {

        ImageView imgCamera;
        TextView imgSave;
        LinearLayout imgClose;

        mPhotoEditorView = findViewById(R.id.photoEditorView);
        mRootView = findViewById(R.id.rootView);




        imgCamera = findViewById(R.id.imgCamera);
        imgCamera.setOnClickListener(this);


        imgSave = findViewById(R.id.imgSave);
        imgSave.setOnClickListener(this);

        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);

    }


    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {

    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onRemoveViewListener(int numberOfAddedViews) {
        Log.d(TAG, "onRemoveViewListener() called with: numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgSave:
//                saveImage();
                break;

            case R.id.imgClose:
                onBackPressed();
                break;

            case R.id.imgCamera:
                if(imagesList.size()>0) {
                    showAnimation();
                    saveImage(IssaveImg,"camera");
                }else
                    openCamera();
                break;

        }
    }

    private void saveImage(int position,String camera){
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            saveImage1(position,camera);

        }
    }
    @SuppressLint("MissingPermission")
    private void saveImage1(int position,String camera) {

//            showLoading("Saving...");
//        File file = new File(Environment.getExternalStorageDirectory()
//                + File.separator + ""
//                + System.currentTimeMillis() + ".png");

        String path = getApplicationContext().getDir("AFM", Context.MODE_PRIVATE)+ File.separator;
//                    f.delete();
        OutputStream outFile = null;
        final String name=String.valueOf(System.currentTimeMillis()) + ".png";
        File file = new File(path, name);

            try {
                file.createNewFile();

                SaveSettings saveSettings = new SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build();

                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                       hideAnimation();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imagesList.get(IssaveImg).setUrl(imagePath);
                                imagesFix.put(imagesList.get(IssaveImg).getImageid(),imagesList.get(IssaveImg).getUrl());
                                mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagesList.get(position).getUrl())));
                                IssaveImg=position;
                                imagesAdapte.notifyDataSetChanged();
                                hideAnimation();
                            }
                        });
                        if(camera.equalsIgnoreCase("camera")){
                            openCamera();
                        }
//                        showSnackbar("Image Saved Successfully");



                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
//                        hideLoading();
                        hideAnimation();
//                        showSnackbar("Failed to save Image");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                hideAnimation();
//                hideLoading();
//                showSnackbar(e.getMessage());
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    mPhotoEditor.clearAllViews();
                    Uri photoURI = FileProvider.getUriForFile(this, "com.avaal.com.afm2020autoCx.provider", new File(imageFilePath));
                    try {
                        Log.d(TAG, "URI value = " + photoURI.getPath());
                        ExifInterface exif = new ExifInterface(imageFilePath);
                        Log.d(TAG, "Exif value = " + exif);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                        bitmap = getScaledBitmap(bitmap, 400, 800);
                        Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);
                        mPhotoEditorView.getSource().setImageBitmap(rotatedBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//                    Bitmap photo = null;
//                    try {
//                        Uri photoURI = FileProvider.getUriForFile(this, "com.avaal.com.afmdriver.provider", new File(imageFilePath));
//                        photo = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                    mPhotoEditorView.getSource().setImageBitmap(photo);
                    break;
                case PICK_REQUEST:
                    try {
                        mPhotoEditor.clearAllViews();
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1222:
                    imagesList.clear();
                    int k=0;
                    imagesFix = (HashMap<Integer, String>) data.getSerializableExtra("images");
                    orignalimagesFix.putAll(imagesFix);
                    Map<Integer, String> getImagemap = new TreeMap<Integer, String>(imagesFix);
                    for (Map.Entry<Integer, String> entry : getImagemap.entrySet()) {
                        if(orignalimagesFix.get(entry.getKey())!=null) {
                            if (orignalimagesFix.get(entry.getKey()).equalsIgnoreCase(""))
                                orignalimagesFix.put(entry.getKey(),entry.getValue());
                        }
                               if(getIntent().getStringExtra("IsEdit")!=null){
                                   if (!entry.getValue().equalsIgnoreCase("IsGallery") && !entry.getValue().equalsIgnoreCase("uploaded") && !entry.getValue().equalsIgnoreCase("")) {
                                       if(fixName.get(entry.getKey())!=null) {
                                           DamageImageModel damageImageModel = new DamageImageModel(fixName.get(entry.getKey()), entry.getValue(), entry.getKey(),fixName.get(entry.getKey()),fixViewName.get(entry.getKey()));
                                           imagesList.add(damageImageModel);
                                       }else{
//                                if(getIntent().getStringExtra("Isupdate")!=null){
//                                    if(imageName.equalsIgnoreCase("")){
//                                        ++k;
//                                        DamageImageModel damageImageModel = new DamageImageModel("extra_" + date.getTime()+""+k, entry.getValue(), entry.getKey(),"InspectionExtraImages","extra_"+new Date().getTime()+""+k);
//                                        imagesList.add(damageImageModel);
//                                    }else {
//                                        DamageImageModel damageImageModel = new DamageImageModel(imageName, entry.getValue(), entry.getKey(), imageName, fixViewName.get(entry.getKey()));
//                                        imagesList.add(damageImageModel);
//                                    }
//                                }else {
                                           ++k;
                                           DamageImageModel damageImageModel = new DamageImageModel("extra_" + new Date().getTime()+""+k, entry.getValue(), entry.getKey(),"InspectionExtraImages","extra_"+new Date().getTime()+""+k);
                                           imagesList.add(damageImageModel);
//                                }

                                       }


                                   }
                               }else {

                                   if(getIntent().getStringExtra("IsGallery")!=null){
                                       if (!entry.getValue().equals("uploaded") &&!entry.getValue().equals("") && !entry.getValue().equals("IsGallery")) {
                                            if (fixName.get(entry.getKey()) != null) {
                                               DamageImageModel damageImageModel = new DamageImageModel(fixName.get(entry.getKey()), entry.getValue(), entry.getKey(), fixName.get(entry.getKey()), fixViewName.get(entry.getKey()));
                                               imagesList.add(damageImageModel);
                                           } else {
                                               ++k;
                                               DamageImageModel damageImageModel = new DamageImageModel("extra_" + new Date().getTime() + "" + k, entry.getValue(), entry.getKey(), "InspectionExtraImages", "extra_" + new Date().getTime() + "" + k);
                                               imagesList.add(damageImageModel);

                                           }

                                       }
                                   }
                                   else{
                                       if (!entry.getValue().equals("")) {
                                           if (fixName.get(entry.getKey()) != null) {
                                               DamageImageModel damageImageModel = new DamageImageModel(fixName.get(entry.getKey()), entry.getValue(), entry.getKey(), fixName.get(entry.getKey()), fixViewName.get(entry.getKey()));
                                               imagesList.add(damageImageModel);
                                           } else {
                                               ++k;
                                               DamageImageModel damageImageModel = new DamageImageModel("extra_" + new Date().getTime() + "" + k, entry.getValue(), entry.getKey(), "InspectionExtraImages", "extra_" + new Date().getTime() + "" + k);
                                               imagesList.add(damageImageModel);

                                           }

                                       }
                                   }


                               }
                    }
                   new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            if(imagesList.size()>0) {
                                IssaveImg=imagesList.size()-1;
                                mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagesList.get(IssaveImg).getUrl())));
                            }
                            Collections.sort(imagesList, new Comparator<DamageImageModel>() {
                                @Override
                                public int compare(DamageImageModel lhs, DamageImageModel rhs) {
                                    return lhs.getImageid()- rhs.getImageid();
                                }
                            });
                            imagesAdapte.notifyDataSetChanged();
                            image.scrollToPosition(IssaveImg);
                        }
                    }, 100);

                    break;
                case 100:
                    imagesList.clear();
                    returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                    Random rand = new Random();
                    Date date = new Date();
                    for (int i = 0; returnValue.size() > i; i++) {
                        int t = rand.nextInt(1000);
                        imagesFix.put(t, new File(returnValue.get(i)).getAbsolutePath());
                        orignalimagesFix.put(t, new File(returnValue.get(i)).getAbsolutePath());
                        DamageImageModel damageImageModel = new DamageImageModel("extra_" + date.getTime() + "" + i, new File(returnValue.get(i)).getAbsolutePath(), t, "InspectionExtraImages", "extra_" + new Date().getTime() + "" + i);
                        imagesList.add(damageImageModel);
                    }

//                    Log.e("new select ", "" + returnValue.size());
//            TemImagesAdapte.addImage(returnValue);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            if (imagesList.size() > 0) {
                                IssaveImg = imagesList.size() - 1;
                                mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagesList.get(IssaveImg).getUrl())));
                            }
                            Collections.sort(imagesList, new Comparator<DamageImageModel>() {
                                @Override
                                public int compare(DamageImageModel lhs, DamageImageModel rhs) {
                                    return lhs.getImageid() - rhs.getImageid();
                                }
                            });
                            imagesAdapte.notifyDataSetChanged();
                            image.scrollToPosition(IssaveImg);
                        }
                    }, 300);
                    break;
            }
        }
    }


//    @Override
//    public void onStickerClick(Bitmap bitmap) {
//        mPhotoEditor.addImage(bitmap);
//        mTxtCurrentTool.setText(R.string.label_sticker);
//    }

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
    @Override
    public void isPermissionGranted(boolean isGranted, String permission) {
        if (isGranted) {
            saveImage(IssaveImg,"");
        }
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want to exit without saving image ?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveImage(IssaveImg,"");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();

    }



    void showFilter(boolean isVisible) {
        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mRootView);

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);

        mConstraintSet.applyTo(mRootView);
    }

    @Override
    public void onBackPressed() {
//        if (mIsFilterVisible) {
//            showFilter(false);
//        } else if (!mPhotoEditor.isCacheEmpty()) {
           backbutton();
//        } else {
//            super.onBackPressed();
//        }
    }

    void backbutton(){
        if(imagesList.size()==0){
            finish();
            return;
        }else
        saveImage(IssaveImg,"");
        final Dialog dialog = new Dialog(DamageActivity.this);
        dialog.setContentView(R.layout.custome_alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("Inspection Images");
        message.setText("Do you want to save the pictures ?");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(imagesList.size()>0){
                    showAnimation();
                    sendpreinspection(imagesList.get(uploadImg));
                }else
                    finish();


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }
    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        int[] stickerList = new int[]{R.drawable.ms,R.drawable.s,R.drawable.b,R.drawable.ch,
                R.drawable.cr,R.drawable.d_1,R.drawable.f,R.drawable.ff,R.drawable.ft,
                R.drawable.g,R.drawable.hd,R.drawable.lc,R.drawable.m,R.drawable.md,
                R.drawable.o,R.drawable.pc,R.drawable.r,R.drawable.ru,R.drawable.sc};
//        int[] stickerListView = new int[]{R.drawable.ic_broken, R.drawable.ic_chipped,R.drawable.ic_cracked,R.drawable.ic_scratched};
        String[] stickerTextListView = new String[]{"MS","S","B","CH","CR","D","F","FF","FT","G","HD","LC","M","MD","O","PC","R","RU","SC"};
        String[] stickerNameList = new String[]{"Multiple Scratches", "Scratches","Broken","Chipped","Cracked","Dent","Faded","Foreign Fluid","Flat Tire","Gouge","Hail Damage","Loose Contents","Missing","Major Damage","Other","Paint Chip","Rubbed","Rust","Scuffed"};

        @Override
        public StickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sticker, parent, false);
            return new StickerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StickerAdapter.ViewHolder holder, int position) {
//            holder.imgSticker.setImageResource(stickerListView[position]);
            holder.imgName.setText(stickerNameList[position]);
            holder.txtimg.setText(stickerTextListView[position]);
        }

        @Override
        public int getItemCount() {
            return stickerList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgSticker;
            TextView imgName,txtimg;

            ViewHolder(View itemView) {
                super(itemView);
                imgSticker = itemView.findViewById(R.id.imgSticker);
                imgName=itemView.findViewById(R.id.imgName);
                txtimg=itemView.findViewById(R.id.txtimg);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mPhotoEditor.addImage(BitmapFactory.decodeResource(getResources(),
                                stickerList[getLayoutPosition()]));


                    }

                });
            }
        }
    }
    void openCamera( ) {
        Intent intent=new Intent(DamageActivity.this, CameraDemoActivity.class);
        intent.putExtra("images",imagesFix);
        startActivityForResult(intent,1222);

//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.custom_choose_view);
//        dialog.setCancelable(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        // if button is clicked, close the custom dialog
//        Button ok = (Button) dialog.findViewById(R.id.buttonOk);
//        Button cancel = (Button) dialog.findViewById(R.id.buttoncancel);
//        TextView title = (TextView) dialog.findViewById(R.id.title);
//
//        title.setText("Select Option");
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                returnValue.clear();
//                Pix.start(DamageActivity.this, options);
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                Intent intent=new Intent(DamageActivity.this, CameraDemoActivity.class);
//                intent.putExtra("images",imagesFix);
//                startActivityForResult(intent,1222);
//
//            }
//        });
//        dialog.show();
//

//        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
//        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
//            marshMallowPermission.requestPermissionForExternalStorage();
//            return;
//        }
//        if (!marshMallowPermission.checkPermissionForCamera()) {
//            marshMallowPermission.requestPermissionForCamera();
//        } else {
//            Intent pictureIntent = new Intent(
//                    MediaStore.ACTION_IMAGE_CAPTURE);
//            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
//                //Create a file to store the image
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) {
//                    // Error occurred while creating the File
//                }
//                if (photoFile != null) {
//                    Uri photoURI = FileProvider.getUriForFile(this, "com.avaal.com.afmdriver.provider", photoFile);
//                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                            photoURI);
//                    startActivityForResult(pictureIntent,
//                            CAMERA_REQUEST);
//                }
//            }
//
//
//        }
    }

    private File createImageFile() throws IOException {
//        String timeStamp =
//                new SimpleDateFormat("yyyyMMdd_HHmmss",
//                        Locale.US).format(new Date());
        String imageFileName = "temp";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);

//        File storageDir =getApplicationContext().getDir("AutoEnroute", Context.MODE_PRIVATE);;
//        File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");

//        File direct = new File(Environment.getExternalStorageDirectory() + "/AutoEnroute");
//
//        if (!direct.exists()) {
//            direct.mkdirs();
//        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    void sendpreinspection(DamageImageModel imageModel) {

//        System.gc();
        final File f = new File(imageModel.getUrl());
        Log.e("file Url",""+f);
        String name=imageModel.getName();
        Log.e("file Name",name);
        String filepath=f.getAbsolutePath();
//          File f1=f.getAbsoluteFile();
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
//                Locale.getDefault()).format(new Date());
//        String fileName=docType;
        String filetemdata = "";
//        String lastdegit=str.substring(str.length() - 8);
//        AfmDbHandler dbHandler = new AfmDbHandler(this);

//        long length = f.length() / 1024;//size in KB
//        if(length<150){
//            MDToast mdToast = MDToast.makeText(this, "Image Size should be less then 150KB", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
//            mdToast.show();
//            return;
//        }

        long size = 0;
        try {
            Bitmap thumbnail;
//            Bitmap thumbnail = BitmapFactory.decodeFile(f.getAbsolutePath());
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//            bitmapOptions.inSampleSize = 2;
            thumbnail = BitmapFactory.decodeFile(filepath, bitmapOptions);



            int actualHeight = bitmapOptions.outHeight;
            int actualWidth = bitmapOptions.outWidth;

//      max Height and width values of the compressed image is taken as 816x612
            float maxHeight = 1024.0f;
            float maxWidth = 886.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }

//      setting inSampleSize value allows to load a scaled down version of the original image
            bitmapOptions.inSampleSize = calculateInSampleSize(bitmapOptions, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
            bitmapOptions.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
            bitmapOptions.inPurgeable = true;
            bitmapOptions.inInputShareable = true;
            bitmapOptions.inTempStorage = new byte[16 * 1024];

            try {
//          load the bitmap from its path
                thumbnail = BitmapFactory.decodeFile(filepath, bitmapOptions);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            ExifInterface exif = new ExifInterface(filepath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            thumbnail = rotateBitmap(thumbnail, orientation);

            thumbnail = getScaledBitmap(thumbnail, 800, 600);
            if(!prf.getBoolData("ImageWaterMarks")){
                Bitmap second = addText(thumbnail);
                thumbnail.recycle();
                filetemdata = bitmapToBase64(second);
                second.recycle();
            }else
            {
                filetemdata = bitmapToBase64(thumbnail);
                thumbnail.recycle();
            }
//            saveImage(second, fileName,type);
            size = filetemdata.length();
            size = size / 1024;

//            thumbnail.recycle();
//            System.gc();
//            Runtime.getRuntime().gc();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }

        String exten = null;
//        if (f.getName().contains(".png"))
//            exten = ".png";
//        else
            exten = ".jpg";
        String code=imageModel.getCode();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        SaveImageModel vindetail=new SaveImageModel(name,"image/jpeg",""+size,".jpeg",filetemdata,"",code,itemId,"0",""+ prf.getStringData("corporateId"),""+ prf.getStringData("userCode"),""+ prf.getStringData("userName"));
//        Call<SaveImageModel> call1 = apiInterface.updateProfile(vehicleIds, fileNames,authKey1,content,docTypess,exten,size1,remarks, body);
        Call<SaveImageModel> call1 = apiInterface.saveImages(vindetail,"bearer "+ prf.getStringData("accessToken"),"application/json");

        call1.enqueue(new Callback<SaveImageModel>() {
            @Override
            public void onResponse(Call<SaveImageModel> call, Response<SaveImageModel> response) {


                Log.e("message ", response.message());


                try {
                    if (response.message().equalsIgnoreCase("ok")) {
//                    if(f.exists())
//                        f.delete();





//
//                        if (titleName.equalsIgnoreCase("BackRightAngleView")) {
//                            backRightfileUrl=fleetlist.Value;
//                            if(rooffileUrl.equalsIgnoreCase(""))
//                                openCamera("Roof");
//
//                        } else if (titleName.equalsIgnoreCase("BackLeftAngleView")) {
//                            backLeftfileUrl=fleetlist.Value;
//                            if(backRightfileUrl.equalsIgnoreCase(""))
//                                openCamera("BackRightAngleView");
//
//                        } else if (titleName.equalsIgnoreCase("FrontRightAngleView")) {
//                                frontRightfileUrl=fleetlist.Value;
//                            if(frontLeftfileUrl.equalsIgnoreCase(""))
//                                openCamera("FrontLeftAngleView");
//
//                        } else if (titleName.equalsIgnoreCase("FrontLeftAngleView")) {
//
//                                  frontLeftfileUrl=fleetlist.Value;
//                            if(backLeftfileUrl.equalsIgnoreCase(""))
//                                openCamera("BackLeftAngleView");
//
//                        } else if (titleName.equalsIgnoreCase("Windshield")) {
//                                 windshieldfileUrl=fleetlist.Value;
//                        }else if (titleName.equalsIgnoreCase("Roof")) {
//                                  rooffileUrl=fleetlist.Value;
//                            if(windshieldfileUrl.equalsIgnoreCase(""))
//                                openCamera("Windshield");
//                        }else
//                            getImageList();

                    } else {

                    }

                }catch (Exception e){
                    new Util().sendSMTPMail(DamageActivity.this,null,"DrvE004",e,""+call.request().url().toString());
                    e.printStackTrace();
                }finally {
                    if(imagesList.size()>uploadImg+1) {
                        ++uploadImg;
                        sendpreinspection(imagesList.get(uploadImg));
                    }else {
                        MDToast mdToast = MDToast.makeText(DamageActivity.this, "Images Upload Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
//                        new Util().toastView(DamageActivity.this,"Images Upload Successfully","");
                        hideAnimation();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveImageModel> call, Throwable t) {

                new Util().sendSMTPMail(DamageActivity.this,t,"DrvE001",null,""+call.request().url().toString());










                call.cancel();

            }
        });


    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private Bitmap addText(Bitmap toEdit)throws Exception{
        Calendar calendar = Calendar.getInstance();
        Bitmap newBitmap = null;
        Bitmap dest = toEdit.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(dest);
        newBitmap = Bitmap.createBitmap(dest.getWidth(), 80, Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas(newBitmap);
//        newCanvas.drawColor(ContextCompat.getColor(this, R.color.colorAccent));
        newCanvas.drawARGB(100,169,169,169);

//        canvas.drawColor(ContextCompat.getColor(this, R.color.darkgray));

        Paint paint = new Paint();  //set the look
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        paint.setStyle(Paint.Style.FILL);
//        paint.setShadowLayer(2.0f, 1.0f, 1.0f, Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setColor(ContextCompat.getColor(this, R.color.darkgray));
        paint.setTextSize(20f);
        Locale loc = new Locale("en", "US");
        String timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",
                loc).format(calendar.getTime());
//        canvas.drawBitmap(originalImg, 100, 50, paint);
//        if(fullAddress!=null)
//        canvas.drawText(fullAddress , 10,  30, paint);
//        canvas.drawText(timeStamp , 10,  50, paint);

//        if(fullAddress!=null)
            newCanvas.drawText(prf.getStringData("address") , 10,  25, paint);
        newCanvas.drawText(timeStamp , 10,  50, paint);
        Matrix matrix = new Matrix();
        matrix.postScale(0, 0);
        newCanvas.drawBitmap(newBitmap, new Matrix(), paint);

        canvas.drawBitmap(dest, matrix, paint);
        canvas.drawBitmap(newBitmap,0,toEdit.getHeight()-70, paint);
        newBitmap.recycle();



        return dest;
    }

    public class ImageAdatpter extends RecyclerView.Adapter<ImageAdatpter.MyViewHolder> {

        ArrayList<DamageImageModel> triplist;
        boolean istselect = true;
        private int count;

        public ImageAdatpter(ArrayList<DamageImageModel> triplist) {
            this.triplist = triplist;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Glide.with(DamageActivity.this).load(new File("" + triplist.get(position).getUrl())).into(holder.image_view);
            holder.name.setText(triplist.get(position).getViewName());
            if (position == IssaveImg) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.border.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.border.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
                }
            }

            holder.image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();


                    showAnimation();
                    saveImage(position, "");


                }
            });
//
        }

        @Override
        public ImageAdatpter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_item, parent, false);

            return new ImageAdatpter.MyViewHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return triplist.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            FrameLayout border;
            ImageView image_view;
            TextView name;
            View referNo;

            public MyViewHolder(View view) {
                super(view);

                image_view = (ImageView) view.findViewById(R.id.image_view);
                name = (TextView) view.findViewById(R.id.name);
                border = (FrameLayout) view.findViewById(R.id.border);

            }
        }

    }

    private String bitmapToBase64(Bitmap bitmap) {
//        System.gc();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.damage_main);
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
}
