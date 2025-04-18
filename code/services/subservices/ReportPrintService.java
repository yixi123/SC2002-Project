package services.subservices;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.users.ApplicantDB;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.ProjectApplication;
import models.users.Applicant;

public class ReportPrintService {

    public void generateApplicantReport(Scanner sc, BTOProject project) {
        
        List<ProjectApplication> projectApplications = ProjectAppDB.getApplicationsByProject(project.getProjectName());
        projectApplications = projectApplications.stream()
                .filter(app -> app.getStatus() == ProjectAppStat.BOOKED)
                .collect(Collectors.toList());
        if (projectApplications.isEmpty()) {
            System.out.println("No applicants found for the selected project.");
            return;
        }

        Map<Applicant, ProjectApplication> applicantsAndApplication = projectApplications.stream()
                .collect(Collectors.toMap(app -> ApplicantDB.getApplicantByID(app.getUser()), app -> app));

        System.out.print("Choose a filter catergory");
        System.out.println("1. Flat Type");
        System.out.println("2. Marital Status");
        System.out.println("3. Age");
        System.out.print("Enter your choice: ");
        int filterChoice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (filterChoice) {
            case 1 -> {
                System.out.print("Enter Flat Type (1 for TWO_ROOM, 2 for THREE_ROOM): ");
                int flatTypeChoice = sc.nextInt();
                applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                        .filter(entry -> entry.getValue().getFlatType().ordinal() == flatTypeChoice - 1)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            case 2 -> {
                System.out.print("Enter Marital Status (1 for SINGLE, 2 for MARRIED): ");
                int maritalStatusChoice = sc.nextInt();
                applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                        .filter(entry -> entry.getKey().getMaritalStatus().ordinal() == maritalStatusChoice - 1)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            case 3 -> {
                // Choose to get age by range or exact age
                System.out.print("Do you want to filter by exact age (yes/no)? ");
                String exactAgeChoice = sc.nextLine().trim().toLowerCase();
                if (exactAgeChoice.equals("yes")) {
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> entry.getKey().getAge() == age)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                } else {
                    // Filter by age range
                    System.out.print("Enter minimum age: ");
                    int minAge = sc.nextInt();
                    System.out.print("Enter maximum age: ");
                    int maxAge = sc.nextInt();
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> {
                                int age = entry.getKey().getAge();
                                return age >= minAge && age <= maxAge;
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
            }
            default -> {
                System.out.println("Invalid choice. Returning to menu.");
                return;
            }
            
        }

        if (applicantsAndApplication.isEmpty()) {
            System.out.println("No applicants found for the selected filter.");
            return;
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
