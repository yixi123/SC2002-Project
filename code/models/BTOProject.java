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

    public void toggleVisibility() {
        this.visibility = !this.visibility;
    }

    public void printProjectInfo() {
        System.out.println("Project Name: " + projectName);
        System.out.println("Neighborhood: " + neighborhood);
        System.out.println("2-Room Units: " + twoRoomUnits + " (Price: $" + sellingPriceForType1 + ")");
        System.out.println("3-Room Units: " + threeRoomUnits + " (Price: $" + sellingPriceForType2 + ")");
        System.out.println("Application Opening Date: " + openingDate);
        System.out.println("Application Closing Date: " + closingDate);
        System.out.println("Manager: " + manager);
        System.out.println("Officer Slot: " + officerSlot);
        System.out.println("Officers: " + String.join(", ", officers));
        System.out.println("Visibility: " + (visibility ? "Visible" : "Hidden"));
        System.out.println("--------------------------------------------------");
    }
}
