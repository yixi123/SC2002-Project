package services;

import java.util.*;
import models.BTOProject;
import utils.FileLoader;
import utils.FileSaver;

public class ProjectController {
    private static List<BTOProject> projects = FileLoader.loadProjects("code/database/ProjectList.csv");

    public List<BTOProject> getProjects() {
        return projects;
    }

    public void createProject(BTOProject project) {
        projects.add(project);
        // Save the project to the database (CSV file)
        FileSaver.saveProjects("code/database/ProjectList.csv", projects);
    }

    public BTOProject getAssignedProjectByOfficer(String officerName) {
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


}
