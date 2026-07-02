package com.foodsharing.entity;

/**
 * Enum for Food Categories
 */
public enum FoodCategory {
    COOKED("Cooked Food"),
    RAW("Raw Food"),
    BAKERY("Bakery Items"),
    DAIRY("Dairy Products"),
    FRUITS("Fruits"),
    VEGETABLES("Vegetables"),
    BEVERAGES("Beverages"),
    SNACKS("Snacks"),
    OTHER("Other");

    private final String displayName;

    FoodCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
