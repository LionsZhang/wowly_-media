package com.wowly.lib.pulltorefresh.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.wowly.lib.pulltorefresh.holder.ViewHolder;

import java.util.List;

/**
 * ClassName:   DefaultMultiPtrActionListener
 * Description: 默认的多布局Pull Action实现类
 * <p>
 * Author:      lionszhang
 * Date:        2018/3/12 18:07
 */
public class DefaultMultiPtrActionListener implements OnMultiPullListActionListener {
    @Override
    public void loadData(int viewId, int taskId, int pageIndex, String tips) {

    }

    @Override
    public void clickItem(int viewId, Object item, int position) {

    }

    @Override
    public void createListItem(int viewId, ViewHolder holder, Object currentItem, List list, int position) {

    }

    @Override
    public void onRefreshComplete() {

    }

    @Override
    public void convert(BaseViewHolder helper, Object item) {

    }

    @Override
    public void onAllLoadedComplete() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
