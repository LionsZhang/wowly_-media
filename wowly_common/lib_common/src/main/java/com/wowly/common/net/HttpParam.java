package com.wowly.common.net;

import java.util.HashMap;
/**
@Author Lionszhang
@Date   2021/3/22 14:35
@Name   HttpParam.java
@Instruction 请求参数构建类
*/
public class HttpParam {
    private HashMap mParamMap;

    public HttpParam(String method) {
        mParamMap = new HashMap();
        mParamMap.put("method", method);
    }

    public HttpParam put(String key, String value) {
        mParamMap.put(key, value);
        return this;
    }

    public HashMap build() {
        return mParamMap;
    }

}
