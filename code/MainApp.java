import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import models.*;
import services.*;
import services.controller.AuthController;
import utils.*;

public class MainApp {
    static FilterSettings filterSettings = new FilterSettings(); // Initialize filter settings
    public static void main(String[] args) {
        AuthController loginManager = new AuthController();
        Scanner scanner = new Scanner(System.in);
        String nric, password;

        try {
            User user = loginManager.authenticate(nric, password);
            if (user != null) {
                System.out.println("Login successful! Welcome, " + user.getNric());
                System.out.println("Role: " + user.getRole());

                // Example: Password change functionality
                System.out.print("Do you want to change your password? (yes/no): ");
                String changePassword = scanner.nextLine();
                if (changePassword.equalsIgnoreCase("yes")) {
                    System.out.print("Enter old password: ");
                    String oldPassword = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    loginManager.updatePassword(user, oldPassword, newPassword);
                    System.out.println("Password updated successfully. Please log in again.");
                    return;
                }

                // Role-based navigation
                if (user instanceof Applicant) {
                    applicantMenu((Applicant) user, scanner);
                } else if (user instanceof HDBOfficer) {
                    hdbOfficerMenu((HDBOfficer) user, scanner);
                } else if (user instanceof HDBManager) {
                    hdbManagerMenu((HDBManager) user, scanner);
                }
            } else {
                System.out.println("Invalid NRIC or password.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void applicantMenu(Applicant applicant, Scanner scanner) {
        ApplicantController.st
    }

    private static void hdbOfficerMenu(HDBOfficer officer, Scanner scanner) {

        System.out.println("HDB Officer Menu:");
        int choice;
        do {
            System.out.println("1. View Projects");
            System.out.println("2. Apply for Project");
            System.out.println("3. View Application Status");
            System.out.println("4. Withdraw Application");
            System.out.println("5. Register to Handle a Project");
            System.out.println("6. View Status of Registration");
            System.out.println("7. View Details of Assigned Project");
            System.out.println("8. View and Reply to Enquiries");
            System.out.println("9. Update Flat Booking Details");
            System.out.println("10. Generate Receipt for Applicant");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1 -> officer.viewProjects();
                case 2 -> {
                    System.out.print("Enter project name to apply: ");
                    String projectName = scanner.nextLine();
                    System.out.print("Enter room type (2-Room or 3-Room): ");
                    String roomType = scanner.nextLine();
                    officer.registerForProject(projectName, roomType);
                }
                case 3 -> officer.viewApplicationStatus();
                case 4 -> officer.withdrawApplication();
                case 5 -> {
                    System.out.print("Enter project name to register as HDB Officer: ");
                    String projectName = scanner.nextLine();
                    // Logic to check criteria and register for the project
                }
                case 6 -> {
                    System.out.println("Registration Status:");
                    // Logic to display registration status
                }
                case 7 -> {
                    System.out.println("Details of Assigned Project:");
                    // Logic to display project details regardless of visibility
                }
                case 8 -> {
                    System.out.println("Enquiries for Assigned Project:");
                    // Logic to view and reply to enquiries
                    System.out.print("Enter enquiry ID to reply: ");
                    int enquiryId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter your reply: ");
                    String replyContent = scanner.nextLine();
                    // Logic to add reply to the enquiry
                }
                case 9 -> {
                    System.out.print("Enter applicant NRIC: ");
                    String applicantNric = scanner.nextLine();
                    System.out.print("Enter flat type (2-Room or 3-Room): ");
                    String flatType = scanner.nextLine();
                    // Logic to update flat booking details
                }
                case 10 -> {
                    System.out.print("Enter applicant NRIC to generate receipt: ");
                    String applicantNric = scanner.nextLine();
                    // Logic to generate and display receipt
                }
                case 11 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 11);
    }

    private static void hdbManagerMenu(HDBManager manager, Scanner scanner) {
        System.out.println("HDB Manager Menu:");
        // Add menu options for HDB Manager
    }
}
