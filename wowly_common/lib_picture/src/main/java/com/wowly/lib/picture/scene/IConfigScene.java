package com.wowly.lib.picture.scene;

/**
 * <br> ClassName:   IConfigScene
 * <br> Description: 场景配置接口
 * <br>
 * <br> Author:      lionszhang
 * <br> Date:        2017/7/11 15:15
 */
public interface IConfigScene<T> {
    /**
     *<br> Description: 图片加载参数配置
     *<br> Author:      lionszhang
     *<br> Date:        2017/7/11 15:15
     * @param info
     *                  图片参数
     */
    void config(T info);
}
