package database.dataclass.projects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import models.projects.BTOProject;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

public class ProjectDB {
  private static List<BTOProject> db;
  private static IFileLoader fileLoader = new FileLoader();
  private static IFileSaver fileSaver = new FileSaver();

  public static void initiateDB(){
    db = fileLoader.loadProjects();
  }

  private static void saveToFile(){
    fileSaver.saveProjects(db);
  }
  
  //update or add
  public static void updateDB(List<BTOProject> dataList){
    saveToFile();
    initiateDB();
  }
  public static void updateDB(){
    saveToFile();
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


  public static void updateProject(){
    saveToFile();
  }

  public static void addProject(BTOProject project){
    db.add(project);
    saveToFile();
  }

  public static void removeProject(BTOProject project){
    Iterator<BTOProject> it = db.iterator();
    BTOProject temp;
    while(it.hasNext()){
      temp = it.next();
      if(temp.getProjectName().equals(project.getProjectName())){
        it.remove();
        ProjectAppDB.removeApplicationByProject(project.getProjectName());
        OfficerAppDB.removeApplicationByProject(project.getProjectName());
      }
    }
    saveToFile();
  }

  public static List<BTOProject> getProjectsByOfficer(String officerId){
    List<BTOProject> projects = new ArrayList<>();
    for (BTOProject project : db) {
        if (project.getOfficers() != null && project.getOfficers().contains(officerId)) {
            projects.add(project);
        }
    }
    return projects; 
  }

  public static List<BTOProject> getProjectsByManager(String managerId){
    List<BTOProject> projects = new ArrayList<>();
    for (BTOProject project : db) {
        if (project.getManagerID() != null && project.getManagerID().equals(managerId)) {
            projects.add(project);
        }
    }
    return projects; 
  }
}
