package com.testonline.bean;

import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import com.testonline.access.GroupsAccess;
import com.testonline.table.Groups;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class GroupsBean extends Groups {

	private static final long serialVersionUID = 1L;
	private ArrayList<Groups> groupsSet = null;
	private Groups groupsObj = null;
	
	public ArrayList<Groups> listOfGroups(){
		groupsSet = new GroupsAccess().listOfGroups();
		return groupsSet;
	}
	
	public Groups getGroups(){
		Groups g = new Groups();
		try{
			int groupID = Integer.parseInt(FacesUtil.getRequestParameter("groupid"));
			g.setGroupID(groupID);
			GroupsAccess ga = new GroupsAccess();
			groupsObj = ga.getGroups(g);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return groupsObj;
	}
	
	public void saveGroups(){
		Groups g = new Groups();
		g.setName(this.getName());
		String expiredAt = FacesUtil.getRequestParameter("groups:expiredAtInputDate");
		g.setExpiredAt(expiredAt);
		GroupsAccess ga = new GroupsAccess();
		boolean commited = ga.saveGroups(g);
		if (commited == true)
			FacesUtil.doRedirect("groups.jsf");
	}
	
	public void deleteGroups(){
		Groups g = new Groups();
		boolean commited = false;
		try{
			int groupID = Integer.parseInt(FacesUtil.getRequestParameter("groupid"));
			g.setGroupID(groupID);
			GroupsAccess ga = new GroupsAccess();
			commited = ga.deleteGroups(g);
		}
		catch(Exception e){
			
		}
		if (commited == true)
			FacesUtil.doRedirect("groups.jsf");
	}
	
	public void updateGroups(){
		Groups g = new Groups();
		g.setName(FacesUtil.getRequestParameter("name"));
		g.setExpiredAt(FacesUtil.getRequestParameter("groups:expiredAtInputDate"));
		g.setGroupID(this.getGroups().getGroupID());
		GroupsAccess ga = new GroupsAccess();
		boolean commited = ga.updateGroups(g);
		if (commited == true)
			FacesUtil.doRedirect("groups.jsf");
	}
	
}
