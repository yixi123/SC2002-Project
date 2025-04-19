package models.projects;
import java.util.Date;
import models.enums.FlatType;

public class FilterSettings {
    private String projectName;
    private Date activeDate;
    private String neighborhood;
    private FlatType flatType;
    private Double maxPriceForType1;
    private Double maxPriceForType2;
    private String manager;
    private Integer minTwoRoomUnits;
    private Integer minThreeRoomUnits;
    private Integer officerSlot;
    private Boolean visibility;
    private SortBy sortBy;
    private boolean sortAscending;

    public FilterSettings() {
        this.sortBy = SortBy.PROJECT_NAME; // Default sort by project name
        this.sortAscending = true; 
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public Double getMaxPriceForType1() {
        return maxPriceForType1;
    }

    public void setMaxPriceForType1(Double maxPriceForType1) {
        this.maxPriceForType1 = maxPriceForType1;
    }

    public Double getMaxPriceForType2() {
        return maxPriceForType2;
    }

    public void setMaxPriceForType2(Double maxPriceForType2) {
        this.maxPriceForType2 = maxPriceForType2;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getMinTwoRoomUnits() {
        return minTwoRoomUnits;
    }

    public void setMinTwoRoomUnits(Integer minTwoRoomUnits) {
        this.minTwoRoomUnits = minTwoRoomUnits;
    }

    public Integer getMinThreeRoomUnits() {
        return minThreeRoomUnits;
    }

    public void setMinThreeRoomUnits(Integer minThreeRoomUnits) {
        this.minThreeRoomUnits = minThreeRoomUnits;
    }

    public Integer getOfficerSlot() {
        return officerSlot;
    }

    public void setOfficerSlot(Integer officerSlot) {
        this.officerSlot = officerSlot;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    public enum SortBy {
        NONE,
        PROJECT_NAME,
        NEIGHBORHOOD,
        TWO_ROOM_UNITS,
        THREE_ROOM_UNITS,
        SELLING_PRICE_TYPE1,
        SELLING_PRICE_TYPE2,
        OPENING_DATE,
        CLOSING_DATE,
        MANAGER,
        OFFICER_SLOT
    }

    
}
