package com.foodsharing.service;

import com.foodsharing.entity.Admin;
import com.foodsharing.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service for Admin Management
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Login admin
     */
    public Admin loginAdmin(String username, String password) throws Exception {
        Optional<Admin> admin = adminRepository.findByUsernameAndPassword(username, password);
        if (admin.isEmpty()) {
            throw new Exception("Invalid username or password");
        }
        if (!admin.get().getIsActive()) {
            throw new Exception("Admin account is inactive");
        }
        return admin.get();
    }

    /**
     * Get admin by ID
     */
    public Admin getAdminById(Long adminId) throws Exception {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new Exception("Admin not found"));
    }

    /**
     * Get all admins
     */
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
