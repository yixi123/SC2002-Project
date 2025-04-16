package database.dataclass.projects;

import models.projects.OfficerApplication;
import utils.FileLoader;

import java.util.ArrayList;
import java.util.List;

public class OfficerAppDB {
  private static List<OfficerApplication> db;

  public static void initiateDB(){
    db = FileLoader.loadOfficerApplications();
  }

  //update or add
  public static void updateDB(List<OfficerApplication> dataList){
    db = dataList;
  }

  public static List<OfficerApplication> getDB(){
    return db;
  }

}
