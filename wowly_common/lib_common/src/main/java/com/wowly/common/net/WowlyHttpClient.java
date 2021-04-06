package com.wowly.common.net;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WowlyHttpClient {


    private static final int DEFAULT_TIMEOUT = 60;
    private static WowlyHttpClient instance;
        private String baseUrl= "https://github.com/";
    // private String baseUrl = "https://github.com.com/";
    private Retrofit mRetrofit;

    private WowlyHttpClient() {
    }


    public static WowlyHttpClient getInstance() {
        if (instance == null) {
            synchronized (WowlyHttpClient.class) {
                if (instance == null) {
                    instance = new WowlyHttpClient();
                }
            }
        }
        return instance;
    }

    /**
     * @Author Lionszhang
     * @Date 2021/3/18 18:15
     * @Name AitdHttpClient.java
     * @Instruction 创建Http请求服务类
     */

    public void initAitdHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new CommonInterceptor());
//        builder.addInterceptor(new SimpleMockInterceptor(BuildConfig.IS_DEBUG));
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
//                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    public HttpParam generateParam(String method) {

        return new HttpParam(method);
    }



    /**
     * @Author Lionszhang
     * @Date 2021/3/18 18:15
     * @Name AitdHttpClient.java
     * @Instruction 创建Http请求参数
     */
    private RequestBody createRequestBody(HashMap map) {
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return requestBody;
    }


    public void post(HashMap map, WowCallback callback) {
        mRetrofit.create(HttpService.class).post(createRequestBody(map)).enqueue(callback);

    }

    public void get(HashMap map, WowCallback callback) {
        mRetrofit.create(HttpService.class).post(createRequestBody(map)).enqueue(callback);

    }

}
