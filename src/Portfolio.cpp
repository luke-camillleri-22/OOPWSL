#include "Portfolio.h"
#include "Snapshot.h" 
#include "Stock.h"
#include "Bond.h"        
#include "MutualFund.h"  
#include "Broker.h"  
#include "Bank.h"
#include "MutualFundManager.h"    
#include <ctime>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>

Portfolio::~Portfolio() {
    for (auto asset : assets) delete asset;
    for (auto intermediary : intermediaries) delete intermediary;
}

// add asset to portfolio
void Portfolio::addAsset(FinancialAsset* asset) {
    assets.push_back(asset);
}

// get asset 
FinancialAsset* Portfolio::getAsset(const std::string& name) const {
    for (auto asset : assets) {
        if (asset->getName() == name) {
            return asset;
        }
    }
    return nullptr;
}

// update asset 
bool Portfolio::updateAsset(const std::string& name, float newValue) {
    FinancialAsset* asset = getAsset(name);
    if (asset) {
        asset->setValue(newValue);
        return true;
    }
    return false;
}

// sell an asset 
bool Portfolio::sellAsset(const std::string& name) {
    for (auto it = assets.begin(); it != assets.end(); ++it) {
        if ((*it)->getName() == name) {
            delete *it;  // freeing asset memory
            assets.erase(it);  // removing from the vector
            return true;
        }
    }
    return false;
}

// add intermediary to the portfolio
void Portfolio::addIntermediary(FinancialIntermediary* intermediary) {
    intermediaries.push_back(intermediary);
}

// get intermediary 
FinancialIntermediary* Portfolio::getIntermediary(const std::string& name) const {
    for (auto intermediary : intermediaries) {
        if (intermediary->getIntermediaryName() == name) {
            return intermediary;
        }
    }
    return nullptr;
}

// update intermediary 
bool Portfolio::updateIntermediary(const std::string& name, const std::string& newIntermediaryName) {
    FinancialIntermediary* intermediary = getIntermediary(name);
    if (intermediary) {
        intermediary->setIntermediaryName(newIntermediaryName);
        return true;
    }
    return false;
}

// remove intermediary 
bool Portfolio::removeIntermediary(const std::string& name) {
    for (auto it = intermediaries.begin(); it != intermediaries.end(); ++it) {
        if ((*it)->getIntermediaryName() == name) {
            delete *it; 
            intermediaries.erase(it);  
            return true;
        }
    }
    return false;  
}

// take snapshot of all current assets
void Portfolio::takeSnapshot() {
    std::time_t currentTime = std::time(nullptr);
    for (const auto& asset : assets) {
        Snapshot snapshot(asset->getName(), asset->getValue(), currentTime);
        assetSnapshots.push_back(snapshot);
    }
}

// display all stored snapshots
void Portfolio::displaySnapshots() const {
    for (const auto& snapshot : assetSnapshots) {
        snapshot.displaySnapshot();
    }
}

// snapshots of a specific asset between start and end dates
void Portfolio::displaySnapshotsByDate(const std::string& assetName, std::time_t startDate, std::time_t endDate) const {
    bool found = false;
    for (const auto& snapshot : assetSnapshots) {
        if (snapshot.getAssetName() == assetName &&
            snapshot.getSnapshotDate() >= startDate &&
            snapshot.getSnapshotDate() <= endDate) {
            
            snapshot.displaySnapshot();
            found = true;
        }
    }

    if (!found) {
        std::cout << "No snapshots found for asset " << assetName 
                  << " in the given date range." << std::endl;
    }
}

float Portfolio::calculateTotalAnnualReturn() const {
    float totalReturn = 0;
    for (const auto& asset : assets) {
        totalReturn += asset->calculateAnnualReturn();
    }
    return totalReturn;
}

void Portfolio::displayPortfolio() const {
    for (const auto& asset : assets) {
        asset->displayAsset();
    }

    for (const auto& intermediary : intermediaries) {
        intermediary->displayIntermediary();
    }
}

