package dataAccess;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javax.persistence.*;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.QuestionAlreadyExistsException;

/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess {

	protected EntityManager db;
	protected EntityManagerFactory emf;

	final ConfigXML config = ConfigXML.getInstance();


	public DataAccess(boolean initializeMode)  {
		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + 
				config.isDataAccessLocal() + " getDatabaseOpenMode: " + config.getDataBaseOpenMode());
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
	public void initializeDB() {

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) { month = 0; year += 1;}  


			Event ev1 = new Event( "Atlético","Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event( "Eibar","Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event( "Getafe","Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event( "Alavés","Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event( "Español","Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event( "Las Palmas","Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event( "Malaga","Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event( "Girona","Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event( "Real Sociedad","Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event( "Betis","Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event( "Atletico","Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event( "Eibar","Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event( "Getafe","Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event( "Alavés","Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event( "Español","Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event( "Las Palmas","Sevilla", UtilDate.newDate(year, month, 1));


			Event ev17 = new Event( "Málaga","Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event( "Girona","Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event( "Real Sociedad","Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event( "Betis","Real Madrid", UtilDate.newDate(year, month + 1, 28));



			// Question creation
			Question q1 = ev1.addQuestion("qIDMatchWinner", 1);
			Question q2 = ev1.addQuestion("qIDFirstScore", 2);
			Question q3 = ev11.addQuestion("qIDMatchWinner", 1);
			Question q4 = ev11.addQuestion("qIDTotalGoals", 2);
			Question q5 = ev17.addQuestion("qIDMatchWinner", 1);
			Question q6 = ev17.addQuestion("qIDGoalsFirstHalf", 2);

			// Update properties
			PropertiesManager propMgr = new PropertiesManager();
			propMgr.addTagToResources("qIDMatchWinner",
					"Who will win the match?",
					"¿Quién ganará el partido?",
					"Zeinek irabaziko du partidua?");

			propMgr.addTagToResources("qIDFirstScore",
					"Who will score first?",
					"¿Quién meterá el primer gol?",
					"Zeinek sartuko du lehenengo gola?");

			propMgr.addTagToResources("qIDTotalGoals",
					"How many goals will be scored in the match?",
					"¿Cuántos goles se marcarán?",
					"Zenbat gol sartuko dira?");

			propMgr.addTagToResources("qIDGoalsFirstHalf",
					"Will there be goals in the first half?",
					"¿Habrá goles en la primera parte?",
					"Golak sartuko dira lehenengo zatian?");




			//Admin user:
			String sDate1 = "01/01/1980";
		    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
			User u1 = new User("juanan", "hello", "Juan Antonio", "Pereira", date1);
			u1.grantAdmin();

			//Regular user:
			String sDate2 = "01/01/1980";
			Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
			User u2 = new User("ainhoa", "123", "Ainhoa", "Corporation", date1);

			
			if(!existUser(u1)) {
				db.persist(u1);
				System.out.println("Admin user created and persisted");
				//admin user persisted
			}
			if(!existUser(u2)) {
				db.persist(u2);
				System.out.println("Regular user created and persisted");

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
	 * @param questionID text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExistsException if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String questionID, float betMinimum)
			throws QuestionAlreadyExistsException {
		System.out.println(">> DataAccess: createQuestion=> event = " + event + " question = " +
				questionID + " minimum bet = " + betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.doesQuestionExist(questionID)) throw new QuestionAlreadyExistsException(
				ResourceBundle.getBundle("Etiquetas").getString("ErrorQuestionAlreadyExists"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(questionID, betMinimum);
		//db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added 
		// in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;
	}
	
	
	public Event createEvent(String homeTeam, String awayTeam, Date matchDate){

		System.out.println(">> DataAccess: createEvent=> Home team = " + homeTeam + ", Away team = " + awayTeam);
		Event ev = null;

		if (!this.isAnyTeamPlaying(homeTeam, awayTeam, matchDate)) {
			db.getTransaction().begin();

			ev = new Event(homeTeam, awayTeam, matchDate);
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
		Vector<Event> res = new Vector<>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", 
				Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){
			System.out.println(ev.getTeamTemplate());
			res.add(ev);
		}
		return res;
	}


	/**
	 * This method retrieves all currently registered events in the database
	 *
	 * @return collection of events
	 */
	public Vector<Event> getAllEvents() {
		System.out.println(">> DataAccess: getEvents");

		Vector<Event> res = new Vector<>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev",
				Event.class);

		List<Event> events = query.getResultList();
		for (Event ev:events) {
			System.out.println(ev.getTeamTemplate());
			res.add(ev);
		}
		return res;
	}


	/**
	 * This method retrieves all currently registered fee results in the database
	 *
	 * @return collection of fee results
	 */
	public Vector<Result> getResultByType(String questionID) {
		System.out.println(">> DataAccess: getResults");

		Vector<Result> res = new Vector<>();
		TypedQuery<Result> query = db.createQuery("SELECT r FROM Result r WHERE r.questionID=?1",
				Result.class);
		query.setParameter(1, questionID);

		List<Result> results = query.getResultList();
		for (Result r : results) {
			res.add(r);
		}
		return res;
	}



	public Vector<Question> getQuestions(Event event) {
		System.out.println("Event is "+ event);
		System.out.println(">> DataAccess: getQuestions");
		Vector<Question> res = new Vector<>();
		TypedQuery<Question> query = db.createQuery("SELECT qu FROM Question qu WHERE qu.event=?1",
				Question.class);
		query.setParameter(1, event);
		List<Question> questions = query.getResultList();
		for (Question q:questions){
			System.out.println(q.toString());
			res.add(q);
		}
		return res;
	}


	public List<User> getAllUsers() {

		TypedQuery<User> q = db.createQuery("SELECT u FROM User u" , User.class);
		return q.getResultList();
	}



	public Vector<Bet> getUserBets(Question question, User user) {
		System.out.println("Question is "+ question);
		System.out.println("User is "+ user);
		System.out.println(">> DataAccess: getUserBets");
		Vector<Bet> res = new Vector<>();

		// Aqui lo que hago es consegir todas las bets que tiene un user
		TypedQuery<Bet> query = db.createQuery("SELECT bets FROM User",
				Bet.class);
		List<Bet> bets = query.getResultList();
		for (Bet b1:bets){
			System.out.println(b1.toString());
			res.add(b1);
		}
		return (Vector<Bet>) bets;
	}




	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<>();

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);

		// Introduce class on persistence model, so it is identified before querying
		db.getMetamodel().entity(Event.class);

		// Query
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
				config.isDataAccessLocal() + " getDatabaseOpenMode: " + config.getDataBaseOpenMode());

		String fileName = config.getDataBaseFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (config.isDataAccessLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
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
	
	
	public boolean isAnyTeamPlaying(String homeTeam, String awayTeam, Date date)  {
		for (Event ev : this.getEvents(date))
			if (ev.getHomeTeam().equals((homeTeam)) && ev.getAwayTeam().equals((awayTeam)))
				return true;

		return false;	
	}


	public void close() {
		db.close();
		System.out.println("Database is closed");
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

	public boolean existUser2(String username) {
		try {
			TypedQuery<User> q = db.createQuery("SELECT u FROM User u WHERE u.username = \"" + username + "\"", User.class);
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
			return db.find(User.class, username);
			
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	

	/**
	 * Method to create different fees
	 * @param quest quest
	 * @param questionID question type
	 * @param fee fee
	 * @return 0 if everything has updated correctly, -1 if the fee is already stored
	 */
	public int createFee(Question quest,String questionID, float fee) {
		db.getTransaction().begin();
		TypedQuery<Question> q = db.createQuery("SELECT p FROM Question " + "p WHERE p.questionNumber = ?1", Question.class);
		q.setParameter(1, quest.getQuestionNumber());
		Question ourquestion = q.getSingleResult();
		
		if(ourquestion!=null) {
			if(ourquestion.resultisAlreadyStored(questionID)) {// check if that fee is not used yet
				return -1;
			}else {
				Result f = new Result(questionID, fee);
				db.persist(f);
				ourquestion.addtoResultList(f);
				db.persist(ourquestion);
			}
			
			
		}else {//the question doesn't exist
			System.out.println("That question doesn't exist");
		}
		db.getTransaction().commit();
		return 0;
	}

	/**
	 * method to add more money to the current user
	 * @param who
	 * @param am
	 * @return
	 */
	public double insertMoney(User who, double am)  {
		double total=who.getMoneyAvailable()+ am; //the money he had + the deposited money
		String description = new String("insertMoney");
		LocalDate localDate = null;
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		Movement mov = new Movement(am, date, description);
		db.getTransaction().begin();
		who.setMoneyAvailable(total);
		who.addMovement(mov);
		User dbUser=db.find(User.class, who.getUsername());
		dbUser.setMoneyAvailable(total);
		dbUser.addMovement(mov);
		db.persist(mov);
		db.getTransaction().commit();
		System.out.println(">> DataAccess: money updated");
		return who.getMoneyAvailable();
	}


	/**
	 * method to add a bet to the result of a question
	 * @param f
	 * @param amountBet
	 * @param who
	 * @return
	 */
	public Bet placeBetToQuestion(Result f, Double amountBet, User who){
		System.out.println(">> DataAccess: placeAbet=> On result = " + f.getQuestionID() + ", amount = " +amountBet + " by " + who.getName() + " " + who.getSurname());
		Result result = db.find(Result.class, f.getId());
		Bet bet = new Bet(amountBet,f);
		db.getTransaction().begin();
		f.addBet(bet);
		who.addBet(bet);
		User dbUser=db.find(User.class, who.getUsername());//update the database object too
		dbUser.addBet(bet);
		result.addBet(bet);
		db.persist(bet);
		db.getTransaction().commit();
		if (bet != null){
			this.restMoney(who, amountBet, bet);
		}
		return bet;
	}

	/**
	 * method to rest money to the current user
	 * @param who
	 * @param bet
	 * @return
	 */


	public double restMoney(User who, double betAmount, Bet bet)  {
		double total=who.getMoneyAvailable()- betAmount; //the money he had - the deposited money
		String description = new String("restMoney");
		LocalDate localDate = null;
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		Movement mov = new Movement(betAmount*(-1), date, description);
		db.getTransaction().begin();
		who.setMoneyAvailable(total);//our object of the app
		who.addMovement(mov);
		User dbUser=db.find(User.class, who.getUsername());
		dbUser.setMoneyAvailable(total);
		dbUser.addMovement(mov);
		db.persist(mov);
		db.getTransaction().commit();

		System.out.println(">> DataAccess: money updated");

		return who.getMoneyAvailable();
	}

	/**
	 * method that deletes Bet from database, from user's list and from result's list
	 * @param currentUser
	 * @param question
	 * @param bet1
	 * @return
	 */
	public Bet removeCurrentUserBet(User currentUser, Question question, Bet bet1) {
		System.out.println(">> DataAccess: removeAbet=> On bet = " + bet1 + ", by " + currentUser.getName() + " " + currentUser.getSurname());
		Bet bet = db.find(Bet.class, bet1.getBetNum());
		User dbUser = db.find(User.class, currentUser.getUsername());
		Question dbQuestion=db.find(Question.class,question.getQuestionNumber());
		Result dbResult = null;
		//find the result that has that bet
		for(Result r:dbQuestion.getResults()){
			for(Bet b:r.getBets()){
				if(b.getBetNum()== bet1.getBetNum()){
					 dbResult=db.find(Result.class,r.getId());
					break;
				}
			}
		}
		db.getTransaction().begin();
		currentUser.getBets().remove(bet);
		dbUser.getBets().remove(bet);
		this.insertMoney(currentUser, bet.getAmount());
		if(dbResult!=null){
			dbResult.getBets().remove(bet);
		}
		db.remove(bet);
		db.getTransaction().commit();
		return bet;

	}


	public User editUserName(User who, String newUsername){
		db.getTransaction().begin();
		User dbUser=db.find(User.class, who.getUsername());
		dbUser.setUsername(newUsername);
		who.setUsername(newUsername);
		db.getTransaction().commit();

		System.out.println(">> DataAccess: username updated");

		return who;
	}


	public User editPassWord(User who, String newPassword){
		db.getTransaction().begin();
		User dbUser=db.find(User.class, who.getUsername());
		dbUser.setUsername(newPassword);
		who.setUsername(newPassword);
		db.getTransaction().commit();

		System.out.println(">> DataAccess: password updated");

		return who;
	}



    public Event removeEvent(Event ev) {

		Event dbEvent=db.find(Event.class, ev);
		db.getTransaction().begin();
		db.remove(dbEvent);
		db.getTransaction().commit();


		db.getTransaction().begin();
		//update users list

		for (User u:this.getAllUsers()){
			for(Bet b:u.getBets()){
				if(b.getResult()==null){
					u.removeBet(b);
					if(u.getBets().size()==0) break;
				}
			}
			db.persist(u);
		}
		db.getTransaction().commit();

		return dbEvent;
    }

	public double getUsersMoney(User who) {
		db.getTransaction().begin();
		User dbUser=db.find(User.class, who.getUsername());
		Double money = dbUser.getMoneyAvailable();
		db.getTransaction().commit();

		System.out.println(">> DataAccess: getting the money available of the current user");

		return money;
	}
}