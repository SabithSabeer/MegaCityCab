package com.megacitycab.models;

public class Admin {
    private int id;  // Ensure this field exists
    private String username, email, phone, nic, password;

    public Admin(int id, String username, String email, String phone, String nic, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.nic = nic;
        this.password = password;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getNic() { return nic; }
    public String getPassword() { return password; }
}
