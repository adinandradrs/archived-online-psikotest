package com.testonline.bean;

import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import com.testonline.access.AnswersAccess;
import com.testonline.access.QuestionsAccess;
import com.testonline.table.Answers;
import com.testonline.table.Categories;
import com.testonline.table.Questions;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class QuestionsBean extends Questions {

	private static final long serialVersionUID = 1L;
	private ArrayList<Questions> questionsSet = null;
	private DataModel<Questions[]> tabList = null;
	private int totalAnswers = 1;
	private Questions questionsObj;
	
	public int getTotalAnswers(){
		return this.totalAnswers;
	}
	
	public void setTotalAnswers(int totalAnswers){
		this.totalAnswers = totalAnswers;
	}
	
	public ArrayList<Questions> listOfQuestions(){
		if (FacesUtil.getRequestParameter("catid") != null){
			int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
			Categories c = new Categories();
			c.setCategoryID(categoryID);
			questionsSet = new QuestionsAccess().listOfQuestions(c);
			if(questionsSet.size() == 0){
				Questions q = new Questions();
				q.setQuestionNum(0);
				q.setDescription("<i>Add New Description</i>");
				questionsSet.add(q);
			}
			return questionsSet;
		}
		else{
			if(questionsSet.size() == 0){
				Questions q = new Questions();
				q.setQuestionNum(0);
				q.setDescription("<i>Add New Description</i>");
				questionsSet.add(q);
			}
			return questionsSet;
		}
	}
	
	public ArrayList<Questions> listOfQuestions(Questions[] arr){
		questionsSet = new QuestionsAccess().listOfQuestions(arr);
		return questionsSet;
	}
	
	public DataModel<Questions[]> getQuestionsBatch(){
		if (FacesUtil.getRequestParameter("catid") != null){
			int categoryID = Integer.parseInt(FacesUtil.getRequestParameter("catid"));
			Categories c = new Categories();
			c.setCategoryID(categoryID);
			tabList = new ArrayDataModel<Questions[]>(new QuestionsAccess().getQuestionsBatch(c));
			return tabList;
		}
		else{
			return tabList;
		}
	}
	
	public void saveQuestions(){
		int commited1 = 0;
		boolean commited2 = false;
		boolean commited3 = false;
		this.setIsMulti(0);
		this.setIsEssay(0);
		this.setIsAllowMultiKey(0);
		this.setDescription("");
		Categories c = new Categories();
		if(FacesUtil.getRequestParameter("catid")!=null)
			c.setCategoryID(Integer.parseInt(FacesUtil.getRequestParameter("catid")));
		this.setCategory(c);
		if (FacesUtil.getRequestParameter("questions:isMulti") != null)
			this.setIsMulti(1);
		if (FacesUtil.getRequestParameter("questions:isAllowMultiKey")!=null)
			this.setIsAllowMultiKey(1);
		if (FacesUtil.getRequestParameter("questions:isEssay")!=null)
			this.setIsEssay(1);
		if (FacesUtil.getRequestParameter("questions:description")!= null)
			this.setDescription(FacesUtil.getRequestParameter("questions:description"));
		commited1 = new QuestionsAccess().saveQuestions(this);
		this.setQuestionID(commited1);
		totalAnswers = Integer.parseInt(FacesUtil.getRequestParameter("questions:totalAnwers"));
		for (int i = 1; i <= totalAnswers; i++){
			Answers a = new Answers();
			String description = FacesUtil.getRequestParameter("questions:anwersSet"+i);
			int isKey = 0;
			if (FacesUtil.getRequestParameter("questions:isKey"+i) != null)
				isKey = 1;
			a.setIsKey(isKey);
			a.setDescription(description);
			a.setQuestion(this);
			commited2 = new AnswersAccess().saveAnswers(a);
			if(commited2 == false)
				break;
		}
		commited3 = ((commited1 != 0) && commited2);
		if(commited3 == true)
			FacesUtil.doRedirect("categories-edit.jsf?catid=" + FacesUtil.getRequestParameter("catid"));
	}
	
	public void updateQuestions(){
		int questionID = 0;
		if (FacesUtil.getRequestParameter("questionid") != null)
			questionID = Integer.parseInt(FacesUtil.getRequestParameter("questionid"));
		else
			questionID = Integer.parseInt(String.valueOf(FacesUtil.getSession("questionid"))); 
		String description = FacesUtil.getRequestParameter("questions:description");
		this.setQuestionID(questionID);
		this.setDescription(description);
		boolean commited1 = new QuestionsAccess().updateQuestions(this);
		boolean commited2 = false;
		int size = new AnswersBean().listOfAnswers(this).size();
		for(int i = 1; i<= size;i++){
			Answers a = new Answers();
			description = FacesUtil.getRequestParameter("questions:anwersSet"+(i));
			int isKey = 0;
			int answerID = Integer.parseInt(FacesUtil.getRequestParameter("questions:answerID" + i));
			if (FacesUtil.getRequestParameter("questions:isKey"+i) != null)
				isKey = 1;
			a.setDescription(description);
			a.setIsKey(isKey);
			a.setAnswerID(answerID);
			commited2 = new AnswersAccess().updateAnswers(a);
			if(commited2 == false)
				break;
		}
		boolean commited3 = (commited1 && commited2);
		if(commited3 == true)
			FacesUtil.doRedirect("categories-edit.jsf?catid=" + FacesUtil.getRequestParameter("catid"));
	}
	
	public void deleteQuestions(){
		int questionID = Integer.parseInt(FacesUtil.getRequestParameter("questionid"));
		Questions q = new Questions();
		q.setQuestionID(questionID);
		boolean commited1 = new QuestionsAccess().deleteQuestions(q);
		boolean commited2 = new AnswersAccess().deleteAnswers(q);
		if ((commited1 && commited2) == true)
			FacesUtil.doRedirect("categories-edit.jsf?catid=" + FacesUtil.getRequestParameter("catid"));
	}
	
	public Questions getQuestions(){
		int questionID = 0;
		if (FacesUtil.getRequestParameter("questionid") != null){
			questionID = Integer.parseInt(FacesUtil.getRequestParameter("questionid").toString());
			FacesUtil.setSession("questionid", questionID);
		}
		else if (FacesUtil.getSession("questionid") != null){
			questionID = Integer.parseInt(String.valueOf(FacesUtil.getSession("questionid")));
		}
		Questions q = new Questions();
		q.setQuestionID(questionID);
		questionsObj = new QuestionsAccess().getQuestions(q);
		return questionsObj;
	}
	
}
