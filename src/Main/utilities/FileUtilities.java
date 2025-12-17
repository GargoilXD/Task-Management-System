package Main.utilities;

import Main.interfaces.Completable;
import Main.models.Projects.HardwareProject;
import Main.models.Projects.Project;
import Main.models.Projects.SoftwareProject;
import Main.models.Task;
import Main.models.Users.AdminUser;
import Main.models.Users.RegularUser;
import Main.models.Users.User;

import Main.utilities.exceptions.FileLoadException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class FileUtilities {
    public static Path ProjectDataPath = Paths.get("data/projects_data.json");
    public static Path TaskDataPath = Paths.get("data/tasks_data.json");
    public static Path UserDataPath = Paths.get("data/users_data.json");
    public static ArrayList<Project> loadProjects() throws FileLoadException {
        try {
            String data = Files.readString(ProjectDataPath);
            ArrayList<Project> projects = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(data);
            for (Object object : jsonArray) {
                if (Objects.requireNonNull(object) instanceof JSONObject jsonObject) {
                    Project project;
                    String ID = jsonObject.get("ID").toString();
                    String Name = jsonObject.get("Name").toString();
                    String Description = jsonObject.get("Description").toString();
                    int TeamSize = Integer.parseInt(jsonObject.get("TeamSize").toString());
                    double Budget = Double.parseDouble(jsonObject.get("Budget").toString());
                    if (jsonObject.get("Type").toString().equals("Software")) {
                        project = new SoftwareProject(ID, Name, Description, TeamSize, Budget);
                    } else {
                        project = new HardwareProject(ID, Name, Description, TeamSize, Budget);
                    }
                    projects.add(project);
                } else {
                    throw new ParseException(0);
                }
            }
            return projects;
        } catch (IOException e) {
            throw new FileLoadException("Error loading projects file!");
        } catch (ParseException e) {
            throw new FileLoadException("Projects file corrupted!");
        }
    }
    public static void saveProjects(ArrayList<Project> projects) {
        JSONArray jsonArray = new JSONArray();
        for (Project project : projects) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", project.ID);
            jsonObject.put("Name", project.Name);
            jsonObject.put("Type", project instanceof SoftwareProject? "Software" : "Hardware");
            jsonObject.put("Description", project.Description);
            jsonObject.put("TeamSize", project.TeamSize);
            jsonObject.put("Budget", project.Budget);
            jsonArray.add(jsonObject);
        }
        try {
            Files.writeString(ProjectDataPath, jsonArray.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Task> loadTasks() throws FileLoadException {
        try {
            String data = Files.readString(TaskDataPath);
            ArrayList<Task> tasks = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(data);
            for (Object object : jsonArray) {
                if (Objects.requireNonNull(object) instanceof JSONObject jsonObject) {
                    String ProjectID = jsonObject.get("ProjectID").toString();
                    String ID = jsonObject.get("ID").toString();
                    String Name = jsonObject.get("Name").toString();
                    Completable.STATUS Status = switch (jsonObject.get("Status").toString()) {
                        case "COMPLETED" -> Completable.STATUS.COMPLETED;
                        case "IN_PROGRESS" -> Completable.STATUS.IN_PROGRESS;
                        case "PENDING" -> Completable.STATUS.PENDING;
                        default -> throw new RuntimeException();
                    };
                    tasks.add(new Task(ID, ProjectID, Name, Status));
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new FileLoadException("Error loading tasks file!");
        } catch (ParseException e) {
            throw new FileLoadException("Tasks file corrupted!");
        }
    }
    public static void saveTasks(ArrayList<Task> tasks) {
        JSONArray jsonArray = new JSONArray();
        for (Task task : tasks) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ProjectID", task.ProjectID);
            jsonObject.put("ID", task.ID);
            jsonObject.put("Name", task.Name);
            jsonObject.put("Status", task.Status.toString());
            jsonArray.add(jsonObject);
        }
        try {
            Files.writeString(TaskDataPath, jsonArray.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<User> loadUsers() throws FileLoadException {
        try {
            String data = Files.readString(UserDataPath);
            ArrayList<User> users = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(data);
            for (Object object : jsonArray) {
                if (Objects.requireNonNull(object) instanceof JSONObject jsonObject) {
                    User user;
                    String ID = jsonObject.get("ID").toString();
                    String Name = jsonObject.get("Name").toString();
                    String Password = jsonObject.get("Password").toString();
                    String Email = jsonObject.get("Email").toString();
                    if (jsonObject.get("Type").toString().equals("Admin")) {
                        user = new AdminUser(ID, Name, Password, Email);
                    } else {
                        JSONArray jsonTaskArray = ((JSONArray) jsonObject.get("AssignedTasks"));
                        String[] tasks = new  String[jsonTaskArray.size()];
                        for (int index = 0; index < jsonTaskArray.size(); index++) {
                            tasks[index] = jsonTaskArray.get(index).toString();
                        }
                        user = new RegularUser(ID, Name, Password, Email, tasks);
                    }
                    users.add(user);
                }
            }
            return users;
        } catch (IOException e) {
            throw new FileLoadException("Error loading users file!");
        } catch (ParseException e) {
            throw new FileLoadException("Users file corrupted!");
        }
    }
    public static void saveUsers(ArrayList<User> users) {
        JSONArray jsonArray = new JSONArray();
        for (User user : users) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", user.ID);
            jsonObject.put("Name", user.Name);
            jsonObject.put("Password", user.Password);
            jsonObject.put("Email", user.Email);
            if (user instanceof RegularUser regularUser) {
                jsonObject.put("Type", "Regular");
                jsonObject.put("AssignedTasks", regularUser.getAssignedTasks());
            } else {
                jsonObject.put("Type", "Admin");
            }
            jsonArray.add(jsonObject);
        }
        try {
            Files.writeString(UserDataPath, jsonArray.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

