package com.testonline.access;

import java.sql.Timestamp;
import java.util.Date;
import com.testonline.table.Attempts;
import com.testonline.table.Categories;
import com.testonline.table.Users;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class AttemptsAccess extends BaseAccess {

	private boolean executed = false;
	private boolean hasAttempt = true;
	
	public boolean saveAttempts(Attempts a){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "INSERT INTO ATTEMPTS (categoryid,userid,attemptat,issubmit) VALUES(?,?,?,?)";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, a.getCategory().getCategoryID());
			this.preparedStatement.setString(2, a.getUser().getUserID());
			this.preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
			this.preparedStatement.setInt(4, a.getIsSubmit());
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
	
	public boolean updateAttempts(Attempts a){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "UPDATE ATTEMPTS SET issubmit = ? WHERE userid = ? and categoryid = ?";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, a.getIsSubmit());
			this.preparedStatement.setString(2, a.getUser().getUserID());
			this.preparedStatement.setInt(3, a.getCategory().getCategoryID());
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
	
	public boolean deleteAttempts(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "DELETE FROM attempts WHERE userid = ?";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setString(2, u.getUserID());
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
	
	public boolean isSubmit(Users u, Categories c){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT COUNT(*) as TotalRow FROM attempts WHERE categoryid = ? and userid = ? and issubmit = 1";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, c.getCategoryID());
			this.preparedStatement.setString(2, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			int totalRow = 0;
			while(resultSet.next())
				totalRow = resultSet.getInt("TotalRow");
			if (totalRow == 0)
				hasAttempt = false;
			else
				hasAttempt = true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return hasAttempt;
	}
	
	public boolean getAttempts(Users u, Categories c){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT COUNT(*) as TotalRow FROM attempts WHERE categoryid = ? and userid = ?";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, c.getCategoryID());
			this.preparedStatement.setString(2, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			int totalRow = 0;
			while(resultSet.next())
				totalRow = resultSet.getInt("TotalRow");
			if (totalRow == 0)
				hasAttempt = false;
			else
				hasAttempt = true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return hasAttempt;
	}
	
	public boolean isInRange(Users u, Categories c){
		boolean inInterval = false;
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT " +
					" 		CASE " +
					" 			WHEN now() < DATE_ADD(a.attemptat, INTERVAL c.timeinminutes MINUTE) THEN 1 " +
					" 			ELSE 0 " +
					" 		END as 'ininterval' " +
					" 	FROM attempts a " + 
					" 	JOIN categories c ON a.categoryid = c.categoryid where a.userid = ? and c.categoryid = ?;";
			this.preparedStatement = this.connect.prepareStatement(this.sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.preparedStatement.setInt(2, c.getCategoryID());
			this.resultSet = this.preparedStatement.executeQuery();
			while(this.resultSet.next()){
				if (this.resultSet.getInt("ininterval") == 0)
					inInterval = false;
				else 
					inInterval = true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return inInterval;
	}
	
}
