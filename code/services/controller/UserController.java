package services.controller;

import java.util.Scanner;
import models.users.Applicant;
import models.users.HDBManager; 
import models.users.HDBOfficer;
import models.users.User;

public abstract class UserController {
  public static User currentUser = null;  

  public UserController(User user) {
    currentUser = user;
  }

  public static void start(Scanner sc){
    
    if(currentUser instanceof Applicant){
      ApplicantController app = new ApplicantController();
      app.start(sc);
    }
    else if(currentUser instanceof HDBOfficer){
      OfficerController.start(sc);
    }
    else if(currentUser instanceof HDBManager){
      ManagerController.start(sc);
    }
  }
  public abstract void viewProjectList();
}
