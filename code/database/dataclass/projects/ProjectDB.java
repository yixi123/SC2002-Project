package database.dataclass.projects;

import java.util.Iterator;
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
    FileSaver.saveProjects(dataList);
    initiateDB();
  }
  public static List<BTOProject> getDB(){
    return db;
  }



  public static BTOProject getProjectByName(String projectName) {
    for (BTOProject project : db) {
      if (project.getProjectName().equalsIgnoreCase(projectName)) {
        return project;
      }
    }
    return null; 
  }


  public static void updateProject(BTOProject project){
    for(int i = 0; i < db.size(); i++){
      if(db.get(i).getProjectName().equals(project.getProjectName())){
        db.set(i, project);
      }
    }
    FileSaver.saveProjects(db);
  }

  public static void addProject(BTOProject project){
    db.add(project);
    FileSaver.saveProjects(db);
  }

  public static void removeProject(BTOProject project){
    Iterator<BTOProject> it = db.iterator();
    BTOProject temp;
    while(it.hasNext()){
      temp = it.next();
      if(temp.getProjectName().equals(project.getProjectName())){
        it.remove();
      }
    }
    FileSaver.saveProjects(db);
  }
}
