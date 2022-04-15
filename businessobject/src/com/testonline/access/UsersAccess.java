package com.testonline.access;

import java.util.ArrayList;

import com.testonline.table.Groups;
import com.testonline.table.Users;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class UsersAccess extends BaseAccess {
	
	private boolean canLogin = false;
	private int totalRow = 0;
	private Users usersObj = null;
	private ArrayList<Users> usersSet = null;
	private boolean executed = false;
	
	public ArrayList<Users> listOfUsers(Groups g){
		this.connect = new DBConnection().getConnection();
		try{
			this.usersSet = new ArrayList<Users>();
			this.sql = "SELECT * FROM users WHERE groupid = ?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, g.getGroupID());
			this.resultSet = this.preparedStatement.executeQuery();
			while(resultSet.next()){
				usersObj = new Users();
				usersObj.setUserID(this.resultSet.getString("userid"));
				usersObj.setFirstName(this.resultSet.getString("firstname"));
				usersObj.setLastName(this.resultSet.getString("lastname"));
				Groups g2 = new Groups();
				g2.setGroupID(this.resultSet.getInt("groupid"));
				usersObj.setGroup(g2);
				this.usersSet.add(usersObj);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return usersSet;
	}
	
	public Users getUsers(Users u){
		connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT * FROM Users WHERE userid=? AND password=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.preparedStatement.setString(2, u.getPassword());
			this.resultSet = this.preparedStatement.executeQuery();
			while (resultSet.next()){
				usersObj = new Users();
				usersObj.setUserID(resultSet.getString("userid"));
				usersObj.setFirstName(resultSet.getString("firstname"));
				usersObj.setLastName(resultSet.getString("lastname"));
				usersObj.setIsAdmin(resultSet.getInt("isadmin"));
				//to be continued
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return usersObj;
	}
	
	public Users getUsersByID(Users u){
		connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT * FROM Users WHERE userid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			while (resultSet.next()){
				usersObj = new Users();
				usersObj.setUserID(resultSet.getString("userid"));
				usersObj.setPassword(resultSet.getString("password"));
				usersObj.setFirstName(resultSet.getString("firstname"));
				usersObj.setLastName(resultSet.getString("lastname"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return usersObj;
	}
	
	public Users getUsersForReport(Users u){
		connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT * FROM Users WHERE userid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			while (resultSet.next()){
				usersObj = new Users();
				usersObj.setUserID(resultSet.getString("userid"));
				usersObj.setFirstName(resultSet.getString("firstname"));
				usersObj.setLastName(resultSet.getString("lastname"));
				//to be continued
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return usersObj;
	}
	
	public boolean canLogin(Users u){
		connect = new DBConnection().getConnection();
		try {
			this.sql = "select count(*) as TotalRow from users u join groups g on u.groupid = g.groupid where g.expiredat > now() and u.userid = ? and u.password = ?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.preparedStatement.setString(2, u.getPassword());
			this.resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				this.totalRow = this.resultSet.getInt("TotalRow");
			if (this.totalRow == 1)
				this.canLogin = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return canLogin;
	}
	
	public boolean saveUsers(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "INSERT INTO users (userid, password,firstname,lastname,isadmin,groupid) VALUES(?,?,?,?,?,?)";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.preparedStatement.setString(2, u.getPassword());
			this.preparedStatement.setString(3, u.getFirstName());
			this.preparedStatement.setString(4, u.getLastName());
			this.preparedStatement.setInt(5, u.getIsAdmin());
			this.preparedStatement.setInt(6, u.getGroup().getGroupID());
			int executed = this.preparedStatement.executeUpdate();
			if (executed != 0)
				this.executed = true;
			else 
				this.executed = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return executed;
	}
	
	public boolean deleteUsers(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "DELETE FROM users WHERE userid = ?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			int executed = this.preparedStatement.executeUpdate();
			if (executed != 0){
				this.executed = true;
			}
			else 
				this.executed = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
			if (this.executed == true){
				new AttemptsAccess().deleteAttempts(u);
				new FormsAccess().deleteForms(u);
			}
		}
		return executed;
	}
	
	public boolean updateUsers(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "UPDATE users SET password=?,firstname=?,lastname=? WHERE userid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getPassword());
			this.preparedStatement.setString(2, u.getFirstName());
			this.preparedStatement.setString(3, u.getLastName());
			this.preparedStatement.setString(4, u.getUserID());
			int executed = this.preparedStatement.executeUpdate();
			if (executed != 0){
				this.executed = true;
			}
			else 
				this.executed = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return executed;
	}
}
