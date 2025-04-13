package services.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import models.User;
import services.EnquiryService;
import models.Applicant;
import models.BTOProject;
import models.Enquiry;
import models.FilterSettings;

import utils.FilterUtil;
import utils.SortUtil;
import view.ApplicantView;
import view.ProjectView;

public class ApplicantController extends UserController{
  static FilterSettings filterSettings = new FilterSettings();
  ApplicantView applicantView = new ApplicantView();

  public Applicant retreiveApplicant(){
    return (Applicant) auth.getCurrentUser();
  }

  public void initiateController(Scanner scanner){
    view.displayMenu();
    int choice;
    do {
      choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
          case 1 -> viewProjectList();
          case 2 -> {
              int filterChoice;
              do {
                  ProjectView.displayFilterSettings(filterSettings);
                  System.out.print("Choose a filter to edit (1-14): ");
                  filterChoice = scanner.nextInt();
                  scanner.nextLine(); // Consume newline

                  switch (filterChoice) {
                      case 1 -> {
                          System.out.print("Enter project name (or leave blank): ");
                          String projectName = scanner.nextLine();
                          filterSettings.setProjectName(projectName.isEmpty() ? null : projectName);
                      }
                      case 2 -> {
                          System.out.print("Enter opening date (or leave blank): ");
                          String openingDate = scanner.nextLine();
                          try {
                              filterSettings.setOpeningDate(openingDate.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(openingDate));
                          } catch (ParseException e) {
                              System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                          }
                      }
                      case 3 -> {
                          System.out.print("Enter closing date (or leave blank): ");
                          String closingDate = scanner.nextLine();
                          try {
                              filterSettings.setClosingDate(closingDate.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(closingDate));
                          } catch (ParseException e) {
                              System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                          }
                      }
                      case 4 -> {
                          System.out.print("Enter neighborhood (or leave blank): ");
                          String neighborhood = scanner.nextLine();
                          filterSettings.setNeighborhood(neighborhood.isEmpty() ? null : neighborhood);
                      }
                      case 5 -> {
                          System.out.print("Enter flat type (2-Room or 3-Room, or leave blank): ");
                          String flatType = scanner.nextLine();
                          filterSettings.setFlatType(flatType.isEmpty() ? null : flatType);
                      }
                      case 6 -> {
                          System.out.print("Enter max price for Type 1 (or leave blank): ");
                          String maxPriceType1Input = scanner.nextLine();
                          Double maxPriceType1 = maxPriceType1Input.isEmpty() ? null : Double.parseDouble(maxPriceType1Input);
                          filterSettings.setMaxPriceForType1(maxPriceType1);
                      }
                      case 7 -> {
                          System.out.print("Enter max price for Type 2 (or leave blank): ");
                          String maxPriceType2Input = scanner.nextLine();
                          Double maxPriceType2 = maxPriceType2Input.isEmpty() ? null : Double.parseDouble(maxPriceType2Input);
                          filterSettings.setMaxPriceForType2(maxPriceType2);
                      }
                      case 8 -> {
                          System.out.print("Enter manager name (or leave blank): ");
                          String manager = scanner.nextLine();
                          filterSettings.setManager(manager.isEmpty() ? null : manager);
                      }
                      case 9 -> {
                          System.out.print("Enter minimum 2-room units (or leave blank): ");
                          String minTwoRoomUnitsInput = scanner.nextLine();
                          Integer minTwoRoomUnits = minTwoRoomUnitsInput.isEmpty() ? null : Integer.parseInt(minTwoRoomUnitsInput);
                          filterSettings.setMinTwoRoomUnits(minTwoRoomUnits);
                      }
                      case 10 -> {
                          System.out.print("Enter minimum 3-room units (or leave blank): ");
                          String minThreeRoomUnitsInput = scanner.nextLine();
                          Integer minThreeRoomUnits = minThreeRoomUnitsInput.isEmpty() ? null : Integer.parseInt(minThreeRoomUnitsInput);
                          filterSettings.setMinThreeRoomUnits(minThreeRoomUnits);
                      }
                      case 11 -> {
                          System.out.print("Enter officer slot (or leave blank): ");
                          String officerSlot = scanner.nextLine();
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
                          int sortOption = scanner.nextInt();
                          scanner.nextLine(); // Consume newline
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
                          boolean sortAscending = scanner.nextBoolean();
                          scanner.nextLine(); // Consume newline
                          filterSettings.setSortAscending(sortAscending);
                      }
                      case 14 -> System.out.println("Returning to menu...");
                      default -> System.out.println("Invalid choice. Please try again.");
                  }
              } while (filterChoice != 14);
          }
          case 3 -> applyProject();
          case 4 -> viewApplicationStatus();
          case 5 -> withdrawProject();
          case 6 -> {
              System.out.println("Your Enquiries:");
              List<Enquiry> userEnquiries = EnquiryService.getEnquiriesByUser(applicant.getName());
              if (userEnquiries.isEmpty()) {
                  System.out.println("No enquiries found.");
              } else {
                  for (int i = 0; i < userEnquiries.size(); i++) {
                      System.out.println((i + 1) + ". " + userEnquiries.get(i));
                  }
                  System.out.print("Enter the number of the enquiry to view its chat: ");
                  int enquiryChoice = scanner.nextInt();
                  scanner.nextLine(); // Consume newline
                  if (enquiryChoice > 0 && enquiryChoice <= userEnquiries.size()) {
                      Enquiry lastEnquiry = EnquiryService.printChat(userEnquiries.get(enquiryChoice - 1).getId());
                      if (lastEnquiry != null) {
                          System.out.println("Options:");
                          System.out.println("1. Edit the last enquiry/reply (if you are the user)");
                          System.out.println("2. Add a reply to the chat");
                          System.out.println("3. Delete the enquiry/reply (if you are the user)");
                          System.out.println("4. Exit");
                          System.out.print("Enter your choice: ");
                          int option = scanner.nextInt();
                          scanner.nextLine(); // Consume newline
                          switch (option) {
                              case 1 -> {
                                  if (lastEnquiry.getUser().equals(applicant.getName())) {
                                      System.out.print("Enter new content for the enquiry: ");
                                      String newContent = scanner.nextLine();
                                      lastEnquiry.setContent(newContent);
                                      EnquiryService.saveEnquiries();
                                      System.out.println("Enquiry updated successfully.");
                                  } else {
                                      System.out.println("You are not the owner of this enquiry. Cannot edit.");
                                  }
                              }
                              case 2 -> {
                                  System.out.print("Enter your reply: ");
                                  String replyContent = scanner.nextLine();
                                  applicant.addReply(lastEnquiry, replyContent);
                              }
                              case 3 -> {
                                  if (lastEnquiry.getUser().equals(applicant.getName())) {
                                      EnquiryService.getEnquiries().remove(lastEnquiry);
                                      EnquiryService.saveEnquiries();
                                      System.out.println("Enquiry deleted successfully.");
                                  } else {
                                      System.out.println("You are not the owner of this enquiry. Cannot delete.");
                                  }
                              }
                              case 4 -> System.out.println("Exiting...");
                              default -> System.out.println("Invalid choice.");
                          }
                      }
                  } else {
                      System.out.println("Invalid choice.");
                  }
              }
          }
          case 7 -> addEnquiry();
          case 8 -> System.out.println("Exiting...");
          default -> System.out.println("Invalid choice.");
      }
    } while (choice != 8);

  }

  public void viewProjectList(){
    List<BTOProject> filteredProjects = FilterUtil.filterBySettings(ProjectController.getProjects(), filterSettings);
    List<BTOProject> sortedProjects = SortUtil.sortBySettings(filteredProjects, filterSettings);
    sortedProjects.stream().filter(p -> p.isVisible()).forEach(System.out::println);
  }

  public void applyProject(Scanner scanner){
    Applicant applicant = retreiveApplicant();
    if (applicant.getProjectApplication() != null) {
        System.out.println("You already applied for a project.");
    } else {

        System.out.println("Available Projects:");
        viewProjectList();
        System.out.print("Enter the project number you want to apply: ");
        int projectChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (projectChoice > 0 && projectChoice <= sortedProjects.size()) {
            BTOProject selectedProject = sortedProjects.get(projectChoice - 1);
            System.out.println("Choose room type:");
            System.out.println("1. 2-Room");
            System.out.println("2. 3-Room");
            System.out.print("Enter your choice (1 or 2): ");

            int roomTypeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            String roomType = switch (roomTypeChoice) {
                case 1 -> "2-Room";
                case 2 -> "3-Room";
                default -> {
                    System.out.println("Invalid choice. Returning to menu."); yield "";
                }
            };
            applicant.registerForProject(selectedProject.getProjectName(), roomType);
        } else {
            System.out.println("Invalid choice. Returning to menu.");
        }
    }
  }

  public void viewApplicationStatus() {
    retreiveApplicant().viewApplicationStatus();
  }

  public void withdrawProject() {
    retreiveApplicant().withdrawApplication();
  }

  public void addEnquiry() {
      System.out.print("Enter project name: ");
      String projectName = scanner.nextLine();
      System.out.print("Enter enquiry content: ");
      String content = scanner.nextLine();
      applicant.createEnquiry(projectName, content);
  }

  public void viewEnquiry(){
    
  }
}
