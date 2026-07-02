package com.foodsharing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * FoodDonation Entity - Represents a food donation listing
 */
@Entity
@Table(name = "food_donations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;

    @NotBlank(message = "Food name is required")
    private String foodName;

    private String description;

    @Enumerated(EnumType.STRING)
    private FoodCategory category; // COOKED, RAW, BAKERY, DAIRY, FRUITS, VEGETABLES

    private Integer quantity;
    private String unit; // kg, pieces, liters

    private String foodImagePath;

    private LocalDateTime preparationTime;
    private LocalDateTime expiryTime;
    private LocalDateTime pickupDeadline;

    private String pickupAddress;
    private String pickupCity;
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private DonationStatus status; // AVAILABLE, REQUESTED, APPROVED, COLLECTED, EXPIRED

    private String specialInstructions;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private Boolean isApproved = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime collectedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = DonationStatus.AVAILABLE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
