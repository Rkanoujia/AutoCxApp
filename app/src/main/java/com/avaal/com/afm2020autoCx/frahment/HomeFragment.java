package com.avaal.com.afm2020autoCx.frahment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.CallABActivity;
import com.avaal.com.afm2020autoCx.ConfirmLoadActivity;
import com.avaal.com.afm2020autoCx.CreateTripType;
import com.avaal.com.afm2020autoCx.DashBoardBottomMenu;
import com.avaal.com.afm2020autoCx.EnrouteTripActivity;
import com.avaal.com.afm2020autoCx.InventoryVehicleListActivity;
import com.avaal.com.afm2020autoCx.ProfileActivity;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.models.DashBoardModel;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 08-03-2018.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.save_load)
    TextView save_load;
    @BindView(R.id.ship_load)
    TextView ship_load;
    @BindView(R.id.trip_enroute)
    TextView trip_enroute;
    @BindView(R.id.trip_pending)
    TextView trip_pending;
    @BindView(R.id.received_)
    TextView received_;
    @BindView(R.id.progess_)
    CircularProgressBar progess_;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void onStart() {
        super.onStart();
//        final PreferenceManager prf=new PreferenceManager(getActivity());
//        if(!prf.getBoolData("addShow")) {
//            new MaterialTapTargetPrompt.Builder(getActivity())
//                    .setTarget(getActivity().findViewById(R.id.create_trip))
//                    .setPrimaryText("Add your first Order")
//                    .setPromptBackground(new RectanglePromptBackground())
//                    .setSecondaryText("Tap the envelop to start composing your first Order")
//                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
//                        @Override
//                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
//                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
//                                // User has pressed the prompt target
//                                prf.saveBoolData("addShow",true);
//                            }
//                        }
//                    })
//                    .show();
//        }



    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @OnClick(R.id.inventry)
    void onCreateTrip(){

        new PreferenceManager(getActivity()).saveStringData("OrderType", "Export");
        new PreferenceManager(getActivity()).saveStringData("OrderStatus", "save");
        new PreferenceManager(getActivity()).saveStringData("When", "add");
        new PreferenceManager(getActivity()).saveStringData("vehicleList", null);
        Intent j = new Intent(getActivity(), InventoryVehicleListActivity.class);
        j.putExtra("IsSelect", "false");
//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
       getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//        finish();

    }
    @OnClick(R.id.create_trip)
    void onCreateInventry(){
        Intent j = new Intent(getActivity(), CreateTripType.class);

//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//        finish();

    }
    @OnClick(R.id.profile_)
    void onProfile(){
        Intent j = new Intent(getActivity(), ProfileActivity.class);

//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//        finish();

    }
    @OnClick(R.id.ab_call)
    void onABCall(){
        Intent j = new Intent(getActivity(), CallABActivity.class);

//        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
        startActivity(j);
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//        finish();

    }

    @OnClick(R.id.save_loads_view)
    void saveload(){
        Intent j = new Intent(getActivity(), DashBoardBottomMenu.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        j.putExtra("open","save");
        startActivity(j);
    }
    @OnClick(R.id.ship_loads_view)
    void shippedload(){
        Intent j = new Intent(getActivity(), DashBoardBottomMenu.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        j.putExtra("open","ship");
        startActivity(j);
    }
    @OnClick(R.id.enroute_loads_view)
    void enroute(){
        Intent j = new Intent(getActivity(), EnrouteTripActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        j.putExtra("open","enroute");
        startActivity(j);
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    @OnClick(R.id.pending_loads_view)
    void pending(){
        Intent j = new Intent(getActivity(), ConfirmLoadActivity.class);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        j.putExtra("open","pending");
        startActivity(j);
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}