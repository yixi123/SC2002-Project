public class ApplicationRejector implements IApplicationRejector {
    private final ManagerController controller;

    public ApplicationRejector(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void rejectApplication(Applicant applicant) {
        HDBManager manager = controller.getCurrentManager();
        Project project = applicant.getProject();

        if (!project.getManager().equals(manager) || !project.isVisible()) {
            System.out.println("❌ Cannot reject: Not your visible project.");
            return;
        }

        applicant.setStatus(ApplicationStatus.REJECTED);
        System.out.println("❌ Application rejected.");
    }
}
