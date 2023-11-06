package ba.edu.ibu.finance_tracker.rest.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ba.edu.ibu.finance_tracker.core.model.Alert;
import ba.edu.ibu.finance_tracker.core.service.AlertService;
import ba.edu.ibu.finance_tracker.rest.dto.AlertDTO.AlertCreateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.AlertDTO.AlertUpdateRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@PreAuthorize("hasAuthority('ADMIN')")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public Alert createAlert(@RequestBody AlertCreateRequestDTO alertRequest) {
        return alertService.createAlert(alertRequest);
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/{id}")
    public Alert getAlertById(@PathVariable String id) {
        return alertService.getAlertById(id).orElse(null);
    }

    @PutMapping("/update")
    public Alert updateAlert(@RequestBody AlertUpdateRequestDTO updateRequest) {
        return alertService.updateAlert(updateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteAlert(@PathVariable String id) {
        alertService.deleteAlert(id);
    }
}
