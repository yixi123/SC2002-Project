package database.dataclass.projects;

import java.util.ArrayList;
import java.util.List;
import models.projects.ProjectApplication;
import utils.FileLoader;
import utils.FileSaver;

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
  public static void updateDB(){
    FileSaver.saveProjectApplications(db);
  }


  public static List<ProjectApplication> getApplicationByUser(String nric) {
      List<ProjectApplication> result = new ArrayList<>();
      for (ProjectApplication application : db) {
          if (application.getUser().equalsIgnoreCase(nric)) {
              result.add(application);
          }
      }
      return result; 
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

  public static void addApplication(ProjectApplication application) {
      db.add(application);
      FileSaver.saveProjectApplications(db);
  }

  public static void removeApplication(ProjectApplication application) {
      db.remove(application);
      FileSaver.saveProjectApplications(db);
  }

  public static void updateApplication(ProjectApplication application) {
      for (int i = 0; i < db.size(); i++) {
          if (db.get(i).getUser().equals(application.getUser()) &&
              db.get(i).getProjectName().equals(application.getProjectName())) {
              db.set(i, application);
              break;
          }
      }
      FileSaver.saveProjectApplications(db);
  }


  public static List<ProjectApplication> getDB(){
    return db;
  }
}
