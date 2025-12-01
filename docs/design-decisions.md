# Design Decisions for Task Management System

## Overview
This document outlines the key design decisions made during the development of the Task Management System (TMS). The decisions are categorized into architectural choices, component design, and user interface considerations.

## Architectural Choices

### 1. Layered Architecture
The system is designed using a layered architecture, separating concerns into distinct layers:
- **Presentation Layer**: Handles user interaction through console menus.
- **Service Layer**: Contains business logic and manages operations related to users, tasks, and projects.
- **Model Layer**: Represents the data structures and business entities, such as users, tasks, and projects.

### 2. Use of Interfaces
The `Completable` interface is implemented by classes that require a completion status. This design promotes flexibility and allows for easy extension of functionality in the future.

## Component Design

### 1. User Management
- **AdminUser and RegularUser**: Two types of users are defined to differentiate between administrative and standard functionalities. This design allows for role-based access control, enhancing security and usability.

### 2. Project Management
- **Project Class Hierarchy**: The system distinguishes between hardware and software projects through inheritance. The `Project` class serves as a base class, while `HardwareProject` and `SoftwareProject` extend it to include specific attributes.

### 3. Task Management
- **Task Class**: Each task is associated with a project and has a status indicating its progress. The task management functionality is encapsulated within the `TaskService` class, which handles task-related operations.

## User Interface Design

### 1. Console Menus
- **Modular Menu Design**: The user interface is built using modular console menus (`ConsoleMenu`, `ManualMenu`, `OptionMenu`). This design allows for easy navigation and enhances user experience by providing clear options and prompts.

### 2. Input Validation
- **ValidationUtils**: A dedicated utility class is used for input validation, ensuring that user inputs are checked for correctness before processing. This reduces the likelihood of errors and improves system robustness.
