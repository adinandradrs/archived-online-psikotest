package com.testonline.custom.calcuation.bean;

import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import com.testonline.custom.calculation.Papikostik;
import com.testonline.table.Users;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class PapikostikBean extends Papikostik {

	public ArrayList<Papikostik> listOfPapikostik(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		return this.listOfPapikostik(u);
	}
	
	public ArrayList<Papikostik> listOfPapikostikNotForChart(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		return this.listOfPapikostikNotForChart(u);
	}
	
}
