package ba.edu.ibu.finance_tracker.core.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Expense {

    @Id
    private String id;
    private String userId;
    private double amount;
    private LocalDateTime expenseDate;
    private String category;
    private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setSpentDate(LocalDateTime expenseDate) {
        this.expenseDate = expenseDate;
    }

}
