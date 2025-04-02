package com.example.MongoSpring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "budgets")
public class Budget {
    @Id
    private String id;
    private String userId;
    private BigDecimal totalLimit;
    private BigDecimal totalSpent;
    private Map<String, BigDecimal> categoryLimits;
    private Map<String, BigDecimal> categorySpent;

    public Budget() {
        this.totalSpent = BigDecimal.ZERO;
        this.categoryLimits = new HashMap<>();
        this.categorySpent = new HashMap<>();
    }

    public Budget(String userId, BigDecimal totalLimit, Map<String, BigDecimal> categoryLimits) {
        this.userId = userId;
        this.totalLimit = totalLimit;
        this.totalSpent = BigDecimal.ZERO;
        this.categoryLimits = categoryLimits != null ? categoryLimits : new HashMap<>();
        this.categorySpent = new HashMap<>();
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public BigDecimal getTotalLimit() { return totalLimit; }
    public void setTotalLimit(BigDecimal totalLimit) { this.totalLimit = totalLimit; }
    public BigDecimal getTotalSpent() { return totalSpent; }
    public void setTotalSpent(BigDecimal totalSpent) { this.totalSpent = totalSpent; }
    public Map<String, BigDecimal> getCategoryLimits() { return categoryLimits; }
    public void setCategoryLimits(Map<String, BigDecimal> categoryLimits) { this.categoryLimits = categoryLimits; }
    public Map<String, BigDecimal> getCategorySpent() { return categorySpent; }
    public void setCategorySpent(Map<String, BigDecimal> categorySpent) { this.categorySpent = categorySpent; }

    // Calculate percentage spent
    public BigDecimal getPercentageSpent() {
        return totalLimit.compareTo(BigDecimal.ZERO) > 0 ?
                totalSpent.multiply(BigDecimal.valueOf(100)).divide(totalLimit, 2, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;
    }

    // Check if user is over budget
    public boolean isOverBudget() {
        return totalSpent.compareTo(totalLimit) > 0;
    }
}
