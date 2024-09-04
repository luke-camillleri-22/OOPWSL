#include "MutualFund.h"

MutualFund::MutualFund(const std::string& name, float value, float expenseRatio)
    : FinancialAsset(name, value), expenseRatio(expenseRatio) {}

float MutualFund::calculateAnnualReturn() const {
    return value * (1 - expenseRatio);
}

void MutualFund::displayAsset() const {
    std::cout << "Mutual Fund: " << name << ", Value: $" << value
              << ", Expense Ratio: " << expenseRatio << std::endl;
}
