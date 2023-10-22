package ba.edu.ibu.finance_tracker.rest.dto.AlertDTO;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public class AlertCreateRequestDTO {

    @Schema(defaultValue = "emir")
    private String userId;

    @Schema(defaultValue = "Something happened to your account")
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime alertDate;
    // i cant get it to display the
    // date in the format i want in the
    // request message help pls

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

    public LocalDateTime getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(LocalDateTime alertDate) {
        this.alertDate = alertDate;
    }

}