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
                System.out.println("\nEnquiries:");
                System.out.println(ViewFormatter.breakLine());
                for (int i = 0; i < enquiryList.size(); i++) {
                    System.out.println((i + 1) + ". " + enquiryList.get(i).toString());
                }
                System.out.println(ViewFormatter.breakLine());
                System.out.println("0. Back to menu");
                System.out.printf("Please select an enquiry [0 - %d]: ", enquiryList.size());
                int enquiryChoice = sc.nextInt() - 1;
                sc.nextLine(); // Consume newline
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

        System.out.println("\nEnquiries for project: " + projectName);
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
