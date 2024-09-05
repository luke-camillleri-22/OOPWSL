package com.example;

public class Bond extends FinancialAsset {
    private float interestRate;
    private String maturityDate;

    public Bond(String name, float value, float interestRate, String maturityDate) {
        super(name, value);
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    @Override
    public float calculateAnnualReturn() {
        return value * interestRate;  // This formula is correct
    }

    @Override
    public void displayAsset() {
        System.out.println("Bond: " + name + ", Value: $" + value + ", Interest Rate: " + interestRate
                + ", Maturity Date: " + maturityDate);
    }
}
