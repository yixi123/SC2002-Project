package models;

public class HDBOfficer extends User {
    private String assignedProject;

    public HDBOfficer(String nric, String password, int age, String maritalStatus) {
        super(nric, password, age, maritalStatus);
    }

    public void handleFlatBooking(String applicantNric, String flatType) {
        // Logic to handle flat booking
    }

    public void replyToEnquiry(String enquiryText, String reply) {
        // Logic to reply to enquiries
    }
}
