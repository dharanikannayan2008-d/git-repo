package com.foodsharing.repository;

import com.foodsharing.entity.FoodDonation;
import com.foodsharing.entity.DonationStatus;
import com.foodsharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * FoodDonation Repository - Data Access Layer for FoodDonation entity
 */
@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {
    List<FoodDonation> findByDonor(User donor);
    List<FoodDonation> findByStatus(DonationStatus status);
    List<FoodDonation> findByPickupCity(String city);
    
    @Query("SELECT f FROM FoodDonation f WHERE f.status = :status AND f.pickupCity = :city")
    List<FoodDonation> findAvailableDonationsByCity(@Param("status") DonationStatus status, 
                                                     @Param("city") String city);
    
    @Query("SELECT f FROM FoodDonation f WHERE f.expiryTime < :now")
    List<FoodDonation> findExpiredDonations(@Param("now") LocalDateTime now);
    
    @Query("SELECT COUNT(f) FROM FoodDonation f WHERE f.status = :status")
    long countByStatus(@Param("status") DonationStatus status);
}
