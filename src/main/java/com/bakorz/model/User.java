package com.bakorz.model;

import java.time.LocalDateTime;

/**
 * Represents a User in the Metavie application.
 * Contains user authentication and profile information.
 * Tracks account creation and last login timestamps.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class User {
    /** Unique identifier for the user */
    private String userId;

    /** Username for display and login */
    private String username;

    /** User's email address */
    private String email;

    /** User's password (should be hashed in production) */
    private String password;

    /** Timestamp of account creation */
    private LocalDateTime createdAt;

    /** Timestamp of last successful login */
    private LocalDateTime lastLogin;

    /**
     * Default constructor for User.
     */
    public User() {
    }

    /**
     * Constructor for creating a new user with basic information.
     * Sets creation timestamp to current time.
     * 
     * @param userId   Unique user ID
     * @param username Username
     * @param email    Email address
     * @param password Password (should be hashed)
     */
    public User(String userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Full constructor with all fields including timestamps.
     * 
     * @param userId    Unique user ID
     * @param username  Username
     * @param email     Email address
     * @param password  Password (should be hashed)
     * @param createdAt Account creation timestamp
     * @param lastLogin Last login timestamp
     */
    public User(String userId, String username, String email, String password,
            LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    // Getters and Setters with documentation

    /**
     * Gets the user ID.
     * 
     * @return Unique user identifier
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * 
     * @param userId User identifier to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     * 
     * @return Username string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username Username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     * 
     * @return Email address string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     * 
     * @param email Email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     * Note: In production, passwords should be hashed.
     * 
     * @return Password string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * Note: In production, passwords should be hashed before setting.
     * 
     * @param password Password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the account creation timestamp.
     * 
     * @return Creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the account creation timestamp.
     * 
     * @param createdAt Creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the last login timestamp.
     * 
     * @return Last login timestamp
     */
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets the last login timestamp.
     * 
     * @param lastLogin Last login timestamp to set
     */
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Returns a string representation of the User.
     * Excludes password for security.
     * 
     * @return String containing user information
     */
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
