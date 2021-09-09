package com.datechnologies.androidtest.api;

import com.google.gson.annotations.SerializedName;

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */

public class ChatLogMessageModel
{
    @SerializedName("user_id")
    private Integer user_id;
    @SerializedName("name")
    private String name;
    @SerializedName("avatar_url")
    private String avatar_url;
    @SerializedName("message")
    private String message;

    public ChatLogMessageModel(Integer user_id, String name, String avatar_url, String message) {
        this.user_id = user_id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.message = message;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
