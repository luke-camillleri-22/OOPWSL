#ifndef MUTUAL_FUND_H
#define MUTUAL_FUND_H

#include "FinancialAsset.h"

class MutualFund : public FinancialAsset {
private:
    float expenseRatio;

public:
    MutualFund(const std::string& name, float value, float expenseRatio);

    float getExpenseRatio() const { return expenseRatio; }

    float calculateAnnualReturn() const override;
    void displayAsset() const override;
};

#endif
