package com.avaal.com.afm2020autoCx;

import com.avaal.com.afm2020autoCx.models.MakeModel;
import com.avaal.com.afm2020autoCx.models.VehicleModelModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dell pc on 15-03-2018.
 */

public interface VehicleApiInterface {
    @GET("/api/vehicles/GetMakesForManufacturerAndYear/mer")
    Call<MakeModel> getMakeVehicle(@Query("year") String ggd, @Query("format") String type);

    @GET("/api/vehicles/GetModelsForMake/{id}")
    Call<VehicleModelModel> getModelVehicle(@Path("id") String id, @Query("format") String type);
}
