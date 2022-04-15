package com.testonline.table;

import java.io.Serializable;

public class Attempts implements Serializable {

	private static final long serialVersionUID = 1L;

	private int attemptID;
	private Categories category;
	private Users user;
	private int isSubmit;
	
	public int getIsSubmit() {
		return isSubmit;
	}
	public void setIsSubmit(int isSubmit) {
		this.isSubmit = isSubmit;
	}
	public int getAttemptID() {
		return attemptID;
	}
	public void setAttemptID(int attemptID) {
		this.attemptID = attemptID;
	}
	public Categories getCategory() {
		return category;
	}
	public void setCategory(Categories category) {
		this.category = category;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
}
