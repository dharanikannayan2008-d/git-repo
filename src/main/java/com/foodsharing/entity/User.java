package com.foodsharing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * User Entity - Represents a user in the system (Donor, Receiver, or Admin)
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Column(unique = true)
    private String phone;

    private String address;
    private String city;
    private String state;
    private String postalCode;

    @Enumerated(EnumType.STRING)
    private UserRole role; // DONOR, RECEIVER, ADMIN

    private String organizationName; // For NGO/Receivers
    private String organizationType; // NGO, Charity, Volunteer, Orphanage

    @Column(columnDefinition = "TINYINT(1) default 1")
    private Boolean isActive = true;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private Boolean isVerified = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
