package ba.edu.ibu.finance_tracker.rest.dto.ExpenseDTO;

import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

public class ExpenseCreateRequestDTO {
    @Schema(defaultValue = "emir")
    private String userId;
    private double amount;
    @Schema(defaultValue = "Food")
    private String category;
    @Schema(defaultValue = "Cash")
    private String source;
    private Date expenseDate;
    @Schema(defaultValue = "emirson")
    private String recipientChildId;

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

    public String getRecipientChildId() {
        return recipientChildId;
    }

    public void setRecipientChildId(String recipientChildId) {
        this.recipientChildId = recipientChildId;
    }

    // public Boolean getIsTransferToChild() {
    // return isTransferToChild;
    // }

    // public void setIsTransferToChild(Boolean isTransferToChild) {
    // this.isTransferToChild = isTransferToChild;
    // }

}