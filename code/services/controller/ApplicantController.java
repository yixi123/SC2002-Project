package services.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import models.projects.Enquiry;
import models.projects.FilterSettings;
import models.users.Applicant;
import models.users.User;
import services.subservices.EnquiryService;
import view.ApplicantView;
import view.ProjectView;

public class ApplicantController extends UserController{
    static FilterSettings filterSettings = new FilterSettings();
    ApplicantView applicantView = new ApplicantView();

    public Applicant retreiveApplicant(){
        return (Applicant) auth.getCurrentUser();
    }

    public static void start(Scanner scanner){
        
    }

    @Override
    public void viewProjectList(){

    }

    public void applyProject(Scanner scanner){

    }

    public void viewApplicationStatus() {

    }

    public void withdrawProject() {

    }

    public void addEnquiry() {

    }

    public void viewEnquiry(){
        
    }
}
