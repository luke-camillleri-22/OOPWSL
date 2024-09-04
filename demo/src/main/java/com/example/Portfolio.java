package com.example;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import com.example.FinancialAsset;
import com.example.Stock;

public class Portfolio {
    private List<FinancialAsset> assets;
    private List<FinancialIntermediary> intermediaries;
    private List<Snapshot> snapshots = new ArrayList<>();

    public Portfolio() {
        assets = new ArrayList<>();
        intermediaries = new ArrayList<>();
    }

    // CREATE: Add a financial asset
    public void addAsset(FinancialAsset asset) {
        assets.add(asset);
    }

    // READ: Get an asset by name
    public FinancialAsset getAsset(String name) {
        for (FinancialAsset asset : assets) {
            if (asset.getName().equals(name)) {
                return asset;
            }
        }
        return null;  // Asset not found
    }

    // UPDATE: Update asset value by name
    public boolean updateAsset(String name, float newValue) {
        FinancialAsset asset = getAsset(name);
        if (asset != null) {
            asset.setValue(newValue);
            return true;
        }
        return false;  // Asset not found
    }

    // DELETE: Remove an asset by name
    public boolean removeAsset(String name) {
        FinancialAsset asset = getAsset(name);
        if (asset != null) {
            assets.remove(asset);
            return true;
        }
        return false;  // Asset not found
    }

    // CREATE: Add an intermediary
    public void addIntermediary(FinancialIntermediary intermediary) {
        intermediaries.add(intermediary);
    }

    // READ: Get an intermediary by name
    public FinancialIntermediary getIntermediary(String name) {
        for (FinancialIntermediary intermediary : intermediaries) {
            if (intermediary.getIntermediaryName().equals(name)) {
                return intermediary;
            }
        }
        return null;  // Intermediary not found
    }

    // UPDATE: Update intermediary name by old name
    public boolean updateIntermediary(String oldName, String newName) {
        FinancialIntermediary intermediary = getIntermediary(oldName);
        if (intermediary != null) {
            intermediary.intermediaryName = newName;
            return true;
        }
        return false;  // Intermediary not found
    }

    // DELETE: Remove an intermediary by name
    public boolean removeIntermediary(String name) {
        FinancialIntermediary intermediary = getIntermediary(name);
        if (intermediary != null) {
            intermediaries.remove(intermediary);
            return true;
        }
        return false;  // Intermediary not found
    }

    public void displayPortfolio() {
        System.out.println("Portfolio Assets:");
        for (FinancialAsset asset : assets) {
            asset.displayAsset();
        }

        System.out.println("\nIntermediaries:");
        for (FinancialIntermediary intermediary : intermediaries) {
            intermediary.displayIntermediary();
        }
    }

    public float calculateTotalAnnualReturn() {
        float totalReturn = 0;
        for (FinancialAsset asset : assets) {
            totalReturn += asset.calculateAnnualReturn();
        }
        return totalReturn;
    }

    public void takeSnapshot() {
        for (FinancialAsset asset : assets) {
            snapshots.add(new Snapshot(asset.getName(), asset.getValue(), LocalDateTime.now()));
        }
        System.out.println("Snapshot taken.");
    }

    public void displaySnapshots() {
        for (Snapshot snapshot : snapshots) {
            snapshot.displaySnapshot();
        }
    }

    public void displaySnapshotsForAsset(String assetName, LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("Snapshots for asset: " + assetName);
        for (Snapshot snapshot : snapshots) {
            if (snapshot.getAssetName().equals(assetName)
                && !snapshot.getTimestamp().isBefore(startDate)
                && !snapshot.getTimestamp().isAfter(endDate)) {
                snapshot.displaySnapshot();
            }
        }
    }

    public void saveState(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            // Save financial assets
            for (FinancialAsset asset : assets) {
                writer.write(asset.getClass().getSimpleName() + "," + asset.getName() + "," + asset.getValue() + "\n");
            }

            // Save snapshots
            for (Snapshot snapshot : snapshots) {
                writer.write("Snapshot," + snapshot.getAssetName() + "," + snapshot.getAssetValue() + "," + snapshot.getTimestamp() + "\n");
            }

            // Save intermediaries
            for (FinancialIntermediary intermediary : intermediaries) {
                writer.write(intermediary.getClass().getSimpleName() + "," + intermediary.getIntermediaryName() + "\n");
            }

            System.out.println("Portfolio saved to " + filename);
        }
    }

    public void loadState(String filename) throws IOException {

        assets.clear();
        intermediaries.clear();
        snapshots.clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                switch (data[0]) {
                    case "Stock":
                        assets.add(new Stock(data[1], Float.parseFloat(data[2]), "Ticker", 50, 0.05f));
                        break;
                    case "Bond":
                        assets.add(new Bond(data[1], Float.parseFloat(data[2]), 0.03f, "2030-01-01"));
                        break;
                    case "MutualFund":
                        assets.add(new MutualFund(data[1], Float.parseFloat(data[2]), 0.02f));
                        break;
                    case "Snapshot":
                        snapshots.add(new Snapshot(data[1], Float.parseFloat(data[2]), LocalDateTime.parse(data[3])));
                        break;
                    case "Broker":
                        intermediaries.add(new Broker(data[1]));
                        break;
                    case "Bank":
                        intermediaries.add(new Bank(data[1]));
                        break;
                    case "MutualFundManager":
                        intermediaries.add(new MutualFundManager(data[1]));
                        break;
                }
            }
            System.out.println("Portfolio loaded from " + filename);
        }
    }
}
