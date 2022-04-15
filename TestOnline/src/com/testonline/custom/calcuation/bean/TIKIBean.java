package com.testonline.custom.calcuation.bean;

import java.util.ArrayList;

import com.testonline.custom.calculation.TIKI;
import com.testonline.table.Users;
import com.testonline.webutil.FacesUtil;

public class TIKIBean extends TIKI {

	public ArrayList<TIKI> listOfTIKI(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		return new TIKI().listOfTIKI(u);
	}
	
	public int IQ(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		return new TIKI().convertToTIKI(u);
	}
	
	public int attempt(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		TIKI tikiObj = new TIKI();
		tikiObj.countAttempt(u);
		this.setAttempt(tikiObj.getAttempt());
		return this.getAttempt();
	}
	
}
