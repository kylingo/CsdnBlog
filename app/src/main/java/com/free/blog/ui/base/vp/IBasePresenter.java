package com.free.blog.ui.base.vp;

/**
 * @author tangqi on 17-3-20.
 */
public interface IBasePresenter {

    boolean isNetWorkAvailable();

    void subscribe();

    void unSubscribe();
}
