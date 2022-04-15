package com.testonline.access;

import java.util.ArrayList;
import java.util.Arrays;

import com.testonline.table.Categories;
import com.testonline.table.Questions;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class QuestionsAccess extends BaseAccess {

	private ArrayList<Questions> questionSet = null;
	private Questions questionsObj = null;
	private boolean executed = false;
	
	public ArrayList<Questions> listOfQuestions(Questions[] arr){
		questionSet = new ArrayList<Questions>(Arrays.asList(arr));
		return questionSet;
	}
	
	public Questions getQuestions(Questions q){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT * FROM questions WHERE questionid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, q.getQuestionID());
			this.resultSet = this.preparedStatement.executeQuery();
			while(this.resultSet.next()){
				questionsObj = new Questions();
				Categories c = new Categories();
				c.setCategoryID(this.resultSet.getInt("categoryid"));
				questionsObj.setCategory(c);
				questionsObj.setDescription(this.resultSet.getString("description"));
				questionsObj.setIsAllowMultiKey(this.resultSet.getInt("isallowmultikey"));
				questionsObj.setIsEssay(this.resultSet.getInt("isessay"));
				questionsObj.setIsMulti(this.resultSet.getInt("ismulti"));
				questionsObj.setQuestionID(this.resultSet.getInt("questionid"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.questionsObj;
	}
	
	public Questions[][] getQuestionsBatch(Categories c){
		ArrayList<Questions> list = this.listOfQuestions(c);
		int total = list.size();
		int perPage = 10;
		double result = total / perPage;
		int base = (int) Math.floor(result);
		int spare = total - (perPage * base);
		int resultTab = 0;
		if(spare == 0)
			resultTab = base;
		else 
			resultTab = base + 1;
		Questions [][] arr = new Questions [resultTab][perPage];
		int idx = 0;
		for(int i = 0; i<arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				if(idx < total)
					arr[i][j] = list.get(idx);
				idx++;
			}
		}
		return arr;
	}
	
	public ArrayList<Questions> listOfQuestions(Categories c){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT q.questionid, q.description, c.description, c.timeinminutes, c.categoryid, c.name, q.ismulti, q.isessay, q.isallowmultikey FROM questions q JOIN categories c on q.categoryid = c.categoryid WHERE c.categoryid = ?";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, c.getCategoryID());
			this.resultSet = this.preparedStatement.executeQuery();
			this.questionSet = new ArrayList<Questions>();
			int questionNum = 1;
			while(resultSet.next()){
				this.questionsObj = new Questions();
				Categories categoryObj = new Categories();
				this.questionsObj.setQuestionID(this.resultSet.getInt("questionid"));
				this.questionsObj.setDescription(this.resultSet.getString("description"));
				categoryObj.setCategoryID(resultSet.getInt("categoryid"));
				categoryObj.setName(resultSet.getString("name"));
				categoryObj.setTimeInMinutes(resultSet.getInt("timeinminutes"));
				categoryObj.setDescription(resultSet.getString("description"));
				this.questionsObj.setCategory(categoryObj);
				this.questionsObj.setQuestionNum(questionNum);
				this.questionsObj.setIsMulti(resultSet.getInt("ismulti"));
				this.questionsObj.setIsEssay(resultSet.getInt("isessay"));
				this.questionsObj.setIsAllowMultiKey(resultSet.getInt("isallowmultikey"));
				questionNum++;
				questionSet.add(this.questionsObj);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return questionSet;
	}
	
	public int saveQuestions(Questions q){
		this.connect = new DBConnection().getConnection();
		int lastID = 0;
		try{
			this.sql = "INSERT INTO questions (categoryid, description, ismulti, isessay, isallowmultikey) VALUES(?,?,?,?,?);";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, q.getCategory().getCategoryID());
			this.preparedStatement.setString(2, q.getDescription());
			this.preparedStatement.setInt(3, q.getIsMulti());
			this.preparedStatement.setInt(4, q.getIsEssay());
			this.preparedStatement.setInt(5, q.getIsAllowMultiKey());
			int executed = this.preparedStatement.executeUpdate();
			if(executed != 0){
				this.sql = "SELECT LAST_INSERT_ID() as LastID";
				this.preparedStatement = this.connect.prepareStatement(this.sql);
				this.resultSet = this.preparedStatement.executeQuery();
				while(this.resultSet.next())
					lastID = this.resultSet.getInt("LastID");
			}
			else
				lastID = 0;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return lastID;
	}
	
	public boolean updateQuestions(Questions q){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "UPDATE questions SET description=? WHERE questionid=?;";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, q.getDescription());
			this.preparedStatement.setInt(2, q.getQuestionID());
			int executed = this.preparedStatement.executeUpdate();
			if(executed != 0)
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
	
	public boolean deleteQuestions(Questions q){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "DELETE FROM questions WHERE questionid=?;";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, q.getQuestionID());
			int executed = this.preparedStatement.executeUpdate();
			if(executed != 0)
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
