package models;

public abstract class User {
    public String ID;
    public String name;
    public String password;
    public String email;
    static int LastID;

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
