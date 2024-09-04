#include "Broker.h"

Broker::Broker(const std::string& intermediaryName)
    : FinancialIntermediary(intermediaryName) {}

void Broker::displayIntermediary() const {
    std::cout << "Broker: " << intermediaryName << std::endl;
}
