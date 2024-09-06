#include <iostream>
#include <fstream>
#include "/mnt/c/Users/Owner/OOP/asset_viewer_cpp/src/demo/src/main/proto/financial_portfolio.pb.h"

void printAsset(const com::example::Asset_Proto& asset) {
    std::string type = asset.type();

    if (type == "Stock") {
        std::cout << "Stock: " << asset.stock().baseasset().name() << "\tValue: " << asset.stock().baseasset().value() << "$\tTicker: " << asset.stock().ticker() << "\tShares: " << asset.stock().shares() << "\tDividend Yield: " << asset.stock().dividendyield() << std::endl;
    } else if (type == "Bond") {
        std::cout << "Bond: " << asset.bond().baseasset().name() << "\tValue: " << asset.bond().baseasset().value() << "\tInterest Rate: " << asset.bond().interestrate() << "\tMaturity Date: " << asset.bond().maturitydate() << std::endl;
    } else if (type == "MutualFund") {
        std::cout << "Mutual Fund: " << asset.mutualfund().baseasset().name() << "\tValue: " << asset.mutualfund().baseasset().value() << "\tExpense Ratio: " << asset.mutualfund().expenseratio() << std::endl;
    } else {
        std::cerr << "Unknown asset type: " << asset.type() << std::endl;
    }
}

void printIntermediary(const com::example::Intermediary_Proto& intermediary) {
    std::string type = intermediary.type();

    if (type == "Broker") {
        std::cout << "Broker: " << intermediary.broker().intermediary().name() << std::endl;
    } else if (type == "Bank") {
        std::cout << "Bank: " << intermediary.bank().intermediary().name()<< std::endl;
    }else if(type =="MutualFundManager"){
        std::cout << "Mutual Fund Manager: " << intermediary.mutualfundmanager().intermediary().name()<< std::endl;
    } else {
        std::cerr << "Unknown intermediary type: " << intermediary.type() << std::endl;
    }
}

void printSnapshot(const com::example::Snapshot_Proto& snapshot) {
    std::cout << "Snapshot: " << snapshot.timestamp() << "\tName: " << snapshot.assetname() << "\t\tValue: " << snapshot.assetvalue() << std::endl;
}

int main(int argc, char** argv) {
    if (argc != 2) {
        std::cerr << "Usage: " << argv[0] << " PORTFOLIO_FILE" << std::endl;
        return 1;
    }

    std::cout << "Reading portfolio from: " << argv[1] << std::endl;

    const char* portfolio_file = argv[1];
    std::ifstream input(portfolio_file, std::ios::binary);
    if (!input) {
        std::cerr << "Failed to open file: " << portfolio_file << std::endl;
        return 1;
    }

    com::example::Portfolio_Proto portfolio;
    if (!portfolio.ParseFromIstream(&input)) {
        std::cerr << "Failed to parse portfolio." << std::endl;
        return 1;
    }

    std::cout << "Assets:" << std::endl;
    for (const auto& asset : portfolio.assets()) {
        printAsset(asset);
    }

    std::cout << "\n\nIntermediaries:" << std::endl;
    for (const auto& intermediary : portfolio.intermediaries()) {
        printIntermediary(intermediary);
    }

    std::cout << "\n\nSnapshots:" << std::endl;
    for (const auto& snapshot : portfolio.snapshots()) {
        printSnapshot(snapshot);
        
    }

    

    return 0;
}
