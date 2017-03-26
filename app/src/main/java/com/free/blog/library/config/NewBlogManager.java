package com.free.blog.library.config;

/**
 * @author studiotang on 17/3/26
 */
public class NewBlogManager extends UrlManager {

    @Override
    protected int getFirstIndex() {
        return 1;
    }

    @Override
    protected String getSpfKey() {
        return KeyConfig.NEW_BLOG_TYPE;
    }

    @Override
    protected String[] getUrls() {
        return mUrls;
    }

    private static String[] mUrls = {
            UrlFactory.NewBlogUrl.ANDROID,
            UrlFactory.NewBlogUrl.MOBILE,
            UrlFactory.NewBlogUrl.WEB,
            UrlFactory.NewBlogUrl.ENTERPRISE,
            UrlFactory.NewBlogUrl.CODE,
            UrlFactory.NewBlogUrl.WWW,
            UrlFactory.NewBlogUrl.DATABASE,
            UrlFactory.NewBlogUrl.SYSTEM,
            UrlFactory.NewBlogUrl.CLOUD,
            UrlFactory.NewBlogUrl.SOFTWARE,
            UrlFactory.NewBlogUrl.OTHER
    };
}
