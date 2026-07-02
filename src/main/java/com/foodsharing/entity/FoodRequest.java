package com.foodsharing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * FoodRequest Entity - Represents a food request from a receiver
 */
@Entity
@Table(name = "food_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private FoodDonation donation;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, APPROVED, REJECTED, COLLECTED

    private String requestMessage;
    private String pickupNote;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private Boolean donorApproved = false;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private Boolean receiverCollected = false;

    private LocalDateTime approvalTime;
    private LocalDateTime collectionTime;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = RequestStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
