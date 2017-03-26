package com.free.blog.ui.content;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.free.blog.BlogApplication;
import com.free.blog.R;
import com.free.blog.model.entity.BlogHtml;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.local.dao.BlogCollectDao;
import com.free.blog.model.local.dao.BlogContentDao;
import com.free.blog.model.local.dao.BlogHistoryDao;
import com.free.blog.model.local.dao.DaoFactory;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.rx.RxSubscriber;
import com.free.blog.library.util.JsoupUtils;
import com.free.blog.ui.base.vp.single.ISinglePresenter;
import com.free.blog.ui.base.vp.single.SinglePresenter;
import com.free.blog.ui.content.comment.CommentActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.Iterator;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author studiotang on 17/3/21
 */
class BlogContentPresenter extends SinglePresenter<BlogHtml> implements BlogContentContract.Presenter {

    private BlogContentContract.View<BlogHtml, ISinglePresenter> mViewDelegate;
    private BlogContentDao mBlogContentDao;
    private BlogCollectDao mBlogCollectDao;
    private BlogHistoryDao mBlogHistoryDao;
    private BlogItem mBlogItem;
    private String mUrl;

    BlogContentPresenter(BlogContentContract.View<BlogHtml, ISinglePresenter> viewDelegate,
                         @NonNull BlogItem blogItem, @NonNull String url) {
        super(viewDelegate);
        mViewDelegate = viewDelegate;
        mBlogItem = blogItem;
        mUrl = url;
        mBlogContentDao = DaoFactory.create().getBlogContentDao(BlogApplication.getContext());
        mBlogCollectDao = DaoFactory.create().getBlogCollectDao(BlogApplication.getContext());
        mBlogHistoryDao = DaoFactory.create().getBlogHistoryDao(BlogApplication.getContext());
    }

    @Override
    protected Observable<BlogHtml> getObservable() {
        return Observable.just(mUrl)
                .map(new Func1<String, BlogHtml>() {
                    @Override
                    public BlogHtml call(String s) {
                        return mBlogContentDao.query(mUrl);
                    }
                })
                .flatMap(new Func1<BlogHtml, Observable<BlogHtml>>() {
                    @Override
                    public Observable<BlogHtml> call(BlogHtml blogHtml) {
                        if (blogHtml != null) {
                            return Observable.just(blogHtml);
                        }

                        return getNetObservable();
                    }
                })
                .compose(RxHelper.<BlogHtml>getErrAndIOSchedulerTransformer());
    }

    private Observable<BlogHtml> getNetObservable() {
        return NetEngine.getInstance().getHtml(mUrl)
                .flatMap(new Func1<String, Observable<BlogHtml>>() {
                    @Override
                    public Observable<BlogHtml> call(String result) {
                        String title = JsoupUtils.getBlogTitle(result);
                        String content = JsoupUtils.getBlogContent(result);
                        String html = adjustPicSize(content);

                        if (TextUtils.isEmpty(html)) {
                            return Observable.just(null);
                        }

                        BlogHtml blogHtml = new BlogHtml();
                        blogHtml.setUrl(mUrl);
                        blogHtml.setHtml(html);
                        blogHtml.setTitle(title);
                        blogHtml.setUpdateTime(System.currentTimeMillis());
                        blogHtml.setReserve("");

                        mBlogContentDao.insert(blogHtml);
                        return Observable.just(blogHtml);
                    }
                });
    }

    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public void comment(Activity activity, String blogId) {
        Intent i = new Intent();
        i.setClass(activity, CommentActivity.class);
        i.putExtra(CommentActivity.EXTRA_BLOG_ID, blogId);
        activity.startActivity(i);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
    }

    @Override
    public void share(Activity activity, String title, String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, title + "：" + "\n" + url);
        activity.startActivity(Intent.createChooser(intent, "CSDN博客分享"));
    }

    @Override
    public void queryCollect() {
        Observable.just(mBlogItem.getLink())
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return null != mBlogCollectDao.query(mBlogItem.getLink());
                    }
                })
                .compose(RxHelper.<Boolean>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<Boolean>() {
                    @Override
                    public void onError(Throwable e) {
                        mViewDelegate.onUpdateCollectUI(false);
                    }

                    @Override
                    public void onNext(Boolean result) {
                        mViewDelegate.onUpdateCollectUI(result);
                    }
                });
    }

    @Override
    public void collect(final boolean isCollect) {
        Observable.just(isCollect)
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean isCollect) {
                        if (isCollect) {
                            mBlogItem.setUpdateTime(System.currentTimeMillis());
                            mBlogCollectDao.insert(mBlogItem);
                            return true;
                        } else {
                            mBlogCollectDao.delete(mBlogItem);
                            return true;
                        }
                    }
                })
                .compose(RxHelper.<Boolean>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<Boolean>() {
                    @Override
                    public void onError(Throwable e) {
                        mViewDelegate.onCollectFailure(isCollect);
                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            mViewDelegate.onCollectSuccess(isCollect);
                        } else {
                            mViewDelegate.onCollectFailure(isCollect);
                        }
                    }
                });
    }

    @Override
    public void saveHistory() {
        if (mBlogItem != null) {
            mBlogItem.setUpdateTime(System.currentTimeMillis());
            mBlogHistoryDao.insert(mBlogItem);
        }
    }

    /**
     * 适应页面
     */
    private String adjustPicSize(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            return null;
        }
        Element localElement = Jsoup.parse(paramString).getElementsByClass("details").get(0);
        Iterator<?> localIterator = localElement.getElementsByTag("img").iterator();
        while (true) {
            if (!localIterator.hasNext())
                return "<script type=\"text/javascript\" " + "src=\"file:///android_asset/shCore.js\">" +
                        "</script><script type=\"text/javascript\" src=\"file:///android_asset/shBrushCpp.js\"></script>" +
                        "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushXml.js\"></script>" +
                        "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushJScript.js\"></script>" +
                        "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushJava.js\"></script>" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">" +
                        "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
                        + localElement.toString();
            ((Element) localIterator.next()).attr("width", "100%");
        }
    }
}
