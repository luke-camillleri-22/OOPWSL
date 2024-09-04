#ifndef FINANCIAL_ASSET_H
#define FINANCIAL_ASSET_H

#include <string>
#include <iostream>

// abstract base class for financial assets
class FinancialAsset {
protected:
    std::string name;
    float value;

public:
    FinancialAsset(const std::string& name, float value)
        : name(name), value(value) {}

    virtual ~FinancialAsset() = default;

    // Getter and setter for name
    std::string getName() const { return name; }
    void setName(const std::string& newName) { name = newName; }

    // Getter and setter for value
    float getValue() const { return value; }
    void setValue(float newValue) { value = newValue; }

    virtual float calculateAnnualReturn() const = 0;
    virtual void displayAsset() const = 0;
};

#endif
