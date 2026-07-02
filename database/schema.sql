-- MySQL Database Schema for Smart Leftover Food Sharing System

CREATE DATABASE IF NOT EXISTS food_sharing_db;
USE food_sharing_db;

-- Users Table
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    role ENUM('DONOR', 'RECEIVER', 'ADMIN') NOT NULL,
    organization_name VARCHAR(255),
    organization_type VARCHAR(100),
    is_active TINYINT(1) DEFAULT 1,
    is_verified TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_city (city)
);

-- Food Donations Table
CREATE TABLE food_donations (
    donation_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    donor_id BIGINT NOT NULL,
    food_name VARCHAR(255) NOT NULL,
    description TEXT,
    category ENUM('COOKED', 'RAW', 'BAKERY', 'DAIRY', 'FRUITS', 'VEGETABLES', 'BEVERAGES', 'SNACKS', 'OTHER'),
    quantity INT,
    unit VARCHAR(50),
    food_image_path VARCHAR(500),
    preparation_time TIMESTAMP,
    expiry_time TIMESTAMP NOT NULL,
    pickup_deadline TIMESTAMP NOT NULL,
    pickup_address VARCHAR(255),
    pickup_city VARCHAR(100),
    contact_number VARCHAR(20),
    status ENUM('AVAILABLE', 'REQUESTED', 'APPROVED', 'COLLECTED', 'EXPIRED', 'CANCELLED') DEFAULT 'AVAILABLE',
    special_instructions TEXT,
    is_approved TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    collected_at TIMESTAMP,
    FOREIGN KEY (donor_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_donor_id (donor_id),
    INDEX idx_status (status),
    INDEX idx_pickup_city (pickup_city),
    INDEX idx_expiry_time (expiry_time)
);

-- Food Requests Table
CREATE TABLE food_requests (
    request_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    donation_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COLLECTED', 'CANCELLED') DEFAULT 'PENDING',
    request_message TEXT,
    pickup_note TEXT,
    donor_approved TINYINT(1) DEFAULT 0,
    receiver_collected TINYINT(1) DEFAULT 0,
    approval_time TIMESTAMP,
    collection_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (donation_id) REFERENCES food_donations(donation_id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_donation_id (donation_id),
    INDEX idx_receiver_id (receiver_id),
    INDEX idx_status (status)
);

-- Admin Table
CREATE TABLE admin (
    admin_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(200),
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- Create indexes for better query performance
CREATE INDEX idx_donation_donor_city ON food_donations(donor_id, pickup_city);
CREATE INDEX idx_request_donation_receiver ON food_requests(donation_id, receiver_id);
