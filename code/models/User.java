package models;

import java.time.LocalDateTime;
import services.EnquiryService;
import services.ProjectApplicationService;
import services.ProjectController;

public abstract class User {
    protected String name;
    protected String nric;
    protected String password;
    protected int age;
    protected String maritalStatus;
    protected EnquiryService enquiryService = new EnquiryService();
    protected ProjectController projectController = new ProjectController();
    protected ProjectApplicationService applicationController = new ProjectApplicationService();

    public User(String name, String nric, String password, int age, String maritalStatus) {
        this.name = name;
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getRole() {
        if (this instanceof Applicant) {
            return "Applicant";
        } else if (this instanceof HDBOfficer) {
            return "HDB Officer";
        } else if (this instanceof HDBManager) {
            return "HDB Manager";
        } else {
            return "Unknown";
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void createEnquiry(String project, String content, EnquiryService enquiryManager, ProjectController projectManager) {
        boolean projectExists = projectManager.getProjects().stream()
                                              .anyMatch(p -> p.getProjectName().equalsIgnoreCase(project));
        if (!projectExists) {
            System.out.println("Project with name '" + project + "' does not exist.");
            return;
        }

        int newId = enquiryManager.getEnquiries().size() + 1; 
        Enquiry newEnquiry = new Enquiry(newId, this.name, project, "Enquiry", content, LocalDateTime.now(), 0);
        enquiryManager.addEnquiry(newEnquiry);
        System.out.println("Enquiry created successfully with ID: " + newId);
    }

    public void addReply(Enquiry originalEnquiry, String content, EnquiryService enquiryManager) {
        if (originalEnquiry.getReplyId() != 0) {
            System.out.println("This enquiry has already been replied to. Please check the original enquiry.");
            return;
        }
        int newId = enquiryManager.getEnquiries().size() + 1; // Generate a new ID for the reply
        Enquiry reply = new Enquiry(newId, this.name, originalEnquiry.getProject(), "Reply", content, LocalDateTime.now(), 0);
        originalEnquiry.setReplyId(newId); // Link the reply to the original enquiry
        enquiryManager.addEnquiry(reply);
        System.out.println("Reply added successfully with ID: " + newId);
    }
}
