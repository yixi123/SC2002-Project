package database.dataclass.projects;

import java.util.List;
import models.projects.Enquiry;
import utils.FileLoader ;

public class EnquiryDB {

  private static List<Enquiry> db; 

  public static void initiateDB(){
    db = FileLoader.loadEnquiries();
  }

  //update or add
  public static void updateDB(List<Enquiry> enqList){
    db = enqList;
  }

  //remove
  public static List<Enquiry> getDB(){
    return db;
  }
}
