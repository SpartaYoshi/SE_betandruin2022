package domain;

import java.util.Date;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User {
	@Id
	private String username;
	private String passwd;
	private String name;
	private String surname;
	private Date birthdate;
	private double moneyAvailable;
	// private double balance;
	private boolean admin;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Bet> bets= new Vector<>();


	public User() {
		super();
	}

	public User(String username, String passwd, String name, String surname, Date birthdate) {
		super();
		this.username = username;
		this.passwd = passwd;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		moneyAvailable=10;
		admin = false;
		bets = new Vector<Bet>();
	}

	public User(String username, String passwd, String name, String surname, Date birthdate, Double moneyAvailable) {
		super();
		this.username = username;
		this.passwd = passwd;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.moneyAvailable=moneyAvailable;
		admin = false;
		bets = new Vector<Bet>();
	}


	/**
	 * This method adds a bet to a user
	 *
	 * @param bet to be added to the list of bets
	 * @return Bet
	 */
	public Bet addBet(Bet bet)  {
		bets.add(bet);
		return bet;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPasswd() {
		return passwd;
	}


	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	public double getMoneyAvailable() {return moneyAvailable;}

	public void setMoneyAvailable(double amount) {
		this.moneyAvailable = amount;
	}


	public boolean isAdmin() {
		return admin;
	}

	public void grantAdmin() {
		admin = true;
	}
	
	public void revokeAdmin() {
		admin = false;
	}


	public Vector<Bet> getBets() {
		return bets;
	}

	public void setBets(Vector<Bet> bets) {
		this.bets = bets;
	}

	public void removeBet(Bet b){
		this.bets.remove(b);
	}

	
}
