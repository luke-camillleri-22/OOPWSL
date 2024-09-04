#ifndef BROKER_H
#define BROKER_H

#include "FinancialIntermediary.h"

class Broker : public FinancialIntermediary {
public:
    Broker(const std::string& intermediaryName);

    void displayIntermediary() const override;
};

#endif
