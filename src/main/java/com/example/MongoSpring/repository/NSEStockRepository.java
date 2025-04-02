package com.example.MongoSpring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.MongoSpring.model.NSEStock;

@Repository
public interface NSEStockRepository extends MongoRepository<NSEStock, String> {
    NSEStock findBySymbol(String symbol); // Fetch stock by symbol
}
