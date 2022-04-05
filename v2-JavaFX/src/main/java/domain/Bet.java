package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Bet implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double amount;
    @Id
    private Integer BetNum;

    public Bet(Double amount) {
        this.amount = amount;
    }

    public Bet() {

    }
}
