package com.example;

import java.time.LocalDateTime;

public class SnapshotBuilder {
    private String assetName;
    private float assetValue;
    private String timestamp;

    public SnapshotBuilder setAssetName(String assetName) {
        this.assetName = assetName;
        return this;
    }

    public SnapshotBuilder setAssetValue(float assetValue) {
        this.assetValue = assetValue;
        return this;
    }

    public SnapshotBuilder setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Snapshot build() {
        return new Snapshot(assetName, assetValue, timestamp);
    }

    public static FinancialPortfolio.Snapshot_Proto toProto(Snapshot snapshot) {
        return FinancialPortfolio.Snapshot_Proto.newBuilder()
            .setAssetName(snapshot.getAssetName())
            .setAssetValue(snapshot.getAssetValue())
            .setTimestamp(snapshot.getTimestamp())
            .build();
    }

    public static Snapshot fromProto(FinancialPortfolio.Snapshot_Proto snapshotProto) {
        return new Snapshot(
            snapshotProto.getAssetName(),
            snapshotProto.getAssetValue(),
            snapshotProto.getTimestamp()
        );
    }
    
}