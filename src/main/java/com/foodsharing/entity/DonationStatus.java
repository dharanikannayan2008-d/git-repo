package com.foodsharing.entity;

/**
 * Enum for Donation Status
 */
public enum DonationStatus {
    AVAILABLE("Available"),
    REQUESTED("Requested"),
    APPROVED("Approved"),
    COLLECTED("Collected"),
    EXPIRED("Expired"),
    CANCELLED("Cancelled");

    private final String displayName;

    DonationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
