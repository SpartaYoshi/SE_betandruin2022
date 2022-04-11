package businessLogic;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.*;
import exceptions.*;


/**
 * Implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BlFacade")
public class BlFacadeImplementation implements BlFacade {

	DataAccess dbManager;
	ConfigXML config = ConfigXML.getInstance();
	User currentUser;
	Question currentQuestion;

	public BlFacadeImplementation()  {		
		System.out.println("Creating BlFacadeImplementation instance");
		boolean initialize = config.getDataBaseOpenMode().equals("initialize");
		dbManager = new DataAccess(initialize);
		if (initialize)
			dbManager.initializeDB();
		dbManager.close();
	}

	public BlFacadeImplementation(DataAccess dam)  {
		System.out.println("Creating BlFacadeImplementation instance with DataAccess parameter");
		if (config.getDataBaseOpenMode().equals("initialize")) {
			dam.open(true);
			dam.initializeDB();
			dam.close();
		}
		dbManager = dam;		
	}


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
	public Question createQuestion(Event event, String question, float betMinimum) 
			throws EventFinished, QuestionAlreadyExist {

		//The minimum bid must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);		
		dbManager.close();
		return qry;
	}
	
	
	/**
	 * This method creates an event which includes two teams
	 * @param team1 team
	 * @param team2 team
	 * @param date in which the event will be done
	 * @return the created event
	 * @throws EventFinished if current data is after data of the event
	 */
	public Event createEvent(String team1, String team2, Date date) throws EventFinished, TeamPlayingException, TeamRepeatedException {

		dbManager.open(false);
		Event ev = null;
		Date currentdate = new Date();

		System.out.println("Current date is: "+ currentdate);
		if (currentdate.compareTo(date) > 0) {
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));
				
		}else {
			if (team1.toLowerCase().trim().equals(team2.toLowerCase().trim())){
				throw new TeamRepeatedException();
			}
			else {
				ev = dbManager.createEvent(team1, team2, date);
				if (ev == null) {

					throw new TeamPlayingException();
				}
			}
			
			dbManager.close();
		}
			
		return ev;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	
	@WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	@WebMethod
	public Vector<Question> getQuestions(Event event){
		dbManager.open(false);
		Vector<Question>  questions = dbManager.getQuestions(event);
		dbManager.close();
		return questions;
	}

	@WebMethod
	public Vector<Bet> getUserBets(Question question, User user){
		dbManager.open(false);
		Vector<Bet>  bets = dbManager.getUserBets(question, user);
		dbManager.close();
		return bets;
	}

	@Override
	public Bet removeCurrentUserBet(User currentUser, Bet bet1) {
		dbManager.open(false);
		Bet bet = dbManager.removeCurrentUserBet(currentUser, bet1);
		dbManager.close();
		return bet;
	}


	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */

	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		dbManager.close();
	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod	
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
	
	
	@WebMethod
	public String registerUser(User user) {
		dbManager.open(false);
		try {
			if (dbManager.existUser(user))
				throw new UserIsTakenException();
			
			LocalDate today = new Date(System.currentTimeMillis()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate birthdate = user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if (Period.between(birthdate, today).getYears() < 18)
				throw new UserIsUnderageException();

			dbManager.registerUser(user);
			dbManager.close();
			return "";
			
		} catch (UserIsTakenException e) {
			dbManager.close();
			return "The username is already taken. Please try a different one.";
		} catch (UserIsUnderageException e) {
			dbManager.close();
			return "Bet&Ruin services are not available for users under 18 years.";
	
		}
	}
	
	
	
	@WebMethod
	public User loginUser(String username, String password) throws FailedLoginException {
		dbManager.open(false);

		User user = dbManager.getUser(username);
		dbManager.close();

		if (user == null || !password.equals(user.getPasswd()))
			throw new FailedLoginException();
		
		return user;
	}
	
	

	@WebMethod
	public void createFee(Question q,String pResult, float pFee) throws FeeAlreadyExists {
		dbManager.open(false);
		int n=dbManager.createFee(q,pResult,pFee);
		if (n == -1) {
			throw new FeeAlreadyExists();
		}
		dbManager.close();

	}

	@WebMethod
	public Bet placeBet(double amount, Question question, Result result, Date date) throws NotEnoughMoneyException, MinimumBetException, EventFinished {
		Bet newBet = null;
		User who = this.getCurrentUser();
		Date currentdate = new Date();

		System.out.println("Current date is: "+ currentdate);


		dbManager.open(false);
		if (amount>this.currentUser.getMoneyAvailable()){
			throw new NotEnoughMoneyException();
		}
		else if (amount<question.getBetMinimum()){
			throw new MinimumBetException();
		}
		else if (currentdate.compareTo(date) > 0) {
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));
		}
		else{
			newBet = dbManager.placeBetToQuestion(result, amount, who);
		}



		dbManager.close();


		return newBet;





	}


	@WebMethod
	public double getMoneyAvailable(){
		User who=this.getCurrentUser();
		return who.getMoneyAvailable();
	}

	@WebMethod
	public double getMoneyMinimumBet(Question q){
		return q.getBetMinimum();
	}




	@WebMethod
	public double insertMoney(double amount) throws FailedMoneyUpdateException{
		User who=this.getCurrentUser();
		System.out.println(">> user tenia "+who.getMoneyAvailable());

		dbManager.open(false);

		double totalmoney= 0;

		totalmoney = dbManager.insertMoney(who,amount);

		dbManager.close();

		if(totalmoney==0){
			throw new FailedMoneyUpdateException("User has still 0€");
		}

		return totalmoney;
	}

	@Override
	public User getCurrentUser() {
		return this.currentUser;
	}

	@Override
	public void setCurrentUser(User current) {
		this.currentUser=current;
	}


	public Question getCurrentQuestion() {
		return this.currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion){
		this.currentQuestion=currentQuestion;
	}


}