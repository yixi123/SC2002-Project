package models.users;

import database.dataclass.projects.ProjectDB;

import models.enums.MaritalStatus;
import models.projects.BTOProject;

public class HDBOfficer extends Applicant {
    private BTOProject assignedProject;

    public HDBOfficer(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        this.assignedProject = ProjectDB.getProjectByOfficer(nric);
    }

    public BTOProject getAssignedProject(){
        return assignedProject;
    }

    public void setAssignedProject(BTOProject assignedProject){
        this.assignedProject = assignedProject;
    }
}
