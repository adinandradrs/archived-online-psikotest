package com.testonline.table;

import java.io.Serializable;

public class Answers implements Serializable {

	private static final long serialVersionUID = 1L;

	private int answerID;
	private Questions question;
	private String description;
	private int isKey;
	
	public int getAnswerID() {
		return answerID;
	}
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	public Questions getQuestion() {
		return question;
	}
	public void setQuestion(Questions question) {
		this.question = question;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIsKey() {
		return isKey;
	}
	public void setIsKey(int isKey) {
		this.isKey = isKey;
	}
	
}
