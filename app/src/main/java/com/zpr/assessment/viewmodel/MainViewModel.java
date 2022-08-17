package com.zpr.assessment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zpr.assessment.utils.Constants;
import com.zpr.assessment.service.repository.NewsFeedRepository;
import com.zpr.assessment.service.model.NewsResponse;
import com.zpr.assessment.utils.Resource;

public class MainViewModel extends ViewModel {
    public MutableLiveData<Resource<NewsResponse>> mutableLiveData = new MutableLiveData<>();
    private NewsFeedRepository newsFeedRepository;

    public LiveData<Resource<NewsResponse>> loadNewsFeeds() {
        newsFeedRepository = NewsFeedRepository.getInstance();
        mutableLiveData = newsFeedRepository.getNewsFeeds(Constants.API_KEY, Constants.COUNTRIES_ZA, mutableLiveData);
        return mutableLiveData;
    }
}