package utils;

// This is the abstract ConsoleMenu class which is the base class for OptionMenu and ManualMenu
public abstract class ConsoleMenu {
    public String name;
    public String title;

    public ConsoleMenu(String name, String title) {
        this.name = name;
        this.title = title;
    }
    // This is the abstract display method used by OptionMenu and ManualMenu
    public abstract void display();
}

