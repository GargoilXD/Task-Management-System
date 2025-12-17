package Main.services;

import Main.models.Users.User;
import Main.utilities.exceptions.EntityAlreadyExists;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UserService {
    ArrayList<User> users;
    public User currentUser;

    public UserService(ArrayList<User> users) {
        this.users = users;
    }
    public User findUserByID(String ID) {
        return users.stream().filter((user -> user.ID.equals(ID))).findFirst().orElse(null);
    }
    public User findUserByName(String Name) {
        return users.stream().filter((user -> user.Name.equalsIgnoreCase(Name))).findFirst().orElse(null);
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public void addUser(User user) {
        if (findUserByName(user.Name) != null) throw new EntityAlreadyExists("User already exists");
        users.add(user);
    }
    public void removeUser(String UserID) {
        User found = findUserByID(UserID);
        if (found == null) throw new NoSuchElementException("User with ID " + UserID + " does not exist");
        users.remove(found);
    }
    public boolean validateCredentials(String username, String password) {
        return users.stream().anyMatch((user -> {
            if (user.Name.equals(username) && user.Password.equals(password)) {
                currentUser = user;
                return true;
            }
            return false;
        }));
    }
}
