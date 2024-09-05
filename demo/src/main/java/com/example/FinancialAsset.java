package com.example;

public abstract class FinancialAsset {
    protected String name;
    protected float value;

    public FinancialAsset(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }
    
    public abstract float calculateAnnualReturn();
    public abstract void displayAsset();
}
