package Main;

import Main.services.ProjectService;
import Main.services.ReportService;
import Main.services.TaskService;
import Main.services.UserService;
import static Main.services.ConcurrencyService.initializeServices;
import static Main.services.ConcurrencyService.uninitializeServices;
import Main.utilities.ConsoleMenu.*;
import Main.utilities.Validator;


import java.util.Scanner;

public class Main {
    public static UserService userService;
    public static ProjectService projectService;
    public static TaskService taskService;
    public static ReportService reportService;
    public static void main(String[] args) {
        initializeServices();
        Validator.input = new Scanner(System.in);
        Menus.getLoginMenu().asRoot().display();
        Validator.input.close();
        uninitializeServices();
    }
}