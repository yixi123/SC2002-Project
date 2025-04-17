package database.dataclass.projects;

import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;
import utils.FileLoader;
import utils.FileSaver;

import java.util.List;
import java.util.ArrayList;

public class ProjectAppDB {
  private static List<ProjectApplication> db;
  
  public static void initiateDB(){
    db = FileLoader.loadProjectApplications();
  }
  
  //update or add
  public static void updateDB(List<ProjectApplication> dataList){
    db = dataList;
    FileSaver.saveProjectApplications(db);
  }

  public static ProjectApplication getApplicationByUser(String nric) {
      for (ProjectApplication application : db) {
          if (application.getUser().equalsIgnoreCase(nric) && !(application.getStatus() == ProjectAppStat.UNSUCCESSFUL)) {
              return application;
          }
      }
      return null; // No application found for the user
  }
    
  public static List<ProjectApplication> getApplicationsByProject(String project) {
      List<ProjectApplication> result = new ArrayList<>();
      for (ProjectApplication application : db) {
          if (application.getProjectName().equalsIgnoreCase(project)) {
              result.add(application);
          }
      }
      return result;
  }


  public static List<ProjectApplication> getDB(){
    return db;
  }
}
