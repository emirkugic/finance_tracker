package ba.edu.ibu.finance_tracker.rest.dto.CreditCardDTO;

public class CardInfoRequestDTO {

    private String userId;
    private String cardId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

}
