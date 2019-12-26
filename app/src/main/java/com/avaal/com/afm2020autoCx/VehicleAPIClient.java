package com.avaal.com.afm2020autoCx;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dell pc on 15-03-2018.
 */

public class VehicleAPIClient {
    private static Retrofit retrofit = null;




    static Retrofit getVehicleAPIClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://vpic.nhtsa.dot.gov")
//                .baseUrl("http://192.168.1.175:42736")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();



        return retrofit;



    }
}
