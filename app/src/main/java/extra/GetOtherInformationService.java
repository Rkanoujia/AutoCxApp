package extra;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.avaal.com.afm2020autoCx.APIClient;
import com.avaal.com.afm2020autoCx.APIInterface;
import com.avaal.com.afm2020autoCx.models.GetCompanyModel;
import com.avaal.com.afm2020autoCx.models.ProfileDataModel;

import androidx.annotation.RequiresApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetOtherInformationService extends Service {
    APIInterface apiInterface;
    PreferenceManager prf;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prf=new PreferenceManager(getApplication());
        getInfo();
        return super.onStartCommand(intent, flags, startId);
    }

    public GetOtherInformationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    void getInfo(){

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetCompanyModel> call1 = apiInterface.getCompanyParameter(""+prf.getStringData("corporateId"),"bearer "+prf.getStringData("accessToken"),"application/json");

        call1.enqueue(new Callback<GetCompanyModel>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<GetCompanyModel> call, Response<GetCompanyModel> response) {


//                hideAnimation();
                try {
                    if (response.message().equalsIgnoreCase("ok")) {

                        GetCompanyModel getCompanyModel = response.body();
//                hideAnimation();
                        if (getCompanyModel.IsDuplicateCustomerOrderNoAllowed != null)
                            prf.saveBoolData("IsDuplicateCustomerOrderNoAllowed", getCompanyModel.IsDuplicateCustomerOrderNoAllowed);


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetCompanyModel> call, Throwable t) {

                call.cancel();

            }
        });
    }

}