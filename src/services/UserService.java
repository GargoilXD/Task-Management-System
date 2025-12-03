package services;

import models.Users.User;
import utilities.KArray;

public class UserService {
    KArray<User> users = new KArray<User>();
    public User currentUser;

    public UserService(User[] users) {
        for (User user : users) {
            this.users.add(user);
        }
    }
    public boolean userExists(User user) {
        return users.contains(user);
    }
    public User getUser(String UserID) {
        return users.customFind((user) -> user.ID.equals(UserID));
    }
    public User[] getUsers() {
        return users.toArray();
    }
    public void addUser(User user) {
        users.add(user);
    }
    public void removeUser(User user) {
        users.removeElement(users.findElement(user));
    }
    public boolean validateCredentials(String username, String password) {
        return users.customFind(
            (user) -> {
                if (user.name.equals(username) && (user.password.equals(password))) {
                    currentUser = user;
                    return true;
                }
                return false;
            }
        ) != null;
    }
}
