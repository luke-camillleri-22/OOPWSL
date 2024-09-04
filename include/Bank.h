#ifndef BANK_H
#define BANK_H

#include "FinancialIntermediary.h"

class Bank : public FinancialIntermediary {
public:
    Bank(const std::string& intermediaryName);

    void displayIntermediary() const override;
};

#endif
