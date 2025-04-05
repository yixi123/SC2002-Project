package models;

import java.util.Date;
import services.ProjectManager;

public class HDBManager extends User {
    private String managedProject;

    public HDBManager(String name, String nric, String password, int age, String maritalStatus) {
        super(name, nric, password, age, maritalStatus);
    }

    public void createProject(String projectName, String neighborhood, int twoRoomUnits, int threeRoomUnits, 
                              double sellingPriceForType1, double sellingPriceForType2, 
                              Date openingDate, Date closingDate) {
        BTOProject newProject = new BTOProject(
            projectName, neighborhood, twoRoomUnits, threeRoomUnits, 
            sellingPriceForType1, sellingPriceForType2, openingDate, closingDate, true
        );
        newProject.setManager(this.name);
        ProjectManager projectManager = new ProjectManager();
        projectManager.createProject(newProject);
        System.out.println("Project created successfully: " + projectName);
    }


    public void approveApplication(String applicantNric) {
        System.out.println("Application approved for NRIC: " + applicantNric);
        // Add logic to update the application status in the database or data structure
    }
}
