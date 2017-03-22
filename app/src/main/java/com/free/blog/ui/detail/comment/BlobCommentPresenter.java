package com.free.blog.ui.detail.comment;

import com.free.blog.BlogApplication;
import com.free.blog.data.entity.Comment;
import com.free.blog.data.entity.CommentComparator;
import com.free.blog.data.local.dao.BlogCommentDao;
import com.free.blog.data.local.dao.DaoFactory;
import com.free.blog.data.remote.NetEngine;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.ui.base.mvp.refresh.IRefreshPresenter;
import com.free.blog.ui.base.mvp.refresh.IRefreshView;
import com.free.blog.ui.base.mvp.refresh.RefreshPresenter;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author tangqi on 17-3-22.
 */
class BlobCommentPresenter extends RefreshPresenter<List<Comment>> {

    private BlogCommentDao mBlogCommentDao;
    private String mBlogId;

    BlobCommentPresenter(IRefreshView<List<Comment>, IRefreshPresenter> viewDelegate, String blogId) {
        super(viewDelegate);
        mBlogId = blogId;
        mBlogCommentDao = DaoFactory.create().getBlogCommentDao(BlogApplication.getContext(), blogId);
    }

    @Override
    protected Observable<? extends List<Comment>> getObservable(final int page) {
        if (isNetWorkAvailable()) {
            return NetEngine.getInstance().getBlogComment(mBlogId, page)
                    .map(new Func1<String, List<Comment>>() {
                        @Override
                        public List<Comment> call(String s) {
                            List<Comment> list = JsoupUtils.getBlogCommentList(s, page, getPageSize());
                            CommentComparator comparator = new CommentComparator();
                            Collections.sort(list, comparator);

                            mBlogCommentDao.insert(list);
                            return list;
                        }
                    })
                    .compose(RxHelper.<List<Comment>>getErrAndIOSchedulerTransformer());
        }

        return Observable.just(page).
                map(new Func1<Integer, List<Comment>>() {
                    @Override
                    public List<Comment> call(Integer page) {
                        return mBlogCommentDao.query(page);
                    }
                });
    }


}
