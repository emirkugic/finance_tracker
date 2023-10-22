package ba.edu.ibu.finance_tracker.rest.dto.RepeatingExpensesDTO;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class RepeatingExpenseCreateRequest {

    private String userId;
    private double amount;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime dueDate;

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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

}
