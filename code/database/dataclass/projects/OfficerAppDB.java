package database.dataclass.projects;

import models.enums.OfficerAppStat;
import models.projects.OfficerApplication;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

import java.util.ArrayList;
import java.util.List;

/**
 * The OfficerAppDB class manages the database of officer applications.
 * It provides methods to initialize, update, retrieve, add, and remove officer applications.
 * The data is persisted using file operations.
 */
public class OfficerAppDB {

  /** The in-memory database of officer applications. */
  private static List<OfficerApplication> db;

  /** The file loader utility for loading officer applications from a file. */
  private static IFileLoader fileLoader = new FileLoader();

  /** The file saver utility for saving officer applications to a file. */
  private static IFileSaver fileSaver = new FileSaver();

  /**
   * Initializes the officer application database by loading data from a file.
   */
  public static void initiateDB(){
    db = fileLoader.loadOfficerApplications();
  }

  /**
   * Updates the database with a new list of officer applications and reloads the data.
   *
   * @param dataList The list of officer applications to update the database with.
   */
  public static void updateDB(List<OfficerApplication> dataList){
    saveToFile();
    initiateDB();
  }

  /**
   * Saves the current database to a file.
   */
  private static void saveToFile(){
    fileSaver.saveOfficerApplications(db);
  }

  /**
   * Retrieves all officer applications submitted by a specific user.
   *
   * @param nric The NRIC of the user whose applications are to be retrieved.
   * @return A list of officer applications submitted by the specified user.
   */
  public static List<OfficerApplication> getApplicationsByUser(String nric) {
      List<OfficerApplication> result = new ArrayList<>();
      for (OfficerApplication application : db) {
          if (application.getUser().equalsIgnoreCase(nric) && !application.getStatus().equals(OfficerAppStat.REJECTED)) {
              result.add(application);
          }
      }
      return result;
  }

  /**
   * Retrieves all officer applications related to a specific project.
   *
   * @param project The name of the project whose applications are to be retrieved.
   * @return A list of officer applications related to the specified project.
   */
  public static List<OfficerApplication> getApplicationsByProject(String project) {
      List<OfficerApplication> result = new ArrayList<>();
      for (OfficerApplication application : db) {
          if (application.getProjectName().equalsIgnoreCase(project)) {
              result.add(application);
          }
      }
      return result;
  }

  /**
   * Adds a new officer application to the database and saves it to a file.
   *
   * @param application The officer application to be added.
   */
  public static void addApplication(OfficerApplication application) {
      db.add(application);
      saveToFile();
  }

  /**
   * Removes an officer application from the database and saves the changes to a file.
   *
   * @param application The officer application to be removed.
   */
  public static void removeApplication(OfficerApplication application) {
      db.remove(application);
      saveToFile();
  }

  /**
   * Removes all officer applications related to a specific project and saves the changes to a file.
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
  public static void updateApplication(){
    saveToFile();
  }

  /**
   * Retrieves the entire officer application database.
   *
   * @return The list of all officer applications in the database.
   */
  public static List<OfficerApplication> getDB(){
    return db;
  }

}
