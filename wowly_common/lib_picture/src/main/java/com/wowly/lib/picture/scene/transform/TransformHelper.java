package com.wowly.lib.picture.scene.transform;


import com.wowly.lib.picture.loader.Monet;

/**
 * <br> ClassName:   TransformHelper
 * <br> Description: 图片变换Helper
 * <br>
 * <br> Author:      lionszhang
 * <br> Date:        2017/9/22 9:29
 */
public class TransformHelper {

    /**
     *<br> Description: 获取Helper
     *<br> Author:      lionszhang
     *<br> Date:        2017/9/22 9:30
     */
    private static ITransform getHelper() {
        switch (Monet.getCurrentStrategy()) {
            case Monet.STRATEGY_GLIDE:
            default:
                return new GlideTransform();
        }
    }

    public static Object fitCenter() {
        return getHelper().fitCenter();
    }

    public static Object centerCrop() {
        return getHelper().centerCrop();
    }

    public static Object centerInside() {
        return getHelper().centerInside();
    }

    public static Object connerCrop(int radius) {
        return getHelper().connerCrop(radius);
    }

    public static Object circleCrop() {
        return getHelper().circleCrop();
    }

    public static Object fitXY() { return getHelper().fitXY(); }
}
