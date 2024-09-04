#ifndef MUTUAL_FUND_MANAGER_H
#define MUTUAL_FUND_MANAGER_H

#include "FinancialIntermediary.h"

class MutualFundManager : public FinancialIntermediary {
public:
    MutualFundManager(const std::string& intermediaryName);

    void displayIntermediary() const override;
};

#endif
