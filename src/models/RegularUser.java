package models;

public class RegularUser extends User {
    public String [] assignedTasks = new String[10];
    int assignedTasksIndex = 0;
    public RegularUser(String name, String password, String email) {
        super(name, password, email);
    }
    public RegularUser(String name, String password) {
        super(name, password);
    }
    public void assign(String assignedTask) {
        assignedTasks[assignedTasksIndex] = assignedTask;
        assignedTasksIndex++;
    }
}
