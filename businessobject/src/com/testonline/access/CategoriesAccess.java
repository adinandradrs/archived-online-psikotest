package com.testonline.access;

import java.util.ArrayList;

import com.testonline.table.Categories;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class CategoriesAccess extends BaseAccess {
	
	private ArrayList<Categories> categoriesSet = null;
	private Categories categoriesObj = null;
	private boolean executed = false;
	
	public ArrayList<Categories> listOfCategories(Categories c){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT * FROM categories WHERE parent = ?";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, c.getCategoryID());
			this.resultSet = this.preparedStatement.executeQuery();
			this.categoriesSet = new ArrayList<Categories>();
			while(resultSet.next()){
				this.categoriesObj = new Categories();
				this.categoriesObj.setCategoryID(resultSet.getInt("categoryid"));
				this.categoriesObj.setName(resultSet.getString("name"));
				this.categoriesObj.setTimeInMinutes(resultSet.getInt("timeinminutes"));
				this.categoriesObj.setDescription(resultSet.getString("description"));
				categoriesSet.add(categoriesObj);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.categoriesSet;
	}
	
	public Categories getCategories(Categories c){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT * FROM categories WHERE categoryid = ?";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, c.getCategoryID());
			this.resultSet = this.preparedStatement.executeQuery();
			this.categoriesSet = new ArrayList<Categories>();
			while(resultSet.next()){
				this.categoriesObj = new Categories();
				this.categoriesObj.setCategoryID(resultSet.getInt("categoryid"));
				this.categoriesObj.setName(resultSet.getString("name"));
				this.categoriesObj.setWorkflow(resultSet.getInt("workflow"));
				this.categoriesObj.setTimeInMinutes(resultSet.getInt("timeinminutes"));
				this.categoriesObj.setDescription(resultSet.getString("description"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.categoriesObj;
	}
	
	public ArrayList<Categories> listOfCategories(){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT c1.*, c2.name as 'ParentName' FROM categories c1 left join categories c2 on c1.parent = c2.categoryid;";
			this.preparedStatement = connect.prepareStatement(sql);
			this.resultSet = this.preparedStatement.executeQuery();
			this.categoriesSet = new ArrayList<Categories>();
			while(resultSet.next()){
				this.categoriesObj = new Categories();
				this.categoriesObj.setCategoryID(resultSet.getInt("categoryid"));
				this.categoriesObj.setName(resultSet.getString("name"));
				this.categoriesObj.setTimeInMinutes(resultSet.getInt("timeinminutes"));
				this.categoriesObj.setDescription(resultSet.getString("description"));
				Categories parent = new Categories();
				parent.setCategoryID(resultSet.getInt("parent"));
				parent.setName(resultSet.getString("parentname"));
				this.categoriesObj.setParent(parent);
				categoriesSet.add(categoriesObj);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.categoriesSet;
	}
	
	public boolean updateCategories(Categories c){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "UPDATE categories set description=?, timeinminutes=? WHERE categoryid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, c.getDescription());
			this.preparedStatement.setInt(2, c.getTimeInMinutes());
			this.preparedStatement.setInt(3, c.getCategoryID());
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
		return this.executed;
	}
	
}
