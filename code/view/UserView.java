package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import models.enums.FlatType;
import models.projects.FilterSettings;

/**
 * The UserView class provides a user interface for managing filter settings.
 * It allows users to adjust various filter criteria for project searches.
 */
public class UserView {
    /**
     * Shared filter settings for displaying BTO projects.
     */

    /**
     * Allows the user to interactively adjust filtering options for BTO project listings.
     * The method provides a looped menu to set or clear filter fields such as project name,
     * neighborhood, flat type, maximum price for each flat type, and sorting preferences.
     *
     * @param sc Scanner object used for user input
     */
  public FilterSettings adjustFilterSettings(Scanner sc, FilterSettings filterSettings) {
    int filterChoice = 0;
    do {
        System.out.println("Current Filter Settings:");
        System.out.println(ViewFormatter.breakLine());
        System.out.println("1. Project Name: " + (filterSettings.getProjectName() == null ? "None" : filterSettings.getProjectName()));
        System.out.println("2. Neighborhood: " + (filterSettings.getNeighborhood() == null ? "None" : filterSettings.getNeighborhood()));
        System.out.println("3. Flat Type: " + (filterSettings.getFlatType() == null ? "None" : filterSettings.getFlatType()));
        System.out.println("4. Max Price for 2-room flat: " + (filterSettings.getMaxPriceForType1() == null ? "None" : filterSettings.getMaxPriceForType1()));
        System.out.println("5. Max Price for 3-room flat: " + (filterSettings.getMaxPriceForType2() == null ? "None" : filterSettings.getMaxPriceForType2()));
        System.out.println("6. Sorting Option: " + (filterSettings.getSortBy() == null ? "None" : filterSettings.getSortBy()));
        System.out.println("7. Sort Ascending: " + filterSettings.isSortAscending());
        System.out.println("8. Back to Menu");
        System.out.println(ViewFormatter.breakLine());
        System.out.print("Choose a filter to edit (1-8): ");
        try{
            filterChoice = sc.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Invalid input! Please try again!");
            continue;
        } finally {
            sc.nextLine();
        }

        switch (filterChoice) {
            case 1 -> {
                System.out.print("Enter project name (or leave blank): ");
                String projectName = sc.nextLine();
                filterSettings.setProjectName(projectName.isEmpty() ? null : projectName);
            }
            case 2 -> {
                System.out.print("Enter neighborhood (or leave blank): ");
                String neighborhood = sc.nextLine();
                filterSettings.setNeighborhood(neighborhood.isEmpty() ? null : neighborhood);
            }
            case 3 -> {
                System.out.print("Enter flat type (1 for TWO_ROOM, 2 for THREE_ROOM, or leave blank): ");
                String flatTypeInput = sc.nextLine();
                if (flatTypeInput.isEmpty()) {
                    filterSettings.setFlatType(null);
                } else {
                    int flatTypeChoice = Integer.parseInt(flatTypeInput);
                    filterSettings.setFlatType(flatTypeChoice == 1 ? FlatType.TWO_ROOM : FlatType.THREE_ROOM);
                }
            }
            case 4 -> {
                System.out.print("Enter max price for Type 1 (or leave blank): ");
                String maxPriceType1Input = sc.nextLine();
                Double maxPriceType1 = maxPriceType1Input.isEmpty() ? null : Double.parseDouble(maxPriceType1Input);
                filterSettings.setMaxPriceForType1(maxPriceType1);
            }
            case 5 -> {
                System.out.print("Enter max price for Type 2 (or leave blank): ");
                String maxPriceType2Input = sc.nextLine();
                Double maxPriceType2 = maxPriceType2Input.isEmpty() ? null : Double.parseDouble(maxPriceType2Input);
                filterSettings.setMaxPriceForType2(maxPriceType2);
            }
            case 6 -> {
                System.out.println("Sorting Options:");
                System.out.println(ViewFormatter.breakLine());
                System.out.println("1. Project Name");
                System.out.println("2. Neighborhood");
                System.out.println("3. Two-Room Units");
                System.out.println("4. Three-Room Units");
                System.out.println("5. Selling Price for Type 1");
                System.out.println("6. Selling Price for Type 2");
                System.out.println(ViewFormatter.breakLine());
                System.out.print("Choose sorting option (1-6): ");
                int sortOption = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.println(ViewFormatter.breakLine());
                switch (sortOption) {
                    case 1 -> filterSettings.setSortBy(FilterSettings.SortBy.PROJECT_NAME);
                    case 2 -> filterSettings.setSortBy(FilterSettings.SortBy.NEIGHBORHOOD);
                    case 3 -> filterSettings.setSortBy(FilterSettings.SortBy.TWO_ROOM_UNITS);
                    case 4 -> filterSettings.setSortBy(FilterSettings.SortBy.THREE_ROOM_UNITS);
                    case 5 -> filterSettings.setSortBy(FilterSettings.SortBy.SELLING_PRICE_TYPE1);
                    case 6 -> filterSettings.setSortBy(FilterSettings.SortBy.SELLING_PRICE_TYPE2);
                    default -> System.out.println("Invalid sorting option. No sorting applied.");
                }
            }
            case 7 -> {
                System.out.print("Sort ascending? (true/false): ");
                System.out.println(ViewFormatter.breakLine());
                boolean sortAscending = sc.nextBoolean();
                sc.nextLine(); // Consume newline
                filterSettings.setSortAscending(sortAscending);
            }
            case 8 -> {System.out.println("Returning to menu..."); return filterSettings;}
            default -> System.out.println("Invalid choice. Please try again.");
        }
    } while (filterChoice != 9);
    return filterSettings;
  }
}
