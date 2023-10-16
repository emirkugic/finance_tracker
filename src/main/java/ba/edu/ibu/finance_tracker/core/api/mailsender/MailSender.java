package ba.edu.ibu.finance_tracker.core.api.mailsender;

import java.util.List;
import org.springframework.stereotype.Component;
import ba.edu.ibu.finance_tracker.core.model.User;

@Component
public interface MailSender {

    public String send(List<User> users, String message);
}
