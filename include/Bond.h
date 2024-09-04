#ifndef BOND_H
#define BOND_H

#include "FinancialAsset.h"
#include <string>

class Bond : public FinancialAsset {
private:
    float interestRate;
    std::string maturityDate;

public:
    Bond(const std::string& name, float value, float interestRate, const std::string& maturityDate);


    float getInterestRate() const { return interestRate; }
    std::string getMaturityDate() const { return maturityDate; }
    float calculateAnnualReturn() const override;
    void displayAsset() const override;
};

#endif
