package database.dataclass.projects;

import models.enums.OfficerAppStat;
import models.projects.OfficerApplication;
import utils.FileLoader;
import utils.FileSaver;

import java.util.ArrayList;
import java.util.List;

public class OfficerAppDB {
  private static List<OfficerApplication> db;

  public static void initiateDB(){
    db = FileLoader.loadOfficerApplications();
  }

  //update or add
  public static void updateDB(List<OfficerApplication> dataList){
    FileSaver.saveOfficerApplications(dataList);
    initiateDB();
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
      FileSaver.saveOfficerApplications(db);
  }

  public static void removeApplication(OfficerApplication application) {
      db.remove(application);
      FileSaver.saveOfficerApplications(db);
  }

  public static void removeApplicationByProject(String projectName) {
      db.removeIf(application -> application.getProjectName().equalsIgnoreCase(projectName));
      FileSaver.saveOfficerApplications(db);
  }

  public static void updateApplication(OfficerApplication application) {
      for (int i = 0; i < db.size(); i++) {

          if (db.get(i).getUser().equals(application.getUser()) &&
              db.get(i).getProjectName().equals(application.getProjectName())) {
                  db.set(i, application);
                  break;
          }
      }
      FileSaver.saveOfficerApplications(db);
  }

  public static List<OfficerApplication> getDB(){
    return db;
  }

}
