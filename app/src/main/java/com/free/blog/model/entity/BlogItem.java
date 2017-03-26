package com.free.blog.model.entity;

import com.lidroid.xutils.db.annotation.Column;

/**
 * 博客实体类
 *
 * @author tangqi
 * @since 2015年8月6日下午10:28:30
 */
public class BlogItem extends BaseEntity {

    private static final long serialVersionUID = 3348273811892710379L;

    @Column(column = "title")
    private String title; // 标题

    @Column(column = "link")
    private String link; // 文章链接

    @Column(column = "date")
    private String date; // 博客发布时间

    @Column(column = "content")
    private String content; // 文章内容

    @Column(column = "category")
    private String category; // 博客分类

    @Column(column = "isTop")
    private int topFlag;// 是否置顶

    @Column(column = "icoType")
    private String icoType;// 文章类型

    @Column(column = "updateTime")
    private long updateTime;// 更新时间

    @Column(column = "totalPage")
    private int totalPage;

    @Column(column = "reserve")
    private String reserve;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @SuppressWarnings("unused")
    public int getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(int topFlag) {
        this.topFlag = topFlag;
    }

    public String getIcoType() {
        return icoType;
    }

    public void setIcoType(String icoType) {
        this.icoType = icoType;
    }

    @SuppressWarnings("unused")
    public String getReserve() {
        return reserve;
    }

    @SuppressWarnings("unused")
    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    @SuppressWarnings("unused")
    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
