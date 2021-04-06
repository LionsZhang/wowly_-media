package com.wowly.lib.pulltorefresh.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.wowly.lib.pulltorefresh.holder.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lionszhang
 * @Filename:
 * @Description:    空RecyclerView.Adapter，修复error“Recycler View..No adapter attached: skipping layout”
 * @date: 2017/2/14 09:43
 */
public class EmptyRecyclerViewAdapter extends RecyclerView.Adapter<CommonViewHolder>{

    private Context mContext;
    private List mData = new ArrayList();

    public EmptyRecyclerViewAdapter(Context context)
    {
        this.mContext = context;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CommonViewHolder holder = new CommonViewHolder(new View(mContext));
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
