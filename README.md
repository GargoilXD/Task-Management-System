# Task Management System

## Overview
The Task Management System (TMS) is a Java-based application designed to help users manage projects and tasks efficiently. It provides a user-friendly interface for both admin and regular users to create, update, and track the progress of various projects and their associated tasks.

## Features
- **User Management**: Admin users can create and manage regular users as well as create tasks and projects, while regular users can manage their own tasks.
- **Project Management**: Users can create, update, and delete projects, with support for both software and hardware projects.
- **Task Management**: Users can add, update, and delete tasks associated with projects, as well as view task statuses.
- **Status Reports**: The system generates status reports for projects, providing insights into progress and completion rates.
- **Menu Navigation**: The application features a console-based menu system for easy navigation and user interaction.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 21 or higher

### Installation
1. Clone the repository:
   ```
   git clone https://github.com/yourusername/Task-Management-System.git
   ```
2. Navigate to the project directory:
   ```
   cd Task-Management-System
   ```
3. Build the project using Java:
   ```
   javac Main.Main.java
   ```

### Running the Application
To run the application, execute the following command:
```
java Main.Main
```

## Usage Instructions for Task Management System

## Getting Started
1. **Run the Application**:
    - Compile and run the `Main.Main.java` file. This will start the application and display the login menu.

2. **Login**:
    - Enter your username and password to log in. If you are an admin user, you will have additional privileges.

## Main.Main Features
### User Management
- **Create User**: Admin users can create new users (both admin and regular).
- **Assign Tasks**: Admin users can assign tasks to regular users.

### Project Management
- **Create Project**: Admin users can create new projects (both software and hardware).
- **Browse Projects**: Users can view all projects or filter them by type (software/hardware) or budget.
- **View Project Details**: Users can view information about a specific project, including associated tasks and their statuses.

### Task Management
- **Add Task**: Users can add new tasks to existing projects.
- **Update Task Status**: Users can update the status of tasks (Pending, In Progress, Completed).
- **Remove Task**: Users can remove tasks from projects.

### Status Reports
- **View Status Reports**: Users can generate and view status reports for projects and tasks.

## Common Workflows
### Creating a New User
1. Navigate to the "Manage Users" menu.
2. Select "Create User".
3. Enter the username, password, and email.
4. Specify if the user is an admin or regular user.

### Creating a New Project
1. Navigate to the "Manage Projects" menu.
2. Select "Create Project".
3. Enter the project details, including name, description, team size, and budget.

### Adding a Task to a Project
1. Navigate to the "Manage Tasks" menu.
2. Select "Add New Task".
3. Enter the task name and initial status.
4. Assign the task to the appropriate project.

### Viewing Project Details
1. Navigate to the "Browse Projects" menu.
2. Select a project to view its details, including associated tasks and their statuses.

## Design Decisions
- [Design Decisions](docs/design-decisions.md): Outlines the design choices made during development.

## UML
- [UML](docs/UML%20Diagram.png): Indicates the UML diagram for the project