package com.testonline.bean;

import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletRequest;
import com.testonline.access.AnswersAccess;
import com.testonline.access.AttemptsAccess;
import com.testonline.access.CategoriesAccess;
import com.testonline.access.FormsAccess;
import com.testonline.table.Answers;
import com.testonline.table.Attempts;
import com.testonline.table.Categories;
import com.testonline.table.Forms;
import com.testonline.table.Questions;
import com.testonline.table.Users;
import com.testonline.webutil.FacesUtil;

@ManagedBean
@SessionScoped
public class FormsBean extends Forms {

	private static final long serialVersionUID = 1L;
	private ArrayList<Questions> listOfQuestions = new QuestionsBean().listOfQuestions();
	
	public void saveForms(){
		HttpServletRequest request = FacesUtil.getRequestParameter();
		Users u = (Users)FacesUtil.getSession("user");
		Forms f = null;
		Answers a = null;
		FormsAccess fa = new FormsAccess();
		for (Questions q : listOfQuestions) {
			if(q.getIsMulti() == 0 && q.getIsEssay() == 0){
				if(FacesUtil.getRequestParameter(String.valueOf(q.getQuestionID())) != null){
					f = new Forms();
					a = new Answers();
					f.setQuestion(q);
					a.setAnswerID(Integer.parseInt(FacesUtil.getRequestParameter(String.valueOf(q.getQuestionID()))));
					f.setAnswer(a);
					f.setUser(u);
					f.setResult(this.getDescision(a));
					fa.saveForms(f);
				}
			}
			else if(q.getIsMulti() == 1 && q.getIsEssay() == 0){
				String [] multiAnswers = request.getParameterValues(String.valueOf(q.getQuestionID()));
				try{
					for (String answer : multiAnswers) {
						f = new Forms();
						a = new Answers();
						f.setQuestion(q);
						a.setAnswerID(Integer.parseInt(answer));
						f.setAnswer(a);
						f.setUser(u);
						f.setResult(this.getDescision(a));
						fa.saveForms(f);
					}
				}
				catch(Exception e){
					//if null in my array happened
				}
//				Koding lama
//				if(FacesUtil.getRequestParameter(String.valueOf(q.getQuestionID())) != null){
//					f = new Forms();
//					a = new Answers();
//					f.setQuestion(q);
//					a.setAnswerID(Integer.parseInt(FacesUtil.getRequestParameter(String.valueOf(q.getQuestionID()))));
//					f.setAnswer(a);
//					f.setUser(u);
//					f.setResult(this.getDescision(a));
//					fa.saveForms(f);
//				}
			}
/*			else if(q.getIsMulti() == 1 && q.getIsAllowMultiKey() == 1 && q.getIsEssay() == 0){
				String [] multiAnswers = request.getParameterValues(String.valueOf(q.getQuestionID()));
				try{
					for (String answer : multiAnswers) {
						f = new Forms();
						a = new Answers();
						f.setQuestion(q);
						a.setAnswerID(Integer.parseInt(answer));
						f.setAnswer(a);
						f.setUser(u);
						f.setResult(this.getDescision(a));
						fa.saveForms(f);
					}
				}
				catch(Exception e){
					//if null in my array happened
				}
			}
*/
			else if(q.getIsEssay() == 1){
				if(FacesUtil.getRequestParameter(String.valueOf(q.getQuestionID())) != null){
					String userDesc = FacesUtil.getRequestParameter(String.valueOf(q.getQuestionID()));
					if(userDesc.trim().length() > 0){
						f = new Forms();
						a = new Answers();
						f.setQuestion(q);
						a.setAnswerID(Integer.parseInt("0"));
						f.setAnswer(a);
						f.setUser(u);
						f.setDescription(userDesc);
						f.setResult(this.getDescision(q,userDesc));
						fa.saveForms(f);
					}
				}
			}
		}
		String categoryID = FacesUtil.getRequestParameter("catid");
		Categories c = new Categories();
		Attempts a1 = new Attempts();
		c.setCategoryID(Integer.parseInt(categoryID));
		a1.setUser(u);
		a1.setCategory(c);
		a1.setIsSubmit(1);
		new AttemptsAccess().updateAttempts(a1);
		int workflow = new CategoriesAccess().getCategories(c).getWorkflow();
		if (workflow != 0)
			FacesUtil.doRedirect("introduction.jsf?catid=" + workflow);
		else 
			FacesUtil.doRedirect("index.jsf");
	}
	
	private int getDescision(Answers a){
		Answers getKey = new AnswersAccess().getAnswers(a);
		if (getKey.getIsKey() == 1)
			return 1;
		else 
			return 0;
	}
	
	private int getDescision(Questions q, String userDesc){
		Answers getKey = new AnswersAccess().getAnswers(q);
		if(userDesc.trim().equalsIgnoreCase(getKey.getDescription().replaceAll("\\<.*?>", "").trim()))
			return 1;
		else 
			return 0;
	}
	
}
