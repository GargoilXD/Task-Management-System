package services;

import models.Users.User;
import services.exceptions.EntityAlreadyExists;
import utilities.KArray;

import java.util.NoSuchElementException;

public class UserService {
    KArray<User> users = new KArray<User>(User.class);
    public User currentUser;

    public UserService(User[] users) {
        for (User user : users) {
            this.users.add(user);
        }
    }
    public User findUserByID(String ID) {
        return users.customFind((user) -> user.ID.equals(ID));
    }
    public User findUserByName(String Name) {
        return users.customFind((user) -> user.Name.equals(Name));
    }
    public User[] getUsers() {
        return users.toArray();
    }
    public void addUser(User user) {
        if (findUserByName(user.Name) != null) throw new EntityAlreadyExists("User already exists");
        users.add(user);
    }
    public void removeUser(String UserID) {
        User found = findUserByID(UserID);
        if (found == null) throw new NoSuchElementException("User with ID " + UserID + " does not exist");
        users.removeElement(found);
    }
    public boolean validateCredentials(String username, String password) {
        return users.customFind(
            (user) -> {
                if (user.Name.equals(username) && (user.Password.equals(password))) {
                    currentUser = user;
                    return true;
                }
                return false;
            }
        ) != null;
    }
}
