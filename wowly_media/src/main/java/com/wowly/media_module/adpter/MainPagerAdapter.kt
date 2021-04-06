package com.wowly.media_module.adpter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wowly.media_module.view.HomeFragment
import com.wowly.media_module.view.HomeFragment.Companion.newInstance
import com.wowly.media_module.view.MainActivity
import com.wowly.media_module.view.SetFragment
import com.wowly.media_module.view.WindowFragment


/**
@Author Lionszhang
@Date   2021/2/27 14:23
@Name   MainPagerAdapter
@Instruction ViewPager 适配器
*/
class MainPagerAdapter(private val mActivity: FragmentActivity) : FragmentStateAdapter(mActivity) {
    companion object {
        const val mHomeFragmentIndex = 0
        const val mWindowFragmentIndex = 1
        const val mSetFragmentIndex = 2
    }
    override fun getItemCount(): Int {
        mActivity as MainActivity
    return mActivity.getSize()
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            mHomeFragmentIndex ->  HomeFragment.newInstance(mHomeFragmentIndex)
            mWindowFragmentIndex ->WindowFragment.newInstance(mWindowFragmentIndex)
            else -> SetFragment.newInstance(mSetFragmentIndex)
        }
}}