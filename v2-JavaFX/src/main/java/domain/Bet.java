package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

@Entity
public class Bet implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double amount;
    @XmlID
    @Id
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @GeneratedValue
    private Integer betNum;

    public Bet(Double amount) {
        this.amount = amount;
    }

    public Bet() {
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
