package com.wowly.common.net;


import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface HttpService {


    @POST("SocialFinance")
    Call<JsonObject> post(@Body RequestBody rout);


    @GET("SocialFinance")
    Call<JsonObject>get(@Body RequestBody rout);

}
