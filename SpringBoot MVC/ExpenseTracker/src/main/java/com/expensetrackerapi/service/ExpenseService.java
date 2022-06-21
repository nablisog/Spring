package com.expensetrackerapi.service;

import com.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ExpenseService {
    Page<Expense> getAllExpenses(Pageable page);
    Expense getExpenseById(Long id);
    void deleteExpenseById(Long id);
    Expense saveExpense(Expense expense);
    Expense updateExpense(Long id, Expense expense);
    List<Expense> readByCategory(String category,Pageable page);
    List<Expense> readByName(String keyword,Pageable page);
    List<Expense> readByDate(Date startDate,Date endDate, Pageable page);



}
