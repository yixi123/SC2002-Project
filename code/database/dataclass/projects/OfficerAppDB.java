package database.dataclass.projects;

import models.enums.OfficerAppStat;
import models.projects.OfficerApplication;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

import java.util.ArrayList;
import java.util.List;

public class OfficerAppDB {
  private static List<OfficerApplication> db;
  private static IFileLoader fileLoader = new FileLoader();
  private static IFileSaver fileSaver = new FileSaver();

  public static void initiateDB(){
    db = fileLoader.loadOfficerApplications();
  }

  //update or add
  public static void updateDB(List<OfficerApplication> dataList){
    saveToFile();
    initiateDB();
  }

  private static void saveToFile(){
    fileSaver.saveOfficerApplications(db);
  }

  public static List<OfficerApplication> getApplicationsByUser(String nric) {
      List<OfficerApplication> result = new ArrayList<>();
      for (OfficerApplication application : db) {
          if (application.getUser().equalsIgnoreCase(nric) && !application.getStatus().equals(OfficerAppStat.REJECTED)) {
              result.add(application);
          }
      }
      return result;
  }

  public static List<OfficerApplication> getApplicationsByProject(String project) {
      List<OfficerApplication> result = new ArrayList<>();
      for (OfficerApplication application : db) {
          if (application.getProjectName().equalsIgnoreCase(project)) {
              result.add(application);
          }
      }
      return result;
  }

  public static void addApplication(OfficerApplication application) {
      db.add(application);
      saveToFile();
  }

  public static void removeApplication(OfficerApplication application) {
      db.remove(application);
      saveToFile();
  }

  public static void removeApplicationByProject(String projectName) {
      db.removeIf(application -> application.getProjectName().equalsIgnoreCase(projectName));
      saveToFile();
  }

  public static void updateApplication(){
    saveToFile();
  }

  public static List<OfficerApplication> getDB(){
    return db;
  }

}
