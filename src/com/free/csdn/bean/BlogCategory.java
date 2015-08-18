package com.free.csdn.bean;

import com.lidroid.xutils.db.annotation.Column;

/**
 * 博客分类
 *
 * @author tangqi
 * @data 2015年8月18日下午10:42:07
 */
public class BlogCategory extends BaseEntity {
	private static final long serialVersionUID = -8366599113596257949L;

	@Column(column = "name")
	private String name;

	@Column(column = "link")
	private String link;
	
	@Column(column = "image")
	private String image;

	@Column(column = "content")
	private String content;

	@Column(column = "updateTime")
	private String updateTime;

	@Column(column = "reserve")
	private String reserve;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
