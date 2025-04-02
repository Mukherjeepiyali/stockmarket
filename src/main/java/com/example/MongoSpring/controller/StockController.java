package com.example.MongoSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.MongoSpring.model.NSEStock;
import com.example.MongoSpring.repository.NSEStockRepository;
import com.example.MongoSpring.services.AlphaVantageService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stocks")
@CrossOrigin(origins = "http://localhost:3000") // ✅ Allow React frontend
public class StockController {

    @Autowired
    private AlphaVantageService alphaVantageService;

    @Autowired
    private NSEStockRepository stockRepository;

    // ✅ Fetch all stored stock symbols
    @GetMapping("/symbols")
    public List<String> getAllStockSymbols() {
        return alphaVantageService.getAllStockSymbols();
    }

    // ✅ Fetch real-time data for all stocks
    @GetMapping("/real-time/all")
    public List<Map<String, Object>> getAllRealTimeStockData() {
        return alphaVantageService.getAllRealTimeStockData();
    }

    // ✅ API to add a new stock symbol to MongoDB
    @PostMapping("/add")
    public List<NSEStock> addStocks(@RequestBody List<NSEStock> stocks) {
        return stockRepository.saveAll(stocks);
    }
}
