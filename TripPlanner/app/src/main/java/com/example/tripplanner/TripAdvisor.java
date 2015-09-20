package com.example.tripplanner;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TripAdvisor {
    @GET("search/{location}")
    Call<TripAdvisorApi.SearchResults> searchGeos(
            @Path("location") String location,
            @Query("key") String apiKey
    );
    @GET("location/{location_id}/attractions")
    Call<TripAdvisorApi.SearchResults> searchAttractions(
            @Path("location_id") String location_id,
            @Query("key") String apiKey
    );
}
