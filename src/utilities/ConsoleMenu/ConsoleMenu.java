package utilities.ConsoleMenu;

public abstract class ConsoleMenu {
    public String name;
    public String title;
    boolean root = false;
    public boolean goToRoot = false;

    public ConsoleMenu(String name, String title) {
        this.name = name;
        this.title = title;
    }
    public ConsoleMenu asRoot() {
        root = true;
        return this;
    }
    // This is the abstract display method used by OptionMenu and ManualMenu
    public abstract void display();
}

