package com.foodsharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO for Food Donation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDonationDTO {
    private Long donationId;
    private String foodName;
    private String description;
    private String category;
    private Integer quantity;
    private String unit;
    private String foodImagePath;
    private LocalDateTime preparationTime;
    private LocalDateTime expiryTime;
    private LocalDateTime pickupDeadline;
    private String pickupAddress;
    private String pickupCity;
    private String contactNumber;
    private String status;
    private String specialInstructions;
    private LocalDateTime createdAt;
    private Long donorId;
    private String donorName;
}
