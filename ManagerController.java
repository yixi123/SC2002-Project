package services.controller;

import java.util.Scanner;

import services.interfaces.IProjectCreator;
import services.interfaces.IProjectEditor;
import services.interfaces.IProjectDeleter;
import services.interfaces.IApplicationHandler;
import services.interfaces.IReportGenerator;
import services.interfaces.IVisibilityToggler;
import services.interfaces.IEnquiryResponder;
import services.interfaces.IOfficerViewer;
import services.interfaces.ICommonView;
import services.interfaces.IOfficerRegistrationManager;

import services.subservices.ProjectCreator;
import services.subservices.ProjectEditor;
import services.subservices.ProjectDeleter;
import services.subservices.ApplicationHandler;
import services.subservices.ReportGenerator;
import services.subservices.VisibilityToggler;
import services.subservices.EnquiryResponder;
import services.subservices.OfficerViewer;
import services.subservices.AllProjectViewer;
import services.subservices.OwnProjectViewer;
import services.subservices.EnquiryView;
import services.subservices.OfficerRegistrationManager;

import models.*;

public class ManagerController extends UserController {
    private final HDBManager currentManager;

    // ----- Manager-specific services -----
    private final IProjectCreator projectCreator = new ProjectCreator();
    private final IProjectEditor projectEditor = new ProjectEditor(this);
    private final IProjectDeleter projectDeleter = new ProjectDeleter();
    private final IOfficerRegistrationManager officerRegistrationManager = new OfficerRegistrationManager(this);
    private final IApplicationHandler applicationHandler = new ApplicationHandler(this);
    private final IReportGenerator reportGenerator = new ReportGenerator(this);
    private final IVisibilityToggler visibilityToggler = new VisibilityToggler();
    private final IEnquiryResponder enquiryResponder = new EnquiryResponder();
    private final IOfficerViewer officerViewer = new OfficerViewer(this);
    private final ICommonView allProjectViewer = new AllProjectViewer();
    private final ICommonView ownProjectViewer = new OwnProjectViewer();
    private final ICommonView enquiryViewer = new EnquiryView();

    public ManagerController(HDBManager manager) {
        super(manager);
        this.currentManager = manager;
    }

    public HDBManager getCurrentManager() {
        return currentManager;
    }

    public void start(Scanner sc) {
        int option;
        do {
            System.out.println("\n📋 Manager Portal");
            System.out.println("-------------------------------------");
            System.out.println("1. Enter Project Portal");
            System.out.println("2. View My Projects");
            System.out.println("3. View All Projects");
            System.out.println("4. View Enquiries");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");
            option = sc.nextInt();

            switch (option) {
                case 1 -> enterProjectPortal(sc);
                case 2 -> ownProjectViewer.display();
                case 3 -> allProjectViewer.display();
                case 4 -> viewEnquiries(sc);
                case 5 -> System.out.println("👋 Logging out...");
                default -> System.out.println("❌ Invalid option.");
            }
        } while (option != 5);
    }

