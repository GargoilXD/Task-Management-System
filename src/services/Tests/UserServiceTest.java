package services.Tests;

import models.Users.AdminUser;
import models.Users.RegularUser;
import models.Users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.UserService;

class UserServiceTest {
    static UserService userService;

    @BeforeAll
    static void setUp() {
        userService = new UserService(
                new User[] {
                        new AdminUser("Kobby", "12345"),
                        new AdminUser("Ama", "12345"),
                        new RegularUser("Kofi", "12345", new String[] {"T001", "T002", "T003"})
                }
        );
    }
    public void findUserByID() {
        Assertions.assertNotNull(userService.findUserByName("Kobby"));
    }
    public void findUserByName() {
        Assertions.assertNotNull(userService.findUserByID("U001"));
    }
    @Test
    void getUsers() {
        Assertions.assertEquals(3, userService.getUsers().length);
    }

    @Test
    void addUser() {
        userService.addUser(new AdminUser("Kwame", "12345"));
        Assertions.assertNotNull(userService.findUserByName("Kwame"));
    }

    @Test
    void removeUser() {
        userService.removeUser("U001");
        Assertions.assertNull(userService.findUserByID("U001"));
    }
}