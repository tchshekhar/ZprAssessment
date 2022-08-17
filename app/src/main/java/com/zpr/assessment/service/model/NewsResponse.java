package com.zpr.assessment.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    private Pagination pagination;
    @SerializedName("data")
    private List<News> newsList;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "pagination=" + pagination +
                ", newsList=" + newsList +
                '}';
    }
}
