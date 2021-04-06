package com.wowly.home;


/**
@Author Lionszhang
@Date   2021/2/25 16:57
@Name   Init.java
@Instruction 全局初始化类
*/
public class WowlyInit {
    private static WowlyInit mInstance;
    private WowlyInit(){

    }

    /**
    @Author  Lionszhang
    @Date   2021/2/25 17:10
    @Instruction 后续添加应用全局参数配置初始化
    */
    public  void init() {

    }

    public static WowlyInit getInstance() {
        if (mInstance == null) {
            synchronized (WowlyInit.class) {
                if (mInstance == null) {
                    mInstance = new WowlyInit();
                }
            }
        }
        return mInstance;
    }

}
