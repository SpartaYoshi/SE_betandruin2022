package businessLogic;

import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.*;
import exceptions.*;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BlFacade {

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventAlreadyFinishedException if current data is after data of the event
 	 * @throws QuestionAlreadyExistsException if the same question already exists for the event
	 */
	@WebMethod
	Question createQuestion(Event event, String question, float betMinimum) throws EventAlreadyFinishedException, QuestionAlreadyExistsException;
	
	/**
	 * This method creates an event which includes two teams
	 * @param team1 first team
	 * @param team2 second team
	 * @param date in which the event will be done
	 * @return the created event
	 * @throws EventAlreadyFinishedException if current data is after data of the event
	 */
	Event createEvent(String team1, String team2, Date date) throws EventAlreadyFinishedException, TeamPlayingException, IdenticalTeamsException;
		
	/**
	 * This method retrieves all the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod Vector<Event> getEvents(Date date);


	@WebMethod public Vector<Result> getAllResults();
	
	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod
	Vector<Date> getEventsMonth(Date date);

	/**
	 * method that persists a new user into the database
	 * @param user
	 * @return
	 */
	@WebMethod
	String registerUser(User user);
	

	/**
	 * method that ensures a user instance exists in the database
	 * @param username
	 * @param password
	 * @return
	 * @throws FailedLoginException
	 */
	@WebMethod
	User loginUser(String username, String password) throws FailedLoginException;

	/**
	 * method to set an specific fee for the result of a question
	 * @param q
	 * @param pResult
	 * @param pFee
	 * @throws FeeAlreadyExistsException
	 */
	@WebMethod
	void createFee(Question q, int pResult, float pFee) throws FeeAlreadyExistsException;

	/**
	 * method to insert the money wanted into the user's account
	 * @param am double
	 * @return double the amount of money available the user has. (-1 if it has failed)
	 */
	@WebMethod
	double insertMoney(double am, Bet bet, String type) throws FailedMoneyUpdateException;

	/**
	 * method to get the actual user logged
 	 * @return User
	 */
	@WebMethod
	User getCurrentUser();

	/**
	 * method to set who is the current user
	 * @param current a User
	 */
	@WebMethod
	void setCurrentUser(User current);

	/**
	 * method executed by the current user, which places a bet and his money is discounted from the total amount
	 * @param amount
	 * @param question
	 * @param result
	 * @param date
	 * @return
	 * @throws NotEnoughMoneyException
	 * @throws MinimumBetException
	 * @throws EventAlreadyFinishedException
	 */
	@WebMethod
	Bet placeBet(double amount, Question question, Result result, Date date) throws NotEnoughMoneyException, MinimumBetException, EventAlreadyFinishedException;

	@WebMethod

	Vector<Question> getQuestions(Event value);

	@WebMethod
	Vector<Bet> getUserBets(Question question, User user);

	/**
	 * method executed by the user, to remove a bet he has made, so he can retake the money inverted
	 * @param currentUser
	 * @param question
	 * @param bet1
	 * @return
	 */
	@WebMethod
	Bet removeCurrentUserBet(User currentUser, Question question, Bet bet1);

	/**
	 * method to know the money the current user has available
	 * @return
	 */
	@WebMethod
	double getMoneyAvailable();

	/**
	 * method to know the minimum Bet of a question of a event
	 * @param q
	 * @return
	 */
	@WebMethod
	double getMoneyMinimumBet(Question q);

	/**
	 * method to set control mode the current user's status mode: Anonymous, user or admin
	 * @param mode
	 */
	@WebMethod
	void setSessionMode(String mode);

	/**
	 * method that returns the session mode of the current user
	 * @return
	 */
	@WebMethod String getSessionMode();

	@WebMethod
	String editProfileUsername(User user);

	@WebMethod
	String editProfilePassword(User user);


	@WebMethod
	Event removeEvent(Event ev);

	@WebMethod
	int markFinalResult(Result r,int finalR);

	@WebMethod
	int processBets(Result r);

	@WebMethod
	void updateResultsFromAPI();
}
