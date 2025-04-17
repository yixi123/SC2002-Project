package services.controller;

import java.util.Scanner;

// Assuming UserController is in the same package or imported
public class OfficerController extends UserController {

  public void start(Scanner sc){

    System.out.println("--------------------------------");
    System.out.println("\tOfficer Portal");
    System.out.println("--------------------------------");
    System.out.println("1. Project List");
    System.out.println("2. Filter Settings");
    System.out.println("3. View Application Status");
    System.out.println("4. Withdraw Project Application");
    System.out.println("5. View My Enquiry");
    System.out.println("6. Reply Enquiry");
    System.out.println("7. Logout");
    System.out.println("--------------------------------");
    System.out.print("Enter your choice: ");
    int choice = sc.nextInt();
    sc.nextLine();

    // switch(choice){
    //   case 1 -> viewProjectList(sc);
    //   case 2 -> adjustFilterSettings(sc);
    //   case 3 -> viewApplicationStatus(sc);
    //   case 4 -> withdrawProject(sc);
    //   case 5 -> viewEnquiry(sc);
    //   case 6 -> replyEnquiry(sc);
    //   case 7 -> System.out.println("Logging out...");
    //   default -> {
    //     System.out.println("Invalid choice. Please try again.");
    //     start(sc);
    //   }
    // }
  }
    

  public void viewHandledProject() {
  }

  public void viewSuccessfulApplicantsList() {
  }

  public void handleApplicantsSuccessfulApp() {
  }

  public void generateReceipt() {
  }

  public void registerAsOfficer() {
  }

  public void registerAsApplicant() {
  }

  public void viewProjectListAsApp() {
  }

  public void viewEnquiries() {
  }

  public void replyEnquiries() {
  }

  public void writeEnquiryAsApp() {
  }

  public void editEnquiryAsApp() {
  }

  public void deleteEnquiryAsApp() {
  }

}