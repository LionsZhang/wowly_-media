package com.wowly.common.util;

import android.widget.Toast;

import com.wowly.common.app.BaseApplication;


public class ToastUtil {

    public static void toastWithShortTime(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastWithShortTime(int stringId) {
        Toast.makeText(BaseApplication.getContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    public static void toastWithLongTime(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void toastWithLongTime(int stringId) {
        Toast.makeText(BaseApplication.getContext(), stringId, Toast.LENGTH_LONG).show();
    }

    public static void toastWithCustomerTime(int stringId, int time) {
        Toast.makeText(BaseApplication.getContext(), stringId, time).show();
    }

    public static void toastWithCustomerTime(String msg, int time) {
        Toast.makeText(BaseApplication.getContext(), msg, time).show();
    }
}
