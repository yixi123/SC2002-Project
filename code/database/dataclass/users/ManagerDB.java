package database.dataclass.users;

import java.util.List;
import models.users.HDBManager;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

public class ManagerDB {
  private static List<HDBManager> db;
  private static IFileLoader fileLoader = new FileLoader();
  private static IFileSaver fileSaver = new FileSaver();

  public static void initiateDB(){
    db = fileLoader.loadManagers();
  }

  private static void saveToFile(){
    fileSaver.saveManagers(db);
  }
  
  //update or add
  public static void updateDB(List<HDBManager> dataList){
    saveToFile();
    initiateDB();
  }

  public static void updateUser(HDBManager user) {
    for(int i = 0; i < db.size(); i++){
      if(db.get(i).getNric().equals(user.getNric())){
        db.set(i, user);
      }
    }
    saveToFile();
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
