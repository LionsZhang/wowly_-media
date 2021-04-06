package com.wowly.lib.picture.compress.encode;

import android.util.Base64;



import java.io.ByteArrayOutputStream;

/**
 * <br> ClassName:   DefaultCodeProcessor
 * <br> Description: 编码工具类
 * <br>
 * <br> Author:      lionszhang
 * <br> Date:        2017/5/23 16:49
 */

public class DefaultCodeProcessor implements ICodeProcessor {
    @Override
    public String encryptCode(ByteArrayOutputStream baos) {
        String encryption = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        LoggerManager.d("compress", "encryption size : " + encryption.length() / 1024 + " Kb");
        return encryption;
    }
}
