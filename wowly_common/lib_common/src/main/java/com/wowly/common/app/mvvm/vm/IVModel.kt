package com.wowly.common.app.mvvm.vm

import com.wowly.common.app.mvvm.model.BaseModel

interface IVModel {

    /**
     * 创建M层Model
     */
    fun createModel(): BaseModel?

    /**
     * 初始化原始页面数据
     */
    fun initData()
    /**
     * 与View交互默认方式
     */
    fun toView(action:Int)
}