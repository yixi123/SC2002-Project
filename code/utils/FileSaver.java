package utils;

import java.io.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;

/**
 * FileSaver is a class responsible for saving various types of data (e.g., applicants,
 * officers, managers, projects, enquiries, and applications) into their respective
 * CSV files.
 * This class supports writing to both default file paths and user-specified paths.
 */
public class FileSaver implements IFileSaver {

    /**
     * File path constants for saving CSV data.
     * These include default paths for Applicants, Officers, Managers, Projects,
     * Enquiries, Project Applications, and Officer Applications.
     */
    private static final String APPLICANTS_FILE = "code/database/csv/ApplicantList.csv";
    private static final String OFFICERS_FILE = "code/database/csv/OfficerList.csv";
    private static final String MANAGERS_FILE = "code/database/csv/ManagerList.csv";
    private static final String PROJECTS_FILE = "code/database/csv/ProjectList.csv";
    private static final String ENQUIRIES_FILE = "code/database/csv/EnquiresList.csv";
    private static final String PROJECT_APPLICATIONS_FILE = "code/database/csv/ProjectApplicationList.csv";
    private static final String OFFICER_APPLICATIONS_FILE = "code/database/csv/OfficerApplicationList.csv";

    /**
     * Saves a list of applicants to the default file path.
     * @param applicants The list of Applicant objects to save
     */
    public void saveApplicants(List<Applicant> applicants) {
        saveApplicants(APPLICANTS_FILE, applicants);
    }

    /**
     * Saves a list of officers to the default file path.
     * @param officers The list of HDBOfficer objects to save
     */
    public void saveOfficers(List<HDBOfficer> officers) {
        saveOfficers(OFFICERS_FILE, officers);
    }

    /**
     * Saves a list of managers to the default file path.
     * @param managers The list of HDBManager objects to save
     */
    public void saveManagers(List<HDBManager> managers) {
        saveManagers(MANAGERS_FILE, managers);
    }

    /**
     * Saves a list of BTO projects to the default file path.
     * @param projects The list of BTOProject objects to save
     */
    public void saveProjects(List<BTOProject> projects) {
        saveProjects(PROJECTS_FILE, projects);
    }

    /**
     * Saves a list of enquiries to the default file path.
     * @param enquiries The list of Enquiry objects to save
     */
    public void saveEnquiries(List<Enquiry> enquiries) {
        saveEnquiries(ENQUIRIES_FILE, enquiries);
    }

    /**
     * Saves a list of project applications to the default file path.
     * @param applications The list of ProjectApplication objects to save
     */
    public void saveProjectApplications(List<ProjectApplication> applications) {
        saveProjectApplications(PROJECT_APPLICATIONS_FILE, applications);
    }

    /**
     * Saves a list of officer applications to the default file path.
     * @param applications The list of OfficerApplication objects to save
     */
    public void saveOfficerApplications(List<OfficerApplication> applications) {
        saveOfficerApplications(OFFICER_APPLICATIONS_FILE, applications);
    }

