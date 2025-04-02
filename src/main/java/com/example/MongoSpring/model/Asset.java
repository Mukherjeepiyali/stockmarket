package com.example.MongoSpring.model;

public class Asset {

    private String assetName;
    private String assetType; // Stocks, Mutual Funds, etc.
    private double quantity;
    private double purchasePrice;
    private double currentPrice;

    public Asset() {}

    public Asset(String assetName, String assetType, double quantity, double purchasePrice, double currentPrice) {
        this.assetName = assetName;
        this.assetType = assetType;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
    }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public String getAssetType() { return assetType; }
    public void setAssetType(String assetType) { this.assetType = assetType; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }

    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public double calculateAssetValue() {
        return quantity * currentPrice;
    }

    public double calculateGains() {
        return (currentPrice - purchasePrice) * quantity;
    }
}
