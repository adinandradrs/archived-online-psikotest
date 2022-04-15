package com.testonline.access;

import java.util.ArrayList;

import com.testonline.table.Answers;
import com.testonline.table.Categories;
import com.testonline.table.Forms;
import com.testonline.table.Questions;
import com.testonline.table.Users;
import com.testonline.util.BaseAccess;
import com.testonline.util.DBConnection;

public class FormsAccess extends BaseAccess {
	
	private boolean executed = false;
	private ArrayList<Forms> formsSet = null;
	private Forms formsObj = null;
	
	public boolean saveForms(Forms f){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "INSERT INTO FORMS (userid, answerid, questionid, result, description) VALUES(?,?,?,?,?)";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setString(1, f.getUser().getUserID());
			this.preparedStatement.setInt(2, f.getAnswer().getAnswerID());
			this.preparedStatement.setInt(3, f.getQuestion().getQuestionID());
			this.preparedStatement.setInt(4, f.getResult());
			this.preparedStatement.setString(5, f.getDescription());
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
		return executed;
	}
	
	public ArrayList<Forms> listOfForms(Users u, Categories c){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "select q.description as question,q.questionid from forms f " +
					" join questions q on q.questionid = f.questionid " +
					" join categories c on c.categoryid = q.categoryid " +
					" join answers a on a.answerid = f.answerid " +
					" where userid= ? and c.categoryid = ? " +
					" group by q.questionid";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.preparedStatement.setInt(2, c.getCategoryID());
			this.resultSet = this.preparedStatement.executeQuery();
			this.formsSet = new ArrayList<Forms>();
			int i = 1;
			while(this.resultSet.next()){
				this.formsObj = new Forms();
				Questions q = new Questions();
				q.setQuestionNum(i);
				q.setQuestionID(this.resultSet.getInt("questionid"));
				q.setDescription(this.resultSet.getString("question"));
				Categories c1 = new Categories();
				c1.setCategoryID(c.getCategoryID());
				q.setCategory(c1);
				this.formsObj.setQuestion(q);
				this.formsSet.add(this.formsObj);
				i++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.formsSet;
	}
	
	public ArrayList<Forms> listOfForms(Users u, Categories c, Questions q){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "select a.description as answer, a.iskey, f.result from forms f " +
					" join questions q on q.questionid = f.questionid " +
					" join categories c on c.categoryid = q.categoryid " +
					" join answers a on a.answerid = f.answerid " +
					" where userid= ? and c.categoryid = ? " +
					" and f.questionid = ?";
			this.preparedStatement = this.connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
			this.preparedStatement.setInt(2, c.getCategoryID());
			this.preparedStatement.setInt(3, q.getQuestionID());
			this.resultSet = this.preparedStatement.executeQuery();
			this.formsSet = new ArrayList<Forms>();
			while(this.resultSet.next()){
				this.formsObj = new Forms();
				Answers a = new Answers();
				a.setIsKey(this.resultSet.getInt("iskey"));
				a.setDescription(this.resultSet.getString("answer"));
				this.formsObj.setAnswer(a);
				this.formsObj.setResult(this.resultSet.getInt("result"));
				this.formsSet.add(this.formsObj);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.closeAccess();
		}
		return this.formsSet;
	}
	
	public boolean deleteForms(Users u){
		this.connect = new DBConnection().getConnection();
		try{
			this.sql = "DELETE FROM forms WHERE userid = ?";
			this.preparedStatement = connect.prepareStatement(sql);
			this.preparedStatement.setString(1, u.getUserID());
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
		return executed;
	}

}
