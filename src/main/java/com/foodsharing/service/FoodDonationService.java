package com.foodsharing.service;

import com.foodsharing.dto.FoodDonationDTO;
import com.foodsharing.entity.FoodDonation;
import com.foodsharing.entity.DonationStatus;
import com.foodsharing.entity.FoodCategory;
import com.foodsharing.entity.User;
import com.foodsharing.repository.FoodDonationRepository;
import com.foodsharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for Food Donation Management
 */
@Service
public class FoodDonationService {

    @Autowired
    private FoodDonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Create a new food donation
     */
    public FoodDonation createDonation(FoodDonationDTO donationDTO, Long donorId, MultipartFile imageFile) throws Exception {
        User donor = userRepository.findById(donorId)
                .orElseThrow(() -> new Exception("Donor not found"));

        FoodDonation donation = new FoodDonation();
        donation.setDonor(donor);
        donation.setFoodName(donationDTO.getFoodName());
        donation.setDescription(donationDTO.getDescription());
        donation.setCategory(FoodCategory.valueOf(donationDTO.getCategory().toUpperCase()));
        donation.setQuantity(donationDTO.getQuantity());
        donation.setUnit(donationDTO.getUnit());
        donation.setPreparationTime(donationDTO.getPreparationTime());
        donation.setExpiryTime(donationDTO.getExpiryTime());
        donation.setPickupDeadline(donationDTO.getPickupDeadline());
        donation.setPickupAddress(donationDTO.getPickupAddress());
        donation.setPickupCity(donationDTO.getPickupCity());
        donation.setContactNumber(donationDTO.getContactNumber());
        donation.setStatus(DonationStatus.AVAILABLE);
        donation.setSpecialInstructions(donationDTO.getSpecialInstructions());

        // Handle image upload
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            donation.setFoodImagePath(imagePath);
        }

        return donationRepository.save(donation);
    }

    /**
     * Get donation by ID
     */
    public FoodDonation getDonationById(Long donationId) throws Exception {
        return donationRepository.findById(donationId)
                .orElseThrow(() -> new Exception("Donation not found"));
    }

    /**
     * Get all donations by donor
     */
    public List<FoodDonation> getDonationsByDonor(Long donorId) throws Exception {
        User donor = userRepository.findById(donorId)
                .orElseThrow(() -> new Exception("Donor not found"));
        return donationRepository.findByDonor(donor);
    }

    /**
     * Get all available donations
     */
    public List<FoodDonation> getAvailableDonations() {
        return donationRepository.findByStatus(DonationStatus.AVAILABLE);
    }

    /**
     * Get donations by city
     */
    public List<FoodDonation> getDonationsByCity(String city) {
        return donationRepository.findByPickupCity(city);
    }

    /**
     * Get available donations by city
     */
    public List<FoodDonation> getAvailableDonationsByCity(String city) {
        return donationRepository.findAvailableDonationsByCity(DonationStatus.AVAILABLE, city);
    }

    /**
     * Get expired donations
     */
    public List<FoodDonation> getExpiredDonations() {
        return donationRepository.findExpiredDonations(LocalDateTime.now());
    }

    /**
     * Update donation
     */
    public FoodDonation updateDonation(Long donationId, FoodDonationDTO donationDTO) throws Exception {
        FoodDonation donation = getDonationById(donationId);
        donation.setFoodName(donationDTO.getFoodName());
        donation.setDescription(donationDTO.getDescription());
        donation.setCategory(FoodCategory.valueOf(donationDTO.getCategory().toUpperCase()));
        donation.setQuantity(donationDTO.getQuantity());
        donation.setUnit(donationDTO.getUnit());
        donation.setExpiryTime(donationDTO.getExpiryTime());
        donation.setPickupDeadline(donationDTO.getPickupDeadline());
        donation.setPickupAddress(donationDTO.getPickupAddress());
        donation.setPickupCity(donationDTO.getPickupCity());
        donation.setContactNumber(donationDTO.getContactNumber());
        donation.setSpecialInstructions(donationDTO.getSpecialInstructions());
        return donationRepository.save(donation);
    }

    /**
     * Update donation status
     */
    public FoodDonation updateDonationStatus(Long donationId, DonationStatus status) throws Exception {
        FoodDonation donation = getDonationById(donationId);
        donation.setStatus(status);
        if (status == DonationStatus.COLLECTED) {
            donation.setCollectedAt(LocalDateTime.now());
        }
        return donationRepository.save(donation);
    }

    /**
     * Delete donation
     */
    public void deleteDonation(Long donationId) {
        donationRepository.deleteById(donationId);
    }

    /**
     * Save uploaded image
     */
    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return "/uploads/" + fileName;
    }

    /**
     * Get total donations count
     */
    public long getTotalDonationsCount() {
        return donationRepository.count();
    }

    /**
     * Get collected donations count
     */
    public long getCollectedDonationsCount() {
        return donationRepository.countByStatus(DonationStatus.COLLECTED);
    }
}
