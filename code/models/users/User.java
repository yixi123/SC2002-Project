package models.users;

import models.enums.MaritalStatus;

/**
 * Represents a generic user in the system.
 * This is an abstract class extended by specific user types like Applicant, HDBOfficer, and HDBManager.
 */
public abstract class User {

    /** Full name of the user. */
    protected String name;

    /** NRIC or unique identifier for the user. */
    protected String nric;

    /** Encrypted or plain-text password (depending on implementation). */
    protected String password;

    /** Age of the user. */
    protected int age;

    /** Marital status of the user. */
    protected MaritalStatus maritalStatus;

    /**
     * Constructs a User object.
     *
     * @param name           Full name of the user
     * @param nric           NRIC or unique identifier
     * @param password       User password
     * @param age            Age of the user
     * @param maritalStatus  Marital status of the user
     */
    public User(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        this.name = name;
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    /**
     * Gets the user's full name.
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the NRIC or ID of the user.
     * @return the NRIC of the user
     */
    public String getNric() {
        return nric;
    }

    /**
     * Gets the user's password.
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the age of the user.
     * @return the user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the marital status of the user.
     * @return the marital status
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Gets the role of the user based on class type.
     * @return a string representing the user's role
     */
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

    /**
     * Updates the password of the user.
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
