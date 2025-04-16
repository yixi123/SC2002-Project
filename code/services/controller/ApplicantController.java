package services.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import database.dataclass.projects.ProjectDB;

import models.projects.Enquiry;
import models.projects.FilterSettings;
import models.users.Applicant;
import models.users.User;
import models.projects.*;

import services.subservices.EnquiryService;
import view.ApplicantView;
import view.ProjectView;

public class ApplicantController {
    static FilterSettings filterSettings = new FilterSettings();
    ApplicantView applicantView = new ApplicantView();

    public static void start(Scanner sc){
        ApplicantController app = new ApplicantController();

        System.out.println("--------------------------------");
        System.out.println("\tApplicant Portal");
        System.out.println("--------------------------------");
        System.out.println("1. View Project List");
        System.out.println("2. Apply for a Project");
        System.out.println("3. View Application Status");
        System.out.println("4. Withdraw Project");
        System.out.println("5. View Enquiry");
        System.out.println("6. Add Enquiry");
        System.out.println("7. Logout");
        System.out.println("--------------------------------");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                app.viewProjectList(sc);
                break;
            case 2:
                app.applyProject(sc);
                break;
            case 3:
                app.viewApplicationStatus(sc);
                break;
            case 4:
                app.withdrawProject(sc);
                break;
            case 5:
                app.viewEnquiry(sc);
                break;
            case 6:
                app.addEnquiry(sc);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                start(sc);
                break;
        }
    }

    public void viewProjectList(Scanner sc){
        List<BTOProject> projects =  ProjectDB.getDB();
        projects.stream()
                .filter(
    }

    public void applyProject(Scanner sc){

    }

    public void viewApplicationStatus(Scanner sc) {

    }

    public void withdrawProject(Scanner sc) {

    }

    public void addEnquiry(Scanner sc) {

    }

    public void viewEnquiry(Scanner sc){
        
    }
}