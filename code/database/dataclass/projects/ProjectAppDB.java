package database.dataclass.projects;

import java.util.ArrayList;
import java.util.List;
import models.projects.ProjectApplication;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

/**
 * The ProjectAppDB class manages the database of project applications.
 * It provides methods to initialize, update, retrieve, add, and remove project applications.
 * The data is persisted using file operations.
 */
public class ProjectAppDB {
    private static List<ProjectApplication> db;
    private static IFileLoader fileLoader = new FileLoader();
    private static IFileSaver fileSaver = new FileSaver();

    /**
     * Initializes the project application database by loading data from a file.
     */
    public static void initiateDB() {
        db = fileLoader.loadProjectApplications();
    }

    /**
     * Saves the current database to a file.
     */
    private static void saveToFile() {
        fileSaver.saveProjectApplications(db);
    }

    /**
     * Updates the database with a new list of project applications and saves it to a file.
     *
     * @param dataList The list of project applications to update the database with.
     */
    public static void updateDB(List<ProjectApplication> dataList) {
        db = dataList;
        saveToFile();
    }

    /**
     * Updates the current state of the database by saving it to a file.
     */
    public static void updateDB() {
        saveToFile();
    }

    /**
     * Retrieves all project applications submitted by a specific user.
     *
     * @param nric The NRIC of the user whose applications are to be retrieved.
     * @return A list of project applications submitted by the specified user.
     */
    public static List<ProjectApplication> getApplicationByUser(String nric) {
        List<ProjectApplication> result = new ArrayList<>();
        for (ProjectApplication application : db) {
            if (application.getUser().equalsIgnoreCase(nric)) {
                result.add(application);
            }
        }
        return result;
    }

    /**
     * Retrieves all project applications related to a specific project.
     *
     * @param project The name of the project whose applications are to be retrieved.
     * @return A list of project applications related to the specified project.
     */
    public static List<ProjectApplication> getApplicationsByProject(String project) {
        List<ProjectApplication> result = new ArrayList<>();
        for (ProjectApplication application : db) {
            if (application.getProjectName().equalsIgnoreCase(project)) {
                result.add(application);
            }
        }
        return result;
    }

    /**
     * Retrieves a project application submitted by a specific user for a specific project.
     *
     * @param userID The NRIC of the user.
     * @param projectName The name of the project.
     * @return The project application object, or null if not found.
     */
    public static ProjectApplication getApplicationsByUserAndProject(String userID, String projectName) {
        for (ProjectApplication application : db) {
            if (application.getUser().equals(userID) && application.getProjectName().equals(projectName)) {
                return application;
            }
        }
        return null;
    }

    /**
     * Adds a new project application to the database and saves it to a file.
     *
     * @param application The project application to be added.
     */
    public static void addApplication(ProjectApplication application) {
        db.add(application);
        saveToFile();
    }

    /**
     * Removes a project application from the database and saves the changes to a file.
     *
     * @param application The project application to be removed.
     */
    public static void removeApplication(ProjectApplication application) {
        db.remove(application);
        saveToFile();
    }

    /**
     * Removes all project applications related to a specific project and saves the changes to a file.
     *
     * @param projectName The name of the project whose applications are to be removed.
     */
    public static void removeApplicationByProject(String projectName) {
        db.removeIf(application -> application.getProjectName().equalsIgnoreCase(projectName));
        saveToFile();
    }

    /**
     * Updates the current state of the database by saving it to a file.
     */
    public static void updateApplication() {
        saveToFile();
    }

    /**
     * Retrieves the entire project application database.
     *
     * @return The list of all project applications in the database.
     */
    public static List<ProjectApplication> getDB() {
        return db;
    }
}
