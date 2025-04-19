package services.controller;

import database.dataclass.projects.ProjectDB;
import java.util.List;
import java.util.Scanner;
import models.enums.OfficerAppStat;
import models.enums.ProjectAppStat;
import models.projects.*;
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

  public void viewProjectList(){
    List<BTOProject> projects =  ProjectDB.getDB();
    List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

    if (filteredProjects.isEmpty()) {
      System.out.println("No projects match your filters.");
    } else {
      filteredProjects.forEach(System.out::println);
    }
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

  public void viewEnquiries() {
    BTOProject project = retreiveOfficer().getActiveProject();
    if (project == null) {
      System.out.println("No project assigned.");
      return;
    }
    enquiryService.viewEnquiriesByProject(project.getProjectName());
  }

  public void replyEnquiries(Scanner sc) {
    System.out.print("Enter Enquiry ID to reply: ");
    int id = sc.nextInt(); sc.nextLine();
    System.out.print("Reply: ");
    String r = sc.nextLine();
    enquiryService.replyEnquiry(id, retreiveOfficer().getNric(), r);
  }

  public void updateApplicantAppStat(String applicantId, String newStatus) {
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
    proAppService.updateApplicationStatus(
            app,
            models.enums.ProjectAppStat.valueOf(newStatus)
    );
    System.out.printf("Set %s → %s%n", applicantId, newStatus);
  }

  public void registerAsOfficer(BTOProject project) {
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
