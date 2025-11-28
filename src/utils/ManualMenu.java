package utils;

// This class was created to handle Menus with unique needs, so as to not need to create a new class for each need.
/* Usage:
    new ManualMenu(
            "Menu Name",
            """
                    ==============================
                                 TITLE       \s
                    ==============================""",
            () -> {
            // Same code from Line 199 in getLoginMenu in Main
                String username;
                String password;
                int tries = 5;
                while (true) {
                    if (tries == 0) {
                        System.out.println("Login Failed. Please try again later.");
                        if (main) {
                            System.exit(0);
                        } else {
                            return;
                        }
                    }
                    System.out.println("Username: ");
                    username = scanner.nextLine();
                    System.out.println("Enter Password: ");
                    password = scanner.nextLine();
                    if (userService.validateCredentials(username, password)) {
                        getMainMenu(scanner).display();
                        System.exit(0);
                    }
                    tries--;
                    System.out.printf("Wrong username or password. %s Tries left %n", tries);
                }
            }
    );
*/
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
