package com.testonline.table;

import java.io.Serializable;

public class Categories implements Serializable{

	private static final long serialVersionUID = 1L;

	private int categoryID;
	private String name;
	private int timeInMinutes;
	private String description;
	private Categories parent;
	private int workflow;
	
	public int getWorkflow(){
		return workflow;
	}
	public void setWorkflow(int workflow){
		this.workflow = workflow;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTimeInMinutes() {
		return timeInMinutes;
	}
	public void setTimeInMinutes(int timeInMinutes) {
		this.timeInMinutes = timeInMinutes;
	}
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public Categories getParent(){
		return this.parent;
	}
	public void setParent(Categories parent){
		this.parent = parent;
	}
}
