package utils;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import models.*;

public class FileLoader {
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
                    dateFormat.parse(data[8]), dateFormat.parse(data[9])
                );
                project.setManager(data[10]);
                project.setOfficerSlot(Integer.parseInt(data[11]));
                project.setOfficers(Arrays.asList(data[12].split(";")));
                projects.add(project);
                
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
