package services.subservices;

import database.dataclass.projects.EnquiryDB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

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

    public void viewMyEnquiries(String userID) {
        List<Enquiry> myEnquiries = EnquiryDB.getEnquiriesByUserID(userID);
        if (myEnquiries.isEmpty()) {
            System.out.println("No enquiries found for user ID: " + userID);
        }else{
            System.out.println("My Enquiries:");
            System.out.println("---------------------------------------");
            myEnquiries.stream().forEach(enq -> System.out.println(enq.toString()));
        }
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
}
