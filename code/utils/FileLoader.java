package utils;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.*;
import models.projects.Application;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;

public class FileLoader implements IFileLoader {

    private static final String APPLICANTS_FILE = "code/database/csv/ApplicantList.csv";
    private static final String OFFICERS_FILE = "code/database/csv/OfficerList.csv";
    private static final String MANAGERS_FILE = "code/database/csv/ManagerList.csv";
    private static final String PROJECTS_FILE = "code/database/csv/ProjectList.csv";
    private static final String ENQUIRIES_FILE = "code/database/csv/EnquiresList.csv";
    private static final String PROJECT_APPLICATIONS_FILE = "code/database/csv/ProjectApplicationList.csv";
    private static final String OFFICER_APPLICATIONS_FILE = "code/database/csv/OfficerApplicationList.csv";

    public static List<Applicant> loadApplicants() {
        return loadApplicants(APPLICANTS_FILE);
    }

    public static List<HDBOfficer> loadOfficers() {
        return loadOfficers(OFFICERS_FILE);
    }

    public static List<HDBManager> loadManagers() {
        return loadManagers(MANAGERS_FILE);
    }

    public static List<BTOProject> loadProjects() {
        return loadProjects(PROJECTS_FILE);
    }

    public static List<Enquiry> loadEnquiries() {
        return loadEnquiries(ENQUIRIES_FILE);
    }

    public static List<ProjectApplication> loadProjectApplications() {
        return loadProjectApplications(PROJECT_APPLICATIONS_FILE);
    }

    public static List<OfficerApplication> loadOfficerApplications() {
        return loadOfficerApplications(OFFICER_APPLICATIONS_FILE);
    }

    public static List<Applicant> loadApplicants(String filePath) {
        List<Applicant> applicants = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                applicants.add(new Applicant(data[0], data[1], data[4], Integer.parseInt(data[2]), data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicants;
    }

    public static List<HDBOfficer> loadOfficers(String filePath) {
        List<HDBOfficer> officers = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                officers.add(new HDBOfficer(data[0], data[1], data[4], Integer.parseInt(data[2]), data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return officers;
    }

    public static List<HDBManager> loadManagers(String filePath) {
        List<HDBManager> managers = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                managers.add(new HDBManager(data[0], data[1], data[4], Integer.parseInt(data[2]), data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return managers;
    }

    public static List<BTOProject> loadProjects(String filePath) {
        List<BTOProject> projects = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.readLine(); // Skip header
            String line;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                BTOProject project = new BTOProject(
                    data[0], data[1], Integer.parseInt(data[3]), Integer.parseInt(data[6]),
                    Double.parseDouble(data[4]), Double.parseDouble(data[7]),
                    dateFormat.parse(data[8]), dateFormat.parse(data[9]), Boolean.parseBoolean(data[13])
                );
                project.setManagerID(data[10]);
                project.setOfficerSlot(Integer.parseInt(data[11]));
                project.setOfficers(Arrays.asList(data[12].split(";")));
                projects.add(project);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public static List<Enquiry> loadEnquiries(String filePath) {
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
                    data[3],                  // Category
                    data[4],                  // Content
                    LocalDateTime.parse(data[5], formatter), // Timestamp
                    Integer.parseInt(data[6]) // ReplyID
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return enquiries;
    }

    public static List<ProjectApplication> loadProjectApplications(String filePath) {
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
                    data[2], 
                    dateFormat.parse(data[3]), 
                    data[4]
                ));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public static List<OfficerApplication> loadOfficerApplications(String filePath) {
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
                    data[2], 
                    dateFormat.parse(data[3])
                ));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return applications;
    }
}
