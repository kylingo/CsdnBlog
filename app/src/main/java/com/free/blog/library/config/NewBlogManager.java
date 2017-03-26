package com.free.blog.library.config;

/**
 * @author studiotang on 17/3/26
 */
public class NewBlogManager extends BaseUrlManger {

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
            UrlManager.NewBlogUrl.ANDROID,
            UrlManager.NewBlogUrl.MOBILE,
            UrlManager.NewBlogUrl.WEB,
            UrlManager.NewBlogUrl.ENTERPRISE,
            UrlManager.NewBlogUrl.CODE,
            UrlManager.NewBlogUrl.WWW,
            UrlManager.NewBlogUrl.DATABASE,
            UrlManager.NewBlogUrl.SYSTEM,
            UrlManager.NewBlogUrl.CLOUD,
            UrlManager.NewBlogUrl.SOFTWARE,
            UrlManager.NewBlogUrl.OTHER
    };
}
