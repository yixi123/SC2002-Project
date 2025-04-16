package database.dataclass.projects;

import models.projects.ProjectApplication;
import utils.FileLoader;

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
  }

  public static List<ProjectApplication> getDB(){
    return db;
  }
}
