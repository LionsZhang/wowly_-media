package com.wowly.common.app.mvvm.view

interface IView {

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 初始化布局
     */
    fun initView()
    /**
     * 响应VM层的默认处理函数
     */
    fun responseVM(action :Int)
}