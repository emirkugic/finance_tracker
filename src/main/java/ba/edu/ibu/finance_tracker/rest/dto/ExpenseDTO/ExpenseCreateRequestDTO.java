package ba.edu.ibu.finance_tracker.rest.dto.ExpenseDTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public class ExpenseCreateRequestDTO {
    @Schema(defaultValue = "emir")
    private String userId;
    private double amount;
    @Schema(defaultValue = "Food")
    private String category;
    @Schema(defaultValue = "Cash")
    private String source;
    @Schema(defaultValue = "2023-10-22 20:42:25.41") // remind me to ask you for this bug im having with the date format
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime expenseDate;
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

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDateTime expenseDate) {
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