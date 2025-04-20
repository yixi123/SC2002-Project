package database.dataclass.users;

import java.util.List;
import models.users.Applicant;
import utils.FileLoader;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

public class ApplicantDB {
  private static List<Applicant> db;
  private static IFileLoader fileLoader = new FileLoader();
  private static IFileSaver fileSaver = new FileSaver();

  public static void initiateDB(){
    db = fileLoader.loadApplicants();
  }

  private static void saveToFile(){
    fileSaver.saveApplicants(db);
  }
  
  //update or add
  public static void updateDB(List<Applicant> dataList){
    saveToFile();
    initiateDB();
  }

  public static List<Applicant> getDB(){
    return db;
  }

  public static void updateUser(Applicant applicant) {
    for (int i = 0; i < db.size(); i++) {
      if (db.get(i).getNric().equals(applicant.getNric())) {
        db.set(i, applicant);
        break;
      }
    }
    saveToFile();
  }

  public static String getUsernameByID(String userID){
    for (Applicant applicant : db) {
      if (applicant.getNric().equals(userID)) {
        return applicant.getName();
      }
    }
    return null;
  }

  public static Applicant getApplicantByID(String userID){
    for (Applicant applicant : db) {
      if (applicant.getNric().equals(userID)) {
        return applicant;
      }
    }
    return null;
  }

  public static void addUser(Applicant applicant) {
    db.add(applicant);
    saveToFile();
  }

}
