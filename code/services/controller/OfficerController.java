package services.controller;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.projects.ProjectDB;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import models.projects.*;
import models.users.HDBOfficer;

import services.interfaces.IEnquiryService;
import services.interfaces.IOfficerApplicationService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IReceiptPrintService;

import services.subservices.EnquiryService;
import services.subservices.OfficerApplicationService;
import services.subservices.ProjectApplicationService;
import services.subservices.ReceiptPrintService;

import view.OfficerView;
import view.ViewFormatter;


public class OfficerController extends ApplicantController {
  private final IOfficerApplicationService offAppService;
  private final IProjectApplicationService proAppService;
  private final IEnquiryService enquiryService;
  private final IReceiptPrintService printService;

  private static OfficerView officerView = new OfficerView();


  public OfficerController() {
		this.offAppService  = new OfficerApplicationService();
		this.proAppService  = new ProjectApplicationService();
		this.enquiryService = new EnquiryService();
		this.printService   = new ReceiptPrintService();
 	}	

	public HDBOfficer retrieveOfficer(){
			HDBOfficer currentUser = (HDBOfficer) auth.getCurrentUser();
			currentUser.setAssignedProject(ProjectDB.getProjectsByOfficer(currentUser.getNric()));
			currentUser.setOfficerApplications(offAppService.getApplicationsByUser(currentUser.getNric()));
      currentUser.setMyApplication(ProjectAppDB.getApplicationByUser(currentUser.getNric()));
			return currentUser;
		}


	@Override
	public void start(Scanner sc) throws Error{
		try{
			officerView.enterMainMenu(sc);
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

  	//--------------------------------------------------------------------------------

	public void viewHandledProject() {
		try{
			officerView.displayHandledProject();
		} catch(NullPointerException e){
			System.out.println("No project assigned.");
		} catch(Exception e){
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

	public void enterOfficerProjectPortal(Scanner sc) throws Error {
		try{
      filterSettings.setVisibility(true);
      filterSettings.setActiveDate(new Date());
			officerView.displayOfficerProjectPortal(sc, filterSettings);
		}catch(Exception e){
			System.out.println("An error occurred: " + e.getMessage());
		} 
	}

	public void viewSuccessfulApplicantsList() {
		officerView.displayHandledProjectSuccessfulApplicants();
	}

	public void handleApplicantsSuccessfulApp(String applicantId) {
		BTOProject project = retrieveOfficer().getActiveProject();
		if (project == null) {
			System.out.println("No project assigned.");
			return;
		}
		proAppService.bookApplication(project.getProjectName(), applicantId);
		
	}

	public void generateReceipt(String applicantId) {
		String receipt = printService.printReceipt(applicantId);
		if (receipt != null){
			System.out.println(receipt);
		} else {
			System.out.println("No booking found for applicant " + applicantId);
		}
	}

	public void viewEnquiries(Scanner sc){
		BTOProject project = retrieveOfficer().getActiveProject();
		if (project == null) {
			System.out.println("No project assigned.");
			return;
		}
		List<Enquiry> enquiryList =  enquiryService.getEnquiriesbyProject(project.getProjectName());
		Enquiry selectedEnquiry = enquiryService.chooseFromEnquiryList(sc, enquiryList);
    officerView.viewEnquiryActionMenuForOfficer(sc, selectedEnquiry);
  }

  public void replyEnquiry(Scanner sc, Enquiry selectedEnquiry) {
    if (selectedEnquiry.getReplierUserID() != null) {
      System.out.println("This enquiry has already been replied to.");
      return;
    }
    System.out.print("Enter your reply content: ");
    String replyContent = sc.nextLine();
    System.out.println(ViewFormatter.breakLine());
    enquiryService.replyEnquiry(selectedEnquiry.getId(), retrieveOfficer().getNric(), replyContent);
    System.out.println("Reply sent successfully.");
    System.out.println(ViewFormatter.breakLine());
  }


	public void registerAsOfficer(BTOProject project) {
		offAppService.applyForOfficer(retrieveOfficer(), project);
	}

  public void viewOfficerApplicationStatus() {
     officerView.displayOfficerApplicationStatus();
  }


	// -----------------------------------------------------------------------------------
	@Override
	public void enterProjectPortal(Scanner sc) throws Exception{
		officerView.displayProjectPortal(sc, filterSettings);
	}

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
