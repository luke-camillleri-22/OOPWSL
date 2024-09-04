package com.example;

import java.time.LocalDateTime;

public class Snapshot {
    private String assetName;
    private float assetValue;
    private LocalDateTime timestamp;

    public Snapshot(String assetName, float assetValue, LocalDateTime timestamp) {
        this.assetName = assetName;
        this.assetValue = assetValue;
        this.timestamp = timestamp;
    }

    public String getAssetName() {
        return assetName;
    }

    public float getAssetValue() {
        return assetValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void displaySnapshot() {
        System.out.println("Snapshot - Asset: " + assetName + ", Value: $" + assetValue + ", Time: " + timestamp);
    }
}
