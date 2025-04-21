package models.users;

import java.util.List;
import models.enums.MaritalStatus;
import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;

/**
 * Represents an applicant user in the BTO system.
 * Applicants can manage and track their project applications.
 */
public class Applicant extends User {

    /**
     * List of project applications submitted by the applicant.
     */
    protected List<ProjectApplication> myApplications;

    /**
     * Constructs an Applicant object.
     *
     * @param name           Applicant's full name
     * @param nric           Applicant's NRIC
     * @param password       Applicant's password
     * @param age            Applicant's age
     * @param maritalStatus  Applicant's marital status
     */
    public Applicant(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
    }

    /**
     * Retrieves the list of project applications submitted by the applicant.
     *
     * @return list of ProjectApplication objects
     */
    public List<ProjectApplication> getMyApplication() {
        return myApplications;
    }

    /**
     * Sets or updates the list of project applications for the applicant.
     *
     * @param project list of ProjectApplication objects
     */
    public void setMyApplication(List<ProjectApplication> project){
        myApplications = project;
    }

    /**
     * Retrieves the current active (non-withdrawn, non-unsuccessful) application.
     *
     * @return the active ProjectApplication, or null if none found
     */
    public ProjectApplication getActiveApplication(){
        for (ProjectApplication application : myApplications) {
            if (application.getStatus() != ProjectAppStat.WITHDRAWN && application.getStatus() != ProjectAppStat.UNSUCCESSFUL) {
                return application;
            }
        }
        return null;
    }
}