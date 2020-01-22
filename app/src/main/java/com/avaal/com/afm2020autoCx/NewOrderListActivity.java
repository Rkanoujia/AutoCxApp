package com.avaal.com.afm2020autoCx;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.adapter.TabAdapter;
import com.avaal.com.afm2020autoCx.frahment.AfmOrderFragment;
import com.avaal.com.afm2020autoCx.frahment.FragmentOne;
import com.avaal.com.afm2020autoCx.frahment.LoadsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.BottomSheetLayout;
import extra.Util;


public class NewOrderListActivity extends AppCompatActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.home_)
    TextView home_;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_orderlist_activity);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // Get the ActionBar here to configure the way it behaves.
//        bg_image.setAlpha(0.6f
        title.setText("Orders");
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoadsFragment(), "My Loads");
        adapter.addFragment(new AfmOrderFragment(), "AFM Orders");
//        adapter.addFragment(new FragmentOne(), "Shipment");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        if(getIntent().getStringExtra("AFMOrder")!=null)
        viewPager.setCurrentItem(1);
    }
    @Override
    public void onBackPressed() {
        // Write your code here
//        new Util().myIntent(NewOrderListActivity.this,NewMenuActivity.class);
        super.onBackPressed();
    }

    @OnClick(R.id.back)
    void back(){
//        finish();
//        new Util().myIntent(NewOrderListActivity.this,NewMenuActivity.class);

        super.onBackPressed();
    }
    @OnClick(R.id.home_)
    void home(){
        new Util().myIntent(this,NewDashBoardActivity.class);
    }
}