package com.example;

import java.io.IOException;
import java.time.LocalDateTime;

import com.example.Bond;
import com.example.MutualFund;
import com.example.Portfolio;
import com.example.AssetBuilder;
import com.example.IntermediaryBuilder;
import com.example.FinancialPortfolio;
import com.example.Facade;

public class App {
    public static void main(String[] args) throws IOException {
        Portfolio portfolio = new Portfolio();
        String saveLoadPath = "portfolio_state.txt";
        Facade facade = new Facade("portfolio_state.bin");
        FinancialPortfolio.Portfolio_Proto.Builder portfolioBuilder = FinancialPortfolio.Portfolio_Proto.newBuilder();


        boolean end = false;

        while(!end){
            System.out.println("---------------------Portfolio Management System---------------------");
            System.out.println("Enter Choice: ");
            System.out.println("1. Add Asset");
            System.out.println("2. Get Asset");
            System.out.println("3. Update Asset");
            System.out.println("4. Remove Asset");
            System.out.println("5. Add Intermediary");
            System.out.println("6. Take Snapshot");
            System.out.println("7. Display Portfolio");
            System.out.println("8. Display Snapshots");
            System.out.println("9. Save State");
            System.out.println("10. Load State");
            System.out.println("11. Display Snapshots for Asset");
            System.out.println("12. Clear Portfolio");
            System.out.println("13. Calculate Annual Return");
            System.out.println("14. Exit");

            System.out.print("Choice: ");
            int choice = Integer.parseInt(System.console().readLine());
            String assetName;
            AssetBuilder assetBuilder = new AssetBuilder();
            IntermediaryBuilder intermediaryBuilder = new IntermediaryBuilder();

            switch(choice){
                case 1:
                    System.out.println("Enter Asset Type (Stock, Bond, MutualFund): ");
                    String assetType = System.console().readLine();

                    System.out.println("Enter Asset Name: ");
                    assetName = System.console().readLine();

                    System.out.println("Enter Asset Value: ");
                    float assetValue = Float.parseFloat(System.console().readLine());

                    if(assetType.equals("Stock")){
                        System.out.println("Enter Ticker Symbol: ");
                        String tickerSymbol = System.console().readLine();
                        
                        System.out.println("Enter the number of shares: ");
                        int shares = Integer.parseInt(System.console().readLine());

                        System.out.println("Enter Stock Dividend Yield: ");
                        float assetDividendYield = Float.parseFloat(System.console().readLine());
                        
                        FinancialAsset stock = assetBuilder
                            .setName(assetName)
                            .setValue(assetValue)
                            .setTickerSymbol(tickerSymbol)
                            .setShares(shares)
                            .setDividendYield(assetDividendYield)
                            .setType("Stock")
                            .build();

                        portfolio.addAsset(stock);
                    }
                    else if(assetType.equals("Bond")){
                        System.out.println("Enter Bond Interest Rate: ");
                        float assetInterestRate = Float.parseFloat(System.console().readLine());

                        System.out.println("Enter Bond Maturity Date: ");
                        String assetMaturityDate = System.console().readLine();
                        FinancialAsset bond = assetBuilder
                            .setName(assetName)
                            .setValue(assetValue)
                            .setInterestRate(assetInterestRate)
                            .setMaturityDate(assetMaturityDate)
                            .setType("Bond")
                            .build();
                        portfolio.addAsset(bond);
                    }
                    else if(assetType.equals("MutualFund")){
                        System.out.println("Enter Mutual Fund Expense Ratio: ");
                        float assetExpenseRatio = Float.parseFloat(System.console().readLine());

                        FinancialAsset mutualFund = assetBuilder
                            .setName(assetName)
                            .setValue(assetValue)
                            .setExpenseRatio(assetExpenseRatio)
                            .setType("MutualFund")
                            .build();
                        portfolio.addAsset(mutualFund);
                    }
                    break;
                case 2:
                    System.out.println("Enter Asset Name: ");
                    assetName = System.console().readLine();
                    FinancialAsset asset = portfolio.getAsset(assetName);
                    if(asset != null){
                        asset.displayAsset();
                    }
                    else{
                        System.out.println("Asset not found");
                    }
                    break;
                case 3:
                    System.out.println("Enter Asset Name: ");
                    assetName = System.console().readLine();
                    System.out.println("Enter New Asset Value: ");
                    float newValue = Float.parseFloat(System.console().readLine());
                    portfolio.updateAsset(assetName, newValue);
                    break;
                case 4:
                    System.out.println("Enter Asset Name: ");
                    assetName = System.console().readLine();
                    portfolio.removeAsset(assetName);
                    break;
                case 5:
                    System.out.println("Enter Intermediary Type (Broker, Bank, MutualFundManager): ");
                    String intermediaryType = System.console().readLine();
                    System.out.println("Enter Intermediary Name: ");
                    String intermediaryName = System.console().readLine();

                    if(intermediaryType.equals("Broker")){
                        portfolio.addIntermediary(new IntermediaryBuilder()
                            .setName(intermediaryName)
                            .setType("Broker")
                            .build());
                    }
                    else if(intermediaryType.equals("Bank")){
                        portfolio.addIntermediary(new IntermediaryBuilder()
                            .setName(intermediaryName)
                            .setType("Bank")
                            .build());
                    }
                    else if(intermediaryType.equals("MutualFundManager")){
                        portfolio.addIntermediary(new IntermediaryBuilder()
                            .setName(intermediaryName)
                            .setType("MutualFundManager")
                            .build());
                    }else{
                        System.out.println("Invalid Intermediary Type");
                    }
                    break;
                case 6:
                    portfolio.takeSnapshot();
                    break;
                case 7:
                    portfolio.displayPortfolio();
                    break;
                case 8:
                    portfolio.displaySnapshots();
                    break;
                case 9:
                    try{

                        System.out.println(portfolio.getAssets());
                        for (FinancialAsset saveAsset : portfolio.getAssets()) {
                            FinancialPortfolio.Asset_Proto assetProto = AssetBuilder.toProto(saveAsset);
                            portfolioBuilder.addAssets(assetProto);
                        }

                        for (FinancialIntermediary saveIntermediary : portfolio.getIntermediaries()) {
                            FinancialPortfolio.Intermediary_Proto intermediaryProto = IntermediaryBuilder.toProto(saveIntermediary);
                            portfolioBuilder.addIntermediaries(intermediaryProto);
                        }

                        for (Snapshot saveSnapshot : portfolio.getSnapshots()) {
                            FinancialPortfolio.Snapshot_Proto snapshotProto = SnapshotBuilder.toProto(saveSnapshot);
                            portfolioBuilder.addSnapshots(snapshotProto);
                        }

                        FinancialPortfolio.Portfolio_Proto portfolioProto = portfolioBuilder.build();
                        facade.save(portfolioProto);

                        System.out.println("Portfolio saved successfully.");
                    }catch (IOException e){
                        System.out.println("Error saving portfolio: " + e.getMessage());
                    }
                    break;
                case 10:
                    try {
                        FinancialPortfolio.Portfolio_Proto portfolioProto = facade.load();

                        portfolio = new Portfolio();

                        for (FinancialPortfolio.Asset_Proto assetProto : portfolioProto.getAssetsList()) {
                            FinancialAsset loadAsset = AssetBuilder.fromProto(assetProto);
                            portfolio.addAsset(loadAsset);
                        }

                        for (FinancialPortfolio.Intermediary_Proto intermediaryProto : portfolioProto.getIntermediariesList()) {
                            FinancialIntermediary loadIntermediary = IntermediaryBuilder.fromProto(intermediaryProto);
                            portfolio.addIntermediary(loadIntermediary);
                        }

                        for (FinancialPortfolio.Snapshot_Proto snapshotProto : portfolioProto.getSnapshotsList()) {
                            Snapshot loadSnapshot = SnapshotBuilder.fromProto(snapshotProto);
                            portfolio.addSnapshot(loadSnapshot);
}
                        System.out.println("Portfolio loaded successfully.");
                    } catch (IOException e) {
                        System.out.println("Error loading portfolio: " + e.getMessage());
                    }
                    break;
                case 11:
                    System.out.println("Enter Asset Name: ");
                    assetName = System.console().readLine();
                    
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime startTime = now.minusHours(1);
                    LocalDateTime endTime = now;
                    portfolio.displaySnapshotsForAsset(assetName, startTime, endTime);
                    break;
                case 12:
                    //clear portfolio
                    portfolio = new Portfolio();
                    break;
                case 13:
                    System.out.println("Calculating Annual Return...");
                    System.out.println("Annual Return: " + portfolio.calculateTotalAnnualReturn() + "%");
                    System.out.println();
                    break;
                case 14:
                    end = true;
                    break;
                default:
                    System.out.println("Invalid Choice");
                }
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
        }
            
    }
}
