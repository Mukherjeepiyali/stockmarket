package com.example.MongoSpring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.MongoSpring.model.NSEStock;
import com.example.MongoSpring.repository.NSEStockRepository;

import java.util.*;
import java.util.logging.Logger;

@Service
public class AlphaVantageService {

    private static final Logger LOGGER = Logger.getLogger(AlphaVantageService.class.getName());

    @Value("${alphavantage.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final NSEStockRepository stockRepository;

    public AlphaVantageService(NSEStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // ✅ Fetch all stock symbols from MongoDB
    public List<String> getAllStockSymbols() {
        List<NSEStock> stocks = stockRepository.findAll();
        List<String> symbols = new ArrayList<>();

        for (NSEStock stock : stocks) {
            String formattedSymbol = stock.getSymbol();
            if (!formattedSymbol.endsWith(".BSE") && !formattedSymbol.endsWith(".NSE")) {
                formattedSymbol += ".BSE"; // Default to BSE
            }
            symbols.add(formattedSymbol);
        }

        LOGGER.info("Fetched Symbols from MongoDB: " + symbols);
        return symbols;
    }

    // ✅ Fetch real-time stock data for all stored symbols
    public List<Map<String, Object>> getAllRealTimeStockData() {
        List<Map<String, Object>> stockDataList = new ArrayList<>();
        List<String> stockSymbols = getAllStockSymbols();

        for (String symbol : stockSymbols) {
            String url = String.format(
                "%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                BASE_URL, symbol, apiKey);

            try {
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);
                LOGGER.info("Fetched real-time data for: " + symbol + " => " + response);

                if (response != null && response.containsKey("Global Quote")) {
                    stockDataList.add(response);
                } else {
                    LOGGER.warning("No valid real-time data for: " + symbol);
                }
            } catch (Exception e) {
                LOGGER.severe("Error fetching real-time data for " + symbol + ": " + e.getMessage());
            }
        }
        return stockDataList;
    }
}
