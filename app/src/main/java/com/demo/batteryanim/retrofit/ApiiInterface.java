package com.demo.batteryanim.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiiInterface {
    @GET
    Call<ResponseBody> getAppStructure(@Url String str);
}
