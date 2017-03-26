package com.free.blog.library.config;

/**
 * @author studiotang on 17/3/26
 */
public class HotBlogManager extends BaseUrlManger {

    public HotBlogManager() {
        super();
    }

    @Override
    protected int getFirstIndex() {
        return 1;
    }

    @Override
    protected String getSpfKey() {
        return KeyConfig.HOT_BLOG_TYPE;
    }

    @Override
    protected String[] getUrls() {
        return mUrls;
    }

    private static String[] mUrls = {
            UrlManager.HotBlogUrl.ANDROID,
            UrlManager.HotBlogUrl.MOBILE,
            UrlManager.HotBlogUrl.WEB,
            UrlManager.HotBlogUrl.ENTERPRISE,
            UrlManager.HotBlogUrl.CODE,
            UrlManager.HotBlogUrl.WWW,
            UrlManager.HotBlogUrl.DATABASE,
            UrlManager.HotBlogUrl.SYSTEM,
            UrlManager.HotBlogUrl.CLOUD,
            UrlManager.HotBlogUrl.SOFTWARE,
            UrlManager.HotBlogUrl.OTHER
    };
}
