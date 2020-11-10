package com.avaal.com.afm2020autoCx;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.models.LogoutModel;

import com.avaal.com.afm2020autoCx.tabtargetview.TapTarget;
import com.avaal.com.afm2020autoCx.tabtargetview.TapTargetSequence;
import com.avaal.com.afm2020autoCx.tabtargetview.TapTargetView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.valdesekamdem.library.mdtoast.MDToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMenuActivity extends AppCompatActivity {
//    @BindView(R.id.account)
//    CardView account;
//    @BindView(R.id.status)
//    CardView status;
//    @BindView(R.id.account_li)
//    LinearLayout account_li;
//    @BindView(R.id.status_li)
//    LinearLayout status_li;
//    @BindView(R.id.account_txt)
//    TextView account_txt;
    @BindView(R.id.close_menu)
FloatingActionButton close_menu;
    PathInterpolator pathInterpolator;
    PreferenceManager prf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet_layout);
        ButterKnife.bind(this);
        prf = new PreferenceManager(this);
        if(prf.getStringData("menuTutorial").equalsIgnoreCase(""))
        tutolrialView();
        slideFromRightToLeft();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    private void slideFromRightToLeft() {
        ObjectAnimator buttonAnimator = ObjectAnimator.ofFloat(close_menu, "translationX",300f, 0f);
        buttonAnimator.setDuration(2000);
        buttonAnimator.setInterpolator(new BounceInterpolator());
        buttonAnimator.start();
    }
    @OnClick(R.id.close_menu)
    void close(){
        ObjectAnimator buttonAnimator = ObjectAnimator.ofFloat(close_menu, "translationX",0f, 250f);
        buttonAnimator.setDuration(2000);
        buttonAnimator.setInterpolator(new BounceInterpolator());
        buttonAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // do stuff
                new Util().myIntent(NewMenuActivity.this,NewDashBoardActivity.class);
            }
        }, 500);
    }
    @OnClick(R.id.add_inven)
    void add_inven(){
        new PreferenceManager(this).saveStringData("OrderType", "Export");
        new PreferenceManager(this).saveStringData("OrderStatus", "save");
        new PreferenceManager(this).saveStringData("When", "add");
        new PreferenceManager(this).saveStringData("vehicleList", null);
        Intent j = new Intent(this, InventoryVehicleListActivity.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        j.putExtra("IsSelect", "false");
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
    @OnClick(R.id.user_profile)
    void user_profile(){
        Intent j = new Intent(this, ProfileActivity.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
    @OnClick(R.id.add_order)
    void add_order(){
        Intent j = new Intent(this, CreateTripType.class);
          j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
       overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
    @OnClick(R.id.cmp_pro)
    void cmp_pro(){
        Intent j = new Intent(this, CallABActivity.class);
j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
    @OnClick(R.id.orders)
    void orders(){
        Intent j = new Intent(this, NewOrderListActivity.class);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
    @OnClick(R.id.logout)
    void logout(){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to Logout ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        logout1();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
//                                    Intent j = new Intent(DashBoardBottomMenu.this, DashBoardBottomMenu.class);
//                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                                    j.putExtra("open","home");
//                                    startActivity(j);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
    @OnClick(R.id.invoices)
    void invoices(){
        Intent j = new Intent(this, InvoiceListActivity.class);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

//        MDToast mdToast = MDToast.makeText(this, "Coming Soon..", MDToast.LENGTH_LONG, MDToast.TYPE_INFO);
//        mdToast.show();
    }
    @OnClick(R.id.receipts)
    void receipts(){
        Intent j = new Intent(this, ReceiptListActivity.class);
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(j);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

//        MDToast mdToast = MDToast.makeText(this, "Coming Soon..", MDToast.LENGTH_LONG, MDToast.TYPE_INFO);
//        mdToast.show();

    }
    @OnClick(R.id.track_order)
    void track_order(){

        MDToast mdToast = MDToast.makeText(this, "Coming Soon..", MDToast.LENGTH_LONG, MDToast.TYPE_INFO);
        mdToast.show();
    }
    @OnClick(R.id.reports)
    void reports(){
        MDToast mdToast = MDToast.makeText(this, "Coming Soon..", MDToast.LENGTH_LONG, MDToast.TYPE_INFO);
        mdToast.show();

    }
    void logout1(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        PreferenceManager prf = new PreferenceManager(this);
        LogoutModel vindetail=new LogoutModel(prf.getStringData("authKey"),prf.getStringData("carrierPrimaryId"));
        Call<LogoutModel> call1 = apiInterface.logOut(""+prf.getStringData("userName"),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {

                LogoutModel getdata = response.body();
                try {
                    if (getdata.status) {
                        MDToast mdToast = MDToast.makeText(NewMenuActivity.this, "Logout Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                        mdToast.show();
                        PreferenceManager prf = new PreferenceManager(NewMenuActivity.this);
//                prf.saveStringData("authKey", "");
                        prf.saveBoolData("logout", false);
//                prf.saveStringData("carrierPrimaryId", "");
//                prf.saveStringData("userName", "");
//                prf.saveStringData("corporateId", "");
//                prf.saveStringData("password", "");
//                prf.saveBoolData("remember", false);
                        finishAffinity();
                        new Util().myIntent(NewMenuActivity.this, LoginActivity.class);

//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));




//                    Picasso.with(this).load("https://images.unsplash.com/photo-450037586774-00cb81edd142?auto=format&fit=crop&w=750&q=80").error(R.drawable.R.drawable.noprofile).into(front_right);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-504196606672-aef5c9cefc92?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_left);
//                    Picasso.with(this).load("https://images.unsplash.com/photo-500395235658-f87dff62cbf3?auto=format&fit=crop&w=750&q=80").error(R.drawable.ic_camera).into(back_right);


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(NewMenuActivity.this,t,"CxE001",null,""+call.request().url().toString());
            }
        });
    }

    void tutolrialView(){
        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.pro_), "Profile","View Your Profile")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.colorPrimary)
                                .descriptionTextColor(android.R.color.white)
                                .transparentTarget(true)
                                .outerCircleAlpha(0.96f)
                                .drawShadow(true)
                                .targetRadius(60)
                .textColor(android.R.color.white),
                        TapTarget.forView(findViewById(R.id.inv), "Add Inventory", "To Add Inventory Vehicle")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.colorPrimary)
                                .descriptionTextColor(android.R.color.white)
                                .transparentTarget(true)
                                .outerCircleAlpha(0.96f)
                                .drawShadow(true)
                                .targetRadius(60)
                .textColor(android.R.color.white),
                        TapTarget.forView(findViewById(R.id.cr_load), "Create Load", "Create New Loads")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.colorPrimary)
                                .outerCircleAlpha(0.96f)
                                .descriptionTextColor(android.R.color.white)
                                .transparentTarget(true)
                                .targetRadius(60)
                .drawShadow(true)
                                .textColor(android.R.color.white),
        TapTarget.forView(findViewById(R.id.pr_cmp), "Company Profile", "View Your Company Profile ")
                .dimColor(android.R.color.white)
                .outerCircleColor(R.color.colorPrimary)
                .outerCircleAlpha(0.96f)
                .descriptionTextColor(android.R.color.white)
                .transparentTarget(true)
                .drawShadow(true)
                .targetRadius(60)
                .textColor(android.R.color.white),
        TapTarget.forView(findViewById(R.id.ord), "Orders", " To View Your All Loads and Its Status")
                .dimColor(android.R.color.white)
                .outerCircleColor(R.color.colorPrimary)
                .outerCircleAlpha(0.96f)
                .descriptionTextColor(android.R.color.white)
                .transparentTarget(true)
                .drawShadow(true)
                .targetRadius(60)
                .textColor(android.R.color.white))

//                        TapTarget.forBounds(rickTarget, "Down", ":^)")
//                                .cancelable(false)
//                                .icon(rick))
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        // Yay
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }


                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                        final AlertDialog dialog = new AlertDialog.Builder(NewMenuActivity.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You cancelled the sequence at step " )
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                })
        .start();
        prf.saveStringData("menuTutorial","1");

    }
}