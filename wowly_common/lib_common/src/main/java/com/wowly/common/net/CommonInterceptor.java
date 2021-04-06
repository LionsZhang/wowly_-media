package com.wowly.common.net;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class CommonInterceptor implements Interceptor {
    private StringBuffer stringBuffer = new StringBuffer();
    private String TAG = "CommonInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Request request = chain.request().newBuilder().build();
            Log.e(TAG, " request:" + request.toString());
            String path = request.url().toString();
            Log.e(TAG, " request>>path:" + path);
            String method = request.method();
            Log.e(TAG, " method>>method=:" + method);
            RequestBody requestBody = request.body();
            String body = null;
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = Charset.defaultCharset();
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                body = buffer.readString(charset);
            }

            JSONObject jsonObject = new JSONObject(body);
             String atMethod=   jsonObject.optString("method");

            Log.e(TAG, "发送请求: method：" + request.method()
                    + "\nurl：" + request.url()
                    + "\n请求头：" + request.headers()
                    + "\n请求参数: " + body);

//        if("POST".equals(method)){
//
//        }

//        StringBuilder sb = new StringBuilder();
//        if (request.body() instanceof FormBody) {
//            FormBody body = (FormBody) request.body();
//            for (int i = 0; i < body.size(); i++) {
//                sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
//            }
//            sb.delete(sb.length() - 1, sb.length());
//        }
//
//        Log.e(TAG, "RequestParams:{"+sb.toString()+"}");


            // 重新构建请求
            Request.Builder builder = chain.request().newBuilder();
            builder.url(stringBuffer.append(path).append(atMethod).toString())//拼接URL
                    .addHeader("token", "lkjlkjlkjkl")
                    .addHeader("langCode", "1");
            Request newRequest = builder.build();
            Log.e(TAG, " newRequest:" + newRequest.toString());
            long t1 = System.nanoTime();
            Response response = chain.proceed(newRequest);
            long t2 = System.nanoTime();
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.e(TAG, "okhttp>>>" + String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            Log.e(TAG, "okhttp>>>" + "response body: " + responseBody.string());
            return response;
        } catch (JSONException e) {


        }

        return null;
    }
}
