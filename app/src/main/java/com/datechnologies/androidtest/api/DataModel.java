package com.datechnologies.androidtest.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {
    @SerializedName("data")
    private List<ChatLogMessageModel> data;

    public DataModel(List<ChatLogMessageModel> data) {
        this.data = data;
    }

    public List<ChatLogMessageModel> getData() {
        return data;
    }

    public void setData(List<ChatLogMessageModel> data) {
        this.data = data;
    }
}
