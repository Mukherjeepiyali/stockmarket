package com.example.MongoSpring.controller;

import com.example.MongoSpring.model.Budget;
import com.example.MongoSpring.model.User;
import com.example.MongoSpring.repository.UserRepository;
import com.example.MongoSpring.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserRepository userRepository;

    // DTO for Setting Budget
    public static class BudgetRequest {
        private String email;
        private BigDecimal limit;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public BigDecimal getLimit() { return limit; }
        public void setLimit(BigDecimal limit) { this.limit = limit; }
    }

    // 📌 Set Budget for User
    @PostMapping("/set")
    public ResponseEntity<?> setBudget(@RequestBody BudgetRequest request) {
        if (request.getEmail() == null || request.getLimit() == null) {
            return ResponseEntity.badRequest().body("Email and budget limit are required");
        }

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Budget budget = budgetService.setBudget(user.get().getId(), request.getLimit());
        return ResponseEntity.ok(budget);
    }

    // 📌 Get Budget Details
    @GetMapping("/get")
    public ResponseEntity<?> getBudget(@RequestParam String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Budget budget = budgetService.getBudget(user.get().getId());
        if (budget == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No budget found for this user.");
        }

        return ResponseEntity.ok(budget);
    }

    // 📌 Get Percentage of Budget Spent
    @GetMapping("/percentage")
    public ResponseEntity<?> getPercentageSpent(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        BigDecimal percentageSpent = budgetService.getPercentageSpent(user.get().getId());
        return ResponseEntity.ok(percentageSpent);
    }

    // 📌 Check If Over Budget
    @GetMapping("/warning")
    public ResponseEntity<?> checkBudgetWarning(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Budget budget = budgetService.getBudget(user.get().getId());
        if (budget == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No budget found.");
        }

        boolean isOverBudget = budget.isOverBudget();
        if (isOverBudget) {
            return ResponseEntity.ok("⚠️ Warning: You have exceeded your budget!");
        } else {
            return ResponseEntity.ok("✅ Your spending is within budget.");
        }
    }
}
