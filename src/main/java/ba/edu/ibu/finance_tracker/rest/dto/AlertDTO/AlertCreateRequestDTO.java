package ba.edu.ibu.finance_tracker.rest.dto.AlertDTO;

import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

public class AlertCreateRequestDTO {

    @Schema(defaultValue = "emir")
    private String userId;
    @Schema(defaultValue = "Something happened to your account")
    private String message;
    private Date alertDate;

    public Date getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(Date alertDate) {
        this.alertDate = alertDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // public LocalDateTime getAlertDate() {
    // return alertDate;
    // }

    // public void setAlertDate(LocalDateTime alertDate) {
    // this.alertDate = alertDate;
    // }

}