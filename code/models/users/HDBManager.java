package models.users;

import java.util.ArrayList;
import java.util.List;
import models.enums.MaritalStatus;
import models.projects.BTOProject;

/**
 * Represents an HDB Manager in the BTO system.
 * Managers are responsible for overseeing BTO projects.
 */
public class HDBManager extends User {

    /**
     * List of BTO projects managed by this manager.
     */
    private List<BTOProject> managedProjects;

    /**
     * Constructs an HDBManager object.
     *
     * @param name           Manager's full name
     * @param nric           Manager's NRIC
     * @param password       Manager's password
     * @param age            Manager's age
     * @param maritalStatus  Manager's marital status
     */
    public HDBManager(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        managedProjects = new ArrayList<>();
    }

    /**
     * Retrieves the list of BTO projects managed by this manager.
     *
     * @return list of BTOProject objects
     */
    public List<BTOProject> getManagedProjectsList(){
        return managedProjects;
    }

    /**
     * Sets or updates the list of BTO projects managed by this manager.
     *
     * @param projectList list of BTOProject objects
     */
    public void setManagedProjectsList(List<BTOProject> projectList){
        managedProjects = projectList;
    }
}
