package com.wowly.lib.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * <br> ClassName:   PullToRefreshScrollView
 * <br> Description: 自定义下拉刷新 ScrollView
 * <br>
 * <br> Author:     lionszhang
 * <br> Date:        2017/7/12 16:57
 */
public abstract class PtrScrollView extends PtrBaseView {

    private ScrollView mScrollView;

    @Override
    public void onInitMainView() {
    }

    @Override
    public View onInitContent() {
        mScrollView = new ScrollView(getContext());
        mScrollView.setLayoutParams(new ScrollView.LayoutParams(-1, -1));
        return mScrollView;
    }

    public PtrScrollView(Context context) {
        super(context);
        initView();
    }

    public PtrScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        mScrollView.setVerticalFadingEdgeEnabled(false);
        mScrollView.setVerticalScrollBarEnabled(false);

        setDefaultLoadingHeaderView();
        setMode(Mode.REFRESH);
    }


    public void fullScroll(int direction) {
        if (mScrollView != null) {
            mScrollView.fullScroll(direction);
        }

    }

    public void setFillViewport(boolean isFillViewport) {
        if (mScrollView != null) {
            mScrollView.setFillViewport(isFillViewport);
        }
    }

}
