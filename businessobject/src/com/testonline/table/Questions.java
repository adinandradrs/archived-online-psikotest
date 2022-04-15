package com.testonline.table;

import java.io.Serializable;

public class Questions implements Serializable {

	private static final long serialVersionUID = 1L;

	private int questionID;
	private Categories category;
	private String description;
	private int isMulti;
	private int isEssay;
	private int isAllowMultiKey;
	private int questionNum; //non persistence
	
	public int getQuestionID() {
		return questionID;
	}
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	public Categories getCategory() {
		return category;
	}
	public void setCategory(Categories category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuestionNum(){
		return this.questionNum;
	}
	public void setQuestionNum(int questionNum){
		this.questionNum = questionNum;
	}
	public int getIsMulti() {
		return isMulti;
	}
	public void setIsMulti(int isMulti) {
		this.isMulti = isMulti;
	}
	public int getIsEssay() {
		return isEssay;
	}
	public void setIsEssay(int isEssay) {
		this.isEssay = isEssay;
	}
	public int getIsAllowMultiKey() {
		return isAllowMultiKey;
	}
	public void setIsAllowMultiKey(int isAllowMultiKey) {
		this.isAllowMultiKey = isAllowMultiKey;
	}
}
