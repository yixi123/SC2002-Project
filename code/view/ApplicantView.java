package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import archive.ProjectController;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.FilterSettings;
import services.controller.ApplicantController;
import services.subservices.EnquiryService;
import utils.FilterUtil;
import utils.SortUtil;

public class ApplicantView implements IUserView {
  private FilterSettings filterSettings = new FilterSettings();

  public void displayMenu(){
    System.out.println("1. View Projects");
    System.out.println("2. Apply Filters");
    System.out.println("3. Apply for Project");
    System.out.println("4. View Application Status");
    System.out.println("5. Withdraw Application");
    System.out.println("6. View my Enquiry");
    System.out.println("7. Submit Enquiry");
    System.out.println("8. Exit");
    System.out.print("Enter your choice: ");
  }

  public void display(Scanner scanner){
    System.out.println("Applicant Menu:");
  }
}
