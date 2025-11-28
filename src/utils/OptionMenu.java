package utils;

import java.util.function.Supplier;

public class OptionMenu extends ConsoleMenu {
    Supplier<String> information;
    String optionsTitle;
    ConsoleMenu[] subMenus;
    String backTitle;
    String choiceMessage;
    Supplier<Integer> Choice;

    public OptionMenu(String name, String title, Supplier<String> information, String optionsTitle, ConsoleMenu[] subMenus, String backTitle, String choiceMessage, Supplier<Integer> Choice) {
        super(name, title);
        this.information = information;
        this.optionsTitle = optionsTitle;
        this.subMenus = subMenus;
        this.backTitle = backTitle;
        this.choiceMessage = choiceMessage;
        this.Choice = Choice;
    }
    public OptionMenu(String name, String title, String optionsTitle, ConsoleMenu[] subMenus, String backTitle, String choiceMessage, Supplier<Integer> Choice) {
        super(name, title);
        this.information = () -> "";
        this.optionsTitle = optionsTitle;
        this.subMenus = subMenus;
        this.backTitle = backTitle;
        this.choiceMessage = choiceMessage;
        this.Choice = Choice;
    }
    @Override
    public void display() {
        IO.println(title);
        IO.println(information.get());
        while (true) {
            IO.println(optionsTitle);
            int index = 0;
            for (; index < subMenus.length; index++) {
                IO.println(String.format("%s. %s", index + 1, subMenus[index].name));
            }
            IO.println(String.format("%s. %s", index + 1, backTitle));
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
            IO.println(choiceMessage);
            int choice = Choice.get();
            if (choice <= 0 || choice > range) {
                IO.println("Invalid choice.");
            } else {
                return choice;
            }
        }
    }
}
