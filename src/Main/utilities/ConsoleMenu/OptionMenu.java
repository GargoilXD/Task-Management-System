package Main.utilities.ConsoleMenu;

import Main.utilities.Validator;

public class OptionMenu extends ConsoleMenu {
    final String optionsTitle;
    final ConsoleMenu[] subMenus;
    final String backTitle;
    final String choiceMessage;

    public OptionMenu(String name, String title, String optionsTitle, ConsoleMenu[] subMenus, String backTitle, String choiceMessage) {
        super(name, title);
        this.optionsTitle = optionsTitle;
        this.subMenus = subMenus;
        this.backTitle = backTitle;
        this.choiceMessage = choiceMessage;
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
            System.out.println(choiceMessage);
            int choice = Validator.getValidInteger(1, subMenus.length + 1) - 1;
            if (choice == index) {
                return;
            } else {
                subMenus[choice].display();
                if (subMenus[choice].goToRoot && !root) {
                    goToRoot = true;
                    return;
                }
            }
        }
    }
}
