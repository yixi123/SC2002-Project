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
 * FileLoader is the utility class for loading data from CSV files into Java objects.
 * Provides methods to load Applicants, Officers, Managers, Projects, Enquiries,
 * and both Project and Officer Applications from their respective file paths.
 * This class handles parsing, file reading, and mapping of raw CSV data into model instances.
 */
public class FileLoader implements IFileLoader {

    /**
     * File path constants for CSV data sources used in the system.
     * These paths point to the respective CSV files for loading and saving:
     * - Applicants
     * - Officers
     * - Managers
     * - BTO Projects
     * - Enquiries
     * - Project Applications
     * - Officer Applications
     */
    private static final String APPLICANTS_FILE = "code/database/csv/ApplicantList.csv";
    private static final String OFFICERS_FILE = "code/database/csv/OfficerList.csv";
    private static final String MANAGERS_FILE = "code/database/csv/ManagerList.csv";
    private static final String PROJECTS_FILE = "code/database/csv/ProjectList.csv";
    private static final String ENQUIRIES_FILE = "code/database/csv/EnquiresList.csv";
    private static final String PROJECT_APPLICATIONS_FILE = "code/database/csv/ProjectApplicationList.csv";
    private static final String OFFICER_APPLICATIONS_FILE = "code/database/csv/OfficerApplicationList.csv";

    /**
     * Loads a list of {@code Applicant} objects from the default file path.
     * @return List of applicants
     */
    public List<Applicant> loadApplicants() {
        return loadApplicants(APPLICANTS_FILE);
    }

    /**
     * Loads a list of {@code HDBOfficer} objects from the default file path.
     * @return List of HDB officers
     */
    public List<HDBOfficer> loadOfficers() {
        return loadOfficers(OFFICERS_FILE);
    }

    /**
     * Loads a list of {@code HDBManager} objects from the default file path.
     * @return List of HDB managers
     */
    public List<HDBManager> loadManagers() {
        return loadManagers(MANAGERS_FILE);
    }

    /**
     * Loads a list of {@code BTOProject} objects from the default file path.
     * @return List of BTO projects
     */
    public List<BTOProject> loadProjects() {
        return loadProjects(PROJECTS_FILE);
    }

    /**
     * Loads a list of {@code Enquiry} objects from the default file path.
     * @return List of enquiries
     */
    public List<Enquiry> loadEnquiries() {
        return loadEnquiries(ENQUIRIES_FILE);
    }

    /**
     * Loads a list of {@code ProjectApplication} objects from the default file path.
     * @return List of project applications
     */
    public List<ProjectApplication> loadProjectApplications() {
        return loadProjectApplications(PROJECT_APPLICATIONS_FILE);
    }

    /**
     * Loads a list of {@code OfficerApplication} objects from the default file path.
     * @return List of officer applications
     */
    public List<OfficerApplication> loadOfficerApplications() {
        return loadOfficerApplications(OFFICER_APPLICATIONS_FILE);
    }

    /**
     * Loads applicants from a specified CSV file path.
     * @param filePath The path to the applicant CSV file
     * @return List of applicants
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
     * Loads officers from a specified CSV file path.
     * @param filePath The path to the officer CSV file
     * @return List of officers
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
     * Loads managers from a specified CSV file path.
     * @param filePath The path to the manager CSV file
     * @return List of managers
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
     * Loads BTO projects from a specified CSV file path.
     * Parses project metadata, pricing, dates, and assigned officers.
     *
     * @param filePath The path to the project CSV file
     * @return List of BTO projects
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
     * Loads enquiries from a specified CSV file path.
     * Parses fields including timestamps and optional reply content.
     *
     * @param filePath The path to the enquiry CSV file
     * @return List of enquiries
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
     * Loads project applications from a specified CSV file path.
     * Parses application details including status, date, and flat type.
     *
     * @param filePath The path to the project application CSV file
     * @return List of project applications
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
     * Loads officer applications from a specified CSV file path.
     * Parses application date and status fields.
     *
     * @param filePath The path to the officer application CSV file
     * @return List of officer applications
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
