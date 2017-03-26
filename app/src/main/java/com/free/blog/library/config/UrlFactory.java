package com.free.blog.library.config;

/**
 * 分类管理
 *
 * @author tangqi
 * @since 2015年8月19日下午9:53:12
 */

public class UrlFactory {

    private static final String HOST = Config.BLOG_HOST;

    /**
     * 博客分类
     *
     * @author Frank
     */
    public class CategoryName {
        public static final String ANDROID = "Android";
        public static final String MOBILE = "移动开发";
        static final String WEB = "Web前端";
        static final String ENTERPRISE = "架构设计";
        static final String CODE = "编程语言";
        static final String WWW = "互联网";
        static final String DATABASE = "数据库";
        static final String SYSTEM = "系统运维";
        static final String CLOUD = "云计算";
        static final String SOFTWARE = "研发管理";
        static final String OTHER = "综合";
    }

    /**
     * 博客分类URL
     *
     * @author Frank
     */

    class ColumnUrl {
        final static String ANDROID = HOST + "column/list.html?q=Android";
        final static String MOBILE = HOST + "mobile/column.html";
        final static String WEB = HOST + "web/column.html";
        final static String ENTERPRISE = HOST + "enterprise/column.html";
        final static String CODE = HOST + "code/column.html";
        final static String WWW = HOST + "www/column.html";
        final static String DATABASE = HOST + "database/column.html";
        final static String SYSTEM = HOST + "system/column.html";
        final static String CLOUD = HOST + "cloud/column.html";
        final static String SOFTWARE = HOST + "software/column.html";
        final static String OTHER = HOST + "other/column.html";
    }

    class NewBlogUrl {
        final static String ANDROID = HOST + "mobile/newarticle.html";
        final static String MOBILE = HOST + "mobile/newarticle.html";
        final static String WEB = HOST + "web/newarticle.html";
        final static String ENTERPRISE = HOST + "enterprise/newarticle.html";
        final static String CODE = HOST + "code/newarticle.htmll";
        final static String WWW = HOST + "www/newarticle.html";
        final static String DATABASE = HOST + "database/newarticle.html";
        final static String SYSTEM = HOST + "system/newarticle.html";
        final static String CLOUD = HOST + "cloud/newarticle.html";
        final static String SOFTWARE = HOST + "software/newarticle.html";
        final static String OTHER = HOST + "other/newarticle.html";
    }

    class HotBlogUrl {
        final static String ANDROID = HOST + "mobile/hotarticle.html";
        final static String MOBILE = HOST + "mobile/hotarticle.html";
        final static String WEB = HOST + "web/hotarticle.html";
        final static String ENTERPRISE = HOST + "enterprise/hotarticle.html";
        final static String CODE = HOST + "code/hotarticle.htmll";
        final static String WWW = HOST + "www/hotarticle.html";
        final static String DATABASE = HOST + "database/hotarticle.html";
        final static String SYSTEM = HOST + "system/hotarticle.html";
        final static String CLOUD = HOST + "cloud/hotarticle.html";
        final static String SOFTWARE = HOST + "software/hotarticle.html";
        final static String OTHER = HOST + "other/hotarticle.html";
    }

}
