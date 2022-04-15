package com.testonline.access;

import java.util.ArrayList;
import com.testonline.table.Groups;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class GroupsAccess extends BaseAccess {
	
	private ArrayList<Groups> groupsSet = null;
	private boolean executed = false;
	private Groups groupsObj = null;
	
	public ArrayList<Groups> listOfGroups(){
		this.connect = new DBConnection().getConnection();
		try{
			groupsSet = new ArrayList<Groups>();
			this.sql = "SELECT * FROM groups";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.resultSet = this.preparedStatement.executeQuery();
			while(resultSet.next()){
				groupsObj = new Groups();
				groupsObj.setGroupID(this.resultSet.getInt("groupid"));
				groupsObj.setName(this.resultSet.getString("name"));
				groupsObj.setExpiredAt(this.resultSet.getString("expiredAt"));
				this.groupsSet.add(groupsObj);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return groupsSet;
	}
	
	public Groups getGroups(Groups g){
		this.connect = new DBConnection().getConnection();
		try{
			groupsSet = new ArrayList<Groups>();
			this.sql = "SELECT * FROM groups where groupid = ?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, g.getGroupID());
			this.resultSet = this.preparedStatement.executeQuery();
			while(resultSet.next()){
				groupsObj = new Groups();
				groupsObj.setGroupID(this.resultSet.getInt("groupid"));
				groupsObj.setName(this.resultSet.getString("name"));
				groupsObj.setExpiredAt(this.resultSet.getString("expiredAt"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return groupsObj;
	}
	
	
	
	public boolean saveGroups(Groups g){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "INSERT INTO groups (name, expiredat) VALUES(?,?)";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, g.getName());
			this.preparedStatement.setString(2, g.getExpiredAt());
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
	
	public boolean deleteGroups(Groups g){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "DELETE FROM groups WHERE groupid = ?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, g.getGroupID());
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
	
	public boolean updateGroups(Groups g){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "UPDATE groups SET name=?, expiredat=? WHERE groupid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, g.getName());
			this.preparedStatement.setString(2, g.getExpiredAt());
			this.preparedStatement.setInt(3, g.getGroupID());
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

}
