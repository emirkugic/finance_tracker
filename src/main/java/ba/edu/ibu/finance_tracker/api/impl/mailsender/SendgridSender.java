package ba.edu.ibu.finance_tracker.api.impl.mailsender;

import org.springframework.stereotype.Component;
import ba.edu.ibu.finance_tracker.core.api.mailsender.MailSender;
import ba.edu.ibu.finance_tracker.core.model.User;
import java.util.List;

@Component

public class SendgridSender implements MailSender {

    @Override
    public String send(List<User> users, String message) {
        for (User user : users) {
            System.out.println("Message sent to: " + user.getEmail());
        }
        return "Message: " + message + " | Sent via Sendgrid.";
    }
}
