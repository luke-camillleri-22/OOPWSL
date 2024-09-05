package com.example;

public class Stock extends FinancialAsset {
    private String tickerSymbol;
    private int shares;
    private float dividendYield;

    public Stock(String name, float value, String tickerSymbol, int shares, float dividendYield) {
        super(name, value);
        this.tickerSymbol = tickerSymbol;
        this.shares = shares;
        this.dividendYield = dividendYield;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public int getShares() {
        return shares;
    }

    public float getDividendYield() {
        return dividendYield;
    }

    @Override
    public float calculateAnnualReturn() {
        // Correct formula for calculating dividend yield per share
        return (value * dividendYield) / shares;
    }

    @Override
    public void displayAsset() {
        System.out.println("Stock: " + name + ", Ticker: " + tickerSymbol + ", Value: $" + value
                + ", Shares: " + shares + ", Dividend Yield: " + dividendYield);
    }
    
}
