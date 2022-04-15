package com.testonline.bean;

import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import com.testonline.access.CategoriesAccess;
import com.testonline.table.Categories;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class CategoriesBean extends Categories {

	private static final long serialVersionUID = 1L;

	private ArrayList<Categories> categoriesSet = null;
	private Categories categoriesObj = null;
	
	public ArrayList<Categories> listOfCategories(){
		if (FacesUtil.getRequestParameter("catid") != null){
			int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
			Categories c = new Categories();
			c.setCategoryID(categoryID);
			categoriesSet = new CategoriesAccess().listOfCategories(c);
			return categoriesSet;
		}
		else {
			return new CategoriesAccess().listOfCategories();
		}
	}
	
	public Categories getCategories(){
		Categories c = null;
		c = new Categories();
		if (FacesUtil.getRequestParameter("catid") != null){
			int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
			if(FacesUtil.getSession("catid") == null)
				FacesUtil.setSession("catid", categoryID);
			c.setCategoryID(categoryID);
			categoriesObj = new CategoriesAccess().getCategories(c);
		}
		else if(FacesUtil.getRequestParameter("catid") == null){
			int categoryID = Integer.parseInt(FacesUtil.getSession("catid").toString());
			c.setCategoryID(categoryID);
			categoriesObj = new CategoriesAccess().getCategories(c);
		}
		return categoriesObj;		
		
	}
	
	public void updateCategories(){
		String description = FacesUtil.getRequestParameter("categories:editor1").toString();
		int timeInMinutes = Integer.parseInt(FacesUtil.getRequestParameter("categories:timeInMinutes"));
		int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
		Categories c = new Categories();
		c.setCategoryID(categoryID);
		c.setDescription(description);
		c.setTimeInMinutes(timeInMinutes);
		CategoriesAccess ca = new CategoriesAccess();
		boolean commited = ca.updateCategories(c);
		if(commited == true)
			FacesUtil.doRedirect("categories.jsf");
	}
}
