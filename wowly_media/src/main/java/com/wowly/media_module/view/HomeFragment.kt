package com.wowly.media_module.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.wowly.common.app.mvvm.view.BaseFragment
import com.wowly.media_module.R
import com.wowly.media_module.databinding.FragmentHomeBinding
import com.wowly.media_module.vm.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val TAG = "HomeFragment"

    companion object {
        private const val Fragment_TYPE = "HomeFragment"

        @JvmStatic
        fun newInstance(type: Int): HomeFragment {
            return HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(Fragment_TYPE, type)
                }
            }
        }
    }

    override fun initParam() {
    }

    override fun responseVM(action: Int) {
    }

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun initData() {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
    }

    override fun initView() {
        mViewModel.text.observe(viewLifecycleOwner, Observer {
            mBinding.textHome
                    .text = it
        })
    }
}