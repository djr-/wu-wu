package com.example.tripplanner;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Api.Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);
}