    private void enterProjectPortal(Scanner sc) {
        int option;
        do {
            System.out.println("\n🏗️ Project Management");
            System.out.println("-------------------------------------");
            System.out.println("1. Create BTO Project");
            System.out.println("2. Edit Project");
            System.out.println("3. Delete Project");
            System.out.println("4. Toggle Project Visibility");
            System.out.println("5. View Applicant List");
            System.out.println("6. View Officer Applications");
            System.out.println("7. Generate Report");
            System.out.println("8. Back");
            System.out.print("Please select an option: ");
            option = sc.nextInt();

            switch (option) {
                case 1 -> projectCreator.createProject();
                case 2 -> openProjectEditorMenu(sc);
                case 3 -> {
                    System.out.print("Enter project name to delete: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    projectDeleter.deleteProject(name);
                }
                case 4 -> {
                    System.out.print("Enter project name to toggle visibility: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    Project project = ProjectDB.findByName(name);
                    if (project != null) visibilityToggler.toggleVisibility(project);
                    else System.out.println("❌ Project not found.");
                }
               case 5 -> {
    System.out.print("Enter project name: ");
    sc.nextLine(); // clear buffer
    String pname = sc.nextLine();
    Project project = ProjectDB.findByName(pname);

    if (project == null) {
        System.out.println("❌ Project not found.");
        break;
    }

    List<Applicant> allApplicants = ApplicantDB.getAllApplicants();
    List<Applicant> projectApplicants = new ArrayList<>();

    // Filter applicants belonging to the project
    for (Applicant a : allApplicants) {
        if (a.getProject() != null && a.getProject().equals(project)) {
            projectApplicants.add(a);
        }
    }

    if (projectApplicants.isEmpty()) {
        System.out.println("⚠️ No applicants found for this project.");
        break;
    }

    // Display numbered list of applicants
    System.out.println("\n📋 Applicants for project: " + pname);
    for (int i = 0; i < projectApplicants.size(); i++) {
        System.out.println((i + 1) + ". " + projectApplicants.get(i).getName());
    }

    // Ask manager to choose index
    System.out.print("Enter the number of the applicant to approve: ");
    int choice = sc.nextInt();

    if (choice < 1 || choice > projectApplicants.size()) {
        System.out.println("❌ Invalid selection.");
        break;
    }

    // Approve selected applicant
    Applicant selected = projectApplicants.get(choice - 1);
    applicationHandler.approveApplication(selected);
}

                case 6 -> viewOfficerApplicantList(sc);
                case 7 -> {
                    System.out.print("Enter project name: ");
                    sc.nextLine();
                    String pname = sc.nextLine();
                    Project project = ProjectDB.findByName(pname);
                    if (project != null) {
                        ReportFilter filter = new ReportFilter(); // default filter
                        reportGenerator.generateReport(project, filter);
                    }
                }
                case 8 -> System.out.println("↩️ Returning...");
                default -> System.out.println("❌ Invalid option.");
            }
        } while (option != 8);
    }

    private void openProjectEditorMenu(Scanner sc) {
        int editOption;
        do {
            System.out.println("\n🛠️ Project Editor");
            System.out.println("1. Edit Units");
            System.out.println("2. Edit Dates");
            System.out.println("3. Edit Officer Slots");
            System.out.println("4. Back");
            System.out.print("Choose option: ");
            editOption = sc.nextInt();

            switch (editOption) {
                case 1 -> projectEditor.editUnits();
                case 2 -> projectEditor.editDates();
                case 3 -> projectEditor.editOfficerSlots();
                case 4 -> System.out.println("↩️ Back to project menu.");
                default -> System.out.println("❌ Invalid option.");
            }
        } while (editOption != 4);
    }

    private void viewOfficerApplicantList(Scanner sc) {
        System.out.print("Enter project name: ");
        sc.nextLine();
        String pname = sc.nextLine();
        Project project = ProjectDB.findByName(pname);

        if (project != null) {
            System.out.println("1. View Approved Officers");
            System.out.println("2. View Pending Officers");
            System.out.print("Choose: ");
            int subOpt = sc.nextInt();

            if (subOpt == 1) officerViewer.viewApprovedOfficers(project);
            else if (subOpt == 2) officerViewer.viewPendingOfficers(project);
            else System.out.println("❌ Invalid option.");
        } else {
            System.out.println("❌ Project not found.");
        }
    }

   private void viewEnquiries(Scanner sc) {
    System.out.print("Enter project name: ");
    sc.nextLine(); // clear buffer
    String name = sc.nextLine();
    Project project = ProjectDB.findByName(name);

    if (project == null) {
        System.out.println("❌ Project not found.");
        return;
    }

    // Get all enquiries for the project
    List<Enquiry> relatedEnquiries = new ArrayList<>();
    for (Enquiry e : EnquiryDB.getAllEnquiries()) {
        if (e.getProject().equals(project)) {
            relatedEnquiries.add(e);
        }
    }

    if (relatedEnquiries.isEmpty()) {
        System.out.println("📭 No enquiries found for this project.");
        return;
    }

    // Display enquiries with index
    System.out.println("📩 Enquiries for project: " + project.getProjectID());
    for (int i = 0; i < relatedEnquiries.size(); i++) {
        System.out.println("[" + i + "] " + relatedEnquiries.get(i));
    }

    // Ask if they want to respond
    System.out.print("Reply to an enquiry? (y/n): ");
    if (!sc.nextLine().equalsIgnoreCase("y")) return;

    System.out.print("Enter enquiry index to respond to: ");
    int index = sc.nextInt();
    sc.nextLine(); // clear buffer

    if (index < 0 || index >= relatedEnquiries.size()) {
        System.out.println("❌ Invalid index.");
        return;
    }

    Enquiry selected = relatedEnquiries.get(index);
    System.out.print("Enter your response: ");
    String response = sc.nextLine();

    enquiryResponder.replyToEnquiry(selected, response);

            }
        }
    }
}
