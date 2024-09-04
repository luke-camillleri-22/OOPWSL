#include <iostream>
#include <ctime>
#include <thread>
#include <chrono>
#include "Portfolio.h"
#include "Stock.h"
#include "Bond.h"
#include "MutualFund.h"
#include "Broker.h"
#include "Bank.h"
#include "MutualFundManager.h"

int main() {
    Portfolio portfolio;

    // creating assets
    Stock* stock = new Stock("Tech Corp", 1000, "TC", 50, 0.05);
    Bond* bond = new Bond("Gov Bond", 2000, 0.03, "2030-01-01");
    MutualFund* mutualFund = new MutualFund("Growth Fund", 3000, 0.02);

    // adding assets to the portfolio
    portfolio.addAsset(stock);
    portfolio.addAsset(bond);
    portfolio.addAsset(mutualFund);

    // creating intermediaries
    Broker* broker = new Broker("Broker Guy Steve");
    Bank* bank = new Bank("National Bank");
    MutualFundManager* manager = new MutualFundManager("Jordan Carter");

    // adding intermediaries to the portfolio
    portfolio.addIntermediary(broker);
    portfolio.addIntermediary(bank);
    portfolio.addIntermediary(manager);

    // taking a snapshot of the current asset states
    portfolio.takeSnapshot();

    // displaying portfolio assets and intermediaries
    std::cout << "Original Portfolio:\n";
    portfolio.displayPortfolio();

    // updating an intermediary's name
    portfolio.updateIntermediary("Broker Guy Steve", "John Smith");
    std::cout << "\nUpdated Portfolio (Broker name updated):\n";
    portfolio.displayPortfolio();

    // removing an intermediary
    portfolio.removeIntermediary("National Bank");
    std::cout << "\nPortfolio after removing National Bank:\n";
    portfolio.displayPortfolio();

    portfolio.takeSnapshot();

    std::cout << "\nSnapshots of assets:\n";
    portfolio.displaySnapshots();

    std::cout << "Simulating changes in asset values...\n";
    std::this_thread::sleep_for(std::chrono::seconds(2));
    portfolio.updateAsset("Tech Corp", 1200);
    portfolio.takeSnapshot();

    std::cout << "Simulating more changes in asset values...\n";
    std::this_thread::sleep_for(std::chrono::seconds(2));
    portfolio.updateAsset("Tech Corp", 1500);
    portfolio.takeSnapshot();

    //start and end date range for filtering
    std::time_t startDate = std::time(nullptr) - 10;  // Start 10 seconds ago
    std::time_t endDate = std::time(nullptr);         // End now

    std::cout << "\nSnapshots of assets:\n";
    portfolio.displaySnapshots();

    std::string filter = "Tech Corp";

    // display filtered snapshots
    std::cout << "\nFiltered Snapshots for: " << filter << "\n";
    portfolio.displaySnapshotsByDate(filter, startDate, endDate);

    // save portfolio state
    portfolio.saveState("portfolio_state.txt");

    Portfolio portfolio2;
    portfolio2.loadState("portfolio_state.txt");

    std::cout << "\nRestored Portfolio:\n";
    portfolio2.displayPortfolio();
    portfolio2.displaySnapshots();


    return 0;
}
