package com.example.tripplanner;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TripAdvisor {
    @GET("search/{location}")
    Call<Api.SearchResults> searchGeos(
            @Path("location") String location,
            @Query("key") String apiKey
    );
}
