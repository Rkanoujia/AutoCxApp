package com.avaal.com.afm2020autoCx.barcode;

/**
 * Created by dell pc on 05-01-2018.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.ui.camera.CameraSource;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;


import java.io.IOException;



/**
 * Activity for the multi-tracker app.  This app detects barcodes and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and ID of each barcode.
 */
public final class BarcodeCaptureActivity extends AppCompatActivity {
    private static final String TAG = "Barcode-reader";
    // intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;

    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // constants used to pass extra data in the intent
    boolean autoFocus=true;
    public static final String UseFlash = "UseFlash";
    public static final String BarcodeObject = "Barcode";

    // helper objects for detecting taps and pinches.
//    private ScaleGestureDetector scaleGestureDetector;
    String ItemId,type,position;
    TextView qr_txt,bar_txt;
    View n_view;
    LinearLayout img_;
    private BarcodeDetector barcodeDetector;
    boolean useFlash=false;
    private CameraSource cameraSource;
    View focus;
    SurfaceView surfaceView;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.barcode_capture);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        // read parameters from the intent used to launch the activity.
        useFlash = getIntent().getBooleanExtra(UseFlash, false);
        qr_txt=(TextView)findViewById(R.id.qr_txt);
        bar_txt=(TextView)findViewById(R.id.bar_txt);
        img_=findViewById(R.id.img_);
        n_view=findViewById(R.id.n_view);
        focus=(View)findViewById(R.id.focus);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        final TranslateAnimation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, .3f);
        mAnimation.setDuration(800);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setFillAfter(true);
        mAnimation.setInterpolator(new LinearInterpolator());
        img_.setAnimation(mAnimation);
        bar_txt.setTextColor(Color.WHITE);
        bar_txt.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        qr_txt.setTextColor(getResources().getColor(R.color.colorPrimary));
        qr_txt.setBackgroundColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            n_view.setBackground(getDrawable(R.drawable.bar_code_scan));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.img_bar).setVisibility(View.INVISIBLE);
                img_.setVisibility(View.GONE);
            }
        });

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(useFlash){
                    if(cameraSource!=null) {
                        cameraSource.release();
                        cameraSource=null;
                    }
                    useFlash=false;
                    focus.setBackground(getResources().getDrawable(R.drawable.ic_flash_off));
                    int rc = ActivityCompat.checkSelfPermission(BarcodeCaptureActivity.this, Manifest.permission.CAMERA);
                    if (rc == PackageManager.PERMISSION_GRANTED) {
                        initialiseDetectorsAndSources();
                    }
                }else{
                    if(cameraSource!=null) {
                        cameraSource.release();
                        cameraSource=null;
                    }
                    useFlash=true;
                    focus.setBackground(getResources().getDrawable(R.drawable.ic_flash_on));
                    int rc = ActivityCompat.checkSelfPermission(BarcodeCaptureActivity.this, Manifest.permission.CAMERA);
                    if (rc == PackageManager.PERMISSION_GRANTED) {
                       initialiseDetectorsAndSources();
                    }
                }
            }
        });

        qr_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                   startActivity(new Intent(BarcodeCaptureActivity.this,QRCodeActivity.class));
                qr_txt.setTextColor(Color.WHITE);
                qr_txt.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                bar_txt.setTextColor(getResources().getColor(R.color.colorPrimary));
                bar_txt.setBackgroundColor(Color.WHITE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    n_view.setBackground(getDrawable(R.drawable.qr_code_scan));
                }
                img_.setVisibility(View.VISIBLE);
                findViewById(R.id.img_bar).setVisibility(View.VISIBLE);
            }
        });
        bar_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar_txt.setTextColor(Color.WHITE);
                bar_txt.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                qr_txt.setTextColor(getResources().getColor(R.color.colorPrimary));
                qr_txt.setBackgroundColor(Color.WHITE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    n_view.setBackground(getDrawable(R.drawable.bar_code_scan));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.img_bar).setVisibility(View.INVISIBLE);
                        img_.setVisibility(View.GONE);
                    }
                });

            }
        });

//        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length>0){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                finish();
            else
                openCamera();
        }else
            finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(cameraSource!=null) {
            cameraSource.release();
            cameraSource=null;
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cameraSource!=null) {
            cameraSource.release();
            cameraSource=null;
        }
    }
    private void initialiseDetectorsAndSources() {
        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .setFocusMode(autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                openCamera();
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barCode = detections.getDetectedItems();
                if (barCode.size() > 0) {
                    setBarCode(barCode);
                }
            }
        });
    }

    private void openCamera(){
        try {
            if (ActivityCompat.checkSelfPermission(BarcodeCaptureActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(surfaceView.getHolder());
            } else {
                ActivityCompat.requestPermissions(BarcodeCaptureActivity.this, new
                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBarCode(final SparseArray<Barcode> barCode){

        Intent data = new Intent();
        data.putExtra(BarcodeObject, barCode.valueAt(0).displayValue);
        setResult(CommonStatusCodes.SUCCESS, data);
        finish();

    }


}