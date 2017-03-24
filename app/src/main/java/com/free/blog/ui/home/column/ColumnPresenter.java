package com.free.blog.ui.home.column;

import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.model.entity.Channel;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.IRefreshView;
import com.free.blog.ui.base.mvp.refresh.RefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author studiotang on 17/3/23
 */
public class ColumnPresenter extends RefreshPresenter<List<Channel>> {

    private String mKeywords;

    public ColumnPresenter(IRefreshView<List<Channel>, IRefreshPresenter> viewDelegate,
                           String keywords) {
        super(viewDelegate);
        mKeywords = keywords;
    }

    @Override
    protected Observable<? extends List<Channel>> getObservable(int page) {
        return NetEngine.getInstance().getColumnList(mKeywords, page)
                .map(new Func1<String, List<Channel>>() {
                    @Override
                    public List<Channel> call(String s) {
                        return JsoupUtils.getColumnList(s, mKeywords);
                    }
                })
                .compose(RxHelper.<List<Channel>>getErrAndIOSchedulerTransformer());
    }
}
