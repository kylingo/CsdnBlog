package com.free.blog.library.rx;

import rx.Subscriber;

/**
 * @author studiotang on 17/3/19
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }
}
