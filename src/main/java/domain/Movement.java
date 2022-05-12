package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Movement {
    @Id
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @GeneratedValue
    private Integer id;

    private Double amount;
    private Date date;
    private String descriptionType;
    @OneToOne
    private Bet bet;


    public Movement(Double amount, Date date, String descriptionType, Bet bet) {
        this.amount = amount;
        this.date = date;
        this.descriptionType = descriptionType;
        this.bet = bet;
    }

    public Movement(Double amount, Date date, String descriptionType) {
        this.amount = amount;
        this.date = date;
        this.descriptionType = descriptionType;
        this.bet = null;
    }

    public Movement() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + descriptionType + '\'' +
                '}';
    }
}
