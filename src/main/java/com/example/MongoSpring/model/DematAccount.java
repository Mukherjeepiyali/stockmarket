package com.example.MongoSpring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "demat_accounts")
public class DematAccount {

    @Id
    private String id;

    private String panNumber;
    private String aadharNumber;
    private String fullName;
    private String email;
    
    private double balance = 0;  // Default balance is ₹0

    // ✅ Method to update balance safely (positive & negative amounts)
    public boolean updateBalance(double amount) {
        if (this.balance + amount >= 0) { // Ensure balance never goes negative
            this.balance += amount;
            return true;
        }
        return false; // Transaction failed due to insufficient funds
    }
}
