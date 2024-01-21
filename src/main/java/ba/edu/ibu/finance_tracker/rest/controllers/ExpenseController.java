package ba.edu.ibu.finance_tracker.rest.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ba.edu.ibu.finance_tracker.core.model.Expense;
import ba.edu.ibu.finance_tracker.core.service.ExpenseService;
import ba.edu.ibu.finance_tracker.rest.dto.ExpenseDTO.ExpenseCreateRequestDTO;
import ba.edu.ibu.finance_tracker.rest.dto.ExpenseDTO.UpdateExpenseAmountDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/expenses")
// @SecurityRequirement(name = "JWT Security")
public class ExpenseController {

    final private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseCreateRequestDTO expenseRequest) {
        String response = expenseService.createExpense(expenseRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    @PutMapping("/updateAmount")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Boolean> updateExpenseAmount(@RequestBody UpdateExpenseAmountDTO requestDTO) {
        boolean isSuccess = expenseService.updateExpenseAmount(requestDTO.getId(), requestDTO.getNewAmount());
        return ResponseEntity.ok(isSuccess);
    }

    @GetMapping
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("user/{userId}")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Expense> getExpensesByUserId(@PathVariable String userId) {
        return expenseService.getAllExpensesByUserId(userId);
    }

    @PostMapping("/transfer/{parentId}/{childId}")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Expense transferToChild(@PathVariable String parentId, @PathVariable String childId,
            @RequestBody double amount) {
        return expenseService.transferToChild(parentId, childId, amount);
    }

    @GetMapping("/getBetweenDates")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Expense> getAllExpenseBetweenDates(@RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return expenseService.getAllExpensesBetweenDates(userId, startDate, endDate);
    }

    @GetMapping("/getByDate")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Expense> getAllExpenseByDate(@RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return expenseService.getAllExpensesByDate(userId, date);
    }

    @GetMapping("/getByCategory")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Expense> getAllExpenseByCategory(@RequestParam String userId, @RequestParam String category) {
        return expenseService.getAllExpensesByCategory(userId, category);
    }

    @GetMapping("/getBySource")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Expense> getAllBySource(@RequestParam String userId, @RequestParam String source) {
        return expenseService.getExpensesBySource(userId, source);
    }

    @GetMapping("/totalAmountByCategoryOrSource")
    // @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public double getSumByCategoryOrSourceAndDateRange(
            @RequestParam String userId,
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> source,
            // yyyy-MM-dd format
            @RequestParam Optional<String> startDate,
            @RequestParam Optional<String> endDate) {

        LocalDate start = startDate.map(date -> LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                .orElse(LocalDate.of(2020, 1, 1));
        LocalDate end = endDate.map(date -> LocalDate.parse(date, DateTimeFormatter.ISO_DATE)).orElse(LocalDate.now());

        return expenseService.getSumAmountByCategoryOrSourceAndDateRange(userId, category, source, Optional.of(start),
                Optional.of(end));
    }

}
