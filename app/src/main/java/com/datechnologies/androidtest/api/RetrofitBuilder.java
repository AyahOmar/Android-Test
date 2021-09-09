package com.datechnologies.androidtest.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://dev.rapptrlabs.com/Tests/scripts/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static GetDataService service = RetrofitBuilder.getRetrofitInstance().create(GetDataService.class);

}
