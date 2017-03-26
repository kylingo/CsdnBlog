package com.free.blog.library.config;

/**
 * @author studiotang on 17/3/25
 */
public class ColumnManager extends UrlManager {

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
            UrlFactory.ColumnUrl.ANDROID,
            UrlFactory.ColumnUrl.MOBILE,
            UrlFactory.ColumnUrl.WEB,
            UrlFactory.ColumnUrl.ENTERPRISE,
            UrlFactory.ColumnUrl.CODE,
            UrlFactory.ColumnUrl.WWW,
            UrlFactory.ColumnUrl.DATABASE,
            UrlFactory.ColumnUrl.SYSTEM,
            UrlFactory.ColumnUrl.CLOUD,
            UrlFactory.ColumnUrl.SOFTWARE,
            UrlFactory.ColumnUrl.OTHER
    };
}
