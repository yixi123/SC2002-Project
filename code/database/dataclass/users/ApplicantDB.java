package database.dataclass.users;

import java.util.List;
import models.users.Applicant;
import utils.FileLoader;
import utils.FileSaver;

public class ApplicantDB {
  private static List<Applicant> db;

  public static void initiateDB(){
    db = FileLoader.loadApplicants();
  }
  
  //update or add
  public static void updateDB(List<Applicant> dataList){
    FileSaver.saveApplicants(dataList);
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
    FileSaver.saveApplicants(db);
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

}
