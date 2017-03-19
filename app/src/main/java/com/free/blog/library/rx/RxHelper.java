package com.free.blog.library.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author studiotang on 17/3/19
 */
public class RxHelper {

    public static <T> Observable.Transformer<T, T> getErrAndIOSchedulerTransformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.onErrorResumeNext(RxHelper.<T>getErrReturnFunc())
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
