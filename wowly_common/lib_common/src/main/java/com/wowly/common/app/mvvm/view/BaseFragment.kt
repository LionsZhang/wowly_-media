package com.wowly.common.app.mvvm.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.wowly.common.R
import com.wowly.common.app.mvvm.vm.BaseViewModel
import com.wowly.common.util.statusbar.StatusBarUtil
import com.wowly.common.wiget.dialog.LoadDialog


/**
@Author Lionszhang
@Date   2021/2/25 16:36
@Name   BaseFragment.kt
@Instruction MVVM层Fragment基类，所有Fragment子界面应当继承
 */
abstract class BaseFragment<V : BaseViewModel, B : ViewDataBinding> : Fragment(), IView {

    lateinit var mBinding: B
    lateinit var mViewModel: V
    private var loadingProgressDialog: LoadDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }


    /**参数初始化*/

    abstract fun initParam()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        mViewModel = createViewModel(getViewModelClass(), true)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatusBar()
        addLiveDataObserve()
        initData()
        initView()
    }

    /**
    @Author  Lionszhang
    @Date   2021/2/24 15:06
    @Return  返回Activity对应的ViewModel
     */
    private fun createViewModel(clazz: Class<V>, isShareViewModel: Boolean): V {
        return  ViewModelProvider(if (isShareViewModel) requireActivity() else this).get(clazz)
    }


    private fun addLiveDataObserve() {
        mViewModel.mHintInfoLiveData.observe(viewLifecycleOwner) {
            Log.e("BaseFragment", "addLiveDataObserve>>mHintInfoLiveData")
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        /**VM层与V层交互默认处理方式*/

        mViewModel.mNavLiveDate.observe(viewLifecycleOwner) {
            Log.e("BaseFragment", "addLiveDataObserve>>action=$it")
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

    /**
     * 初始化状态栏
     */
    private fun initStatusBar() {
        if (isOpenStatusBar()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                StatusBarUtil.setStatusBarColor(activity, resources.getColor(getStatusBarColor(), null))
            } else {
                StatusBarUtil.setStatusBarColor(activity, resources.getColor(getStatusBarColor()))
            }
            StatusBarUtil.setStatusBarDarkTheme(activity, true)
        }
    }

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
    @Date   2021/3/8 16:09
    @Param action 动作常量
    @Instruction  对VM层的默认响应函数
    */
    abstract override fun responseVM(action: Int)


    abstract fun getLayout(): Int

    /**
     * 获取ViewModel的class
     */
    abstract fun getViewModelClass(): Class<V>

 private   fun getLoadingProgressDialog(): LoadDialog? {
        if (!activity?.isFinishing!!) {
            if (loadingProgressDialog == null) {
                loadingProgressDialog = LoadDialog(activity)
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





}