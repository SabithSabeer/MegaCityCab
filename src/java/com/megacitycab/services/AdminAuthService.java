package com.megacitycab.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.megacitycab.config.DBConn;

public class AdminAuthService {
    public boolean validateAdmin(String email, String password) {
        boolean isValid = false;
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM admin WHERE email = ?")) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");

                // If password is stored as plain text
                if (password.equals(dbPassword)) {
                    isValid = true;
                }

                // If passwords are hashed, use bcrypt (Example)
                // if (BCrypt.checkpw(password, dbPassword)) {
                //     isValid = true;
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
