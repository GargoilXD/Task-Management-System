package Tests;

import Main.models.Projects.HardwareProject;
import Main.models.Projects.Project;
import Main.models.Projects.SoftwareProject;
import Main.utilities.FileUtilities;
import Main.utilities.exceptions.EntityAlreadyExists;
import Main.utilities.exceptions.EntityDoesNotExist;
import org.junit.jupiter.api.*;
import Main.services.ProjectService;

class ProjectServiceTest {
    static ProjectService projectService;

    @BeforeAll
    static void setUp() {
        projectService = new ProjectService(FileUtilities.loadProjects());
    }

    @Test
    void filterProjects() {
        for (Project project : projectService.filterProjects(new ProjectService.FILTER.SOFTWARE())) {
            assert project instanceof SoftwareProject;
        }
        for (Project project : projectService.filterProjects(new ProjectService.FILTER.HARDWARE())) {
            assert project instanceof HardwareProject;
        }
        for (Project project : projectService.filterProjects(new ProjectService.FILTER.BUDGET(100, 10000))) {
            assert project.Budget >= 100 && project.Budget <= 10000;
        }
    }

    @Test
    void findProjectByID() {
        assert projectService.findProjectByID("P002") != null;
    }

    @Test
    void findProjectByName() {
        assert projectService.findProjectByName("IoT Sensor Kit") != null;
    }

    @Test
    void createProject() {
        projectService.createProject("The Marathon Project", "Running long distances", 67, 1000000, false);
        assert projectService.findProjectByName("The Marathon Project") != null;
    }
    @Test
    void createProjectAndCatchException() {
        Assertions.assertThrows(EntityAlreadyExists.class, () -> {
            projectService.createProject("The Jumping Project", "Running jumping distances", 67, 1000000, false);
            projectService.createProject("The Jumping Project", "Running jumping distances", 67, 1000000, false);
        });
    }

    @Test
    void removeProject() {
        projectService.removeProject("P003");
        assert projectService.findProjectByID("P003") == null;
    }
    @Test
    void removeProjectAndCatchException() {
        Assertions.assertThrows(EntityDoesNotExist.class, () -> {
            projectService.removeProject("P004");
            projectService.removeProject("P004");
        });
    }
}