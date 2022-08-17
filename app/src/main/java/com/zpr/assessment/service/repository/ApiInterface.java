package com.zpr.assessment.service.repository;

import com.zpr.assessment.service.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("news")
    Call<NewsResponse> getNewsFeeds(
            @Query("access_key") String apiKey,
            @Query("countries") String countries
    );
}
