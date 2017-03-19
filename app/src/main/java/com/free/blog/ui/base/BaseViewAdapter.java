package com.free.blog.ui.base;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author studiotang on 17/3/19
 */
public abstract class BaseViewAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K>
        implements View.OnClickListener {

    protected OnItemClickListener mOnItemClickListener;

    public BaseViewAdapter() {
        super(0);
    }

    @Override
    public void onClick(View view) {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}
