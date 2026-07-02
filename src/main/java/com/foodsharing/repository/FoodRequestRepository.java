package com.foodsharing.repository;

import com.foodsharing.entity.FoodRequest;
import com.foodsharing.entity.RequestStatus;
import com.foodsharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * FoodRequest Repository - Data Access Layer for FoodRequest entity
 */
@Repository
public interface FoodRequestRepository extends JpaRepository<FoodRequest, Long> {
    List<FoodRequest> findByReceiver(User receiver);
    List<FoodRequest> findByStatus(RequestStatus status);
    List<FoodRequest> findByDonation_Donor(User donor);
}
