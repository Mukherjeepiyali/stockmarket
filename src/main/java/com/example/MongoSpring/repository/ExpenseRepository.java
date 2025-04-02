package com.example.MongoSpring.repository;

import com.example.MongoSpring.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByUserId(String userId);

    List<Expense> findByUserIdAndDateAfter(String userId, LocalDateTime date);
}
