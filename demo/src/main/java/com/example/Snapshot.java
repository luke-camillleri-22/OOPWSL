package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Snapshot {

    private final String assetName;
    private final float assetValue;
    private final String timestamp;

    public Snapshot(String assetName, float assetValue, String timestamp) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void displaySnapshot() {
        System.out.println("Snapshot - Asset: " + assetName + ", Value: $" + assetValue + ", Time: " + timestamp);
    }
}
