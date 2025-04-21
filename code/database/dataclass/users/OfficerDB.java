package database.dataclass.users;

import java.util.List;

import models.users.HDBOfficer;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

/**
 * The OfficerDB class manages the database of HDB officers.
 * It provides methods to initialize, update, retrieve, and add officers.
 * The data is persisted using file operations.
 */
public class OfficerDB {
    private static List<HDBOfficer> db;
    private static IFileLoader fileLoader = new FileLoader();
    private static IFileSaver fileSaver = new FileSaver();

    /**
     * Initializes the officer database by loading data from a file.
     */
    public static void initiateDB() {
        db = fileLoader.loadOfficers();
    }

    /**
     * Saves the current database to a file.
     */
    private static void saveToFile() {
        fileSaver.saveOfficers(db);
    }

    /**
     * Updates the database with a new list of officers and saves it to a file.
     *
     * @param dataList The list of officers to update the database with.
     */
    public static void updateDB(List<HDBOfficer> dataList) {
        db = dataList;
        saveToFile();
    }

    /**
     * Retrieves the entire officer database.
     *
     * @return The list of all officers in the database.
     */
    public static List<HDBOfficer> getDB() {
        return db;
    }

    /**
     * Retrieves the name of an officer by their NRIC.
     *
     * @param userID The NRIC of the officer.
     * @return The name of the officer, or null if not found.
     */
    public static String getUsernameByID(String userID) {
        for (HDBOfficer officer : db) {
            if (officer.getNric().equals(userID)) {
                return officer.getName();
            }
        }
        return null;
    }

    /**
     * Updates the current state of the database by saving it to a file.
     */
    public static void updateUser() {
        saveToFile();
    }

    /**
     * Adds a new officer to the database.
     *
     * @param user The officer to be added.
     */
    public static void addUser(HDBOfficer user) {
        db.add(user);
    }
}
