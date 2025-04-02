package com.example.MongoSpring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "portfolios")
public class Portfolio {

    @Id
    private String id;

    @DBRef
    private User user;  

    private String portfolioName;

    @Field("assets")
    private List<Asset> assets;  

    @Field("createdAt")
    private Date createdAt;

    @Field("updatedAt")
    private Date updatedAt;

    public Portfolio() {
        this.assets = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Portfolio(User user, String portfolioName, List<Asset> assets) {
        this.user = user;
        this.portfolioName = portfolioName;
        this.assets = assets != null ? assets : new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Getters
    public String getId() { return id; }
    public User getUser() { return user; }
    public String getPortfolioName() { return portfolioName; }
    public List<Asset> getAssets() { return assets; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }

    // Setters
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets != null ? new ArrayList<>(assets) : new ArrayList<>();
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
