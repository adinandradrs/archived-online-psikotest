package com.testonline.custom.calculation;

import com.testonline.table.Users;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class WPTA extends BaseAccess {

	private int totalScore;
	private WPTA WPTAobj;
	
	public int getTotalScore(){
		return this.totalScore;
	}
	public void setTotalScore(int totalScore){
		this.totalScore = totalScore;
	}
	
	public WPTA getWPTA(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "select (CASE WHEN sum(result) != count(questionid) THEN 0 ELSE 1 END) as final, questionid from forms where userid = ? and questionid in (select questionid from questions where categoryid = 10) group by questionid";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			int iterator = 0;
			while(resultSet.next()){
				iterator += resultSet.getInt("final");
			}
			WPTAobj = new WPTA();
			WPTAobj.setTotalScore(iterator);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.WPTAobj;
	}
	
}
