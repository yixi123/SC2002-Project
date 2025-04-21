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
import services.interfaces.IReportPrintService;
import view.ViewFormatter;

public class ReportPrintService implements IReportPrintService {

    public void printReport(Scanner sc, BTOProject project) {
        
        List<ProjectApplication> projectApplications = ProjectAppDB.getApplicationsByProject(project.getProjectName());
        projectApplications = projectApplications.stream()
                .filter(app -> app.getStatus() == ProjectAppStat.BOOKED)
                .collect(Collectors.toList());
        if (projectApplications.isEmpty()) {
            System.out.println("No applicants found\n for the selected project.");
            return;
        }

        Map<Applicant, ProjectApplication> applicantsAndApplication = projectApplications.stream()
                .collect(Collectors.toMap(app -> ApplicantDB.getApplicantByID(app.getUser()), app -> app));

        applicantsAndApplication = filterReportContent(sc, applicantsAndApplication);
        if (applicantsAndApplication.isEmpty()) {
            System.out.println("No applicants found for the selected filter criteria.");
            return;
        }


        System.out.println("\nA report of the list of applicants\n based on the filter category is as follows:");
        System.out.println(ViewFormatter.breakLine());
        for (Applicant applicant : applicantsAndApplication.keySet()) {
            ProjectApplication application = applicantsAndApplication.get(applicant);
            System.out.println("Applicant Name: " + applicant.getName());
            System.out.println("Age: " + applicant.getAge());
            System.out.println("Marital Status: " + applicant.getMaritalStatus());
            System.out.println("Project Name: " + application.getProjectName());
            System.out.println("Flat Type: " + application.getFlatType());
            System.out.println(ViewFormatter.breakLine());

        }

        System.out.println("Report generated successfully.");
    }

    public Map<Applicant, ProjectApplication> filterReportContent(Scanner sc, Map<Applicant, ProjectApplication> applicantsAndApplication) {
        int filterChoice = 0;
        do{
            System.out.println("\nChoose a filter category: ");
            System.out.println(ViewFormatter.breakLine());
            System.out.println("1. Project Name");
            System.out.println("2. Flat Type");
            System.out.println("3. Marital Status");
            System.out.println("4. Age");
            System.out.println("0. Done");
            System.out.println(ViewFormatter.breakLine());
            System.out.print("Enter your choice: ");
            filterChoice = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println(ViewFormatter.breakLine());

            switch (filterChoice) {
                case 1 -> {
                    System.out.print("Enter project name: ");
                    String projectName = sc.nextLine();
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> entry.getValue().getProjectName().equalsIgnoreCase(projectName))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
                case 2 -> {
                    System.out.print("Enter flat type\n (1 for TWO_ROOM, 2 for THREE_ROOM): ");
                    int flatTypeChoice = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    String flatType = (flatTypeChoice == 1) ? "TWO_ROOM" : "THREE_ROOM";
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> entry.getValue().getFlatType().toString().equalsIgnoreCase(flatType))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
                case 3 -> {
                    System.out.print("Enter marital status\n (1 for SINGLE, 2 for MARRIED): ");
                    int maritalStatusChoice = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    String maritalStatus = (maritalStatusChoice == 1) ? "SINGLE" : "MARRIED";
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> entry.getKey().getMaritalStatus().toString().equalsIgnoreCase(maritalStatus))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
                case 4 -> {
                    System.out.print("Do you like to filter by age range?\n (yes/no): ");
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
                case 0 -> System.out.println("Done filtering. Generating report...");
                default -> System.out.println("Invalid choice! Please choose provided options!");
            }
        }while(filterChoice != 0);

        return applicantsAndApplication;
    }


}
