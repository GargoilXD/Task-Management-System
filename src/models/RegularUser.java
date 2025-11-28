package models;

// This is the class for RegularUsers
public class RegularUser extends User {
    static final int MAX_TASK_COUNT = 10;
    public String [] assignedTasks = new String[10];
    int assignedTasksIndex = 0;
    public RegularUser(String name, String password, String email) {
        super(name, password, email);
    }
    public RegularUser(String name, String password) {
        super(name, password);
    }
    public void assign(String assignedTask) {
        if (assignedTasksIndex >= MAX_TASK_COUNT) {
            System.out.println("User can't assign anymore");
            return;
        }
        assignedTasks[assignedTasksIndex] = assignedTask;
        assignedTasksIndex++;
        System.out.println("User assigned.");
    }
}
