package services.controller;

import database.dataclass.projects.ProjectDB;

import java.util.List;
import java.util.Scanner;
import models.enums.OfficerAppStat;
import models.enums.ProjectAppStat;
import models.projects.*;
import models.users.Applicant;
import models.users.HDBOfficer;
import services.interfaces.IEnquiryService;
import services.interfaces.IOfficerApplicationService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IReceiptPrintService;
import services.subservices.EnquiryService;
import services.subservices.OfficerApplicationService;
import services.subservices.ProjectApplicationService;
import services.subservices.ReceiptPrintService;
import utils.FilterUtil;
import view.OfficerView;


public class OfficerController extends ApplicantController {
  private final IOfficerApplicationService offAppService;
  private final IProjectApplicationService proAppService;
  private final IEnquiryService enquiryService;
  private final IReceiptPrintService printService;

  private static OfficerView officerView = new OfficerView();


  public OfficerController() {
    this.offAppService  = new OfficerApplicationService();
    this.proAppService  = new ProjectApplicationService();
    this.enquiryService = new EnquiryService();
    this.printService   = new ReceiptPrintService();
  }

  public HDBOfficer retreiveOfficer(){
        HDBOfficer currentUser = (HDBOfficer) auth.getCurrentUser();
        currentUser.setAssignedProject(ProjectDB.getProjectsByOfficer(currentUser.getNric()));
        currentUser.setOfficerApplications(offAppService.getApplicationsByUser(currentUser.getNric()));
        return currentUser;
    }

  @Override
  public void start(Scanner sc) throws Error{
      try{
          System.out.println("Welcome to the HDB Officer Portal!");
          officerView.enterMainMenu(sc);
      } catch (Exception e) {
          System.out.println("An error occurred: " + e.getMessage());
      }
  }

  //--------------------------------------------------------------------------------

  public void viewHandledProject() {
    BTOProject p = retreiveOfficer().getActiveProject();
    if (p == null) System.out.println("No assignment.");
    else           System.out.println(p);
  }

  public void enterOfficerProjectPortal(Scanner sc) throws Exception {
      officerView.displayOfficerProjectPortal(sc, filterSettings);
  }
    
  

  public void viewSuccessfulApplicantsList() {
    BTOProject project = retreiveOfficer().getActiveProject();
    if (project == null) {
      System.out.println("No project assigned.");
      return;
    }
    List<ProjectApplication> apps = proAppService.getProjectApplications(project.getProjectName());
    if (apps == null || apps.isEmpty()) {
      System.out.println("No applications found for project " + project.getProjectName());
      return;
    }
    apps.stream()
            .filter(a -> a.getStatus() == ProjectAppStat.SUCCESSFUL)
            .forEach(System.out::println);
  }

  public void handleApplicantsSuccessfulApp(String applicantId) {
    BTOProject project = retreiveOfficer().getActiveProject();
    if (project == null) {
      System.out.println("No project assigned.");
      return;
    }
    ProjectApplication app = proAppService.getApplicationByUserAndProject(applicantId, project.getProjectName());
    if (app == null) {
      System.out.println("No application found for applicant " + applicantId);
      return;
    }
    if (app.getStatus() == ProjectAppStat.BOOKED) {
      System.out.println("Application is already booked.");
      return;
    }
    if (app.getStatus() != ProjectAppStat.SUCCESSFUL) {
      System.out.println("Application is not successful cannot proceed to booking.");
      return;
    }
    proAppService.bookApplication(project.getProjectName(), applicantId);
    
  }

  public void generateReceipt(String applicantId) {
    String receipt = printService.printReceipt(applicantId);
    if (receipt != null){
      System.out.println(receipt);
    } else {
      System.out.println("No booking found for applicant " + applicantId);
    }
  }

  public void viewEnquiries(Scanner sc){
    BTOProject project = retreiveOfficer().getActiveProject();
    if (project == null) {
      System.out.println("No project assigned.");
      return;
    }
    List<Enquiry> enquiryList =  enquiryService.getEnquiriesbyProject(project.getProjectName());
    if (enquiryList.isEmpty()) {
      System.out.println("No enquiries found for project: " + project.getProjectName());
      return;
    }
    Enquiry selectedEnquiry = enquiryService.chooseFromEnquiryList(sc, enquiryList);
    if (selectedEnquiry == null) {
        return;
    }
    officerView.viewEnquiryActionMenuForOfficer(sc, selectedEnquiry);
  }

  public void replyEnquiry(Scanner sc, Enquiry selectedEnquiry) {
    if (selectedEnquiry.getReplierUserID() != null) {
      System.out.println("This enquiry has already been replied to.");
      return;
    }
    System.out.print("Enter your reply content: ");
    String replyContent = sc.nextLine();
    System.out.println("-----------------------------------------");
    enquiryService.replyEnquiry(selectedEnquiry.getId(), retreiveOfficer().getNric(), replyContent);
    System.out.println("Reply sent successfully.");
    System.out.println("-----------------------------------------");
  }


  public void registerAsOfficer(BTOProject project) {
    if (project == null) {
      System.out.println("Project not found.");
      return;
    }
    if (project.getOfficerSlot() <= 0) {
      System.out.println("No officer slots available for this project.");
      return;
    }
    HDBOfficer officer = retreiveOfficer();
    List<OfficerApplication> apps = officer.getOfficerApplications();
    apps.removeIf(app -> app.getStatus() == OfficerAppStat.REJECTED);
    for (OfficerApplication app: apps) {
      BTOProject p = ProjectDB.getProjectByName(app.getProjectName());
      if (p != null && (!p.getOpeningDate().after(project.getClosingDate()) || !p.getClosingDate().before(project.getOpeningDate()))) {
        System.out.println("This project overlaps with your current application.");
        System.out.println("Your current application: " + app.getProjectName() + " | Status: " + app.getStatus());
        return;
      }
    }
    offAppService.addApplication(project.getProjectName(), officer.getNric());
    System.out.println("Registration PENDING approval.");
  }


  // -----------------------------------------------------------------------------------
  @Override
  public void enterProjectPortal(Scanner sc) throws Exception{
      officerView.displayProjectPortal(sc, filterSettings);
  }

  @Override
  public void applyForProject(Scanner sc, BTOProject selectedProject) {
        HDBOfficer officer = retreiveOfficer();
        List<OfficerApplication> applications = officer.getOfficerApplications();
        
        if (!officer.getMyApplication().isEmpty()) {
            System.out.println("You have already applied for a project.\n Please check your application status.");
            return;
        }
        for (OfficerApplication application : applications) {
            if (application.getProjectName().equals(selectedProject.getProjectName())) {
                System.out.println("You have already applied as an officer for this project.\n Please check your application status.");
                return;
            }
        }
        projectApplicationService.applyForProject(sc, officer, selectedProject.getProjectName());
        

    }
}
