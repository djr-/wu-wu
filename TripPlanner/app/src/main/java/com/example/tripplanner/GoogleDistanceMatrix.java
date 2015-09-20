package com.example.tripplanner;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GoogleDistanceMatrix {
    @GET("json")
    Call<GoogleDistanceMatrixApi.Result> computeTimeBetween(
            @Query("origins") String origins,
            @Query("destinations") String destinations,
            @Query("key") String apiKey
    );
}
