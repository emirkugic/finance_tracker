package ba.edu.ibu.finance_tracker.rest.controllers;

import org.springframework.web.bind.annotation.*;

import ba.edu.ibu.finance_tracker.core.model.Alert;
import ba.edu.ibu.finance_tracker.core.service.AlertService;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        return alertService.createAlert(alert);
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/{id}")
    public Alert getAlertById(@PathVariable String id) {
        return alertService.getAlertById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Alert updateAlert(@PathVariable String id, @RequestBody Alert updatedAlert) {
        if (!id.equals(updatedAlert.getId())) {
            throw new RuntimeException("ID mismatch!");
        }
        return alertService.updateAlert(updatedAlert);
    }

    @DeleteMapping("/{id}")
    public void deleteAlert(@PathVariable String id) {
        alertService.deleteAlert(id);
    }
}
