package ba.edu.ibu.finance_tracker.rest.dto.IncomeDTO;

import java.util.Date;

public class IncomeCreateRequestDTO {

    private String userId;
    private double amount;
    private String source;
    private String receivedThrough;
    private String from;
    private Date receivedDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReceivedThrough() {
        return receivedThrough;
    }

    public void setReceivedThrough(String receivedThrough) {
        this.receivedThrough = receivedThrough;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

}