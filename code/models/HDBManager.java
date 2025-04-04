package models;

public class HDBManager extends User {
    private String managedProject;

    public HDBManager(String name, String nric, String password, int age, String maritalStatus) {
        super(name, nric, password, age, maritalStatus);
    }

    public void createProject(String projectName, String neighborhood, int twoRoomUnits, int threeRoomUnits) {
        // Logic to create a new project
    }

    public void toggleProjectVisibility(String projectName) {
        // Logic to toggle project visibility
    }

    public void approveApplication(String applicantNric) {
        // Logic to approve an application
    }
}
