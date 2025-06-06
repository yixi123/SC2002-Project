package services.subservices;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.users.ApplicantDB;
import java.util.InputMismatchException;
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

/**
 * Service class responsible for generating detailed reports on booked project applications.
 * Reports can be filtered by applicant attributes such as flat type, marital status, and age.
 * The report displays information such as applicant name, age, marital status, and project/flat details.
 */
public class ReportPrintService implements IReportPrintService {

    /**
     * Generates a report of applicants who have successfully booked a flat
     * in the specified BTO project. The report is generated after applying
     * user-specified filters such as flat type, marital status, and age.
     *
     * @param sc Scanner for user input
     * @param project The BTOProject object for which the report is generated
     * @return A formatted string containing the filtered applicant report,
     * or null if no matching applicants are found
     */
    public String printReport(Scanner sc, BTOProject project) {
        
        List<ProjectApplication> projectApplications = ProjectAppDB.getApplicationsByProject(project.getProjectName());
        projectApplications = projectApplications.stream()
                .filter(app -> app.getStatus() == ProjectAppStat.BOOKED)
                .collect(Collectors.toList());
        if (projectApplications.isEmpty()) {
            System.out.println("No applicants found\n for the selected project.");
            return null;
        }

        Map<Applicant, ProjectApplication> applicantsAndApplication = projectApplications.stream()
                .collect(Collectors.toMap(app -> ApplicantDB.getApplicantByID(app.getUser()), app -> app));

        applicantsAndApplication = filterReportContent(sc, applicantsAndApplication);
        if (applicantsAndApplication.isEmpty()) {
            System.out.println("No applicants found for the selected filter criteria.");
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\nA report of the list of applicant based on the filter category is as follows:\n");
        System.out.println(ViewFormatter.breakLine());
        for (Applicant applicant : applicantsAndApplication.keySet()) {
            ProjectApplication application = applicantsAndApplication.get(applicant);
            sb.append("Applicant Name: " + applicant.getName()).append("\n")
                .append("Age: " + applicant.getAge()).append("\n")
                .append("Marital Status: " + applicant.getMaritalStatus()).append("\n")
                .append("Project Name: " + application.getProjectName()).append("\n")
                .append("Flat Type: " + application.getFlatType()).append("\n")
                .append(ViewFormatter.breakLine());
        }
        System.out.println(sb.toString());

        System.out.println("Report generated successfully.");


        return sb.toString();
    }

    /**
     * Applies user-selected filters to a given map of applicants and their project applications.
     * The following filters can be applied:
     * - Flat type: TWO_ROOM or THREE_ROOM
     * - Marital status: SINGLE or MARRIED
     * - Age: either a specific value or a range (minimum and maximum)
     *
     * @param sc Scanner for user input
     * @param applicantsAndApplication A map of applicants and their project applications
     * @return A filtered version of the original map based on the selected filter criteria
     */
    public Map<Applicant, ProjectApplication> filterReportContent(Scanner sc, Map<Applicant, ProjectApplication> applicantsAndApplication) {
        int filterChoice = 0;
        do{
            System.out.println("\nEdit filter category: ");
            System.out.println(ViewFormatter.breakLine());
            System.out.println("1. Flat Type");
            System.out.println("2. Marital Status");
            System.out.println("3. Age");
            System.out.println("0. Done and generate report");
            System.out.println(ViewFormatter.breakLine());
            System.out.print("Enter your choice: ");
            try {
                filterChoice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please try again!");
                continue;
            } finally {
                sc.nextLine(); // Consume newline
            }
            System.out.println(ViewFormatter.breakLine());

            switch (filterChoice) {
                case 1 -> {
                    System.out.print("Enter flat type\n (1 for TWO_ROOM, 2 for THREE_ROOM): ");
                    int flatTypeChoice;
                    try {
                        flatTypeChoice = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please try again!");
                        continue;
                    } finally {
                        sc.nextLine(); // Consume newline
                    }
                    String flatType = (flatTypeChoice == 1) ? "TWO_ROOM" : "THREE_ROOM";
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> entry.getValue().getFlatType().toString().equalsIgnoreCase(flatType))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
                case 2 -> {
                    System.out.print("Enter marital status\n (1 for SINGLE, 2 for MARRIED): ");
                    int maritalStatusChoice;
                    try {
                        maritalStatusChoice = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please try again!");
                        continue;
                    } finally {
                        sc.nextLine(); // Consume newline
                    }
                    String maritalStatus = (maritalStatusChoice == 1) ? "SINGLE" : "MARRIED";
                    applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                            .filter(entry -> entry.getKey().getMaritalStatus().toString().equalsIgnoreCase(maritalStatus))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
                case 3 -> {
                    System.out.print("Do you like to filter by age range?\n (yes/no): ");
                    String filterByAgeRange = sc.nextLine();
                    if (filterByAgeRange.equalsIgnoreCase("yes")) {
                        System.out.print("Enter minimum age: ");
                        int minAge;
                        try {
                            minAge = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please try again!");
                            continue;
                        } finally {
                            sc.nextLine(); // Consume newline
                        }
                        System.out.print("Enter maximum age: ");
                        int maxAge;
                        try {
                            maxAge = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please try again!");
                            continue;
                        } finally {
                            sc.nextLine(); // Consume newline
                        }
                        applicantsAndApplication = applicantsAndApplication.entrySet().stream()
                                .filter(entry -> entry.getKey().getAge() >= minAge && entry.getKey().getAge() <= maxAge)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    }
                    else {
                        System.out.print("Enter exact age: ");
                        int exactAge;
                        try {
                            exactAge = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please try again!");
                            continue;
                        } finally {
                            sc.nextLine(); // Consume newline
                        }
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
