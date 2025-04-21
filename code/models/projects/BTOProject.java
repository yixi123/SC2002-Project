package models.projects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import database.dataclass.projects.ProjectDB;

/**
 * Represents a Build-To-Order (BTO) housing project with details such as unit types, prices,
 * application period, project manager, officer slots, and assigned officers.
 */
public class BTOProject {

    /** Name of the project. */
    private String projectName;

    /** Neighborhood where the project is located. */
    private String neighborhood;

    /** Number of available 2-room units. */
    private int twoRoomUnits;

    /** Number of available 3-room units. */
    private int threeRoomUnits;

    /** Selling price for 2-room units. */
    private double sellingPriceForType1;

    /** Selling price for 3-room units. */
    private double sellingPriceForType2;

    /** Start date of the application period. */
    private Date openingDate;

    /** End date of the application period. */
    private Date closingDate;

    /** Whether the project is currently visible to users. */
    private boolean visibility;

    /** NRIC of the manager overseeing the project. */
    private String managerID;

    /** Number of officer slots available for assignment. */
    private int officerSlot;

    /** List of officer NRICs assigned to the project. */
    private List<String> officersID = new java.util.ArrayList<>();

    /**
     * Constructs a BTOProject object.
     *
     * @param projectName         name of the project
     * @param neighborhood        neighborhood location
     * @param twoRoomUnits        available 2-room units
     * @param threeRoomUnits      available 3-room units
     * @param sellingPriceForType1 price for 2-room units
     * @param sellingPriceForType2 price for 3-room units
     * @param openingDate         start date of application
     * @param closingDate         end date of application
     * @param managerID           NRIC of the manager
     * @param officerSlots        number of officer slots
     * @param visibility          project visibility
     */
    public BTOProject(String projectName, String neighborhood, int twoRoomUnits, int threeRoomUnits,
                      double sellingPriceForType1, double sellingPriceForType2,
                      Date openingDate, Date closingDate, String managerID, int officerSlots, boolean visibility) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.twoRoomUnits = twoRoomUnits;
        this.threeRoomUnits = threeRoomUnits;
        this.sellingPriceForType1 = sellingPriceForType1;
        this.sellingPriceForType2 = sellingPriceForType2;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.managerID = managerID;
        this.officerSlot = officerSlots;
        this.visibility = visibility;
    }

    /**
     * Gets the ID of the manager assigned to the project.
     * @return manager's NRIC
     */
    public String getManagerID() {
        return managerID;
    }

    /**
     * Gets the current number of available officer slots.
     * @return number of officer slots
     */
    public int getOfficerSlot() {
        return officerSlot;
    }

    /**
     * Gets the list of officer NRICs assigned to the project.
     * @return list of officer IDs
     */
    public List<String> getOfficers() {
        return officersID;
    }

    /**
     * Gets the name of the project.
     * @return project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the neighborhood where the project is located.
     * @return neighborhood name
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Gets the number of available 2-room units.
     * @return number of 2-room units
     */
    public int getTwoRoomUnits() {
        return twoRoomUnits;
    }

    /**
     * Gets the number of available 3-room units.
     * @return number of 3-room units
     */
    public int getThreeRoomUnits() {
        return threeRoomUnits;
    }

    /**
     * Gets the selling price for 2-room units.
     * @return price for 2-room units
     */
    public double getSellingPriceForType1() {
        return sellingPriceForType1;
    }

    /**
     * Gets the selling price for 3-room units.
     * @return price for 3-room units
     */
    public double getSellingPriceForType2() {
        return sellingPriceForType2;
    }

    /**
     * Gets the application opening date.
     * @return opening date
     */
    public Date getOpeningDate() {
        return openingDate;
    }

    /**
     * Gets the application closing date.
     * @return closing date
     */
    public Date getClosingDate() {
        return closingDate;
    }

    /**
     * Sets the manager ID for the project.
     * @param manager manager's NRIC
     */
    public void setManagerID(String manager) {
        this.managerID = manager;
    }

    /**
     * Sets the number of officer slots for the project.
     * @param officerSlot number of officer slots
     */
    public void setOfficerSlot(int officerSlot) {
        this.officerSlot = officerSlot;
    }

    /**
     * Sets the list of officers assigned to the project.
     * This will replace any existing officer assignments.
     *
     * @param officers A list of officer NRICs to assign to the project
     */
    public void setOfficers(List<String> officers) {
        this.officersID = officers;
    }

    /**
     * Adds an officer to the project and updates the database.
     * Reduces officer slot count by one.
     * @param officer officer NRIC to add
     */
    public void addOfficer(String officer) {
        officerSlot--;
        this.officersID.add(officer);
        ProjectDB.updateDB();
    }

    /**
     * Sets the number of 2-room units available in the project.
     * @param twoRoomUnits number of 2-room units
     */
    public void setTwoRoomUnits(int twoRoomUnits) {
        this.twoRoomUnits = twoRoomUnits;
    }

    /**
     * Sets the number of 3-room units available in the project.
     * @param threeRoomUnits number of 3-room units
     */
    public void setThreeRoomUnits(int threeRoomUnits) {
        this.threeRoomUnits = threeRoomUnits;
    }

    /**
     * Sets the visibility of the project.
     * @param visibility true to make visible, false to hide
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Sets the application opening date.
     * @param openingDate the opening date to set
     */
    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    /**
     * Sets the application closing date.
     * @param closingDate the closing date to set
     */
    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Checks if the project is marked visible.
     * @return true if visible, false otherwise
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Checks if the project is active (visible and open for applications).
     * @return true if the project is currently active
     */
    public boolean isActive() {
        Date today = new Date();
        return isVisible() && today.before(closingDate) && today.after(openingDate);
    }

    /**
     * Returns a formatted multi-line string with project details.
     */
    @Override
    public String toString() {
        return "Project Name: " + projectName + "\n" +
               "Neighborhood: " + neighborhood + "\n" +
               "2-Room Units: " + twoRoomUnits + " (Price: $" + sellingPriceForType1 + ")\n" +
               "3-Room Units: " + threeRoomUnits + " (Price: $" + sellingPriceForType2 + ")\n" +
               "Application Opening Date: " + openingDate + "\n" +
               "Application Closing Date: " + closingDate + "\n" +
               "Manager: " + managerID + "\n" +
               "Officer Slot: " + officerSlot + "\n" +
               "Officers: " + String.join(", ", officersID) + "\n" +
               "--------------------------------------------------";
    }

    /**
     * Returns a short summary of the project.
     * @return concise one-liner string
     */
    public String shortToString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        return String.format("%-20s [ %s ]\n   %s - %s", projectName, neighborhood, dateFormatter.format(openingDate), dateFormatter.format(closingDate));
    }
}
