package com.megacitycab.services;

import com.megacitycab.dao.CustomerDAO;
import com.megacitycab.models.Customer;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public String registerNewCustomer(String username, String email, String password, String phone, String address, String nic) {
        try {
            if (!isValidInput(username, email, password, phone, address, nic)) {
                return "Error: Invalid input data. Please check your details and try again.";
            }
            if (customerDAO.isEmailExists(email)) {
                return "Error: Email already exists!";
            }

            // Hash the password securely
            String hashedPassword;
            try {
                hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
            } catch (Exception e) {
                e.printStackTrace();
                return "Error: Password hashing failed!";
            }
            int customerId = 0;

            Customer customer = new Customer(customerId, username, email, hashedPassword, phone, address, nic);
            boolean isRegistered = customerDAO.registerCustomer(customer);

            return isRegistered ? "Registration successful!" : "Error: Registration failed.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: Database issue!";
        }
    }

    private boolean isValidInput(String username, String email, String password, String phone, String address, String nic) {
        // Username: 3-20 characters, letters and numbers only
        if (username == null || !Pattern.matches("^[a-zA-Z0-9]{3,20}$", username)) {
            return false;
        }

        // Email: Basic format validation
        if (email == null || !Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email)) {
            return false;
        }

        // Password: Minimum 8 characters, at least one uppercase, lowercase, number, and special character
        if (password == null || !Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", password)) {
            return false;
        }

        // Phone: 10 digits only
        if (phone == null || !Pattern.matches("^\\d{10}$", phone)) {
            return false;
        }

        // Address: Not empty
        if (address == null || address.trim().isEmpty()) {
            return false;
        }

        // NIC: Must be 10 or 12 characters (old/new format)
        if (nic == null || !Pattern.matches("^(\\d{9}[Vv]|\\d{12})$", nic)) {
            return false;
        }

        return true;
    }
}
