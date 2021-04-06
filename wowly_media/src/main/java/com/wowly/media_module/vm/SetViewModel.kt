package com.wowly.media_module.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wowly.common.app.mvvm.model.BaseModel
import com.wowly.common.app.mvvm.vm.BaseViewModel

class SetViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is setting Fragment"
    }
    val text: LiveData<String> = _text
    override fun createModel(): BaseModel? {
        return null
    }

    override fun initData() {

    }
}

