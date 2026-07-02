package com.foodsharing.service;

import com.foodsharing.dto.LoginDTO;
import com.foodsharing.dto.RegisterDTO;
import com.foodsharing.entity.User;
import com.foodsharing.entity.UserRole;
import com.foodsharing.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service for User Management
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Register a new user
     */
    public User registerUser(RegisterDTO registerDTO) throws Exception {
        // Check if email already exists
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }

        // Check if phone already exists
        if (userRepository.findByPhone(registerDTO.getPhone()).isPresent()) {
            throw new Exception("Phone number already registered");
        }

        // Verify passwords match
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new Exception("Passwords do not match");
        }

        User user = new User();
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword()); // In production, hash the password
        user.setPhone(registerDTO.getPhone());
        user.setAddress(registerDTO.getAddress());
        user.setCity(registerDTO.getCity());
        user.setState(registerDTO.getState());
        user.setPostalCode(registerDTO.getPostalCode());
        user.setRole(UserRole.valueOf(registerDTO.getRole().toUpperCase()));
        user.setOrganizationName(registerDTO.getOrganizationName());
        user.setOrganizationType(registerDTO.getOrganizationType());
        user.setIsActive(true);
        user.setIsVerified(false);

        return userRepository.save(user);
    }

    /**
     * Login user
     */
    public User loginUser(LoginDTO loginDTO) throws Exception {
        Optional<User> user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (user.isEmpty()) {
            throw new Exception("Invalid email or password");
        }
        if (!user.get().getIsActive()) {
            throw new Exception("User account is inactive");
        }
        return user.get();
    }

    /**
     * Get user by ID
     */
    public User getUserById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
    }

    /**
     * Get user by email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Update user profile
     */
    public User updateUserProfile(User user) {
        return userRepository.save(user);
    }

    /**
     * Get all users by role
     */
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    /**
     * Get all active users
     */
    public List<User> getAllActiveUsers() {
        return userRepository.findByIsActive(true);
    }

    /**
     * Deactivate user account
     */
    public void deactivateUser(Long userId) throws Exception {
        User user = getUserById(userId);
        user.setIsActive(false);
        userRepository.save(user);
    }

    /**
     * Delete user
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
