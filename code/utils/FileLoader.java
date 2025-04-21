package utils;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.enums.*;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;

/**
 * The FileLoader class provides methods to load data from CSV files into various models.
 * It supports loading applicants, officers, managers, projects, enquiries, and applications.
 */
public class FileLoader implements IFileLoader {

    private static final String APPLICANTS_FILE = "code/database/csv/ApplicantList.csv";
    private static final String OFFICERS_FILE = "code/database/csv/OfficerList.csv";
    private static final String MANAGERS_FILE = "code/database/csv/ManagerList.csv";
    private static final String PROJECTS_FILE = "code/database/csv/ProjectList.csv";
    private static final String ENQUIRIES_FILE = "code/database/csv/EnquiresList.csv";
    private static final String PROJECT_APPLICATIONS_FILE = "code/database/csv/ProjectApplicationList.csv";
    private static final String OFFICER_APPLICATIONS_FILE = "code/database/csv/OfficerApplicationList.csv";

    /**
     * Loads all applicants from the default file path.
     *
     * @return A list of Applicant objects.
     */
    public List<Applicant> loadApplicants() {
        return loadApplicants(APPLICANTS_FILE);
    }

    /**
     * Loads all officers from the default file path.
     *
     * @return A list of HDBOfficer objects.
     */
    public List<HDBOfficer> loadOfficers() {
        return loadOfficers(OFFICERS_FILE);
    }

    /**
     * Loads all managers from the default file path.
     *
     * @return A list of HDBManager objects.
     */
    public List<HDBManager> loadManagers() {
        return loadManagers(MANAGERS_FILE);
    }

    /**
     * Loads all projects from the default file path.
     *
     * @return A list of BTOProject objects.
     */
    public List<BTOProject> loadProjects() {
        return loadProjects(PROJECTS_FILE);
    }

    /**
     * Loads all enquiries from the default file path.
     *
     * @return A list of Enquiry objects.
     */
    public List<Enquiry> loadEnquiries() {
        return loadEnquiries(ENQUIRIES_FILE);
    }

    /**
     * Loads all project applications from the default file path.
     *
     * @return A list of ProjectApplication objects.
     */
    public List<ProjectApplication> loadProjectApplications() {
        return loadProjectApplications(PROJECT_APPLICATIONS_FILE);
    }

    /**
     * Loads all officer applications from the default file path.
     *
     * @return A list of OfficerApplication objects.
     */
    public List<OfficerApplication> loadOfficerApplications() {
        return loadOfficerApplications(OFFICER_APPLICATIONS_FILE);
    }

    /**
     * Loads applicants from a specified file path.
     *
     * @param filePath The path to the CSV file.
     * @return A list of Applicant objects.
     */
    public List<Applicant> loadApplicants(String filePath) {
        List<Applicant> applicants = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                applicants.add(new Applicant(data[0], data[1], data[4], Integer.parseInt(data[2]), MaritalStatus.valueOf(data[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicants;
    }

    /**
     * Loads officers from a specified file path.
     *
     * @param filePath The path to the CSV file.
     * @return A list of HDBOfficer objects.
     */
    public List<HDBOfficer> loadOfficers(String filePath) {
        List<HDBOfficer> officers = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                officers.add(new HDBOfficer(data[0], data[1], data[4], Integer.parseInt(data[2]), MaritalStatus.valueOf(data[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return officers;
    }

    /**
     * Loads managers from a specified file path.
     *
     * @param filePath The path to the CSV file.
     * @return A list of HDBManager objects.
     */
    public List<HDBManager> loadManagers(String filePath) {
        List<HDBManager> managers = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                managers.add(new HDBManager(data[0], data[1], data[4], Integer.parseInt(data[2]), MaritalStatus.valueOf(data[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return managers;
    }

    /**
     * Loads projects from a specified file path.
     *
     * @param filePath The path to the CSV file.
     * @return A list of BTOProject objects.
     */
    public List<BTOProject> loadProjects(String filePath) {
        List<BTOProject> projects = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                BTOProject project = new BTOProject(
                    data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[4]),
                    Double.parseDouble(data[3]), Double.parseDouble(data[5]),
                    dateFormat.parse(data[6]), dateFormat.parse(data[7]), data[8], Integer.parseInt(data[9]) , Boolean.parseBoolean(data[11])
                );
                project.setOfficers(new ArrayList<>(Arrays.asList(data[10].split(";"))));
                projects.add(project);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return projects;
    }

    /**
     * Loads enquiries from a specified file path.
     *
     * @param filePath The path to the CSV file.
     * @return A list of Enquiry objects.
     */
    public List<Enquiry> loadEnquiries(String filePath) {
        List<Enquiry> enquiries = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
    
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                enquiries.add(new Enquiry(
                    Integer.parseInt(data[0]), // ID
                    data[1],                  // User
                    data[2],                  // Project
                    data[3],                  // Content
                    LocalDateTime.parse(data[4], formatter), // Timestamp
                    data[5].equals("null") ? null : data[5],                  // ReplierUserID
                    data[6],                  // ReplyContent
                    data[7].equals("null") ? null : LocalDateTime.parse(data[7], formatter) // ReplierTimestamp
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return enquiries;
    }

    /**
     * Loads project applications from a specified file path.
     *
     * @param filePath The path to the CSV file.
     * @return A list of ProjectApplication objects.
     */
    public List<ProjectApplication> loadProjectApplications(String filePath) {
        List<ProjectApplication> applications = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                applications.add(new ProjectApplication(
                    data[0], 
                    data[1], 
                    ProjectAppStat.valueOf(data[2]), 
                    dateFormat.parse(data[3]), 
                    FlatType.valueOf(data[4]) 
                ));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return applications;
    }

    /**
     * Loads officer applications from a specified file path.
     *
     * @param filePath The path to the CSV file.
     * @return A list of OfficerApplication objects.
     */
    public List<OfficerApplication> loadOfficerApplications(String filePath) {
        List<OfficerApplication> applications = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                applications.add(new OfficerApplication(
                    data[0], 
                    data[1], 
                    OfficerAppStat.valueOf(data[2]), 
                    dateFormat.parse(data[3])
                ));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return applications;
    }
}
