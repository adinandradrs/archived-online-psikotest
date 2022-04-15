package com.testonline.custom.calculation;

import com.testonline.table.Users;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class MBTI extends BaseAccess {

	private String result;
	private MBTI MBTIobj;
	
	public String getResult(){
		return this.result;
	}
	public void setResult(String result){
		this.result = result;
	}
	
	public MBTI getMBTI(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT TabA.A, TabB.B, TabA.categoryID, " +
						" CASE " +
						" WHEN TabA.CategoryID = 11 " +
						" THEN " +
						" CASE " +
						" WHEN TabA.A > TabB.B THEN " +
						" 'E' " +
						" WHEN TabA.A < TabB.B THEN " +
						" 'I' " +
						" END " +
						" WHEN TabA.CategoryID = 12 " +
						" THEN " +
						" CASE " +
						" WHEN TabA.A > TabB.B THEN " +
						" 'S' " +
						" WHEN TabA.A < TabB.B THEN " +
						" 'N' " +
						" END " +
						" WHEN TabA.CategoryID = 13 " +
						" THEN " +
						" CASE " +
						" WHEN TabA.A > TabB.B THEN " +
						" 'T' " +
						" WHEN TabA.A < TabB.B THEN " +
						" 'F' " +
						" END " +
						" WHEN TabA.CategoryID = 14 " +
						" THEN " +
						" CASE " +
						" WHEN TabA.A > TabB.B THEN " +
						" 'J' " +
						" WHEN TabA.A < TabB.B THEN " +
						" 'P' " +
						" END " +
						" END AS 'Result' " +
						" FROM " +
						" (SELECT B.Total as 'B', B.categoryid FROM (select count(f.result) as 'Total',f.result, c.categoryid from forms f " +
						" join questions q on f.questionid = q.questionid " + 
						" join categories c on c.categoryid = q.categoryid " +
						" where f.questionid in " +
						" (select questionid " +
						" from questions where categoryid in ( " +
						" SELECT categoryid FROM categories where parent = 9 " +
						" ) " +
						" ) " +
						" and f.userid = ? " +
						" group by f.result,c.categoryid) as B " +
						" WHERE B.result = 0) as TabB " +
						" JOIN " +
						" (SELECT A.Total as 'A', A.categoryid FROM (select count(f.result) as 'Total',f.result, c.categoryid from forms f " +
						" join questions q on f.questionid = q.questionid " +
						" join categories c on c.categoryid = q.categoryid " +
						" where f.questionid in " +
						" (select questionid " +
						" from questions where categoryid in ( " +
						" SELECT categoryid FROM categories where parent = 9 " +
						" ) " +
						" ) " +
						" and f.userid = ? " +
						" group by f.result,c.categoryid) as A " +
						" WHERE A.result = 1) as TabA ON TabB.categoryid = TabA.categoryid;";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.preparedStatement.setString(2, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			String iterator = "";
			while(resultSet.next())
				iterator += resultSet.getString("result");
			MBTIobj = new MBTI();
			MBTIobj.setResult(iterator);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return MBTIobj;
	}
	
}
