package com.testonline.custom.calcuation.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import com.testonline.custom.calculation.WPTA;
import com.testonline.table.Users;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class WPTABean extends WPTA {

	public WPTA getWPTA(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users(); 
		u.setUserID(userID);
		return this.getWPTA(u);
	}
	
}
