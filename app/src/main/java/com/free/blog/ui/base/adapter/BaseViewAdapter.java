package com.free.blog.ui.base.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * @author studiotang on 17/3/19
 */
public abstract class BaseViewAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public BaseViewAdapter() {
        this(0);
    }

    public BaseViewAdapter(int layoutResId) {
        super(layoutResId);
    }
}
