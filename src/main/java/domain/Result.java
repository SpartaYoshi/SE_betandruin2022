package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Result implements Serializable{
	
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Bet> bets = new Vector<>();
	
	private String result;

	public Vector<Bet> getBets() {
		return bets;
	}

	private float fee;


	public Result() {
	}

	public Result(String r, float f) {
		this.fee=f;
		this.result=r;
		
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @return the fee
	 */
	public float getFee() {
		return fee;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(float fee) {
		this.fee = fee;
	}

	public void setBets(Vector<Bet> bets) {
		this.bets = bets;
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


	@Override
	public String toString() {
		return result;
	}
}
