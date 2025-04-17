public class ApplicationHandler implements IApplicationHandler {
    private final ManagerController controller;
    private final FlatQuotaManager quotaManager;

    public ApplicationHandler(ManagerController controller) {
        this.controller = controller;
        this.quotaManager = new FlatQuotaManager();
    }

    @Override
    public void approveApplication(Applicant applicant) {
        Project project = applicant.getProject();
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager) || !project.isVisible()) {
            System.out.println("❌ Cannot approve: Not your active project.");
            return;
        }

        if (!quotaManager.isQuotaAvailable(project, applicant.getRequestedFlatType())) {
            System.out.println("❌ No units available for requested flat type.");
            return;
        }

        quotaManager.reduceQuota(project, applicant.getRequestedFlatType());
        applicant.setStatus(ApplicationStatus.APPROVED);

        System.out.println("✅ Application approved.");
    }

    @Override
    public void rejectApplication(Applicant applicant) {
        Project project = applicant.getProject();
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager) || !project.isVisible()) {
            System.out.println("❌ Cannot reject: Not your active project.");
            return;
        }

        applicant.setStatus(ApplicationStatus.REJECTED);
        System.out.println("❌ Application rejected.");
    }

    @Override
    public void handleWithdrawal(Applicant applicant, boolean approve) {
        Project project = applicant.getProject();
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager) || !project.isVisible()) {
            System.out.println("❌ Cannot process withdrawal: Not your active project.");
            return;
        }

        if (approve) {
            applicant.setStatus(ApplicationStatus.WITHDRAWN);
            System.out.println("✅ Withdrawal approved.");
        } else {
            applicant.setStatus(ApplicationStatus.REJECTED);
            System.out.println("❌ Withdrawal rejected.");
        }
    }
}
