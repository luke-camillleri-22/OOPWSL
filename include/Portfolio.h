#ifndef PORTFOLIO_H
#define PORTFOLIO_H

#include <vector>
#include "FinancialAsset.h"
#include "FinancialIntermediary.h"
#include "Snapshot.h"

class Portfolio {
private:
    std::vector<FinancialAsset*> assets;
    std::vector<FinancialIntermediary*> intermediaries;
    std::vector<Snapshot> assetSnapshots;

public:
    ~Portfolio();

    // CRUD methods for assets
    void addAsset(FinancialAsset* asset);
    FinancialAsset* getAsset(const std::string& name) const;
    bool updateAsset(const std::string& name, float newValue);
    bool sellAsset(const std::string& name);

    // CRUD methods for intermediaries
    void addIntermediary(FinancialIntermediary* intermediary);
    FinancialIntermediary* getIntermediary(const std::string& name) const;
    bool updateIntermediary(const std::string& name, const std::string& newIntermediaryName);
    bool removeIntermediary(const std::string& name);

    // methods for snapshots
    void takeSnapshot();
    void displaySnapshots() const;

    // filtering snapshots by asset and date range
    void displaySnapshotsByDate(const std::string& assetName, std::time_t startDate, std::time_t endDate) const;

    float calculateTotalAnnualReturn() const;
    void displayPortfolio() const;

    // save and restore the portfolio state
    void saveState(const std::string& filename) const;
    void loadState(const std::string& filename);
};

#endif
