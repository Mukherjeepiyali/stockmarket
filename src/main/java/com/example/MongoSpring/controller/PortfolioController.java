package com.example.MongoSpring.controller;

import com.example.MongoSpring.model.Portfolio;
import com.example.MongoSpring.model.Asset;
import com.example.MongoSpring.model.User;
import com.example.MongoSpring.repository.PortfolioRepository;
import com.example.MongoSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new portfolio
    @PostMapping("/create")
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody Map<String, Object> payload) {
        String userId = (String) payload.get("userId");
        String portfolioName = (String) payload.get("portfolioName");

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Portfolio portfolio = new Portfolio(user.get(), portfolioName, new ArrayList<>());
        portfolio.setCreatedAt(new Date());
        portfolio.setUpdatedAt(new Date());

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return ResponseEntity.ok(savedPortfolio);
    }

    // Fetch all portfolios of a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Portfolio>> getUserPortfolios(@PathVariable String userId) {
        List<Portfolio> portfolios = portfolioRepository.findByUserId(userId);
        return ResponseEntity.ok(portfolios);
    }

    // Fetch a single portfolio by ID
    @GetMapping("/{portfolioId}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable String portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        return portfolio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Update an existing portfolio
    @PutMapping("/{portfolioId}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable String portfolioId, @RequestBody Map<String, Object> payload) {
        Optional<Portfolio> existingPortfolio = portfolioRepository.findById(portfolioId);

        if (!existingPortfolio.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Portfolio portfolio = existingPortfolio.get();

        if (payload.containsKey("portfolioName")) {
            portfolio.setPortfolioName((String) payload.get("portfolioName"));
        }

        // Deserialize assets properly
        if (payload.containsKey("assets")) {
            List<Asset> assetList = (List<Asset>) payload.get("assets"); 
            portfolio.setAssets(assetList);
        }

        portfolio.setUpdatedAt(new Date());  // Update timestamp

        Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
        return ResponseEntity.ok(updatedPortfolio);
    }

    // Delete a portfolio by ID
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable String portfolioId) {
        if (!portfolioRepository.existsById(portfolioId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Portfolio not found");
        }
        portfolioRepository.deleteById(portfolioId);
        return ResponseEntity.ok("Portfolio deleted successfully");
    }
}
