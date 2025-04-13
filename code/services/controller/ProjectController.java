package services.controller;

import java.util.*;
import models.BTOProject;
import utils.FileLoader;
import utils.FileSaver;

public class ProjectController  {
    private static List<BTOProject> projects = FileLoader.loadProjects();

    public static List<BTOProject> getProjects() {
        return projects;
    }

    public static void createProject(BTOProject project) {
        projects.add(project);
        FileSaver.saveProjects(projects);
    }

    public static BTOProject getAssignedProjectByOfficer(String officerName) {
        for (BTOProject project : projects) {
            if (project.getOfficers() != null && project.getOfficers().contains(officerName)) {
                // Check if current date is within the opening and closing dates
                Date currentDate = new Date();
                if (currentDate.after(project.getOpeningDate()) && currentDate.before(project.getClosingDate())) {
                    return project; 
                }
            }
        }
        return null; // No project assigned to the officer
    }

    public static
        if (applicant.getProjectApplication() != null) {
            System.out.println("You already applied for a project.");
        } else {
            System.out.println("Available Projects:");

            System.out.print("Enter the project number you want to apply: ");
            int projectChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (projectChoice > 0 && projectChoice <= sortedProjects.size()) {
                BTOProject selectedProject = sortedProjects.get(projectChoice - 1);
                System.out.println("Choose room type:");
                System.out.println("1. 2-Room");
                System.out.println("2. 3-Room");
                System.out.print("Enter your choice (1 or 2): ");
                int roomTypeChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                String roomType = switch (roomTypeChoice) {
                    case 1 -> "2-Room";
                    case 2 -> "3-Room";
                    default -> {
                        System.out.println("Invalid choice. Returning to menu."); yield "";
                    }
                };
                applicant.registerForProject(selectedProject.getProjectName(), roomType);
            } else {
                System.out.println("Invalid choice. Returning to menu.");
            }
        }


}
