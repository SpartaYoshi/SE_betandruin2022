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
	private boolean isAdmin;
	

	public User(String username, String passwd, String name, String surname, Date birthdate) {

		this.username = username;
		this.passwd = passwd;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		
		isAdmin = false;
	}
	
	
	public void grantAdmin() {
		isAdmin = true;
	}
	
	
	public void revokeAdmin() {
		isAdmin = false;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
	
	
}
