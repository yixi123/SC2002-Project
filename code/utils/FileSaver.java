package utils;

import models.*;
import java.io.*;
import java.text.*;
import java.util.*;

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
            bw.write("Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Officer\n"); // Header
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (BTOProject project : projects) {
                bw.write(String.format("%s,%s,2-Room,%d,%.2f,3-Room,%d,%.2f,%s,%s,%s,%d,%s\n",
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
                        String.join(";", project.getOfficers())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
