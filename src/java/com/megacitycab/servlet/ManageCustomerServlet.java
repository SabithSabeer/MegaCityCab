package com.megacitycab.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.megacitycab.config.DBConn;
import com.megacitycab.models.ManageCustomer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/ManageCustomerServlet")
public class ManageCustomerServlet extends HttpServlet {
    private static final Gson gson = new Gson();

    // GET method to retrieve all customers
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ManageCustomer> customers = new ArrayList<>();
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customer");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(new ManageCustomer(
                        rs.getInt("customer_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("nic")
                ));
            }
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(customers));
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to retrieve customers.\"}");
        }
    }

    // POST method to add a new customer
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ManageCustomer customer = gson.fromJson(request.getReader(), ManageCustomer.class);

        if (customer.getUsername() == null || customer.getEmail() == null || customer.getPassword() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing required fields.\"}");
            return;
        }

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO customer (username, email, password, phone, address, nic) VALUES (?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPassword()); // Storing passwords in plain text is not recommended
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getNic());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"message\": \"Customer added successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Failed to add customer.\"}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error while adding customer.\"}");
        }
    }

    // PUT method to update customer details
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ManageCustomer customer = gson.fromJson(request.getReader(), ManageCustomer.class);

        if (customer.getCustomerId() <= 0 || customer.getUsername() == null || customer.getEmail() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid or missing data.\"}");
            return;
        }

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE customer SET username = ?, email = ?, phone = ?, address = ?, nic = ? WHERE customer_id = ?")) {

            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getNic());
            stmt.setInt(6, customer.getCustomerId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Customer updated successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Customer update failed.\"}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error while updating customer.\"}");
        }
    }

    // DELETE method to delete a customer
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        JsonObject json = gson.fromJson(reader, JsonObject.class);

        if (json == null || !json.has("customer_id")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing customer_id.\"}");
            return;
        }

        int customerId = json.get("customer_id").getAsInt();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
             PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM customer WHERE customer_id = ?")) {

            // Check if the customer exists
            checkStmt.setInt(1, customerId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Customer ID not found.\"}");
                return;
            }

            // Proceed with deletion
            deleteStmt.setInt(1, customerId);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Customer deleted successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Failed to delete customer.\"}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error while deleting customer.\"}");
        }
    }
}
