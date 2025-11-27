package utils;

import java.util.function.Supplier;

public class ManualMenu extends ConsoleMenu {
    Runnable getInput;
    public ManualMenu(String name, String title, Supplier<String> information, Runnable getInput) {
        super(name, title, information);
        this.getInput = getInput;
    }
    public ManualMenu(String name, String title, Runnable getInput) {
        super(name, title);
        this.getInput = getInput;
    }

    @Override
    public void display() {
        getInput.run();
    }
}
