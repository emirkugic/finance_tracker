package ba.edu.ibu.finance_tracker.rest.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ba.edu.ibu.finance_tracker.core.model.Income;
import ba.edu.ibu.finance_tracker.core.service.IncomeService;
import ba.edu.ibu.finance_tracker.rest.dto.IncomeDTO.IncomeCreateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.IncomeDTO.UpdateIncomeAmountDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/incomes")
// @SecurityRequirement(name = "JWT Security")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Income createIncome(@RequestBody IncomeCreateRequestDTO incomeRequestDTO) {
        Income newIncome = new Income();
        newIncome.setUserId(incomeRequestDTO.getUserId());
        newIncome.setAmount(incomeRequestDTO.getAmount());
        newIncome.setSource(incomeRequestDTO.getSource());
        newIncome.setReceivedThrough(incomeRequestDTO.getReceivedThrough());
        newIncome.setFrom(incomeRequestDTO.getFrom());
        newIncome.setReceivedDate(incomeRequestDTO.getReceivedDate());

        return incomeService.createIncome(newIncome);
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void deleteIncome(@PathVariable String id) {
        incomeService.deleteIncome(id);
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> updateIncomeAmount(@PathVariable String id,
            @RequestBody UpdateIncomeAmountDTO request) {
        String responseMessage = incomeService.updateIncomeAmount(id, request.getNewAmount());
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/user/{userId}")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Income> getAllIncomesByUserId(@PathVariable String userId) {
        return incomeService.getAllIncomesByUserId(userId);
    }

    @GetMapping
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("/parent/{parentId}")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Income> getAllIncomesByParentId(@PathVariable String parentId) {
        return incomeService.getAllIncomesByParentId(parentId);
    }

    @GetMapping("/getBetweenDates")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Income> getAllIncomesBetweenDates(@RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return incomeService.getAllIncomesBetweenDates(userId, startDate, endDate);
    }

    @GetMapping("/getByDate")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Income> getAllIncomesByDate(@RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return incomeService.getAllIncomesByDate(userId, date);
    }

    @GetMapping("/getBySource")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Income> getBySource(@RequestParam String userId, @RequestParam String source) {
        return incomeService.getAllBySource(userId, source);
    }

    @GetMapping("/getByReceivedThrough")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Income> getByReceivedThrough(@RequestParam String userId, @RequestParam String receivedThrough) {
        return incomeService.getAllByReceivedThrough(userId, receivedThrough);
    }

    @GetMapping("/getByFrom")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Income> getByFrom(@RequestParam String userId, @RequestParam String from) {
        return incomeService.getAllByFrom(userId, from);
    }

    @GetMapping("/amountSumBySourceOrReceivedThroughOrFrom")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public double getAmountSumBySourceOrReceivedThroughOrFrom(
            @RequestParam String userId,
            @RequestParam Optional<String> source,
            @RequestParam Optional<String> receivedThrough,
            @RequestParam Optional<String> from,
            @RequestParam Optional<String> startDate,
            @RequestParam Optional<String> endDate) {

        LocalDate start = startDate.map(date -> LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                .orElse(LocalDate.of(2020, 1, 1));
        LocalDate end = endDate.map(date -> LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                .orElse(LocalDate.now());

        return incomeService.getSumIncomeByCriteriaAndDateRange(userId, source, receivedThrough, from,
                Optional.of(start), Optional.of(end));
    }

}
