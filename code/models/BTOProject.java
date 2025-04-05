package models;

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
    private String manager; // Added to match CSV data
    private int officerSlot; // Added to match CSV data
    private List<String> officers; // Added to match CSV data

    public BTOProject(String projectName, String neighborhood, int twoRoomUnits, int threeRoomUnits, 
                      double sellingPriceForType1, double sellingPriceForType2, 
                      Date openingDate, Date closingDate) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.twoRoomUnits = twoRoomUnits;
        this.threeRoomUnits = threeRoomUnits;
        this.sellingPriceForType1 = sellingPriceForType1;
        this.sellingPriceForType2 = sellingPriceForType2;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.visibility = true;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setOfficerSlot(int officerSlot) {
        this.officerSlot = officerSlot;
    }

    public void setOfficers(List<String> officers) {
        this.officers = officers;
    }

    public String getManager() {
        return manager;
    }

    public int getOfficerSlot() {
        return officerSlot;
    }

    public List<String> getOfficers() {
        return officers;
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

    public void setTwoRoomUnits(int twoRoomUnits) {
        this.twoRoomUnits = twoRoomUnits;
    }

    public void setThreeRoomUnits(int threeRoomUnits) {
        this.threeRoomUnits = threeRoomUnits;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void toggleVisibility() {
        this.visibility = !this.visibility;
    }

    @Override
    public String toString() {
        return "Project Name: " + projectName + "\n" +
               "Neighborhood: " + neighborhood + "\n" +
               "2-Room Units: " + twoRoomUnits + " (Price: $" + sellingPriceForType1 + ")\n" +
               "3-Room Units: " + threeRoomUnits + " (Price: $" + sellingPriceForType2 + ")\n" +
               "Application Opening Date: " + openingDate + "\n" +
               "Application Closing Date: " + closingDate + "\n" +
               "Manager: " + manager + "\n" +
               "Officer Slot: " + officerSlot + "\n" +
               "Officers: " + String.join(", ", officers) + "\n" +
               "Visibility: " + (visibility ? "Visible" : "Hidden") + "\n" +
               "--------------------------------------------------";
    }
}
