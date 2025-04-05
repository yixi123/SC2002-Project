package utils;

import java.io.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.*;

public class FileSaver {
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
                        project.getManager(),
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
            bw.write("ID,User,Project,Category,Content,timestamp,ReplyID\n"); // Header
            for (Enquiry enquiry : enquiries) {
                bw.write(String.format("%d,%s,%s,%s,%s,%s,%d\n",
                    enquiry.getId(),
                    enquiry.getUser(),
                    enquiry.getProject(),
                    enquiry.getCategory(),
                    enquiry.getContent(),
                    enquiry.getTimestamp().format(formatter), // Format timestamp
                    enquiry.getReplyId()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveProjectApplications(String filePath, List<ProjectApplication> applications) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("User,Project,Status,Apply Date,Flat Type\n"); // Header
            for (ProjectApplication application : applications) {
                bw.write(String.format("%s,%s,%s,%s\n",
                        application.getUser(),
                        application.getProjectName(),
                        application.getStatus(),
                        application.getApplicationDate(),
                        application.getFlatType()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveOfficerApplications(String filePath, List<Application> applications) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("User,Project,Status,Apply Date\n"); // Header
            for (Application application : applications) {
                bw.write(String.format("%s,%s,%s,%s\n",
                        application.getUser(),
                        application.getProjectName(),
                        application.getStatus(),
                        application.getApplicationDate()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
