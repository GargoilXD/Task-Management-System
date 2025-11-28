package models;

// This is the class for AdminUsers
public class AdminUser extends User {
    public AdminUser(String name, String password, String email) {
        super(name, password, email);
    }
    public AdminUser(String name, String password) {
        super(name, password);
    }
}
