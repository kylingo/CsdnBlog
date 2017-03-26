package com.free.blog.ui.home.find.last;

import android.view.View;
import android.widget.AdapterView;

import com.free.blog.library.view.pop.CategoryPopupWindow;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.ui.base.activity.BaseBlogListActivity;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;

import java.util.List;

/**
 * @author studiotang on 17/3/25
 */
public class NewBlogActivity extends BaseBlogListActivity implements
        NewBlogContract.View<List<BlogItem>, IRefreshPresenter> {
    @Override
    protected String getActionBarTitle() {
        return getPresenter().getTitle();
    }

    @Override
    protected void beforeInitView() {
        new NewBlogPresenter(this);
    }

    @Override
    public void updateTitle(String title) {
        setActionBarTitle(title);
    }

    @Override
    protected boolean isShowMenu() {
        return true;
    }

    @Override
    public NewBlogPresenter getPresenter() {
        return (NewBlogPresenter) mPresenter;
    }

    @Override
    protected void showMenu(View view) {
        List<BlogCategory> categoryList = getPresenter().getCategoryList();
        CategoryPopupWindow popupWindow = new CategoryPopupWindow(this, categoryList);
        popupWindow.setOnItemClickListener(new OnCategoryItemClickListener());
        popupWindow.showAsDropDown(view);
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
