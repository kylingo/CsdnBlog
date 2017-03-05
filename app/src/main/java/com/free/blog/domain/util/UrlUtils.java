package com.free.blog.domain.util;


import static com.free.blog.domain.config.AppConstants.CSDN_BASE_URL;

/**
 * URL工具
 *
 * @author tangqi
 * @since 2015年7月8日下午9:20:20
 */
public class UrlUtils {

    /**
     * 获取博客列表的URL
     */
    public static String getBlogListURL(String baseUrl, int page) {
        return baseUrl + page;
    }

    /**
     * 获取CSDN博客默认URL
     */
    public static String getBlogDefaultUrl(String userId) {
        return CSDN_BASE_URL + "/" + userId + "/article/list" + "/";
    }

    public static String getBlogCategoryUrl(String categoryLink) {
        return CSDN_BASE_URL + "/" + categoryLink + "/";
    }

    /**
     * 返回博文评论列表链接
     */
    public static String getCommentListURL(String filename, String pageIndex) {
        return "http://blog.csdn.net/wwj_748/comment/list/" + filename + "?page=" + pageIndex;
    }

    public static String getColumnList(String type, int page) {
        return CSDN_BASE_URL + "/column/list.html?" + "q=" + type + "&page=" + page;
    }
}
