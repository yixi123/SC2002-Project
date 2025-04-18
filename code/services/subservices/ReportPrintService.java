package services.subservices;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import models.projects.ProjectApplication;
import models.users.Applicant;

public class ReportPrintService {

    public void generateApplicantReport(Scanner sc, Map<Applicant, ProjectApplication> applicantsAndApplication) {
        
        System.out.print("Choose a filter catergory");
        System.out.println("1. Project Name");
        System.out.println("2. Flat Type");
        System.out.println("3. Marital Status");
        System.out.println("4. Age");
        System.out.print("Enter your choice: ");
        int filterChoice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (filterChoice) {
            case 1 -> {
                System.out.print("Enter project name: ");
                String projectName = sc.nextLine();
                applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                        .filter(entry -> entry.getValue().getProjectName().equalsIgnoreCase(projectName))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            case 2 -> {
                System.out.print("Enter flat type (1 for TWO_ROOM, 2 for THREE_ROOM): ");
                int flatTypeChoice = sc.nextInt();
                sc.nextLine(); // Consume newline
                String flatType = (flatTypeChoice == 1) ? "TWO_ROOM" : "THREE_ROOM";
                applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                        .filter(entry -> entry.getValue().getFlatType().toString().equalsIgnoreCase(flatType))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            case 3 -> {
                System.out.print("Enter marital status (1 for SINGLE, 2 for MARRIED): ");
                int maritalStatusChoice = sc.nextInt();
                sc.nextLine(); // Consume newline
                String maritalStatus = (maritalStatusChoice == 1) ? "SINGLE" : "MARRIED";
                applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                        .filter(entry -> entry.getKey().getMaritalStatus().toString().equalsIgnoreCase(maritalStatus))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            case 4 -> {
                System.out.print("Do you like to filter by age range? (yes/no): ");
                String filterByAgeRange = sc.nextLine();
                if (filterByAgeRange.equalsIgnoreCase("yes")) {
                    System.out.print("Enter minimum age: ");
                    int minAge = sc.nextInt();
                    System.out.print("Enter maximum age: ");
                    int maxAge = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> entry.getKey().getAge() >= minAge && entry.getKey().getAge() <= maxAge)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
                else {
                    System.out.print("Enter exact age: ");
                    int exactAge = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> entry.getKey().getAge() == exactAge)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
            }
            default -> System.out.println("Invalid choice. Returning to menu.");
        }

        System.out.println("A report of the list of applicants based on the filter category is as follows:");
        System.out.println("-------------------------------------------------");
        for (Applicant applicant : applicantsAndApplication.keySet()) {
            ProjectApplication application = applicantsAndApplication.get(applicant);
            System.out.println("Applicant Name: " + applicant.getName());
            System.out.println("Age: " + applicant.getAge());
            System.out.println("Marital Status: " + applicant.getMaritalStatus());
            System.out.println("Project Name: " + application.getProjectName());
            System.out.println("Flat Type: " + application.getFlatType());
            System.out.println("-------------------------------------------------");

        }
    }
}
