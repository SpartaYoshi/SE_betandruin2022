package businessLogic;

import java.lang.reflect.Type;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import dataAccess.APIManager;
import domain.*;
import exceptions.*;


/**
 * Implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BlFacade")
public class BlFacadeImplementation implements BlFacade {

	final DataAccess dbManager;
	final ConfigXML config = ConfigXML.getInstance();
	User currentUser;
	String sessionMode;
	static List<Match> matchList;


	public BlFacadeImplementation()  {
		System.out.println("Creating BlFacadeImplementation instance");
		boolean initialize = config.getDataBaseOpenMode().equals("initialize");
		dbManager = new DataAccess(initialize);
		if (initialize)
			dbManager.initializeDB();
		dbManager.close();
		sessionMode = "Anon";
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
	 * @throws EventAlreadyFinishedException if current data is after data of the event
	 * @throws QuestionAlreadyExistsException if the same question already exists for the event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventAlreadyFinishedException, QuestionAlreadyExistsException {

		//The minimum bid must be greater than 0
		dbManager.open(false);
		Question qry;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventAlreadyFinishedException(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);
		dbManager.close();
		return qry;
	}


	/**
	 * This method creates an event which includes two teams
	 * @param homeTeam team
	 * @param awayTeam team
	 * @param matchDate in which the event will be done
	 * @return the created event
	 * @throws EventAlreadyFinishedException if current data is after data of the event
	 */
	public Event createEvent(String homeTeam, String awayTeam, Date matchDate) throws EventAlreadyFinishedException, TeamPlayingException, IdenticalTeamsException {

		dbManager.open(false);
		Event ev;
		Date currentDate = new Date();

		if (currentDate.compareTo(matchDate) > 0)
			throw new EventAlreadyFinishedException(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));


		if (homeTeam.toLowerCase().trim().equals(awayTeam.toLowerCase().trim()))
			throw new IdenticalTeamsException();

		ev = dbManager.createEvent(homeTeam, awayTeam, matchDate);

		if (ev == null) {
			throw new TeamPlayingException();
		}

		dbManager.close();

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


	public Vector<Result> getAllResults()  {
		dbManager.open(false);
		Vector<Result>  results = dbManager.getFinalResults();
		dbManager.close();
		return results;
	}


	@Override
	public Bet removeCurrentUserBet(User currentUser, Question question, Bet bet1) {
		dbManager.open(false);
		Bet bet = dbManager.removeCurrentUserBet(currentUser, question, bet1);
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
			ConfigXML config = ConfigXML.getInstance();
			switch(config.getLocale()){
				case "en":
					return "The username is already taken. Please try a different one.";
				case "es":
					return "El nombre de usuario ya existe. Por favor, pruebe con uno distinto.";
				case "eus":
					return "Erabiltzaile izena dagoeneko existitzen da. Saiatu zaitez beste batekin.";
			}

		} catch (UserIsUnderageException e) {
			dbManager.close();
			ConfigXML config = ConfigXML.getInstance();
			switch(config.getLocale()) {
				case "en":
					return "Bet&Ruin services are not available for users under 18 years.";
				case "es":
					return "Los servicios Bet&Ruin no están disponibles para menores de 18 años.";
				case "eus":
					return "Bet&Ruin zerbitzuak ez daude 18 urtetik beherakoentzat eskuragarri.";
			}
		}
		return "";
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
	public void createFee(Question q,int pResult, float pFee) throws FeeAlreadyExistsException {
		dbManager.open(false);
		int n = dbManager.createFee(q, pResult, pFee);
		if (n == -1) {
			throw new FeeAlreadyExistsException();
		}
		dbManager.close();

	}

	@WebMethod
	public Bet placeBet(double amount, Question question, Result result, Date date) throws NotEnoughMoneyException, MinimumBetException, EventAlreadyFinishedException {
		Bet newBet;
		User who = this.getCurrentUser();
		Date currentdate = new Date();

		dbManager.open(false);
		if (amount>this.currentUser.getBalance()){
			throw new NotEnoughMoneyException();
		}
		else if (amount<question.getBetMinimum()){
			throw new MinimumBetException();
		}
		else if (currentdate.compareTo(date) > 0) {
			throw new EventAlreadyFinishedException(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));
		}
		else{
			newBet = dbManager.placeBetToQuestion(result, amount, who);
		}
		dbManager.close();
		return newBet;
	}

