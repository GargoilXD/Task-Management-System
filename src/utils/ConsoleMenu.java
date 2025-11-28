package utils;

public abstract class ConsoleMenu {
    public String name;
    public String title;

    public ConsoleMenu(String name, String title) {
        this.name = name;
        this.title = title;
    }
    public void display() {}
}