void Portfolio::saveState(const std::string& filename) const {
    std::ofstream outFile(filename);

    if (!outFile) {
        std::cerr << "Error opening file for writing: " << filename << std::endl;
        return;
    }

    // saving assets with specific attributes
    for (const auto& asset : assets) {
        if (auto stock = dynamic_cast<Stock*>(asset)) {
            outFile << "Stock," << stock->getName() << "," << stock->getValue() << ","
                    << stock->getShares() << "," << stock->getDividendYield() << "\n";
        } else if (auto bond = dynamic_cast<Bond*>(asset)) {
            outFile << "Bond," << bond->getName() << "," << bond->getValue() << ","
                    << bond->getInterestRate() << "," << bond->getMaturityDate() << "\n";
        } else if (auto mutualFund = dynamic_cast<MutualFund*>(asset)) {
            outFile << "MutualFund," << mutualFund->getName() << "," << mutualFund->getValue() << ","
                    << mutualFund->getExpenseRatio() << "\n";
        }
    }

    // delimiter to separate assets and snapshots
    outFile << "--- Snapshots ---\n";

    // snapshots
    for (const auto& snapshot : assetSnapshots) {
        outFile << snapshot.getAssetName() << "," << snapshot.getAssetValue() << ","
                << snapshot.getSnapshotDate() << "\n";
    }

    // intermediaries
    outFile << "--- Intermediaries ---\n";
    for (const auto& intermediary : intermediaries) {
        if (auto broker = dynamic_cast<Broker*>(intermediary)) {
            outFile << "Broker," << broker->getIntermediaryName() << "\n";
        } else if (auto bank = dynamic_cast<Bank*>(intermediary)) {
            outFile << "Bank," << bank->getIntermediaryName() << "\n";
        } else if (auto mutualFundManager = dynamic_cast<MutualFundManager*>(intermediary)) {
            outFile << "MutualFundManager," << mutualFundManager->getIntermediaryName() << "\n";
        }
    }

    outFile.close();
    std::cout << "Portfolio state saved to " << filename << "\n";
}


std::vector<std::string> split(const std::string& str, char delimiter) {
    std::vector<std::string> tokens;
    std::string token;
    std::istringstream tokenStream(str);
    while (std::getline(tokenStream, token, delimiter)) {
        tokens.push_back(token);
    }
    return tokens;
}

void Portfolio::loadState(const std::string& filename) {
    std::ifstream inFile(filename);

    if (!inFile) {
        std::cerr << "Error opening file: " << filename << std::endl;
        return;
    }

    std::string line;
    std::vector<std::string> tokens;

    // clear portfolio data
    assets.clear();
    assetSnapshots.clear();
    intermediaries.clear();

    // assets
    std::cout << "Loading assets..." << std::endl;
    while (std::getline(inFile, line)) {
        if (line == "--- Snapshots ---") {
            break;  // end assets
        }

        tokens = split(line, ',');
        if (tokens.size() > 1) {
            std::string assetType = tokens[0];
            std::string name = tokens[1];
            float value = std::stof(tokens[2]);

            if (assetType == "Stock") {
                int shares = std::stoi(tokens[3]);
                float dividendYield = std::stof(tokens[4]);
                assets.push_back(new Stock(name, value, "", shares, dividendYield));
                std::cout << "Loaded Stock: " << name << ", value: " << value << "\n";
            } else if (assetType == "Bond") {
                float interestRate = std::stof(tokens[3]);
                std::string maturityDate = tokens[4];
                assets.push_back(new Bond(name, value, interestRate, maturityDate));
                std::cout << "Loaded Bond: " << name << ", value: " << value << "\n";
            } else if (assetType == "MutualFund") {
                float expenseRatio = std::stof(tokens[3]);
                assets.push_back(new MutualFund(name, value, expenseRatio));
                std::cout << "Loaded MutualFund: " << name << ", value: " << value << "\n";
            }
        }
    }

    // snapshots
    std::cout << "Loading snapshots..." << std::endl;
    while (std::getline(inFile, line)) {
        if (line == "--- Intermediaries ---") {
            break;  // end snapshots
        }

        tokens = split(line, ',');
        if (tokens.size() == 3) {
            std::string name = tokens[0];
            float value = std::stof(tokens[1]);
            std::time_t snapshotDate = std::stol(tokens[2]);

            assetSnapshots.push_back(Snapshot(name, value, snapshotDate));
            std::cout << "Loaded Snapshot: " << name << ", value: " << value << "\n";
        }
    }

    // intermediaries
    std::cout << "Loading intermediaries..." << std::endl;
    while (std::getline(inFile, line)) {
        tokens = split(line, ',');
        if (tokens.size() == 2) {
            std::string intermediaryType = tokens[0];
            std::string name = tokens[1];

            if (intermediaryType == "Broker") {
                intermediaries.push_back(new Broker(name));
            } else if (intermediaryType == "Bank") {
                intermediaries.push_back(new Bank(name));
            } else if (intermediaryType == "MutualFundManager") {
                intermediaries.push_back(new MutualFundManager(name));
            }

            std::cout << "Loaded Intermediary: " << intermediaryType << " - " << name << "\n";
        }
    }

    inFile.close();
    std::cout << "Portfolio state loaded from " << filename << "\n";
}
