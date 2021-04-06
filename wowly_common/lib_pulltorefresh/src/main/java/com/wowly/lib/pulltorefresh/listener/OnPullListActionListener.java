package com.wowly.lib.pulltorefresh.listener;

import com.wowly.lib.pulltorefresh.holder.ViewHolder;

import java.util.List;

public interface OnPullListActionListener<T> {
	
	void loadData(int viewId, int taskId, int pageIndex, String tips);
	void clickItem(int viewId, T item, int position);
	void createListItem(int viewId, ViewHolder holder, T currentItem, List<T> list, int position);
	void onRefreshComplete();

}
