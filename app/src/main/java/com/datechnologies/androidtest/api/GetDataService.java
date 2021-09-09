package com.datechnologies.androidtest.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {
    @GET("chat_log.php")
    Call<DataModel> getChat();


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> login(@Field("email") String email,@Field("password") String password );
}
