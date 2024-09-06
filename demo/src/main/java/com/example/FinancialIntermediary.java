package com.example;

public abstract class FinancialIntermediary {

    protected String intermediaryName;

    public FinancialIntermediary(String intermediaryName) {
        this.intermediaryName = intermediaryName;
    }

    public String getIntermediaryName() {
        return intermediaryName;
    }

    public abstract void displayIntermediary();
}
