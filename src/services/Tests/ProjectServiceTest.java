package services.Tests;

import models.Projects.HardwareProject;
import models.Projects.Project;
import models.Projects.SoftwareProject;
import org.junit.jupiter.api.*;
import services.ProjectService;

class ProjectServiceTest {
    static ProjectService projectService;
    @BeforeAll
    static void setUp() {
        projectService = new ProjectService(
                new Project[] {
                        new SoftwareProject("Alpha Tracker", "Task tracking app for startups", 5, 15000),
                        new HardwareProject("IoT Sensor Kit", "Sensor prototype for smart devices", 3, 1000),
                        new SoftwareProject("Cloud Backup Tool", "Automated backup solution for SMBs", 4, 12000),
                        new HardwareProject("Smart Thermostat", "Energy-efficient home climate control", 6, 2500),
                        new SoftwareProject("HR Onboarding Portal", "Streamlined employee onboarding system", 6, 18000),
                        new SoftwareProject("EduQuiz Platform", "Interactive quiz app for educators", 4, 9500),
                        new HardwareProject("Solar-Powered Charger", "Portable charger using renewable energy", 5, 1800),
                });
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
    void removeProject() {
        projectService.removeProject("P003");
        assert projectService.findProjectByID("P003") == null;
    }
}