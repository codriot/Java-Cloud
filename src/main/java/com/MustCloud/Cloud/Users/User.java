package com.MustCloud.Cloud.Users;

import com.MustCloud.Cloud.Files.File;
import com.MustCloud.Cloud.Util.JwtUtil;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    private LocalDateTime signupDate = LocalDateTime.now();

    private String accountType = "Normal";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files;

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserIdToken() {
        return JwtUtil.generateToken(this.userId);
    }

    public void setUserIdFromToken(String token) {
        this.userId = JwtUtil.extractUserId(token);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDateTime signupDate) {
        this.signupDate = signupDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}