package ba.edu.ibu.finance_tracker.rest.dto.CreditCardDTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreditCardCreateRequestDTO {
    @Schema(defaultValue = "emir")
    private String userId;
    @Schema(defaultValue = "6969")
    private String cardNumber;
    @Schema(defaultValue = "Raiffeisen Bank")
    private String cardName;

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