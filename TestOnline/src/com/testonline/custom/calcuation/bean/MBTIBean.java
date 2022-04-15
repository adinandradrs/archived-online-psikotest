package com.testonline.custom.calcuation.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import com.testonline.custom.calculation.MBTI;
import com.testonline.table.Users;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class MBTIBean extends MBTI {

	public MBTI getMBTI(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		return new MBTI().getMBTI(u);
	}
	
}
