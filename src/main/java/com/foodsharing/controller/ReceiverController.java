package com.foodsharing.controller;

import com.foodsharing.entity.FoodDonation;
import com.foodsharing.entity.FoodCategory;
import com.foodsharing.service.FoodDonationService;
import com.foodsharing.service.FoodRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for Receiver Operations
 */
@Controller
@RequestMapping("/receiver")
public class ReceiverController {

    @Autowired
    private FoodDonationService donationService;

    @Autowired
    private FoodRequestService requestService;

    @GetMapping("/dashboard")
    public String receiverDashboard(HttpSession session, Model model) {
        Long receiverId = (Long) session.getAttribute("userId");
        if (receiverId == null) {
            return "redirect:/auth/login";
        }
        
        try {
            List<FoodDonation> availableDonations = donationService.getAvailableDonations();
            model.addAttribute("availableDonations", availableDonations);
            model.addAttribute("totalAvailable", availableDonations.size());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "receiver/dashboard";
    }

    @GetMapping("/available-food")
    public String viewAvailableFood(Model model) {
        try {
            List<FoodDonation> donations = donationService.getAvailableDonations();
            model.addAttribute("donations", donations);
            model.addAttribute("categories", FoodCategory.values());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "receiver/available-food";
    }

    @GetMapping("/food-details/{id}")
    public String viewFoodDetails(@PathVariable Long id, Model model) {
        try {
            FoodDonation donation = donationService.getDonationById(id);
            model.addAttribute("donation", donation);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "receiver/food-details";
    }

    @PostMapping("/request-food/{donationId}")
    public String requestFood(@PathVariable Long donationId,
                             @RequestParam("requestMessage") String requestMessage,
                             HttpSession session, Model model) {
        Long receiverId = (Long) session.getAttribute("userId");
        if (receiverId == null) {
            return "redirect:/auth/login";
        }
        
        try {
            requestService.createRequest(donationId, receiverId, requestMessage);
            model.addAttribute("success", "Food request created successfully!");
            return "redirect:/receiver/my-requests";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/receiver/food-details/" + donationId;
        }
    }

    @GetMapping("/my-requests")
    public String myRequests(HttpSession session, Model model) {
        Long receiverId = (Long) session.getAttribute("userId");
        if (receiverId == null) {
            return "redirect:/auth/login";
        }
        
        try {
            var requests = requestService.getRequestsByReceiver(receiverId);
            model.addAttribute("requests", requests);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "receiver/my-requests";
    }

    @GetMapping("/search-by-city")
    public String searchByCity(@RequestParam("city") String city, Model model) {
        try {
            List<FoodDonation> donations = donationService.getAvailableDonationsByCity(city);
            model.addAttribute("donations", donations);
            model.addAttribute("searchCity", city);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "receiver/search-results";
    }
}
