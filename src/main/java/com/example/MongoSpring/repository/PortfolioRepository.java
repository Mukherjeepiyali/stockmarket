package com.example.MongoSpring.repository;

import com.example.MongoSpring.model.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
    
    // Fetch portfolios by user ID
    List<Portfolio> findByUserId(String userId);
    
    // Check if a portfolio exists by ID
    boolean existsById(String id);
}
