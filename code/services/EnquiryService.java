package services;

import java.util.ArrayList;
import java.util.List;

import models.projects.Enquiry;
import utils.FileLoader;
import utils.FileSaver;

public class EnquiryService {
    private static List<Enquiry> enquiries = FileLoader.loadEnquiries("code/database/EnquiresList.csv");

    public static List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public static void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
        saveEnquiries();
    }

    public static int getNewId() {
        int lastId = enquiries.isEmpty() ? 0 : enquiries.get(enquiries.size() - 1).getId();
        return lastId + 1;
    }

    public static void saveEnquiries() {
        FileSaver.saveEnquiries("code/database/EnquiresList.csv", enquiries);
    }

    public static Enquiry printChat(int id) {
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

    public static List<Enquiry> getEnquiriesByUser(String user) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getCategory().equalsIgnoreCase("Enquiry") && enquiry.getUser().equalsIgnoreCase(user)) {
                result.add(enquiry);
            }
        }
        return result;
    }

    public static List<Enquiry> getEnquiriesByProject(String project) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getCategory().equalsIgnoreCase("Enquiry") && enquiry.getProject().equalsIgnoreCase(project)) {
                result.add(enquiry);
            }
        }
        return result;
    }
}
