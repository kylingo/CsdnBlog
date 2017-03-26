package com.free.blog.ui.home.find.rank;

import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.model.entity.BlogRank;
import com.free.blog.model.entity.RankItem;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.vp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.vp.refresh.IRefreshView;
import com.free.blog.ui.base.vp.refresh.RefreshPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author studiotang on 17/3/25
 */
class RankPresenter extends RefreshPresenter<List<RankItem>> implements RankContract.Presenter {

    RankPresenter(IRefreshView<List<RankItem>, IRefreshPresenter> viewDelegate) {
        super(viewDelegate);
    }

    @Override
    protected Observable<? extends List<RankItem>> getObservable(int page) {
        return NetEngine.getInstance().getBlogRank()
                .map(new Func1<String, List<RankItem>>() {
                    @Override
                    public List<RankItem> call(String result) {
                        List<BlogRank> blogRanks = JsoupUtils.getBlogRank(result);
                        if (blogRanks != null && blogRanks.size() > 0) {
                            List<RankItem> list = new ArrayList<>();
                            for (BlogRank blogRank : blogRanks) {
                                list.addAll(blogRank.getData());
                            }
                            return list;
                        }

                        return null;
                    }
                })
                .compose(RxHelper.<List<RankItem>>getErrAndIOSchedulerTransformer());
    }

    @Override
    public boolean hasMore(int size) {
        return false;
    }
}
