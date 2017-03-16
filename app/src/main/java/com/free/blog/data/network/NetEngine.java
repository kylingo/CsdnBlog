package com.free.blog.data.network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author tangqi on 17-3-14.
 */
public class NetEngine {

    private final INetApi mNetApi;
    private static final NetEngine sInstance = new NetEngine();

    public static NetEngine getInstance() {
        return sInstance;
    }

    private NetEngine() {
        mNetApi = RetrofitClient.getInstance().getNetApi();
    }

    public Observable<String> getBloggerInfo(String userId) {
        return mNetApi.getBloggerInfo(userId);
    }

    public Observable<String> getBlogList(String userId, int page) {
        return mNetApi.getBlogList(userId, page);
    }

    public Observable<String> getBlogCategoryList(String category, int page) {
        return mNetApi.getBlogCategoryList(category, page);
    }

    public Observable<String> getBlogContent(String url) {
        return mNetApi.getBlogContent(url);
    }

    public static <T> Observable.Transformer<T, T> getErrAndIOSchedulerTransformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.onErrorResumeNext(NetEngine.<T>getErrRetuunFunc())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Func1<Throwable, Observable<T>> getErrRetuunFunc() {
        return new Func1<Throwable, Observable<T>>() {
            @Override
            public Observable call(Throwable throwable) {
                return Observable.error(throwable);
            }
        };
    }

}
