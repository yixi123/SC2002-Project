package database.dataclass.users;

import java.util.List;

import models.users.HDBOfficer;
import utils.FileLoader;
import utils.FileSaver;

public class OfficerDB {
    private static List<HDBOfficer> db;

  public static void initiateDB(){
    db = FileLoader.loadOfficers();
  }
  
  //update or add
  public static void updateDB(List<HDBOfficer> dataList){
    db = dataList;
    FileSaver.saveOfficers(db);
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

  public static void updateUser(HDBOfficer user) {
    for(int i = 0; i < db.size(); i++){
      if(db.get(i).getNric().equals(user.getNric())){
        db.set(i, user);
      }
    }
  }

  public static void addUser(HDBOfficer user) {
    db.add(user);
  }

}
