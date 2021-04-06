package com.wowly.media_module.view

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wowly.common.app.mvvm.view.BaseActivity
import com.wowly.common.global.ARouterUrl
import com.wowly.media_module.R
import com.wowly.media_module.databinding.ActivityMainBinding
import com.wowly.media_module.vm.MainViewModel
import com.wowly.media_module.adpter.MainPagerAdapter

/**
@Author Lionszhang
@Date   2021/2/26 14:17
@Name   RegisterActivity.kt
@Instruction 注册界面
 */

private val TAB_TITLES = arrayOf(
    "Home", "Window", "Setting"
)
private val mIconActivate = intArrayOf(
        R.mipmap.home_icon_home_on,
        R.mipmap.home_icon_window_on,
        R.mipmap.home_icon_set_on
)
@Route(path = ARouterUrl.ROUTE_MAIN_MODULE_MAIN_ACTIVITY)
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    lateinit var mMainPagerAdapter: MainPagerAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun isOpenStatusBar(): Boolean = true

    override fun initData() {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
    }

    fun getSize(): Int {
        return TAB_TITLES.size
    }

    private fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }


    override fun initView() {
        mMainPagerAdapter = MainPagerAdapter(this)
        mBinding.viewPager.adapter = mMainPagerAdapter
        TabLayoutMediator( mBinding.tabs, mBinding.viewPager) { tab, position ->
            tab.setIcon(mIconActivate[position])
        }.attach()
        mBinding.tabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    override fun responseVM(action: Int) {
        Log.e("ACTIVITY", "responseVM>>action=$action")
    }

    override fun isActivityWithFragment(): Boolean {
        return true
    }
}

