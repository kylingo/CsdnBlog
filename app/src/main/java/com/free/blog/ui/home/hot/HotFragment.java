package com.free.blog.ui.home.hot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.free.blog.R;
import com.free.blog.ui.base.fragment.BaseFragment;

/**
 * 热门文章
 *
 * @author Frank
 * @since 2015年9月29日下午2:36:45
 */

public class HotFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_column, container, false);
    }
}
