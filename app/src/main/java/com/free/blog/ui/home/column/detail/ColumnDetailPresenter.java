package com.free.blog.ui.home.column.detail;

import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.entity.BlogColumn;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.vp.refresh.IRefreshView;
import com.free.blog.ui.base.vp.refresh.RefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author studiotang on 17/3/24
 */
class ColumnDetailPresenter extends RefreshPresenter<List<BlogItem>> {
    private BlogColumn mBlogColumn;

    ColumnDetailPresenter(IRefreshView<List<BlogItem>, IRefreshPresenter> viewDelegate,
                          BlogColumn blogColumn) {
        super(viewDelegate);
        mBlogColumn = blogColumn;
    }

    @Override
    protected Observable<? extends List<BlogItem>> getObservable(int page) {
        return NetEngine.getInstance().getHtmlByPage(mBlogColumn.getUrl(), page)
                .map(new Func1<String, List<BlogItem>>() {
                    @Override
                    public List<BlogItem> call(String s) {
                        return JsoupUtils.getColumnDetail(s, mBlogColumn.getName());
                    }
                })
                .compose(RxHelper.<List<BlogItem>>getErrAndIOSchedulerTransformer());
    }
}
