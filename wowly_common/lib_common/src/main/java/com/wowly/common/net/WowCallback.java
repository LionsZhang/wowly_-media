package com.wowly.common.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wowly.common.net.exception.ApiException;
import com.wowly.common.net.model.resp.ResultInfo;
import com.wowly.common.net.util.JsonParseUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class WowCallback<T> implements Callback<JsonObject> {
    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        try {
            Log.e("ATCallback","jsonResponse:" + response.toString());
            onRetrofitResponse(response);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private void onRetrofitResponse(Response<JsonObject> response) {

        switch (response.code()) {
            case 200:
                if (response != null && response.body() != null) {
                    Log.e("WowCallback","jsonResponseBody:" + response.body());
                    ResultInfo   httpResult = JsonParseUtil.parseToObject(response.body().toString(), ResultInfo.class);
                    // response.body():{"code":200,"data":{"token":"xxx123asss"},"message":"成功","success":true}
                    Log.e("WowCallback","response.body():" + response.body().toString());
                    //成功，则回调onSuccessFul
                    if (httpResult.isSuccess()){

                        //1、请求data为空
                        //2、请求未指定 T 泛型

                        Object data;
                        ResultInfo   parseResult;
                        if (httpResult.getData() == null) {
                            data = null;
                        } else {
                            Type superClass = getClass().getGenericSuperclass();
                            if (superClass instanceof ParameterizedType) {
                                Type itemType = ((ParameterizedType) superClass).getActualTypeArguments()[0];
                                data = JsonParseUtil.parseToGenericObject(new Gson().toJson(httpResult.getData()), itemType);
                            } else {
                                data = httpResult.getData();
                            }
                        }

                        if (data instanceof ResultInfo) {
                          parseResult= (ResultInfo) data;
                            onSuccessFul(parseResult);
                        } else {
                            ResultInfo resultInfo = new ResultInfo();
                            resultInfo.setCode(httpResult.getCode());
                            resultInfo.setMessage(httpResult.getMessage());
                            resultInfo.setData(data);
                            parseResult= resultInfo;
                        }
                        onSuccessFul(parseResult);
                        return;
                    }
//                    否则，根据code处理
                    switch (httpResult.getCode()) {
                        //示例：
                        default:
                            onFailed(new ApiException(ApiException.CODE_OTHER_EXCEPTION,ApiException.RETROFIT_ERROR_UNKNOWN));
                            break;
                    }
                    return;
                }
                onFailed(new ApiException(ApiException.CODE_OTHER_EXCEPTION,ApiException.RETROFIT_ERROR_UNKNOWN));
                break;
            case ApiException.CODE_REQ_CREATED:
            case ApiException.CODE_REQ_UNAUTHORIZED:
            case ApiException.CODE_REQ_NOT_FORBIDDEN:
            case ApiException.CODE_REQ_NOT_FOUND:
                onFailed(new ApiException(response.code(),response.message()));

            default:
                onFailed(new ApiException(ApiException.CODE_OTHER_EXCEPTION,ApiException.RETROFIT_ERROR_REQUEST_FAILED));
                break;
        }

    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        onFailed(new ApiException(ApiException.CODE_OTHER_EXCEPTION,t.getMessage()));
    }

    /**
     * 请求成功
     *
     * @param result
     */
    public abstract void onSuccessFul(ResultInfo result);

    /**
     * 请求失败
     *
     * @param error
     */
    public abstract void onFailed(ApiException error);


}
