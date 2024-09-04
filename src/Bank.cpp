#include "Bank.h"

Bank::Bank(const std::string& intermediaryName)
    : FinancialIntermediary(intermediaryName) {}

void Bank::displayIntermediary() const {
    std::cout << "Bank: " << intermediaryName << std::endl;
}
