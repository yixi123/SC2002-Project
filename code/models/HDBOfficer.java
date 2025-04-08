package models;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import services.EnquiryService;
import services.OfficerApplicationService;
import services.ProjectController;

public class HDBOfficer extends Applicant {
    private OfficerApplicationService officerApplicationManager = new OfficerApplicationService();
    private List<Application> projectApplicationsAsOfficer;
    private BTOProject assignedProject;

    public HDBOfficer(String name, String nric, String password, int age, String maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        this.projectApplicationsAsOfficer = officerApplicationManager.getApplicationsByUser(name);
        this.assignedProject = projectController.getAssignedProjectByOfficer(name);
    }

    public void registerToHandleProject(BTOProject project) {
        if (projectApplicationsAsOfficer.stream().anyMatch(app -> app.getProjectName().equals(project.getProjectName()))) {
            System.out.println("You are already registered to handle this project.");
            return;
        }

        String projectName = project.getProjectName();
        Application projectApplication = new Application(name, projectName, "Pending", new Date());
        projectApplicationsAsOfficer.add(projectApplication);
        officerApplicationManager.addApplication(projectApplication);
        System.out.println("Registration to handle project submitted successfully.");
    }

    public void viewRegistrationStatus() {
        if (projectApplicationsAsOfficer == null) {
            System.out.println("No registration found.");
            return;
        }
        for (Application application : projectApplicationsAsOfficer) {
            System.out.println(application);
        }
    }

    public void viewAssignedProjectDetails(ProjectController projectManager) {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        System.out.println(assignedProject);
    }

    public void viewAndReplyToEnquiries(EnquiryService enquiryManager, Scanner scanner) {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        List<Enquiry> enquiries = enquiryManager.getEnquiriesByProject(assignedProject.getProjectName());
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries for the assigned project.");
            return;
        }

        for (int i = 0; i < enquiries.size(); i++) {
            System.out.println((i + 1) + ". " + enquiries.get(i));
        }

        System.out.print("Enter the number of the enquiry to reply: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (choice > 0 && choice <= enquiries.size()) {
            System.out.print("Enter your reply: ");
            String replyContent = scanner.nextLine();
            addReply(enquiries.get(choice - 1), replyContent, enquiryManager);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void updateFlatBooking(String nric) {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        ProjectApplication projectApplication = projectApplicationService.getApplicationByUser(nric);

        if (projectApplication == null) {
            System.out.println("Applicantion not found.");
            return;
        }
        
        String flatType = projectApplication.getFlatType();
        String applicationProject = projectApplication.getProjectName();

        if (applicationProject.equals(assignedProject.getProjectName())) {
            System.out.println("Applicant's application is not for the assigned project.");
            return;
        }

        if (!projectApplication.getStatus().equals("successful")) {
            System.out.println("Applicant's application is not successful.");
            return;
        }

        if (flatType.equals("2-room")){
            if (assignedProject.getTwoRoomUnits() <= 0) {
                System.out.println("Flat booking for 2-room is not available.");
                return;
            } else {
                assignedProject.setTwoRoomUnits(assignedProject.getTwoRoomUnits() - 1);
            }
        }  else if (flatType.equals("3-room")){
            if (assignedProject.getThreeRoomUnits() <= 0) {
                System.out.println("Flat booking for 3-room is not available.");
                return;
            } else {
                assignedProject.setThreeRoomUnits(assignedProject.getThreeRoomUnits() - 1);
            }
        } 
        projectApplication.setStatus("Booked");
        projectApplicationService.updateApplicationStatus(name, applicationProject, "Booked");
        System.out.println("Flat booking updated successfully.");

    }
}
