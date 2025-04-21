package database.dataclass.users;

import java.util.List;
import models.users.Applicant;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

/**
 * The ApplicantDB class manages the database of applicants.
 * It provides methods to initialize, update, retrieve, and add applicants.
 * The data is persisted using file operations.
 */
public class ApplicantDB {
    private static List<Applicant> db;
    private static IFileLoader fileLoader = new FileLoader();
    private static IFileSaver fileSaver = new FileSaver();

    /**
     * Initializes the applicant database by loading data from a file.
     */
    public static void initiateDB() {
        db = fileLoader.loadApplicants();
    }

    /**
     * Saves the current database to a file.
     */
    private static void saveToFile() {
        fileSaver.saveApplicants(db);
    }

    /**
     * Updates the database with a new list of applicants and reloads the data.
     *
     * @param dataList The list of applicants to update the database with.
     */
    public static void updateDB(List<Applicant> dataList) {
        saveToFile();
        initiateDB();
    }

    /**
     * Retrieves the entire applicant database.
     *
     * @return The list of all applicants in the database.
     */
    public static List<Applicant> getDB() {
        return db;
    }

    /**
     * Updates the current state of the database by saving it to a file.
     */
    public static void updateUser() {
        saveToFile();
    }

    /**
     * Retrieves the name of an applicant by their NRIC.
     *
     * @param userID The NRIC of the applicant.
     * @return The name of the applicant, or null if not found.
     */
    public static String getUsernameByID(String userID) {
        for (Applicant applicant : db) {
            if (applicant.getNric().equals(userID)) {
                return applicant.getName();
            }
        }
        return null;
    }

    /**
     * Retrieves an applicant by their NRIC.
     *
     * @param userID The NRIC of the applicant.
     * @return The applicant object, or null if not found.
     */
    public static Applicant getApplicantByID(String userID) {
        for (Applicant applicant : db) {
            if (applicant.getNric().equals(userID)) {
                return applicant;
            }
        }
        return null;
    }

    /**
     * Adds a new applicant to the database and saves it to a file.
     *
     * @param applicant The applicant to be added.
     */
    public static void addUser(Applicant applicant) {
        db.add(applicant);
        saveToFile();
    }
}
