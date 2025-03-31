package models;

import java.util.Date;

public class BTOProject {
    private String projectName;
    private String neighborhood;
    private int twoRoomUnits;
    private int threeRoomUnits;
    private Date openingDate;
    private Date closingDate;
    private boolean visibility;

    public BTOProject(String projectName, String neighborhood, int twoRoomUnits, int threeRoomUnits, Date openingDate, Date closingDate) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.twoRoomUnits = twoRoomUnits;
        this.threeRoomUnits = threeRoomUnits;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.visibility = true;
    }

    public void toggleVisibility() {
        this.visibility = !this.visibility;
    }
}
