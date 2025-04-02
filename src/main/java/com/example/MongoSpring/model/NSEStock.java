package com.example.MongoSpring.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nsestocks") // MongoDB Collection
public class NSEStock {

    @Id
    private String id;
    private String symbol;
    private String companyName;

    // Constructors
    public NSEStock() {}

    public NSEStock(String symbol, String companyName) {
        this.symbol = symbol;
        this.companyName = companyName;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
