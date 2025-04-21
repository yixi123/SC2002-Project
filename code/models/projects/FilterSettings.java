package models.projects;
import java.util.Date;
import models.enums.FlatType;

/**
 * Represents a set of filtering and sorting options used to narrow down BTO project listings.
 */
public class FilterSettings {

    /** Specific project name to filter by (optional). */
    private String projectName;

    /** A specific date to filter projects that are active on that date. */
    private Date activeDate;

    /** Neighborhood to filter projects by. */
    private String neighborhood;

    /** Desired flat type (e.g., 2-room, 3-room). */
    private FlatType flatType;

    /** Maximum acceptable price for type 1 (2-room) flats. */
    private Double maxPriceForType1;

    /** Maximum acceptable price for type 2 (3-room) flats. */
    private Double maxPriceForType2;

    /** Manager’s ID or name to filter projects managed by a specific person. */
    private String manager;

    /** Minimum number of 2-room units required. */
    private Integer minTwoRoomUnits;

    /** Minimum number of 3-room units required. */
    private Integer minThreeRoomUnits;

    /** Minimum officer slots available. */
    private Integer officerSlot;

    /** Whether the project must be visible to users. */
    private Boolean visibility;

    /** Field to sort projects by. */
    private SortBy sortBy;

    /** Whether sorting should be in ascending order. */
    private boolean sortAscending;

    /**
     * Constructs a FilterSettings object with default sorting
     * (by project name in ascending order).
     */
    public FilterSettings() {
        this.sortBy = SortBy.PROJECT_NAME;
        this.sortAscending = true;
    }

    /**
     * Gets the project name to filter by.
     * @return the project name filter
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project name to filter.
     * @param projectName name of the project
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the active date filter for ongoing projects.
     * @return the active date
     */
    public Date getActiveDate() {
        return activeDate;
    }

    /**
     * Sets the date to filter for projects active on that day.
     * @param activeDate the specific date
     */
    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    /**
     * Gets the neighborhood filter.
     * @return neighborhood name
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Sets the neighborhood filter.
     * @param neighborhood the name of the neighborhood
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Gets the desired flat type.
     * @return flat type (e.g., 2-room or 3-room)
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Sets the flat type to filter by.
     * @param flatType desired flat type
     */
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    /**
     * Gets the maximum price filter for 2-room flats.
     * @return max price for type 1
     */
    public Double getMaxPriceForType1() {
        return maxPriceForType1;
    }

    /**
     * Sets the maximum price for 2-room flats.
     * @param maxPriceForType1 maximum budget
     */
    public void setMaxPriceForType1(Double maxPriceForType1) {
        this.maxPriceForType1 = maxPriceForType1;
    }

    /**
     * Gets the maximum price filter for 3-room flats.
     * @return max price for type 2
     */
    public Double getMaxPriceForType2() {
        return maxPriceForType2;
    }

    /**
     * Sets the maximum price for 3-room flats.
     * @param maxPriceForType2 maximum budget
     */
    public void setMaxPriceForType2(Double maxPriceForType2) {
        this.maxPriceForType2 = maxPriceForType2;
    }

    /**
     * Gets the manager filter (name or ID).
     * @return manager filter
     */
    public String getManager() {
        return manager;
    }

    /**
     * Sets the manager ID to filter by.
     * @param manager manager name or ID
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * Gets the minimum 2-room unit count filter.
     * @return minimum number of 2-room units
     */
    public Integer getMinTwoRoomUnits() {
        return minTwoRoomUnits;
    }

    /**
     * Sets the minimum number of 2-room units required.
     * @param minTwoRoomUnits minimum 2-room count
     */
    public void setMinTwoRoomUnits(Integer minTwoRoomUnits) {
        this.minTwoRoomUnits = minTwoRoomUnits;
    }

    /**
     * Gets the minimum 3-room unit count filter.
     * @return minimum number of 3-room units
     */
    public Integer getMinThreeRoomUnits() {
        return minThreeRoomUnits;
    }

    /**
     * Sets the minimum number of 3-room units required.
     * @param minThreeRoomUnits minimum 3-room count
     */
    public void setMinThreeRoomUnits(Integer minThreeRoomUnits) {
        this.minThreeRoomUnits = minThreeRoomUnits;
    }

    /**
     * Gets the officer slot filter.
     * @return minimum required officer slots
     */
    public Integer getOfficerSlot() {
        return officerSlot;
    }

    /**
     * Sets the minimum number of officer slots required.
     * @param officerSlot minimum officer slots
     */
    public void setOfficerSlot(Integer officerSlot) {
        this.officerSlot = officerSlot;
    }

    /**
     * Gets the visibility filter.
     * @return true if only visible projects should be shown
     */
    public Boolean getVisibility() {
        return visibility;
    }

    /**
     * Sets the visibility requirement.
     * @param visibility true to include only visible projects
     */
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Gets the current sort field.
     * @return the sortBy enum value
     */
    public SortBy getSortBy() {
        return sortBy;
    }

    /**
     * Sets the field to sort the filtered results by.
     * @param sortBy sort key
     */
    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Returns whether the sort should be ascending.
     * @return true for ascending, false for descending
     */
    public boolean isSortAscending() {
        return sortAscending;
    }

    /**
     * Sets the sorting direction.
     * @param sortAscending true for ascending order
     */
    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    /**
     * Enum representing the available fields by which the results can be sorted.
     */
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
