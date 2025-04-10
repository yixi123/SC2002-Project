package services;

import models.Enquiry;
import utils.FileLoader;
import utils.FileSaver;

import java.util.ArrayList;
import java.util.List;

public class EnquiryService {
    private static List<Enquiry> enquiries = FileLoader.loadEnquiries("code/database/EnquiresList.csv");

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
        saveEnquiries();
    }

    public void saveEnquiries() {
        FileSaver.saveEnquiries("code/database/EnquiresList.csv", enquiries);
    }

    public Enquiry printChat(int id) {
        Enquiry current = enquiries.stream()
                                   .filter(enquiry -> enquiry.getId() == id)
                                   .findFirst()
                                   .orElse(null);
        if (current == null) {
            System.out.println("Enquiry with ID " + id + " not found.");
            return null;
        }

        System.out.println(current);
        if (current.getReplyId() != 0) {
            return printChat(current.getReplyId());
        }
        return current; // Return the last printed enquiry
    }

    public List<Enquiry> getEnquiriesByUser(String user) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getCategory().equalsIgnoreCase("Enquiry") && enquiry.getUser().equalsIgnoreCase(user)) {
                result.add(enquiry);
            }
        }
        return result;
    }

    public List<Enquiry> getEnquiriesByProject(String project) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getCategory().equalsIgnoreCase("Enquiry") && enquiry.getProject().equalsIgnoreCase(project)) {
                result.add(enquiry);
            }
        }
        return result;
    }
}
