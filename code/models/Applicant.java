package models;

import java.util.List;

public class Applicant extends User {
    private List<String> enquiries;
    private String appliedProject;
    private String applicationStatus;

    public Applicant(String nric, String password, int age, String maritalStatus) {
        super(nric, password, age, maritalStatus);
        this.applicationStatus = "None";
    }

    public void viewProjects() {
        // Logic to view projects
    }

    public void viewApplicationStatus() {
        System.out.println("Application Status: " + applicationStatus);
    }

    public void withdrawApplication() {
        if (appliedProject != null) {
            System.out.println("Withdrawing application for project: " + appliedProject);
            appliedProject = null;
            applicationStatus = "None";
        } else {
            System.out.println("No application to withdraw.");
        }
    }

    public void submitEnquiry(String enquiryText) {
        enquiries.add(enquiryText);
        System.out.println("Enquiry submitted: " + enquiryText);
    }
}
