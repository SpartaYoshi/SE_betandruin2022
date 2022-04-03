package businessLogic;

import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Event;
import domain.Question;
import domain.User;
import exceptions.*;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BlFacade  {

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod
	Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	/**
	 * This method creates an event which includes two teams
	 * @param team1 first team
	 * @param team2 second team
	 * @param date in which the event will be done
	 * @return the created event
	 * @throws EventFinished if current data is after data of the event
	 */
	public Event createEvent(String team1, String team2, Date date) throws EventFinished, TeamPlayingException, TeamRepeatedException;
		
	/**
	 * This method retrieves all the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	
	@WebMethod public String registerUser(User user);
	

	@WebMethod public User loginUser(String username, String password) throws FailedLoginException;

	
	@WebMethod public void createFee(Question q,String pResult,float pFee) throws FeeAlreadyExists;

	/**
	 * method to insert the money wanted into the user's account
	 * @param am double
	 * @return double the amount of money available the user has. (-1 if it has failed)
	 */
	@WebMethod public double insertMoney(double am) throws FailedMoneyUpdateException;

	/**
	 * method to get the actual user logged
 	 * @return User
	 */
	@WebMethod public User getUser();

	/**
	 * method to set who is the current user
	 * @param current a User
	 */
	@WebMethod public void setUser(User current);

	@WebMethod  public double placeBet(double amount) throws NotEnoughMoneyException, MinimumBetException, FailedMoneyUpdateException

}