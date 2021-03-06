package com.free.blog.model.entity;

import com.lidroid.xutils.db.annotation.Column;

import java.io.Serializable;

/**
 * 实体类--基类
 *
 * @author tangqi
 * @since 2015年8月01日下午10:42:07
 */
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 4995176180527325406L;
	
	@Column(column = "id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
