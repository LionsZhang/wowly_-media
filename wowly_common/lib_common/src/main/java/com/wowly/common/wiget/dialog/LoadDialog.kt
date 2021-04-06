package com.wowly.common.wiget.dialog

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.wowly.common.R

class LoadDialog @JvmOverloads constructor(context: Context?, attrs: Int = R.style.loading_dialog) :
    Dialog(
        context!!, attrs
    ), IDiaLog {
    private lateinit var loadingIv: ImageView
    private lateinit var animator: ValueAnimator

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_loading_dialog)
        setCanceledOnTouchOutside(false)
        loadingIv = findViewById(R.id.loading_quan_img)
        animator = ObjectAnimator.ofFloat(loadingIv, "rotation", 0f, -360f)
        animator.repeatCount = ValueAnimator.INFINITE //无限循环
        animator.repeatMode = ValueAnimator.RESTART
        animator.interpolator = LinearInterpolator() //不卡顿
        animator.duration = 500
    }

    override fun show() {
        super.show()
        animator.start()
    }

    override fun hide() {
        super.hide()
        animator.end()
    }

    override fun dismiss() {
        super.dismiss()
        animator.end()
    }

    override fun clear() {
        dismiss()
        animator.cancel()
    }
}