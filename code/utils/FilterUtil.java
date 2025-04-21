package utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import models.projects.BTOProject;
import models.projects.FilterSettings;

/**
 * The FilterUtil class provides utility methods to filter lists of BTO projects
 * based on various criteria such as project name, neighborhood, unit counts, and prices.
 */
public class FilterUtil {

    /**
     * Filters a list of projects based on a given condition.
     *
     * @param projects The list of projects to filter.
     * @param condition The condition to filter by.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterBy(List<BTOProject> projects, Predicate<BTOProject> condition) {
        return projects.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    /**
     * Filters a list of projects by project name.
     *
     * @param projects The list of projects to filter.
     * @param projectName The project name to filter by.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterByProjectName(List<BTOProject> projects, String projectName) {
        return filterBy(projects, project -> project.getProjectName().equalsIgnoreCase(projectName));
    }

    /**
     * Filters a list of projects by neighborhood.
     *
     * @param projects The list of projects to filter.
     * @param neighborhood The neighborhood to filter by.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterByNeighborhood(List<BTOProject> projects, String neighborhood) {
        return filterBy(projects, project -> project.getNeighborhood().equalsIgnoreCase(neighborhood));
    }

    /**
     * Filters a list of projects by the minimum number of two-room units.
     *
     * @param projects The list of projects to filter.
     * @param minUnits The minimum number of two-room units.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterByTwoRoomUnits(List<BTOProject> projects, int minUnits) {
        return filterBy(projects, project -> project.getTwoRoomUnits() >= minUnits);
    }

    /**
     * Filters a list of projects by the minimum number of three-room units.
     *
     * @param projects The list of projects to filter.
     * @param minUnits The minimum number of three-room units.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterByThreeRoomUnits(List<BTOProject> projects, int minUnits) {
        return filterBy(projects, project -> project.getThreeRoomUnits() >= minUnits);
    }

    /**
     * Filters a list of projects by the maximum selling price for type 1 units.
     *
     * @param projects The list of projects to filter.
     * @param maxPrice The maximum selling price for type 1 units.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterBySellingPriceForType1(List<BTOProject> projects, double maxPrice) {
        return filterBy(projects, project -> project.getSellingPriceForType1() <= maxPrice);
    }

    /**
     * Filters a list of projects by the maximum selling price for type 2 units.
     *
     * @param projects The list of projects to filter.
     * @param maxPrice The maximum selling price for type 2 units.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterBySellingPriceForType2(List<BTOProject> projects, double maxPrice) {
        return filterBy(projects, project -> project.getSellingPriceForType2() <= maxPrice);
    }

    /**
     * Filters a list of projects by active date.
     *
     * @param projects The list of projects to filter.
     * @param activeDate The active date to filter by.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterByActiveDate(List<BTOProject> projects, java.util.Date activeDate) {
        return filterBy(projects, project -> !project.getOpeningDate().after(activeDate) && !project.getClosingDate().before(activeDate));
    }

    /**
     * Filters a list of projects by manager name.
     *
     * @param projects The list of projects to filter.
     * @param managerName The manager name to filter by.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterByManager(List<BTOProject> projects, String managerName) {
        return filterBy(projects, project -> project.getManagerID().equalsIgnoreCase(managerName));
    }

    /**
     * Filters a list of projects by the minimum number of officer slots.
     *
     * @param projects The list of projects to filter.
     * @param minSlots The minimum number of officer slots.
     * @return The filtered list of projects.
     */
    public static List<BTOProject> filterByOfficerSlot(List<BTOProject> projects, int minSlots) {
        return filterBy(projects, project -> project.getOfficerSlot() >= minSlots);
    }

    /**
     * Filters a list of projects by visibility.
     *
     * @param projects The list of projects to filter.
     * @return The filtered list of visible projects.
     */
    public static List<BTOProject> filterByVisibility(List<BTOProject> projects) {
        return filterBy(projects, BTOProject::isVisible);
    }

    /**
     * Filters a list of projects based on the provided filter settings.
     *
     * @param projects The list of projects to filter.
     * @param settings The filter settings containing the filtering criteria.
     * @return The filtered list of projects.
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
