package com.free.csdn.bean;

/**
 *
 * @author tangqi
 * @data 2015年8月9日下午2:21:54
 */

public class Channel extends BaseEntity {

	private static final long serialVersionUID = 3205931841537722040L;

	/**
	 * 频道名
	 */
	private String channelName;

	/**
	 * 频道图标地址
	 */
	private String imgUrl;

	/**
	 * 频道图标ID（本地）
	 */
	private int imgResourceId;

	/**
	 * 保留
	 */
	private String reserve;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getImgResourceId() {
		return imgResourceId;
	}

	public void setImgResourceId(int imgResourceId) {
		this.imgResourceId = imgResourceId;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
}
