package com.example;

import com.example.FinancialPortfolio;


public class AssetBuilder {
    private String name;
    private float value;
    private String tickerSymbol;    
    private int shares;             
    private float dividendYield;    
    private float interestRate;     
    private String maturityDate;    
    private float expenseRatio;     
    private String type;            

    public AssetBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AssetBuilder setValue(float value) {
        this.value = value;
        return this;
    }

    // Stock-specific setters
    public AssetBuilder setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
        return this;
    }

    public AssetBuilder setShares(int shares) {
        this.shares = shares;
        return this;
    }

    public AssetBuilder setDividendYield(float dividendYield) {
        this.dividendYield = dividendYield;
        return this;
    }

    // Bond-specific setters
    public AssetBuilder setInterestRate(float interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public AssetBuilder setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    // MutualFund-specific setter
    public AssetBuilder setExpenseRatio(float expenseRatio) {
        this.expenseRatio = expenseRatio;
        return this;
    }

    public AssetBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public FinancialAsset build() {
        switch (type) {
            case "Stock":
                return new Stock(name, value, tickerSymbol, shares, dividendYield);
            case "Bond":
                return new Bond(name, value, interestRate, maturityDate);
            case "MutualFund":
                return new MutualFund(name, value, expenseRatio);
            default:
                throw new IllegalArgumentException("Unknown asset type: " + type);
        }
    }

    public static FinancialPortfolio.Asset_Proto toProto(FinancialAsset asset) {
        FinancialPortfolio.Asset_Proto.Builder assetProto = FinancialPortfolio.Asset_Proto.newBuilder();
        
        if (asset instanceof Stock) {
            Stock stock = (Stock) asset;
            assetProto.setType("Stock");
            FinancialPortfolio.Stock_Proto.Builder stockProto = FinancialPortfolio.Stock_Proto.newBuilder();
            stockProto.setBaseAsset(FinancialPortfolio.BaseAsset_Proto.newBuilder()
                .setName(stock.getName())
                .setValue(stock.getValue())
                .build()
            );
            stockProto.setTicker(stock.getTickerSymbol());
            stockProto.setShares(stock.getShares());
            stockProto.setDividendYield(stock.getDividendYield());
            assetProto.setStock(stockProto);
        } else if (asset instanceof Bond) {
            Bond bond = (Bond) asset;
            assetProto.setType("Bond");
            FinancialPortfolio.Bond_Proto.Builder bondProto = FinancialPortfolio.Bond_Proto.newBuilder();
            bondProto.setBaseAsset(FinancialPortfolio.BaseAsset_Proto.newBuilder()
                .setName(bond.getName())
                .setValue(bond.getValue())
                .build()
            );
            bondProto.setInterestRate(bond.getInterestRate());
            bondProto.setMaturityDate(bond.getMaturityDate());
            assetProto.setBond(bondProto);
        } else if (asset instanceof MutualFund) {
            MutualFund mutualFund = (MutualFund) asset;
            assetProto.setType("MutualFund");
            FinancialPortfolio.MutualFund_Proto.Builder mutualFundProto = FinancialPortfolio.MutualFund_Proto.newBuilder();
            mutualFundProto.setBaseAsset(FinancialPortfolio.BaseAsset_Proto.newBuilder()
                .setName(mutualFund.getName())
                .setValue(mutualFund.getValue())
                .build()
            );
            mutualFundProto.setExpenseRatio(mutualFund.getExpenseRatio());
            assetProto.setMutualFund(mutualFundProto);
        } else {
            throw new IllegalArgumentException("Unknown asset type: " + asset.getClass().getName());
        }


        return assetProto.build();
    }


    public static FinancialAsset fromProto(FinancialPortfolio.Asset_Proto assetProto) {
        System.out.println(assetProto.getType());
        switch (assetProto.getType()) {
            case "Stock":
                return fromStockProto(assetProto.getStock());
            case "Bond":
                return fromBondProto(assetProto.getBond());
            case "MutualFund":
                return fromMutualFundProto(assetProto.getMutualFund());
            default:
                throw new IllegalArgumentException("Unknown asset type: " + assetProto.getType());
        }
    }

    private static Stock fromStockProto(FinancialPortfolio.Stock_Proto stockProto) {
        return new Stock(
            stockProto.getBaseAsset().getName(),
            stockProto.getBaseAsset().getValue(),
            stockProto.getTicker(),
            stockProto.getShares(),
            stockProto.getDividendYield()
        );
    }

    private static Bond fromBondProto(FinancialPortfolio.Bond_Proto bondProto) {
        return new Bond(
            bondProto.getBaseAsset().getName(),
            bondProto.getBaseAsset().getValue(),
            bondProto.getInterestRate(),
            bondProto.getMaturityDate()
        );
    }

    private static MutualFund fromMutualFundProto(FinancialPortfolio.MutualFund_Proto mutualFundProto) {
        return new MutualFund(
            mutualFundProto.getBaseAsset().getName(),
            mutualFundProto.getBaseAsset().getValue(),
            mutualFundProto.getExpenseRatio()
        );
    }
}
