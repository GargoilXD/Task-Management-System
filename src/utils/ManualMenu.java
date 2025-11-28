package utils;

public class ManualMenu extends ConsoleMenu {
    Runnable getInput;
    public ManualMenu(String name, String title, Runnable getInput) {
        super(name, title);
        this.getInput = getInput;
    }

    @Override
    public void display() {
        System.out.println(this.title);
        getInput.run();
    }
}
