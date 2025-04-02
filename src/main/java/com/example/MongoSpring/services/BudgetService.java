package com.example.MongoSpring.services;

import com.example.MongoSpring.model.Budget;
import com.example.MongoSpring.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    public Budget setBudget(String userId, BigDecimal totalLimit) {
        Budget budget = budgetRepository.findByUserId(userId).orElse(new Budget(userId, totalLimit, null));
        budget.setTotalLimit(totalLimit);
        return budgetRepository.save(budget);
    }

    public Budget getBudget(String userId) {
        return budgetRepository.findByUserId(userId).orElse(null);
    }

    public void updateSpentAmount(String userId, String category, BigDecimal amount) {
        Optional<Budget> optionalBudget = budgetRepository.findByUserId(userId);
        if (optionalBudget.isPresent()) {
            Budget budget = optionalBudget.get();
            budget.setTotalSpent(budget.getTotalSpent().add(amount));

            budget.getCategorySpent().put(category, budget.getCategorySpent().getOrDefault(category, BigDecimal.ZERO).add(amount));

            budgetRepository.save(budget);
        }
    }

    public BigDecimal getPercentageSpent(String userId) {
        return budgetRepository.findByUserId(userId).map(Budget::getPercentageSpent).orElse(BigDecimal.ZERO);
    }
}
