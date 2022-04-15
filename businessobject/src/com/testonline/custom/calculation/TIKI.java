package com.testonline.custom.calculation;

import java.util.ArrayList;

import com.testonline.table.Categories;
import com.testonline.table.Users;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class TIKI extends BaseAccess {
	
	private String name;
	private int total;
	private int convert;
	private ArrayList<TIKI> TIKISet;
	private int attempt;
	
	public String getName(){
		return this.name;
	}
	
	public int getTotal(){
		return this.total;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setTotal(int total){
		this.total = total;
	}
	
	public void setAttempt(int attempt){
		this.attempt = attempt;
	}
	
	public int getAttempt(){
		return this.attempt;
	}
	
	public void countAttempt(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "select count(categoryid) as total from (select categoryid from (select c.categoryid " +
					" from forms f " + 
					" join questions q on f.questionid = q.questionid " +
					" join categories c on c.categoryid = q.categoryid " +
					" where f.userid = ? and " +
					" c.categoryid in (select c.categoryid from categories c where c.parent = 2)) as temp " +
					" group by categoryid) as temp2;"; 
			this.preparedStatement = this.connect.prepareStatement(this.sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			while(this.resultSet.next())
				this.setAttempt(this.resultSet.getInt("total"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
	}
	
	public int convertToTIKI(Users u){
		this.connect = new DBConnection().getConnection();
		int sum = 0;
		for (TIKI tiki : this.listOfTIKI(u)) 
			sum += tiki.getTotal();
		this.countAttempt(u);
		if (this.getAttempt() <= 4){
			try{
				this.sql = "SELECT * FROM cust_iq WHERE score = ?";
				this.preparedStatement = this.connect.prepareStatement(sql);
				this.preparedStatement.setInt(1, sum);
				this.resultSet = this.preparedStatement.executeQuery();
				while(this.resultSet.next())
					this.convert = this.resultSet.getInt("result");
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				this.closeAccess();
			}
		}
		else
			this.convert = sum;
		return this.convert;
	}
	
	public ArrayList<TIKI> listOfTIKI(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			String sql1 = "select count(f.formid) as Total, c.name " +
							" from forms f " +
							" join questions q on f.questionid = q.questionid " +
							" join categories c on q.categoryid = c.categoryid " +
							" and c.categoryid = ? " +
							" and f.userid = ? " +
							" and f.result = 1 ";
			String sql2 = "select count(questionid) as Total,name from ( " +
							" select (CASE WHEN sum(result) != count(f.questionid) THEN 0 ELSE 1 END) as final, f.questionid, c.name " +
							" from forms f " +
							" join questions q on f.questionid = q.questionid " +
							" join categories c on c.categoryid = q.categoryid " +
							" where " +
							" f.questionid in ( " +
							" select q2.questionid " + 
							" from questions q2 " +
							" where categoryid = ? " + 
							" ) and userid = ? group by f.questionid " +
							" ) as temp where final = 1";
			Categories c = new Categories();
			this.TIKISet = new ArrayList<TIKI>();
			
			c.setCategoryID(3);
			TIKISet.add(this.getTiki(u, c, sql1));
			
			c.setCategoryID(4);
			TIKISet.add(this.getTiki(u, c, sql2));
			
			c.setCategoryID(5);
			TIKISet.add(this.getTiki(u, c, sql2));
			
			c.setCategoryID(6);
			TIKISet.add(this.getTiki(u, c, sql2));
			
			c.setCategoryID(7);
			TIKISet.add(this.getTiki(u, c, sql1));
			
			c.setCategoryID(8);
			TIKISet.add(this.getTiki(u, c, sql1));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.TIKISet;
	}
	
	private TIKI TIKIObj = null;
	
	private TIKI getTiki (Users u, Categories c,String sql){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = sql;
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, c.getCategoryID());
			this.preparedStatement.setString(2, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			while(this.resultSet.next()){
				this.TIKIObj = new TIKI();
				this.TIKIObj.setName(this.resultSet.getString("name"));
				this.TIKIObj.setTotal(this.resultSet.getInt("Total"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.TIKIObj;
	}
	
}
