public class EnquiryViewer implements IEnquiryViewer {
    @Override
    public void viewProjectEnquiries(Project project) {
        HDBManager manager = (HDBManager) LoginSession.getUser();

        

        for (Enquiry e : EnquiryDB.getAllEnquiries()) {
            if (e.getProject().equals(project)) {
                System.out.println(e);
            }
        }
    }
}