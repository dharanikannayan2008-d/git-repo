package com.foodsharing.controller;

import com.foodsharing.dto.LoginDTO;
import com.foodsharing.dto.RegisterDTO;
import com.foodsharing.entity.User;
import com.foodsharing.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Authentication
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDTO loginDTO, 
                       HttpSession session, Model model) {
        try {
            User user = userService.loginUser(loginDTO);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userEmail", user.getEmail());
            
            // Redirect based on role
            if (user.getRole().toString().equals("DONOR")) {
                return "redirect:/donor/dashboard";
            } else if (user.getRole().toString().equals("RECEIVER")) {
                return "redirect:/receiver/dashboard";
            }
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterDTO registerDTO, Model model) {
        try {
            userService.registerUser(registerDTO);
            model.addAttribute("success", "Registration successful! Please login.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
