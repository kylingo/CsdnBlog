package com.free.blog.ui.home.blogger;

import com.free.blog.BlogApplication;
import com.free.blog.library.config.BloggerManager;
import com.free.blog.library.config.CategoryManager;
import com.free.blog.library.config.Config;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.rx.RxSubscriber;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.model.entity.Blogger;
import com.free.blog.model.local.dao.BloggerDao;
import com.free.blog.model.local.dao.DaoFactory;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.RefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author tangqi on 17-3-23.
 */
class BloggerPresenter extends RefreshPresenter<List<Blogger>> implements
        BloggerContract.Presenter {

    private BloggerDao mBloggerDao;
    private String mType;

    BloggerPresenter(BloggerContract.View<List<Blogger>, IRefreshPresenter> viewDelegate, String type) {
        super(viewDelegate);
        mType = type;
        mBloggerDao = DaoFactory.create().getBloggerDao(BlogApplication.getContext(), type);
    }

    @Override
    protected BloggerContract.View<List<Blogger>, IRefreshPresenter> getViewDelegate() {
        return (BloggerContract.View<List<Blogger>, IRefreshPresenter>) mViewDelegate;
    }

    @Override
    protected Observable<? extends List<Blogger>> getObservable(int page) {
        return Observable.just(page)
                .map(new Func1<Integer, List<Blogger>>() {
                    @Override
                    public List<Blogger> call(Integer page) {
                        BloggerManager.init(BlogApplication.getContext(), mBloggerDao, mType);
                        return mBloggerDao.queryAll();
                    }
                })
                .compose(RxHelper.<List<Blogger>>getErrAndIOSchedulerTransformer());
    }

    @Override
    public void addBlogger(final String userId) {
        Observable.just(userId)
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return mBloggerDao.query(s) == null;
                    }
                })
                .compose(RxHelper.<Boolean>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<Boolean>() {
                    @Override
                    public void onError(Throwable e) {
                        getViewDelegate().addBloggerFailure();
                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            getBlogger(userId);
                        } else {
                            getViewDelegate().addBloggerRepeat();
                        }
                    }
                });
    }

    @Override
    public void deleteBlogger(Blogger blogger) {
        Observable.just(blogger)
                .map(new Func1<Blogger, Boolean>() {
                    @Override
                    public Boolean call(Blogger blogger) {
                        if (blogger != null) {
                            mBloggerDao.delete(blogger);
                            return true;
                        }
                        return false;
                    }
                })
                .compose(RxHelper.<Boolean>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<Boolean>() {
                    @Override
                    public void onError(Throwable e) {
                        getViewDelegate().deleteBloggerFailure();
                    }

                    @Override
                    public void onNext(Boolean result) {
                        getViewDelegate().deleteBloggerSuccess();
                        loadRefreshData();
                    }
                });
    }

    @Override
    public void stickBlogger(Blogger blogger) {
        Observable.just(blogger)
                .map(new Func1<Blogger, Blogger>() {
                    @Override
                    public Blogger call(Blogger blogger) {
                        if (blogger.getIsTop() == 1) {
                            blogger.setIsTop(0);
                        } else {
                            blogger.setIsTop(1);
                        }
                        blogger.setUpdateTime(System.currentTimeMillis());
                        mBloggerDao.insert(blogger);
                        return blogger;
                    }
                })
                .compose(RxHelper.<Blogger>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<Blogger>() {
                    @Override
                    public void onError(Throwable e) {
                        getViewDelegate().stickBloggerFailure();
                    }

                    @Override
                    public void onNext(Blogger blogger) {
                        getViewDelegate().stickBloggerSuccess(blogger);
                        loadRefreshData();
                    }
                });
    }

    private void getBlogger(final String userId) {
        getViewDelegate().addBloggerStart();

        NetEngine.getInstance().getBloggerInfo(userId)
                .map(new Func1<String, Blogger>() {
                    @Override
                    public Blogger call(String html) {
                        Blogger blogger = JsoupUtils.getBlogger(html);
                        initBlogger(blogger, userId);
                        return blogger;
                    }
                })
                .compose(RxHelper.<Blogger>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<Blogger>() {
                    @Override
                    public void onError(Throwable e) {
                        getViewDelegate().addBloggerFailure();
                    }

                    @Override
                    public void onNext(Blogger blogger) {
                        if (blogger != null) {
                            getViewDelegate().addBloggerSuccess(blogger);
                        } else {
                            getViewDelegate().addBloggerFailure();
                        }
                    }
                });
    }

    private void initBlogger(Blogger blogger, String userId) {
        if (blogger != null) {
            blogger.setUserId(userId);
            blogger.setLink(Config.HOST_BLOG + userId);
            blogger.setType(mType);
            String mCategory = CategoryManager.CategoryName.MOBILE;
            blogger.setCategory(mCategory);
            blogger.setIsTop(0);
            blogger.setIsNew(1);
            blogger.setUpdateTime(System.currentTimeMillis());

            mBloggerDao.insert(blogger);
        }
    }
}
