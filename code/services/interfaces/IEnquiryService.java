package services.interfaces;

import java.util.List;
import java.util.Scanner;
import models.projects.Enquiry;

public interface IEnquiryService {

    
    public List<Enquiry> getMyEnquiries(String nric);

    public Enquiry chooseFromEnquiryList(Scanner sc, List<Enquiry> enquiryList);

    public void viewEnquiryActionMenu(Scanner sc, Enquiry selectedEnquiry);

    public void addEnquiry(String nric, String projectName, String content);

    public void replyEnquiry(int id, String replierID, String reply); 
    
    public void deleteEnquiry(int id);

    public Enquiry findEnquiry(int id);

    public void editEnquiry(Scanner sc, Enquiry selectedEnquiry);

    public void viewAllEnquiries();

    public void viewEnquiriesByProject(String projectName);


}
    
    
        
