package database.dataclass.projects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import models.projects.BTOProject;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

/**
 * The ProjectDB class manages the database of BTO projects.
 * It provides methods to initialize, update, retrieve, add, and remove projects.
 * The data is persisted using file operations.
 */
public class ProjectDB {
    private static List<BTOProject> db;
    private static IFileLoader fileLoader = new FileLoader();
    private static IFileSaver fileSaver = new FileSaver();

    /**
     * Initializes the project database by loading data from a file.
     */
    public static void initiateDB() {
        db = fileLoader.loadProjects();
    }

    /**
     * Saves the current database to a file.
     */
    private static void saveToFile() {
        fileSaver.saveProjects(db);
    }

    /**
     * Updates the database with a new list of projects and reloads the data.
     *
     * @param dataList The list of projects to update the database with.
     */
    public static void updateDB(List<BTOProject> dataList) {
        saveToFile();
        initiateDB();
    }

    /**
     * Updates the current state of the database by saving it to a file.
     */
    public static void updateDB() {
        saveToFile();
        initiateDB();
    }

    /**
     * Retrieves the entire project database.
     *
     * @return The list of all projects in the database.
     */
    public static List<BTOProject> getDB() {
        return db;
    }

    /**
     * Retrieves a project by its name.
     *
     * @param projectName The name of the project.
     * @return The project object, or null if not found.
     */
    public static BTOProject getProjectByName(String projectName) {
        for (BTOProject project : db) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }

    /**
     * Updates the current state of the database by saving it to a file.
     */
    public static void updateProject() {
        saveToFile();
    }

    /**
     * Adds a new project to the database and saves it to a file.
     *
     * @param project The project to be added.
     */
    public static void addProject(BTOProject project) {
        db.add(project);
        saveToFile();
    }

    /**
     * Removes a project from the database and saves the changes to a file.
     *
     * @param project The project to be removed.
     */
    public static void removeProject(BTOProject project) {
        Iterator<BTOProject> it = db.iterator();
        BTOProject temp;
        while (it.hasNext()) {
            temp = it.next();
            if (temp.getProjectName().equals(project.getProjectName())) {
                it.remove();
                ProjectAppDB.removeApplicationByProject(project.getProjectName());
                OfficerAppDB.removeApplicationByProject(project.getProjectName());
            }
        }
        saveToFile();
    }

    /**
     * Retrieves all projects assigned to a specific officer.
     *
     * @param officerId The NRIC of the officer.
     * @return A list of projects assigned to the officer.
     */
    public static List<BTOProject> getProjectsByOfficer(String officerId) {
        List<BTOProject> projects = new ArrayList<>();
        for (BTOProject project : db) {
            if (project.getOfficers() != null && project.getOfficers().contains(officerId)) {
                projects.add(project);
            }
        }
        return projects;
    }

    /**
     * Retrieves all projects managed by a specific manager.
     *
     * @param managerId The NRIC of the manager.
     * @return A list of projects managed by the manager.
     */
    public static List<BTOProject> getProjectsByManager(String managerId) {
        List<BTOProject> projects = new ArrayList<>();
        for (BTOProject project : db) {
            if (project.getManagerID() != null && project.getManagerID().equals(managerId)) {
                projects.add(project);
            }
        }
        return projects;
    }
}
