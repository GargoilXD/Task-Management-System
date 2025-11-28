package utils;

public abstract class ConsoleMenu {
    String name;
    String title;

    public ConsoleMenu(String name, String title) {
        this.name = name;
        this.title = title;
    }
    public void display() {}
}

