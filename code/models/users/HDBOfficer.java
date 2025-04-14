package models.users;

import models.projects.BTOProject;
import services.controller.ProjectController;

public class HDBOfficer extends Applicant {
    private BTOProject assignedProject;

    public HDBOfficer(String name, String nric, String password, int age, String maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        this.assignedProject = ProjectController.getAssignedProjectByOfficer(name);
    }

    public BTOProject getAssignedProject(){
        return assignedProject;
    }

    public void setAssignedProject(BTOProject assignedProject){
        this.assignedProject = assignedProject;
    }
}
