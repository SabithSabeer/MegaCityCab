package com.megacitycab.services;

import com.megacitycab.dao.ManageCustomerDAO;
import com.megacitycab.models.ManageCustomer;

import java.util.List;

public class ManageCustomerService {
    private final ManageCustomerDAO managecustomerDAO;

    public ManageCustomerService() {
        this.managecustomerDAO = new ManageCustomerDAO();
    }

    // Fetch all customers
    public List<ManageCustomer> getAllCustomers() {
        return managecustomerDAO.getAllCustomers();
    }

    // Insert a new customer
    public boolean insertCustomer(ManageCustomer customer) {
        return managecustomerDAO.insertCustomer(customer);
    }

    // Delete a customer by ID
    public boolean deleteCustomer(int customerId) {
        return managecustomerDAO.deleteCustomer(customerId);
    }

    // Update customer details
    public boolean updateCustomer(ManageCustomer customer) {
        return managecustomerDAO.updateCustomer(customer);
    }
}
