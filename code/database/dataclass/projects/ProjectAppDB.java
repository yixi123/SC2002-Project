package database.dataclass.projects;

import java.util.ArrayList;
import java.util.List;
import models.projects.ProjectApplication;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

public class ProjectAppDB {
  private static List<ProjectApplication> db;
  private static IFileLoader fileLoader = new FileLoader();
  private static IFileSaver fileSaver = new FileSaver();
  
  public static void initiateDB(){
    db = fileLoader.loadProjectApplications();
  }

  private static void saveToFile(){
    fileSaver.saveProjectApplications(db);
  }
  
  //update or add
  public static void updateDB(List<ProjectApplication> dataList){
    db = dataList;
    saveToFile();
  }
  public static void updateDB(){
    saveToFile();
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

    public static ProjectApplication getApplicationsByUserAndProject(String userID, String projectName) {
        for (ProjectApplication application : db) {
            if (application.getUser().equals(userID) && application.getProjectName().equals(projectName)) {
                return application;
            }
        }
        return null;
    }

  public static void addApplication(ProjectApplication application) {
      db.add(application);
      saveToFile();
  }

  public static void removeApplication(ProjectApplication application) {
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


  public static List<ProjectApplication> getDB(){
    return db;
  }
}
