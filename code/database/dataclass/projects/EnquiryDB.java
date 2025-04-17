package database.dataclass.projects;

import java.util.ArrayList;
import java.util.List;
import models.projects.Enquiry;
import utils.FileLoader ;
import utils.FileSaver;

public class EnquiryDB {

  private static List<Enquiry> db; 

  public static void initiateDB(){
    db = FileLoader.loadEnquiries();
  }

  //update or add
  public static void updateDB(List<Enquiry> enqList){
    db = enqList;
    FileSaver.saveEnquiries(db);
  }

  //remove
  public static List<Enquiry> getDB(){
    return db;
  }

  public static List<Enquiry> getEnquiriesByUserID(String userID) {
      List<Enquiry> result = new ArrayList<>();
      for (Enquiry enquiry : db) {
          if (enquiry.getUserID().equalsIgnoreCase(userID)) {
              result.add(enquiry);
          }
      }
      return result;
  }

  public static List<Enquiry> getEnquiriesByProject(String project) {
      List<Enquiry> result = new ArrayList<>();
      for (Enquiry enquiry : db) {
          if (enquiry.getProjectID().equalsIgnoreCase(project)) {
              result.add(enquiry);
          }
      }
      return result;
  }
}
