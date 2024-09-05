package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.google.protobuf.InvalidProtocolBufferException; 
import com.example.FinancialPortfolio;

public class Facade {

    private final String filePath;

    public Facade(String filePath) {
        this.filePath = filePath;
    }

    // Save the current portfolio state to a file
    public void save(FinancialPortfolio.Portfolio_Proto portfolio) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            portfolio.writeTo(fos);  // Serialize the portfolio
            System.out.println("Portfolio saved successfully.");
        }
    }

    // Load the portfolio state from a file
    public FinancialPortfolio.Portfolio_Proto load() throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return FinancialPortfolio.Portfolio_Proto.parseFrom(fis);  // Deserialize the portfolio
        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to parse portfolio data.");
            throw new IOException(e);
        }
    }
}
