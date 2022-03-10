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
import domain.Event;
import domain.Question;
import domain.User;
import exceptions.EventFinished;
import exceptions.FailedLoginException;
import exceptions.FeeAlreadyExists;
import exceptions.QuestionAlreadyExist;
import exceptions.UserIsTakenException;
import exceptions.UserIsUnderageException;


/**
 * Implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BlFacade")
public class BlFacadeImplementation implements BlFacade {

	DataAccess dbManager;
	ConfigXML config = ConfigXML.getInstance();

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
	 * @param first team 
	 * @param second team
	 * @param date in which the event will be done
	 * @return the created event
	 * @throws EventFinished if current data is after data of the event
	 */
	public Event createEvent(String team1, String team2, Date date) throws EventFinished {

		dbManager.open(false);
		Event ev = null;
		Date currentdate = new Date();
		System.out.println("Current date is: "+ currentdate);
		if (currentdate.compareTo(date) > 0) {
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));
		}else {
			ev = dbManager.createEvent(team1, team2, date);		
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
	public void registerUser(User user) throws UserIsTakenException, UserIsUnderageException {
		dbManager.open(false);
		try {
			if (dbManager.existUser(user))
				throw new UserIsTakenException();
			
			LocalDate today = new Date(System.currentTimeMillis()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate birthdate = user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if (Period.between(birthdate, today).getYears() < 18)
				throw new UserIsUnderageException();

			
			dbManager.registerUser(user);
			
		} catch (UserIsTakenException e) {
			e.printStackTrace();
		} catch (UserIsUnderageException e) {
			e.printStackTrace();
		} finally {
			dbManager.close();
		}
	}
	
	
	
	@WebMethod
	public User loginUser(String username, String password) throws FailedLoginException {
		dbManager.open(false);

		User user = dbManager.getUser(username);
		dbManager.close();

		if (user == null || password.equals(user.getPasswd()))
			throw new FailedLoginException();
		
		return user;
	}
	
	
	@WebMethod
	public void createFee(Question q,String pResult, float pFee) throws FeeAlreadyExists {
		dbManager.open(true);
		int n=dbManager.createFee(q,pResult,pFee);
		if (n == -1) {
			throw new FeeAlreadyExists();
		}
		dbManager.close();

	}

	
}