package ba.edu.ibu.finance_tracker.core.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ba.edu.ibu.finance_tracker.core.model.Alert;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.AlertRepository;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;
import ba.edu.ibu.finance_tracker.rest.dto.AlertDTO.AlertCreateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.AlertDTO.AlertUpdateRequestDTO;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public AlertService(AlertRepository alertRepository, UserRepository userRepository) {
        this.alertRepository = alertRepository;
        this.userRepository = userRepository;
    }

    public Alert createAlert(AlertCreateRequestDTO alertRequest) {
        Alert newAlert = new Alert();
        newAlert.setUserId(alertRequest.getUserId());
        newAlert.setMessage(alertRequest.getMessage());
        newAlert.setAlertDate(alertRequest.getAlertDate());

        // commented out because it's annoying when testing the api
        // if (alertRequest.getAlertDate().isBefore(java.time.LocalDateTime.now())) {
        // throw new RuntimeException("Alert date cannot be in the past!");
        // }
        if (alertRequest.getMessage().length() > 140) {
            throw new RuntimeException("Alert message cannot be longer than 100 characters!");
        }

        Optional<User> existingUser = userRepository.findById(alertRequest.getUserId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("UserID doesn't exist");
        }

        return alertRepository.save(newAlert);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public Optional<Alert> getAlertById(String id) {
        Optional<Alert> alert = alertRepository.findById(id);
        if (alert.isPresent()) {
            return alert;
        } else {
            throw new RuntimeException("Alert ID not found!");
        }
    }

    public Alert updateAlert(AlertUpdateRequestDTO updateRequest) {
        Optional<Alert> existingAlertOpt = alertRepository.findById(updateRequest.getAlertId());

        if (!existingAlertOpt.isPresent()) {
            throw new RuntimeException("Alert not found!");
        }

        Alert existingAlert = existingAlertOpt.get();
        existingAlert.setMessage(updateRequest.getNewMessage());
        return alertRepository.save(existingAlert);
    }

    public void deleteAlert(String id) {
        Optional<Alert> alert = alertRepository.findById(id);
        if (alert.isPresent()) {
            alertRepository.delete(alert.get());
        } else {
            throw new RuntimeException("Alert ID not found!");
        }
    }
}
