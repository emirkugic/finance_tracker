package ba.edu.ibu.finance_tracker.rest.dto.AlertDTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class AlertUpdateRequestDTO {

    private String alertId;
    @Schema(defaultValue = "Something happened to your account update")
    private String newMessage;

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }
}