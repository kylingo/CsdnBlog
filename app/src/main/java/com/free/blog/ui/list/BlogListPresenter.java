package com.free.blog.ui.list;

import com.free.blog.BlogApplication;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.local.dao.BlogItemDao;
import com.free.blog.model.local.dao.DaoFactory;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.library.config.Config;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.rx.RxSubscriber;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.IRefreshView;
import com.free.blog.ui.base.mvp.refresh.RefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author tangqi on 17-3-20.
 */
class BlogListPresenter extends RefreshPresenter<List<BlogItem>> implements
        BlogListContract.Presenter {

    private BlogItemDao mBlogItemDao;
    private List<BlogCategory> mCategoryList;
    private String mUserId;
    private String mCategory;
    private String mCategoryLink;

    BlogListPresenter(IRefreshView<List<BlogItem>, IRefreshPresenter> viewDelegate,
                      String userId, String category, List<BlogCategory> categoryList) {
        super(viewDelegate);
        mUserId = userId;
        mCategory = category;
        mCategoryList = categoryList;
        mBlogItemDao = DaoFactory.create().getBlogItemDao(BlogApplication.getContext(), userId);
        queryCategory();
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

    private void queryCategory() {
        Observable.just(mBlogItemDao)
                .map(new Func1<BlogItemDao, List<BlogCategory>>() {
                    @Override
                    public List<BlogCategory> call(BlogItemDao blogItemDao) {
                        return blogItemDao.queryCategory();
                    }
                })
                .compose(RxHelper.<List<BlogCategory>>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<List<BlogCategory>>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<BlogCategory> blogCategories) {
                        if (mCategoryList != null && blogCategories != null) {
                            mCategoryList.clear();
                            mCategoryList.addAll(blogCategories);
                        }

                    }
                });
    }
}
