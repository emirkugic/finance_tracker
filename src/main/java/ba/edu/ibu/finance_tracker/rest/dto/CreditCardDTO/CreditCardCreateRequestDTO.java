package ba.edu.ibu.finance_tracker.rest.dto.CreditCardDTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreditCardCreateRequestDTO {
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

}