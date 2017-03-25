package com.free.blog.ui.home.column;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.free.blog.R;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogColumn;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.base.fragment.BaseRefreshFragment;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.home.column.detail.ColumnDetailActivity;
import com.free.blog.ui.list.BlogCategoryAdapter;

import java.util.List;

/**
 * @author studiotang on 17/3/23
 */
public class ColumnFragment extends BaseRefreshFragment<List<BlogColumn>> implements
        ColumnContract.View<List<BlogColumn>, IRefreshPresenter> {

    private PopupWindow mPopupWindow;

    @Override
    protected String getActionBarTitle() {
        return getPresenter().getTitle();
    }

    @Override
    protected void beforeInitView() {
        new ColumnPresenter(this);
    }

    @Override
    protected BaseViewAdapter onCreateAdapter() {
        return new ColumnAdapter();
    }

    @Override
    public ColumnPresenter getPresenter() {
        return (ColumnPresenter) mPresenter;
    }

    @Override
    public boolean enableLoadMore() {
        return true;
    }

    @Override
    protected boolean isShowMenu() {
        return true;
    }

    @Override
    protected void showMenu(View view) {
        if (mPopupWindow == null) {
            initPopupWindow();
        }

        int xOffset = (int) ((int) getResources().getDimension(R.dimen.popwindow_bloglist_width)
                - getResources().getDimension(R.dimen.share_bar_height));
        mPopupWindow.showAsDropDown(view, (-1) * xOffset, 0);
    }

    @SuppressWarnings("deprecation")
    private void initPopupWindow() {
        @SuppressLint("InflateParams")
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_bloglist, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        ListView listView = (ListView) contentView.findViewById(R.id.lv_blog_type);
        List<BlogCategory> categoryList = getPresenter().getCategoryList();
        BlogCategoryAdapter adapter = new BlogCategoryAdapter(getActivity(), categoryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnCategoryItemClickListener());

        contentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BlogColumn channel = (BlogColumn) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ColumnDetailActivity.class);
        intent.putExtra(ColumnDetailActivity.EXTRA_COLUMN, channel);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return true;
    }

    @Override
    public void updateTitle(String title) {
        setActionBarTitle(title);
    }

    private class OnCategoryItemClickListener implements AdapterView.OnItemClickListener {

        @SuppressWarnings("unchecked")
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            mPopupWindow.dismiss();

            BlogCategory item = (BlogCategory) adapterView.getAdapter().getItem(position);
            getPresenter().setType(item);
            mAdapter.setNewData(null);
            doRefresh();
        }
    }
}
