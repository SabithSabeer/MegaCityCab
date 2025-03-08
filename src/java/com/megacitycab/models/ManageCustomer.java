package com.megacitycab.models;

public class ManageCustomer {
    private int customerId;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String nic;

    // Constructor for GET requests (without password)
    public ManageCustomer(int customerId, String username, String email, String phone, String address, String nic) {
        this.customerId = customerId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.nic = nic;
        this.password = null; // Password is not available in GET requests
    }

    // Constructor for POST requests (with password)
    public ManageCustomer(int customerId, String username, String email, String password, String phone, String address, String nic) {
        this.customerId = customerId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.nic = nic;
    }

    // Constructor for updating (with password field)
    public ManageCustomer(String username, String email, String password, String phone, String address, String nic) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.nic = nic;
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
