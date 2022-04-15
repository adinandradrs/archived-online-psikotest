package com.testonline.table;

import java.io.Serializable;

public class Groups implements Serializable {

	private static final long serialVersionUID = 1L;

	private int groupID;
	private String name;
	private String expiredAt;
	
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpiredAt() {
		return expiredAt;
	}
	public void setExpiredAt(String expiredAt) {
		this.expiredAt = expiredAt;
	} 

}
