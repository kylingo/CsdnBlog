package com.free.blog.model.entity;

import java.util.List;

/**
 * @author studiotang on 17/3/25
 */
public class BlogRank extends BaseEntity {

    private String name;

    private List<RankItem> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RankItem> getData() {
        return data;
    }

    public void setData(List<RankItem> data) {
        this.data = data;
    }
}
