package ba.edu.ibu.finance_tracker.core.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Expense {

    @Id
    private String id;
    private String userId;
    private double amount;
    private String category;
    private String source;
    private Date expenseDate;
    private boolean isTransferToChild;
    private String recipientChildId;

    public boolean isTransferToChild() {
        return isTransferToChild;
    }

    public void setTransferToChild(boolean isTransferToChild) {
        this.isTransferToChild = isTransferToChild;
    }

    public String getRecipientChildId() {
        return recipientChildId;
    }

    public void setRecipientChildId(String recipientChildId) {
        this.recipientChildId = recipientChildId;
    }

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

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

}
