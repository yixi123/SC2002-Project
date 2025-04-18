package models.projects;

import java.util.Date;
import java.util.List;

public class BTOProject {
    private String projectName;
    private String neighborhood;
    private int twoRoomUnits;
    private int threeRoomUnits;
    private double sellingPriceForType1; 
    private double sellingPriceForType2; 
    private Date openingDate;
    private Date closingDate;
    private boolean visibility;
    private String managerID;
    private int officerSlot; 
    private List<String> officersID; 

    public BTOProject(String projectName, String neighborhood, int twoRoomUnits, int threeRoomUnits, 
                      double sellingPriceForType1, double sellingPriceForType2, 
                      Date openingDate, Date closingDate, boolean visibility) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.twoRoomUnits = twoRoomUnits;
        this.threeRoomUnits = threeRoomUnits;
        this.sellingPriceForType1 = sellingPriceForType1;
        this.sellingPriceForType2 = sellingPriceForType2;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.visibility = visibility;
    }

    public String getManagerID() {
        return managerID;
    }

    public int getOfficerSlot() {
        return officerSlot;
    }

    public List<String> getOfficers() {
        return officersID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public int getTwoRoomUnits() {
        return twoRoomUnits;
    }

    public int getThreeRoomUnits() {
        return threeRoomUnits;
    }

    public double getSellingPriceForType1() {
        return sellingPriceForType1;
    }

    public double getSellingPriceForType2() {
        return sellingPriceForType2;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setManagerID(String manager) {
        this.managerID = manager;
    }

    public void setOfficerSlot(int officerSlot) {
        this.officerSlot = officerSlot;
    }

    public void setOfficers(List<String> officers) {
        this.officersID = officers;
    }

    public void addOfficer(String officer) {
        this.officersID.add(officer);
    }

    public void setTwoRoomUnits(int twoRoomUnits) {
        this.twoRoomUnits = twoRoomUnits;
    }

    public void setThreeRoomUnits(int threeRoomUnits) {
        this.threeRoomUnits = threeRoomUnits;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isVisible() {
        return visibility;
    }

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

    public String shortToString() {
        return String.format("%-30s [ %s ] %s - %s", projectName, neighborhood, openingDate.toString(), closingDate.toString());
    }
}
