package com.testonline.access;

import java.util.ArrayList;

import com.testonline.table.Answers;
import com.testonline.table.Questions;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class AnswersAccess extends BaseAccess {
	
	private ArrayList<Answers> answersSet = null;
	private Answers answersObj = null;
	private boolean executed = false;
	
	public ArrayList<Answers> listOfAnswers(Questions q){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT * FROM answers a join questions q on a.questionid = q.questionid WHERE a.questionid = ?";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, q.getQuestionID());
			this.resultSet = this.preparedStatement.executeQuery();
			this.answersSet = new ArrayList<Answers>();
			while(this.resultSet.next()){
				answersObj = new Answers();
				answersObj.setAnswerID(resultSet.getInt("answerid"));
				answersObj.setDescription(resultSet.getString("description"));
				answersObj.setIsKey(resultSet.getInt("iskey"));
				Questions q1 = new Questions();
				q1.setQuestionID(resultSet.getInt("questionID"));
				answersObj.setQuestion(q1);
				answersSet.add(answersObj);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.answersSet;
	}
	
	public Answers getAnswers(Answers a){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT a.answerid, a.description, a.iskey, q.questionid FROM answers a join questions q on a.questionid = q.questionid WHERE a.answerid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, a.getAnswerID());
			this.resultSet = this.preparedStatement.executeQuery();
			while(resultSet.next()){
				answersObj = new Answers();
				Questions q = new Questions();
				q.setQuestionID(this.resultSet.getInt("questionid"));
				answersObj.setQuestion(q);
				answersObj.setAnswerID(this.resultSet.getInt("answerid"));
				answersObj.setDescription(this.resultSet.getString("description"));
				answersObj.setIsKey(this.resultSet.getInt("iskey"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.answersObj;
	}
	
	public Answers getAnswers(Questions q){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "SELECT a.answerid, a.description, a.iskey, q.questionid FROM answers a join questions q on a.questionid = q.questionid WHERE q.questionid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, q.getQuestionID());
			this.resultSet = this.preparedStatement.executeQuery();
			while(resultSet.next()){
				answersObj = new Answers();
				Questions q1 = new Questions();
				q1.setQuestionID(this.resultSet.getInt("questionid"));
				answersObj.setQuestion(q1);
				answersObj.setAnswerID(this.resultSet.getInt("answerid"));
				answersObj.setDescription(this.resultSet.getString("description"));
				answersObj.setIsKey(this.resultSet.getInt("iskey"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.answersObj;
	}
	
	public boolean saveAnswers(Answers a){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "INSERT INTO answers (questionid, description, iskey) VALUES(?,?,?)";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, a.getQuestion().getQuestionID());
			this.preparedStatement.setString(2, a.getDescription());
			this.preparedStatement.setInt(3, a.getIsKey());
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
	
	public boolean updateAnswers(Answers a){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "UPDATE answers SET description=?, iskey=? WHERE answerid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, a.getDescription());
			this.preparedStatement.setInt(2, a.getIsKey());
			this.preparedStatement.setInt(3, a.getAnswerID());
			int executed = this.preparedStatement.executeUpdate();
			if (executed != 0)
				this.executed = true;
			else
				this.executed = false;
		}
		catch(Exception e){
			
		}
		finally{
			this.closeAccess();
		}
		return this.executed;
	}
	
	public boolean deleteAnswers(Answers a){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "DELETE FROM answers WHERE answerid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, a.getAnswerID());
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
	
	public boolean deleteAnswers(Questions q){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "DELETE FROM answers WHERE questionid=?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setInt(1, q.getQuestionID());
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
