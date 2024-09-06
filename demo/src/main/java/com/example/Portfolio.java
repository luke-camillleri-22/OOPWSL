package com.example;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;

import com.example.AssetBuilder;
import com.example.FinancialAsset;
import com.example.IntermediaryBuilder;
import com.example.SnapshotBuilder;
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

    public List<FinancialAsset> getAssets() {
        return assets;
    }

    // UPDATE: Update asset value by name
    public boolean updateAsset(String name, float newValue) {
        FinancialAsset asset = getAsset(name);
        if (asset != null) {
            switch(asset.getClass().getSimpleName()){
                case "Stock":
                    FinancialAsset stock = new AssetBuilder()
                            .setName(asset.getName())
                            .setValue(newValue)
                            .setTickerSymbol(((Stock) asset).getTickerSymbol())
                            .setShares(((Stock) asset).getShares())
                            .setDividendYield(((Stock) asset).getDividendYield())
                            .setType("Stock")
                            .build();
                    assets.remove(asset);
                    assets.add(stock);
                    return true;
                case "Bond":
                    FinancialAsset bond = new AssetBuilder()
                            .setName(asset.getName())
                            .setValue(newValue)
                            .setInterestRate(((Bond) asset).getInterestRate())
                            .setMaturityDate(((Bond) asset).getMaturityDate())
                            .setType("Bond")
                            .build();
                    assets.remove(asset);
                    assets.add(bond);
                    return true;
                case "MutualFund":
                    FinancialAsset mutualFund = new AssetBuilder()
                            .setName(asset.getName())
                            .setValue(newValue)
                            .setExpenseRatio(((MutualFund) asset).getExpenseRatio())
                            .setType("MutualFund")
                            .build();
                    assets.remove(asset);
                    assets.add(mutualFund);
                    return true;
            }
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

    public List<FinancialIntermediary> getIntermediaries() {
        return intermediaries;
    }

    // UPDATE: Update intermediary name by old name
    public boolean updateIntermediary(String oldName, String newName) {
        FinancialIntermediary intermediary = getIntermediary(oldName);
        if (intermediary != null) {
            switch(intermediary.getClass().getSimpleName()){
                case "Broker":
                    FinancialIntermediary broker = new IntermediaryBuilder()
                            .setName(newName)
                            .setType("Broker")
                            .build();
                    intermediaries.remove(intermediary);
                    intermediaries.add(broker);
                    return true;
                case "Bank":
                    FinancialIntermediary bank = new IntermediaryBuilder()
                            .setName(newName)
                            .setType("Bank")
                            .build();
                    intermediaries.remove(intermediary);
                    intermediaries.add(bank);
                    return true;
                case "MutualFundManager":
                    FinancialIntermediary mutualFundManager = new IntermediaryBuilder()
                            .setName(newName)
                            .setType("MutualFundManager")
                            .build();
                    intermediaries.remove(intermediary);
                    intermediaries.add(mutualFundManager);
                    return true;
            }
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
            LocalDateTime timestamp = LocalDateTime.now();
            String timeString = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Snapshot snapshot = new SnapshotBuilder()
                    .setAssetName(asset.getName())
                    .setAssetValue(asset.getValue())
                    .setTimestamp(timeString)
                    .build();
            snapshots.add(snapshot);
        }
        System.out.println("Snapshot taken.");
    }

    public void addSnapshot(Snapshot snapshot) {
        snapshots.add(snapshot);
    }

    public void displaySnapshots() {
        for (Snapshot snapshot : snapshots) {
            System.out.println("Snapshot - Asset: " + snapshot.getAssetName() +
                    ", Value: $" + snapshot.getAssetValue() +
                    ", Time: " + snapshot.getTimestamp());
        }
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void displaySnapshotsForAsset(String assetName, LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("Snapshots for asset: " + assetName);
        for (Snapshot snapshot : snapshots) {
            if (snapshot.getAssetName().equals(assetName)) {
                LocalDateTime timestamp = LocalDateTime.parse(snapshot.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if (timestamp.isAfter(startDate) && timestamp.isBefore(endDate)) {
                    snapshot.displaySnapshot();
                }
            }
        }
    }

    public void saveState(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            // Save financial assets
            for (FinancialAsset asset : assets) {
                //writer.write(asset.getClass().getSimpleName() + "," + asset.getName() + "," + asset.getValue() + "\n");
                switch(asset.getClass().getSimpleName()){
                    case "Stock":
                        writer.write("Stock," + asset.getName() + "," + asset.getValue() + "," + ((Stock) asset).getTickerSymbol() + "," + ((Stock) asset).getShares() + "," + ((Stock) asset).getDividendYield() + "\n");
                        break;
                    case "Bond":
                        writer.write("Bond," + asset.getName() + "," + asset.getValue() + "," + ((Bond) asset).getInterestRate() + "," + ((Bond) asset).getMaturityDate() + "\n");
                        break;
                    case "MutualFund":
                        writer.write("MutualFund," + asset.getName() + "," + asset.getValue() + "," + ((MutualFund) asset).getExpenseRatio() + "\n");
                        break;
                }
            }

            // Save snapshots
            for (Snapshot snapshot : snapshots) {
                writer.write("Snapshot," + snapshot.getAssetName() + "," + snapshot.getAssetValue() + "," + snapshot.getTimestamp() + "\n");
            }

            // Save intermediaries
            for (FinancialIntermediary intermediary : intermediaries) {
                switch (intermediary.getClass().getSimpleName()) {
                    case "Broker":
                        writer.write("Broker," + intermediary.getIntermediaryName() + "\n");
                        break;
                    case "Bank":
                        writer.write("Bank," + intermediary.getIntermediaryName() + "\n");
                        break;
                    case "MutualFundManager":
                        writer.write("MutualFundManager," + intermediary.getIntermediaryName() + "\n");
                        break;
                }
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
                        //assets.add(new Stock(data[1], Float.parseFloat(data[2]), data[3], Integer.parseInt(data[4]), Float.parseFloat(data[5])));
                        assets.add(new AssetBuilder()
                                .setName(data[1])
                                .setValue(Float.parseFloat(data[2]))
                                .setTickerSymbol(data[3])
                                .setShares(Integer.parseInt(data[4]))
                                .setDividendYield(Float.parseFloat(data[5]))
                                .setType("Stock")
                                .build());
                        break;
                    case "Bond":
                        //assets.add(new Bond(data[1], Float.parseFloat(data[2]), 0.03f, "2030-01-01"));
                        assets.add(new AssetBuilder()
                                .setName(data[1])
                                .setValue(Float.parseFloat(data[2]))
                                .setInterestRate(Float.parseFloat(data[3]))
                                .setMaturityDate(data[4])
                                .setType("Bond")
                                .build());
                        break;
                    case "MutualFund":
                        //assets.add(new MutualFund(data[1], Float.parseFloat(data[2]), 0.02f));
                        assets.add(new AssetBuilder()
                                .setName(data[1])
                                .setValue(Float.parseFloat(data[2]))
                                .setExpenseRatio(Float.parseFloat(data[3]))
                                .setType("MutualFund")
                                .build());
                        break;
                    case "Snapshot":
                        //snapshots.add(new Snapshot(data[1], Float.parseFloat(data[2]), LocalDateTime.parse(data[3])));
                        snapshots.add(new SnapshotBuilder()
                                .setAssetName(data[1])
                                .setAssetValue(Float.parseFloat(data[2]))
                                .setTimestamp(data[3])
                                .build());
                        break;
                    case "Broker":
                        intermediaries.add(new IntermediaryBuilder()
                                .setName(data[1])
                                .setType("Broker")
                                .build());
                        break;
                    case "Bank":
                        intermediaries.add(new IntermediaryBuilder()
                                .setName(data[1])
                                .setType("Bank")
                                .build());
                        break;
                    case "MutualFundManager":
                        intermediaries.add(new IntermediaryBuilder()
                                .setName(data[1])
                                .setType("MutualFundManager")
                                .build());
                        break;
                }
            }
            System.out.println("Portfolio loaded from " + filename);
        }
    }
}
