package com.example;

public class Bank extends FinancialIntermediary {

    public Bank(String intermediaryName) {
        super(intermediaryName);
    }

    @Override
    public void displayIntermediary() {
        System.out.println("Bank: " + intermediaryName);
    }
}
