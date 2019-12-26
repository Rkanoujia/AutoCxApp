package com.avaal.com.afm2020autoCx.camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.avaal.com.afm2020autoCx.R;


public class CameraViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view_activity);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }

}