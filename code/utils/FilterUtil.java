package utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import models.projects.BTOProject;
import models.projects.FilterSettings;

/**
 * FilterUtil is a utility class for filtering {@code BTOProject} objects based on various criteria.
 * Supports filtering by project name, neighborhood, number of units, selling price,
 * active date, manager, officer slots, visibility, and combinations via {@code FilterSettings}.
 */
public class FilterUtil {

    /**
     * Filters a list of projects using a custom condition.
     *
     * @param projects The list of BTO projects to filter
     * @param condition A predicate defining the filter condition
     * @return A list of projects that satisfy the condition
     */
    public static List<BTOProject> filterBy(List<BTOProject> projects, Predicate<BTOProject> condition) {
        return projects.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    /**
     * Filters projects by exact project name (case-insensitive).
     *
     * @param projects List of BTO projects
     * @param projectName Project name to filter by
     * @return Filtered list of projects with the given name
     */
    public static List<BTOProject> filterByProjectName(List<BTOProject> projects, String projectName) {
        return filterBy(projects, project -> project.getProjectName().equalsIgnoreCase(projectName));
    }

    /**
     * Filters projects by neighborhood (case-insensitive).
     *
     * @param projects List of BTO projects
     * @param neighborhood Neighborhood name to match
     * @return Filtered list of projects in the given neighborhood
     */
    public static List<BTOProject> filterByNeighborhood(List<BTOProject> projects, String neighborhood) {
        return filterBy(projects, project -> project.getNeighborhood().equalsIgnoreCase(neighborhood));
    }

    /**
     * Filters projects that have at least a specified number of two-room units.
     *
     * @param projects List of BTO projects
     * @param minUnits Minimum number of two-room units required
     * @return Filtered list of projects with sufficient two-room units
     */
    public static List<BTOProject> filterByTwoRoomUnits(List<BTOProject> projects, int minUnits) {
        return filterBy(projects, project -> project.getTwoRoomUnits() >= minUnits);
    }

    /**
     * Filters projects that have at least a specified number of three-room units.
     *
     * @param projects List of BTO projects
     * @param minUnits Minimum number of three-room units required
     * @return Filtered list of projects with sufficient three-room units
     */
    public static List<BTOProject> filterByThreeRoomUnits(List<BTOProject> projects, int minUnits) {
        return filterBy(projects, project -> project.getThreeRoomUnits() >= minUnits);
    }

    /**
     * Filters projects whose selling price for two-room flats is less than or equal to the specified price.
     *
     * @param projects List of BTO projects
     * @param maxPrice Maximum selling price allowed
     * @return Filtered list of projects within the price limit
     */
    public static List<BTOProject> filterBySellingPriceForType1(List<BTOProject> projects, double maxPrice) {
        return filterBy(projects, project -> project.getSellingPriceForType1() <= maxPrice);
    }

    /**
     * Filters projects whose selling price for three-room flats is less than or equal to the specified price.
     *
     * @param projects List of BTO projects
     * @param maxPrice Maximum selling price allowed
     * @return Filtered list of projects within the price limit
     */
    public static List<BTOProject> filterBySellingPriceForType2(List<BTOProject> projects, double maxPrice) {
        return filterBy(projects, project -> project.getSellingPriceForType2() <= maxPrice);
    }

    /**
     * Filters projects that are active on a specific date (i.e., between opening and closing dates).
     *
     * @param projects List of BTO projects
     * @param activeDate Date to check project activity
     * @return Filtered list of projects active on the given date
     */
    public static List<BTOProject> filterByActiveDate(List<BTOProject> projects, java.util.Date activeDate) {
        return filterBy(projects, project -> !project.getOpeningDate().after(activeDate) && !project.getClosingDate().before(activeDate));
    }

    /**
     * Filters projects managed by a specific manager (case-insensitive).
     *
     * @param projects List of BTO projects
     * @param managerName Manager's NRIC to match
     * @return Filtered list of projects managed by the given manager
     */
    public static List<BTOProject> filterByManager(List<BTOProject> projects, String managerName) {
        return filterBy(projects, project -> project.getManagerID().equalsIgnoreCase(managerName));
    }

    /**
     * Filters projects that have at least a specified number of officer slots.
     *
     * @param projects List of BTO projects
     * @param minSlots Minimum officer slots required
     * @return Filtered list of projects meeting the slot requirement
     */
    public static List<BTOProject> filterByOfficerSlot(List<BTOProject> projects, int minSlots) {
        return filterBy(projects, project -> project.getOfficerSlot() >= minSlots);
    }

    /**
     * Filters only projects marked as publicly visible.
     *
     * @param projects List of BTO projects
     * @return List of projects where visibility is set to true
     */
    public static List<BTOProject> filterByVisibility(List<BTOProject> projects) {
        return filterBy(projects, BTOProject::isVisible);
    }

    /**
     * Applies multiple filters to a list of BTO projects based on a {@code FilterSettings} object.
     * Applies filters sequentially in the following order:
     * - Project name
     * - Neighborhood
     * - Unit availability
     * - Price ceilings
     * - Date of activity
     * - Manager
     * - Officer slot count
     * - Visibility
     * - Sorting (if specified)
     *
     * @param projects The list of BTO projects to filter
     * @param settings FilterSettings object containing filter criteria
     * @return Filtered list of projects based on all matching criteria
     */
    public static List<BTOProject> filterBySettings(List<BTOProject> projects, FilterSettings settings) {
        List<BTOProject> filteredProjects = projects;

        if (settings.getProjectName() != null) {
            filteredProjects = filterByProjectName(filteredProjects, settings.getProjectName());
        }
        if (settings.getNeighborhood() != null) {
            filteredProjects = filterByNeighborhood(filteredProjects, settings.getNeighborhood());
        }
        if (settings.getMinTwoRoomUnits() != null && settings.getMinTwoRoomUnits() > 0) {
            filteredProjects = filterByTwoRoomUnits(filteredProjects, settings.getMinTwoRoomUnits());
        }
        if (settings.getMinThreeRoomUnits() != null && settings.getMinThreeRoomUnits() > 0) {
            filteredProjects = filterByThreeRoomUnits(filteredProjects, settings.getMinThreeRoomUnits());
        }
        if (settings.getMaxPriceForType1() != null && settings.getMaxPriceForType1() > 0) {
            filteredProjects = filterBySellingPriceForType1(filteredProjects, settings.getMaxPriceForType1());
        }
        if (settings.getMaxPriceForType2() != null && settings.getMaxPriceForType2() > 0) {
            filteredProjects = filterBySellingPriceForType2(filteredProjects, settings.getMaxPriceForType2());
        }
        if (settings.getActiveDate() != null) {
            filteredProjects = filterByActiveDate(filteredProjects, settings.getActiveDate());
        }
        if (settings.getManager() != null) {
            filteredProjects = filterByManager(filteredProjects, settings.getManager());
        }
        if (settings.getOfficerSlot() != null && settings.getOfficerSlot() > 0) {
            filteredProjects = filterByOfficerSlot(filteredProjects, settings.getOfficerSlot());
        }
        if (settings.getVisibility() != null && settings.getVisibility()) {
            filteredProjects = filterByVisibility(filteredProjects);
        }
        if (settings.getSortBy() != null) {
            filteredProjects = SortUtil.sortBySettings(filteredProjects, settings);
        }

        return filteredProjects;
    }
}
