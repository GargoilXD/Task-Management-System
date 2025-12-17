package Tests;

import Main.models.Users.AdminUser;
import Main.utilities.FileUtilities;
import Main.utilities.exceptions.EntityAlreadyExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Main.services.UserService;

import java.util.NoSuchElementException;

class UserServiceTest {
    static UserService userService;

    @BeforeAll
    static void setUp() {
        userService = new UserService(FileUtilities.loadUsers());
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
    void addUser() {
        userService.addUser(new AdminUser("Kwame", "12345"));
        Assertions.assertNotNull(userService.findUserByName("Kwame"));
    }

    @Test
    void addUserAndCatchException() {
        Assertions.assertThrows(EntityAlreadyExists.class, () -> {
            userService.addUser(new AdminUser("Kwame", "12345"));
            userService.addUser(new AdminUser("Kwame", "12345"));
        });
    }

    @Test
    void removeUser() {
        userService.removeUser("U001");
        Assertions.assertNull(userService.findUserByID("U001"));
    }

    @Test
    void removeUserAndCatchException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.removeUser("U002");
            userService.removeUser("U002");
        });
    }
}