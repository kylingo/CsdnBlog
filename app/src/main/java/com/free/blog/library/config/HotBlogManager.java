package com.free.blog.library.config;

/**
 * @author studiotang on 17/3/26
 */
public class HotBlogManager extends UrlManager {

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
            UrlFactory.HotBlogUrl.ANDROID,
            UrlFactory.HotBlogUrl.MOBILE,
            UrlFactory.HotBlogUrl.WEB,
            UrlFactory.HotBlogUrl.ENTERPRISE,
            UrlFactory.HotBlogUrl.CODE,
            UrlFactory.HotBlogUrl.WWW,
            UrlFactory.HotBlogUrl.DATABASE,
            UrlFactory.HotBlogUrl.SYSTEM,
            UrlFactory.HotBlogUrl.CLOUD,
            UrlFactory.HotBlogUrl.SOFTWARE,
            UrlFactory.HotBlogUrl.OTHER
    };
}
