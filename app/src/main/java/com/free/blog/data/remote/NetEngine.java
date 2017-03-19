package com.free.blog.data.remote;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author tangqi on 17-3-14.
 */
public class NetEngine {

    private final IBlogApi mBlogApi;
    private static final NetEngine sInstance = new NetEngine();

    public static NetEngine getInstance() {
        return sInstance;
    }

    private NetEngine() {
        mBlogApi = RetrofitClient.getInstance().getNetApi();
    }

    public Observable<String> getBloggerInfo(String userId) {
        return mBlogApi.getBloggerInfo(userId);
    }

    public Observable<String> getBlogList(String userId, int page) {
        return mBlogApi.getBlogList(userId, page);
    }

    public Observable<String> getCategoryBlogList(String category, int page) {
        return mBlogApi.getCategoryBlogList(category, page);
    }

    public Observable<String> getBlogContent(String url) {
        return mBlogApi.getHtml(url);
    }

    public Observable<String> getBlogComment(String blogId, int page) {
        return mBlogApi.getBlogComment(blogId, page);
    }

    public Observable<String> getColumnList(String keywords, int page) {
        return mBlogApi.getColumnList(keywords, page);
    }

    public static <T> Observable.Transformer<T, T> getErrAndIOSchedulerTransformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.onErrorResumeNext(NetEngine.<T>getErrReturnFunc())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Func1<Throwable, Observable<T>> getErrReturnFunc() {
        return new Func1<Throwable, Observable<T>>() {
            @Override
            public Observable<T> call(Throwable throwable) {
                return Observable.error(throwable);
            }
        };
    }

}
