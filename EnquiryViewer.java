public class EnquiryViewer implements IEnquiryViewer {
    private final ManagerController controller;

    public EnquiryViewer(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void viewProjectEnquiries(Project project) {
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ You are not authorized to view this project's enquiries.");
            return;
        }

        System.out.println("📥 Enquiries for project: " + project.getName());

        for (Enquiry e : EnquiryDB.getAllEnquiries()) {
            if (e.getProject().equals(project)) {
                System.out.println(e);
            }
        }
    }
}
