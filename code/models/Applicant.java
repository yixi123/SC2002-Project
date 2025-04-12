package models;

import java.util.Date;
import java.util.List;
import services.EnquiryService;
import services.ProjectApplicationService;
import services.ProjectController;

public class Applicant extends User {
    protected ProjectApplication projectApplication;

    public Applicant(String name, String nric, String password, int age, String maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        projectApplication = ProjectApplicationService.getApplicationByUser(nric);
    }

    public void viewProjects() {
        System.out.println("Available BTO Projects:");
        for (BTOProject project : ProjectController.getProjects()) {
            System.out.println(project);
        }
    }

    public void registerForProject(String projectName, String roomType) {
        if (projectApplication != null) {
            System.out.println("You already applied for a project.");
            return;
        }

        if (ProjectController.getProjects().stream().anyMatch(p -> p.getProjectName().equals(projectName))) {
            projectApplication = new ProjectApplication(this.name, projectName, "Pending", new Date(), roomType);
            ProjectApplicationService.addApplication(projectApplication);
            System.out.println("Registration submitted for project: " + projectName);
        } else {
            System.out.println("Project not found.");
        }
    }
    
    public ProjectApplication getProjectApplication() {
        return projectApplication;
    }

    public void viewApplicationStatus() {
        if (projectApplication == null) {
            System.out.println("No application found.");
            return;
        }
        System.out.println(projectApplication);
    }

    public void withdrawApplication() {
        if (projectApplication != null) {
            System.out.println("Withdrawing application for project: " + projectApplication.getProjectName());
            ProjectApplicationService.updateApplicationStatus(this.nric, projectApplication.getProjectName(), "Withdrawn");
            projectApplication = null;
        } else {
            System.out.println("No application to withdraw.");
        }
    }

    public List<Enquiry> viewEnquiries() {
        List<Enquiry> enquiries = EnquiryService.getEnquiriesByUser(this.name);
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries found.");
        } else {
            System.out.println("Your Enquiries:");
            for (Enquiry enquiry : enquiries) {
                System.out.println(enquiry);
            }
        }
        return enquiries;
    }

    public void submitEnquiry(String project, String enquiryText) {
        System.out.println("Submitting enquiry: " + enquiryText);
        this.createEnquiry(project, enquiryText);
        System.out.println("Enquiry submitted successfully.");
    }
}
