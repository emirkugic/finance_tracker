package ba.edu.ibu.finance_tracker.core.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;

@Document
public class Income {

    @Id
    private String id;
    private String userId;
    private double amount;
    private String source;
    private String receivedThrough;
    private String from;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime receivedDate; // the reason I didn't put = LocalDateTime.now() is because I want to set it
                                        // manually in case the user has forgotten to add the income on the day he
                                        // received it, but the user can also leave it empty and it will be set to
                                        // today's date in the service

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getReceivedThrough() {
        return receivedThrough;
    }

    public void setReceivedThrough(String receivedThrough) {
        this.receivedThrough = receivedThrough;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
