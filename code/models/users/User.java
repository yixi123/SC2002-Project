package models.users;

import java.time.LocalDateTime;

import archive.ProjectController;
import models.projects.Enquiry;
import services.subservices.EnquiryService;

public abstract class User {
    protected String name;
    protected String nric;
    protected String password;
    protected int age;
    protected String maritalStatus;

    public User(String name, String nric, String password, int age, String maritalStatus) {
        this.name = name;
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getRole() {
        if (this instanceof Applicant) {
            return "Applicant";
        } else if (this instanceof HDBOfficer) {
            return "HDB Officer";
        } else if (this instanceof HDBManager) {
            return "HDB Manager";
        } else {
            return "Unknown";
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
