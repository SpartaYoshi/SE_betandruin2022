package domain;

import java.io.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {


	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;

	private String questionID;
	private float betMinimum;

	@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Result> resultList = new Vector<>();


	@OneToOne
	@XmlIDREF
	private Event event;





	public Question(){
		super();
	}

	public Question(String questionID) {
		this.questionID = questionID;
	}

	public Question(Integer queryNumber, String questionID, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.questionID = questionID;
		this.betMinimum = betMinimum;
		this.event = event;
	}

	public Question(String query, float betMinimum,  Event event) {
		super();
		this.questionID = query;
		this.betMinimum=betMinimum;

	}


	public Result getResultOption(int option) {
		try {
			Result rCompare = new Result(option);
			int index = -1;
			if (resultList.contains(option))
				index = resultList.indexOf(rCompare);

			return resultList.get(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}

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
	public String getQuestionID() {
		return questionID;
	}


	/**
	 * Sets the question description of the bet
	 * 
	 * @param question to be set
	 */	
	public void setQuestionID(String question) {
		this.questionID = question;
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

	
	public boolean isResultStored(int result) {
		for (Result f:this.resultList) {
			if (f.getPossibleResult() == result)
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {

		return questionID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Question question = (Question) o;
		return Objects.equals(questionID, question.questionID);
	}


}