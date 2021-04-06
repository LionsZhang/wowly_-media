package com.wowly.lib.pulltorefresh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.wowly.lib.pulltorefresh.holder.CommonViewHolder;
import com.wowly.lib.pulltorefresh.holder.ViewHolder;

import java.util.List;

/**
 * @author: lionszhang
 * @Filename:
 * @Description: 封装RecyclerView.Adapter，兼容com.junte.ui.ViewHolder
 * @date: 2017/1/9 11:19
 */
public abstract class
CommonBaseAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mData;
    protected final int mItemLayoutId;


    public CommonBaseAdapter(Context context, List<T> mData, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemLayoutId = itemLayoutId;
        this.mData = mData;
    }

    /**
     * <br> Description: 刷新数据
     * <br> Author:     fangbingran
     * <br> Date:        2017/6/15 18:07
     */
    public void setData(List<T> mData) {
        this.mData = mData;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = getViewHolder(0, null, parent);
        CommonViewHolder myViewHolder = new CommonViewHolder(viewHolder.getConvertView());
        myViewHolder.viewHolder = viewHolder;

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int pos = position + 1;                 //+1，兼容旧版本
                onItemClick(holder.itemView, position);
            }
        });
        holder.viewHolder.setPosition(position);
        convert(holder.viewHolder, mData.get(position), mData, position);
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    protected abstract void convert(ViewHolder holder, T item, List<T> list, int position);

    protected abstract void onItemClick(View itemView, int position);


    @Override
    public int getItemCount() {
        return mData.size();
    }

}
