package com.free.blog.ui.list;

import com.free.blog.BlogApplication;
import com.free.blog.data.entity.BlogCategory;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.data.local.dao.BlogItemDao;
import com.free.blog.data.local.dao.DaoFactory;
import com.free.blog.data.remote.NetEngine;
import com.free.blog.library.config.Config;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.rx.RxSubscriber;
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
public class BlogListRxPresenter extends RefreshPresenter<List<BlogItem>>
        implements BlogListRxContract.Presenter {

    private BlogItemDao mBlogItemDao;
    private List<BlogCategory> mCategoryList;
    private String mUserId;
    private String mCategory;
    private String mCategoryLink;

    public BlogListRxPresenter(String userId, String category, IBaseRefreshView<List<BlogItem>,
            IBaseRefreshPresenter> viewDelegate) {
        super(viewDelegate);
        this.mUserId = userId;
        this.mCategory = category;
        this.mBlogItemDao = DaoFactory.getInstance()
                .getBlogItemDao(BlogApplication.getContext(), userId);
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
        queryCategory(mCategoryList);
    }

    @Override
    protected Observable<? extends List<BlogItem>> getObservable(int page) {
        if (isNetWorkAvailable()) {
            return getObservableString(page)
                    .map(new Func1<String, List<BlogItem>>() {
                        @Override
                        public List<BlogItem> call(String s) {
                            List<BlogItem> list = JsoupUtils
                                    .getBlogItemList(mCategory, s, mCategoryList);
                            mBlogItemDao.insertCategory(mCategoryList);
                            mBlogItemDao.insert(mCategory, list);
                            return list;
                        }
                    })
                    .compose(RxHelper.<List<BlogItem>>getErrAndIOSchedulerTransformer());
        }

        return Observable.just(page)
                .map(new Func1<Integer, List<BlogItem>>() {
                    @Override
                    public List<BlogItem> call(Integer page) {
                        return mBlogItemDao.query(mCategory, page);
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

    private void queryCategory(List<BlogCategory> list) {
        Observable.just(list)
                .map(new Func1<List<BlogCategory>, Boolean>() {
                    @Override
                    public Boolean call(List<BlogCategory> blogCategories) {
                        List<BlogCategory> queryList = mBlogItemDao.queryCategory();
                        if (blogCategories != null && queryList != null) {
                            blogCategories.clear();
                            blogCategories.addAll(queryList);
                            return true;
                        }
                        return false;
                    }
                })
                .compose(RxHelper.<Boolean>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<Boolean>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }
}
