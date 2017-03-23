package com.free.blog.ui.home.mine;

import com.free.blog.BlogApplication;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.local.dao.BlogCollectDao;
import com.free.blog.model.local.dao.DaoFactory;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.IRefreshView;
import com.free.blog.ui.base.mvp.refresh.RefreshPresenter;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author studiotang on 17/3/22
 */
class BlogCollectPresenter extends RefreshPresenter<List<BlogItem>> {

    private BlogCollectDao mBlogCollectDao;

    BlogCollectPresenter(IRefreshView<List<BlogItem>, IRefreshPresenter> viewDelegate) {
        super(viewDelegate);
        mBlogCollectDao = DaoFactory.create().getBlogCollectDao(BlogApplication.getContext());
    }

    @Override
    protected Observable<? extends List<BlogItem>> getObservable(int page) {
        return Observable.just(page)
                .map(new Func1<Integer, List<BlogItem>>() {
                    @Override
                    public List<BlogItem> call(Integer page) {
                        return mBlogCollectDao.query(page, getPageSize());
                    }
                })
                .compose(RxHelper.<List<BlogItem>>getErrAndIOSchedulerTransformer());
    }
}
