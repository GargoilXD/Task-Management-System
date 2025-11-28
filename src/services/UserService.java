package services;

import models.AdminUser;
import models.User;

// This is the UserService class, responsible for Managing users.
public class UserService {
    static final int MAX_USER_COUNT = 20;
    User[] users = new User[MAX_USER_COUNT];
    // This counts the users
    int userIndex = 0;
    public User current_user = new AdminUser("Kobby", "12345");

    // This function finds users by ID
    public User findUser(String userID) {
        for (int index = 0; index < userIndex; index++) {
            if (users[index].ID.equals(userID)) {
                return users[index];
            }
        }
        return null;
    }
    public void addUser(User user) {
        if (userIndex >= MAX_USER_COUNT) {
            System.out.println("Maximum user count reached");
            return;
        }
        users[userIndex] = user;
        userIndex++;
        System.out.println("User added successfully");
    }

    // This function finds a user that matches the credentials and reports weather there's a match
    public boolean validateCredentials(String username, String password) {
        for (int index = 0; index < userIndex; index++) {
            if (users[index].name.equals(username) && (users[index].password.equals(password))) {
                current_user = users[index];
                return true;
            }
        }
        return false;
    }
}
