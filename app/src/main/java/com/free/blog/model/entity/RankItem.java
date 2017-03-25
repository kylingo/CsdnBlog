package com.free.blog.model.entity;

import com.lidroid.xutils.db.annotation.Column;

/**
 * @author studiotang on 17/3/25
 */
public class RankItem extends BaseEntity {

    @Column(column = "name")
    private String name;

    @Column(column = "category")
    private String category;

    @Column(column = "icon")
    private String icon;

    @Column(column = "url")
    private String url;

    @Column(column = "size")
    private String size;

    @Column(column = "viewCount")
    private String viewCount;

    @Column(column = "reserve")
    private String reserve;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
