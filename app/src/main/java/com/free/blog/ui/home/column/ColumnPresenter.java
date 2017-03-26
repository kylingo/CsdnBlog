package com.free.blog.ui.home.column;

import com.free.blog.library.config.ColumnManager;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogColumn;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.vp.refresh.RefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author studiotang on 17/3/23
 */
class ColumnPresenter extends RefreshPresenter<List<BlogColumn>> implements
        ColumnContract.Presenter {

    private ColumnManager mColumnManager;
    private BlogCategory mBlogCategory;

    ColumnPresenter(ColumnContract.View<List<BlogColumn>, IRefreshPresenter> viewDelegate) {
        super(viewDelegate);
        mColumnManager = new ColumnManager();
        mBlogCategory = mColumnManager.getType();
    }

    @Override
    protected ColumnContract.View<List<BlogColumn>, IRefreshPresenter> getViewDelegate() {
        return (ColumnContract.View<List<BlogColumn>, IRefreshPresenter>) mViewDelegate;
    }

    @Override
    protected Observable<? extends List<BlogColumn>> getObservable(int page) {
        return NetEngine.getInstance().getHtmlByPage(mBlogCategory.getLink(), page)
                .map(new Func1<String, List<BlogColumn>>() {
                    @Override
                    public List<BlogColumn> call(String s) {
                        return JsoupUtils.getColumnList(s, mBlogCategory.getName());
                    }
                })
                .compose(RxHelper.<List<BlogColumn>>getErrAndIOSchedulerTransformer());
    }

    @Override
    public String getTitle() {
        return mBlogCategory.getName();
    }

    @Override
    public List<BlogCategory> getCategoryList() {
        return mColumnManager.getCategoryList();
    }

    @Override
    public void setType(BlogCategory blogCategory) {
        mBlogCategory = blogCategory;
        mColumnManager.saveType(blogCategory);
        getViewDelegate().updateTitle(blogCategory.getName());
    }
}
