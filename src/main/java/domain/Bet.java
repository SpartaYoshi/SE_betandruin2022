package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable {
    @XmlID
    @Id
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @GeneratedValue
    private Integer betNum;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "result")
    @XmlIDREF
    private Result result;

    @ManyToOne
    private User better;

    public Bet(Double amount, Result r, User better) {
        super();
        this.amount = amount;
        this.result = r;
        this.better = better;
    }

    public Bet(Integer numBet, Double amount,Result r, User better) {
        super();
        this.betNum = numBet;
        this.amount = amount;
        this.result=r;
        this.better = better;
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
        this.betNum = betNum;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result r) {
        this.result=r;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "betNum=" + betNum +
                ", amount=" + amount +
                ", result=" + result +
                '}';
    }

    public User getBetter() {
        return better;
    }
}
