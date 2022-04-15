package com.testonline.custom.calcuation.bean;

import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import com.testonline.access.FormsAccess;
import com.testonline.table.Categories;
import com.testonline.table.Forms;
import com.testonline.table.Questions;
import com.testonline.table.Users;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class ReportBean {
	
	private ArrayList<Forms> formsSet = null;
	
	public ArrayList<Forms> listOfFormsMasterForReport(){
		String userID = FacesUtil.getRequestParameter("userid");
		int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
		Users u = new Users();
		u.setUserID(userID);
		Categories c = new Categories();
		c.setCategoryID(categoryID);
		formsSet = new FormsAccess().listOfForms(u, c);
		return formsSet;
	}
	
	public ArrayList<Forms> listOfFormsMasterForReport(int paramCategoryID){
		String userID = FacesUtil.getRequestParameter("userid");
		int categoryID = paramCategoryID;
		Users u = new Users();
		u.setUserID(userID);
		Categories c = new Categories();
		c.setCategoryID(categoryID);
		formsSet = new FormsAccess().listOfForms(u, c);
		return formsSet;
	}
	
	public ArrayList<Forms> listOfFormsDetailForReport(Questions q){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		Categories c = q.getCategory();
		formsSet = new FormsAccess().listOfForms(u, c, q);
		return formsSet;
	}
	
}
