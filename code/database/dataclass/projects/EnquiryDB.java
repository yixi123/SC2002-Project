package database.dataclass.projects;

import java.util.ArrayList;
import java.util.List;
import models.projects.Enquiry;
import utils.FileLoader ;
import utils.FileSaver;
import utils.IFileLoader;
import utils.IFileSaver;

public class EnquiryDB {

  private static List<Enquiry> db; 
  private static IFileLoader fileLoader = new FileLoader();
  private static IFileSaver fileSaver = new FileSaver();

  public static void initiateDB(){
    db = fileLoader.loadEnquiries();
  }

  //update or add
  public static void updateDB(List<Enquiry> enqList){
    db = enqList;
    saveToFile();
  }

  private static void saveToFile(){
    fileSaver.saveEnquiries(db);
  }

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

  public static void addEnquiry(Enquiry enquiry) {
      db.add(enquiry);
      saveToFile();
  }

  public static void removeEnquiry(Enquiry enquiry) {
      db.remove(enquiry);
      saveToFile();
  }

  public static void removeEnquiryByID(int enquiryID) {
      db.removeIf(enquiry -> enquiry.getId() == enquiryID);
      saveToFile();
  }

  public static void updateEnquiry() {
      saveToFile();
  }
}
