package ba.edu.ibu.finance_tracker.rest.dto.ExpenseDTO;

public class UpdateExpenseAmountDTO {
    private String id;
    private double newAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }
}
