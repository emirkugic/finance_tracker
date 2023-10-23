package ba.edu.ibu.finance_tracker.rest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    final private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<String> createExpense(@RequestBody ExpenseCreateRequestDTO expenseRequest) {
        Expense newExpense = new Expense();
        newExpense.setUserId(expenseRequest.getUserId());
        newExpense.setAmount(expenseRequest.getAmount());
        newExpense.setCategory(expenseRequest.getCategory());
        newExpense.setSource(expenseRequest.getSource());
        newExpense.setExpenseDate(expenseRequest.getExpenseDate());
        newExpense.setRecipientChildId(expenseRequest.getRecipientChildId());

        String response = expenseService.createExpense(newExpense);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    @PutMapping("/updateAmount")
    public ResponseEntity<Boolean> updateExpenseAmount(@RequestBody UpdateExpenseAmountDTO requestDTO) {
        boolean isSuccess = expenseService.updateExpenseAmount(requestDTO.getId(), requestDTO.getNewAmount());
        return ResponseEntity.ok(isSuccess);
    }

    @GetMapping
    List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("user/{userId}")
    public List<Expense> getExpensesByUserId(@PathVariable String userId) {
        return expenseService.getAllExpensesByUserId(userId);
    }

    // @GetMapping("/parent/{parentId}")
    // public List<Expense> getAllExpensesByParentId(@PathVariable String parentId)
    // {
    // return expenseService.getAllExpensesByParentId(parentId);
    // }

    @PostMapping("/transfer/{parentId}/{childId}")
    public Expense transferToChild(@PathVariable String parentId, @PathVariable String childId,
            @RequestBody double amount) {
        return expenseService.transferToChild(parentId, childId, amount);
    }

    @GetMapping("getBetweenDates")
    public List<Expense> getAllExpenseBetweenDates(@RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
        return expenseService.getAllExpensesBetweenDates(userId, startDate, endDate);
    }

}