/*
	@WebMethod
	public String editProfileUsername(User user, String newUsername) {
		try {
			if (dbManager.existUser2(newUsername))
				throw new UserIsTakenException();
			else{
				dbManager.editUserName(user, newUsername);
			}
			dbManager.close();
			return "";

		} catch (UserIsTakenException e) {
			dbManager.close();
			ConfigXML config = ConfigXML.getInstance();
			switch (config.getLocale()) {
				case "en":
					return "The username is already taken. Please try a different one.";
				case "es":
					return "El nombre de usuario ya existe. Por favor, pruebe con uno distinto.";
				case "eus":
					return "Erabiltzaile-izena existitzen da. Beste batekin saiatu, mesedez.";
			}
		}
		return " ";
	}
	*/


	@WebMethod
	public String editProfilePassword(User user, String newPassword) {
		dbManager.editPassWord(user, newPassword);
		return "";
	}



	@WebMethod
	public double getMoneyAvailable() {
		User who = this.getCurrentUser();
		Double amount= who.getBalance();
		return amount;
	}

	@WebMethod
	public double getMoneyMinimumBet(Question q){
		return q.getBetMinimum();
	}







	@WebMethod
	public double insertMoney(double amount, Bet bet, String type) throws FailedMoneyUpdateException{
		User who=this.getCurrentUser();
		dbManager.open(false);

		double totalmoney;

		totalmoney = dbManager.insertMoney(who,amount, bet,type);

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
		this.currentUser = current;
	}

	public void setSessionMode(String mode){
		sessionMode = mode;
	}

	public String getSessionMode(){
		return sessionMode;
	}

	@Override
	public Event removeEvent(Event ev) {
		dbManager.open(false);
		Event deleted=dbManager.removeEvent(ev);
		dbManager.close();
		return deleted;
	}

	@Override
	public int markFinalResult(Result r, int finalR) {
		dbManager.open(false);
		int updated=dbManager.markFinalResult(r,finalR);
		dbManager.close();
		return updated;
	}

	/*
	@Override
	public int payWinners(Bet b,int finalR) {
		int cont=0;
		dbManager.open(false);
		List<User> allUsers=dbManager.getAllUsers();
		for(User u:allUsers){
			for(Bet betuser:u.getBets()){
				if(betuser.getBetNum()==b.getBetNum() && betuser.getResult().getPossibleResult()==finalR){// he has a bet, that it's correct, we have to pay this user
					float total= (float) (betuser.getAmount()+(betuser.getAmount()*b.getResult().getFee()));
					dbManager.insertMoney(u,total,betuser,"GainedBetMoney");//update his account
					cont++;
				}
			}

		}

		return cont;
	}*/


	private void fetchFromAPI() {
		APIManager dataFetcher = new APIManager();
		String APIData = dataFetcher.request("matches");

		Gson gson = new Gson();
		JsonObject jsonObj = gson.fromJson(APIData, JsonObject.class);

		Type matchListType = new TypeToken<ArrayList<Match>>(){}.getType();
		matchList = gson.fromJson((jsonObj.get("matches")), matchListType);
	}


	@WebMethod
	public int processBets(Result r) {
		System.out.println(">> Processing final result: " + r.toString());
		int cont=0;
		for (Bet b : r.getBets()) {
			User u = b.getBetter();
			double profit = b.getAmount() * r.getFee();
			dbManager.open(false);
			dbManager.insertMoney(u, profit, b,"BetProfit");
			cont++;
			dbManager.close();
		}
		return cont;
	}


	private void processMatchResult(Event ev, Match matchAPI) {

		System.out.println("Match found: @" + ev.getStrDate() + ", " + ev.getHomeTeam() + " - " + ev.getAwayTeam());

		// WINNER BET
		String winner = matchAPI.getWinner();

		Result rHome = ev.getQuestionByID("qIDMatchWinner").getResultOption(1);
		Result rAway = ev.getQuestionByID("qIDMatchWinner").getResultOption(2);
		Result rDraw = ev.getQuestionByID("qIDMatchWinner").getResultOption(3);

		if (winner != null) {
			if (winner.equals(ev.getHomeTeam()))
				processBets(rHome);
			else
				processBets(rAway);
		} else
			processBets(rDraw);


		// TOTAL GOALS BET
		int totalGoals = matchAPI.getTotalGoals();
		Result r = ev.getQuestionByID("qIDTotalGoals").getResultOption(totalGoals);
		processBets(r);

	}


	@WebMethod
	public int updateResultsFromAPI() {
		fetchFromAPI();

		int matchesProcessed = 0;
		List<Event> eventList = dbManager.getAllEvents();

		for (Event ev : eventList) {
			String evHomeTeam = ev.getHomeTeam();
			String evAwayTeam = ev.getAwayTeam();
			String evDate = ev.getStrDate();

			// Convert Event to Match for API to Database matching
			Match conv = new Match (evHomeTeam, evAwayTeam, evDate);

			if (matchList.contains(conv)) {
				Match m = matchList.get(matchList.indexOf(conv));
				if (m.getStatus().equals("FINISHED")) {
					processMatchResult(ev, m);
					matchesProcessed++;
				}
			}
		}
		return matchesProcessed;
	}
}