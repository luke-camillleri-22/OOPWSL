#ifndef STOCK_H
#define STOCK_H

#include "FinancialAsset.h"

class Stock : public FinancialAsset {
private:
    std::string tickerSymbol;
    int shares;
    float dividendYield;

public:
    Stock(const std::string& name, float value, const std::string& tickerSymbol, int shares, float dividendYield);

    int getShares() const { return shares; }
    float getDividendYield() const { return dividendYield; }

    float calculateAnnualReturn() const override;

    void displayAsset() const override;
};

#endif

