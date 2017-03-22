package com.free.blog.ui.list;

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
import com.free.blog.data.entity.BlogCategory;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.data.entity.Blogger;
import com.free.blog.library.config.Config;
import com.free.blog.library.util.DisplayUtils;
import com.free.blog.ui.base.activity.BaseRefreshActivity;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.detail.BlogContentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author studiotang on 17/3/19
 */
public class BlogListActivity extends BaseRefreshActivity<List<BlogItem>> implements
        BlogListContract.View<List<BlogItem>, IRefreshPresenter> {

    public static final String EXTRA_BLOGGER = "blogger";

    protected BlogListPresenter mPresenter;
    private Blogger mBlogger;
    private String mCategory;
    private List<BlogCategory> mBlogCategoryList;
    private PopupWindow mPopupWindow;

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
    protected BlogListAdapter onCreateAdapter() {
        return new BlogListAdapter();
    }

    @Override
    protected boolean isShowMenu() {
        return true;
    }

    @Override
    public void setPresenter(IRefreshPresenter presenter) {
        super.setPresenter(presenter);
        mPresenter = (BlogListPresenter) presenter;
    }

    @Override
    protected void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        BlogItem item = (BlogItem) adapter.getItem(position);
        Intent i = new Intent();
        i.setClass(this, BlogContentActivity.class);
        i.putExtra(BlogContentActivity.EXTRA_BLOG_ITEM, item);
        startActivity(i);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
    }

    @Override
    protected void showMenu(View view) {
        if (mPopupWindow == null) {
            getPopupWindow();
        }

        int xOffset = (int) getResources().getDimension(R.dimen.popwindow_bloglist_width) - DisplayUtils.dp2px(this, 40);
        mPopupWindow.showAsDropDown(view, (-1) * xOffset, 0);
    }

    @SuppressWarnings("deprecation")
    private void getPopupWindow() {
        @SuppressLint("InflateParams")
        View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_bloglist, null);

        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        ListView listView = (ListView) contentView.findViewById(R.id.lv_blog_type);
        BlogCategoryAdapter adapter = new BlogCategoryAdapter(this, mBlogCategoryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();
                if (position == 0) {
                    setActionBarTitle(getActionBarTitle());
                    mCategory = Config.BLOG_CATEGORY_ALL;
                    mPresenter.setCategoryName(mCategory);
                    mPresenter.setCategoryLink(null);

                    mAdapter.setNewData(null);
                    doRefresh();
                } else {
                    BlogCategory blogCategory = ((BlogCategoryAdapter) parent.getAdapter()).getItem(position);
                    setActionBarTitle(blogCategory.getName());
                    mCategory = blogCategory.getName();
                    mPresenter.setCategoryName(mCategory);
                    mPresenter.setCategoryLink(blogCategory.getLink());

                    mAdapter.setNewData(null);
                    doRefresh();
                }
            }
        });

        contentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }
}
