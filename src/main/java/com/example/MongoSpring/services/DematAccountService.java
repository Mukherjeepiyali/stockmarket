package com.example.MongoSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.MongoSpring.model.DematAccount;
import com.example.MongoSpring.repository.DematAccountRepository;
import java.util.Optional;

@Service
public class DematAccountService {

    @Autowired
    private DematAccountRepository dematAccountRepository;

    // ✅ Create a new Demat account
    public DematAccount createDematAccount(DematAccount dematAccount) {
        return dematAccountRepository.save(dematAccount);
    }

    // ✅ Fetch account details using PAN number
    public Optional<DematAccount> getAccountByPan(String panNumber) {
        return dematAccountRepository.findByPanNumber(panNumber);
    }

    // ✅ Update account balance (deposit/withdraw)
    public String updateAccountBalance(String panNumber, double amount) {
        Optional<DematAccount> accountOpt = dematAccountRepository.findByPanNumber(panNumber);
        
        if (accountOpt.isPresent()) {
            DematAccount account = accountOpt.get();
            
            if (account.updateBalance(amount)) {
                dematAccountRepository.save(account);
                return "✅ Transaction successful! New balance: ₹" + account.getBalance();
            }
            return "❌ Transaction failed! Insufficient balance.";
        }
        return "❌ Account not found!";
    }

    // ✅ Buy stocks (deducts balance)
    public String buyStock(String panNumber, double stockPrice, int quantity) {
        double totalCost = stockPrice * quantity;
        return updateAccountBalance(panNumber, -totalCost);
    }

    // ✅ Sell stocks (adds balance)
    public String sellStock(String panNumber, double stockPrice, int quantity) {
        double totalEarnings = stockPrice * quantity;
        return updateAccountBalance(panNumber, totalEarnings);
    }

    public double getBalanceByPan(String pan) {
        Optional<DematAccount> account = dematAccountRepository.findByPanNumber(pan); // ✅ Correct query
        return account.map(DematAccount::getBalance).orElse(0.0); // ✅ Return correct balance
    }
    
    
}
