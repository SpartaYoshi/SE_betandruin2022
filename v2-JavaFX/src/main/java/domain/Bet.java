package domain;

public class Bet {
    private Double amount;
    private Question question;

    public Bet(Double amount, Question question) {
        this.amount = amount;
        this.question = question;
    }
}
