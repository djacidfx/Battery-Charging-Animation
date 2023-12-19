package com.demo.batteryanim.retrofit;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiiClient {
    private static OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
    private static Retrofit retrofit;

    public static Retrofit getClient(String str) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(str).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
        }
        return retrofit;
    }
}
