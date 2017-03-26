package com.free.blog.ui.list;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.free.blog.library.config.Config;
import com.free.blog.library.view.pop.CategoryPopupWindow;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.entity.Blogger;
import com.free.blog.ui.base.activity.BaseBlogListActivity;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author studiotang on 17/3/19
 */
public class BlogListActivity extends BaseBlogListActivity implements
        BlogListContract.View<List<BlogItem>, IRefreshPresenter> {

    public static final String EXTRA_BLOGGER = "blogger";

    private Blogger mBlogger;
    private String mCategory;
    private List<BlogCategory> mBlogCategoryList;

    @Override
    protected String getActionBarTitle() {
        return mBlogger != null ? mBlogger.getTitle() : null;
    }

    @Override
    protected void beforeInitView() {
        mBlogger = (Blogger) getIntent().getSerializableExtra(EXTRA_BLOGGER);
        mBlogCategoryList = new ArrayList<>();
        mCategory = Config.BLOG_CATEGORY_ALL;
        new BlogListPresenter(this, mBlogger.getUserId(), mCategory, mBlogCategoryList);
    }

    @Override
    protected boolean isShowMenu() {
        return true;
    }

    @Override
    public BlogListPresenter getPresenter() {
        return (BlogListPresenter) mPresenter;
    }

    @Override
    protected boolean hasMore(List<BlogItem> blogItems) {
        if (blogItems != null && blogItems.size() > 0) {
            BlogItem blogItem = blogItems.get(blogItems.size() - 1);
            return mPresenter.hasMore(blogItem.getTotalPage());
        }
        return false;
    }

    @Override
    protected void showMenu(View view) {
        CategoryPopupWindow popupWindow = new CategoryPopupWindow(this, mBlogCategoryList);
        popupWindow.setOnItemClickListener(new OnCategoryItemClickListener());
        popupWindow.showAsDropDown(view);
    }

    private class OnCategoryItemClickListener implements OnItemClickListener {

        @SuppressWarnings("unchecked")
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (position == 0) {
                setActionBarTitle(getActionBarTitle());
                mCategory = Config.BLOG_CATEGORY_ALL;
                getPresenter().setCategoryName(mCategory);
                getPresenter().setCategoryLink(null);

                mAdapter.setNewData(null);
                doRefresh();
            } else {
                BlogCategory blogCategory = ((BlogCategoryAdapter) adapterView.getAdapter()).getItem(position);
                setActionBarTitle(blogCategory.getName());
                mCategory = blogCategory.getName();
                getPresenter().setCategoryName(mCategory);
                getPresenter().setCategoryLink(blogCategory.getLink());

                mAdapter.setNewData(null);
                doRefresh();
            }
        }
    }
}
