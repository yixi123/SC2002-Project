package utils;

import java.util.Comparator;
import java.util.List;

import models.projects.BTOProject;
import models.projects.FilterSettings;

/**
 * The SortUtil class provides utility methods to sort lists of BTO projects
 * based on various criteria such as project name, neighborhood, unit counts, and prices.
 */
public class SortUtil {

    /**
     * Sorts a list of projects by project name.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortByProjectName(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getProjectName, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts a list of projects by neighborhood.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortByNeighborhood(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getNeighborhood, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts a list of projects by the number of two-room units.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortByTwoRoomUnits(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getTwoRoomUnits).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getTwoRoomUnits));
        }
        return projects;
    }

    /**
     * Sorts a list of projects by the number of three-room units.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortByThreeRoomUnits(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getThreeRoomUnits).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getThreeRoomUnits));
        }
        return projects;
    }

    /**
     * Sorts a list of projects by the selling price for type 1 units.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortBySellingPriceForType1(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType1).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType1));
        }
        return projects;
    }

    /**
     * Sorts a list of projects by the selling price for type 2 units.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortBySellingPriceForType2(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType2).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType2));
        }
        return projects;
    }

    /**
     * Sorts a list of projects by opening date.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortByOpeningDate(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getOpeningDate, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts a list of projects by closing date.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortByClosingDate(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getClosingDate, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts a list of projects by manager ID.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortByManager(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getManagerID, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts a list of projects by officer slot.
     *
     * @param projects The list of projects to sort.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortByOfficerSlot(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getOfficerSlot).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getOfficerSlot));
        }
        return projects;
    }

    /**
     * Sorts a list of projects based on the provided filter settings.
     *
     * @param projects The list of projects to sort.
     * @param settings The filter settings containing the sorting criteria.
     * @return The sorted list of projects.
     */
    public static List<BTOProject> sortBySettings(List<BTOProject> projects, FilterSettings settings) {
        switch (settings.getSortBy()) {
            case PROJECT_NAME -> sortByProjectName(projects, settings.isSortAscending());
            case NEIGHBORHOOD -> sortByNeighborhood(projects, settings.isSortAscending());
            case TWO_ROOM_UNITS -> sortByTwoRoomUnits(projects, settings.isSortAscending());
            case THREE_ROOM_UNITS -> sortByThreeRoomUnits(projects, settings.isSortAscending());
            case SELLING_PRICE_TYPE1 -> sortBySellingPriceForType1(projects, settings.isSortAscending());
            case SELLING_PRICE_TYPE2 -> sortBySellingPriceForType2(projects, settings.isSortAscending());
            case OPENING_DATE -> sortByOpeningDate(projects, settings.isSortAscending());
            case CLOSING_DATE -> sortByClosingDate(projects, settings.isSortAscending());
            case MANAGER -> sortByManager(projects, settings.isSortAscending());
            case OFFICER_SLOT -> sortByOfficerSlot(projects, settings.isSortAscending());
            default -> {
                // No sorting if SortBy is NONE
            }
        }
        return projects;
    }
}
