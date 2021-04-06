package com.wowly.common.net.exception;

import android.text.TextUtils;

import retrofit2.Response;

/**
 * @Author Lionszhang
 * @Date 2021/2/23 17:32
 * @Name ApiException.java
 * @Instruction 网络请求异常封装类
 */
public class ApiException extends Exception {
    /***无网络连接***/
    public static final int CODE_NO_NETWORK = 4000;

    /***OkHttp 回调 onFailure***/
    public static final int CODE_OKHTTP_FAILURE = 4001;
    /***OkHttp response.isSuccessful() == false***/
    public static final int CODE_OKHTTP_NO_SUCCESS = 4002;

    /***response.code == 299***/
    public static final int CODE_REQ_TOOFREQUEST = 4003;
    /***!response.isSuccessful()***/
    public static final int CODE_REQ_FAILURE = 4004;
    /***OkHttp 回调 onFailure——SocketTimeoutException***/
    public static final int CODE_OKHTTP_SOCKET_TIMEOUT = 4005;
    /***OkHttp 回调 onFailure——SocketException***/
    public static final int CODE_OKHTTP_SOCKET_EXP = 4006;
    /***OkHttp 回调 onFailure——SSLException***/
    public static final int CODE_OKHTTP_SSL_EXP = 4007;
    public static final int CODE_REQ_CREATED = 201;
    public static final int CODE_REQ_UNAUTHORIZED = 401;
    public static final int CODE_REQ_NOT_FORBIDDEN = 403;
    public static final int CODE_REQ_NOT_FOUND = 404;
    /***其余异常***/
    public static final int CODE_OTHER_EXCEPTION = 7000;

    public static final String RETROFIT_ERROR_UNKNOWN="未知错误";
    public static final String RETROFIT_ERROR_REQUEST_FAILED="请求失败";

    /**
     * 错误码
     */
    private int mCode;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 请求URL
     */
    private String mUrl;

    /**
     * 异常处理Object
     */
    private Object mResultObject;


    /**
     *<br> Description: 构造函数
     *<br> Author:      lionszhang
     *<br> Date:        2021/2/23 18:34
     *
     * @param code int
     * @param message String
     * @param throwable Throwable
     */
    public ApiException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.mCode = code;
    }

    /**
     *<br> Description: 构造函数
     *<br> Author:      lionszhang
     *<br> Date:        2021/2/23 18:34
     *
     * @param code int
     * @param message String
     */
    public ApiException(int code, String message) {
        super(message);
        this.mCode = code;
    }

    /**
     *<br> Description: 构造函数
     *<br> Author:      lionszhang
     *<br> Date:        2021/2/23 18:34
     *
     * @param code int
     * @param throwable Throwable
     */
    public ApiException(int code, Throwable throwable) {
        super(throwable);
        this.mCode = code;
    }



    /**
     *<br> Description: 构造函数
     *<br> Author:      lionszhang
     *<br> Date:        2021/2/23 18:34
     *
     * @param code int
     * @param message String
     * @param resultObject Object
     */
    public ApiException(int code, String message, Object resultObject) {
        super(message);
        this.mCode = code;
        this.mResultObject = resultObject;
    }

    /**
     *<br> Description: 构造函数
     *<br> Author:      lionszhang
     *<br> Date:        2021/2/23 18:34
     */
    public ApiException(Response<?> response) {
        super(getMessage(response));
        this.mCode = response.code();
        this.message = response.message();
    }


    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    @Override
    public String getMessage() {
        if (TextUtils.isEmpty(message)) {
            if (TextUtils.isEmpty(super.getMessage())) {
                return "Unknown Error!";
            } else {
                return super.getMessage();
            }
        } else {
            return message;
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     *<br> Description: 获取Response信息
     *<br> Author:      lionszhang
     *<br> Date:        2021/2/23 18:34
     *
     * @param response Response
     * @return String
     */
    private static String getMessage(Response<?> response) {
        if (response == null) {
            return "Response is null!!! ";
        }
        return "HTTP " + response.code() + " " + response.message();
    }
}
