package com.wowly.lib.pulltorefresh.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * ClassName:   OnMultiPullListActionListener
 * Description: 集成OnPullListActionListener的主要原因是沿用之前的Action逻辑，以相同的API对外提供
 * <p>
 * Author:      lionszhang
 * Date:        2018/3/12 18:08
 */
public interface OnMultiPullListActionListener extends OnPullListActionListener {
    void convert(BaseViewHolder helper, Object item);
    void onAllLoadedComplete();
    void onItemClick(BaseQuickAdapter adapter, View view, int position);
}
