package database.dataclass.users;

import java.util.List;

import models.users.HDBOfficer;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

public class OfficerDB {
    private static List<HDBOfficer> db;
    private static IFileLoader fileLoader = new FileLoader();
    private static IFileSaver fileSaver = new FileSaver();

  public static void initiateDB(){
    db = fileLoader.loadOfficers();
  }

  private static void saveToFile(){
    fileSaver.saveOfficers(db);
  }
  
  //update or add
  public static void updateDB(List<HDBOfficer> dataList){
    db = dataList;
    saveToFile();
  }

  public static List<HDBOfficer> getDB(){
    return db;
  }

  public static String getUsernameByID(String userID){
    for (HDBOfficer officer : db) {
      if (officer.getNric().equals(userID)) {
        return officer.getName();
      }
    }
    return null;
  }


  //SINGLE USER DATABASE OPERATIONS

  public static void updateUser() {
    saveToFile();
  }

  public static void addUser(HDBOfficer user) {
    db.add(user);
  }

}
