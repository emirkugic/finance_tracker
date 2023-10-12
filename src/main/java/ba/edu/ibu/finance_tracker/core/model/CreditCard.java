package ba.edu.ibu.finance_tracker.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CreditCard {

    @Id
    private String id;
    private String userId;
    private String cardName;
    private String cardNumber;
    private String expiryDate;

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
