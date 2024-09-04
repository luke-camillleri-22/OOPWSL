#include "Bond.h"

Bond::Bond(const std::string& name, float value, float interestRate, const std::string& maturityDate)
    : FinancialAsset(name, value), interestRate(interestRate), maturityDate(maturityDate) {}

float Bond::calculateAnnualReturn() const {
    return value * interestRate;
}

void Bond::displayAsset() const {
    std::cout << "Bond: " << name << ", Value: $" << value 
              << ", Interest Rate: " << interestRate 
              << ", Maturity Date: " << maturityDate << std::endl;
}
