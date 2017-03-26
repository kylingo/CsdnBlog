package com.free.blog.model.remote;

import com.free.blog.library.config.Config;

import rx.Observable;

/**
 * @author tangqi on 17-3-14.
 */
public class NetEngine implements IBlogApi {

    private final IBlogApi mBlogApi;
    private static final NetEngine sInstance = new NetEngine();

    public static NetEngine getInstance() {
        return sInstance;
    }

    private NetEngine() {
        mBlogApi = RetrofitClient.getInstance().getNetApi();
    }

    @Override
    public Observable<String> getBloggerInfo(String userId) {
        return mBlogApi.getBloggerInfo(userId);
    }

    @Override
    public Observable<String> getBlogList(String userId, int page) {
        return mBlogApi.getBlogList(userId, page);
    }

    @Override
    public Observable<String> getCategoryBlogList(String category, int page) {
        return mBlogApi.getCategoryBlogList(category, page);
    }

    @Override
    public Observable<String> getHtml(String url) {
        return mBlogApi.getHtml(url);
    }

    @Override
    public Observable<String> getHtmlByPage(String url, int page) {
        return mBlogApi.getHtmlByPage(url, page);
    }

    @Override
    public Observable<String> getBlogComment(String blogId, int page) {
        return mBlogApi.getBlogComment(blogId, page);
    }

    @Override
    public Observable<String> getBlogRank() {
        return mBlogApi.getBlogRank();
    }

    @Override
    public Observable<String> getBlogPk() {
        return mBlogApi.getBlogPk();
    }

    public String getBlogPkUrl() {
        return Config.HOST_BLOG + "PK.html";
    }

    public String getBlogDaily() {
        return Config.HOST_BLOG + "column/details/14549.html";
    }
}
