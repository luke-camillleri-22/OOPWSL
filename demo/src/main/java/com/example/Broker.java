package com.example;

public class Broker extends FinancialIntermediary {

    public Broker(String intermediaryName) {
        super(intermediaryName);
    }

    @Override
    public void displayIntermediary() {
        System.out.println("Broker: " + intermediaryName);
    }
}
