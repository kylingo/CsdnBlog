package com.free.blog.data.remote;

import rx.Observable;

/**
 * @author tangqi on 17-3-14.
 */
public class NetEngine {

    private final IBlogApi mBlogApi;
    private static final NetEngine sInstance = new NetEngine();

    public static NetEngine getInstance() {
        return sInstance;
    }

    private NetEngine() {
        mBlogApi = RetrofitClient.getInstance().getNetApi();
    }

    public Observable<String> getBloggerInfo(String userId) {
        return mBlogApi.getBloggerInfo(userId);
    }

    public Observable<String> getBlogList(String userId, int page) {
        return mBlogApi.getBlogList(userId, page);
    }

    public Observable<String> getCategoryBlogList(String category, int page) {
        return mBlogApi.getCategoryBlogList(category, page);
    }

    public Observable<String> getHtml(String url) {
        return mBlogApi.getHtml(url);
    }

    public Observable<String> getBlogComment(String blogId, int page) {
        return mBlogApi.getBlogComment(blogId, page);
    }

    public Observable<String> getColumnList(String keywords, int page) {
        return mBlogApi.getColumnList(keywords, page);
    }
}
