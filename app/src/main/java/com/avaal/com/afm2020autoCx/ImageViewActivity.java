package com.avaal.com.afm2020autoCx;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class ImageViewActivity extends AppCompatActivity {
    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    int mode = NONE;
    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_activity);
        imageView = findViewById(R.id.imageView);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.progress_draw)
                .error(R.drawable.progress_draw)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        try {
            if (getIntent().getStringExtra("isLocal") != null)
                Glide.with(ImageViewActivity.this).applyDefaultRequestOptions(options).load(new File(getIntent().getStringExtra("Url"))).into(imageView);
            else
                Glide.with(ImageViewActivity.this).applyDefaultRequestOptions(options).load(getIntent().getStringExtra("Url")).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
//        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        ImageView view = (ImageView) v;
//        dumpEvent(event);
//
//        // Handle touch events here...
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                savedMatrix.set(matrix);
//                start.set(event.getX(), event.getY());
////                Log.d(TAG, "mode=DRAG");
//                mode = DRAG;
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                oldDist = spacing(event);
////                Log.d(TAG, "oldDist=" + oldDist);
//                if (oldDist > 10f) {
//                    savedMatrix.set(matrix);
//                    midPoint(mid, event);
//                    mode = ZOOM;
////                    Log.d(TAG, "mode=ZOOM");
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_POINTER_UP:
//                mode = NONE;
////                Log.d(TAG, "mode=NONE");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (mode == DRAG) {
//                    // ...
//                    matrix.set(savedMatrix);
//                    matrix.postTranslate(event.getX() - start.x, event.getY()
//                            - start.y);
//                } else if (mode == ZOOM) {
//                    float newDist = spacing(event);
////                    Log.d(TAG, "newDist=" + newDist);
//                    if (newDist > 10f) {
//                        matrix.set(savedMatrix);
//                        float scale = newDist / oldDist;
//                        matrix.postScale(scale, scale, mid.x, mid.y);
//                    }
//                }
//                break;
//        }
//
//        view.setImageMatrix(matrix);
//        return true;
//    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
//    private void dumpEvent(MotionEvent event) {
//        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
//                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
//        StringBuilder sb = new StringBuilder();
//        int action = event.getAction();
//        int actionCode = action & MotionEvent.ACTION_MASK;
//        sb.append("event ACTION_").append(names[actionCode]);
//        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
//                || actionCode == MotionEvent.ACTION_POINTER_UP) {
//            sb.append("(pid ").append(
//                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
//            sb.append(")");
//        }
//        sb.append("[");
//        for (int i = 0; i < event.getPointerCount(); i++) {
//            sb.append("#").append(i);
//            sb.append("(pid ").append(event.getPointerId(i));
//            sb.append(")=").append((int) event.getX(i));
//            sb.append(",").append((int) event.getY(i));
//            if (i + 1 < event.getPointerCount())
//                sb.append(";");
//        }
//        sb.append("]");
//
//    }
//
//    /** Determine the space between the first two fingers */
//    private float spacing(MotionEvent event) {
//        float x = event.getX(0) - event.getX(1);
//        float y = event.getY(0) - event.getY(1);
//        return (float)Math.sqrt(x * x + y * y);
//    }
//
//    /** Calculate the mid point of the first two fingers */
//    private void midPoint(PointF point, MotionEvent event) {
//        float x = event.getX(0) + event.getX(1);
//        float y = event.getY(0) + event.getY(1);
//        point.set(x / 2, y / 2);
//    }
}