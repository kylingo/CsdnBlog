package com.free.csdn.bean;

import com.lidroid.xutils.db.annotation.Column;

/**
 * 博客实体类
 * 
 * @author wwj_748
 * @date 2014/8/9
 */
public class BlogItem extends BaseEntity{
	@Column(column = "title")
	private String title; // 标题
	
	@Column(column = "link")
	private String link; // 文章链接
	
	@Column(column = "date")
	private String date; // 博客发布时间
	
	@Column(column = "imgLink")
	private String imgLink; // 图片链接
	
	@Column(column = "content")
	private String content; // 文章内容
	
	@Column(column = "msg")
	private String msg; // 消息
	
	@Column(column = "type")
	private int type; // 博客分类

	@Column(column = "viewTime")
	private String viewTime;
	
	@Column(column = "isTop")
	private int topFlag;// 

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

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getViewTime() {
		return viewTime;
	}

	public void setViewTime(String viewTime) {
		this.viewTime = viewTime;
	}

	public int getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(int topFlag) {
		this.topFlag = topFlag;
	}

}
