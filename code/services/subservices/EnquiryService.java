package services.subservices;

import database.dataclass.projects.EnquiryDB;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import models.projects.Enquiry;
import services.interfaces.IEnquiryService;

public class EnquiryService implements IEnquiryService{

    public void addEnquiry(String userID, String projectID, String content) {
        List<Enquiry> enquiries = EnquiryDB.getDB();
        Enquiry enquiry = new Enquiry(getNewId(), userID, projectID, content);
        enquiries.add(enquiry);
        EnquiryDB.updateDB(enquiries);
    }

    public int getNewId() {
        List<Enquiry> enquiries = EnquiryDB.getDB();
        int lastId = enquiries.isEmpty() ? 0 : enquiries.get(enquiries.size() - 1).getId();
        return lastId + 1;
    }

    public void viewAllEnquiries(){
        EnquiryDB.getDB().stream()
            .forEach(enq -> System.out.println(enq.toString()));
    }

    public void viewProjectEnquiries(String projectName){
        List<Enquiry> enqList = EnquiryDB.getEnquiriesByProject(projectName);
        if(enqList.isEmpty()){
            System.out.println("No enquiries found for project: " + projectName);
            return;
        }
        enqList.stream()
            .forEach(enq -> System.out.println(enq.toString()));
    }

    public List<Enquiry> getMyEnquiries(String userID) {
        List<Enquiry> myEnquiries = EnquiryDB.getEnquiriesByUserID(userID);
        return myEnquiries;
    }

    public void deleteEnquiry(int id) {
        List<Enquiry> enquiries = EnquiryDB.getDB();
        enquiries = EnquiryDB.getDB();
        Enquiry enq = findEnquiry(id);
        if (enq == null) {
            System.out.println("Enquiry [ID: " + id + "]  is NOT found!");
        } else {
            enquiries.remove(enq);
            System.out.println("Enquiry [ID: " + id + "] deleted successfully.");
            EnquiryDB.updateDB(enquiries);
        }
    }

    public void replyEnquiry(int id, String replierID, String reply){
        List<Enquiry> enquiries = EnquiryDB.getDB();
        
        Enquiry enq = findEnquiry(id);
        int index = enquiries.indexOf(enq);

        enq.setReplierUserID(replierID);
        enq.setReplyContent(reply);
        enq.setReplierTimestamp(LocalDateTime.now());

        enquiries.set(index, enq);
    }


    public Enquiry findEnquiry(int id){
        return EnquiryDB.getDB().stream().filter(enquiry -> enquiry.getId() == id).findFirst().orElse(null);
    }

    public Enquiry chooseFromEnquiryList(Scanner sc, List<Enquiry> enquiryList) {
        if (enquiryList.isEmpty()) {
            System.out.println("You have no enquiries to view.");
        } else {
            System.out.println("My Enquiries:");
            System.out.println("---------------------------------------");
            for (int i = 0; i < enquiryList.size(); i++) {
                System.out.println((i + 1) + ". " + enquiryList.get(i).toString());
            }
            System.out.println("---------------------------------------");
            System.out.println("0. Back to menu");
            System.out.print("Please select an enquiry by entering the corresponding number: ");

            int EnquiryChoice = sc.nextInt() - 1;
            sc.nextLine(); // Consume newline
            if (EnquiryChoice >= 0 && EnquiryChoice < enquiryList.size()) {

                Enquiry selectedEnquiry = enquiryList.get(EnquiryChoice);
                return selectedEnquiry;
            }
            else if (EnquiryChoice == -1) {
                System.out.println("Returning to menu.");
            } 
            else {
                System.out.println("Invalid enquiry choice. Returning to menu.");
            }
        }
        return null;
    }

    public void enquiryOption(Scanner sc, Enquiry selectedEnquiry) {
        Boolean isReplied = selectedEnquiry.getReplierUserID() != null;
        if (isReplied) {
            System.out.println("This enquiry has already been replied to.");
            System.out.println("Reply Content: " + selectedEnquiry.getReplyContent());
            System.out.println("Replied by: " + selectedEnquiry.getReplierUserID() + " on " + selectedEnquiry.getReplierTimestamp());
            return;
        } else {
            System.out.println("This enquiry has not been replied to yet.");
        }
        System.out.println("You can: ");
        System.out.println("1. Edit this enquiry");
        System.out.println("2. Delete this enquiry");
        System.out.print("Enter your choice: ");

        int actionChoice = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (actionChoice) {
            case 1 -> editEnquiry(sc, selectedEnquiry);
            case 2 -> deleteEnquiry(selectedEnquiry.getId());
            default -> System.out.println("Invalid choice. Returning to menu.");
        }
    }

    public void editEnquiry(Scanner sc, Enquiry selectedEnquiry) {
        System.out.print("Enter your new enquiry content: ");
        String newContent = sc.nextLine();
        selectedEnquiry.setContent(newContent);
        EnquiryDB.updateDB(EnquiryDB.getDB());
        System.out.println("Enquiry updated successfully.");
    }

    
}
