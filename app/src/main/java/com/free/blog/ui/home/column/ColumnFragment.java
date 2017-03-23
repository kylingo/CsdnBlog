package com.free.blog.ui.home.column;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.free.blog.R;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.model.entity.Channel;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.base.fragment.BaseRefreshFragment;

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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.show(getActivity(), "即将退出，敬请期待");

//        Channel channel = (Channel) adapter.getItem(position);
//        Intent intent = new Intent(getActivity(), ColumnDetailActivity.class);
//        intent.putExtra(ColumnDetailActivity.EXTRA_COLUMN, channel);
//        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
//        final Channel channel = (Channel) adapter.getItem(position);
//        SelectionDialog dialog = new SelectionDialog(getActivity(), "设置【" + channel
//                .getChannelName() + "】为默认频道？");
//        dialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {
//
//            @Override
//            public void onConfirm(String result) {
//                ToastUtil.show(getActivity(), "设置成功");
//                SpfUtils.put(getActivity(), KeyConfig.BLOG_TYPE, channel.getChannelName());
//            }
//        });
//        dialog.show();
        return true;
    }
}
