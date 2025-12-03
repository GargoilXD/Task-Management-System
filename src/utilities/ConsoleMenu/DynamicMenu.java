package utilities.ConsoleMenu;

public class DynamicMenu extends ConsoleMenu {
    Runnable runnable;
    public DynamicMenu(String name, String title, Runnable runnable) {
        super(name, title);
        this.runnable = runnable;
    }
    @Override
    public void display() {
        System.out.println(this.title);
        runnable.run();
    }
}
