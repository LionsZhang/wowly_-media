package com.wowly.common.net.model.resp;

import android.text.TextUtils;

import java.io.Serializable;


public class ResultErrorInfo implements Serializable {

    private final String DEFAULT_ERROR_MSG = "请求失败";

    /**
     * 返回错误码
     */
    private int ErrorCode;
    /**
     * 返回消息
     */
    private String ErrorMsg;
    /**
     * 验证码地址
     */
    private String Url;
    /**
     * ID
     */
    private String objId;

    private int totalCount;

    private Object resultObj;

    public ResultErrorInfo() {

    }

    public ResultErrorInfo(String errorMsg, String objId) {
        ErrorMsg = errorMsg;
        this.objId = objId;
    }

    public ResultErrorInfo(int errorCode, String errorMsg) {
        ErrorCode = errorCode;
        ErrorMsg = errorMsg;
    }

    public ResultErrorInfo(int totalCount, Object resultObj) {
        this.totalCount = totalCount;
        this.resultObj = resultObj;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMsg() {
        return TextUtils.isEmpty(ErrorMsg) ? DEFAULT_ERROR_MSG : ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getViewId() {
        return objId;
    }

    public void setViewId(String objId) {
        this.objId = objId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public void setResultObj(Object resultObj) {
        this.resultObj = resultObj;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
