package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlID
    @Id
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @GeneratedValue
    private Integer betNum;

    private Double amount;

    public Bet(Double amount) {
        super();
        this.amount = amount;
    }

    public Bet(Integer numBet, Double amount) {
        super();
        this.betNum=numBet;
        this.amount = amount;
    }

    public Bet() {
        super();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getBetNum() {
        return betNum;
    }

    public void setBetNum(Integer betNum) {
        betNum = betNum;
    }
}
