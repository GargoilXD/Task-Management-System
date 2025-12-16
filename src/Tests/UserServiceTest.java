package Tests;

import Main.models.Users.AdminUser;
import Main.models.Users.RegularUser;
import Main.models.Users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Main.services.UserService;

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
    @Test
    public void findUserByID() {
        Assertions.assertNotNull(userService.findUserByName("Kobby"));
    }
    @Test
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