package models.Users;

import utilities.KArray;

// This is the class for RegularUsers
public class RegularUser extends User {
    public KArray<String> assignedTasks = new KArray<>(String.class);

    public RegularUser(String name, String password, String email) { super(name, password, email); }
    public RegularUser(String name, String password, String[] assignedTasks) {
        super(name, password);
        for (String taskID : assignedTasks) {
            this.assignedTasks.add(taskID);
        }
    }
    public boolean isAssignedTask(String taskID) {
        return assignedTasks.contains(taskID);
    }
    public void assign(String assignedTask) {
        assignedTasks.add(assignedTask);
    }
    public void unassign(String assignedTask) {
        assignedTasks.removeElement(assignedTask);
    }
    public String[] getAssignedTasks() {
        return assignedTasks.toArray();
    }
}
