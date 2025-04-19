package services.subservices;

import database.dataclass.projects.EnquiryDB;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import models.projects.Enquiry;
import services.interfaces.IEnquiryService;
import view.ViewFormatter;

public class EnquiryService implements IEnquiryService{

    public Enquiry chooseFromEnquiryList(Scanner sc, List<Enquiry> enquiryList) {
        do{
            if (enquiryList.isEmpty()) {
                System.out.println("You have no enquiries to view.");
            } else {
                System.out.println("Enquiries:");
                System.out.println(ViewFormatter.breakLine());
                for (int i = 0; i < enquiryList.size(); i++) {
                    System.out.println((i + 1) + ". " + enquiryList.get(i).toString());
                }
                System.out.println(ViewFormatter.breakLine());
                System.out.println("0. Back to menu");
                System.out.printf("Please select an enquiry [1 - %d]: ", enquiryList.size());
                System.out.println();
                System.out.println(ViewFormatter.breakLine());
                int enquiryChoice = sc.nextInt() - 1;
                sc.nextLine(); // Consume newline
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

    public List<Enquiry> getMyEnquiries(String userID) {
        List<Enquiry> myEnquiries = EnquiryDB.getEnquiriesByUserID(userID);
        return myEnquiries;
    }

    public List<Enquiry> getEnquiriesbyProject(String projectName) {
        List<Enquiry> enqList = EnquiryDB.getEnquiriesByProject(projectName);
        return enqList;
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

    public void viewEnquiriesByProject(String projectName){
        List<Enquiry> enqList = EnquiryDB.getEnquiriesByProject(projectName);
        if(enqList.isEmpty()){
            System.out.println("No enquiries found for project: " + projectName);
            return;
        }

        System.out.println("Enquiries for project: " + projectName);
        System.out.println(ViewFormatter.breakLine());
        enqList.stream()
            .forEach(enq -> {
                System.out.println(enq.toString(false));
                System.out.println(ViewFormatter.breakLine());;
            });
    }

    public void addEnquiry(String userID, String projectID, String content) {
        List<Enquiry> enquiries = EnquiryDB.getDB();
        Enquiry enquiry = new Enquiry(getNewId(), userID, projectID, content);
        enquiries.add(enquiry);
        EnquiryDB.updateDB(enquiries);
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

    public void editEnquiry(Scanner sc, Enquiry selectedEnquiry) {
        System.out.print("Enter your new enquiry content: ");
        String newContent = sc.nextLine();
        selectedEnquiry.setContent(newContent);
        EnquiryDB.updateDB(EnquiryDB.getDB());
        System.out.println("Enquiry updated successfully.");
    }

    public Enquiry findEnquiry(int id){
        return EnquiryDB.getDB().stream().filter(enquiry -> enquiry.getId() == id).findFirst().orElse(null);
    }


    public void replyEnquiry(int id, String replierID, String reply){
        List<Enquiry> enquiries = EnquiryDB.getDB();
        
        Enquiry enq = findEnquiry(id);
        int index = enquiries.indexOf(enq);

        enq.setReplierUserID(replierID);
        enq.setReplyContent(reply);
        enq.setReplierTimestamp(LocalDateTime.now());

        enquiries.set(index, enq);
        EnquiryDB.updateDB(enquiries);
    }

}
