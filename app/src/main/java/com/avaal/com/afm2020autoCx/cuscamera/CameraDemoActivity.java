package com.avaal.com.afm2020autoCx.cuscamera;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avaal.com.afm2020autoCx.R;
import com.bumptech.glide.Glide;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import androidx.appcompat.app.AppCompatActivity;
import extra.LoaderScreen;

public class CameraDemoActivity extends AppCompatActivity implements PictureCallback,Callback,
        OnClickListener {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Button flipCamera;
    private Button flashCameraButton;
    private Button captureImage;
    private int cameraId;
    private boolean flashmode = false;
    private int rotation;
    ImageView image;
    Bitmap bitmap;
    TextView text;
    ArrayList<String> images=new ArrayList();
    private byte[] mCameraData;
    HashMap<Integer, String> imageList;
    int keyValue;
    boolean editMode=false;
    int imagekey=2131230845;
    boolean isLoaded = false;
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;

    HashMap<Integer,String> fixName;
    ArrayList<String> returnValue = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerademo_activity);
        // camera surface view created
        cameraId = CameraInfo.CAMERA_FACING_BACK;
        flipCamera = (Button) findViewById(R.id.flipCamera);
        flashCameraButton = (Button) findViewById(R.id.flash);
        captureImage = (Button) findViewById(R.id.captureImage);
        image = (ImageView) findViewById(R.id.image);
        text=(TextView)findViewById(R.id.text);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        flipCamera.setOnClickListener(this);
        captureImage.setOnClickListener(this);
        flashCameraButton.setOnClickListener(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Camera.getNumberOfCameras() > 1) {
            flipCamera.setVisibility(View.VISIBLE);
        }
        if (!getBaseContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH)) {
            flashCameraButton.setVisibility(View.GONE);
        }

        fixName = new HashMap<Integer, String>();
        fixName.put(R.drawable.car_c, "Front Right Angle");
        fixName.put(R.drawable.car_a, "Front Left Angle");
        fixName.put(R.drawable.car_f, "Rear Left Angle");
        fixName.put(R.drawable.car_d, "Rear Right Angle");
        fixName.put(R.drawable.car_e, "Rear Roof");
        fixName.put(R.drawable.car_b, "Windshield and Front Roof");
