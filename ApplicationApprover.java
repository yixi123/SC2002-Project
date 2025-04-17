public class ApplicationApprover implements IApplicationApprover {
    private final ManagerController controller;
    private final IFlatQuotaManager quotaManager;

    public ApplicationApprover(ManagerController controller, IFlatQuotaManager quotaManager) {
        this.controller = controller;
        this.quotaManager = quotaManager;
    }

    @Override
    public void approveApplication(Applicant applicant) {
        HDBManager manager = controller.getCurrentManager();
        Project project = applicant.getProject();

        if (!project.getManager().equals(manager) || !project.isVisible()) {
            System.out.println("❌ Cannot approve: Not your visible project.");
            return;
        }

        if (!quotaManager.isFlatAvailable(project, applicant.getRequestedFlatType())) {
            System.out.println("❌ No units available for requested flat type.");
            return;
        }

        quotaManager.reduceFlatQuota(project, applicant.getRequestedFlatType());
        applicant.setStatus(ApplicationStatus.APPROVED);

        System.out.println("✅ Application approved.");
    }
}
