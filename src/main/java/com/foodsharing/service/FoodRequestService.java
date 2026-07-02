package com.foodsharing.service;

import com.foodsharing.dto.FoodRequestDTO;
import com.foodsharing.entity.FoodRequest;
import com.foodsharing.entity.RequestStatus;
import com.foodsharing.entity.FoodDonation;
import com.foodsharing.entity.DonationStatus;
import com.foodsharing.entity.User;
import com.foodsharing.repository.FoodRequestRepository;
import com.foodsharing.repository.FoodDonationRepository;
import com.foodsharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for Food Request Management
 */
@Service
public class FoodRequestService {

    @Autowired
    private FoodRequestRepository requestRepository;

    @Autowired
    private FoodDonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new food request
     */
    public FoodRequest createRequest(Long donationId, Long receiverId, String requestMessage) throws Exception {
        FoodDonation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new Exception("Donation not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new Exception("Receiver not found"));

        // Check if donation is available
        if (!donation.getStatus().equals(DonationStatus.AVAILABLE)) {
            throw new Exception("This donation is not available for request");
        }

        FoodRequest request = new FoodRequest();
        request.setDonation(donation);
        request.setReceiver(receiver);
        request.setStatus(RequestStatus.PENDING);
        request.setRequestMessage(requestMessage);
        request.setDonorApproved(false);
        request.setReceiverCollected(false);

        // Update donation status to REQUESTED
        donation.setStatus(DonationStatus.REQUESTED);
        donationRepository.save(donation);

        return requestRepository.save(request);
    }

    /**
     * Get request by ID
     */
    public FoodRequest getRequestById(Long requestId) throws Exception {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new Exception("Request not found"));
    }

    /**
     * Get all requests by receiver
     */
    public List<FoodRequest> getRequestsByReceiver(Long receiverId) throws Exception {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new Exception("Receiver not found"));
        return requestRepository.findByReceiver(receiver);
    }

    /**
     * Get all pending requests for a donor
     */
    public List<FoodRequest> getPendingRequestsForDonor(Long donorId) throws Exception {
        User donor = userRepository.findById(donorId)
                .orElseThrow(() -> new Exception("Donor not found"));
        return requestRepository.findByDonation_Donor(donor);
    }

    /**
     * Approve request by donor
     */
    public FoodRequest approveRequest(Long requestId) throws Exception {
        FoodRequest request = getRequestById(requestId);
        request.setStatus(RequestStatus.APPROVED);
        request.setDonorApproved(true);
        request.setApprovalTime(LocalDateTime.now());

        // Update donation status
        FoodDonation donation = request.getDonation();
        donation.setStatus(DonationStatus.APPROVED);
        donationRepository.save(donation);

        return requestRepository.save(request);
    }

    /**
     * Reject request by donor
     */
    public FoodRequest rejectRequest(Long requestId) throws Exception {
        FoodRequest request = getRequestById(requestId);
        request.setStatus(RequestStatus.REJECTED);

        // Update donation status back to AVAILABLE
        FoodDonation donation = request.getDonation();
        donation.setStatus(DonationStatus.AVAILABLE);
        donationRepository.save(donation);

        return requestRepository.save(request);
    }

    /**
     * Mark request as collected
     */
    public FoodRequest markAsCollected(Long requestId) throws Exception {
        FoodRequest request = getRequestById(requestId);
        request.setStatus(RequestStatus.COLLECTED);
        request.setReceiverCollected(true);
        request.setCollectionTime(LocalDateTime.now());

        // Update donation status
        FoodDonation donation = request.getDonation();
        donation.setStatus(DonationStatus.COLLECTED);
        donation.setCollectedAt(LocalDateTime.now());
        donationRepository.save(donation);

        return requestRepository.save(request);
    }

    /**
     * Get all requests by status
     */
    public List<FoodRequest> getRequestsByStatus(RequestStatus status) {
        return requestRepository.findByStatus(status);
    }

    /**
     * Delete request
     */
    public void deleteRequest(Long requestId) {
        requestRepository.deleteById(requestId);
    }
}
