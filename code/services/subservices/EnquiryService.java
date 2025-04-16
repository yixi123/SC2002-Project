package services.subservices;

import database.dataclass.projects.EnquiryDB;
import java.util.ArrayList;
import java.util.List;
import models.projects.Enquiry;

public class EnquiryService {
    private static List<Enquiry> enquiries = new ArrayList<>();

    public static void addEnquiry(Enquiry enquiry) {
        enquiries = EnquiryDB.getDB();
        enquiries.add(enquiry);
        saveEnquiries();
    }

    public static int getNewId() {
        int lastId = enquiries.isEmpty() ? 0 : enquiries.get(enquiries.size() - 1).getId();
        return lastId + 1;
    }

    public static void saveEnquiries() {
        EnquiryDB.updateDB(enquiries);
    }

    public static void printEnquiry(int id) {
        Enquiry current = enquiries.stream()
                                   .filter(enquiry -> enquiry.getId() == id)
                                   .findFirst()
                                   .orElse(null);
        if (current == null) {
            System.out.println("Enquiry with ID " + id + " not found.");
            return;
        }

        System.out.println(current);
    }

    public static List<Enquiry> getEnquiriesByUserID(String userID) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getUserID().equalsIgnoreCase(userID)) {
                result.add(enquiry);
            }
        }
        return result;
    }

    public static List<Enquiry> getEnquiriesByProject(String project) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProjectID().equalsIgnoreCase(project)) {
                result.add(enquiry);
            }
        }
        return result;
    }
}
