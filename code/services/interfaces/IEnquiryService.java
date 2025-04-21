package services.interfaces;

import java.util.List;
import java.util.Scanner;
import models.projects.Enquiry;

/**
 * Interface defining enquiry-related operations including adding,
 * editing, viewing, and replying to project enquiries.
 */
public interface IEnquiryService {

    /**
     * Retrieves all enquiries submitted by a specific user.
     * @param nric User's NRIC
     * @return list of enquiries
     */
    List<Enquiry> getMyEnquiries(String nric);

    /**
     * Allows user to select an enquiry from a list.
     * @param sc Scanner for input
     * @param enquiryList List of available enquiries
     * @return selected enquiry
     */
    Enquiry chooseFromEnquiryList(Scanner sc, List<Enquiry> enquiryList);

    /**
     * Adds a new enquiry to the system.
     * @param nric User's NRIC
     * @param projectName Name of the project
     * @param content Enquiry content
     */
    void addEnquiry(String nric, String projectName, String content);

    /**
     * Submits a reply to a given enquiry.
     * @param sc Scanner for input
     * @param selectedEnquiry The enquiry to reply to
     * @param replierID The NRIC of the replier
     */
    void replyEnquiry(Scanner sc, Enquiry selectedEnquiry, String replierID);

    /**
     * Deletes a specific enquiry.
     * @param selectedEnquiry The enquiry to delete
     */
    void deleteEnquiry(Enquiry selectedEnquiry);

    /**
     * Finds an enquiry by its unique ID.
     * @param id Enquiry ID
     * @return the matching enquiry
     */
    Enquiry findEnquiry(int id);

    /**
     * Allows the user to edit an existing enquiry.
     * @param sc Scanner for input
     * @param selectedEnquiry The enquiry to edit
     */
    void editEnquiry(Scanner sc, Enquiry selectedEnquiry);

    /**
     * Displays all enquiries in the system.
     */
    void viewAllEnquiries();

    /**
     * Displays all enquiries related to a specific project.
     * @param projectName The project name
     */
    void viewEnquiriesByProject(String projectName);

    /**
     * Retrieves a list of enquiries for a given project.
     * @param projectName The project name
     * @return list of related enquiries
     */
    List<Enquiry> getEnquiriesbyProject(String projectName);
}

    
    
        
