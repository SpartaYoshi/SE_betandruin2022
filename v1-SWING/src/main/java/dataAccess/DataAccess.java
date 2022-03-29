package dataAccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Event;
import domain.Fee;
import domain.Question;
import domain.User;
import exceptions.QuestionAlreadyExist;
import exceptions.TeamPlayingException;

/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess  {

	protected EntityManager  db;
	protected EntityManagerFactory emf;

	ConfigXML config = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode)  {
		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + 
				config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());
		open(initializeMode);
	}

	public DataAccess()  {	
		this(false);
	}


	/**
	 * This method initializes the database with some trial events and questions. 
	 * It is invoked by the business logic when the option "initialize" is used 
	 * in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) { month = 0; year += 1;}  

			Event ev1 = new Event( "Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event( "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event( "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event( "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event( "Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event( "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event( "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event( "Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event( "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event( "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event( "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event( "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event( "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event( "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event( "Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event( "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));


			Event ev17 = new Event( "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event( "Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event( "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event( "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
		

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			}			
			else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
			}
			
			//Admin user:
			String sDate1="01/01/1980";  
		    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1); 
			User u= new User("juanan", "hello", "Juan Antonio", "Pereira", date1);
			u.grantAdmin();
			
			
			if(!existUser(u)) {
				db.persist(u);
				System.out.println("Admin user created and persisted");
				//admin user persisted
			}
			
		
			
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);			

			db.getTransaction().commit();
			System.out.println("The database has been initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) 
			throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event = " + event + " question = " +
				question + " minimum bet = " + betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.doesQuestionExist(question)) throw new QuestionAlreadyExist(
				ResourceBundle.getBundle("Etiquetas").getString("ErrorQuestionAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		//db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added 
		// in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;
	}
	
	
	public Event createEvent(String team1, String team2, Date date){
		System.out.println(">> DataAccess: createEvent=> First team = " + team1 + ", Second team = " +team2);
		Event ev=null;
		if (!this.isAnyTeamPLaying(team1, team2, date)) {
			db.getTransaction().begin();
			String descr = team1 + " - " + team2;
			ev = new Event(descr, date);
			System.out.println("The new event is "+ ev);
			db.persist(ev);  
			db.getTransaction().commit();
		}


		
		return ev;
	}
	
	

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println("Date is "+ date);
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", 
				Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){
			System.out.println(ev.toString());		 
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev "
				+ "WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){
			System.out.println(d.toString());		 
			res.add(d);
		}
		return res;
	}


	public void open(boolean initializeMode){

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + 
				config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());

		String fileName = config.getDataBaseFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (config.isDataAccessLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", config.getDataBaseUser());
			properties.put("javax.persistence.jdbc.password", config.getDataBasePassword());

			emf = Persistence.createEntityManagerFactory("objectdb://" + config.getDataAccessNode() +
					":"+config.getDataAccessPort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion => event = " + event + 
				" question = " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.doesQuestionExist(question);
	}
	
	
	public boolean isAnyTeamPLaying(String team1, String team2, Date date)  {	
		for (Event ev : this.getEvents(date)) {
			String[] descr = ev.getDescription().split("-");
			for (int i = 0; i<2 ; i++) {
				if (team1.compareTo(descr[i])==0 || team2.compareTo(descr[i])==0) {
					
					return true;
					
				}
			}
		}
		return false;	
	}


	public void close() {
		db.close();
		System.out.println("DataBase is closed");
	}
	
	
	public void registerUser(User user) {
		db.getTransaction().begin();
		db.persist(user);
		db.getTransaction().commit();
	}
	
	
	public boolean existUser(User user) {
		try {
			TypedQuery<User> q = db.createQuery("SELECT u FROM User u WHERE u.username = \"" + user.getUsername() + "\"", User.class);
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * Method to get an existing user
	 * @return user if exists, null if not
	 */
	public User getUser(String username) {
		try {
			System.out.println(">> DataAccess: getUser=> username = " + username);
			User us = db.find(User.class, username);
			return us;
			
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	

	/**
	 * Method to create different fees
	 * @param quest
	 * @param result
	 * @param fee
	 * @return 0 if everything has updated correctly, -1 if the fee is already stored
	 */
	public int createFee(Question quest,String result,float fee) {
		db.getTransaction().begin();
		TypedQuery<Question> q = db.createQuery("SELECT p FROM Question " +"p WHERE p.questionNumber = ?1", Question.class);
		q.setParameter(1, quest.getQuestionNumber());
		Question ourquestion=q.getSingleResult();
		
		if(ourquestion!=null) {
			if(ourquestion.feeisAlreadyStored(result)) {// check if that fee is not used yet
				return -1;
			}else {
				Fee f=ourquestion.addFee(result,fee);
				db.persist(f);
				db.persist(ourquestion);
			}
			
			
		}else {//the question doesn't exist
			System.out.println("That question doesn't exist");
		}
		db.getTransaction().commit();
		return 0;
	}
	

	
}