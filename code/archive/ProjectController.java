package archive;

import java.util.*;

import models.projects.BTOProject;
import models.projects.ProjectApplication;
import services.subservices.ProjectApplicationService;
import utils.FileLoader;
import utils.FileSaver;

public class ProjectController{
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

    public static void applyProject(Scanner scanner){

    }

    public void registerForProject(String projectName, String roomType) {

        // if (ProjectController.getProjects().stream().anyMatch(p -> p.getProjectName().equals(projectName))) {
        //     projectApplication = new ProjectApplication(this.name, projectName, "Pending", new Date(), roomType);
        //     ProjectApplicationService.addApplication(projectApplication);
        //     System.out.println("Registration submitted for project: " + projectName);
        // } else {
        //     System.out.println("Project not found.");
        // }
    }

    

    // public static void viewProject(List<>){

    // }
}
