package com.foodsharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO for Food Request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequestDTO {
    private Long requestId;
    private Long donationId;
    private Long receiverId;
    private String status;
    private String requestMessage;
    private String pickupNote;
    private Boolean donorApproved;
    private Boolean receiverCollected;
    private LocalDateTime createdAt;
    private LocalDateTime approvalTime;
    private LocalDateTime collectionTime;
    private String donationFoodName;
    private String receiverName;
}
