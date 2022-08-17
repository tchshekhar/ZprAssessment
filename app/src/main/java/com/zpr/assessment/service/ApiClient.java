package com.zpr.assessment.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
    retrofit = new Retrofit.Builder()
                .baseUrl("http://api.mediastack.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
