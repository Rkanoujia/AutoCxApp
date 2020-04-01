package com.avaal.com.afm2020autoCx.frahment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.R;
import com.avaal.com.afm2020autoCx.adapter.AppSettingModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import extra.LoaderScreen;
import extra.PreferenceManager;
import extra.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell pc on 08-03-2018.
 */

public class YardsFragment extends Fragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.home_)
    TextView home_;
    @BindView(R.id.notification)
    Switch notification;
    @BindView(R.id.message)
    Switch message;
    @BindView(R.id.email)
    Switch email;
    APIInterface apiInterface;
    PreferenceManager prf;
    Boolean first = false;
    private FrameLayout mainlayout;
    private LoaderScreen loaderScreen;
    private View loaderView;
    boolean isLoaded = false;

    public YardsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.yard_fragment, container, false);
        mainlayout = (FrameLayout) rootView.findViewById(R.id.frame3);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void onStart() {
        super.onStart();
        home_.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        title.setText("Settings");
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prf = new PreferenceManager(getActivity());
        Util util = new Util();
        if (!util.isNetworkAvailable(getActivity())) {
            MDToast mdToast = MDToast.makeText(getActivity(), "Check Your Internet", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
            mdToast.show();
            return;
        }
        showAnimation();
        getSetting();
//        new AlertDialog.Builder(getActivity())
//                .setMessage("Are you sure you want to Logout ?")
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // continue with delete
//                        MDToast mdToast = MDToast.makeText(getActivity(), "Logout Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                        mdToast.show();
//                        Intent j = new Intent(getActivity(), LoginActivity.class);
//
////        j.putExtra("AuthKey", getActivity().getIntent().getStringExtra("AuthKey"));
//                        startActivity(j);
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                        Intent j = new Intent(getActivity(), DashBoardBottomMenu.class);
//                        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                        j.putExtra("open","home");
//                        startActivity(j);
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (first) {
                    showAnimation();
                    saveSetting("notification");
                }

            }
        });
        message.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (first) {
                    showAnimation();
                    saveSetting("Message");
                }

            }
        });
        email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (first) {
                    showAnimation();
                    saveSetting("email");
                }
//                if(b){
//                    MDToast mdToast = MDToast.makeText(getActivity(), "By Email On", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                    mdToast.show();
//                }
//                else{
//                    MDToast mdToast = MDToast.makeText(getActivity(), "By Email Off", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                    mdToast.show();
//                }
            }
        });
    }

    void getSetting() {
//        AppSettingModel vindetail = new AppSettingModel(prf.getStringData("authKey"), prf.getStringData("carrierPrimaryId"), "false", "false", "false", "0");
//        Call<SaveImageModel> call1 = apiInterface.updateProfile(vehicleIds, fileNames,authKey1,content,docTypess,exten,size1,remarks, body);
        Call<AppSettingModel> call1 = apiInterface.getappSetting(""+ prf.getStringData("userCode"),""+prf.getStringData("userName"),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");
        call1.enqueue(new Callback<AppSettingModel>() {
            @Override
            public void onResponse(Call<AppSettingModel> call, Response<AppSettingModel> response) {
                final AppSettingModel getdata = response.body();
                hideAnimation();
                try {
                    if (getdata.status) {
                        notification.setChecked(getdata.data.isByApp);
                        message.setChecked(getdata.data.isByText);
                        email.setChecked(getdata.data.isByMail);
                        first = true;
                    } else {
                        MDToast mdToast = MDToast.makeText(getActivity(), "Some thing went wrong", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AppSettingModel> call, Throwable t) {
                call.cancel();
                MDToast mdToast = MDToast.makeText(getActivity(), "Some Technical Issue", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                mdToast.show();
                new Util().sendSMTPMail(getActivity(),t,"CxE001",null,""+call.request().url());
            }
        });
    }

    //    @OnClick(R.id.save_sett)
    void saveSetting(final String By) {
//        AppSettingModel vindetail = new AppSettingModel(prf.getStringData("authKey"), prf.getStringData("carrierPrimaryId"), "" + , , "" + email.isChecked(), "1");
//        Call<SaveImageModel> call1 = apiInterface.updateProfile(vehicleIds, fileNames,authKey1,content,docTypess,exten,size1,remarks, body);
        Call<AppSettingModel> call1 = apiInterface.appSetting("" + message.isChecked(),""+ email.isChecked(),""+notification.isChecked(),""+prf.getStringData("userName"),""+ prf.getStringData("userName"),new Util().getDateTime(),""+prf.getStringData("userCode"),""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/x-www-form-urlencoded");
        call1.enqueue(new Callback<AppSettingModel>() {
            @Override
            public void onResponse(Call<AppSettingModel> call, Response<AppSettingModel> response) {
                final AppSettingModel getdata = response.body();
                hideAnimation();
                try {
                    if (getdata.status) {
//                    MDToast mdToast = MDToast.makeText(getActivity(), "Update Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                    mdToast.show();

//                        notification.setChecked(getdata.data.isByApp);
//                        message.setChecked(getdata.data.isByText);
//                        email.setChecked(getdata.data.isByMail);
                        if (By.equalsIgnoreCase("notification")) {
                            if (notification.isChecked()) {
                                MDToast mdToast = MDToast.makeText(getActivity(), "By App Notification On", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                            } else {
                                MDToast mdToast = MDToast.makeText(getActivity(), "App Notification Off", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                            }
                        } else if (By.equalsIgnoreCase("Message")) {
                            if (message.isChecked()) {
                                MDToast mdToast = MDToast.makeText(getActivity(), "SMS Notification On", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                            } else {
                                MDToast mdToast = MDToast.makeText(getActivity(), "SMS Notification Off", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                            }
                        } else if (By.equalsIgnoreCase("email")) {
                            if (email.isChecked()) {
                                MDToast mdToast = MDToast.makeText(getActivity(), "Email Notification On", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                            } else {
                                MDToast mdToast = MDToast.makeText(getActivity(), "Email Notification Off", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                            }
                        }


                    } else {
                        MDToast mdToast = MDToast.makeText(getActivity(), "Some thing went wrong", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AppSettingModel> call, Throwable t) {
                call.cancel();
                new Util().sendSMTPMail(getActivity(),t,"CxE001",null,""+call.request().url());
            }
        });
    }

    private void showAnimation() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        if (mainlayout != null && loaderScreen == null) {
            loaderScreen = new LoaderScreen(getActivity());
            loaderView = loaderScreen.getView();
            loaderScreen.showBackground(getActivity(), true);
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
