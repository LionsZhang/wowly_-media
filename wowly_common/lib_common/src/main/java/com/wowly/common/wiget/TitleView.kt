package com.wowly.common.wiget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.wowly.common.R


/**
@Author Lionszhang
@Date   2021/3/2 14:54
@Name   TitleView.kt
@Instruction  材料设计风格标题栏封装类
 */
class TitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {
    lateinit var mToolbar: Toolbar
    lateinit var mLeftNav: ImageView
    private lateinit var mTitleCenterView: TextView
    private lateinit var mTitleRight: TextView
    lateinit var mFrameLayout: FrameLayout
    private  var mTitleEventListener: TitleEventListener?=null

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.common_titile_view, this, true)
        mToolbar = findViewById(R.id.title_view)
        mLeftNav = findViewById(R.id.nav_left)
        mTitleCenterView = findViewById(R.id.title)
        mTitleRight = findViewById(R.id.title_right)
        mFrameLayout=findViewById(R.id.title_root)
        setLeftNavMargin(context.resources.getDimensionPixelSize(R.dimen.dp_5))//默认左边距5
        setRightNavMargin(context.resources.getDimensionPixelSize(R.dimen.dp_5))//默认右边距5
        if (attrs != null) {
            var typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
            formatValue(typedArray)
            typedArray.recycle()
        }
        setClickListener()
    }


    private fun setClickListener() {
        mLeftNav.setOnClickListener {
            mTitleEventListener?.onLeftClick()
        }
        mTitleRight.setOnClickListener {
            mTitleEventListener?.onRightClick()
        }
    }

    interface TitleEventListener {
        fun onLeftClick()
        fun onRightClick()
    }

    fun setTitleEventListener(listener: TitleEventListener) {
        mTitleEventListener = listener
    }


    private fun formatValue(typedArray: TypedArray) {
        var title: String? = typedArray.getString(R.styleable.TitleView_title_center)
        var titleRight: String? = typedArray.getString(R.styleable.TitleView_title_right)
        var drawable: Drawable? = typedArray.getDrawable(R.styleable.TitleView_left_drawable)?: context .resources.getDrawable(R.mipmap.common_back)
        mTitleCenterView.text=  title
        mTitleRight.text=  titleRight
        mLeftNav.setImageDrawable(drawable)

    }

    /**
    @Author  Lionszhang
    @Date   2021/3/2 14:57
    @Return mToolbar
    @Instruction 返回mToolbar供自定义标题栏满足材料设计最新特性
     */
    fun getToolBar(): Toolbar {
        return mToolbar
    }
    /**
    @Author  Lionszhang
    @Date   2021/3/2 14:57
    @Return mFrameLayout
    @Instruction mFrameLayout
     */
     fun getTitleRootView():FrameLayout{
         return mFrameLayout
     }
    /**
    @Author  Lionszhang
    @Date   2021/3/2 14:57
    @Instruction 隐藏根标题栏
     */
    fun hideRootTitleView(){
        mFrameLayout.visibility= View.GONE
    }
    /**
    @Author  Lionszhang
    @Date   2021/3/2 14:57
    @Instruction 设置标题
     */
    fun setCenterTitle(title:String){
        mTitleCenterView.text=title

    }
    /**
    @Author  Lionszhang
    @Date   2021/3/2 14:57
    @Instruction 设置左边ICON
     */
    fun setLeftNavIcon( res:Int){
      var drawable= context .resources.getDrawable(res)
        mLeftNav.setImageDrawable(drawable)
    }

    /**
    @Author  Lionszhang
    @Date   2021/3/2 14:57
    @Instruction 设置左边ICON Margin值
     */
   fun setLeftNavMargin(margin:Int){
       var  leftNavParam= mLeftNav.layoutParams as RelativeLayout.LayoutParams
       leftNavParam.leftMargin=margin
        mLeftNav.layoutParams=leftNavParam
   }
    /**
    @Author  Lionszhang
    @Date   2021/3/2 14:57
    @Instruction 设置右边 Margin值
     */
   fun setRightNavMargin(margin:Int){
       var  titleRightParam= mTitleRight.layoutParams as RelativeLayout.LayoutParams
        titleRightParam.rightMargin=margin
        mTitleRight.layoutParams=titleRightParam
   }
    init {
        init(context, attrs)
    }
}