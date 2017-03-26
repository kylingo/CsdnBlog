package com.free.blog.ui.home.find.last;

import com.free.blog.library.config.NewBlogManager;
import com.free.blog.library.config.UrlManager;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.vp.menu.MenuRefreshPresenter;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author studiotang on 17/3/26
 */
public class NewBlogPresenter extends MenuRefreshPresenter<List<BlogItem>> implements
        NewBlogContract.Presenter {

    public NewBlogPresenter(NewBlogContract.View<List<BlogItem>, IRefreshPresenter> viewDelegate) {
        super(viewDelegate);
    }

    @Override
    protected Observable<? extends List<BlogItem>> getObservable(int page) {
        return NetEngine.getInstance().getHtmlByPage(mBlogCategory.getLink(), page)
                .map(new Func1<String, List<BlogItem>>() {
                    @Override
                    public List<BlogItem> call(String s) {
                        return JsoupUtils.getHotBlog(s, mBlogCategory.getName());
                    }
                })
                .compose(RxHelper.<List<BlogItem>>getErrAndIOSchedulerTransformer());
    }

    @Override
    protected NewBlogContract.View<List<BlogItem>, IRefreshPresenter> getViewDelegate() {
        return (NewBlogContract.View<List<BlogItem>, IRefreshPresenter>) mViewDelegate;
    }

    @Override
    protected UrlManager getUrlManager() {
        return new NewBlogManager();
    }

    @Override
    protected void updateTitle(String title) {
        getViewDelegate().updateTitle(title);
    }
}
