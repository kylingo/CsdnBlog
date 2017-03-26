package com.free.blog.ui.home.column;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.free.blog.library.view.pop.CategoryPopupWindow;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogColumn;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.base.fragment.BaseRefreshFragment;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.home.column.detail.ColumnDetailActivity;

import java.util.List;

/**
 * @author studiotang on 17/3/23
 */
public class ColumnFragment extends BaseRefreshFragment<List<BlogColumn>> implements
        ColumnContract.View<List<BlogColumn>, IRefreshPresenter> {

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
        List<BlogCategory> categoryList = getPresenter().getCategoryList();
        CategoryPopupWindow popupWindow = new CategoryPopupWindow(getActivity(), categoryList);
        popupWindow.setOnItemClickListener(new OnCategoryItemClickListener());
        popupWindow.showAsDropDown(view);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BlogColumn channel = (BlogColumn) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ColumnDetailActivity.class);
        intent.putExtra(ColumnDetailActivity.EXTRA_BLOG_COLUMN, channel);
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

            BlogCategory item = (BlogCategory) adapterView.getAdapter().getItem(position);
            getPresenter().setType(item);
            mAdapter.setNewData(null);
            doRefresh();
        }
    }
}
