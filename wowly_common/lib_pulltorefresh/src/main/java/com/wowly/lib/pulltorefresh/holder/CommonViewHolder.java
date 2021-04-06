package com.wowly.lib.pulltorefresh.holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: lionszhang
 * @Filename:
 * @Description:    封装RecyclerView.ViewHolder，兼容com.junte.ui.ViewHolder
 * @date: 2017/1/12 15:49
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    public CommonViewHolder(View view) {
        super(view);
    }

    public ViewHolder viewHolder;
}
