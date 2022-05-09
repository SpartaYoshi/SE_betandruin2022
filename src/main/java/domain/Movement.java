package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private int id;

    private Double amount;
    private String date;
    private String description;

    public Movement(int id, Double amount, String date) {
        super();
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public Movement(Double amount, String date) {
        super();
        this.amount = amount;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
