package com.free.blog.library.util;

import android.text.TextUtils;

import com.free.blog.library.config.Config;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.model.entity.BlogColumn;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.model.entity.BlogRank;
import com.free.blog.model.entity.Blogger;
import com.free.blog.model.entity.Comment;
import com.free.blog.model.entity.RankItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 网页解析
 *
 * @author tangqi
 * @since 2015年8月9日下午08:09:57
 */
public class JsoupUtils {

    private static final String BLOG_URL = "https://blog.csdn.net";

    public static Blogger getBlogger(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        Document doc = Jsoup.parse(html);
        String imgUrl;
        try {
            imgUrl = doc.getElementsByClass("avatar_pic").attr("src");
        } catch (Exception e) {
            e.printStackTrace();
            imgUrl = "";
        }

        try {
            Elements headerElement = doc.getElementsByClass("title-box");
            String title = headerElement.select("h6").select("a").text();
            String description = headerElement.select("p").text();
            if (TextUtils.isEmpty(title)) {
                return getBlogger2(html);
            }

            Blogger blogger = new Blogger();
            blogger.setTitle(title);
            blogger.setDescription(description);
            blogger.setImgUrl(imgUrl);
            return blogger;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getBlogger2(html);
    }

    /**
     * 第2种风格博主
     */
    private static Blogger getBlogger2(String html) {
        Document doc = Jsoup.parse(html);
        String imgUrl;
        try {
            // 头像
            imgUrl = doc.getElementsByClass("inf_bar").select("a").select("img").attr("src");
        } catch (Exception e) {
            e.printStackTrace();
            imgUrl = "";
        }

        try {
            // 博主名字和个性签名
            Elements headerElement = doc.getElementsByClass("header-left");
            String title = headerElement.select("h1").text();
            String description = headerElement.select("div").select("span").text();
            if (TextUtils.isEmpty(title)) {
                return null;
            }

            Blogger blogger = new Blogger();
            blogger.setTitle(title);
            blogger.setDescription(description);
            blogger.setImgUrl(imgUrl);
            return blogger;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<BlogItem> getBlogList(String category, String html, List<BlogCategory>
            blogCategoryList) {
        List<BlogItem> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);

        // blog item
        try {
            int totalPage = getBlogListPage(doc);

            Elements blogList = doc.getElementsByClass("article-item-box");
            for (Element blogItem : blogList) {
                // 不需要展示item
                if ("display: none;".equals(blogItem.attr("style"))) {
                    continue;
                }

                BlogItem item = new BlogItem();
                String title = blogItem.select("h4").text();

                String icoType = blogItem.getElementsByClass("article-type").get(0).className();
                if (title.contains("置顶")) {
                    item.setTopFlag(1);
                }
                String description = blogItem.getElementsByClass("content").select("a").text();
                String date = blogItem.getElementsByClass("info-box").get(0).text();
                String link = blogItem.getElementsByClass("content").select("a").attr("href");
                if (!TextUtils.isEmpty(link) && !link.contains(BLOG_URL)) {
                    link = BLOG_URL + link;
                }

                item.setTitle(title);
                item.setContent(description);
                item.setDate(date);
                item.setLink(link);
                item.setTotalPage(totalPage);
                item.setCategory(category);
                item.setIcoType(icoType);
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // blog category

        try {
            Element asideCategory = doc.getElementById("asideCategory");
            Elements asideContent = asideCategory.getElementsByClass("aside-content");
            Elements categoryElements = asideContent.select("ul").select("li");
            if (categoryElements != null) {
                blogCategoryList.clear();
                BlogCategory allBlogCategory = new BlogCategory();
                allBlogCategory.setName("全部");
                blogCategoryList.add(0, allBlogCategory);

                for (Element categoryElement : categoryElements) {
                    BlogCategory blogCategory = new BlogCategory();
                    String name = categoryElement.select("a").text().trim().replace("【", "")
                            .replace("】", "");
                    String link = categoryElement.select("a").attr("href");
                    blogCategory.setName(name.trim());
                    blogCategory.setLink(link.trim());

                    blogCategoryList.add(blogCategory);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list.size() > 0) {
            return list;
        } else {
            return getBlogList2(category, html, blogCategoryList);
        }
    }

    private static List<BlogItem> getBlogList2(String category, String html, List<BlogCategory>
            blogCategoryList) {
        List<BlogItem> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);

        // blog item
        try {
            int totalPage = getBlogListPage(doc);

            Elements blogList = doc.getElementsByClass("blog-unit");
            for (Element blogItem : blogList) {
                BlogItem item = new BlogItem();
                String title = blogItem.select("h3").text();

                if (title.contains("置顶")) {
                    item.setTopFlag(1);
                }
                String description = blogItem.select("p.text").text();
                String date = blogItem.getElementsByClass("left-dis-24").get(0).text();
                String link = blogItem.select("a").attr("href");
                if (!TextUtils.isEmpty(link) && !link.contains(BLOG_URL)) {
                    link = BLOG_URL + link;
                }

                item.setTitle(title);
                item.setContent(description);
                item.setDate(date);
                item.setLink(link);
                item.setTotalPage(totalPage);
                item.setCategory(category);
                item.setIcoType(null);
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // blog category
        try {
            Elements panelElements = doc.getElementsByClass("hotArticle-list").get(0)
                    .getElementsByClass("clearfix");
            if (panelElements != null) {
                blogCategoryList.clear();
                BlogCategory allBlogCategory = new BlogCategory();
                allBlogCategory.setName("全部");
                blogCategoryList.add(0, allBlogCategory);

                for (Element panelElement : panelElements) {
                    if (panelElement != null) {
                        BlogCategory blogCategory = new BlogCategory();
                        String name = panelElement.select("a").text().trim().replace("【", "")
                                .replace("】", "");
                        String link = panelElement.select("a").attr("href");
                        blogCategory.setName(name.trim());
                        blogCategory.setLink(link.trim());

                        blogCategoryList.add(blogCategory);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static int getBlogListPage(Document doc) {
        int totalPage = 0;
        Elements elements = doc.getElementsByClass("page-link");
        if (elements != null && elements.size() > 1) {
            Element lastElement = elements.get(elements.size() - 2);
            try {
                String lastPage = lastElement.text();
                totalPage = Integer.parseInt(lastPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalPage;
    }

    private static int getBlogTotalPage(String page) {
        int totalPage = 0;
        if (!TextUtils.isEmpty(page)) {
            int pageStart = page.lastIndexOf("共");
            int pageEnd = page.lastIndexOf("页");
            String pageStr = page.substring(pageStart + 1, pageEnd);
            try {
                totalPage = Integer.parseInt(pageStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return totalPage;
    }

    public static String getBlogContent(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        try {
            Element detailElement = Jsoup.parse(html).getElementsByClass("article_content").get(0);
            detailElement.select("script").remove();

            if (detailElement.getElementById("digg") != null) {
                detailElement.getElementById("digg").remove();
            }

            if (detailElement.getElementsByClass("tag2box") != null) {
                detailElement.getElementsByClass("tag2box").remove();
            }

            if (detailElement.getElementsByClass("category") != null) {
                detailElement.getElementsByClass("category").remove();
            }

            if (detailElement.getElementsByClass("bog_copyright") != null) {
                detailElement.getElementsByClass("bog_copyright").remove();
            }

            detailElement.getElementsByClass("article_manage").remove();
            detailElement.getElementsByTag("h1").tagName("h2");
            for (Element element : detailElement.select("pre[name=code]")) {
                element.attr("class", "brush: java; gutter: false;");
            }

            return detailElement.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getBlogContent2(html);
    }

    /**
     * 第2种风格博客
     */
    private static String getBlogContent2(String html) {
        try {
            Element detailElement = Jsoup.parse(html).getElementsByClass("article_content").get(0);
            detailElement.getElementsByClass("article_manage").remove();
            detailElement.getElementsByTag("h1").tagName("h2");
            for (Element element : detailElement.select("pre[name=code]")) {
                element.attr("class", "brush: java; gutter: false;");
            }

            return detailElement.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBlogTitle(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        try {
            Element titleElement = Jsoup.parse(html).getElementsByClass("article_title").get(0);
            return titleElement.select("h1").select("span").select("a").text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBlogTitle2(html);
    }

    private static String getBlogTitle2(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        try {
            Element titleElement = Jsoup.parse(html).getElementsByClass("csdn_top").get(0);
            return titleElement.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Comment> getCommentList(String json, int pageIndex, int pageSize) {
        List<Comment> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            int index = 0;
            int len = jsonArray.length();

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

    public static List<BlogColumn> getColumnList(String html, String category) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        List<BlogColumn> columnList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        try {
            Elements columnWrap = doc.getElementsByClass("column_wrap");
            for (Element element : columnWrap) {
                Elements columnElements = element.getElementsByClass("column_list");

                for (Element column : columnElements) {
                    BlogColumn blogColumn = new BlogColumn();
                    Element bgElement = column.getElementsByClass("column_bg").get(0);
                    String bgUrl = bgElement.attr("style").replace("background-image:url(", "")
                            .replace(")", "");

                    Element aElement = column.getElementsByClass("column_list_link").get(0);
                    String hostUrl = StringUtils.trimLastChar(Config.BLOG_HOST);
                    String url = aElement.attr("href");
                    if (!TextUtils.isEmpty(url) && !url.contains(hostUrl)) {
                        url = hostUrl + url;
                    }
                    String title = aElement.getElementsByClass("column_list_p").get(0).text();
                    String size = aElement.getElementsByClass("column_list_b_l").get(0).select("span").text();
                    String viewCount = aElement.getElementsByClass("column_list_b_r").get(0).select("span").text();

                    blogColumn.setIcon(bgUrl);
                    blogColumn.setUrl(url);
                    blogColumn.setName(title);
                    blogColumn.setSize(size);
                    blogColumn.setViewCount(viewCount);
                    blogColumn.setCategory(category);
                    columnList.add(blogColumn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return columnList;
    }

    public static List<BlogItem> getColumnDetail(String html, String category) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        List<BlogItem> blogList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        try {
            Elements detailListLi = doc.getElementsByClass("blog_l").select("ul").select("li");

            String page = doc.getElementsByClass("page_nav").select("span").text();
            int totalPage = getBlogTotalPage(page);
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
                blogItem.setTotalPage(totalPage);
                blogList.add(blogItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return blogList;
    }

    public static List<BlogRank> getBlogRank(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        List<BlogRank> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(html);
            Elements blogRanks = doc.getElementsByClass("ranking");
            for (Element rank : blogRanks) {
                BlogRank blogRank = new BlogRank();
                String RankTitle = rank.getElementsByClass("rank_t").text();

                Elements liList = rank.select("ul").select("li");
                List<RankItem> ranks = new ArrayList<>();
                for (Element li : liList) {
                    Element aElement = li.select("label").select("a").get(0);
                    String url = aElement.attr("href");
                    String icon = aElement.select("img").attr("src");
                    String title = aElement.attr("title");
                    if (TextUtils.isEmpty(title)) {
                        title = li.getElementsByClass("blog_a").text();
                    }
                    if (TextUtils.isEmpty(title)) {
                        title = li.getElementsByClass("star_name").text();
                    }
                    String viewCount = li.select("label").select("span").select("b").text();

                    RankItem rankItem = new RankItem();
                    rankItem.setIcon(icon);
                    rankItem.setUrl(url);
                    rankItem.setName(title);
                    rankItem.setViewCount(viewCount);
                    ranks.add(rankItem);
                }

                blogRank.setName(RankTitle);
                blogRank.setData(ranks);
                list.add(blogRank);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<BlogItem> getHotBlog(String html, String category) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        List<BlogItem> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(html);
            String page = doc.getElementsByClass("page_nav").select("span").text();
            int totalPage = getBlogTotalPage(page);

            Elements blogElements = doc.getElementsByClass("blog_list");
            for (Element blogElement : blogElements) {
                Element h3 = blogElement.getElementsByClass("csdn-tracking-statistics").first();
                String title = h3.select("a").text();
                String url = h3.select("a").attr("href");
                String desc = blogElement.getElementsByClass("blog_list_c").text();

                Element detailBDiv = blogElement.getElementsByClass("blog_list_b_r").first();
                String date = detailBDiv.select("label").text();
                String times = detailBDiv.select("span").select("em").text();

                BlogItem blogItem = new BlogItem();
                blogItem.setTitle(title);
                blogItem.setLink(url);
                blogItem.setContent(desc);
                blogItem.setTotalPage(totalPage);
                blogItem.setDate(date + " " + "阅读" + "(" + times + ")");
                blogItem.setCategory(category);
                blogItem.setIcoType(Config.BLOG_TYPE.BLOG_TYPE_ORIGINAL);

                list.add(blogItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
