package domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Event implements Serializable {

	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer eventNumber;
	private Date eventDate;

	private String homeTeam;
	private String awayTeam;

	private boolean processedWithAPI;

	private String description;

	@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL) //when removing also remove questions
	private Vector<Question> questions = new Vector<>();

	private String strDate;

	public Vector<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}



	public Event() {
		super();
	}

	public Event(Integer eventNumber, String homeTeam, String awayTeam, Date eventDate) {
		this.eventNumber = eventNumber;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.eventDate = eventDate;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.strDate = dateFormat.format(eventDate);
		this.description=this.getTeamTemplate();
		processedWithAPI = false;
	}

	public Event(String homeTeam, String awayTeam, Date eventDate) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.eventDate = eventDate;

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.strDate = dateFormat.format(eventDate);
		this.description=this.getTeamTemplate();
		processedWithAPI = false;

	}


	public Question getQuestionByID(String questionID) {
		try {
			Question qCompare = new Question(questionID);
			int index = -1;
			if (questions.contains(questionID))
				index = questions.indexOf(qCompare);

			return questions.get(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}


	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getDescription() {return description;	}

	public void setDescription(String description) {this.description = description;	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}


	public String getStrDate() {
		return strDate;
	}

	/**
	 * This method creates a bet with a question, minimum bet amount and percentage profit
	 * 
	 * @param questionID to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Question addQuestion(String questionID, float betMinimum)  {
		Question q=new Question(questionID, betMinimum, this);
		questions.add(q);
		return q;
	}


	/**
	 * This method checks if the question already exists for that event
	 * 
	 * @param question that needs to be checked if there exists
	 * @return true if the question exists and false in other case
	 */
	public boolean doesQuestionExist(String question)  {	
		for (Question q:this.getQuestions()){
			if (q.getQuestionID().compareTo(question)==0)
				return true;
		}
		return false;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(eventNumber, other.eventNumber);
	}

	public String getTeamTemplate() {
		return homeTeam + " - " + awayTeam;
	}

	@Override
	public String toString() {
		return	"eventDate=" + eventDate +
				", homeTeam='" + homeTeam + '\'' +
				", awayTeam='" + awayTeam;
	}

	public boolean isProcessedWithAPI() {
		return processedWithAPI;
	}

	public void setProcessedWithAPI(boolean processedWithAPI) {
		this.processedWithAPI = processedWithAPI;
	}
}