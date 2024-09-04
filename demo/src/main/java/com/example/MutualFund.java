package com.example;

public class MutualFund extends FinancialAsset {
    private float expenseRatio;

    public MutualFund(String name, float value, float expenseRatio) {
        super(name, value);
        this.expenseRatio = expenseRatio;
    }

    @Override
    public float calculateAnnualReturn() {
        return value * expenseRatio;
    }

    @Override
    public void displayAsset() {
        System.out.println("Mutual Fund: " + name + ", Value: $" + value + ", Expense Ratio: " + expenseRatio);
    }
}
