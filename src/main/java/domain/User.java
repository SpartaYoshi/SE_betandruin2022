package domain;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String username;
	private String passwd;
	
	private String name;
	private String surname;
	private Date birthdate;
	
	// private double balance;
	private boolean admin;
	

	public User(String username, String passwd, String name, String surname, Date birthdate) {

		this.username = username;
		this.passwd = passwd;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		
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


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	
	
	public boolean isPasswordCorrect(String password) {
		if (passwd.equals(password)){
			return true;
		}
		return false;
	}

	
	
}
