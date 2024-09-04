#include "MutualFundManager.h"

MutualFundManager::MutualFundManager(const std::string& intermediaryName)
    : FinancialIntermediary(intermediaryName) {}

void MutualFundManager::displayIntermediary() const {
    std::cout << "Mutual Fund Manager: " << intermediaryName << std::endl;
}
