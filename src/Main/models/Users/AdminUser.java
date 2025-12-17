package Main.models.Users;

// This is the class for AdminUsers
public class AdminUser extends User {
    public AdminUser(String name, String password, String email) {
        super(name, password, email);
    }
    public AdminUser(String ID, String name, String password, String email) {
        super(ID, name, password, email);
    }
    public AdminUser(String name, String password) {
        super(name, password);
    }
}
