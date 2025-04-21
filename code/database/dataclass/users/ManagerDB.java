package database.dataclass.users;

import java.util.List;
import models.users.HDBManager;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

/**
 * The ManagerDB class manages the database of HDB managers.
 * It provides methods to initialize, update, retrieve, and add managers.
 * The data is persisted using file operations.
 */
public class ManagerDB {
    private static List<HDBManager> db;
    private static IFileLoader fileLoader = new FileLoader();
    private static IFileSaver fileSaver = new FileSaver();

    /**
     * Initializes the manager database by loading data from a file.
     */
    public static void initiateDB() {
        db = fileLoader.loadManagers();
    }

    /**
     * Saves the current database to a file.
     */
    private static void saveToFile() {
        fileSaver.saveManagers(db);
    }

    /**
     * Updates the database with a new list of managers and reloads the data.
     *
     * @param dataList The list of managers to update the database with.
     */
    public static void updateDB(List<HDBManager> dataList) {
        saveToFile();
        initiateDB();
    }

    /**
     * Updates the current state of the database by saving it to a file.
     */
    public static void updateUser() {
        saveToFile();
    }

    /**
     * Retrieves the entire manager database.
     *
     * @return The list of all managers in the database.
     */
    public static List<HDBManager> getDB() {
        return db;
    }

    /**
     * Retrieves the name of a manager by their NRIC.
     *
     * @param userID The NRIC of the manager.
     * @return The name of the manager, or null if not found.
     */
    public static String getUsernameByID(String userID) {
        for (HDBManager manager : db) {
            if (manager.getNric().equals(userID)) {
                return manager.getName();
            }
        }
        return null;
    }
}
