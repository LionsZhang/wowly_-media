package com.wowly.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.Collections;
import java.util.List;

/**
 * @Author Lionszhang
 * @Date 2021/2/23 15:32
 * @Name AppInitializer .java
 * @Instruction 不需要其它依赖对象的初始化者,后续将移到应用app层初始化所有module
 */
public class AppInitializer implements Initializer<WowlyInit> {
    @NonNull
    @Override
    public WowlyInit create(@NonNull Context context) {
        // TODO: 2021/2/25  1.后续先初始化应用全局配置，2.再按优先顺序初始化各模块

        return WowlyInit.getInstance();

    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }

}
