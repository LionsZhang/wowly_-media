package com.wowly.lib.datamanager.storage;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wowly.lib.datamanager.cryptographic.CryptographicObject;
import com.wowly.lib.datamanager.cryptographic.ICryptographicObject;

/**
 * @author: lionszhang
 * @Filename:
 * @Description:
 * @date: 2017/5/10 10:20
 */

public class StorageObject implements IStorageObject {
    /**
     * 对象加密工具
     */
    private ICryptographicObject cryptographicObject;
    /**
     * SharedPreferences类
     */
    private SharedPreferences sharedPreferences;

    public StorageObject(SharedPreferences sharedPreferences) {
        cryptographicObject = new CryptographicObject();
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void putObject(String key, Object value) {
        String encryptObject = cryptographicObject.encryptObject(value);
        encryptObject = TextUtils.isEmpty(encryptObject) ? "" : encryptObject;
        sharedPreferences.edit().putString(key, encryptObject).apply();
    }

    @Override
    public Object getObject(String key, Object defValue) {
        String context = sharedPreferences.getString(key, "");
        return TextUtils.isEmpty(context) ? defValue : cryptographicObject.decodeObject(context);
    }
}
