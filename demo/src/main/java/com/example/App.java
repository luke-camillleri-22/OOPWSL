package com.example;

import java.io.IOException;
import java.time.LocalDateTime;

import com.example.Bond;
import com.example.MutualFund;
import com.example.Portfolio;

public class App {
    public static void main(String[] args) throws IOException {
        Portfolio portfolio = new Portfolio();
        String saveLoadPath = "portfolio_state.txt";


        boolean end = false;

        while(!end){
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
            System.out.println("13. Exit");

            int choice = Integer.parseInt(System.console().readLine());
            String assetName;

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

                        Stock stock = new Stock(assetName, assetValue, tickerSymbol, shares, assetDividendYield);
                        portfolio.addAsset(stock);
                    }
                    else if(assetType.equals("Bond")){
                        System.out.println("Enter Bond Interest Rate: ");
                        float assetInterestRate = Float.parseFloat(System.console().readLine());

                        System.out.println("Enter Bond Maturity Date: ");
                        String assetMaturityDate = System.console().readLine();
                        Bond bond = new Bond(assetName, assetValue, assetInterestRate, assetMaturityDate);
                        portfolio.addAsset(bond);
                    }
                    else if(assetType.equals("MutualFund")){
                        System.out.println("Enter Mutual Fund Expense Ratio: ");
                        float assetExpenseRatio = Float.parseFloat(System.console().readLine());

                        MutualFund mutualFund = new MutualFund(assetName, assetValue, assetExpenseRatio);
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
                        Broker broker = new Broker(intermediaryName);
                        portfolio.addIntermediary(broker);
                    }
                    else if(intermediaryType.equals("Bank")){
                        Bank bank = new Bank(intermediaryName);
                        portfolio.addIntermediary(bank);
                    }
                    else if(intermediaryType.equals("MutualFundManager")){
                        MutualFundManager mutualFundManager = new MutualFundManager(intermediaryName);
                        portfolio.addIntermediary(mutualFundManager);
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
                    portfolio.saveState(saveLoadPath);
                    break;
                case 10:
                    portfolio.loadState(saveLoadPath);
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
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                
        }
            
    }
}
