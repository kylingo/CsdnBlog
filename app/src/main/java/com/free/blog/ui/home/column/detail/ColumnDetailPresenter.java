package com.free.blog.ui.home.column.detail;

import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.entity.Channel;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.IRefreshView;
import com.free.blog.ui.base.mvp.refresh.RefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author studiotang on 17/3/24
 */
public class ColumnDetailPresenter extends RefreshPresenter<List<BlogItem>> {
    private Channel mChannel;

    public ColumnDetailPresenter(IRefreshView<List<BlogItem>, IRefreshPresenter> viewDelegate,
                                 Channel channel) {
        super(viewDelegate);
        mChannel = channel;
    }

    @Override
    protected Observable<? extends List<BlogItem>> getObservable(int page) {
        return NetEngine.getInstance().getHtmlByPage(mChannel.getUrl(), page)
                .map(new Func1<String, List<BlogItem>>() {
                    @Override
                    public List<BlogItem> call(String s) {
                        return JsoupUtils.getColumnBlogList(s, mChannel.getChannelName());
                    }
                })
                .compose(RxHelper.<List<BlogItem>>getErrAndIOSchedulerTransformer());
    }
}
