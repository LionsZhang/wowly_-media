package com.wowly.lib.datamanager.cryptographic;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * @author: lionszhang
 * @Filename:
 * @Description:
 * @Copyright:
 * @date: 2017/5/10 9:24
 */

public class Cryptographic implements ICryptographic {
    private static final String ALGORITHM = "desede";
    private static final String TRANSFORMATION = "desede/ECB/PKCS7Padding";

    @Override
    public byte[] encryptByte(byte[] context, String key) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);//返回转换指定算法的秘密密钥的 SecretKeyFactory 对象
            Key desKey = secretKeyFactory.generateSecret(spec);//根据提供的密钥规范（密钥材料）生成 SecretKey 对象。
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            return cipher.doFinal(context);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public byte[] decodeByte(byte[] context, String key) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            Key desKey = secretKeyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return cipher.doFinal(context);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getKey() {
        return java.util.UUID.randomUUID().toString();
    }
}
