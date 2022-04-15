package com.testonline.custom.calculation;

import java.util.ArrayList;

import com.testonline.table.Users;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class Papikostik extends BaseAccess {

	private int totalResult;
	private String result;
	private ArrayList<Papikostik> papikostikSet = null;
	private Papikostik papikostikObj = null;
	private int number;
	
	public int getTotalResult(){
		return this.totalResult;
	}
	public void setTotalResult(int totalResult){
		this.totalResult = totalResult;
	}
	public int getNumber(){
		return this.number;
	}
	public void setNumber(int number){
		this.number = number;
	}
	public String getResult(){
		return this.result;
	}
	public void setResult(String result){
		this.result = result;
	}
	
	public ArrayList<Papikostik> listOfPapikostik(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "select count(cp.result) as TotalResult, cp.result, (select max(parent) from cust_papikostik cp2 where cp2.result = cp.result) as 'Parent' " +
						" from forms f " + 
						" join cust_papikostik cp on f.questionid = cp.questionid and f.answerid = cp.answerid " +
						" where f.userid = ? " +
						" group by cp.result " +
						" order by Parent;"; 
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			int i = 0;
			papikostikSet = new ArrayList<Papikostik>();
			while(resultSet.next()){
				papikostikObj = new Papikostik();
				if(this.resultSet.getString("result").equalsIgnoreCase("K") ||this.resultSet.getString("result").equalsIgnoreCase("Z"))
					papikostikObj.setTotalResult(10 - this.resultSet.getInt("TotalResult"));
				else
					papikostikObj.setTotalResult(this.resultSet.getInt("TotalResult"));
				papikostikObj.setResult(this.resultSet.getString("result"));
				papikostikObj.setNumber(i);
				i++;
				papikostikSet.add(papikostikObj);
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.papikostikSet;
	}
	
	public ArrayList<Papikostik> listOfPapikostikNotForChart(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "select count(cp.result) as TotalResult, cp.result, (select max(parent) from cust_papikostik cp2 where cp2.result = cp.result) as 'Parent' " +
						" from forms f " + 
						" join cust_papikostik cp on f.questionid = cp.questionid and f.answerid = cp.answerid " +
						" where f.userid = ? " +
						" group by cp.result " +
						" order by Parent;"; 
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.resultSet = this.preparedStatement.executeQuery();
			int i = 0;
			papikostikSet = new ArrayList<Papikostik>();
			while(resultSet.next()){
				papikostikObj = new Papikostik();
				papikostikObj.setTotalResult(this.resultSet.getInt("TotalResult"));
				papikostikObj.setResult(this.resultSet.getString("result"));
				papikostikObj.setNumber(i);
				i++;
				papikostikSet.add(papikostikObj);
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.papikostikSet;
	}
	
}
