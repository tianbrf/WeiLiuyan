package com.zjf.weike.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-02 上午 11:10
 */

public abstract class CRecyclerViewAdapter<T> extends RecyclerView.Adapter<CRecyclerViewViewHolder> {

    protected List<T> mData;
    protected Context mContext;
    protected int[] mItemLayoutIds;

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    protected View mEmptyView;

    public CRecyclerViewAdapter(Context context, List<T> data, int... itemLayoutIds) {
        this.mContext = context;
        this.mData = data;
        this.mItemLayoutIds = itemLayoutIds;
    }

    /**
     * 设置监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长点击监听
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        mEmptyView.setVisibility(View.GONE);
    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public CRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CRecyclerViewViewHolder holder = CRecyclerViewViewHolder.createViewHolder(mContext, parent, mItemLayoutIds[viewType], viewType);
        setListener(holder);
        return holder;
    }

    private void setListener(final CRecyclerViewViewHolder holder) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, holder, mData.get(position));
                }
            }
        });

        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int position = holder.getAdapterPosition();
                    return mOnItemLongClickListener.onItemLongClick(v, holder, mData.get(position));
                }
                return false;
            }
        });

    }

    @Override
    public void onBindViewHolder(CRecyclerViewViewHolder holder, int position) {
        setConvertView(holder, mData.get(position), holder.getAdapterPosition());
    }

    protected abstract void setConvertView(CRecyclerViewViewHolder holder, T item, int position);


    @Override
    public int getItemCount() {
        int count = mData != null ? mData.size() : 0;
        if (mEmptyView != null) {
            setEmptyViewState(count);
        }
        return count;
    }

    private void setEmptyViewState(int count) {
        if (count == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else{
            ViewPropertyAnimator alpha = mEmptyView.animate()
                    .setDuration(500)
                    .alpha(0);
            alpha.start();
            alpha.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mEmptyView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }


    /**
     * 添加单条数据
     *
     * @param position
     * @param t
     */
    public void addItemData(int position, T t) {
        mData.add(position, t);
        notifyItemInserted(position);
    }

    /**
     * 添加单条数据
     *
     * @param t
     */
    public void addItemData(T t) {
        int size = mData.size();
        mData.add(size, t);
        notifyItemInserted(size);
    }

    /**
     * 移除单条数据
     *
     * @param position
     */
    public void removeItemData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 修改单条数据
     *
     * @param position
     * @param t
     */
    public void changItemData(int position, T t) {
        mData.set(position, t);
        notifyItemChanged(position);
    }

    /**
     * 刷新数据
     *
     * @param newData
     */
    public void flushData(List<T> newData) {
        mData.clear();
        this.addNewData(newData);
    }

    /**
     * 添加新数据集合
     *
     * @param newData
     */
    public void addNewData(List<T> newData) {
        mData.addAll(newData);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener<T> {
        void onItemClick(View view, CRecyclerViewViewHolder holder, T item);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(View view, CRecyclerViewViewHolder holder, T item);
    }

}
