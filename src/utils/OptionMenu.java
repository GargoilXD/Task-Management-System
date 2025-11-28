package utils;

import java.util.Scanner;

// This class Inherits from the ConsoleMenu Class.
// It displays menu options for the user to choose from
public class OptionMenu extends ConsoleMenu {
    String optionsTitle;
    ConsoleMenu[] subMenus;
    String backTitle;
    String choiceMessage;
    Scanner scanner;

    public OptionMenu(String name, String title, String optionsTitle, ConsoleMenu[] subMenus, String backTitle, String choiceMessage, Scanner scanner) {
        super(name, title);
        this.optionsTitle = optionsTitle;
        this.subMenus = subMenus;
        this.backTitle = backTitle;
        this.choiceMessage = choiceMessage;
        this.scanner = scanner;
    }
    // This Displays the Menu
    /* Example:
        =====================================
                       TITLE
        =====================================

        Options Title
        ---------
        1. Manage Projects
        2. Manage Tasks
        3. View Status Reports
        4. Switch User
        5. Exit
        choiceMessage:
    */
    @Override
    public void display() {
        System.out.println(title);
        while (true) {
            System.out.println(optionsTitle);
            int index = 0;
            for (; index < subMenus.length; index++) {
                System.out.printf("%s. %s%n", index + 1, subMenus[index].name);
            }
            System.out.printf("%s. %s%n", index + 1, backTitle);
            int choice = getChoice(subMenus.length + 1) - 1;
            if (choice == index) {
                return;
            } else {
                subMenus[choice].display();
            }
        }
    }
    // The function repeatedly prompts the user until they give a valid choice
    int getChoice(int range) {
        while (true) {
            System.out.println(choiceMessage);
            try {
                int choice = scanner.nextInt();
                if (choice <= 0 || choice > range) {
                    System.out.println("Invalid choice.");
                } else {
                    scanner.nextLine();
                    return choice;
                }
            } catch (Exception e) {
                System.out.println("Expected number");
                scanner.nextLine();
            }

        }
    }
}
