package ba.edu.ibu.finance_tracker.rest.controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ba.edu.ibu.finance_tracker.core.model.Income;
import ba.edu.ibu.finance_tracker.core.service.IncomeService;
import ba.edu.ibu.finance_tracker.rest.dto.IncomeDTO.IncomeCreateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.IncomeDTO.UpdateIncomeAmountDTO;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
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
    public void deleteIncome(@PathVariable String id) {
        incomeService.deleteIncome(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateIncomeAmount(@PathVariable String id,
            @RequestBody UpdateIncomeAmountDTO request) {
        String responseMessage = incomeService.updateIncomeAmount(id, request.getNewAmount());
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/user/{userId}")
    public List<Income> getAllIncomesByUserId(@PathVariable String userId) {
        return incomeService.getAllIncomesByUserId(userId);
    }

    @GetMapping
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("/parent/{parentId}")
    public List<Income> getAllIncomesByParentId(@PathVariable String parentId) {
        return incomeService.getAllIncomesByParentId(parentId);
    }

    @GetMapping("/getBetweenDates")
    public List<Income> getAllIncomesBetweenDates(@RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return incomeService.getAllIncomesBetweenDates(userId, startDate, endDate);
    }

    @GetMapping("/getByDate")
    public List<Income> getAllIncomesByDate(@RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return incomeService.getAllIncomesByDate(userId, date);
    }

    @GetMapping("/getBySource")
    public List<Income> getBySource(@RequestParam String userId, @RequestParam String source) {
        return incomeService.getAllBySource(userId, source);
    }

    @GetMapping("/getByReceivedThrough")
    public List<Income> getByReceivedThrough(@RequestParam String userId, @RequestParam String receivedThrough) {
        return incomeService.getAllByReceivedThrough(userId, receivedThrough);
    }
}
