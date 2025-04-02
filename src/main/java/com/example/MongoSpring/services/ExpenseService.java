package com.example.MongoSpring.services;

import com.example.MongoSpring.model.Expense;
import com.example.MongoSpring.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // ✅ Add Expense
    public Expense addExpense(String userId, String category, BigDecimal amount) {
        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setDate(LocalDateTime.now());
        return expenseRepository.save(expense);
    }

    // ✅ Get all expenses for a user
    public List<Expense> getExpenses(String userId) {
        return expenseRepository.findByUserId(userId);
    }

    // ✅ Get Spending Trends
    public Map<String, BigDecimal> getSpendingTrends(String userId, String period) {
        LocalDateTime startDate;

        if ("weekly".equalsIgnoreCase(period)) {
            startDate = LocalDateTime.now().minusDays(7);
        } else if ("monthly".equalsIgnoreCase(period)) {
            startDate = LocalDateTime.now().minusDays(30);
        } else {
            throw new IllegalArgumentException("Invalid period. Use 'weekly' or 'monthly'.");
        }

        List<Expense> filteredExpenses = expenseRepository.findByUserIdAndDateAfter(userId, startDate);

        if (filteredExpenses.isEmpty()) {
            return Collections.emptyMap();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return filteredExpenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getDate().format(formatter),
                        TreeMap::new,
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));
    }
}
