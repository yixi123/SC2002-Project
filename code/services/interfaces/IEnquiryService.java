package services.interfaces;

import java.util.List;
import java.util.Scanner;
import models.projects.Enquiry;

public interface IEnquiryService {

    public void addEnquiry(String nric, String projectName, String content);

    public List<Enquiry> getMyEnquiries(String nric);

    public Enquiry chooseFromEnquiryList(Scanner sc, List<Enquiry> enquiryList);

    public void enquiryOption(Scanner sc, Enquiry selectedEnquiry);

    
}
