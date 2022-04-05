package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Fee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int id;
	
	private String result;
	private float fee;


	public Fee() {
	}

	public Fee(String r, float f) {
		this.fee=f;
		this.result=r;
		
	}

	public String toString() {
		return " result: "+ result+ " feeAmount "+fee;
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

	
}
