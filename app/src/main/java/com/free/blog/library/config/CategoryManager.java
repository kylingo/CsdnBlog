package com.free.blog.library.config;

/**
 * 分类管理
 *
 * @author tangqi
 * @since 2015年8月19日下午9:53:12
 */

public class CategoryManager {

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
        /**
         * Android
         */
        final static String ANDROID = "http://blog.csdn.net/column/list.html?q=Android";

        /**
         * 移动开发
         */
        final static String MOBILE = "http://blog.csdn.net/mobile/column.html";

        /**
         * WEB前端
         */
        final static String WEB = "http://blog.csdn.net/web/column.html";

        /**
         * 架构设计
         */
        final static String ENTERPRISE = "http://blog.csdn.net/enterprise/column.html";

        /**
         * 编程语言
         */
        final static String CODE = "http://blog.csdn.net/code/column.html";

        /**
         * 互联网
         */
        final static String WWW = "http://blog.csdn.net/www/column.html";

        /**
         * 数据库
         */
        final static String DATABASE = "http://blog.csdn.net/database/column.html";

        /**
         * 系统运维
         */
        final static String SYSTEM = "http://blog.csdn.net/system/column.html";

        /**
         * 云计算
         */
        final static String CLOUD = "http://blog.csdn.net/cloud/column.html";

        /**
         * 研发管理
         */
        final static String SOFTWARE = "http://blog.csdn.net/software/column.html";

        /**
         * 综合
         */
        final static String OTHER = "http://blog.csdn.net/other/column.html";
    }

}
