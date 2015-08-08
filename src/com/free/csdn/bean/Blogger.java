package com.free.csdn.bean;

/**
 * 博主描述--列表
 * 
 * @author tangqi
 * @data 2015年7月8日
 */

public class Blogger extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6569781303855823679L;

	private String description;
	private String imgUrl;
	private String link;
	private String title;
	private String type;
	private String userId;
	private String reserve;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}