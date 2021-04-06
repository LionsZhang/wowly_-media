package com.wowly.common.app.mvvm.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe

import com.wowly.common.R
import com.wowly.common.app.mvvm.vm.BaseViewModel
import com.wowly.common.util.ToastUtil
import com.wowly.common.util.statusbar.StatusBarUtil
import com.wowly.common.wiget.dialog.LoadDialog

/**
@Author Lionszhang
@Date   2021/2/25 16:36
@Name   BaseActivity.kt
@Instruction MVVM层Activity基类，所有Activity子界面应当继承
 */
abstract class BaseActivity<V : BaseViewModel, B : ViewDataBinding> : AppCompatActivity(),IView {

    lateinit var mBinding: B
    lateinit var mViewModel: V
    private var loadingProgressDialog: LoadDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = createViewModel(getViewModelClass())
        initBaseData(isActivityWithFragment())
        initData()
        initView()
        initStatusBar()
    }

    /**
    @Author  Lionszhang
    @Date   2021/2/24 15:06
    @Return  返回Activity对应的ViewModel
     */
    private fun createViewModel(clazz: Class<V>): V {
        return ViewModelProvider(this).get(clazz)
    }

    /**
    @Author  Lionszhang
    @Date   2021/3/9 16:31
    @Param  Activity有无Fragment
    @Instruction 默认Activity无Fragment  如果是Activity有Fragment 子类重写设置为 ture 避免Fragment和Activity重复注册观察者重复响应
    */

    private fun initBaseData(isActivityWithFragment: Boolean) {
        if (!isActivityWithFragment){
            mViewModel.mHintInfoLiveData.observe(this) {
                ToastUtil.toastWithLongTime(it)
            }
            /**VM层与V层交互默认处理方式*/
            mViewModel.mNavLiveDate.observe(this) {
                when (it) {
                    BaseViewModel.ACTION_SHOW_DIALOG -> {
                        getLoadingProgressDialog()?.show()
                    }
                    BaseViewModel.ACTION_HIDE_DIALOG -> {
                        hideLoadingDialog()
                    }
                    else -> {
                        responseVM(it)
                    }
                }

            }
        }
    }


    /**
    @Author  Lionszhang
    @Date   2021/3/8 16:09
    @Param action 动作常量
    @Instruction  对VM层的默认响应函数
     */
    abstract override fun responseVM(action: Int)
    /**
     * 初始化状态栏
     */
    private fun initStatusBar() {
        if (isOpenStatusBar()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                StatusBarUtil.setStatusBarColor(this, resources.getColor(getStatusBarColor(), null))
            } else {
                StatusBarUtil.setStatusBarColor(this, resources.getColor(getStatusBarColor()))
            }
            StatusBarUtil.setStatusBarDarkTheme(this, true)
        }
    }

    /**
     * 获取activity布局id
     */
    abstract fun getLayoutId(): Int

    /**
     * 获取ViewModel的class
     */
    abstract fun getViewModelClass(): Class<V>

    /**
     * 是否启动沉浸式状态栏
     */
    open fun isOpenStatusBar(): Boolean {
        return false
    }

    /**
     * 获取状态栏颜色
     */
    open fun getStatusBarColor(): Int {
        return R.color.common_white
    }
    /**
    @Author  Lionszhang
    @Date   2021/3/9 16:27
    @Return  Activity是否有Fragment
    @Instruction 默认Activity无Fragment  如果是Activity有Fragment 子类重写设置为 ture
    */
    open fun isActivityWithFragment():Boolean {
        return false
    }

  private  fun getLoadingProgressDialog(): LoadDialog? {
        if (!isFinishing) {
            if (loadingProgressDialog == null) {
                loadingProgressDialog = LoadDialog(this)
            }
        }
        return loadingProgressDialog
    }

 private fun hideLoadingDialog() {
        if (loadingProgressDialog != null && loadingProgressDialog!!.isShowing) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                loadingProgressDialog!!.dismiss()
            } else {
                loadingProgressDialog!!.hide()
            }
        }
    }

    override fun onDestroy() {
        if (loadingProgressDialog != null) {
            loadingProgressDialog!!.clear()
        }
        super.onDestroy()
    }
}