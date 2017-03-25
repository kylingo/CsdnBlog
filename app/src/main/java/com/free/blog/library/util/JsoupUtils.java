package com.free.blog.library.util;

import android.text.TextUtils;
import android.util.Log;

import com.free.blog.library.config.Config;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.entity.Blogger;
import com.free.blog.model.entity.Channel;
import com.free.blog.model.entity.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 网页解析
 *
 * @author tangqi
 * @since 2015年8月9日下午08:09:57
 */
@SuppressWarnings("deprecation")
public class JsoupUtils {

    private static final String BLOG_URL = "http://blog.csdn.net";

    /**
     * 获取博主简易信息
     */
    public static Blogger getBlogger(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        Document localDocument = Jsoup.parse(html);
        Elements localElements = localDocument.getElementsByClass("header");

        String str;
        try {
            Element localElement1 = localDocument.getElementsByClass("panel").get(0).select("ul" +
                    ".panel_body.profile").get(0);
            str = localElement1.getElementById("blog_userface").select("a").select("img").attr
                    ("src");
        } catch (Exception e) {
            e.printStackTrace();
            str = "";
        }

        Blogger blogger = new Blogger();
        String title = localElements.select("h2").text();
        String description = localElements.select("h3").text();
        String imgUrl = str;

        if (TextUtils.isEmpty(title)) {
            return null;
        }
        blogger.setTitle(title);
        blogger.setDescription(description);
        blogger.setImgUrl(imgUrl);
        return blogger;
    }

