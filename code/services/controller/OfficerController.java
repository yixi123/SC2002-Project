package services.controller;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.projects.ProjectDB;

import java.util.List;
import java.util.Scanner;

import models.enums.FlatType;
import models.projects.*;
import models.users.Applicant;
import models.users.HDBOfficer;

import services.interfaces.IEnquiryService;
import services.interfaces.IOfficerApplicationService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IReceiptPrintService;

import services.subservices.EnquiryService;
import services.subservices.OfficerApplicationService;
import services.subservices.ProjectApplicationService;
import services.subservices.ReceiptPrintService;
import utils.IFileSaver;
import view.OfficerView;
import view.ViewFormatter;


/**
 * Controller class for users with the HDB Officer role.
 * Extends ApplicantController and enables officers to manage project-related tasks,
 * such as viewing assigned projects, responding to enquiries, and processing applicants.
 */
public class OfficerController extends ApplicantController {

	/**
	 * Service handling officer application logic.
	 */
	private final IOfficerApplicationService offAppService;

	/**
	 * Service handling project application logic.
	 */
	private final IProjectApplicationService proAppService;

	/**
	 * Service handling all enquiry-related logic.
	 */
	private final IEnquiryService enquiryService;

	/**
	 * Service responsible for generating and printing booking receipts.
	 */
	private final IReceiptPrintService printService;

	/**
	 * View component to display menus and actions specific to officers.
	 */
	private static OfficerView officerView = new OfficerView();

	/**
	 * Constructs an and initializes all required service implementations.
	 * This constructor is used during normal system runtime to set up services needed
	 * for officer operations such as managing officer applications, handling project applications,
	 * responding to enquiries, and generating booking receipts.
	 */
	public OfficerController() {
		this.offAppService  = new OfficerApplicationService();
		this.proAppService  = new ProjectApplicationService();
		this.enquiryService = new EnquiryService();
		this.printService   = new ReceiptPrintService();
	}

	/**
	 * Retrieves the currently logged-in HDB officer and loads their assigned project(s),
	 * officer applications, and personal application data.
	 *
	 * @return the HDBOfficer instance with updated data
	 */
	public HDBOfficer retrieveOfficer(){
		HDBOfficer currentUser = (HDBOfficer) auth.getCurrentUser();
		currentUser.setAssignedProject(ProjectDB.getProjectsByOfficer(currentUser.getNric()));
		currentUser.setOfficerApplications(offAppService.getApplicationsByUser(currentUser.getNric()));
		currentUser.setMyApplication(ProjectAppDB.getApplicationByUser(currentUser.getNric()));
		return currentUser;
	}

