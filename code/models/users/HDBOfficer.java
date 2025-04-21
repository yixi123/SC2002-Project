package models.users;

import java.util.Date;
import java.util.List;
import models.enums.MaritalStatus;
import models.projects.BTOProject;
import models.projects.OfficerApplication;

/**
 * Represents an HDB Officer in the BTO system.
 * An HDB Officer is also an applicant and may be assigned to BTO projects.
 */
public class HDBOfficer extends Applicant {

    /**
     * List of BTO projects assigned to this officer.
     */
    private List<BTOProject> assignedProjects;

    /**
     * The currently active BTO project for this officer, based on current date.
     */
    private BTOProject activeProject;

    /**
     * List of officer applications submitted by this user.
     */
    private List<OfficerApplication> officerApplications;

    /**
     * Constructs an HDBOfficer object.
     *
     * @param name           Officer's full name
     * @param nric           Officer's NRIC
     * @param password       Officer's password
     * @param age            Officer's age
     * @param maritalStatus  Officer's marital status
     */
    public HDBOfficer(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
    }

    /**
     * Gets the list of assigned BTO projects.
     *
     * @return list of BTOProject objects assigned to the officer
     */
    public List<BTOProject> getAssignedProject(){
        return assignedProjects;
    }

    /**
     * Sets the list of assigned BTO projects and updates the currently active project.
     *
     * @param assignedProject list of BTO projects assigned
     */
    public void setAssignedProject(List<BTOProject> assignedProject){
        this.assignedProjects = assignedProject;
        Date date = new Date();
        for (BTOProject project : assignedProject) {
            if (!project.getOpeningDate().after(date) && !project.getClosingDate().before(date)) {
                this.activeProject = project;
            }
        }
    }

    /**
     * Gets the active BTO project currently open and assigned to the officer.
     *
     * @return the active BTOProject object
     */
    public BTOProject getActiveProject() {
        return activeProject;
    }

    /**
     * Sets the currently active BTO project for this officer.
     *
     * @param activeProject the active project to assign
     */
    public void setActiveProject(BTOProject activeProject) {
        this.activeProject = activeProject;
    }

    /**
     * Gets the list of officer applications submitted by this officer.
     *
     * @return list of OfficerApplication objects
     */
    public List<OfficerApplication> getOfficerApplications() {
        return officerApplications;
    }

    /**
     * Sets the list of officer applications for this officer.
     *
     * @param officerApplications list of applications
     */
    public void setOfficerApplications(List<OfficerApplication> officerApplications) {
        this.officerApplications = officerApplications;
    }
}
