package models;

public abstract class User {
    protected String nric;
    protected String password;
    protected int age;
    protected String maritalStatus;

    public User(String nric, String password, int age, String maritalStatus) {
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public String getNric() {
        return nric;
    }

    public void updatePassword(String oldPassword, String newPassword) {
        // ...existing code...
    }

    public String getRole() {
        // ...existing code...
        return null; // Placeholder return statement
    }
}
