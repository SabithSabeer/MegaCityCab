package com.megacitycab.models;

import java.time.LocalDateTime;

public class Admin {
    private int adminId;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String nic;
    private LocalDateTime createdAt;

    public Admin(int adminId, String username, String email, String password, String phone, String nic, LocalDateTime createdAt) {
        this.adminId = adminId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nic = nic;
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }
}
