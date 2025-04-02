package com.example.MongoSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.MongoSpring.model.DematAccount;
import com.example.MongoSpring.services.DematAccountService;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")  // Allow requests from React frontend
@RestController
@RequestMapping("/api/demat")
public class DematAccountController {

    @Autowired
    private DematAccountService dematAccountService;

    // ✅ Create a new Demat account
    @PostMapping("/create")
    public DematAccount createAccount(@RequestBody DematAccount dematAccount) {
        return dematAccountService.createDematAccount(dematAccount);
    }

    // ✅ Get account details by PAN number
    @GetMapping("/{pan}")
    public Optional<DematAccount> getAccountByPan(@PathVariable String pan) {
        return dematAccountService.getAccountByPan(pan);
    }

    // ✅ Deposit/Withdraw money
    @PostMapping("/update-balance")
    public String updateBalance(@RequestParam String panNumber, @RequestParam double amount) {
        return dematAccountService.updateAccountBalance(panNumber, amount);
    }

    // ✅ Buy stock (deduct balance)
    @PostMapping("/buy-stock")
    public String buyStock(@RequestParam String panNumber, @RequestParam double price, @RequestParam int quantity) {
        return dematAccountService.buyStock(panNumber, price, quantity);
    }

    // ✅ Sell stock (increase balance)
    @PostMapping("/sell-stock")
    public String sellStock(@RequestParam String panNumber, @RequestParam double price, @RequestParam int quantity) {
        return dematAccountService.sellStock(panNumber, price, quantity);
    }

    // ✅ Fetch Demat Account Balance by PAN
@GetMapping("/balance/{pan}")
public double getBalance(@PathVariable String pan) {
    return dematAccountService.getBalanceByPan(pan);
}

}
