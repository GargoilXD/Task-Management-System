package services;

import models.Users.User;
import utilities.KArray;

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
        if (findUserByName(user.Name) != null) {
            System.err.println("User with name " + user.Name + " already exists.");
            return;
        };
        users.add(user);
    }
    public void removeUser(String UserID) {
        User found = findUserByID(UserID);
        if (found == null) {
            System.err.println("User with ID " + UserID + " not found.");
            return;
        }
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
