package com.zpr.assessment.service.repository;

import androidx.lifecycle.MutableLiveData;

import com.zpr.assessment.service.ApiClient;
import com.zpr.assessment.utils.Resource;
import com.zpr.assessment.service.model.NewsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedRepository {

    private static NewsFeedRepository newsFeedRepository;
    private ApiInterface apiInterface;

    public static NewsFeedRepository getInstance() {
        if (newsFeedRepository == null) {
            newsFeedRepository = new NewsFeedRepository();
        }
        return newsFeedRepository;
    }

    public NewsFeedRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<Resource<NewsResponse>> getNewsFeeds(String apiKey, String countries, MutableLiveData<Resource<NewsResponse>> mutableLiveData) {
        mutableLiveData.setValue(Resource.loading(null));
        apiInterface.getNewsFeeds(apiKey, countries).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.body() != null) {
                    mutableLiveData.setValue(Resource.success(response.body()));
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                mutableLiveData.setValue(Resource.error(t.getMessage(), null));
            }
        });
        return mutableLiveData;
    }
}
