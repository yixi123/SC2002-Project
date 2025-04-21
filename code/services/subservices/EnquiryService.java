package services.subservices;

import database.dataclass.projects.EnquiryDB;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import models.projects.Enquiry;
import services.interfaces.IEnquiryService;
import view.ViewFormatter;

/**
 * Service class that handles all enquiry-related operations such as
 * adding, viewing, editing, replying to, and deleting enquiries.
 * It interacts with the {@code EnquiryDB} for data persistence.
 */
public class EnquiryService implements IEnquiryService{

    /**
     * Displays a list of enquiries and allows the user to select one for viewing.
     *
     * @param sc Scanner to capture user input
     * @param enquiryList The list of enquiries available for selection
     * @return The selected {@code Enquiry}, or null if the user cancels
     */
    public Enquiry chooseFromEnquiryList(Scanner sc, List<Enquiry> enquiryList) {
        do{
            if (enquiryList.isEmpty()) {
                System.out.println("You have no enquiries to view.");
            } else {
                System.out.println("\nEnquiries:");
                System.out.println(ViewFormatter.breakLine());
                for (int i = 0; i < enquiryList.size(); i++) {
                    System.out.println((i + 1) + ". " + enquiryList.get(i).toString());
                }
                System.out.println(ViewFormatter.breakLine());
                System.out.println("0. Back to menu");
                System.out.printf("Please select an enquiry [0 - %d]: ", enquiryList.size());
                int enquiryChoice;
                try {
                    enquiryChoice = sc.nextInt() - 1;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please try again!");
                    continue;
                } finally {
                    sc.nextLine(); // Consume newline
                }
                System.out.println(ViewFormatter.breakLine());
                if (enquiryChoice >= 0 && enquiryChoice < enquiryList.size()) {

                    Enquiry selectedEnquiry = enquiryList.get(enquiryChoice);
                    return selectedEnquiry;
                }
                else if (enquiryChoice == -1) {
                    System.out.println("Returning to menu."); break;
                } 
                else {
                    System.out.println("Invalid enquiry choice. Try again!");
                }
            }
        } while(true);
        return null;

    }

    /**
     * Retrieves all enquiries submitted by a user with the given NRIC.
     *
     * @param userID The NRIC of the user
     * @return A list of that user's enquiries
     */
    public List<Enquiry> getMyEnquiries(String userID) {
        List<Enquiry> myEnquiries = EnquiryDB.getEnquiriesByUserID(userID);
        return myEnquiries;
    }

    /**
     * Retrieves all enquiries submitted under a given project.
     *
     * @param projectName The name of the project
     * @return A list of enquiries associated with the project
     */
    public List<Enquiry> getEnquiriesbyProject(String projectName) {
        List<Enquiry> enqList = EnquiryDB.getEnquiriesByProject(projectName);
        return enqList;
    }

    /**
     * Gets the next available enquiry ID based on the latest ID in the database.
     *
     * @return The next valid enquiry ID
     */
    public int getNewId() {
        List<Enquiry> enquiries = EnquiryDB.getDB();
        int lastId = enquiries.isEmpty() ? 0 : enquiries.get(enquiries.size() - 1).getId();
        return lastId + 1;
    }

    /**
     * Displays all enquiries stored in the system without filters.
     */
    public void viewAllEnquiries(){
        EnquiryDB.getDB().stream()
            .forEach(enq -> System.out.println(enq.toString()));
    }

    /**
     * Displays all enquiries related to a specific project, with formatting.
     *
     * @param projectName The name of the project
     */
    public void viewEnquiriesByProject(String projectName){
        List<Enquiry> enqList = EnquiryDB.getEnquiriesByProject(projectName);
        if(enqList.isEmpty()){
            System.out.println("No enquiries found for project: " + projectName);
            return;
        }

        System.out.println("\nEnquiries for project: " + projectName);
        System.out.println(ViewFormatter.breakLine());
        enqList.stream()
            .forEach(enq -> {
                System.out.println(enq.toString(false));
                System.out.println(ViewFormatter.breakLine());;
            });
    }

    /**
     * Adds a new enquiry to the system for the specified user and project.
     *
     * @param userID The NRIC of the user submitting the enquiry
     * @param projectID The name of the project the enquiry is for
     * @param content The content of the enquiry
     */
    public void addEnquiry(String userID, String projectID, String content) {
        List<Enquiry> enquiries = EnquiryDB.getDB();
        Enquiry enquiry = new Enquiry(getNewId(), userID, projectID, content);
        enquiries.add(enquiry);
        EnquiryDB.updateDB(enquiries);
    }

    /**
     * Deletes the selected enquiry from the database.
     *
     * @param selectedEnquiry The enquiry to delete
     */
    public void deleteEnquiry(Enquiry selectedEnquiry) {
        List<Enquiry> enquiries = EnquiryDB.getDB();
        enquiries = EnquiryDB.getDB();
        int id = selectedEnquiry.getId();
        Enquiry enq = findEnquiry(id);
        if (enq == null) {
            System.out.println("Enquiry [ID: " + id + "]  is NOT found!");
        } else {
            enquiries.remove(enq);
            System.out.println("Enquiry [ID: " + id + "] deleted successfully.");
            EnquiryDB.updateDB(enquiries);
        }
    }

    /**
     * Edits the content of an existing enquiry.
     *
     * @param sc Scanner for new content input
     * @param selectedEnquiry The enquiry to be edited
     */
    public void editEnquiry(Scanner sc, Enquiry selectedEnquiry) {
        System.out.print("Enter your new enquiry content: ");
        String newContent = sc.nextLine();
        selectedEnquiry.setContent(newContent);
        EnquiryDB.updateDB(EnquiryDB.getDB());
        System.out.println("Enquiry updated successfully.");
    }

    /**
     * Finds and returns an enquiry by its ID.
     *
     * @param id The ID of the enquiry to find
     * @return The matching {@code Enquiry}, or null if not found
     */
    public Enquiry findEnquiry(int id){
        return EnquiryDB.getDB().stream().filter(enquiry -> enquiry.getId() == id).findFirst().orElse(null);
    }

    /**
     * Allows a manager or officer to reply to a selected enquiry.
     * Adds the reply content, reply timestamp, and replier ID.
     *
     * @param sc Scanner for reply content
     * @param selectedEnquiry The enquiry to reply to
     * @param replierID The NRIC of the officer or manager replying
     */
    public void replyEnquiry(Scanner sc, Enquiry selectedEnquiry, String replierID){
        List<Enquiry> enquiries = EnquiryDB.getDB();
        
        Enquiry enq = findEnquiry(selectedEnquiry.getId());
        int index = enquiries.indexOf(enq);

        do{
            try{
                System.out.print("Enter your reply content: ");
                String replyContent = sc.nextLine();
                System.out.println(ViewFormatter.breakLine());
                enq.setReplierUserID(replierID);
                enq.setReplyContent(replyContent);
                enq.setReplierTimestamp(LocalDateTime.now());
                enquiries.set(index, enq);
                EnquiryDB.updateDB(enquiries);
                System.out.println("Reply sent successfully.");
                System.out.println(ViewFormatter.breakLine());
                break;
            }catch(Exception e){
              System.out.println("Error: " + e.getMessage());
              System.out.println("Unsuccessful Reply, Please try again.");
            }
        }while(true);
    }

        
}
