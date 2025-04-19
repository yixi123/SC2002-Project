package models.users;

import java.util.Date;
import java.util.List;
import models.enums.MaritalStatus;
import models.enums.OfficerAppStat;
import models.projects.BTOProject;
import models.projects.OfficerApplication;

public class HDBOfficer extends Applicant {
    private List<BTOProject> assignedProjects;
    private BTOProject activeProject;
    private List<OfficerApplication> officerApplications;

    public HDBOfficer(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
    }

    public List<BTOProject> getAssignedProject(){
        return assignedProjects;
    }

    public void setAssignedProject(List<BTOProject> assignedProject){
        this.assignedProjects = assignedProject;
        Date date = new Date();
        for (BTOProject project : assignedProject) {
            if (!project.getOpeningDate().after(date) && !project.getClosingDate().before(date)) {
                this.activeProject = project;
            }
        }
    }

    public BTOProject getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(BTOProject activeProject) {
        this.activeProject = activeProject;
    }

    public List<OfficerApplication> getOfficerApplications() {
        return officerApplications;
    }

    public void setOfficerApplications(List<OfficerApplication> officerApplications) {
        this.officerApplications = officerApplications;
    }
}
