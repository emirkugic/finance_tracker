package ba.edu.ibu.finance_tracker.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.core.model.Alert;
import ba.edu.ibu.finance_tracker.core.repository.AlertRepository;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public Optional<Alert> getAlertById(String id) {
        return alertRepository.findById(id);
    }

    public Alert updateAlert(Alert alert) {
        if (alertRepository.existsById(alert.getId())) {
            return alertRepository.save(alert);
        } else {
            throw new RuntimeException("Alert not found!");
        }
    }

    public void deleteAlert(String id) {
        alertRepository.deleteById(id);
    }
}
