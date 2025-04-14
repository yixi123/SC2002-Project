package database.dataclass.projects;

import models.projects.Enquiry;
import utils.FileLoader;

import java.util.List ;
import java.util.Iterator;
import java.util.ArrayList;

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
  public static List<Enquiry> getDB(Enquiry enq){
    return db;
  }
}
