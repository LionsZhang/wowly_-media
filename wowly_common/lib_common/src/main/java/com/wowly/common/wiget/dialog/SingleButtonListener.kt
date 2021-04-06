package com.wowly.common.wiget.dialog

import android.view.View

interface SingleButtonListener {
    fun onClick(dialog: BaseDialog?, btn: View?, which: Int)

    companion object {
        const val CANCEL_BUTTON = 1
        const val CONFIRM_BUTTON = 2
    }
}