package services;

import java.util.*;
import models.BTOProject;
import utils.FileLoader;

public class ProjectManager {
    private List<BTOProject> projects;

    public ProjectManager() {
        projects = FileLoader.loadProjects("code/database/ProjectList.csv");
    }

    public List<BTOProject> getProjects() {
        return projects;
    }

    public void createProject(BTOProject project) {
        // Create a new project
    }
}
