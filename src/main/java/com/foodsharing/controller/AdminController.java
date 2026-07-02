package com.foodsharing.controller;

import com.foodsharing.service.FoodDonationService;
import com.foodsharing.service.UserService;
import com.foodsharing.service.FoodRequestService;
import com.foodsharing.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for Admin Dashboard
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private FoodDonationService donationService;

    @Autowired
    private FoodRequestService requestService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        try {
            // Get statistics
            long totalUsers = userService.getAllActiveUsers().size();
            long totalDonations = donationService.getTotalDonationsCount();
            long collectedDonations = donationService.getCollectedDonationsCount();
            long totalDonors = userService.getUsersByRole(UserRole.DONOR).size();
            long totalReceivers = userService.getUsersByRole(UserRole.RECEIVER).size();

            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalDonations", totalDonations);
            model.addAttribute("collectedDonations", collectedDonations);
            model.addAttribute("totalDonors", totalDonors);
            model.addAttribute("totalReceivers", totalReceivers);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "admin/dashboard";
    }
}
