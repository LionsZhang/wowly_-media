package com.wowly.lib.pulltorefresh;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.wowly.lib.pulltorefresh.adapter.EmptyRecyclerViewAdapter;
import com.wowly.lib.pulltorefresh.adapter.HeaderAndFooterWrapper;
import com.wowly.lib.pulltorefresh.decoration.PTRRecyclerViewDecoration;
import com.wowly.lib.pulltorefresh.listener.OnMultiPullListActionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:   PtrMultiTypeRecyclerView
 * Description: 多布局RecyclerView
 * <p>
 * Author:      lionszhang
 * Date:        2018/3/12 11:07
 */
public abstract class PtrMultiRecyclerView<T extends MultiItemEntity> extends PtrBaseView implements PtrBaseView.OnPullBothListener {

    private static final String TAG = "ui-pullToRefresh";
    private final String TIPS_LOAD_DATA = "加载中…";

    protected RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PTRRecyclerViewDecoration myDecoration;
    private List<View> mHeaderViewList;
    private List<View> mFooterViewList;

    protected BaseMultiItemQuickAdapter<T, BaseViewHolder> mMultiAdapter;

    public int loadRefreshId;       //下拉刷新TaskId（可为空）
    public int loadMoreTaskId;      //上拉加载TaskId（可为空）

    public OnMultiPullListActionListener mMultiListener;
    private OnLastPageHintListener mOnLastPageHintListener;

    public List<T> mList = new ArrayList<>();
    public int mTotalCount;
    public int mPageIndex;

    //布局map，key代表type，value代表layout res id
    private SparseIntArray mTypeLayoutMap;

    public PtrMultiRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PtrMultiRecyclerView(Context context) {
        this(context, null);
    }