    /**
     * Saves applicants to the specified file path.
     * @param filePath The CSV file path to save to
     * @param applicants The list of Applicant objects
     */
    public void saveApplicants(String filePath, List<Applicant> applicants) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Name,NRIC,Age,Marital Status,Password\n"); // Header
            for (Applicant applicant : applicants) {
                bw.write(String.format("%s,%s,%d,%s,%s\n",
                        applicant.getName(),
                        applicant.getNric(),
                        applicant.getAge(),
                        applicant.getMaritalStatus(),
                        applicant.getPassword()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves officers to the specified file path.
     * @param filePath The CSV file path to save to
     * @param officers The list of HDBOfficer objects
     */
    public void saveOfficers(String filePath, List<HDBOfficer> officers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Name,NRIC,Age,Marital Status,Password\n"); // Header
            for (HDBOfficer officer : officers) {
                bw.write(String.format("%s,%s,%d,%s,%s\n",
                        officer.getName(),
                        officer.getNric(),
                        officer.getAge(),
                        officer.getMaritalStatus(),
                        officer.getPassword()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves managers to the specified file path.
     * @param filePath The CSV file path to save to
     * @param managers The list of HDBManager objects
     */
    public void saveManagers(String filePath, List<HDBManager> managers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Name,NRIC,Age,Marital Status,Password\n"); // Header
            for (HDBManager manager : managers) {
                bw.write(String.format("%s,%s,%d,%s,%s\n",
                        manager.getName(),
                        manager.getNric(),
                        manager.getAge(),
                        manager.getMaritalStatus(),
                        manager.getPassword()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves projects to the specified file path.
     * Includes project metadata, unit pricing, officer assignments, and visibility.
     *
     * @param filePath The CSV file path to save to
     * @param projects The list of BTOProject objects
     */
    public void saveProjects(String filePath, List<BTOProject> projects) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Project Name,Neighborhood,Number of units for 2-room,Selling price for 2-room,Number of units for 3-room,Selling price for 3-room,Application opening date,Application closing date,Manager,Officer Slot,Officer,Visibility\n"); // Updated header
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (BTOProject project : projects) {
                bw.write(String.format("%s,%s,%d,%.2f,%d,%.2f,%s,%s,%s,%d,%s,%b\n",
                        project.getProjectName(),
                        project.getNeighborhood(),
                        project.getTwoRoomUnits(),
                        project.getSellingPriceForType1(),
                        project.getThreeRoomUnits(),
                        project.getSellingPriceForType2(),
                        dateFormat.format(project.getOpeningDate()),
                        dateFormat.format(project.getClosingDate()),
                        project.getManagerID(),
                        project.getOfficerSlot(),
                        String.join(";", project.getOfficers()),
                        project.isVisible()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves enquiries to the specified file path.
     * Includes user and project references, timestamps, and reply metadata.
     *
     * @param filePath The CSV file path to save to
     * @param enquiries The list of Enquiry objects
     */
    public void saveEnquiries(String filePath, List<Enquiry> enquiries) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("ID,UserID,Project,Content,timestamp,ReplierUserID,ReplyContent,ReplierTimestamp\n"); // Header
            for (Enquiry enquiry : enquiries) {
                bw.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s\n",
                    enquiry.getId(),
                    enquiry.getUserID(),
                    enquiry.getProjectID(),
                    enquiry.getContent(),
                    enquiry.getTimestamp().format(formatter), // Format timestamp
                    enquiry.getReplierUserID(),
                    enquiry.getReplyContent(),
                    enquiry.getReplierTimestamp() != null ? enquiry.getReplierTimestamp().format(formatter) : "null" // Format replier timestamp
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves project applications to the specified file path.
     * Includes applicant, project name, status, application date, and flat type.
     *
     * @param filePath The CSV file path to save to
     * @param applications The list of ProjectApplication objects
     */
    public void saveProjectApplications(String filePath, List<ProjectApplication> applications) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("UserID,Project,Status,Apply Date,Flat Type\n"); // Header
            for (ProjectApplication application : applications) {
                bw.write(String.format("%s,%s,%s,%s,%s\n",
                        application.getUser(),
                        application.getProjectName(),
                        application.getStatus().toString(),
                        dateFormat.format(application.getApplicationDate()),
                        application.getFlatType().toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves officer applications to the specified file path.
     * Includes applicant, project name, status, and application date.
     *
     * @param filePath The CSV file path to save to
     * @param applications The list of OfficerApplication objects
     */
    public void saveOfficerApplications(String filePath, List<OfficerApplication> applications) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("UserID,Project,Status,Apply Date\n"); // Header
            for (OfficerApplication application : applications) {
                bw.write(String.format("%s,%s,%s,%s\n",
                        application.getUser(),
                        application.getProjectName(),
                        application.getStatus().toString(),
                        dateFormat.format(application.getApplicationDate())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