    /**
     * 获取博客列表
     */
    public static List<BlogItem> getBlogList(String category, String html, List<BlogCategory>
            blogCategoryList) {
        List<BlogItem> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements blogList = doc.getElementsByClass("article_item");

        for (Element blogItem : blogList) {
            BlogItem item = new BlogItem();
            String title = blogItem.select("h1").text();

            String icoType = blogItem.getElementsByClass("ico").get(0).className();
            if (title.contains("置顶")) {
                item.setTopFlag(1);
            }
            String description = blogItem.select("div.article_description").text();
            String msg = blogItem.select("div.article_manage").text();
            String date = blogItem.getElementsByClass("article_manage").get(0).text();
            String link = BLOG_URL + blogItem.select("h1").select("a").attr("href");

            item.setTitle(title);
            item.setMsg(msg);
            item.setContent(description);
            item.setDate(date);
            item.setLink(link);
            item.setCategory(category);
            item.setIcoType(icoType);

            // 没有图片
            item.setImgLink(null);
            list.add(item);
        }

        // 获取博客分类
        Elements panelElements = doc.getElementsByClass("panel");
        for (Element panelElement : panelElements) {
            try {
                String panelHead = panelElement.select("ul.panel_head").get(0).text();
                if ("文章分类".equals(panelHead)) {
                    Element panelBodyElement = panelElement.select("ul.panel_body").get(0);
                    Elements typeElements = panelBodyElement.select("li");

                    if (typeElements != null) {
                        // 若发现新的分类，清除以前的分类
                        blogCategoryList.clear();
                        BlogCategory allBlogCategory = new BlogCategory();
                        allBlogCategory.setName("全部");
                        blogCategoryList.add(0, allBlogCategory);

                        for (Element typeElement : typeElements) {
                            BlogCategory blogCategory = new BlogCategory();
                            String name = typeElement.select("a").text().trim().replace("【", "")
                                    .replace("】", "");
                            String link = typeElement.select("a").attr("href");
                            blogCategory.setName(name.trim());
                            blogCategory.setLink(link.trim());

                            blogCategoryList.add(blogCategory);
                        }

                    }
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return list;
    }

    /**
     * 获取博客详情内容
     */
    public static String getContent(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        // 获取详情
        Element localElement1 = Jsoup.parse(html).getElementsByClass("details").get(0);

        // 删除所有script标签
        localElement1.select("script").remove();

        if (localElement1.getElementById("digg") != null) {
            localElement1.getElementById("digg").remove();
        }
        if (localElement1.getElementsByClass("tag2box") != null) {
            localElement1.getElementsByClass("tag2box").remove();
        }

        // 删除博客详情-分类标签
        if (localElement1.getElementsByClass("category") != null) {
            localElement1.getElementsByClass("category").remove();
        }

        // 删除版权标签
        if (localElement1.getElementsByClass("bog_copyright") != null) {
            localElement1.getElementsByClass("bog_copyright").remove();
        }

        localElement1.getElementsByClass("article_manage").remove();
        localElement1.getElementsByTag("h1").tagName("h2");
        Iterator<?> localIterator = localElement1.select("pre[name=code]").iterator();
        while (true) {
            if (!localIterator.hasNext())
                return localElement1.toString();
            Element localElement2 = (Element) localIterator.next();
            localElement2.attr("class", "brush: java; gutter: false;");
            Log.i("CSNDBlog_JsoupUtil", "codeNode.text()" + localElement2.text());
        }
    }

    /**
     * 获取博客详情内容
     */
    public static String getTitle(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            return null;
        }

        Element localElement1 = Jsoup.parse(paramString).getElementsByClass("details").get(0);
        Element titleElement = localElement1.getElementsByClass("article_title").get(0);
        try {
            return titleElement.select("h1").get(0).select("span").get(0).select("a").get(0).text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取博文评论列表
     */
    public static List<Comment> getCommentList(String json, int pageIndex, int pageSize) {
        List<Comment> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            int index = 0;
            int len = jsonArray.length();

            // 如果评论数大于20
            if (len > 20) {
                index = (pageIndex * pageSize) - 20;
            }

            if (len < pageSize && pageIndex > 1) {
                return list;
            }

            if ((pageIndex * pageSize) < len) {
                len = pageIndex * pageSize;
            }

            for (int i = index; i < len; i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String commentId = item.getString("CommentId");
                String content = item.getString("Content");
                String username = item.getString("UserName");
                String parentId = item.getString("ParentId");
                String postTime = item.getString("PostTime");
                String userface = item.getString("Userface");

                Comment comment = new Comment();
                comment.setCommentId(commentId);
                comment.setContent(content);
                comment.setUsername(username);
                comment.setParentId(parentId);
                comment.setPostTime(postTime);
                comment.setUserface(userface);

                if (parentId.equals("0")) {
                    // 如果parentId为0的话，表示它是评论的topic
                    comment.setType(Config.COMMENT_TYPE.PARENT);
                } else {
                    comment.setType(Config.COMMENT_TYPE.CHILD);
                }
                list.add(comment);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Channel> getColumnList(String html, String category) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        List<Channel> channelList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        try {
            Element columnElement = doc
                    .getElementsByClass("blog_home_main").get(0)
                    .getElementsByClass("column_index").get(0);

            Elements columnWrap = columnElement.getElementsByClass("column_wrap");
            for (Element element : columnWrap) {
                Elements columnList = element.getElementsByClass("column_list");

                for (Element column : columnList) {
                    Channel channel = new Channel();
                    Element bgElement = column.getElementsByClass("column_bg").get(0);
                    String bgUrl = bgElement.attr("style").replace("background-image:url(", "")
                            .replace(")", "");

                    Element aElement = column.getElementsByClass("column_list_link").get(0);
                    String url = StringUtils.trimLastChar(Config.HOST_BLOG) + aElement.attr("href");
                    String title = aElement.getElementsByClass("column_c").get(0)
                            .getElementsByClass("column_list_p").get(0)
                            .text();

                    channel.setImgUrl(bgUrl);
                    channel.setUrl(url);
                    channel.setChannelName(title);
                    channel.setCategory(category);
                    channelList.add(channel);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return channelList;
    }

    public static List<BlogItem> getColumnBlogList(String html, String category) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        List<BlogItem> blogList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        try {
            Elements blogDiv = doc.getElementsByClass("blog_l");
            Element detailListUl = blogDiv.select("ul").get(0);
            Elements detailListLi = detailListUl.select("li");
            for (Element element : detailListLi) {
                Element h4 = element.select("h4").get(0);
                String title = h4.text();
                String link = h4.select("a").attr("href");
                String decs = element.getElementsByClass("detail_p").get(0).text();
                Element detailBDiv = element.getElementsByClass("detail_b").get(0);
                String date = detailBDiv.select("span").text();
                String times = detailBDiv.select("em").text();

                BlogItem blogItem = new BlogItem();
                blogItem.setTitle(title);
                blogItem.setLink(link);
                blogItem.setContent(decs);
                blogItem.setCategory(category);
                blogItem.setDate(date + " " + "阅读" + "(" + times + ")");
                blogItem.setIcoType(Config.BLOG_TYPE.BLOG_TYPE_ORIGINAL);
                blogList.add(blogItem);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return blogList;
    }
}
