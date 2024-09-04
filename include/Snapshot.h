#ifndef SNAPSHOT_H
#define SNAPSHOT_H

#include <string>
#include <iostream>
#include <ctime>

class Snapshot {
private:
    std::string assetName;
    float assetValue;
    std::time_t snapshotDate;

public:
    Snapshot(const std::string& name, float value, std::time_t date)
        : assetName(name), assetValue(value), snapshotDate(date) {}


    std::string getAssetName() const { return assetName; }
    float getAssetValue() const { return assetValue; }
    std::time_t getSnapshotDate() const { return snapshotDate; }

    // display snapshot
    void displaySnapshot() const {
        std::cout << "Snapshot - Asset: " << assetName
                  << ", Value: $" << assetValue
                  << ", Date: " << std::asctime(std::localtime(&snapshotDate));
    }
};

#endif
