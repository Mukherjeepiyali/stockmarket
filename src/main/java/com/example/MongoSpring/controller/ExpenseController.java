package com.example.MongoSpring.controller;

import com.example.MongoSpring.model.Expense;
import com.example.MongoSpring.model.User;
import com.example.MongoSpring.repository.UserRepository;
import com.example.MongoSpring.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;

    // ✅ DTO for Adding Expenses
    public static class ExpenseRequest {
        private String email;
        private String category;
        private BigDecimal amount;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }

    // 📌 Add Expense
    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequest request) {
        if (request.getEmail() == null || request.getCategory() == null || request.getAmount() == null) {
            return ResponseEntity.badRequest().body("Email, category, and amount are required");
        }

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String userId = user.get().getId();
        Expense expense = expenseService.addExpense(userId, request.getCategory(), request.getAmount());

        return ResponseEntity.ok(expense);
    }

    // 📌 Get Expenses List
    @GetMapping("/list")
    public ResponseEntity<?> getExpenses(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String userId = user.get().getId();
        List<Expense> expenses = expenseService.getExpenses(userId);

        if (expenses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No expenses found for this user.");
        }

        return ResponseEntity.ok(expenses);
    }

    // 📌 Get Spending Trends (Weekly & Monthly)
    @GetMapping("/trends")
    public ResponseEntity<?> getSpendingTrends(@RequestParam String email, @RequestParam String period) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String userId = user.get().getId();
        Map<String, BigDecimal> spendingTrends = expenseService.getSpendingTrends(userId, period);

        if (spendingTrends.isEmpty()) {
            return ResponseEntity.ok("No spending data available for the selected period.");
        }

        return ResponseEntity.ok(spendingTrends);
    }
}
