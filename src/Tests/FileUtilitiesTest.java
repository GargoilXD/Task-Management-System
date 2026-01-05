package Tests;

import Main.interfaces.Completable;
import Main.models.Projects.HardwareProject;
import Main.models.Projects.Project;
import Main.models.Task;
import Main.models.Users.AdminUser;
import Main.models.Users.User;
import Main.utilities.FileUtilities;
import Main.utilities.exceptions.FileLoadException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class FileUtilitiesTest {

    @Test
    void loadValidProjects() throws IOException {
        FileUtilities.ProjectDataPath = Paths.get("data/test.json");
        try {
            Files.writeString(FileUtilities.ProjectDataPath, "[{\"Type\":\"Software\",\"Description\":\"Streamlined employee onboarding system\",\"Budget\":18000.0,\"ID\":\"P005\",\"TeamSize\":6,\"Name\":\"HR Onboarding Portal\"}]");
            FileUtilities.loadProjects();
        } finally {
            Files.deleteIfExists(FileUtilities.ProjectDataPath);
        }
    }
    @Test
    void loadInvalidProjects() {
        Assertions.assertThrows(FileLoadException.class, () -> {
            FileUtilities.ProjectDataPath = Paths.get("data/test.json");
            try {
                Files.writeString(FileUtilities.ProjectDataPath, "[{\"Typ\":\"Middleware\",\"Description\":\"Streamlined employee onboarding system\",\"Budget\":18000.0,\"ID\":\"P005\",\"TeamSize\":6,\"Name\":\"HR Onboarding Portal\"}]");
                FileUtilities.loadProjects();
            } finally {
                Files.deleteIfExists(FileUtilities.ProjectDataPath);
            }
        });
    }

    @Test
    void saveProjects() throws IOException {
        FileUtilities.ProjectDataPath = Paths.get("data/test.json");
        try {
            ArrayList<Project> projects = new ArrayList<>();
            projects.add(new HardwareProject("Walk", "Just Walking", 6, 5000));
            FileUtilities.saveProjects(projects);
            String data = Files.readString(FileUtilities.ProjectDataPath);
            Assertions.assertEquals("[{\"Type\":\"Hardware\",\"Description\":\"Just Walking\",\"Budget\":5000.0,\"ID\":\"P001\",\"TeamSize\":6,\"Name\":\"Walk\"}]", data);
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            Files.deleteIfExists(FileUtilities.ProjectDataPath);
        }
    }

    @Test
    void loadValidTasks() throws IOException {
        FileUtilities.TaskDataPath = Paths.get("data/test.json");
        try {
            Files.writeString(FileUtilities.TaskDataPath, "[{\"Status\":\"COMPLETED\",\"ProjectID\":\"P001\",\"ID\":\"T001\",\"Name\":\"Design Database\"}]");
            FileUtilities.loadTasks();
        } finally {
            Files.deleteIfExists(FileUtilities.TaskDataPath);
        }
    }

    @Test
    void loadInvalidTasks() {
        Assertions.assertThrows(FileLoadException.class, () -> {
            FileUtilities.TaskDataPath = Paths.get("data/test.json");
            try {
                Files.writeString(FileUtilities.TaskDataPath, "[{\"Status\":\"COMPLETE\",\"ProjectID\":\"P001\",\"ID\":\"T001\",\"Name\":\"Design Database\"}]");
                FileUtilities.loadTasks();
            } finally {
                Files.deleteIfExists(FileUtilities.TaskDataPath);
            }
        });
    }

    @Test
    void saveTasks() throws IOException {
        FileUtilities.TaskDataPath = Paths.get("data/test.json");
        try {
            ArrayList<Task> tasks = new ArrayList<>();
            tasks.add(new Task("P001", "Jump", Completable.STATUS.COMPLETED));
            FileUtilities.saveTasks(tasks);
            String data = Files.readString(FileUtilities.TaskDataPath);
            Assertions.assertEquals("[{\"Status\":\"COMPLETED\",\"ProjectID\":\"P001\",\"ID\":\"T001\",\"Name\":\"Jump\"}]", data);
        } finally {
            Files.deleteIfExists(FileUtilities.TaskDataPath);
        }
    }

    @Test
    void loadValidUsers() throws IOException {
        FileUtilities.UserDataPath = Paths.get("data/test.json");
        try {
            Files.writeString(FileUtilities.UserDataPath, "[{\"Type\":\"Admin\",\"Email\":\"\",\"ID\":\"U001\",\"Name\":\"Kobby\",\"Password\":\"12345\"}]");
            FileUtilities.loadUsers();
        } finally {
            Files.deleteIfExists(FileUtilities.UserDataPath);
        }
    }

    @Test
    void loadInvalidUsers() {
        Assertions.assertThrows(FileLoadException.class, () -> {
            FileUtilities.UserDataPath = Paths.get("data/test.json");
            try {
                Files.writeString(FileUtilities.UserDataPath, "[{\"Type\":\"Admn\",\"Email\":\"\",\"ID\":\"U001\",\"Name\":\"Rose\",\"Password\":\"12345\"}]");
                FileUtilities.loadUsers();
            } finally {
                Files.deleteIfExists(FileUtilities.UserDataPath);
            }
        });
    }

    @Test
    void saveUsers() throws IOException {
        FileUtilities.UserDataPath = Paths.get("data/test.json");
        try {
            ArrayList<User> users = new ArrayList<>();
            users.add(new AdminUser("Rose", "12345"));
            FileUtilities.saveUsers(users);
            String data = Files.readString(FileUtilities.UserDataPath);
            Assertions.assertEquals("[{\"Type\":\"Admin\",\"Email\":\"\",\"ID\":\"U001\",\"Name\":\"Rose\",\"Password\":\"12345\"}]", data);
        } finally {
            Files.deleteIfExists(FileUtilities.UserDataPath);
        }
    }
}