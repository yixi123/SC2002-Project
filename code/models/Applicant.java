package models;

import java.util.Date;
import java.util.List;
import services.EnquiryService;
import services.ProjectApplicationService;
import services.controller.ProjectController;

public class Applicant extends User {
    protected ProjectApplication projectApplication;

    public Applicant(String name, String nric, String password, int age, String maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        projectApplication = ProjectApplicationService.getApplicationByUser(nric);
    }
    
    public ProjectApplication getProjectApplication() {
        return projectApplication;
    }

    public void updateProjectApplication(ProjectApplication project){
        projectApplication = project;
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
