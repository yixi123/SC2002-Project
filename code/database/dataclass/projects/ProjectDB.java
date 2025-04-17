package database.dataclass.projects;

import java.util.List;
import models.projects.BTOProject;
import utils.FileLoader;
import utils.FileSaver ;

public class ProjectDB {
  private static List<BTOProject> db;

  public static void initiateDB(){
    db = FileLoader.loadProjects();
  }
  
  //update or add
  public static void updateDB(List<BTOProject> dataList){
    db = dataList;
    FileSaver.saveProjects(db);
  }
  public static List<BTOProject> getDB(){
    return db;
  }
}
