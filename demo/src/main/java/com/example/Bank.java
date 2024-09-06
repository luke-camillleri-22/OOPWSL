package com.example;

public class Bank extends FinancialIntermediary {

    public Bank(String intermediaryName) {
        super(intermediaryName);
    }

    public String getIntermediaryName() {
        return intermediaryName;
    }

    @Override
    public void displayIntermediary() {
        System.out.println("Bank: " + intermediaryName);
    }
}
