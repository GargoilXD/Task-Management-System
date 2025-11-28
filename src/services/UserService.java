package services;

import models.AdminUser;
import models.User;

public class UserService {
    User[] users = new User[20];
    int userIndex = 0;
    public User current_user = new AdminUser("Kobby", "12345");

    public User findUser(String userID) {
        for (int index = 0; index < userIndex; index++) {
            if (users[index].ID.equals(userID)) {
                return users[index];
            }
        }
        return null;
    }
    public void addUser(User user) {
        users[userIndex] = user;
        userIndex++;
    }

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
