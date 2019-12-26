package com.avaal.com.afm2020autoCx;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avaal.com.afm2020autoCx.frahment.DashboardFragment;
import com.avaal.com.afm2020autoCx.frahment.DeliveredFragment;
import com.avaal.com.afm2020autoCx.frahment.HomeFragment;
import com.avaal.com.afm2020autoCx.frahment.LoadsFragment;
import com.avaal.com.afm2020autoCx.frahment.YardsFragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import extra.PreferenceManager;

public class DashBoardBottomMenu extends AppCompatActivity {

    private TextView mTextMessage;
    Fragment fragment;
    PreferenceManager prf;
    boolean doubleBackToExitPressedOnce = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack("home");
                    transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    transaction.replace(R.id.flContent, fragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    Bundle bundle = new Bundle();
                    bundle.putString("message", "Shipped");
                    fragment = new DashboardFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transactiondash = getSupportFragmentManager().beginTransaction();
                    transactiondash.addToBackStack("dash");
                    transactiondash.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    transactiondash.replace(R.id.flContent, fragment).commit();
                    return true;
                case R.id.navigation_notifications:

//                    new AlertDialog.Builder(DashBoardBottomMenu.this)
//                            .setMessage("Are you sure you want to Logout ?")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with delete
//                                    MDToast mdToast = MDToast.makeText(DashBoardBottomMenu.this, "Logout Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                                    mdToast.show();
//                                    Intent j = new Intent(DashBoardBottomMenu.this, LoginActivity.class);
//
////        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
//                                    startActivity(j);
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // do nothing
////                                    Intent j = new Intent(DashBoardBottomMenu.this, DashBoardBottomMenu.class);
////                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
////                                    j.putExtra("open","home");
////                                    startActivity(j);
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();


                    fragment = new YardsFragment();
                    FragmentTransaction transactionload = getSupportFragmentManager().beginTransaction();
                    transactionload.addToBackStack("yard");
                    transactionload.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    transactionload.replace(R.id.flContent, fragment).commit();
                    return true;
                case R.id.navigation_saveloads:
                    fragment = new LoadsFragment();
                    FragmentTransaction transactionyadr = getSupportFragmentManager().beginTransaction();
                    transactionyadr.addToBackStack("save");
                    transactionyadr.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    transactionyadr.replace(R.id.flContent, fragment).commit();
                    return true;

                case R.id.navigation_deliver:
                    fragment = new DeliveredFragment();
                    FragmentTransaction transactiondel = getSupportFragmentManager().beginTransaction();
                    transactiondel.addToBackStack("deliver");
                    transactiondel.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    transactiondel.replace(R.id.flContent, fragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
////Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dash_board_bottom_menu);
        ButterKnife.bind(this);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        fragment = new HomeFragment();
        transaction1.addToBackStack("home");
        transaction1.replace(R.id.flContent, fragment).commit();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(getIntent().getStringExtra("open").equalsIgnoreCase("home")) {
            fragment = new HomeFragment();

        }
        else  if(getIntent().getStringExtra("open").equalsIgnoreCase("save")) {
            navigation.setSelectedItemId(R.id.navigation_saveloads);
            fragment = new LoadsFragment();

        }else if(getIntent().getStringExtra("open").equalsIgnoreCase("ship")) {
            Bundle bundle = new Bundle();
            bundle.putString("message", "Shipped");
            fragment = new DashboardFragment();
            navigation.setSelectedItemId(R.id.navigation_dashboard);
            fragment.setArguments(bundle);

        }
        else  if(getIntent().getStringExtra("open").equalsIgnoreCase("pending")) {
            Bundle bundle = new Bundle();
            bundle.putString("message", "Trip");
            if(getIntent().getStringExtra("orderId")!=null) {
                Log.e("MenuOorderId",getIntent().getStringExtra("orderId"));
                bundle.putString("orderId", getIntent().getStringExtra("orderId"));
            }
            fragment = new DashboardFragment();
            navigation.setSelectedItemId(R.id.navigation_dashboard);
            fragment.setArguments(bundle);
        }
        else  if(getIntent().getStringExtra("open").equalsIgnoreCase("enroute")) {
            Bundle bundle = new Bundle();
            bundle.putString("message", "Enroute");
            if(getIntent().getStringExtra("orderId")!=null)
                bundle.putString("orderId", getIntent().getStringExtra("orderId"));
            fragment = new DashboardFragment();
            navigation.setSelectedItemId(R.id.navigation_dashboard);
            fragment.setArguments(bundle);

        }else if(getIntent().getStringExtra("open").equalsIgnoreCase("Delivered")) {
            Bundle bundle = new Bundle();
            if(getIntent().getStringExtra("orderId")!=null)
                bundle.putString("orderId", getIntent().getStringExtra("orderId"));
            fragment = new DeliveredFragment();
            navigation.setSelectedItemId(R.id.navigation_deliver);

        }
        transaction.addToBackStack("home1");
        transaction.replace(R.id.flContent, fragment).commit();
        prf = new PreferenceManager(DashBoardBottomMenu.this);
//       if(!prf.getBoolData("Greet"))
//           showDialog(this, "https://www.avaal.com/images/appgreet.jpg");
    }


    @Override
    public void onBackPressed() {

        this.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
//                this.finish();
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finishAffinity();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

        } else {
            getSupportFragmentManager().popBackStack();
            return;
        }







//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//        mDrawer.closeDrawers();
//        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        if (getSupportFragmentManager().getBackStackEntryCount() <= 2) {
////                this.finish();
//            new AlertDialog.Builder(this)
//                    .setTitle("Exit AFMDriverApp?")
//                    .setMessage("Are you sure you want to exit?")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // continue with delete
//                            finishAffinity();
//                            finishAndRemoveTask();
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // do nothing
//                        }
//                    })
//                    .show();
//
//        } else {
//            getSupportFragmentManager().popBackStack();
//            return;
//        }
//        }

    }
    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ad);

        ImageView img_ = dialog.findViewById(R.id.img_view);

        ImageView cancel = (ImageView) dialog.findViewById(R.id.cancel);
        Picasso.with(this).load(msg).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.default_image)/*.resize(1020,1020)*/.into(img_);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prf.saveBoolData("Greet", true);

//                findViewById(R.id.snow_fall).setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
