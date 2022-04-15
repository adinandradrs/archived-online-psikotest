package com.testonline.bean;

import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import com.testonline.access.AnswersAccess;
import com.testonline.table.Answers;
import com.testonline.table.Questions;

@ManagedBean
@SessionScoped
public class AnswersBean extends Answers {

	private static final long serialVersionUID = 1L;
	private ArrayList<Answers> answersSet = null;
	
	public ArrayList<Answers> listOfAnswers(Questions q){
		answersSet = new AnswersAccess().listOfAnswers(q);
		return answersSet;
	}
	
}
