package com.free.blog.ui.list;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.free.blog.R;
import com.free.blog.data.entity.BlogCategory;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.data.entity.Blogger;
import com.free.blog.data.remote.NetEngine;
import com.free.blog.library.config.Config;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.rx.RxSubscriber;
import com.free.blog.library.util.DisplayUtils;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.ui.base.BaseRefreshActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author studiotang on 17/3/19
 */
public class BlogListRxActivity extends BaseRefreshActivity {

    private PopupWindow mPopupWindow;

    private String mUserId;
    private int mPage = 1;
    private Blogger mBlogger;
    private BlogListRxAdapter mAdapter;
    private List<BlogCategory> mBlogCategoryList = new ArrayList<>();
    private String mCategory = Config.BLOG_CATEGORY_ALL;
    private String mCategoryLink;

    @Override
    protected void prepareData() {
        mBlogger = (Blogger) getIntent().getSerializableExtra("blogger");
        mUserId = mBlogger.getUserId();
        mAdapter = new BlogListRxAdapter();
    }

    @Override
    protected String getActionBarTitle() {
        return mBlogger != null ? mBlogger.getTitle() : null;
    }

    @Override
    protected BlogListRxAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void loadData() {
        getBlogListObserver(mPage)
                .compose(RxHelper.<String>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onError(Throwable e) {
                        handleData(null);
                    }

                    @Override
                    public void onNext(String s) {
                        handleData(s);
                    }
                });
    }

    private Observable<String> getBlogListObserver(int page) {
        if (Config.BLOG_CATEGORY_ALL.equals(mCategory)) {
            return NetEngine.getInstance().getBlogList(mUserId, page);
        }

        return NetEngine.getInstance().getCategoryBlogList(mCategoryLink, page);
    }

    private void handleData(String result) {
        if (!TextUtils.isEmpty(result)) {
            List<BlogItem> list = JsoupUtils.getBlogItemList(mCategory, result, mBlogCategoryList);
            mAdapter.setNewData(list);
        }

        onRefreshComplete();
    }

    /**
     * 显示PopWindow
     */
    @Override
    protected void showMenu(View view) {
        if (mPopupWindow == null) {
            getPopupWindow();
        }

        int xOffset = (int) getResources().getDimension(R.dimen.popwindow_bloglist_width) - DisplayUtils.dp2px(this, 40);
        mPopupWindow.showAsDropDown(view, (-1) * xOffset, 0);
    }

    /**
     * 初始化PopupWindow
     */
    private void getPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_bloglist, null);

        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        ListView listView = (ListView) contentView.findViewById(R.id.lv_blog_type);
        BlogCategoryAdapter adapter = new BlogCategoryAdapter(this, mBlogCategoryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();
                if (position == 0) {
                    setActionBarTitle(getActionBarTitle());
                    mCategory = Config.BLOG_CATEGORY_ALL;
                    mCategoryLink = null;

                    mAdapter.setNewData(null);
                    loadData();
                } else {
                    BlogCategory blogCategory = ((BlogCategoryAdapter) parent.getAdapter()).getItem(position);
                    setActionBarTitle(blogCategory.getName());
                    mCategory = blogCategory.getName();
                    mCategoryLink = blogCategory.getLink();

                    mAdapter.setNewData(null);
                    loadData();
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
