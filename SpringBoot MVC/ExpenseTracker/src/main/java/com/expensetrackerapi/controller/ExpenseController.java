package com.expensetrackerapi.controller;

import com.expensetrackerapi.entity.Expense;
import com.expensetrackerapi.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
@RequestMapping("/v1")
@RestController
@AllArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("/allexpense")
    public List<Expense> getAllExpenses(Pageable page){
        return expenseService.getAllExpenses(page).toList();
    }

    @GetMapping("/getbyid/{id}")
    public Expense getExpenseByID(@PathVariable("id") Long id){
        return expenseService.getExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletebyid")
    public void deleteExpenseByID(@RequestParam("id") Long id){
        expenseService.deleteExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/saveExpense")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense){
       return expenseService.saveExpense(expense);
    }

    @PutMapping("/updateExpense/{id}")
    public Expense updateExpenseDetail(@PathVariable("id") Long id,@RequestBody Expense expense){
        return expenseService.updateExpense(id,expense);
    }

    @GetMapping("expense/category")
    public List<Expense> getExpenseByCategory(@RequestParam String category, Pageable page){
        return expenseService.readByCategory(category, page);
    }

    @GetMapping("expense/name")
    public List<Expense> getExpenseByName(@RequestParam String keyword, Pageable page){
        return expenseService.readByName(keyword, page);
    }

    @GetMapping("expense/date")
    public List<Expense> getExpenseByDate(@RequestParam(required = false) Date startDate,
                                          @RequestParam(required = false)Date endDate,
                                          Pageable page){
        return expenseService.readByDate(startDate,endDate, page);
    }

}
