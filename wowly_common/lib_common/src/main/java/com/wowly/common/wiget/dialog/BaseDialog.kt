package com.wowly.common.wiget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View

open class BaseDialog : Dialog {
    /**dialog的布局 */
    @JvmField
    protected var mContentView: View? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}
    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
    }
}