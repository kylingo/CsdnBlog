package com.free.csdn.bean;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;

/**
 * 实体类--基类
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
