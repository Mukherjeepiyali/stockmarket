package com.example.MongoSpring.services;

import com.example.MongoSpring.model.Portfolio;
import com.example.MongoSpring.model.Asset;
import com.example.MongoSpring.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    // Fetch all portfolios of a specific user
    public List<Portfolio> getUserPortfolios(String userId) {
        return portfolioRepository.findByUserId(userId);
    }

    // Fetch a portfolio by its ID
    public Optional<Portfolio> getPortfolioById(String id) {
        return portfolioRepository.findById(id);
    }

    // Create a new portfolio
    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    // Update an existing portfolio
    public Portfolio updatePortfolio(String id, Portfolio updatedPortfolio) {
        return portfolioRepository.findById(id).map(portfolio -> {
            portfolio.setPortfolioName(updatedPortfolio.getPortfolioName());
            if (updatedPortfolio.getAssets() != null) {
                portfolio.setAssets(updatedPortfolio.getAssets());
            }
            return portfolioRepository.save(portfolio);
        }).orElseThrow(() -> new RuntimeException("Portfolio not found with ID: " + id));
    }

    // Delete a portfolio by ID
    public void deletePortfolio(String id) {
        if (!portfolioRepository.existsById(id)) {
            throw new RuntimeException("Portfolio not found with ID: " + id);
        }
        portfolioRepository.deleteById(id);
    }

    // Calculate the total value of the portfolio
    public double calculateTotalPortfolioValue(String portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return Optional.ofNullable(portfolio.getAssets())
                .orElse(Collections.emptyList())
                .stream()
                .mapToDouble(Asset::calculateAssetValue)
                .sum();
    }

    // Calculate the total gains of the portfolio
    public double calculateTotalGains(String portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return Optional.ofNullable(portfolio.getAssets())
                .orElse(Collections.emptyList())
                .stream()
                .mapToDouble(Asset::calculateGains)
                .sum();
    }
}
