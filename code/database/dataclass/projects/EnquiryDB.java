package database.dataclass.projects;

import java.util.ArrayList;
import java.util.List;
import models.projects.Enquiry;
import utils.FileLoader ;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

/**
 * The EnquiryDB class manages the database of enquiries.
 * It provides methods to initialize, update, retrieve, add, and remove enquiries.
 * The data is persisted using file operations.
 */
public class EnquiryDB {

  /** The in-memory database of enquiries. */
  private static List<Enquiry> db; 

  /** The file loader utility for loading enquiries from a file. */
  private static IFileLoader fileLoader = new FileLoader();

  /** The file saver utility for saving enquiries to a file. */
  private static IFileSaver fileSaver = new FileSaver();

  /**
   * Initializes the enquiry database by loading data from a file.
   */
  public static void initiateDB(){
    db = fileLoader.loadEnquiries();
  }

  /**
   * Updates the database with a new list of enquiries and saves it to a file.
   *
   * @param enqList The list of enquiries to update the database with.
   */
  public static void updateDB(List<Enquiry> enqList){
    db = enqList;
    saveToFile();
  }

  /**
   * Saves the current database to a file.
   */
  private static void saveToFile(){
    fileSaver.saveEnquiries(db);
  }

  /**
   * Retrieves the entire enquiry database.
   *
   * @return The list of all enquiries in the database.
   */
  public static List<Enquiry> getDB(){
    return db;
  }

  /**
   * Retrieves all enquiries submitted by a specific user.
   *
   * @param userID The ID of the user whose enquiries are to be retrieved.
   * @return A list of enquiries submitted by the specified user.
   */
  public static List<Enquiry> getEnquiriesByUserID(String userID) {
      List<Enquiry> result = new ArrayList<>();
      for (Enquiry enquiry : db) {
          if (enquiry.getUserID().equalsIgnoreCase(userID)) {
              result.add(enquiry);
          }
      }
      return result;
  }

  /**
   * Retrieves all enquiries related to a specific project.
   *
   * @param project The ID of the project whose enquiries are to be retrieved.
   * @return A list of enquiries related to the specified project.
   */
  public static List<Enquiry> getEnquiriesByProject(String project) {
      List<Enquiry> result = new ArrayList<>();
      for (Enquiry enquiry : db) {
          if (enquiry.getProjectID().equalsIgnoreCase(project)) {
              result.add(enquiry);
          }
      }
      return result;
  }

  /**
   * Adds a new enquiry to the database and saves it to a file.
   *
   * @param enquiry The enquiry to be added.
   */
  public static void addEnquiry(Enquiry enquiry) {
      db.add(enquiry);
      saveToFile();
  }

  /**
   * Removes an enquiry from the database and saves the changes to a file.
   *
   * @param enquiry The enquiry to be removed.
   */
  public static void removeEnquiry(Enquiry enquiry) {
      db.remove(enquiry);
      saveToFile();
  }

  /**
   * Removes an enquiry from the database by its ID and saves the changes to a file.
   *
   * @param enquiryID The ID of the enquiry to be removed.
   */
  public static void removeEnquiryByID(int enquiryID) {
      db.removeIf(enquiry -> enquiry.getId() == enquiryID);
      saveToFile();
  }

  /**
   * Saves the current state of the database to a file.
   */
  public static void updateEnquiry() {
      saveToFile();
  }
}
