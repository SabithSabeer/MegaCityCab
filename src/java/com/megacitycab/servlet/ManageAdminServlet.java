package com.megacitycab.servlet;

import com.google.gson.Gson;
import com.megacitycab.dao.AdminDAO;
import com.megacitycab.models.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/ManageAdminServlet")
public class ManageAdminServlet extends HttpServlet {
    private final AdminDAO adminDAO = new AdminDAO();
    private final Gson gson = new Gson();

    // Handle GET request
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Admin> admins = adminDAO.getAllAdmins();
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(admins));
    }

    // Handle POST request (Add admin)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Admin admin = gson.fromJson(request.getReader(), Admin.class);
        boolean success = adminDAO.addAdmin(admin);
        response.setStatus(success ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST);
    }

    // Handle PUT request (Update admin)
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        Admin admin = gson.fromJson(reader, Admin.class);
        boolean success = adminDAO.updateAdmin(admin);
        response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }

    // Handle DELETE request (Delete admin by username)
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");  // Fetch username parameter from the URL
        if (username == null || username.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Username is required.");
            return;
        }

        boolean success = adminDAO.deleteAdmin(username); // Delete based on username
        response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }
}
