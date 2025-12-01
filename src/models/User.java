package models;

// This is the User Class
public abstract class User {
    public String ID;
    public String name;
    public String password;
    public String email;
    // Static variable that all objects share. For keeping track of user IDs
    static int LastID = 1;

    public User(String name, String password) {
        this.ID = String.format("U%03d", LastID);
        LastID++;
        this.name = name;
        this.password = password;
    }
    public User(String name, String password, String email) {
        this.ID = String.format("U%03d", LastID);
        LastID++;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
