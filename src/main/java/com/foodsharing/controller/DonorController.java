package com.foodsharing.controller;

import com.foodsharing.dto.FoodDonationDTO;
import com.foodsharing.entity.FoodDonation;
import com.foodsharing.entity.FoodCategory;
import com.foodsharing.service.FoodDonationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * Controller for Donor Operations
 */
@Controller
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private FoodDonationService donationService;

    @GetMapping("/dashboard")
    public String donorDashboard(HttpSession session, Model model) {
        Long donorId = (Long) session.getAttribute("userId");
        if (donorId == null) {
            return "redirect:/auth/login";
        }
        
        try {
            List<FoodDonation> donations = donationService.getDonationsByDonor(donorId);
            model.addAttribute("donations", donations);
            model.addAttribute("totalDonations", donations.size());
            model.addAttribute("collectedCount", donations.stream()
                    .filter(d -> d.getStatus().toString().equals("COLLECTED")).count());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "donor/dashboard";
    }

    @GetMapping("/add-food")
    public String showAddFoodPage(Model model) {
        model.addAttribute("foodDonationDTO", new FoodDonationDTO());
        model.addAttribute("categories", FoodCategory.values());
        return "donor/add-food";
    }

    @PostMapping("/add-food")
    public String addFood(@Valid @ModelAttribute FoodDonationDTO donationDTO,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         HttpSession session, Model model) {
        Long donorId = (Long) session.getAttribute("userId");
        if (donorId == null) {
            return "redirect:/auth/login";
        }
        
        try {
            donationService.createDonation(donationDTO, donorId, imageFile);
            model.addAttribute("success", "Food donation created successfully!");
            return "redirect:/donor/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", FoodCategory.values());
            return "donor/add-food";
        }
    }

    @GetMapping("/edit-food/{id}")
    public String showEditFoodPage(@PathVariable Long id, Model model) {
        try {
            FoodDonation donation = donationService.getDonationById(id);
            FoodDonationDTO dto = new FoodDonationDTO();
            dto.setDonationId(donation.getDonationId());
            dto.setFoodName(donation.getFoodName());
            dto.setDescription(donation.getDescription());
            dto.setCategory(donation.getCategory().toString());
            dto.setQuantity(donation.getQuantity());
            dto.setUnit(donation.getUnit());
            
            model.addAttribute("foodDonationDTO", dto);
            model.addAttribute("categories", FoodCategory.values());
            return "donor/edit-food";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/donor/dashboard";
        }
    }

    @PostMapping("/edit-food/{id}")
    public String editFood(@PathVariable Long id,
                          @Valid @ModelAttribute FoodDonationDTO donationDTO,
                          HttpSession session, Model model) {
        try {
            donationService.updateDonation(id, donationDTO);
            model.addAttribute("success", "Food donation updated successfully!");
            return "redirect:/donor/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "donor/edit-food";
        }
    }

    @GetMapping("/delete-food/{id}")
    public String deleteFood(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return "redirect:/donor/dashboard";
    }

    @GetMapping("/my-donations")
    public String myDonations(HttpSession session, Model model) {
        Long donorId = (Long) session.getAttribute("userId");
        if (donorId == null) {
            return "redirect:/auth/login";
        }
        
        try {
            List<FoodDonation> donations = donationService.getDonationsByDonor(donorId);
            model.addAttribute("donations", donations);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "donor/my-donations";
    }
}
