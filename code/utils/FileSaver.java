package utils;

import java.io.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.projects.Application;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.ProjectApplication;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;

public class FileSaver {

    private static final String APPLICANTS_FILE = "code/database/ApplicantList.csv";
    private static final String OFFICERS_FILE = "code/database/OfficerList.csv";
    private static final String MANAGERS_FILE = "code/database/ManagerList.csv";
    private static final String PROJECTS_FILE = "code/database/ProjectList.csv";
    private static final String ENQUIRIES_FILE = "code/database/EnquiresList.csv";
    private static final String PROJECT_APPLICATIONS_FILE = "code/database/ProjectApplicationList.csv";
    private static final String OFFICER_APPLICATIONS_FILE = "code/database/OfficerApplicationList.csv";

    public static void saveApplicants(List<Applicant> applicants) {
        saveApplicants(APPLICANTS_FILE, applicants);
    }

    public static void saveOfficers(List<HDBOfficer> officers) {
        saveOfficers(OFFICERS_FILE, officers);
    }

    public static void saveManagers(List<HDBManager> managers) {
        saveManagers(MANAGERS_FILE, managers);
    }

    public static void saveProjects(List<BTOProject> projects) {
        saveProjects(PROJECTS_FILE, projects);
    }

    public static void saveEnquiries(List<Enquiry> enquiries) {
        saveEnquiries(ENQUIRIES_FILE, enquiries);
    }

    public static void saveProjectApplications(List<ProjectApplication> applications) {
        saveProjectApplications(PROJECT_APPLICATIONS_FILE, applications);
    }

    public static void saveOfficerApplications(List<Application> applications) {
        saveOfficerApplications(OFFICER_APPLICATIONS_FILE, applications);
    }

    public static void saveApplicants(String filePath, List<Applicant> applicants) {
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

    public static void saveOfficers(String filePath, List<HDBOfficer> officers) {
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

    public static void saveManagers(String filePath, List<HDBManager> managers) {
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

    public static void saveProjects(String filePath, List<BTOProject> projects) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Officer,Visibility\n"); // Updated header
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (BTOProject project : projects) {
                bw.write(String.format("%s,%s,2-Room,%d,%.2f,3-Room,%d,%.2f,%s,%s,%s,%d,%s,%b\n",
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
                        project.isVisible() // Include visibility
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveEnquiries(String filePath, List<Enquiry> enquiries) {
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
                    enquiry.getReplierTimestamp() != null ? enquiry.getReplierTimestamp().format(formatter) : "" // Format replier timestamp
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveProjectApplications(String filePath, List<ProjectApplication> applications) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("UserID,Project,Status,Apply Date,Flat Type\n"); // Header
            for (ProjectApplication application : applications) {
                bw.write(String.format("%s,%s,%s,%s\n",
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

    public static void saveOfficerApplications(String filePath, List<Application> applications) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("UserID,Project,Status,Apply Date\n"); // Header
            for (Application application : applications) {
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
