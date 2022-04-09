package domain;

import java.util.*;

import javax.persistence.*;

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

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Bet> bets = new Vector<Bet>();


	public User() {
	}

	public User(String username, String passwd, String name, String surname, Date birthdate) {

		this.username = username;
		this.passwd = passwd;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		moneyAvailable=0;

		
		admin = false;
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
	
	

	
	
}
