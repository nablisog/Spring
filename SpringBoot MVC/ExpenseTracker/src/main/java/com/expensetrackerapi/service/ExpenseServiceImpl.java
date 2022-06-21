package com.expensetrackerapi.service;

import com.expensetrackerapi.entity.Expense;
import com.expensetrackerapi.exception.ResourceNotFoundException;
import com.expensetrackerapi.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(),page);

    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(),id);
        if(expense.isPresent()){
            return expense.get();
        }
        else{
            throw new ResourceNotFoundException("Expense is not found " + id);
        }

    }

    @Override
    public void deleteExpenseById(Long id) {
       Expense expense =  getExpenseById(id);
        expenseRepository.delete(expense);
    }

    @Override
    public Expense saveExpense(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existiongExpense = getExpenseById(id);
        existiongExpense.setName(expense.getName() != null ? expense.getName() : existiongExpense.getName());
        existiongExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existiongExpense.getDescription());
        existiongExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existiongExpense.getAmount());
        existiongExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existiongExpense.getCategory());
        existiongExpense.setDate(expense.getDate() != null ? expense.getDate() : existiongExpense.getDate());

        return expenseRepository.save(existiongExpense);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
        return expenseRepository.
                findByUserIdAndCategory(userService.getLoggedInUser().getId(),category,page).toList();
    }

    @Override
    public List<Expense> readByName(String keyword, Pageable page) {
        return expenseRepository.findByUserIdAndNameContaining(
                                 userService.getLoggedInUser().getId(),keyword,page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
        if (startDate == null){
            startDate = new Date(0);
        }
        if (endDate == null){
            endDate = new Date(System.currentTimeMillis());
        }
        return expenseRepository.findByUserIdAndDateBetween(
                userService.getLoggedInUser().getId(),startDate,endDate,page).toList();
    }

}
