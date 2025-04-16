package services.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import models.projects.FilterSettings;

public abstract class UserController {
  FilterSettings filterSettings = new FilterSettings();
  public abstract void start(Scanner sc);

  public void adjustFilterSettings(Scanner sc){
        int filterChoice;
        do {
            System.out.println("Current Filter Settings:");
            System.out.println("1. Project Name: " + (filterSettings.getProjectName() == null ? "None" : filterSettings.getProjectName()));
            System.out.println("2. Opening Date: " + (filterSettings.getOpeningDate() == null ? "None" : filterSettings.getOpeningDate()));
            System.out.println("3. Closing Date: " + (filterSettings.getClosingDate() == null ? "None" : filterSettings.getClosingDate()));
            System.out.println("4. Neighborhood: " + (filterSettings.getNeighborhood() == null ? "None" : filterSettings.getNeighborhood()));
            System.out.println("5. Flat Type: " + (filterSettings.getFlatType() == null ? "None" : filterSettings.getFlatType()));
            System.out.println("6. Max Price for Type 1: " + (filterSettings.getMaxPriceForType1() == null ? "None" : filterSettings.getMaxPriceForType1()));
            System.out.println("7. Max Price for Type 2: " + (filterSettings.getMaxPriceForType2() == null ? "None" : filterSettings.getMaxPriceForType2()));
            System.out.println("8. Manager: " + (filterSettings.getManager() == null ? "None" : filterSettings.getManager()));
            System.out.println("9. Minimum 2-Room Units: " + (filterSettings.getMinTwoRoomUnits() == null ? "None" : filterSettings.getMinTwoRoomUnits()));
            System.out.println("10. Minimum 3-Room Units: " + (filterSettings.getMinThreeRoomUnits() == null ? "None" : filterSettings.getMinThreeRoomUnits()));
            System.out.println("11. Officer Slot: " + (filterSettings.getOfficerSlot() == null ? "None" : filterSettings.getOfficerSlot()));
            System.out.println("12. Sorting Option: " + (filterSettings.getSortBy() == null ? "None" : filterSettings.getSortBy()));
            System.out.println("13. Sort Ascending: " + filterSettings.isSortAscending());
            System.out.println("14. Back to Menu");
            System.out.print("Choose a filter to edit (1-14): ");
            filterChoice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (filterChoice) {
                case 1 -> {
                    System.out.print("Enter project name (or leave blank): ");
                    String projectName = sc.nextLine();
                    filterSettings.setProjectName(projectName.isEmpty() ? null : projectName);
                }
                case 2 -> {
                    System.out.print("Enter opening date (or leave blank): ");
                    String openingDate = sc.nextLine();
                    try {
                        filterSettings.setOpeningDate(openingDate.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(openingDate));
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter closing date (or leave blank): ");
                    String closingDate = sc.nextLine();
                    try {
                        filterSettings.setClosingDate(closingDate.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(closingDate));
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter neighborhood (or leave blank): ");
                    String neighborhood = sc.nextLine();
                    filterSettings.setNeighborhood(neighborhood.isEmpty() ? null : neighborhood);
                }
                case 5 -> {
                    System.out.print("Enter flat type (2-Room or 3-Room, or leave blank): ");
                    String flatType = sc.nextLine();
                    filterSettings.setFlatType(flatType.isEmpty() ? null : flatType);
                }
                case 6 -> {
                    System.out.print("Enter max price for Type 1 (or leave blank): ");
                    String maxPriceType1Input = sc.nextLine();
                    Double maxPriceType1 = maxPriceType1Input.isEmpty() ? null : Double.parseDouble(maxPriceType1Input);
                    filterSettings.setMaxPriceForType1(maxPriceType1);
                }
                case 7 -> {
                    System.out.print("Enter max price for Type 2 (or leave blank): ");
                    String maxPriceType2Input = sc.nextLine();
                    Double maxPriceType2 = maxPriceType2Input.isEmpty() ? null : Double.parseDouble(maxPriceType2Input);
                    filterSettings.setMaxPriceForType2(maxPriceType2);
                }
                case 8 -> {
                    System.out.print("Enter manager name (or leave blank): ");
                    String manager = sc.nextLine();
                    filterSettings.setManager(manager.isEmpty() ? null : manager);
                }
                case 9 -> {
                    System.out.print("Enter minimum 2-room units (or leave blank): ");
                    String minTwoRoomUnitsInput = sc.nextLine();
                    Integer minTwoRoomUnits = minTwoRoomUnitsInput.isEmpty() ? null : Integer.parseInt(minTwoRoomUnitsInput);
                    filterSettings.setMinTwoRoomUnits(minTwoRoomUnits);
                }
                case 10 -> {
                    System.out.print("Enter minimum 3-room units (or leave blank): ");
                    String minThreeRoomUnitsInput = sc.nextLine();
                    Integer minThreeRoomUnits = minThreeRoomUnitsInput.isEmpty() ? null : Integer.parseInt(minThreeRoomUnitsInput);
                    filterSettings.setMinThreeRoomUnits(minThreeRoomUnits);
                }
                case 11 -> {
                    System.out.print("Enter officer slot (or leave blank): ");
                    String officerSlot = sc.nextLine();
                    filterSettings.setOfficerSlot(officerSlot.isEmpty() ? null : Integer.parseInt(officerSlot));
                }
                case 12 -> {
                    System.out.println("Sorting Options:");
                    System.out.println("1. Project Name");
                    System.out.println("2. Neighborhood");
                    System.out.println("3. Two-Room Units");
                    System.out.println("4. Three-Room Units");
                    System.out.println("5. Selling Price for Type 1");
                    System.out.println("6. Selling Price for Type 2");
                    System.out.println("7. Opening Date");
                    System.out.println("8. Closing Date");
                    System.out.println("9. Manager");
                    System.out.println("10. Officer Slot");
                    System.out.print("Choose sorting option (1-10): ");
                    int sortOption = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    switch (sortOption) {
                        case 1 -> filterSettings.setSortBy(FilterSettings.SortBy.PROJECT_NAME);
                        case 2 -> filterSettings.setSortBy(FilterSettings.SortBy.NEIGHBORHOOD);
                        case 3 -> filterSettings.setSortBy(FilterSettings.SortBy.TWO_ROOM_UNITS);
                        case 4 -> filterSettings.setSortBy(FilterSettings.SortBy.THREE_ROOM_UNITS);
                        case 5 -> filterSettings.setSortBy(FilterSettings.SortBy.SELLING_PRICE_TYPE1);
                        case 6 -> filterSettings.setSortBy(FilterSettings.SortBy.SELLING_PRICE_TYPE2);
                        case 7 -> filterSettings.setSortBy(FilterSettings.SortBy.OPENING_DATE);
                        case 8 -> filterSettings.setSortBy(FilterSettings.SortBy.CLOSING_DATE);
                        case 9 -> filterSettings.setSortBy(FilterSettings.SortBy.MANAGER);
                        case 10 -> filterSettings.setSortBy(FilterSettings.SortBy.OFFICER_SLOT);
                        default -> System.out.println("Invalid sorting option. No sorting applied.");
                    }
                }
                case 13 -> {
                    System.out.print("Sort ascending? (true/false): ");
                    boolean sortAscending = sc.nextBoolean();
                    sc.nextLine(); // Consume newline
                    filterSettings.setSortAscending(sortAscending);
                }
                case 14 -> System.out.println("Returning to menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (filterChoice != 14);
  }
}
