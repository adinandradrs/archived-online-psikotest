package com.testonline.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import com.testonline.access.AttemptsAccess;
import com.testonline.table.Attempts;
import com.testonline.table.Categories;
import com.testonline.table.Users;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class AttemptsBean extends Attempts {

	private static final long serialVersionUID = 1L;
	private boolean hasAttempt = true;
	private boolean canAttempt = false;
	private boolean isSubmit = false;
	private boolean isInRange = false;
	
	public boolean saveAttempts(){
		if (FacesUtil.getRequestParameter("catid") != null){
			int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
			Categories c = new Categories();
			Attempts a = new Attempts();
			Users u = (Users)FacesUtil.getSession("user");
			c.setCategoryID(categoryID);
			a.setCategory(c);
			a.setUser(u);
			canAttempt = new AttemptsAccess().saveAttempts(a);
			FacesUtil.doRedirect("question.jsf?catid="+categoryID);
			return canAttempt;
		}
		else {
			return canAttempt;
		}
	}
	
	public boolean getAttempts(){
		if (FacesUtil.getRequestParameter("catid") != null){
			int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
			Users u = (Users)FacesUtil.getSession("user");
			Categories c = new Categories();
			c.setCategoryID(categoryID);
			AttemptsAccess ac = new AttemptsAccess();
			hasAttempt = ac.getAttempts(u,c);
			return hasAttempt;
		}
		else{
			return hasAttempt;
		}
	}
	
	public boolean isSubmit(){
		if(FacesUtil.getRequestParameter("catid") != null){
			int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
			Users u = (Users)FacesUtil.getSession("user");
			Categories c = new Categories();
			c.setCategoryID(categoryID);
			AttemptsAccess ac = new AttemptsAccess();
			isSubmit = ac.isSubmit(u,c);
			return isSubmit;
		}
		else{
			return isSubmit;
		}
	}
	
	public boolean isInRange(){
		if(FacesUtil.getRequestParameter("catid") != null){
			int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
			Users u = (Users)FacesUtil.getSession("user");
			Categories c = new Categories();
			c.setCategoryID(categoryID);
			AttemptsAccess ac = new AttemptsAccess();
			isInRange = ac.isInRange(u, c);
			return isInRange;
		}
		else{
			return isInRange;
		}
	}
}