	/**
	 * Initiates the officer session by displaying the main menu for officer-related actions.
	 *
	 * @param sc Scanner used to capture user input
	 */
	@Override
	public void start(Scanner sc) {
		try {
			officerView.enterMainMenu(sc);
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

	// -------------------- Officer Functionalities --------------------

	/**
	 * Displays all projects currently assigned to the officer.
	 * If no projects are assigned, a relevant message is shown.
	 */
	public void viewHandledProject() {
		try {
			officerView.displayHandledProject();
		} catch (NullPointerException e) {
			System.out.println("No project assigned.");
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

	/**
	 * Opens the officer’s dedicated project portal, allowing actions
	 * such as viewing applicants and handling enquiries.
	 *
	 * @param sc Scanner for user input
	 */
	public void enterOfficerProjectPortal(Scanner sc) {
		try {
			filterSettings.setVisibility(true);
			filterSettings.setActiveDate(null);
			officerView.displayOfficerProjectPortal(sc, filterSettings);
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}


	/**
	 * Displays a list of successful applicants under the officer's currently active project.
	 * Only applicants whose status is marked as SUCCESSFUL are shown.
	 */
	public void viewSuccessfulApplicantsList() {
		officerView.displayHandledProjectSuccessfulApplicants();
	}

	/**
	 * Confirms a specific applicant’s booking under the officer's active project
	 * and updates the corresponding flat availability based on their chosen flat type.
	 *
	 * @param applicantId The NRIC or ID of the applicant to confirm booking for
	 */
	public void handleApplicantsSuccessfulApp(String applicantId) {
		BTOProject project = retrieveOfficer().getActiveProject();
		if (project == null) {
			System.out.println("No project assigned.");
			return;
		}
		int bookSuccess = proAppService.bookApplication(project.getProjectName(), applicantId);
		if (bookSuccess == 1){
			ProjectApplication app = proAppService.getApplicationByUserAndProject(applicantId, project.getProjectName());
			FlatType flatType = app.getFlatType();
			if (flatType == FlatType.TWO_ROOM) {
				project.setTwoRoomUnits(project.getTwoRoomUnits() - 1);
			} else if (flatType == FlatType.THREE_ROOM) {
				project.setThreeRoomUnits(project.getThreeRoomUnits() - 1);
			}
		}
	}

	/**
	 * Generates a textual receipt for a confirmed applicant booking.
	 * The receipt is printed to console and saved to a local file.
	 *
	 * @param applicantId The NRIC or ID of the applicant
	 */
	public void generateReceipt(String applicantId) {
		String receipt = printService.printReceipt(applicantId);
		if (receipt != null){
			IFileSaver.writeStringToFile(receipt, applicantId + "Recipt.txt");
			System.out.println(receipt);
		} else {
			System.out.println("No booking found for applicant " + applicantId);
		}
	}

	/**
	 * Displays a list of enquiries related to the officer’s currently active project,
	 * and allows the officer to take action on a selected enquiry.
	 *
	 * @param sc Scanner for user input
	 */
	public void viewEnquiries(Scanner sc){
		BTOProject project = retrieveOfficer().getActiveProject();
		if (project == null) {
			System.out.println("No project assigned.");
			return;
		}
		List<Enquiry> enquiryList = enquiryService.getEnquiriesbyProject(project.getProjectName());
		Enquiry selectedEnquiry = enquiryService.chooseFromEnquiryList(sc, enquiryList);
		officerView.viewEnquiryActionMenuForOfficer(sc, selectedEnquiry);
	}

	/**
	 * Allows the officer to respond to a selected enquiry, provided it hasn't already been answered.
	 *
	 * @param sc Scanner for user input
	 * @param selectedEnquiry The enquiry the officer wishes to reply to
	 */
	public void replyEnquiry(Scanner sc, Enquiry selectedEnquiry) {
		if (selectedEnquiry.getReplierUserID() != null) {
			System.out.println("This enquiry has already been replied to.");
			return;
		}
		enquiryService.replyEnquiry(sc, selectedEnquiry, retrieveOfficer().getNric());
	}

	/**
	 * Registers the officer as an applicant for a given project.
	 * This action is blocked if the officer has already applied as an applicant.
	 *
	 * @param project The BTO project the officer wants to register for
	 */
	public void registerAsOfficer(BTOProject project) {
		ProjectApplication existingApp = proAppService.getApplicationByUserAndProject(retrieveOfficer().getNric(), project.getProjectName());
		if (existingApp != null){
			System.out.println("Cannot register as an officer for \"" + project.getProjectName()
					+ "\" because you’ve already applied for it as an applicant." +
					"\nPlease check your application status.");
			System.out.println(ViewFormatter.breakLine());
			return;
		}
		offAppService.applyForOfficer(retrieveOfficer(), project);
	}

	/**
	 * Displays the current status of the officer’s officer applications
	 * (i.e., applications made to manage a project as an officer).
	 */
	public void viewOfficerApplicationStatus() {
		officerView.displayOfficerApplicationStatus();
	}

	// ------------------- Overridden Methods -------------------

	/**
	 * Enters the generic project portal available to officers (inherited from applicants),
	 * but adapted for officers with officer-specific context and filters.
	 *
	 * @param sc Scanner for user input
	 * @throws Exception if the portal fails to load
	 */
	@Override
	public void enterProjectPortal(Scanner sc) throws Exception {
		officerView.displayProjectPortal(sc, filterSettings);
	}

	/**
	 * Allows the officer to apply for a project in the capacity of an applicant,
	 * provided they haven't already applied as an officer or applicant for that project.
	 *
	 * @param sc Scanner for user input
	 * @param selectedProject The BTO project to apply for
	 */
	@Override
	public void applyForProject(Scanner sc, BTOProject selectedProject) {
		HDBOfficer officer = retrieveOfficer();
		List<OfficerApplication> applications = officer.getOfficerApplications();

		if (officer.getActiveApplication() != null) {
			System.out.println("You have already applied for a project.\n Please check your application status.");
			System.out.println(ViewFormatter.breakLine());
			return;
		}
		for (OfficerApplication application : applications) {
			if (application.getProjectName().equals(selectedProject.getProjectName())) {
				System.out.println("You have already applied as an\n officer for this project.\n Please check your application status.");
				System.out.println(ViewFormatter.breakLine());
				return;
			}
		}
		projectApplicationService.applyForProject(sc, officer, selectedProject.getProjectName());
	}
}
