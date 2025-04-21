package utils;

import java.util.Comparator;
import java.util.List;

import models.projects.BTOProject;
import models.projects.FilterSettings;

/**
 * SortUtil is a utility class for sorting {@code BTOProject} lists based on various attributes.
 * Sorting options include name, neighborhood, room unit count, pricing, project dates,
 * manager ID, and officer slot count. Sorting direction (ascending or descending)
 * is also configurable.
 */
public class SortUtil {
    /**
     * Sorts projects by project name.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortByProjectName(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getProjectName, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts projects by neighborhood name.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortByNeighborhood(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getNeighborhood, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts projects by the number of two-room units available.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortByTwoRoomUnits(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getTwoRoomUnits).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getTwoRoomUnits));
        }
        return projects;
    }

    /**
     * Sorts projects by the number of three-room units available.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortByThreeRoomUnits(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getThreeRoomUnits).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getThreeRoomUnits));
        }
        return projects;
    }

    /**
     * Sorts projects by the selling price for two-room units.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortBySellingPriceForType1(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType1).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType1));
        }
        return projects;
    }

    /**
     * Sorts projects by the selling price for three-room units.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortBySellingPriceForType2(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType2).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType2));
        }
        return projects;
    }

    /**
     * Sorts projects by application opening date.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortByOpeningDate(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getOpeningDate, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts projects by application closing date.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortByClosingDate(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getClosingDate, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts projects by manager ID.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortByManager(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getManagerID, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    /**
     * Sorts projects by the number of officer slots.
     *
     * @param projects  List of BTO projects to sort
     * @param ascending {@code true} for ascending order, {@code false} for descending
     * @return Sorted list of projects
     */
    public static List<BTOProject> sortByOfficerSlot(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getOfficerSlot).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getOfficerSlot));
        }
        return projects;
    }

    /**
     * Sorts projects based on the criteria specified in the {@code FilterSettings}.
     * Sorting is performed according to the specified field and order direction.
     *
     * @param projects List of BTO projects to sort
     * @param settings Filter settings object containing sort field and direction
     * @return Sorted list of projects
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
