package com.example;


import com.example.FinancialPortfolio;

public class IntermediaryBuilder {
    private String name;
    private String type; // "Broker", "Bank", "MutualFundManager"

    public IntermediaryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public IntermediaryBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public FinancialIntermediary build() {
        switch (type) {
            case "Broker":
                return new Broker(name);
            case "Bank":
                return new Bank(name);
            case "MutualFundManager":
                return new MutualFundManager(name);
            default:
                throw new IllegalArgumentException("Unknown intermediary type: " + type);
        }
    }

    public static FinancialPortfolio.Intermediary_Proto toProto(FinancialIntermediary intermediary) {
        FinancialPortfolio.Intermediary_Proto.Builder intermediaryProtoBuilder = FinancialPortfolio.Intermediary_Proto.newBuilder()
            .setName(intermediary.getIntermediaryName());
        if (intermediary instanceof Broker) {
            intermediaryProtoBuilder.setType("Broker")
                .setBroker(FinancialPortfolio.Broker_Proto.newBuilder()
                    .setIntermediary(FinancialPortfolio.Intermediary_Proto.newBuilder()
                        .setName(intermediary.getIntermediaryName())
                    )
                );
        } else if (intermediary instanceof Bank) {
            intermediaryProtoBuilder.setType("Bank")
                .setBank(FinancialPortfolio.Bank_Proto.newBuilder()
                    .setIntermediary(FinancialPortfolio.Intermediary_Proto.newBuilder()
                        .setName(intermediary.getIntermediaryName())
                    )
                );
        } else if (intermediary instanceof MutualFundManager) {
            intermediaryProtoBuilder.setType("MutualFundManager")
                .setMutualFundManager(FinancialPortfolio.MutualFundManager_Proto.newBuilder()
                    .setIntermediary(FinancialPortfolio.Intermediary_Proto.newBuilder()
                        .setName(intermediary.getIntermediaryName())
                    )
                );
        } else {
            throw new IllegalArgumentException("Unknown intermediary type: " + intermediary.getClass().getName());
        }
        return intermediaryProtoBuilder.build();
    }

    public static FinancialIntermediary fromProto(FinancialPortfolio.Intermediary_Proto intermediaryProto) {
        switch (intermediaryProto.getType()) {
            case "Broker":
                return fromBrokerProto(intermediaryProto.getBroker());
            case "Bank":
                return fromBankProto(intermediaryProto.getBank());
            case "MutualFundManager":
                return fromMutualFundManagerProto(intermediaryProto.getMutualFundManager());
            default:
                throw new IllegalArgumentException("Unknown intermediary type: " + intermediaryProto.getType());
        }
    }

    private static Broker fromBrokerProto(FinancialPortfolio.Broker_Proto brokerProto) {
        return new Broker(brokerProto.getIntermediary().getName());
    }

    private static Bank fromBankProto(FinancialPortfolio.Bank_Proto bankProto) {
        return new Bank(bankProto.getIntermediary().getName());
    }

    private static MutualFundManager fromMutualFundManagerProto(FinancialPortfolio.MutualFundManager_Proto mutualFundManagerProto) {
        return new MutualFundManager(mutualFundManagerProto.getIntermediary().getName());
    }
}
