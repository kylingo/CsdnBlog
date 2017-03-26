package com.free.blog.ui.home.find;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.model.entity.BlogColumn;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.activity.WebActivity;
import com.free.blog.ui.base.fragment.BaseFragment;
import com.free.blog.ui.home.column.detail.ColumnDetailActivity;
import com.free.blog.ui.home.find.expert.DailyActivity;
import com.free.blog.ui.home.find.hot.HotBlogActivity;
import com.free.blog.ui.home.find.last.LastBlogActivity;
import com.free.blog.ui.home.find.pk.PkActivity;
import com.free.blog.ui.home.find.rank.RankActivity;

/**
 * 发现
 *
 * @author tangqi
 * @since 2015年8月9日上午11:08:13
 */
public class FindFragment extends BaseFragment implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView mTitleView = (TextView) view.findViewById(R.id.tv_title);
        mTitleView.setText(getString(R.string.find));

        LinearLayout mFindView01 = (LinearLayout) view.findViewById(R.id.ll_find_01);
        LinearLayout mFindView02 = (LinearLayout) view.findViewById(R.id.ll_find_02);
        LinearLayout mFindView03 = (LinearLayout) view.findViewById(R.id.ll_find_03);
        LinearLayout mFindView04 = (LinearLayout) view.findViewById(R.id.ll_find_04);
        LinearLayout mFindView05 = (LinearLayout) view.findViewById(R.id.ll_find_05);

        mFindView01.setOnClickListener(this);
        mFindView02.setOnClickListener(this);
        mFindView03.setOnClickListener(this);
        mFindView04.setOnClickListener(this);
        mFindView05.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.ll_find_01:
                intent = new Intent(getActivity(), DailyActivity.class);
                BlogColumn blogColumn = new BlogColumn();
                blogColumn.setName(getString(R.string.blog_daily));
                blogColumn.setUrl(NetEngine.getInstance().getBlogDaily());
                intent.putExtra(ColumnDetailActivity.EXTRA_BLOG_COLUMN, blogColumn);
                break;

            case R.id.ll_find_02:
                intent = new Intent(getActivity(), RankActivity.class);
                break;

            case R.id.ll_find_03:
                intent = new Intent(getActivity(), PkActivity.class);
                intent.putExtra(WebActivity.NAME, getString(R.string.blog_pk));
                intent.putExtra(WebActivity.LINK, NetEngine.getInstance().getBlogPkUrl());
                break;

            case R.id.ll_find_04:
                intent = new Intent(getActivity(), LastBlogActivity.class);
                break;

            case R.id.ll_find_05:
                intent = new Intent(getActivity(), HotBlogActivity.class);
                break;

            default:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            ToastUtil.show(getActivity(), "即将推出");
        }
    }
}
