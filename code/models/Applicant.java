package models;

import java.util.Date;
import java.util.List;
import services.ProjectApplicationManager;
import services.ProjectManager;

public class Applicant extends User {
    protected ProjectApplication projectApplication;
    protected ProjectApplicationManager projectApplicationManager = new ProjectApplicationManager();

    public Applicant(String name, String nric, String password, int age, String maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        projectApplication = projectApplicationManager.getApplicationByUser(name);
    }

    public void viewProjects() {
        System.out.println("Available BTO Projects:");
        for (BTOProject project : projectManager.getProjects()) {
            System.out.println(project);
        }
    }

    public void registerForProject(String projectName, String roomType, ProjectManager projectManager) {
        if (projectApplication != null) {
            System.out.println("You already applied for a project.");
            return;
        }

        if (projectManager.getProjects().stream().anyMatch(p -> p.getProjectName().equals(projectName))) {
            projectApplication = new ProjectApplication(this.name, projectName, "Pending", new Date(), roomType);
            projectApplicationManager.addApplication(projectApplication);
            System.out.println("Registration submitted for project: " + projectName);
        } else {
            System.out.println("Project not found.");
        }
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
            projectApplicationManager.updateApplicationStatus(this.name, projectApplication.getProjectName(), "Withdrawn");
            projectApplication = null;
        } else {
            System.out.println("No application to withdraw.");
        }
    }

    public List<Enquiry> viewEnquiries() {
        List<Enquiry> enquiries = enquiryManager.getEnquiriesByUser(this.name);
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
        this.createEnquiry(project, enquiryText, enquiryManager, projectManager);
        System.out.println("Enquiry submitted successfully.");
    }
}
