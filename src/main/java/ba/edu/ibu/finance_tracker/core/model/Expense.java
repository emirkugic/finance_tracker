package ba.edu.ibu.finance_tracker.core.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;

@Document
public class Expense {

    @Id
    private String id;
    private String userId;
    private double amount;
    private String category;
    private String source;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime expenseDate; // the reason I didn't put = LocalDateTime.now() is because I want to set it
                                       // manually in case the user has forgotten to add the expense on the day he
                                       // received it, but the user can also leave it empty and it will be set to
                                       // today's date by the service
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

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDateTime expenseDate) {
        this.expenseDate = expenseDate;
    }

}
