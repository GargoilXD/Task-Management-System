package models.Users;

// This is the User Class
public abstract class User {
    public String ID;
    public String Name;
    public String Password;
    public String Email;
    // Static variable that all objects share. For keeping track of user IDs
    static int LastID = 1;

    public User(String Name, String Password) {
        this.ID = String.format("U%03d", LastID);
        LastID++;
        this.Name = Name;
        this.Password = Password;
    }
    public User(String Name, String Password, String Email) {
        this.ID = String.format("U%03d", LastID);
        LastID++;
        this.Name = Name;
        this.Password = Password;
        this.Email = Email;
    }
}
