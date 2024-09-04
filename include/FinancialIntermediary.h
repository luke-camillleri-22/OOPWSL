#ifndef FINANCIAL_INTERMEDIARY_H
#define FINANCIAL_INTERMEDIARY_H

#include <string>
#include <iostream>

// Abstract base class for financial intermediaries
class FinancialIntermediary {
protected:
    std::string intermediaryName;

public:
    FinancialIntermediary(const std::string& intermediaryName)
        : intermediaryName(intermediaryName) {}

    virtual ~FinancialIntermediary() = default;

    // Getter and setter for intermediaryName
    std::string getIntermediaryName() const { return intermediaryName; }
    void setIntermediaryName(const std::string& newName) { intermediaryName = newName; }

    virtual void displayIntermediary() const = 0;
};

#endif
