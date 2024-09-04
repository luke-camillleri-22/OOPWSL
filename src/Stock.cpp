#include "Stock.h"

Stock::Stock(const std::string& name, float value, const std::string& tickerSymbol, int shares, float dividendYield)
        : FinancialAsset(name, value), tickerSymbol(tickerSymbol), shares(shares), dividendYield(dividendYield) {}

float Stock::calculateAnnualReturn() const {
    return value * dividendYield;
}

void Stock::displayAsset() const {
    std::cout << "Stock: " << name << ", Ticker: " << tickerSymbol
              << ", Value: $" << value << ", Shares: " << shares
              << ", Dividend Yield: " << dividendYield << std::endl;
}
