package database.dataclass.projects;

import models.enums.ProjectAppStat;
import models.projects.Application;
import models.projects.OfficerApplication;
import utils.FileLoader;
import utils.FileSaver;

import java.util.ArrayList;
import java.util.List;
import utils.FileSaver;

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
          if (application.getUser().equalsIgnoreCase(nric) && !application.getStatus().equals(ProjectAppStat.WITHDRAWN)) {
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

  public static List<OfficerApplication> getDB(){
    return db;
  }

}
