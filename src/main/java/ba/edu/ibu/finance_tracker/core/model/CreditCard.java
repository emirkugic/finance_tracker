package ba.edu.ibu.finance_tracker.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;

@Document
public class CreditCard {

    @Id
    private String id;

    @Schema(defaultValue = "emir")
    private String userId;

    @Schema(defaultValue = "Raiffeisen Bank")
    private String cardName;

    @Schema(defaultValue = "6969")
    private String cardNumber;

    @Schema(defaultValue = "09-2024")
    private String expiryDate;

    @Schema(defaultValue = "0")
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        if (!expiryDate.matches("\\d{2}-\\d{4}")) {
            throw new IllegalArgumentException("Invalid expiry date format. Expected: MM-yyyy");
        }
        this.expiryDate = expiryDate;
    }

}
