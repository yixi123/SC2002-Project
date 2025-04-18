package utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import models.projects.BTOProject;
import models.projects.FilterSettings;

public class FilterUtil {

    public static List<BTOProject> filterBy(List<BTOProject> projects, Predicate<BTOProject> condition) {
        return projects.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    public static List<BTOProject> filterByProjectName(List<BTOProject> projects, String projectName) {
        return filterBy(projects, project -> project.getProjectName().equalsIgnoreCase(projectName));
    }

    public static List<BTOProject> filterByNeighborhood(List<BTOProject> projects, String neighborhood) {
        return filterBy(projects, project -> project.getNeighborhood().equalsIgnoreCase(neighborhood));
    }

    public static List<BTOProject> filterByTwoRoomUnits(List<BTOProject> projects, int minUnits) {
        return filterBy(projects, project -> project.getTwoRoomUnits() >= minUnits);
    }

    public static List<BTOProject> filterByThreeRoomUnits(List<BTOProject> projects, int minUnits) {
        return filterBy(projects, project -> project.getThreeRoomUnits() >= minUnits);
    }

    public static List<BTOProject> filterBySellingPriceForType1(List<BTOProject> projects, double maxPrice) {
        return filterBy(projects, project -> project.getSellingPriceForType1() <= maxPrice);
    }

    public static List<BTOProject> filterBySellingPriceForType2(List<BTOProject> projects, double maxPrice) {
        return filterBy(projects, project -> project.getSellingPriceForType2() <= maxPrice);
    }

    public static List<BTOProject> filterByOpeningDate(List<BTOProject> projects, java.util.Date startDate) {
        return filterBy(projects, project -> !project.getOpeningDate().before(startDate));
    }

    public static List<BTOProject> filterByClosingDate(List<BTOProject> projects, java.util.Date endDate) {
        return filterBy(projects, project -> !project.getClosingDate().after(endDate));
    }

    public static List<BTOProject> filterByManager(List<BTOProject> projects, String managerName) {
        return filterBy(projects, project -> project.getManagerID().equalsIgnoreCase(managerName));
    }

    public static List<BTOProject> filterByOfficerSlot(List<BTOProject> projects, int minSlots) {
        return filterBy(projects, project -> project.getOfficerSlot() >= minSlots);
    }

    public static List<BTOProject> filterByVisibility(List<BTOProject> projects) {
        return filterBy(projects, BTOProject::isVisible);
    }

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
        if (settings.getOpeningDate() != null) {
            filteredProjects = filterByOpeningDate(filteredProjects, settings.getOpeningDate());
        }
        if (settings.getClosingDate() != null) {
            filteredProjects = filterByClosingDate(filteredProjects, settings.getClosingDate());
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

        return filteredProjects;
    }
}
