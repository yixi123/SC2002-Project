package models.users;

import archive.ProjectController;
import models.enums.MaritalStatus;
import models.projects.BTOProject;

public class HDBOfficer extends Applicant {
    private BTOProject assignedProject;

    public HDBOfficer(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
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
