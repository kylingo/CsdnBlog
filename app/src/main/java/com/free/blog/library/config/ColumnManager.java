package com.free.blog.library.config;

/**
 * @author studiotang on 17/3/25
 */
public class ColumnManager extends BaseUrlManger {

    public ColumnManager() {
        super();
    }

    @Override
    protected int getFirstIndex() {
        return 0;
    }

    @Override
    protected String getSpfKey() {
        return KeyConfig.COLUMN_TYPE;
    }

    @Override
    protected String[] getUrls() {
        return mUrls;
    }

    private static String[] mUrls = {
            UrlManager.ColumnUrl.ANDROID,
            UrlManager.ColumnUrl.MOBILE,
            UrlManager.ColumnUrl.WEB,
            UrlManager.ColumnUrl.ENTERPRISE,
            UrlManager.ColumnUrl.CODE,
            UrlManager.ColumnUrl.WWW,
            UrlManager.ColumnUrl.DATABASE,
            UrlManager.ColumnUrl.SYSTEM,
            UrlManager.ColumnUrl.CLOUD,
            UrlManager.ColumnUrl.SOFTWARE,
            UrlManager.ColumnUrl.OTHER
    };
}
