package com.testonline.bean;

import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import com.testonline.access.UsersAccess;
import com.testonline.table.Groups;
import com.testonline.table.Users;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class UsersBean extends Users{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Users> usersSet = null;
	private Users usersObj = null;
	
	public ArrayList<Users> listOfUsers(){
		Groups g = null;
		if(FacesUtil.getRequestParameter("groupid")!= null){
			g = new Groups();
			g.setGroupID(Integer.parseInt(FacesUtil.getRequestParameter("groupid")));
			FacesUtil.setSession("groupid", g.getGroupID());
		}
		else{
			if(FacesUtil.getSession("group") == null){
				int groupID = Integer.parseInt(FacesUtil.getRequestParameter("groupid"));
				g = new Groups();
				g.setGroupID(groupID);
				FacesUtil.setSession("group", g);
			}
			else
				g = (Groups) FacesUtil.getSession("group");
		}
		usersSet = new UsersAccess().listOfUsers(g);
		if(usersSet.size() == 0){
			Users u = new Users();
			u.setUserID("<i>Add New User ID</i>");
			u.setFirstName("<i>Add New First Name</i>");
			u.setLastName("<i>Add New Last Name</i>");
			usersSet.add(u);
		}
		return usersSet;
	}
	
	public void doValidation(){
		if(FacesUtil.getSession("hasLogin") != null){
			FacesUtil.doRedirect("pages/index.jsf");
		}
	}
	
	public void doLogout(){
		FacesUtil.setSession("hasLogin", null);
		FacesUtil.setSession("user", null);
		FacesUtil.doRedirect("login.jsf");
	}
	
	public void doLogin(){
		Users u = new Users();
		u.setUserID(this.getUserID());
		u.setPassword(this.getPassword());
		UsersAccess ua = new UsersAccess();
		boolean canLogin = ua.canLogin(u);
		if (canLogin == true){
			FacesUtil.setSession("hasLogin", true);
			FacesUtil.setSession("user", ua.getUsers(u));
			if (ua.getUsers(u).getIsAdmin() == 0)
				FacesUtil.doRedirect("pages/index.jsf");
			else
				FacesUtil.doRedirect("admin/index.jsf");
		}
	}

	public Users getUsers(){
		Users u = (Users) FacesUtil.getSession("user");
		return u;
	}
	
	public Users getUsersByID(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		usersObj = new UsersAccess().getUsersByID(u);
		return usersObj;
	}
	
	public Users getUsersForReport(){
		String userID = FacesUtil.getRequestParameter("userid");
		Users u = new Users();
		u.setUserID(userID);
		Users uObj = new UsersAccess().getUsersForReport(u);
		return uObj;
	}
	
	public void saveUsers(){
		boolean commited = false;
		this.setIsAdmin(0);
		Groups g = new Groups();
		g.setGroupID(Integer.parseInt(String.valueOf(FacesUtil.getSession("groupid"))));
		this.setGroup(g);
		UsersAccess ua = new UsersAccess();
		commited = ua.saveUsers(this);
		if (commited==true)
			FacesUtil.doRedirect("users.jsf?groupid="+FacesUtil.getSession("groupid"));
	}
	
	public void updateUsers(){
		boolean commited = false;
		Users u = new Users();
		u.setFirstName(FacesUtil.getRequestParameter("users:firstName"));
		u.setLastName(FacesUtil.getRequestParameter("users:lastName"));
		u.setPassword(FacesUtil.getRequestParameter("users:password"));
		u.setUserID(FacesUtil.getRequestParameter("userid"));
		commited = new UsersAccess().updateUsers(u);
		if(commited==true)
			FacesUtil.doRedirect("users.jsf?groupid="+FacesUtil.getSession("groupid"));
	}
	
	public void deleteUsers(){
		boolean commited = false;
		Users u = new Users();
		u.setUserID(FacesUtil.getRequestParameter("userid"));
		commited = new UsersAccess().deleteUsers(u);
		if(commited == true)
			FacesUtil.doRedirect("users.jsf?groupid="+FacesUtil.getSession("groupid"));
	}
	
}
