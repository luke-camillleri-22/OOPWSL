package com.example;

public class MutualFundManager extends FinancialIntermediary {

    public MutualFundManager(String intermediaryName) {
        super(intermediaryName);
    }

    public String getIntermediaryName() {
        return intermediaryName;
    }

    @Override
    public void displayIntermediary() {
        System.out.println("Mutual Fund Manager: " + intermediaryName);
    }
}
