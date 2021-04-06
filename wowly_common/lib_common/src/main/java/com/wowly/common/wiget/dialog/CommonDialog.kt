package com.wowly.common.wiget.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.UiThread
import com.wowly.common.R

/**
 * @Author Lionszhang
 * @Date   2021/3/15 10:25
 * @Name   CommonDialog.java
 * @Instruction 通用dialog
 */
class CommonDialog private constructor(builder: Builder) : BaseDialog(
    builder.context!!, builder.styleId
) {

    private val TAG = "CommonDialog"
    private val mBuilder: Builder?
    private val mInflater: LayoutInflater

    companion object {
        val DEFAULT_CONTENT_LAYOUT = R.layout.common_dialog_tips
        const val TEXT_COLOR_DEFAULT = -0xdededf
        private var default_style = R.style.lib_common_tip_dialog_theme

        /**
         * 设置全局默认style
         * @param styleId
         */
        fun setDefaultStyle(styleId: Int) {
            default_style = styleId
        }
    }

    init {
        mBuilder = builder
        mInflater = LayoutInflater.from(context)
        mContentView = mInflater.inflate(DEFAULT_CONTENT_LAYOUT, null)
        init()
        builder.context = null
    }
     fun checkIsActive(): Boolean {
        return when {
            context is Activity && !(context as Activity).isFinishing -> true
            else -> {
                false
            }
        }
    }

    fun setContent(content: String?): CommonDialog {
        mBuilder?.content = content
        if (!TextUtils.isEmpty(mBuilder?.content)) {
            val txtMessage = mContentView?.findViewById<TextView>(R.id.txtMessage)
            txtMessage?.text = mBuilder?.content
            txtMessage?.visibility = View.VISIBLE
        }
        return this
    }

    private fun setOnConfirmListener(listener: SingleButtonListener?): CommonDialog {
        val btn = mContentView?.findViewById<View>(R.id.btnOk)
        btn?.setOnClickListener {
            if (mBuilder?.isAutoDismiss == true) {
                dismiss()
            }
            listener?.onClick(this@CommonDialog, btn, SingleButtonListener.CONFIRM_BUTTON)
        }
        return this
    }

    private fun setOnCancelListener(listener: SingleButtonListener?): CommonDialog {
        val btn = mContentView?.findViewById<View>(R.id.btnCancel)
        btn?.setOnClickListener {
            if (mBuilder?.isAutoDismiss==true) {
                dismiss()
            }
            listener?.onClick(this@CommonDialog, btn, SingleButtonListener.CANCEL_BUTTON)
        }
        return this
    }

    private fun init() {
        val txtTitle = mContentView!!.findViewById<TextView>(R.id.txtTitle)

        // 设置标题栏
        if (!TextUtils.isEmpty(mBuilder!!.title)) {
            if (mBuilder.titleColor != 0) {
                txtTitle.setTextColor(mBuilder.titleColor)
            }
            txtTitle.visibility = View.VISIBLE
            txtTitle.text = mBuilder.title
        }

        // 设置提示内容
        if (!TextUtils.isEmpty(mBuilder.content)) {
            val txtMessage = mContentView?.findViewById<TextView>(R.id.txtMessage)
            val txtMessageScrollView = mContentView?.findViewById<ScrollView>(R.id.svMessage)
            txtMessage?.gravity = mBuilder.getContentGravity()
            txtMessage?.setTextColor(mBuilder.contentColor)
            if (mBuilder.contentTextSize > 0) {
                // 没有设置则用style中的值
                txtMessage?.textSize = mBuilder.contentTextSize
            }
            txtMessage?.text = mBuilder.content
            txtMessage?.visibility = View.VISIBLE
            txtMessageScrollView?.visibility=View.VISIBLE
            if (mBuilder.contentTopDrawable > 0) {
                txtMessage?.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    mBuilder.contentTopDrawable,
                    0,
                    0
                )
            }
            if (mBuilder.contentDrawablePadding > 0) {
                txtMessage?.compoundDrawablePadding = mBuilder.contentDrawablePadding
            }
        }


        // 设置containerView
        if (mBuilder.containerContentViewId != -1) {
            val centerContainer = mContentView?.findViewById<LinearLayout>(R.id.center_container)
            centerContainer?.visibility = View.VISIBLE
            mInflater.inflate(mBuilder.containerContentViewId, centerContainer, true)
        }
        if (mBuilder.containerContentView != null) {
            val centerContainer = mContentView?.findViewById<LinearLayout>(R.id.center_container)
            centerContainer?.visibility = View.VISIBLE
            centerContainer?.addView(mBuilder.containerContentView)
        }

        // 设置左边的按钮
        if (!TextUtils.isEmpty(mBuilder.cancelText)) {
            mBuilder.isCancelFlag = true
            val cancelBtn = mContentView?.findViewById<TextView>(R.id.btnCancel)
            cancelBtn?.text = mBuilder.cancelText
            cancelBtn?.visibility = View.VISIBLE
            if (mBuilder.cancelColor != 0) {
                cancelBtn?.setTextColor(mBuilder.cancelColor)
            }
            if (mBuilder.singleCancelDrawableId != 0) {
                cancelBtn?.setBackgroundResource(mBuilder.singleCancelDrawableId)
            } else {
                if (mBuilder.cancelDrawableId != 0) {
                    cancelBtn?.setBackgroundResource(mBuilder.cancelDrawableId)
                }
            }
            mContentView!!.findViewById<View>(R.id.dialog_lly).visibility = View.VISIBLE
            mContentView!!.findViewById<View>(R.id.line1).visibility =
                View.VISIBLE
            setOnCancelListener(mBuilder.onCancelListener)
        }

        // 设置右边的按钮
        if (!TextUtils.isEmpty(mBuilder.confirmText)) {
            mBuilder.isConfirmFlag = true
            val confirmBtn = mContentView!!.findViewById<TextView>(R.id.btnOk)
            confirmBtn.visibility = View.VISIBLE
            if (mBuilder.confirmColor != 0) {
                confirmBtn.setTextColor(mBuilder.confirmColor)
            }
            confirmBtn.text = mBuilder.confirmText
            if (mBuilder.singleConfirmDrawableId != 0) {
                confirmBtn.setBackgroundResource(mBuilder.singleConfirmDrawableId)
            } else {
                if (mBuilder.confirmDrawableId != 0) {
                    confirmBtn.setBackgroundResource(mBuilder.confirmDrawableId)
                }
            }
            mContentView!!.findViewById<View>(R.id.dialog_lly).visibility = View.VISIBLE
            mContentView!!.findViewById<View>(R.id.line1).visibility = View.VISIBLE
            setOnConfirmListener(mBuilder.onConfirmListener)
        }
        setContentView(mContentView!!)
    }

    class Builder(var context: Context?) {
        // 标题栏
        var title: String? = null


        // 提示正文
        var content: CharSequence? = null
        private var contentGravity = 0

        @DrawableRes
        var contentTopDrawable = 0

        var contentDrawablePadding = 0



        // 左边的按钮
        var cancelText: String? = null

        @DrawableRes
        var cancelColor = 0

        // 右边的按钮
        var confirmText: String? = null

        @DrawableRes
        var confirmColor = 0
        var isConfirmFlag = false
        var isCancelFlag = false

        @DrawableRes
        private var mStyleId = 0

        @ColorInt
        private var mTitleColor = 0

        @DrawableRes
        private var mContentColor = 0
        var contentTextSize = 0f

        @DrawableRes
        var cancelDrawableId = 0

        @DrawableRes
        var singleCancelDrawableId = 0

        @DrawableRes
        var confirmDrawableId = 0

        @DrawableRes
        var singleConfirmDrawableId = 0

        /**中间内容自定义弹窗布局Id */
        @DrawableRes
        private var mContainerContentViewId = 0

        /**中间内容自定义弹窗VIEW */
        private var mContainerContentView: View? = null
        var onConfirmListener: SingleButtonListener? = null
        var onCancelListener: SingleButtonListener? = null

        /**
         * ture 点击按钮后自动关闭  false 不关闭
         */
        var isAutoDismiss = true
        fun setAutoDismiss(autoDismiss: Boolean): Builder {
            isAutoDismiss = autoDismiss
            return this
        }

        fun setOnConfirmListener(listener: SingleButtonListener?): Builder {
            onConfirmListener = listener
            return this
        }

        fun setOnCancelListener(listener: SingleButtonListener?): Builder {
            onCancelListener = listener
            return this
        }

        fun setOnClickListener(listener: SingleButtonListener?): Builder {
            onConfirmListener = listener
            onCancelListener = listener
            return this
        }

        fun setConfirmText(confirmText: String?): Builder {
            this.confirmText = confirmText
            return this
        }

        fun setCancelText(cancelText: String?): Builder {
            this.cancelText = cancelText
            return this
        }


        fun setConfirmFlag(confirmFlag: Boolean): Builder {
            isConfirmFlag = confirmFlag
            return this
        }

        fun setCancelFlag(cancelFlag: Boolean): Builder {
            isCancelFlag = cancelFlag
            return this
        }

        val titleColor: Int
            get() = if (mTitleColor == 0) {
                TEXT_COLOR_DEFAULT
            } else mTitleColor

        fun setTitleColor(@ColorInt titleColor: Int): Builder {
            mTitleColor = titleColor
            return this
        }

        val contentColor: Int
            get() = if (mContentColor == 0) {
                TEXT_COLOR_DEFAULT
            } else mContentColor

        fun setContentColor(contentColor: Int): Builder {
            mContentColor = contentColor
            return this
        }

        fun setContentTextSize(contentTextSize: Float): Builder {
            this.contentTextSize = contentTextSize
            return this
        }

        fun setCancelColor(cancelColor: Int): Builder {
            this.cancelColor = cancelColor
            return this
        }

        fun setConfirmColor(confirmColor: Int): Builder {
            this.confirmColor = confirmColor
            return this
        }

        fun setCancelDrawableId(cancelDrawableId: Int): Builder {
            this.cancelDrawableId = cancelDrawableId
            return this
        }

        fun setSingleCancelDrawableId(singleCancelDrawableId: Int): Builder {
            this.singleCancelDrawableId = singleCancelDrawableId
            return this
        }

        fun setConfirmDrawableId(confirmDrawableId: Int): Builder {
            this.confirmDrawableId = confirmDrawableId
            return this
        }

        fun setSingleConfirmDrawableId(singleConfirmDrawableId: Int): Builder {
            this.singleConfirmDrawableId = singleConfirmDrawableId
            return this
        }

        @get:SuppressLint("ResourceType")
        val styleId: Int
            get() = if (mStyleId > 0) {
                mStyleId
            } else {
                default_style
            }

        fun setStyleId(styleId: Int): Builder {
            mStyleId = styleId
            return this
        }

        @get:SuppressLint("ResourceType")
        val containerContentViewId: Int
            get() = if (mContainerContentViewId > 0) {
                mContainerContentViewId
            } else {
                -1
            }

        fun setContainerContentViewId(containerContentViewId: Int): Builder {
            mContainerContentViewId = containerContentViewId
            return this
        }

        @get:SuppressLint("ResourceType")
        val containerContentView: View?
            get() = if (mContainerContentView != null) {
                mContainerContentView
            } else {
                null
            }

        fun setContainerContentView(containerContentView: View?): Builder {
            mContainerContentView = containerContentView
            return this
        }

        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setContent(content: CharSequence?): Builder {
            this.content = content
            return this
        }

        fun getContentGravity(): Int {
            return if (contentGravity <= 0) {
                Gravity.CENTER
            } else contentGravity
        }

        fun setContentGravity(contentGravity: Int): Builder {
            this.contentGravity = contentGravity
            return this
        }

        fun setContentTopDrawable(contentTopDrawable: Int): Builder {
            this.contentTopDrawable = contentTopDrawable
            return this
        }

        fun setContentDrawablePadding(contentDrawablePadding: Int): Builder {
            this.contentDrawablePadding = contentDrawablePadding
            return this
        }

        @UiThread
        fun build(): CommonDialog {
            return CommonDialog(this)
        }

        @UiThread
        fun show(): CommonDialog {
            val dialog = build()
            dialog.show()
            return dialog
        }
    }


}