package utils;

import java.util.Scanner;

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
