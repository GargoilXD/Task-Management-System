package Main.models.Users;

import java.util.ArrayList;
import java.util.Arrays;

// This is the class for RegularUsers
public class RegularUser extends User {
    public ArrayList<String> assignedTasks = new ArrayList<>();

    public RegularUser(String name, String password, String email) { super(name, password, email); }
    public RegularUser(String name, String password, String[] assignedTasks) {
        super(name, password);
        this.assignedTasks.addAll(Arrays.asList(assignedTasks));
    }
    public RegularUser(String ID, String name, String password, String Email, String[] assignedTasks) {
        super(ID, name, password, Email);
        this.assignedTasks.addAll(Arrays.asList(assignedTasks));
    }
    public boolean isAssignedTask(String taskID) {
        return assignedTasks.contains(taskID);
    }
    public void assign(String assignedTask) {
        assignedTasks.add(assignedTask);
    }
    public void unassign(String assignedTask) {
        assignedTasks.remove(assignedTask);
    }
    public ArrayList<String> getAssignedTasks() {
        return assignedTasks;
    }
}
