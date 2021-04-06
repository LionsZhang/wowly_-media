package com.wowly.lib.datamanager.cryptographic;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author: lionszhang
 * @Filename:
 * @Description:
 * @date: 2017/5/10 9:25
 */

public class CryptographicString extends Cryptographic implements ICryptographicString {
    /**
     * 密钥与密文组装工具
     */
    private IContextDecorate contextDecorate;

    public CryptographicString() {
        contextDecorate = new ContextDecorate();
    }

    /**
     * 加密字符串
     *
     * @param context 待加密字符串
     * @return 加密后字符串
     */
    @Override
    public String encryptString(String context) {
        if (TextUtils.isEmpty(context)) return null;
        try {
            String key = getKey();
            String cipherText = new String(Base64.encode(encryptByte(context.getBytes("UTF-8"), key), Base64.DEFAULT), "UTF-8");
            return contextDecorate.getDecorateContext(key, cipherText);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 解密字符串
     *
     * @param context 待解密字符串
     * @return 已解密字符串
     */
    @Override
    public String decodeString(String context) {
        String key = contextDecorate.getKey(context);
        String cipherText = contextDecorate.getCipherText(context);
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(cipherText)) {
            return context;
        }
        try {
            return new String(decodeByte(Base64.decode(cipherText.getBytes("UTF-8"), Base64.DEFAULT), key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
