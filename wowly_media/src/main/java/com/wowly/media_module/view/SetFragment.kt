package com.wowly.media_module.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.wowly.common.app.mvvm.view.BaseFragment
import com.wowly.media_module.R
import com.wowly.media_module.databinding.FragmentSettingBinding
import com.wowly.media_module.vm.SetViewModel
import com.wowly.media_module.vm.WindowViewModel

class SetFragment : BaseFragment<SetViewModel, FragmentSettingBinding>() {

            private val TAG = "SetViewModel"

            companion object {
                private const val Fragment_TYPE = "SetFragment"

                @JvmStatic
                fun newInstance(type: Int): SetFragment {
                    return SetFragment().apply {
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
                return R.layout.fragment_setting
            }

            override fun getViewModelClass(): Class<SetViewModel> {
                return SetViewModel::class.java
            }

            override fun initData() {
                mBinding.lifecycleOwner = this
                mBinding.viewModel = mViewModel
            }

            override fun initView() {
                mViewModel.text.observe(viewLifecycleOwner, Observer {
                    mBinding.textSetting
                            .text = it
                })
            }
        }
