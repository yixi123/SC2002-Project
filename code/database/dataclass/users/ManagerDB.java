package database.dataclass.users;

import java.util.List;
import java.util.ArrayList;

import models.users.HDBManager;
import utils.FileLoader;

public class ManagerDB {
  private static List<HDBManager> db;

  public static void initiateDB(){
    db = FileLoader.loadManagers();
  }
  
  //update or add
  public static void updateDB(List<HDBManager> dataList){
    db = dataList;
  }

  public static List<HDBManager> getDB(){
    return db;
  }
}
