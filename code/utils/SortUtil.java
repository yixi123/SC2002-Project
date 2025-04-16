package utils;

import java.util.Comparator;
import java.util.List;

import models.projects.BTOProject;
import models.projects.FilterSettings;

public class SortUtil {

    public static List<BTOProject> sortByProjectName(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getProjectName, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    public static List<BTOProject> sortByNeighborhood(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getNeighborhood, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    public static List<BTOProject> sortByTwoRoomUnits(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getTwoRoomUnits).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getTwoRoomUnits));
        }
        return projects;
    }

    public static List<BTOProject> sortByThreeRoomUnits(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getThreeRoomUnits).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getThreeRoomUnits));
        }
        return projects;
    }

    public static List<BTOProject> sortBySellingPriceForType1(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType1).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType1));
        }
        return projects;
    }

    public static List<BTOProject> sortBySellingPriceForType2(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType2).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingDouble(BTOProject::getSellingPriceForType2));
        }
        return projects;
    }

    public static List<BTOProject> sortByOpeningDate(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getOpeningDate, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    public static List<BTOProject> sortByClosingDate(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getClosingDate, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    public static List<BTOProject> sortByManager(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparing(BTOProject::getManagerID, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()));
        return projects;
    }

    public static List<BTOProject> sortByOfficerSlot(List<BTOProject> projects, boolean ascending) {
        projects.sort(Comparator.comparingInt(BTOProject::getOfficerSlot).reversed());
        if (ascending) {
            projects.sort(Comparator.comparingInt(BTOProject::getOfficerSlot));
        }
        return projects;
    }

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