//        Glide.with(CameraDemoActivity.this).load(R.drawable.car_f).into(image);
//       if(getIntent().getStringExtra("type")!=null) {
//           text.setText(""+getIntent().getStringExtra("type"));
//           if (getIntent().getStringExtra("type").equalsIgnoreCase("FrontRightAngleView")) {
//               Glide.with(CameraDemoActivity.this).load(R.drawable.car_f).into(image);
//
//           }
//       }
        imageList = (HashMap<Integer, String>)getIntent().getSerializableExtra("images");

        if(getIntent().getStringExtra("EditIns")!=null){
            editMode=true;
        }

        image();



    }

    void image(){
        Map<Integer, String> map = new TreeMap<Integer, String>(imageList);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase("")) {
                image.setVisibility(View.VISIBLE);
                keyValue=entry.getKey();
                text.setText(fixName.get(entry.getKey()));
                Glide.with(CameraDemoActivity.this).load(entry.getKey()).into(image);
                System.out.println(entry.getKey());

                return;
            }
        }
        if(editMode){
            imagekey=imagekey+imageList.size();
            keyValue = imagekey;
        }else {
            imagekey=imagekey+imageList.size();
            keyValue = imagekey;
        }
        text.setVisibility(View.INVISIBLE);
        image.setVisibility(View.INVISIBLE);



    }
    void imagemsg(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.camera_popup);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // if button is clicked, close the custom dialog
        Button ok=(Button)dialog.findViewById(R.id.buttonOk) ;
        Button cancel=(Button)dialog.findViewById(R.id.buttoncancel);
        TextView title=(TextView)dialog.findViewById(R.id.title) ;
        TextView message=(TextView)dialog.findViewById(R.id.message) ;
        title.setText("");
        message.setText("Do you want Add Damage ?");
        ok.setText("Yes");
        cancel.setText("No");
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                for (Map.Entry<Integer, String> entry : imageList.entrySet()) {
                    if (entry.getValue().equals("")) {
                        image.setVisibility(View.VISIBLE);
                        keyValue=entry.getKey();
                        text.setText(fixName.get(entry.getKey()));
                        Glide.with(CameraDemoActivity.this).load(entry.getKey()).into(image);
                        System.out.println(entry.getKey());

                        return;
                    }
                }

                if(editMode){
                    imagekey=imagekey+imageList.size();
                    keyValue = imagekey;
                }else {
                    imagekey=imagekey+imageList.size();
                    keyValue = imagekey;
                }
                image.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);


            }
        });
        dialog.show();




    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!openCamera(CameraInfo.CAMERA_FACING_BACK)) {
            alertCameraDialog();
        }

    }

    private boolean openCamera(int id) {
        boolean result = false;
        cameraId = id;
        releaseCamera();
        try {
            camera = Camera.open(cameraId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (camera != null) {
            try {
                setUpCamera(camera);

                camera.setErrorCallback(new ErrorCallback() {

                    @Override
                    public void onError(int error, Camera camera) {

                    }
                });
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
                releaseCamera();
            }
        }
        return result;
    }

    private void setUpCamera(Camera c) {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        rotation = getWindowManager().getDefaultDisplay().getRotation();
//        rotation = getResources().getConfiguration().orientation;
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 0;
                break;
            case Surface.ROTATION_90:
                degree = 90;
                break;
            case Surface.ROTATION_180:
                degree = 180;
                break;
            case Surface.ROTATION_270:
                degree = 270;
                break;

            default:
                break;
        }

        if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
            // frontFacing
            rotation = (info.orientation + degree) % 330;
            rotation = (360 - rotation) % 360;
        } else {
            // Back-facing
            rotation = (info.orientation - degree + 360) % 360;
        }
        c.setDisplayOrientation(rotation);
        Parameters params = c.getParameters();
        showFlashButton(params);

        List<String> focusModes = params.getSupportedFlashModes();
        if (focusModes != null) {
            if (focusModes
                    .contains(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                params.setFlashMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
        }

        params.setRotation(rotation);
    }

    private void showFlashButton(Parameters params) {
        boolean showFlash = (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH) && params.getFlashMode() != null)
                && params.getSupportedFlashModes() != null
                && params.getSupportedFocusModes().size() > 1;

        flashCameraButton.setVisibility(showFlash ? View.VISIBLE
                : View.INVISIBLE);

    }

    private void releaseCamera() {
        try {

            if (camera != null) {
//                Parameters param = camera.getParameters();
//                param.setFlashMode(Parameters.FLASH_MODE_OFF);
//                camera.setParameters(param);
                camera.setPreviewCallback(null);
                camera.setErrorCallback(null);
                camera.stopPreview();
                camera.release();
                mCameraData = null;
                camera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flash:
                flashOnButton();
                break;
            case R.id.flipCamera:
                flipCamera();
                break;
            case R.id.captureImage:
                takeImage();
                break;

            default:
                break;
        }
    }

    private void takeImage() {
        showAnimation();
        captureImage();


//        camera.takePicture(null, null, new PictureCallback() {
//
//            private File imageFile;
//
//            @Override
//            public void onPictureTaken(byte[] data, Camera camera) {
//                try {
//                    // convert byte array into bitmap
//                    Bitmap loadedImage = null;
//                    Bitmap rotatedBitmap = null;
//                    loadedImage = BitmapFactory.decodeByteArray(data, 0,
//                            data.length);
//
//                    // rotate Image
//                    Matrix rotateMatrix = new Matrix();
//                    rotateMatrix.postRotate(rotation);
//                    rotatedBitmap = Bitmap.createBitmap(loadedImage, 0, 0,
//                            loadedImage.getWidth(), loadedImage.getHeight(),
//                            rotateMatrix, false);
//                    String state = Environment.getExternalStorageState();
//                    File folder = null;
//                    if (state.contains(Environment.MEDIA_MOUNTED)) {
//                        folder = new File(Environment
//                                .getExternalStorageDirectory() + "/Demo");
//                    } else {
//                        folder = new File(Environment
//                                .getExternalStorageDirectory() + "/Demo");
//                    }
//
//                    boolean success = true;
//                    if (!folder.exists()) {
//                        success = folder.mkdirs();
//                    }
//                    if (success) {
//                        java.util.Date date = new java.util.Date();
//                        imageFile = new File(folder.getAbsolutePath()
//                                + File.separator
//                                + "Image.jpg");
//
//                        imageFile.createNewFile();
//                    } else {
//                        Toast.makeText(getBaseContext(), "Image Not saved",
//                                Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    ByteArrayOutputStream ostream = new ByteArrayOutputStream();
//
//                    // save image into gallery
//                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
//
//                    FileOutputStream fout = new FileOutputStream(imageFile);
//                    fout.write(ostream.toByteArray());
//                    fout.close();
//                    ContentValues values = new ContentValues();
//
//                    values.put(MediaStore.Images.Media.DATE_TAKEN,
//                            System.currentTimeMillis());
//                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//                    values.put(MediaStore.MediaColumns.DATA,
//                            imageFile.getAbsolutePath());
//
//                    CameraDemoActivity.this.getContentResolver().insert(
//                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
    }
    private void captureImage() {
        camera.takePicture(null, null, this);
    }
    private void flipCamera() {
        int id = (cameraId == CameraInfo.CAMERA_FACING_BACK ? CameraInfo.CAMERA_FACING_FRONT
                : CameraInfo.CAMERA_FACING_BACK);
        if (!openCamera(id)) {
            alertCameraDialog();
        }
    }

    private void alertCameraDialog() {
        Builder dialog = createAlert(CameraDemoActivity.this,
                "Camera info", "error to open camera");
        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        dialog.show();
    }

    private Builder createAlert(Context context, String title, String message) {

        Builder dialog = new Builder(
                new ContextThemeWrapper(context,
                        android.R.style.Theme_Holo_Light_Dialog));
        dialog.setIcon(R.mipmap.ic_launcher);
        if (title != null)
            dialog.setTitle(title);
        else
            dialog.setTitle("Information");
        dialog.setMessage(message);
        dialog.setCancelable(false);
        return dialog;

    }

    private void flashOnButton() {
        if (camera != null) {
            try {
                Parameters param = camera.getParameters();
                param.setFlashMode(!flashmode ? Parameters.FLASH_MODE_TORCH
                        : Parameters.FLASH_MODE_OFF);
                camera.setParameters(param);
                flashmode = !flashmode;
                if(flashmode)
                    flashCameraButton.setBackground(getResources().getDrawable(R.drawable.ic_flash_on));
                else
                    flashCameraButton.setBackground(getResources().getDrawable(R.drawable.ic_flash_off));

            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }
    private File openFileForImage() {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "image_" + timeStamp;
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (OutOfMemoryError e) {

            Toast.makeText(CameraDemoActivity.this, "No Enough Memory",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch
        (IOException e) {
            e.printStackTrace();
        }
        return new File(image.getAbsolutePath());
    }

    private File saveImageToFile(File file) {
        if (bitmap != null) {
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(file);
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)) {
                    Toast.makeText(CameraDemoActivity.this, "Unable to save image to file.",
                            Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(CameraDemoActivity.this, "Saved image to: " + file.getPath(),
//                            Toast.LENGTH_LONG).show();
                    images.add(file.getPath());
                }
                outStream.close();

            } catch (OutOfMemoryError e) {

                Toast.makeText(CameraDemoActivity.this, "No Enough Memory",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (Exception e) {

                Toast.makeText(CameraDemoActivity.this, "Unable to save image to file.",
                        Toast.LENGTH_LONG).show();
            }
            return file;
        } else
            return null;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        mCameraData = data;
//        openCamera(CameraInfo.CAMERA_FACING_BACK);
//        Glide.with(CameraDemoActivity.this).load(R.drawable.car_a).into(image);
        setupImageDisplay();

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                //TODO your background code
//                setupImageDisplay();
//            }
//        });


    }
    private void setupImageDisplay() {
        bitmap = BitmapFactory.decodeByteArray(mCameraData, 0, mCameraData.length);
//        Bitmap rotatedBitmap = null;
//        Matrix rotateMatrix = new Matrix();
//        rotateMatrix.postRotate(rotation);
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                bitmap.getWidth(), bitmap.getHeight(),
//                rotateMatrix, false);
//        ExifInterface exif = new ExifInterface(imageFilePath);
//        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//        bitmap = getScaledBitmap(bitmap, 800, 1000);
//        Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);
        if (mCameraData != null) {

            File file = saveImageToFile(openFileForImage()).getAbsoluteFile();
            imageList.put(keyValue, "" + file);

            Log.e("cameradata", "1");

            Intent intent = new Intent();
            intent.putExtra("File", "" + file.getPath());
            intent.putExtra("images", imageList);
            releaseCamera();
//                intent.putExtra(EXTRA_CAMERA_DATA, mCameraData);
            setResult(RESULT_OK, intent);
            mCameraData = null;
            finish();
//            if(editMode)
//                finish();
//            else
//                imagemsg();


//            openCamera(CameraInfo.CAMERA_FACING_BACK);
//            Glide.with(CameraDemoActivity.this).load(R.drawable.front).into(image);
        } else {
            Log.e("cameradata", "0");
            setResult(RESULT_CANCELED);
        }

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
    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mainlayout = (FrameLayout) findViewById(R.id.main_fram);
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