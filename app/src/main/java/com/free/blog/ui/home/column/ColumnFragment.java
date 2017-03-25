package com.free.blog.ui.home.column;


import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.free.blog.R;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.model.entity.Channel;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.base.fragment.BaseRefreshFragment;
import com.free.blog.ui.home.column.detail.ColumnDetailActivity;

import java.util.List;

/**
 * @author studiotang on 17/3/23
 */
public class ColumnFragment extends BaseRefreshFragment<List<Channel>> {

    private String mKeywords = "android";

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.column_default);
    }

    @Override
    protected void beforeInitView() {
        new ColumnPresenter(this, mKeywords);
    }

    @Override
    protected BaseViewAdapter onCreateAdapter() {
        return new ColumnAdapter();
    }

    @Override
    public boolean enableLoadMore() {
        return true;
    }

    @Override
    protected boolean isShowMenu() {
        return false;
    }

    @Override
    protected void showMenu(View view) {
        ToastUtil.show(getActivity(), "即将退出，敬请期待");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.show(getActivity(), "即将退出，敬请期待");

        Channel channel = (Channel) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ColumnDetailActivity.class);
        intent.putExtra(ColumnDetailActivity.EXTRA_COLUMN, channel);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return true;
    }
}
