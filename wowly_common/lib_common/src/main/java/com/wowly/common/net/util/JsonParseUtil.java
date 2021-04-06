package com.wowly.common.net.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * ClassName:   JsonParseUtil
 * Description: Json解析工具类, Gson实现
 * <p>
 * Author:     lionszhang
 * Date:        2018/3/7 9:57
 */
@SuppressWarnings("unused")
public class JsonParseUtil {
    private static final String TAG = "JsonParseUtil";

    /**
     * Json转Object
     * <p>
     * 如：UserInfo userInfo = JsonParseUtil.parseToObject(dataStr, UserInfo.class);
     *
     * @param dataStr String
     */
    public static <T> T parseToObject(String dataStr, Class<T> cls) {
        if (TextUtils.isEmpty(dataStr)) {
            return null;
        }

        try {
            return new Gson().fromJson(dataStr, cls);
        } catch (JsonSyntaxException jsonSyntaxException) {
            try {
                Class clazz = Class.forName(cls.getName());
                return (T) clazz.newInstance();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Json转泛型Object
     * <p>
     * 如：HttpResult<UserInfo>
     * <p>
     * HttpResult<UserInfo> tmp
     * = JsonParseUtil.parseToGenericObject(dataStr, new TypeReference<HttpResult<UserInfo>>(){})
     */
    public static <T> T parseToGenericObject(String dataStr, GenericType<T> genericType) {
        if (TextUtils.isEmpty(dataStr)) {
            return null;
        }
        try {
            Type type = new TypeToken<GenericType<T>>() {
            }.getType();
            return new Gson().fromJson(dataStr, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseToGenericObject(String dataStr, Type type) {
        if (TextUtils.isEmpty(dataStr)) {
            return null;
        }
        try {
            return new Gson().fromJson(dataStr, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object转Json String
     */
    public static String parseToJson(Object object) {
        return object == null ? "" : new Gson().toJson(object);
    }

    /**
     * Json转单纯的List （dataStr为一个完整Json数组）
     * <p>
     * 如：[{"code":1,"name":"小米"},{"code":2,"name":"大米"}]  -->  List<GoodInfo>
     */
    public static <T> List<T> parseToPureList(String dataStr, Class<T> cls) {
        if (TextUtils.isEmpty(dataStr)) {
            return null;
        }

        try {
            Type collectionType = new TypeToken<List<T>>() {
            }.getType();
            return new Gson().fromJson(dataStr, collectionType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Json转指定Class数组 内涵不确定字段名Json数组
     * <p>
     * 如： { "XXX":[{"Id":1352}] }  -->  List<YYY>
     */
    public static <T> List<T> parseToDynamicList(String dataStr, String fieldName, Class<T> cls) {
        if (TextUtils.isEmpty(dataStr)) {
            return null;
        }
        try {
            JSONObject jsonObj = new JSONObject(dataStr);
            String fieldValue = jsonObj.optString(fieldName);
            return parseToPureList(fieldValue, cls);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Json转指定Class 内涵不确定字段名Json对象
     * <p>
     * 如： { "XXX":{"Id":1352} }  -->  YYY
     */
    public static <T> T parseToDynamicObject(String dataStr, String fieldName, Class<T> cls) {
        if (TextUtils.isEmpty(dataStr)) {
            return null;
        }

        try {
            JSONObject jsonObj = new JSONObject(dataStr);
            String objectData = jsonObj.optString(fieldName);
            return parseToObject(objectData, cls);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static String parseToString(String dataStr, String fieldName) {
        if (TextUtils.isEmpty(dataStr)) {
            return "";
        }
        try {
            JSONObject jsonObj = new JSONObject(dataStr);
            return jsonObj.optString(fieldName);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return "";
        }
    }

    public static <T> String parseToString(List<T> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        try {
            return new Gson().toJson(list);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return "";
        }
    }

    public static int parseToInt(String dataStr, String fieldName) {
        if (TextUtils.isEmpty(dataStr)) {
            return 0;
        }
        try {
            JSONObject jsonObj = new JSONObject(dataStr);
            return jsonObj.optInt(fieldName);
        } catch (JSONException exp) {
            Log.e(TAG, exp.getMessage());
            return 0;
        }
    }


}
