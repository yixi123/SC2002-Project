package services;

import java.util.*;
import models.BTOProject;
import utils.FileLoader;
import utils.FileSaver;

public class ProjectManager {
    private List<BTOProject> projects;

    public ProjectManager() {
        projects = FileLoader.loadProjects("code/database/ProjectList.csv");
    }

    public List<BTOProject> getProjects() {
        return projects;
    }

    public void createProject(BTOProject project) {
        projects.add(project);
        // Save the project to the database (CSV file)
        FileSaver.saveProjects("code/database/ProjectList.csv", projects);
    }
}
