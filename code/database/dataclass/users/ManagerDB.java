package database.dataclass.users;

import java.util.List;
import models.users.HDBManager;
import utils.FileLoader;
import utils.FileSaver;

public class ManagerDB {
  private static List<HDBManager> db;

  public static void initiateDB(){
    db = FileLoader.loadManagers();

  }
  
  //update or add
  public static void updateDB(List<HDBManager> dataList){
    FileSaver.saveManagers(dataList);
    initiateDB();
  }

  public static void updateUser(HDBManager user) {
    for(int i = 0; i < db.size(); i++){
      if(db.get(i).getNric().equals(user.getNric())){
        db.set(i, user);
      }
    }
    FileSaver.saveManagers(db);
  }

  public static List<HDBManager> getDB() {return db;}

  public static String getUsernameByID(String userID){
    for (HDBManager manager : db) {
      if (manager.getNric().equals(userID)) {
        return manager.getName();
      }
    }
    return null;
  }


}
