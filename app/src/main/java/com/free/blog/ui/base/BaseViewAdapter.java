package com.free.blog.ui.base;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author studiotang on 17/3/19
 */
public abstract class BaseViewAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    public BaseViewAdapter() {
        super(0);
    }
}
