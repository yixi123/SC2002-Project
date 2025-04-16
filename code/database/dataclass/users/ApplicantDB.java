package database.dataclass.users;

import java.util.List;
import models.users.Applicant;
import utils.FileLoader;

public class ApplicantDB {
  private static List<Applicant> db;

  public static void initiateDB(){
    db = FileLoader.loadApplicants();
  }
  
  //update or add
  public static void updateDB(List<Applicant> dataList){
    db = dataList;
  }

  public static List<Applicant> getDB(){
    return db;
  }

  public static String getUsernameByID(String userID){
    for (Applicant applicant : db) {
      if (applicant.getNric().equals(userID)) {
        return applicant.getName();
      }
    }
    return null;
  }
}
