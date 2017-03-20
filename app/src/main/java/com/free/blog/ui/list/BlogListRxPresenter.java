package com.free.blog.ui.list;

import com.free.blog.data.entity.BlogCategory;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.data.remote.NetEngine;
import com.free.blog.library.config.Config;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.ui.base.mvp.IBaseRefreshPresenter;
import com.free.blog.ui.base.mvp.IBaseRefreshView;
import com.free.blog.ui.base.mvp.RefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author tangqi on 17-3-20.
 */
public class BlogListRxPresenter extends RefreshPresenter<List<BlogItem>> implements BlogListRxContract.Presenter{

    private List<BlogCategory> mCategoryList;
    private String mUserId;
    private String mCategory;
    private String mCategoryLink;

    public BlogListRxPresenter(String userId, String category, IBaseRefreshView<List<BlogItem>, IBaseRefreshPresenter> viewDelegate) {
        super(viewDelegate);
        this.mUserId = userId;
        this.mCategory = category;
    }

    @Override
    public void setCategoryName(String category) {
        mCategory = category;
    }

    @Override
    public void setCategoryLink(String categoryLink) {
        mCategoryLink = categoryLink;
    }

    @Override
    public void setCategoryList(List<BlogCategory> categoryList) {
        mCategoryList = categoryList;
    }

    @Override
    protected Observable<? extends List<BlogItem>> getObservable(int page) {
        return getObservableString(page)
                .map(new Func1<String, List<BlogItem>>() {
                    @Override
                    public List<BlogItem> call(String s) {
                        return JsoupUtils.getBlogItemList(mCategory, s, mCategoryList);
                    }
                })
                .compose(RxHelper.<List<BlogItem>>getErrAndIOSchedulerTransformer());
    }

    private Observable<String> getObservableString(int page) {
        if (Config.BLOG_CATEGORY_ALL.equals(mCategory)) {
            return NetEngine.getInstance().getBlogList(mUserId, page);
        }

        return NetEngine.getInstance().getCategoryBlogList(mCategoryLink, page);
    }


}
