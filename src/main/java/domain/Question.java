package domain;

import java.io.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.List;
import java.util.Vector;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {


	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;

	private String question; 
	private float betMinimum;
	private String resultPrompt;

	@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)

	private Vector<Result> resultList = new Vector<>();


	@ManyToOne
	@JoinColumn(name = "event_event_number")
	@XmlIDREF
	private Event event;

	public Question(){
		super();
	}

	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
		
		
	}

	public Question(String query, float betMinimum,  Event event) {
		super();
		this.question = query;
		this.betMinimum=betMinimum;
		
	}

	/**
	 * Gets the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Assigns the bet number to a question
	 * 
	 * @param questionNumber to be set
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * Gets the question description of the bet
	 * 
	 * @return the bet question
	 */
	public String getQuestion() {
		return question;
	}


	/**
	 * Sets the question description of the bet
	 * 
	 * @param question to be set
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}



	/**
	 * Gets the minimum amount allowed for a bet
	 * 
	 * @return the minimum bet
	 */
	public float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Gets the minimum amount allowed for the bet
	 * 
	 * @param  betMinimum amount to be set
	 */
	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}


	/**
	 * Gets the result of the  query
	 * 
	 * @return the the query result
	 */
	public String getResultPrompt() {
		return resultPrompt;
	}

	/**
	 * Sets the correct result of the  query
	 * 
	 * @param result is the correct result of the query
	 */
	public void setResultPrompt(String result) {
		this.resultPrompt = result;
	}

	/**
	 * Gets the event associated with the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Sets the event associated with the bet
	 * 
	 * @param event to associate with the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	
	/**
	 * Adds the result to the list
	 */


	public Result addtoResultList(Result r) {

		if(r != null) {
			System.out.println("Our object of result is "+ r);
			resultList.add(r);
		}
		return r;

	}
	
	/**
	 * Sets the result associated with the question
	 */
	public List<Result> getResults() {
		return this.resultList;
	}

	
	public boolean resultisAlreadyStored(String result) {
		for (Result f:this.resultList) {
			if (f.getQuestionType().equals(result))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString(){

		return question;
	}	
}