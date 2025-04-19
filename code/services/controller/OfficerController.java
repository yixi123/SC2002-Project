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

  private OfficerView officerView = new OfficerView();

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
  public void start(Scanner sc) {
    while (true) {
      System.out.println("=== HDB Officer Main Menu ===");
      System.out.println("1) Officer functions");
      System.out.println("2) Applicant functions");
      System.out.println("3) Logout");
      System.out.print("Select an option: ");
      int choice = sc.nextInt(); sc.nextLine();

      switch (choice) {
        case 1 -> officerMenu(sc);
        case 2 -> {
              try {
                  applicantMenu(sc);
              } catch (Exception ex) {
                  System.out.println("An error occurred: " + ex.getMessage());
              } finally {
                  auth.logout();
              }
          }

        case 3 -> {
            System.out.println("Logging out...");
            return;    // exit start()
            }
        default -> System.out.println("Invalid choice. Try again.");
      }
    }
  }

  private void officerMenu(Scanner sc) {
    while (true) {
      System.out.println("--- Officer Functions ---");
      System.out.println("1) View project list");
      System.out.println("2) View handled project");
      System.out.println("3) View successful applicants");
      System.out.println("4) Book applicant flat");
      System.out.println("5) Generate receipt");
      System.out.println("6) Register as officer");
      System.out.println("7) View enquiries");
      System.out.println("8) Reply to enquiry");
      System.out.println("9) Update applicant status");
      System.out.println("0) Back");
      System.out.print("Choice: ");
      int opt = sc.nextInt(); sc.nextLine();

      switch (opt) {
        case 1 -> viewProjectList();
        case 2 -> viewHandledProject();
        case 3 -> viewSuccessfulApplicantsList();
        case 4 -> {
            System.out.print("Applicant NRIC: ");
            String id4 = sc.nextLine();
            handleApplicantsSuccessfulApp(id4);
            }
        case 5 -> {
            System.out.print("Applicant NRIC: ");
            String id5 = sc.nextLine();
            generateReceipt(id5);
            }
        case 6 -> {
            System.out.print("Project ID to register: ");
            String pid6 = sc.nextLine();
            BTOProject project = ProjectDB.getProjectByName(pid6);
            registerAsOfficer(project);
            }
        case 7 -> viewEnquiries();
        case 8 -> replyEnquiries(sc);
        case 9 -> {
            System.out.print("Applicant ID: ");
            String applicantId9 = sc.nextLine();
            System.out.print("New status (SUCCESSFUL, UNSUCCESSFUL, BOOKED): ");
            String newStatus9 = sc.nextLine().toUpperCase();
            updateApplicantAppStat(applicantId9, newStatus9);
            }
        case 0 -> {
            return;  // back to start()
            }
        default -> System.out.println("Invalid.");
      }
    }
  }

  private void applicantMenu(Scanner sc) throws Exception {
    int choice;
		
	 	do{
            System.out.println("--------------------------------");
            System.out.println("\tApplicant Portal");
            System.out.println("--------------------------------");
            System.out.println("1. Enter Project Protal");
            System.out.println("2. Adjust Filter Settings");
            System.out.println("3. View Application Status");
            System.out.println("4. Withdraw Current Application");
            System.out.println("5. View My Enquiry");
            System.out.println("6. Change My Password");
            System.out.println("0. Back");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0 -> {
                    System.out.println("Logging out...");
					System.out.println("--------------------------------");
                    return;
                }
                case 1 -> enterProjectProtal(sc);
                case 2 -> adjustFilterSettings(sc);
                case 3 -> viewApplicationStatus();
                case 4 -> withdrawProject(sc);
                case 5 -> viewMyEnquiry(sc);
                case 6 -> changeMyPassword(sc);
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } while (choice != 0 && choice != 6);
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
  public void enterProjectProtal(Scanner sc) throws Exception{
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