    public void showAllData(List list) {
        if (mMultiAdapter == null) {
            mMultiAdapter = new MyMultiAdapter(list);

            mMultiAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (mMultiListener != null) {
                        mMultiListener.onItemClick(adapter, view, position);
                    }
                }
            });

            mRecyclerView.setAdapter(getWrappedListAdapter(mMultiAdapter));
        } else {
            getAdapter().notifyDataSetChanged();
        }
    }

    public void initMultiTypeLayout(SparseIntArray typeLayoutMap) {
        mTypeLayoutMap = typeLayoutMap;
    }

    public class MyMultiAdapter extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {
        MyMultiAdapter(List data) {
            super(data);
            if (mTypeLayoutMap != null) {
                for (int i = 0; i < mTypeLayoutMap.size(); i++) {
                    addItemType(mTypeLayoutMap.keyAt(i), mTypeLayoutMap.valueAt(i));
                }
            } else {
                addItemType(-1, R.layout.ptr_default_item);
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            if (mMultiListener != null) {
                mMultiListener.convert(helper, item);
            }
        }
    }

    @Override
    public View onInitContent() {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutParams(new RecyclerView.LayoutParams(-1, -1));
        return mRecyclerView;
    }

    protected void initView() {
        setDefaultLoadingHeaderView();
        setDefaultLoadingFooterView();
        setOnRefreshListener(this);

        mLinearLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(new EmptyRecyclerViewAdapter(getContext()));
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onPullDownToRefresh() {
        loadRefreshData(loadRefreshId, false);
    }

    @Override
    public void onPullUpToRefresh() {
        if (mPageIndex <= mTotalCount) {
            loadMoreData(loadMoreTaskId, false);
        } else {
            if (mOnLastPageHintListener != null) {
                mOnLastPageHintListener.onLastPageHint();
            }

            onRefreshComplete();
            if (mMultiListener != null) {
                mMultiListener.onAllLoadedComplete();
            }
        }
    }

    /**
     * 下拉刷新加载数据
     */
    public void loadRefreshData(int taskId, boolean isShowTops) {
        String tips = isShowTops ? TIPS_LOAD_DATA : "";
        mPageIndex = 1;

        if (mMultiListener != null) {
            mMultiListener.loadData(getId(), taskId, mPageIndex, tips);
        }
    }

    /**
     * 上拉刷新加载更多数据
     */
    public void loadMoreData(int taskId, boolean isShowTops) {
        String tips = isShowTops ? TIPS_LOAD_DATA : "";

        if (mMultiListener != null) {
            mMultiListener.loadData(getId(), taskId, mPageIndex, tips);
        }
    }

    public void setDivider(int padding, int divider) {
        if (padding > 0 && divider >= 0) {
            Drawable _divider = divider != 0 ? getResources().getDrawable(divider) : null;
            myDecoration = new PTRRecyclerViewDecoration(getContext(), PTRRecyclerViewDecoration.VERTICAL_LIST, _divider, (int) getResources().getDimension(padding));
            mRecyclerView.addItemDecoration(myDecoration);

            if (mHeaderViewList != null) {
                myDecoration.hadHeaderCount = mHeaderViewList.size();
            }
        }
    }

    public void setDivider(RecyclerView.ItemDecoration decor) {
        if (mRecyclerView != null && decor != null) {
            mRecyclerView.addItemDecoration(decor);

            if (decor instanceof PTRRecyclerViewDecoration) {
                myDecoration = (PTRRecyclerViewDecoration) decor;
                if (mHeaderViewList != null) {
                    myDecoration.hadHeaderCount = mHeaderViewList.size();
                }
            }
        }
    }

    public void addHeaderView(View headerView) {
        if (mHeaderViewList == null) {
            mHeaderViewList = new ArrayList<>();
        }
        mHeaderViewList.add(headerView);
        if (myDecoration != null) {
            myDecoration.hadHeaderCount = mHeaderViewList.size();
        }
    }

    public void addOtherView(View view) {
        addHeaderView(view);
    }

    public void addFooterView(View footerView) {
        if (mFooterViewList == null) {
            mFooterViewList = new ArrayList<>();
        }
        mFooterViewList.add(footerView);
        if (myDecoration != null) {
            myDecoration.hadFooterCount = mFooterViewList.size();
        }

        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter != null && !(adapter instanceof EmptyRecyclerViewAdapter)) {
            if (adapter instanceof HeaderAndFooterWrapper) {
                ((HeaderAndFooterWrapper) adapter).addFooterView(footerView);
                adapter.notifyDataSetChanged();
            } else {
                HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
                headerAndFooterWrapper.addFooterView(footerView);
                mRecyclerView.setAdapter(headerAndFooterWrapper);
            }
        }
    }

    public void removeFooterView(View footerView) {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter != null && (adapter instanceof HeaderAndFooterWrapper)) {
            ((HeaderAndFooterWrapper) adapter).removeFooterView(footerView);

        }
        if (mFooterViewList != null && mFooterViewList.indexOf(footerView) != -1) {
            mFooterViewList.remove(footerView);
        }
    }

    public void scrollToTop() {
        mLinearLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    public BaseMultiItemQuickAdapter getAdapter() {
        return mMultiAdapter;
    }

    public void setPageIndex(int page) {
        mPageIndex = page;
    }

    public List<T> getList() {
        return mList;
    }

    public void notifyDataSetChanged() {
        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 包装adapter，增加HeaderView，FooterView
     */
    protected RecyclerView.Adapter getWrappedListAdapter(RecyclerView.Adapter adapter) {
        if ((mHeaderViewList != null && mHeaderViewList.size() != 0) || (mFooterViewList != null && mFooterViewList.size() != 0)) {
            HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
            //增加HeaderView
            if (mHeaderViewList != null && mHeaderViewList.size() != 0) {
                headerAndFooterWrapper.addHeaderView(mHeaderViewList);
            }
            //增加FooterView
            if (mFooterViewList != null && mFooterViewList.size() != 0) {
                headerAndFooterWrapper.addFooterView(mFooterViewList);
            }
            return headerAndFooterWrapper;
        }
        return adapter;
    }

    public void setOnPullListActionListener(OnMultiPullListActionListener listener) {
        this.mMultiListener = listener;
    }

    public void setOnLastPageHint(OnLastPageHintListener mLastPageHintListener) {
        mOnLastPageHintListener = mLastPageHintListener;
    }

}
