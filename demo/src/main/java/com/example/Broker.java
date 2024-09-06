package com.example;

public class Broker extends FinancialIntermediary {

    public Broker(String intermediaryName) {
        super(intermediaryName);
    }

    public String getIntermediaryName() {
        return intermediaryName;
    }

    @Override
    public void displayIntermediary() {
        System.out.println("Broker: " + intermediaryName);
    }
}
