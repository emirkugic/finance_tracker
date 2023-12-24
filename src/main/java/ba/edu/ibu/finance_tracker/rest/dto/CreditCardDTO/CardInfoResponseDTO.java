package ba.edu.ibu.finance_tracker.rest.dto.CreditCardDTO;

public class CardInfoResponseDTO {
    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private double balance;

    public CardInfoResponseDTO(String cardName, String cardNumber, String expiryDate, double balance) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.balance = balance;
    }

    public CardInfoResponseDTO() {
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
